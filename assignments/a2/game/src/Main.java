public class Main {

    public static void main(String[] args) {
        int fps = Integer.valueOf(args[0]);
        int speed = Integer.valueOf(args[1]);
        GameFrame game = new GameFrame(speed, fps);
        game.show();
    }
}
