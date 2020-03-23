import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {

    private boolean firstStart;
    private boolean ballLaunched;

    private int level;
    private Platform platform;
    private Ball ball;

    private GameFrame gameFrame;
    private int points;

    private BlocksManager blocksManager;


    public GamePanel(GameFrame gameFrame){
        level = 1;
        firstStart = true;
        setFocusable(false);

        setBackground(Color.WHITE);

        setLayout(new GridLayout());

        platform = new Platform();
        ball = new Ball();
        blocksManager = new BlocksManager();
        this.gameFrame = gameFrame;
    }

    public void setWindow(){
        ball.setWindow(getWidth(), getHeight());
        platform.setWindow(getWidth(), getHeight());
        blocksManager.setWindow(getWidth(), getHeight());

        resetGame();
    }

    public void resetGame(){
        gameFrame.updatePoints(points = 0);
        ballLaunched = false;
        platform.setWindow(getWidth(), getHeight());
        platform.resetState();

        ball.setWindow(getWidth(), platform.getTop());
        ball.resetState();

        int num_rows = 5;
        int num_cols = 6;
        if (level == 1){
            num_rows = 5;
            num_cols = 6;
        }

        blocksManager.setBlocks(getWidth(), getHeight(), num_rows, num_cols);
    }

    private void paintGamePanel(Graphics2D g2){
        if (firstStart){
            resetGame();
            firstStart = false;
        }

        paintPlatform(g2);
        paintBall(g2);
        paintBlocks(g2);
    }

    public void moveAll(){
        platform.move();
        ball.move();
        checkCollision();
    }

    public void platformBoosted(boolean bool){
        if (bool){
            platform.boostSpeed();
        } else {
            platform.lowerSpeed();
        }
    }

    public void movePlatform(Direction direction){
        if (!ballLaunched){
            launchBall(direction);
        }
        platform.setDx(direction);
    }

    public void stopPlatform(){
        platform.setDx(Direction.NONE);
    }

    public void launchBall(Direction direction){
        ballLaunched = true;
        ball.setDx(direction);
        ball.setDy(Direction.UP);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();

        try {
            Image image = ImageIO.read(getClass().getResource("paper.png"))
                    .getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH);
            g2.drawImage(image, 0 ,0, null);
        } catch (Exception e){
            e.printStackTrace();
        }

        paintGamePanel(g2);
        g2.dispose();
    }

    private void paintPlatform(Graphics2D g2){
        g2.setStroke(new BasicStroke(10));
        g2.setColor(Color.PINK);
        g2.fill3DRect(platform.getLeft(), platform.getTop(), platform.getWidth(), platform.getHeight(), false);
    }

    private void paintBall(Graphics2D g2){
        g2.setColor(Color.PINK);
        g2.fillOval(ball.getLeft(), ball.getTop(), ball.getDiameter(), ball.getDiameter());
    }

    private void paintBlocks(Graphics2D g2){
        blocksManager.paintBlocks(g2);
    }

    private void checkCollision(){
        if (ball.getTop() > platform.getTop()){
            resetGame();
            return;
        }

        if (blocksManager.checkCollision(ball)){
            addPoints();
        }

        if (platform.hitsTop(ball.getRect())){
            ball.setDy(Direction.UP);
            addPoints();
        }
    }

    private void addPoints(){
        gameFrame.updatePoints(points += 1);
    }
}
