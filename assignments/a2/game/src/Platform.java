import java.awt.*;

enum Direction {
    LEFT,
    RIGHT,
    UP,
    DOWN,
    NONE
}

public class Platform implements Element{


    private int screenW, screenH;

    private final int width = 150;
    private final int height = 20;

    private int dx;
    private int x;
    private int y;

    public Platform(){
        screenW = 0;
        screenH = 0;
        dx= 0;
    }

    @Override
    public void resetState() {
        x = screenW/2 - getWidth()/2;
        y = screenH - getHeight();
        dx = 0;
    }

    @Override
    public void setWindow(int width, int height) {
        screenW = width;
        screenH = height;

        resetState();
    }

    public int getWidth(){
        return width;
    }

    public void boostSpeed(){
        dx *= 2;
    }

    public void lowerSpeed(){
        dx /= 2;
    }

    @Override
    public void setDx(Direction direction) {
        if (direction == Direction.RIGHT){
            dx = 1;
        } else if (direction == Direction.LEFT) {
            dx = -1;
        } else {
            dx = 0;
        }
    }

    @Override
    public Rectangle getRect() {
        return new Rectangle(getLeft(), getTop(), getWidth(), getHeight());
    }

    public boolean hitsTop(Rectangle rectangle){
        if (getRect().intersects(rectangle)){
            if (rectangle.getY() + rectangle.getHeight() -1 <= getTop()){
                return true;
            }
        }

        return false;
    }

    public boolean move(){
        x += dx;

        if (x <= 0) {
            x = 0;
            return false;
        } else if (getRight() >= screenW){
            x =  screenW - getWidth();
            return false;
        }
        return true;
    }

    public int getHeight() {
        return height;
    }

    public int getRight(){
        return x + getWidth();
    }

    public int getLeft(){
        return x;
    }

    public int getTop(){
        return y;
    }
}
