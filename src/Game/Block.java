package Game;

import java.awt.*;

public class Block {

    public boolean check = false;
    public boolean check2 = false;
    private State state;
    private Rectangle rectangle;

    public Block(Rectangle rectangle){
        this.rectangle = rectangle;
        this.state = State.EMPTY;
    }

    public void updateState(State state){
        this.state = state;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public void setRectangle(Rectangle rectangle) {
        this.rectangle = rectangle;
    }

    public Color getColor(){
        if(state == State.EMPTY)
            return Color.DARK_GRAY;

        else if(state == State.BODY)
            return Color.LIGHT_GRAY;

        else if(state == State.APPLE)
            return Color.RED;

        return Color.GRAY;
    }

}
