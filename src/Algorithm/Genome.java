package Algorithm;

import Game.Direction;
import Game.Main;
import NeuralNetworks.Network;
import utils.list;
import utils.tools;

public class Genome extends tools {

    /*
    Parameters:

    1. Distance to the closest wall.
    2. Distance to the wall in the snake's direction.
    3. Distance to the apple.
    4. Distance to the apple in the snake's direction (Default: grid's width).
    5. Distance to the closest body part.
    6. Distance to the body part in the snake's direction (Default: distance to the wall).
    7. Distance to the closest point which has a straight and clear way to the apple.
     */

    public double[] weights = {
            Math.random()*2 - 1, // 1. Distance to the closest wall.
            Math.random()*2 - 1, // 2. Distance to the wall in the snake's direction.
            Math.random()*2 - 1, // 3. Distance to the apple.
            Math.random()*2 - 1, // 4. Distance to the apple in the snake's direction (Default: grid's width).
            Math.random()*2 - 1, // 5. Distance to the closest body part.
            Math.random()*2 - 1, // 6. Distance to the body part in the snake's direction (Default: distance to the wall).
            Math.random()*2 - 1, // 7. Distance to the closest point which has a straight and clear way to the apple.
    };

    private double fitness;

    public boolean mutated = false;

    public Network net;

    public Genome(){
        net = new Network(7, 4);
    }

    public Direction decision(){

        Direction[] directions = {Direction.UP, Direction.DOWN, Direction.RIGHT, Direction.LEFT};
        double[] ratings = new double[4];

        for(int i = 0; i<4; i++){
            Population.loops++;
            if(directions[i] != Main.illegalDirection()){
                Main.population.tryMoving(directions[i]);
                ratings[i] =
                        weights[0] * Main.population.distanceToClosestWall()
                        + weights[1] * Main.population.distanceToWallInSnakeDirection()
                        + weights[2] * Main.population.distanceToApple()
                        + weights[3] * Main.population.distanceToAppleInSnakeDirection()
                        + weights[4] * Main.population.distanceToClosestBodyPart()
                        + weights[5] * Main.population.distanceToBodyPartInSnakeDirection()
                        + weights[6] * Main.population.distanceToClosestPointWithClearWayToApple();
            }
            else{
                ratings[i] = -1000000000;
            }
        }

        double biggest = -1000000000;
        int index = -1;
        for(int i = 0; i<4; i++){
            Population.loops++;
            if(ratings[i] > biggest) {
                biggest = ratings[i];
                index = i;
            }
        }

        Population.loops = 0;
        return directions[index];
    }

    public Direction networkDecision(){
        Direction[] directions = {Direction.UP, Direction.DOWN, Direction.RIGHT, Direction.LEFT};
        Main.population.clear();
        Main.population.snakeAI = Main.population.snake;
        double[] input = {
                Main.population.distanceToClosestWall(), // 1
                Main.population.distanceToWallInSnakeDirection(), // 2
                Main.population.distanceToApple(), // 3
                Main.population.distanceToAppleInSnakeDirection(), // 4
                Main.population.distanceToClosestBodyPart(), // 5
                Main.population.distanceToBodyPartInSnakeDirection(), // 6
                Main.population.distanceToClosestPointWithClearWayToApple()}; // 7
        double max = max(input);
        for (int i = 0; i<input.length; i++){
            input[i] /= max;
        }
        net.think(input);
        int index = maxindex(net.getOutputLayer());
        if (directions[index] == Main.illegalDirection()){
            index = maxindex(remove(net.getOutputLayer(), index));
        }
        return directions[index];
    }

    public double getFitness() {
        return fitness;
    }

    public void setFitness(double fitness) {
        this.fitness = fitness;
    }
}
