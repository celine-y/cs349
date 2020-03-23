import java.awt.*;

public class Block {

    private final int strokeSize = 10;
    private final int space = 5;

    private int x, y;
    private int width, height;
    private boolean destroyed;

    public Block(int startx, int endx, int starty, int endy){
        int x = startx + space;
        int width = (endx - space) - x;

        int y = starty + space;
        int height = (endy - space) - y;
        initBrick(x, y, width, height);
    }

    private void initBrick(int x, int y, int width, int height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        destroyed = false;
    }

    public Rectangle getRectangle(){
        return new Rectangle(x, y, width, height);
    }

    public int getLeft() {
        return x;
    }

    public int getTop() {
        return y;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public int getRight() {
        return getLeft() + getWidth();
    }

    public int getBottom() {
        return getTop() + getHeight();
    }

    public boolean isDestroyed() {
        return destroyed;
    }

    public void setDestroyed(boolean destroyed) {
        this.destroyed = destroyed;
    }
}
