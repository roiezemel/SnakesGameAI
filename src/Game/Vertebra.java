package Game;

public class Vertebra {

    private int x;
    private int y;
    private Vertebra nextVertebra;

    public Vertebra(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Vertebra(int x, int y, Vertebra nextVertebra) {
        this.x = x;
        this.y = y;
        this.nextVertebra = nextVertebra;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Vertebra getNextVertebra() {
        return nextVertebra;
    }

    public void setNextVertebra(Vertebra nextVertebra) {
        this.nextVertebra = nextVertebra;
    }

    public void increaseX(){
        x++;
    }

    public void decreaseX(){
        x--;
    }

    public void increaseY(){
        y++;
    }

    public void decreaseY(){
        y--;
    }

    public void moveToNextPosition(){
        if(nextVertebra != null) {
            this.x = nextVertebra.x;
            this.y = nextVertebra.y;
        }
    }

}
