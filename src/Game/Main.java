package Game;

import Algorithm.Genome;
import Algorithm.Population;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Line2D;
import java.io.File;
import java.text.DecimalFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.Formatter;
import java.util.LinkedList;
import java.util.Scanner;


public class Main extends JPanel implements KeyListener {

    // Members:
    // Game members:
    boolean active = true;

    double score = 0;
    double highScore = 0;

    int blockWidth;
    int blockHeight;

    public static Direction direction = Direction.RIGHT;
    boolean showGrid = false;
    boolean showDetails = true;
    boolean wait = true;

    int newVertebra = 0;

    int FPS = 1001;

    public static int appleX = (int)(Math.random()* Population.gridWidth);
    public static int appleY = (int)(Math.random()* Population.gridHeight);

    // Paint
    Font font = new Font("David", Font.BOLD, 30);
    Font keysFont = new Font("Miriam Fixed", Font.BOLD, 20);
    Font weightsFont = new Font("Miriam Fixed", Font.BOLD,  15);

    // Handling quick mouse events:
    int updateCounter = 0;
    int lastUpdate = 0;
    int saved = 0;

    // Algorithm
    boolean AI = true;
    boolean AIController = false;
    public static Population population = new Population();
    int appleCount = 0;
    Genome player = new Genome();
    Genome highScorer = new Genome();
    boolean mutated = false;

    public Main(){

        this.addKeyListener(this);
        this.setFocusable(true);

        JFrame frame = new JFrame("Snakes Game AI");
        frame.setSize(1200, 900);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(this);

        blockWidth = (frame.getWidth() - 301)/ Population.gridWidth;
        blockHeight = (frame.getHeight() - 60)/ Population.gridHeight;
        int x = 10;
        int y = 30;
        for (int i = 0; i< Population.gridWidth; i++){
            for (int j = 0; j< Population.gridHeight; j++){
                Population.grid[i][j] = new Block(new Rectangle(x, y, blockWidth, blockHeight));
                y+=blockHeight;
            }
            y = 30;
            x+=blockWidth;
        }

        population.initializeRandomPopulation();

        reset();

        Population.grid[appleX][appleY].setState(State.APPLE);

        gameLoop();

    }

    public void gameLoop(){
        while(active){
            Instant start = Instant.now();
            update();
            if(wait)
                wait(1000/FPS);
            while(!active){
                wait(10);
            }
            Instant finish = Instant.now();
            long timeElapsed = Duration.between(start, finish).toMillis() - 1000/FPS;
        }
    }

    public void reset(){

        appleCount = 0;
        updateCounter = 0;
        lastUpdate = 0;
        newVertebra = 0;
        score = 0;
        direction = Direction.RIGHT;

        for (int i = 0; i< Population.gridWidth; i++){
            for (int j = 0; j< Population.gridHeight; j++){
                Population.grid[i][j].setState(State.EMPTY);
            }
        }

        Population.grid[appleX][appleY].setState(State.APPLE);

        population.snake = new LinkedList<>();
        population.snake.add(new Vertebra(Population.gridWidth/2, Population.gridHeight/2));
        population.snake.add(new Vertebra(Population.gridWidth/2 - 1, Population.gridHeight/2, population.snake.get(0)));
        population.snake.add(new Vertebra(Population.gridWidth/2 - 2, Population.gridHeight/2, population.snake.get(1)));
        population.snake.add(new Vertebra(Population.gridWidth/2 - 3, Population.gridHeight/2, population.snake.get(2)));
        population.snake.add(new Vertebra(Population.gridWidth/2 - 4, Population.gridHeight/2, population.snake.get(3)));

        population.snakeAI = new LinkedList<>();
        for(int i = 0; i< population.snake.size(); i++){
            population.snakeAI.add(new Vertebra(population.snake.get(i).getX(), population.snake.get(i).getY(), population.snake.get(i).getNextVertebra()));
        }
    }

    @Override
    public void paint(Graphics g){

        Graphics2D g2d = (Graphics2D)g;
        g2d.setColor(Color.DARK_GRAY);
        g2d.fillRect(0, 0, getWidth(), getHeight());

        for (int i = 0; i< Population.gridWidth; i++){
            for (int j = 0; j< Population.gridHeight; j++){
                g2d.setColor(Population.grid[i][j].getColor());
                if(mutated && Population.grid[i][j].getColor() == Color.LIGHT_GRAY)
                    g2d.setColor(Color.RED);
                if(Population.grid[i][j].check)
                    g2d.setColor(Color.GREEN);
                if(Population.grid[i][j].check2)
                    g2d.setColor(Color.MAGENTA);
                g2d.fill(Population.grid[i][j].getRectangle());

                if(showGrid){
                    g2d.setColor(Color.GRAY);
                    g2d.draw(Population.grid[i][j].getRectangle());
                }
            }
        }

        g2d.setColor(Color.GRAY);
        g2d.drawRect(10, 30, blockWidth* Population.gridWidth, blockHeight* Population.gridHeight);

        g2d.setColor(Color.GRAY);

        if(newVertebra > 0)
            g2d.setColor(Color.ORANGE);

        g2d.setFont(font);
        g2d.drawString("Score: " + score, Population.gridWidth*blockWidth + 50, 100);

        g2d.setColor(Color.ORANGE);
        g2d.drawString("High Score: " + highScore, Population.gridWidth*blockWidth + 50, 700);


        g2d.setColor(Color.LIGHT_GRAY);
        g2d.setFont(keysFont);
        g2d.drawString("Keyboard Control:", Population.gridWidth*blockWidth + 50, 170);

        g2d.drawString("R - Restart", Population.gridWidth*blockWidth + 50, 230);
        g2d.drawString("G - Show grid", Population.gridWidth*blockWidth + 50, 260);
        g2d.drawString("+ - Increase FPS", Population.gridWidth*blockWidth + 50, 290);
        g2d.drawString("- - Decrease FPS", Population.gridWidth*blockWidth + 50, 320);
        g2d.drawString("A - AI Controller", Population.gridWidth*blockWidth + 50, 350);
        g2d.drawString("S - Save genome", Population.gridWidth*blockWidth + 50, 380);
        g2d.drawString("U - Upload genome", Population.gridWidth*blockWidth + 50, 410);
        g2d.drawString("H - Save high scorer", Population.gridWidth*blockWidth + 50, 440);
        g2d.drawString("Z - Reset FPS", Population.gridWidth*blockWidth + 50, 470);
        g2d.drawString("C - Skip player", Population.gridWidth*blockWidth + 50, 500);
        g2d.drawString("D - Show details", Population.gridWidth*blockWidth + 50, 530);



        g2d.drawString("FPS: " + FPS, Population.gridWidth*blockWidth + 50, 580);
        if(AI) {
            g2d.drawString("Generation: " + population.getGeneration(), Population.gridWidth * blockWidth + 50, 610);
            g2d.drawString("Genome number " + population.getGenomeNumber(), Population.gridWidth * blockWidth + 50, 640);
        }

        if(showDetails) {
            g2d.setFont(weightsFont);
            g2d.setColor(Color.GRAY);
            g2d.drawString("Distance to closest wall:                            " + population.getCurrentGenome().weights[0], 30, 50);
            g2d.drawString("Distance to wall in snake's direction:               " + population.getCurrentGenome().weights[1], 30, 65);
            g2d.drawString("Distance to the apple:                               " + population.getCurrentGenome().weights[2], 30, 80);
            g2d.drawString("Distance to apple in snake's direction:              " + population.getCurrentGenome().weights[3], 30, 95);
            g2d.drawString("Distance to closest body part:                       " + population.getCurrentGenome().weights[4], 30, 110);
            g2d.drawString("Distance to body part in snake's direction:          " + population.getCurrentGenome().weights[5], 30, 125);
            g2d.drawString("Distance to closest point with clear way to apple:   " + population.getCurrentGenome().weights[6], 30, 140);
        }
    }

    public void wait(int milliSeconds){
        try {
            Thread.sleep(milliSeconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void update(){
        try {

            if(population.getCurrentGenome().mutated)
                mutated = true;
            else
                mutated = false;

            player = population.getCurrentGenome();

            if(AI) {
                direction = population.getCurrentGenome().decision();
            }
            else if(AIController) {
                direction = player.decision();
            }

            Population.grid[population.snake.get(population.snake.size() - 1).getX()][population.snake.get(population.snake.size() - 1).getY()].setState(State.EMPTY);

            for (int i = population.snake.size() - 1; i>=0; i--) {
                population.snake.get(i).moveToNextPosition();
                Population.grid[population.snake.get(i).getX()][population.snake.get(i).getY()].setState(State.BODY);
            }

            switch (direction) {
                case UP:
                    population.snake.get(0).decreaseY();
                    break;
                case DOWN:
                    population.snake.get(0).increaseY();
                    break;
                case RIGHT:
                    population.snake.get(0).increaseX();
                    break;
                case LEFT:
                    population.snake.get(0).decreaseX();
                    break;
            }

            Population.grid[population.snake.get(0).getX()][population.snake.get(0).getY()].setState(State.HEAD);

            if(isTouchingApple()){
                appleCount++;
            }

            if(isTouchingApple() || newVertebra > 0){
                newVertebra++;
                if(newVertebra > 6)
                    newVertebra = 0;
                score += 1;
                if (population.snake.get(population.snake.size() - 2).getX() > population.snake.get(population.snake.size() - 1).getX()) {
                    population.snake.add(new Vertebra(population.snake.get(population.snake.size() - 1).getX() - 1, population.snake.get(population.snake.size() - 1).getY(), population.snake.get(population.snake.size() - 1)));
                }
                if (population.snake.get(population.snake.size() - 2).getX() < population.snake.get(population.snake.size() - 1).getX()) {
                    population.snake.add(new Vertebra(population.snake.get(population.snake.size() - 1).getX() + 1, population.snake.get(population.snake.size() - 1).getY(), population.snake.get(population.snake.size() - 1)));
                }
                if (population.snake.get(population.snake.size() - 2).getY() > population.snake.get(population.snake.size() - 1).getY()) {
                    population.snake.add(new Vertebra(population.snake.get(population.snake.size() - 1).getX(), population.snake.get(population.snake.size() - 1).getY() - 1, population.snake.get(population.snake.size() - 1)));
                }
                if (population.snake.get(population.snake.size() - 2).getY() < population.snake.get(population.snake.size() - 1).getY()) {
                    population.snake.add(new Vertebra(population.snake.get(population.snake.size() - 1).getX(), population.snake.get(population.snake.size() - 1).getY() + 1, population.snake.get(population.snake.size() - 1)));
                }
                if(newVertebra == 1) {
                    putApple();
                }
            }

            for (Vertebra vertebra : population.snake){
                if(population.snake.get(0).getX() == vertebra.getX() && population.snake.get(0).getY() == vertebra.getY() && vertebra != population.snake.get(0)){
                    active = false;
                    if(AI) {
                        if(score > highScore) {
                            highScore = score;
                            highScorer = population.getCurrentGenome();
                            save("weights" + saved + ".txt", highScorer);
                            saved++;
                        }
                        population.genomeLoosed(score);
                        reset();
                        active = true;
                    }
                }
            }

            if(AI && score > 20){
                if(appleCount == 0 || (appleCount == 1 && score > 30) || (appleCount == 2 && score > 40) || (appleCount == 3 && score > 50)) {
                    score = 0;
                    population.genomeLoosed(score);
                    reset();
                    active = true;
                    System.out.println("Canceled!");
                }
            }

            Population.grid[appleX][appleY].setState(State.APPLE);

            score+=0.01; // instead of 0.01

            DecimalFormat df = new DecimalFormat("#.00");

            score = Double.parseDouble(df.format(score));

            updateCounter++;

        } catch(ArrayIndexOutOfBoundsException e){
            active = false;
            if(AI) {
                if(score > highScore) {
                    highScore = score;
                    highScorer = population.getCurrentGenome();
                    save("weights" + saved + ".txt", highScorer);
                    saved++;
                }
                population.genomeLoosed(score);
                reset();
                active = true;
            }
        }
        finally {
            repaint();
        }

    }

    public void putApple(){
        appleX = (int) (Math.random() * Population.gridWidth);
        appleY = (int) (Math.random() * Population.gridHeight);
        while(Population.grid[appleX][appleY].getState() == State.BODY){
            appleX = (int) (Math.random() * Population.gridWidth);
            appleY = (int) (Math.random() * Population.gridHeight);
        }
        Population.grid[appleX][appleY].setState(State.APPLE);
    }

    public boolean isTouchingApple(){

        for (Vertebra vertebra : population.snake){
            if(vertebra.getX() == appleX && vertebra.getY() == appleY)
                return true;
        }

        return false;

    }

    public static void main(String[] args){
        new Main();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(!AI && !AIController) {
            if (keyEventToDirection(e) != illegalDirection() && updateCounter > lastUpdate) {
                direction = keyEventToDirection(e);
                lastUpdate = updateCounter;
            }
        }

        if(e.getKeyCode() == KeyEvent.VK_G){
            if(showGrid)
                showGrid = false;
            else
                showGrid = true;
        }

        if(e.getKeyCode() == KeyEvent.VK_R && !AI){
            active = true;
            reset();
        }

        if(e.getKeyCode() == KeyEvent.VK_EQUALS){
            FPS++;
        }

        if(e.getKeyCode() == KeyEvent.VK_MINUS){
            FPS--;
            if(FPS == 0)
                FPS = 1;
        }

        if(e.getKeyCode() == KeyEvent.VK_A){
            if(AI) {
                AI = false;
                AIController = true;
            }
            else{
                AI = true;
                AIController = false;
                active = true;
                reset();
            }
        }

        if(e.getKeyCode() == KeyEvent.VK_S){
            save("weights.txt", population.getCurrentGenome());
        }

        if(e.getKeyCode() == KeyEvent.VK_U){
            read("weights14.txt");
        }

        if(e.getKeyCode() == KeyEvent.VK_H){
            save("weights.txt", highScorer);
        }

        if(e.getKeyCode() == KeyEvent.VK_Z){
            FPS = 20;
        }

        if(e.getKeyCode() == KeyEvent.VK_C){
            score = 0;
            population.genomeLoosed(score);
            reset();
            active = true;
            System.out.println("Canceled!");
        }

        if(e.getKeyCode() == KeyEvent.VK_D){
            if(showDetails)
                showDetails = false;
            else
                showDetails = true;
        }

        if(e.getKeyCode() == KeyEvent.VK_W){
            if(wait)
                wait = false;
            else
                wait = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    public static Direction illegalDirection(){
        switch (direction){
            case UP:
                return Direction.DOWN;
            case DOWN:
                return Direction.UP;
            case RIGHT:
                return Direction.LEFT;
            default:
                return Direction.RIGHT;
        }
    }

    public Direction keyEventToDirection(KeyEvent e){
        switch (e.getKeyCode()){
            case KeyEvent.VK_UP:
                return Direction.UP;

            case KeyEvent.VK_DOWN:
                return Direction.DOWN;

            case KeyEvent.VK_RIGHT:
                return Direction.RIGHT;

            default:
                return Direction.LEFT;
        }
    }

    // For the AI algorithm:

    public void save(String path, Genome genome){
        try {
            Formatter fw = new Formatter(path);

            for(int i = 0; i<genome.weights.length; i++){
                System.out.println(genome.weights[i]);
                fw.format("%s", genome.weights[i] + "\r\n");
            }

            fw.close();

            System.out.println("Saved Successfully");
        }catch(Exception e) {System.out.println("Error" + e);}
    }

    public void read(String path){
        try {
            File w = new File(path);
            Scanner sw = new Scanner(w);

            for(int i = 0; i<population.getCurrentGenome().weights.length; i++){
                player.weights[i] = Double.parseDouble(sw.next());
                System.out.println(player.weights[i]);
            }

            sw.close();
            System.out.println("Uploaded Successfully");
        }catch(Exception e) {System.out.println("Error while reading" + e);}
    }
}
