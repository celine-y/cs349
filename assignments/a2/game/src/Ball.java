import java.awt.*;

public class Ball implements Element {

    private int screenW, screenH;

    private final int radius = 20;

    private int x, y;
    private int dx, dy;

    public Ball(){
        screenW = 0;
        screenH = 0;
        dx = 0; dy = 0;
    }

    public void setDy(Direction direction){
        if (direction == Direction.UP){
            dy = -1;
        } else if (direction == Direction.DOWN){
            dy = 1;
        }
    }

    @Override
    public void setDx(Direction direction){
        if (direction == Direction.LEFT){
            dx = -1;
        } else if (direction == Direction.RIGHT){
            dx = 1;
        }
    }

    @Override
    public Rectangle getRect() {
        return new Rectangle(getLeft(), getTop(), getDiameter(), getDiameter());
    }


    @Override
    public void resetState() {
        y = screenH - getDiameter();
        x = screenW/2 - radius;
        dx= 0; dy = 0;
    }

    @Override
    public void setWindow(int width, int height) {
        screenW = width;
        screenH = height;

         resetState();
    }

    public void move(){
        x += dx;

        if (x < 0){
            setDx(Direction.RIGHT);
        } else if (getRight() > screenW){
            setDx(Direction.LEFT);
        }

        y += dy;

        if (y < 0){
            setDy(Direction.DOWN);
        }
    }

    public int getDiameter(){
        return 2*radius;
    }

    public int getRight(){
        return x + getDiameter();
    }

    public int getLeft(){
        return x;
    }

    public int getTop(){
        return y;
    }

    public int getBottom(){
        return y + getDiameter();
    }

    public int getCenterX(){
        return getLeft() + radius;
    }

    public int getCenterY(){
        return getTop() + radius;
    }
}
