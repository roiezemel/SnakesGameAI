package Algorithm;

import Game.Block;
import Game.Direction;
import Game.Main;
import Game.Vertebra;

import java.awt.geom.Line2D;
import java.util.LinkedList;

public class Population {


    public static int gridWidth = 50;
    public static int gridHeight = 50;
    public static Block[][] grid = new Block[gridWidth][gridHeight];

    public LinkedList<Vertebra> snakeAI;
    public LinkedList<Vertebra> snake;

    public static int loops = 0;

    private int populationSize = 50;
    private double mutationRate = 0.05;
    private double mutationStep = 0.2;

    private Genome[] genomes;

    private int currentGenome = 0;
    private int generation = 1;

    public Population(){
        genomes = new Genome[populationSize];
    }

    public Population(int populationSize, double mutationRate, double mutationStep) {
        this.populationSize = populationSize;
        this.mutationRate = mutationRate;
        this.mutationStep = mutationStep;

        genomes = new Genome[populationSize];
    }

    public void clear(){
        for(int i = 0; i< gridWidth; i++){
            for(int j = 0; j< gridHeight; j++){
                loops++;
                grid[i][j].check = false;
                grid[i][j].check2 = false;
            }
        }
    }

    private static double distance(int x1, int y1, int x2, int y2){
        double dX = Math.abs(x2 - x1);
        double dY = Math.abs(y2 - y1);

        return Math.sqrt(Math.pow(dX, 2) + Math.pow(dY, 2));
    }

    public double distanceToClosestWall(){

        double left = snakeAI.get(0).getX();

        double right = gridWidth - snakeAI.get(0).getX() - 1;

        double top = snakeAI.get(0).getY();

        double bottom = gridHeight - snakeAI.get(0).getY() - 1;

        double[] distances = {left, right, top, bottom};

        double smallest = 1000000000;
        for(int i = 0; i<4; i++){
            if(distances[i] < smallest)
                smallest = distances[i];
            loops++;
        }

        return smallest;

    }

    public double distanceToWallInSnakeDirection(){

        switch (Main.direction){
            case UP:
                return snakeAI.get(0).getY();
            case DOWN:
                return gridHeight - snakeAI.get(0).getY() - 1;
            case RIGHT:
                return gridWidth - snakeAI.get(0).getX() - 1;
            case LEFT:
                return snakeAI.get(0).getX();
        }

        return 0;
    }

    public double distanceToApple(){
        return distance(snakeAI.get(0).getX(), snakeAI.get(0).getY(), Main.appleX, Main.appleY);
    }

    public double distanceToAppleInSnakeDirection(){

        if(Main.appleX == snakeAI.get(0).getX()) {
            if (Main.direction == Direction.UP) {
                if (Main.appleY <= snakeAI.get(0).getY()) {
                    return snakeAI.get(0).getY() - Main.appleY;
                } else
                    return gridHeight;
            }

            if (Main.direction == Direction.DOWN) {
                if (Main.appleY > snakeAI.get(0).getY()) {
                    return Main.appleY - snakeAI.get(0).getY();
                } else
                    return gridHeight;
            }
        }

        else if(Main.appleY == snakeAI.get(0).getY()) {
            if (Main.direction == Direction.RIGHT) {
                if (Main.appleX >= snakeAI.get(0).getX()) {
                    return Main.appleX - snakeAI.get(0).getX();
                } else
                    return gridWidth;
            }

            if (Main.direction == Direction.LEFT) {
                if (Main.appleX < snakeAI.get(0).getX()) {
                    return snakeAI.get(0).getX() - Main.appleX;
                } else
                    return gridWidth;
            }
        }

        return (Main.direction == Direction.RIGHT || Main.direction == Direction.LEFT)? gridWidth : gridHeight;
    }

    public double distanceToClosestBodyPart(){
        double smallest = 1000000000;
        for(int i = 4; i< snakeAI.size(); i++){
            loops++;
            double distance = distance(snakeAI.get(0).getX(), snakeAI.get(0).getY(), snakeAI.get(i).getX(), snakeAI.get(i).getY());
            if(distance < smallest)
                smallest = distance;
        }
        return smallest;
    }

    public double distanceToBodyPartInSnakeDirection(){

        for(int i = 1; i< snakeAI.size(); i++){
            loops++;
            double x = snakeAI.get(i).getX();
            double y = snakeAI.get(i).getY();

            if(x == snakeAI.get(0).getX()) {
                if (Main.direction == Direction.UP) {
                    if (y <= snakeAI.get(0).getY()) {
                        return snakeAI.get(0).getY() - y;
                    }
                }

                if (Main.direction == Direction.DOWN) {
                    if (y > snakeAI.get(0).getY()) {
                        return y - snakeAI.get(0).getY();
                    }
                }
            }

            else if(y == snakeAI.get(0).getY()) {
                if (Main.direction == Direction.RIGHT) {
                    if (x >= snakeAI.get(0).getX()) {
                        return x - snakeAI.get(0).getX();
                    }
                }

                if (Main.direction == Direction.LEFT) {
                    if (x < snakeAI.get(0).getX()) {
                        return snakeAI.get(0).getX() - x;
                    }
                }
            }

        }

        return distanceToWallInSnakeDirection();
    }

    public double distanceToClosestPointWithClearWayToApple(){

        if(!isLineIntersectsBodyParts(snakeAI.get(0).getX(), snakeAI.get(0).getY(), Main.appleX, Main.appleY)){
            return 0;
        }

        int x = 0;
        int y = 0;
        double smallest = 1000000000;
        for(int i = 0; i< gridWidth; i++){
            for(int j = 0; j< gridHeight; j++){
                loops++;
                 if((!isLineIntersectsBodyParts(snakeAI.get(0).getX(), snakeAI.get(0).getY(), i, j)) && (!isLineIntersectsBodyParts(i, j, Main.appleX, Main.appleY))){
                     double distance = distance(snakeAI.get(0).getX(), snakeAI.get(0).getY(), i, j);
                     if(distance < smallest){
                         x = i;
                         y = j;
                         smallest = distance;
                     }
                 }
            }
        }

        grid[x][y].check2 = true;
        if(smallest != 1000000000) {
            if(smallest > 25){
                System.out.println("Wow!");
                return distanceToLoneliestPointInTheArea();
            }
            return smallest;
        }
        else {
            return gridWidth;
        }

    }

    public double distanceToLoneliestPointInTheArea(){

        int biggest = -1000000000;
        double distance = 0;
        int x = 0;
        int y = 0;
        for(int i = 0; i< gridWidth; i++){
            for(int j = 0; j< gridHeight; j++){
                loops++;
                if(!isLineIntersectsBodyParts(snakeAI.get(0).getX(), snakeAI.get(0).getY(), i, j)){
                    int emptyAreaCounter = emptyAreaCounter(i, j);
                    if(emptyAreaCounter > biggest) {
                        x = i;
                        y = j;
                        biggest = emptyAreaCounter;
                        distance = distance(snakeAI.get(0).getX(), snakeAI.get(0).getY(), i, j);
                    }
                }
            }
        }

        grid[x][y].check = true;
        return distance;

    }

    public int emptyAreaCounter(int x, int y){

        int counter = 0;

        int step = 1;

        boolean stuck = false;

        while(!stuck){
            for(int i = x - step; i<=x + step; i++){
                loops++;
                if(i >= 0 && i< gridWidth && y + step >= 0 && y + step < gridHeight && !intersectsSnake(i, y + step) && !stuck){
                    counter++;
                }

                else{
                    stuck = true;
                }
            }

            for(int i = x - step; i<=x + step; i++){
                loops++;
                if(i >= 0 && i< gridWidth && y - step >= 0 && y - step < gridHeight && !intersectsSnake(i, y - step) && !stuck){
                    counter++;
                }

                else{
                    stuck = true;
                }
            }

            ///

            for(int i = y - step; i<=y + step; i++){
                loops++;
                if(i >= 0 && i< gridHeight && x + step >= 0 && x + step < gridWidth && !intersectsSnake(i, x + step) && !stuck){
                    counter++;
                }

                else{
                    stuck = true;
                }
            }

            for(int i = y - step; i<=y + step; i++){
                loops++;
                if(i >= 0 && i< gridHeight && x - step >= 0 && x - step < gridWidth && !intersectsSnake(i, x - step) && !stuck){
                    counter++;
                }

                else{
                    stuck = true;
                }
            }
            step++;
        }

        return counter;

    }

    public boolean isLineIntersectsBodyParts(double x1, double y1, double x2, double y2){

        double actualX1 = grid[0][0].getRectangle().x + grid[0][0].getRectangle().width * x1 + grid[0][0].getRectangle().width/2;
        double actualY1 = grid[0][0].getRectangle().y + grid[0][0].getRectangle().height * y1 + grid[0][0].getRectangle().height/2;
        double actualX2 = grid[0][0].getRectangle().x + grid[0][0].getRectangle().width * x2 + grid[0][0].getRectangle().width/2;
        double actualY2 = grid[0][0].getRectangle().y + grid[0][0].getRectangle().height * y2 + grid[0][0].getRectangle().height/2;

        Line2D line = new Line2D.Double();

        line.setLine(actualX1, actualY1, actualX2, actualY2);

        for(int i = 1; i< snakeAI.size(); i++){
            loops++;
            if(line.intersects(grid[snakeAI.get(i).getX()][snakeAI.get(i).getY()].getRectangle())){
                return true;
            }
        }

        return false;
    }

    private boolean intersectsSnake(int x, int y){
        for(int i = 0; i< snakeAI.size(); i++){
            loops++;
            if(snakeAI.get(i).getX() == x && snakeAI.get(i).getY() == y){
                return true;
            }
        }

        return false;
    }

    public void tryMoving(Direction direction){

        for(int i = 0; i< gridWidth; i++){
            for(int j = 0; j< gridHeight; j++){
                loops++;
                grid[i][j].check = false;
                grid[i][j].check2 = false;
            }
        }

       snakeAI = new LinkedList<>();

        for(int i = 0; i< snake.size(); i++){
            loops++;
            snakeAI.add(new Vertebra(snake.get(i).getX(), snake.get(i).getY(), snake.get(i).getNextVertebra()));
        }

        for (int i = snakeAI.size() - 1; i>=0; i--) {
            loops++;
            snakeAI.get(i).moveToNextPosition();
        }

        switch (direction) {
            case UP:
                snakeAI.get(0).decreaseY();
                break;
            case DOWN:
                snakeAI.get(0).increaseY();
                break;
            case RIGHT:
                snakeAI.get(0).increaseX();
                break;
            case LEFT:
                snakeAI.get(0).decreaseX();
                break;
        }

    }

    public void initializeRandomPopulation(){
        for(int i = 0; i< genomes.length; i++){
            genomes[i] = new Genome();
        }
    }

    private Genome breed(Genome mum, Genome dad){

        Genome child = new Genome();

        for(int i = 0; i<child.weights.length; i++){
            child.weights[i] = randomlyPick(mum, dad).weights[i];
            if(Math.random() < mutationRate){
                child.weights[i] = child.weights[i] + (Math.random()*2 - 1) * mutationStep * 2 - mutationStep;
                child.mutated = true;
            }
        }

        return child;
    }

    private Genome breedNetworks(Genome mum, Genome dad){
        Genome child = new Genome();

        for (int i = 1; i<child.net.getWeights().length; i++){
                child.net.getWeights()[i] = randomlyPick(mum, dad).net.getWeights()[i];
                if (Math.random() < mutationRate){
                    for (int j = 0; j<child.net.getWeights()[i].length; j++)
                        for (int d = 0; d<child.net.getWeights()[i][j].length; d++)
                            child.net.getWeights()[i][j][d] += (Math.random()*2 - 1) * mutationStep * 2 - mutationStep;
                    child.mutated = true;
                }
        }

        return child;
    }

    private Genome randomlyPick(Genome g1, Genome g2){
        if(Math.random() > 0.5)
            return g1;
        else
            return g2;
    }

    private void crossover(){

       sort();

       Genome[] newGenomes = new Genome[populationSize];

       Genome[] optionalParents = new Genome[populationSize/5];

       for(int i = 0; i<optionalParents.length; i++){
           optionalParents[i] = genomes[i];
       }

       for(int i = 0; i<populationSize; i++){
           Genome mum = optionalParents[(int)(Math.random()*(optionalParents.length-1))];
           Genome dad = optionalParents[(int)(Math.random()*(optionalParents.length-1))];
           newGenomes[i] = breed(mum, dad);//breedNetworks(mum, dad); //breed(mum, dad);
       }

       genomes = newGenomes;

    }

    private void sort(){
        for(int i = 0; i<genomes.length; i++){
            for(int j = i+1; j<genomes.length; j++){
                if(genomes[i].getFitness() < genomes[j].getFitness()){
                    Genome save = genomes[i];
                    genomes[i] = genomes[j];
                    genomes[j] = save;
                }
            }
        }
    }

    public void genomeLoosed(double score){

        if(currentGenome < populationSize-1) {
            genomes[currentGenome].setFitness(score);
            currentGenome++;
        }
        else{
           currentGenome = 0;
           generation++;
           crossover();
        }
    }

    public Genome getCurrentGenome(){
        return genomes[currentGenome];
    }

    public int getPopulationSize() {
        return populationSize;
    }

    public double getMutationRate() {
        return mutationRate;
    }

    public double getMutationStep() {
        return mutationStep;
    }

    public Genome[] getGenomes() {
        return genomes;
    }

    public int getGeneration() {
        return generation;
    }

    public int getGenomeNumber(){
        return currentGenome + 1;
    }
}
