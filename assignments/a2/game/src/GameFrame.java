import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class GameFrame extends JFrame implements KeyListener, MouseListener {

    private final int pWidth = 800;
    private final int pHeight = 600;

    private final int minW = 620, minH = 510;

    private int fps;
    private int speed;

    private boolean playingGame;

    private GamePanel gamePanel;

    private ToolPanel toolPanel;
    private JPanel mainArea, introPanel, settingPanel;

    private JTextField fpsField, speedField;

    private final AtomicBoolean keyPressedBefore = new AtomicBoolean(false);

    public GameFrame(int speed, int fps){
        if (speed != 0){
            this.speed = speed;
        } else {
            this.speed = 20;
        }

        if (fps != 0){
            this.fps = fps;
        } else {
            this.fps = 40;
        }

        this.setSize(new Dimension(pWidth, pHeight));
        this.addKeyListener(this);

        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                Component window = e.getComponent();
                Dimension newDim = window.getSize();
                if (window.getHeight() <= minH){
                    newDim.height = minH;
                } else if (window.getWidth() <= minW) {
                    newDim.width = minW;
                }
                window.setSize(newDim);

                if (gamePanel != null){
                    gamePanel.setWindow();
                }
            }
        });

        setDisplay();
    }

    public void startGame(){
        setFocusable(true);
        updatePoints(0);

        if (gamePanel == null){
            gamePanel = new GamePanel(this);
        }

        mainArea.remove(introPanel);
        mainArea.add(gamePanel, BorderLayout.CENTER);
        revalidate();
        repaint();

        playingGame = true;

        startTimer();
    }

    public void updatePoints(int points){
        toolPanel.setPoints(points);
    }

    private void startTimer(){
        Thread gameThread = new Thread(){
            public void run() {
                while(true){
                    if (playingGame) {
                        gamePanel.moveAll();
                    }

                    try {
                        Thread.sleep(100/speed);
                    } catch (InterruptedException ex){}
                }
            }
        };
        Thread uiThread = new Thread(){
            public void run(){
                while(true){
                    gamePanel.repaint();

                    try {
                        Thread.sleep(100/fps);
                    } catch (InterruptedException ex){}
                }
            }
        };
        uiThread.start();
        gameThread.start();
    }

    private void displayIntro(){
        introPanel = new JPanel(new BorderLayout());
        introPanel.setOpaque(false);

        JPanel infoPanel = new JPanel();
        setBoxLayoutPageAxis(infoPanel);

        JPanel welcomePanel = new JPanel(new FlowLayout());
        welcomePanel.setOpaque(false);
        JLabel welcomeLabel = new JLabel("Welcome!");
        welcomeLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 40));
        welcomePanel.add(welcomeLabel);

        JPanel namePanel = new JPanel(new FlowLayout());
        namePanel.setOpaque(false);
        JLabel nameLabel = new JLabel("Name");
        nameLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
        JTextField nameField = new JTextField(40);
        namePanel.add(nameLabel);
        namePanel.add(nameField);

        JPanel errorPanel = new JPanel(new FlowLayout());
        errorPanel.setOpaque(false);
        JLabel userError = new JLabel("Please set user name and id");
        setErrorLabel(userError);
        errorPanel.add(userError);


        JPanel userPanel = new JPanel(new FlowLayout());
        userPanel.setOpaque(false);
        JLabel userLabel = new JLabel("User Id");
        userLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
        JTextField userField = new JTextField(40);
        userPanel.add(userLabel);
        userPanel.add(userField);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setOpaque(false);
        JButton start = new JButton("Start");
        start.setFocusable(false);
        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String uid = userField.getText();

                if (!name.isEmpty() && !uid.isEmpty()){
                    toolPanel.setUserName(nameField.getText());
                    toolPanel.setUserId(userField.getText());

                    startGame();
                } else {
                    userError.setVisible(true);
                }
            }
        });
        buttonPanel.add(start);

        Component[] components = new Component[]{welcomePanel, namePanel, userPanel,
                errorPanel, buttonPanel};
        for (Component component: components){
            infoPanel.add(component);
        }

        introPanel.add(infoPanel, BorderLayout.PAGE_START);

        setInstructions();
        this.add(introPanel, BorderLayout.CENTER);
    }

    private void setInstructions(){
        JPanel centerPanel = new JPanel();
        setBoxLayoutPageAxis(centerPanel, true);
        centerPanel.setBackground(Color.PINK);

        JPanel instructTitlePanel = new JPanel(new FlowLayout());
        instructTitlePanel.setOpaque(false);
        JLabel instructionLabel = new JLabel("Instructions");
        instructionLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
        instructTitlePanel.add(instructionLabel);

        JPanel instructPanel = new JPanel(new FlowLayout());
        instructPanel.setOpaque(false);
        String message = "To start the game " + System.lineSeparator()
                + "Move the platform using the arrow keys." + System.lineSeparator()
                + "Goal of the game is to break all the boxes using the bouncing ball." + System.lineSeparator()
                + "Catch the ball using the platform!" + System.lineSeparator()
                + "TIP: speed up the platform by hitting the SPACE bar." + System.lineSeparator()
                + "Pause the game & change the speed/fps by hitting the settings icon in the top right corner.";
        JTextArea stepArea = new JTextArea(7, 30);
        stepArea.setBackground(Color.PINK);
        stepArea.setText(message);
        stepArea.setLineWrap(true);
        stepArea.setEditable(false);
        instructPanel.add(stepArea);

        Component[] components = new Component[]{instructTitlePanel, instructPanel};
        for (Component component:components) {
            centerPanel.add(component);
        }

        introPanel.add(centerPanel, BorderLayout.PAGE_END);
    }

    private void setErrorLabel(JLabel jLabel){
        jLabel.setVisible(false);
        jLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
        jLabel.setForeground(Color.RED);
    }

    private void setBoxLayoutPageAxis(JPanel jPanel) {
        setBoxLayoutPageAxis(jPanel, false);
    }

    private void setBoxLayoutPageAxis(JPanel jPanel, boolean isOpaque){
        jPanel.setOpaque(isOpaque);
        jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.PAGE_AXIS));
    }

    private void setDisplay(){
        mainArea = new JPanel(new BorderLayout());
        mainArea.setBackground(Color.WHITE);

        if (toolPanel == null) {
            toolPanel = new ToolPanel(this);
        }

        if (introPanel == null){
            displayIntro();
        }
        mainArea.add(toolPanel, BorderLayout.PAGE_START);
        mainArea.add(introPanel, BorderLayout.CENTER);

        this.add(mainArea);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
            if (keyCode == KeyEvent.VK_LEFT) {
                gamePanel.movePlatform(Direction.LEFT);
            } else if (keyCode == KeyEvent.VK_RIGHT) {
                gamePanel.movePlatform(Direction.RIGHT);
            } else if (keyCode == KeyEvent.VK_SPACE){
                if (keyPressedBefore.compareAndSet(false, true)) {
                    gamePanel.platformBoosted(true);
                }
            }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
            if (keyCode == KeyEvent.VK_LEFT ||
                    keyCode == KeyEvent.VK_RIGHT) {
                gamePanel.stopPlatform();
            } else if (keyCode == KeyEvent.VK_SPACE){
                if (keyPressedBefore.compareAndSet(true, false)) {
                    gamePanel.platformBoosted(false);
                }
            } else if (keyCode == KeyEvent.VK_ESCAPE){
                this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
            }

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        playingGame = false;
        showSettings();
    }

    private void showSettings(){
        if (settingPanel == null){
            settingPanel = new JPanel();
            settingPanel.setOpaque(false);

            JPanel inputPanel = new JPanel();
            inputPanel.setOpaque(false);
            inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.PAGE_AXIS));

            JPanel messagePanel = new JPanel(new FlowLayout());
            messagePanel.setOpaque(false);
            JLabel messageLabel = new JLabel("Settings");
            messageLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 40));
            messagePanel.add(messageLabel);

            JPanel fpsPanel = new JPanel(new FlowLayout());
            fpsPanel.setOpaque(false);
            JLabel fpsLabel = new JLabel("FPS");
            fpsLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
            fpsField = new JTextField(String.valueOf(fps), 40);
            fpsPanel.add(fpsLabel);
            fpsPanel.add(fpsField);

            JPanel speedPanel = new JPanel(new FlowLayout());
            speedPanel.setOpaque(false);
            JLabel speedLabel = new JLabel("Speed");
            speedLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
            speedField = new JTextField(String.valueOf(speed), 40);
            speedPanel.add(speedLabel);
            speedPanel.add(speedField);

            Component[] components = new Component[]{messagePanel, fpsPanel, speedPanel};
            for (Component component: components){
                inputPanel.add(component);
            }

            settingPanel.add(inputPanel);
        }

        int result = JOptionPane.showConfirmDialog(null, settingPanel,
                "Settings", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String fpsInput = fpsField.getText();
            if (!fpsInput.isEmpty()){
                fps = Integer.valueOf(fpsInput);
            }
            String speedInput = speedField.getText();
            if (!speedInput.isEmpty()){
                speed = Integer.valueOf(speedInput);
            }
            playingGame = true;
        } else {
            playingGame = true;
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
