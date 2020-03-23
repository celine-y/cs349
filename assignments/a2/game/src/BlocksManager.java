import java.awt.*;
import java.util.ArrayList;

public class BlocksManager {

    private ArrayList<Block> blocks;
    private int areaW, areaH;

    private int row, col;

    public BlocksManager(){
        row = 1; col = 1;
        blocks = new ArrayList<>();
    }

    public void setWindow(int width, int height){
        this.areaH = height/2;
        this.areaW = width;

        if (blocks != null){
            sizeBlocks();
        }
    }

    public void setBlocks(int windowW, int windowH, int row, int col){
        areaW = windowW;
        areaH = windowH/2;
        this.row = row;
        this.col = col;

        sizeBlocks();
    }

    private void sizeBlocks(){
        blocks = new ArrayList<>();

        int spaceH = areaW/col;
        int spaceV = areaH/row;

        for (int i = 0; i < row; i++){
            for (int j = 0; j < col; j++){
                blocks.add(new Block(spaceH*j, spaceH*(j+1),
                        spaceV*i, spaceV*(i+1)));
            }
        }
    }

    public void paintBlocks(Graphics2D g2){

        for (Block block:blocks) {
            if (!block.isDestroyed()){
                g2.setColor(Color.WHITE);
                g2.fillRect(block.getLeft(), block.getTop(),
                        block.getWidth(), block.getHeight());

                g2.setColor(new Color(33, 171, 205));
                g2.setStroke(new BasicStroke(2));
                g2.drawRect(block.getLeft(), block.getTop(),
                        block.getWidth(), block.getHeight());
            }

        }
    }

    public boolean checkCollision(Ball ball){
        if (blocks.size() > 0) {
            for (Block block : blocks) {
                if (!block.isDestroyed()) {
                    Rectangle blockRect = block.getRectangle();
                    if ((blockRect).intersects(ball.getRect())) {

                        if (ball.getTop() + 1 >= block.getBottom()) {
                            ball.setDy(Direction.DOWN);
                        } else if (ball.getBottom() - 1 <= block.getTop()) {
                            ball.setDy(Direction.UP);
                        }

                        if (ball.getLeft() + 1 >= block.getRight()) {
                            ball.setDx(Direction.RIGHT);
                        } else if (ball.getRight() - 1 <= block.getLeft()) {
                            ball.setDx(Direction.LEFT);
                        }

                        block.setDestroyed(true);
                        return true;
                    }
                }
            }
        }

        return false;
    }

}
