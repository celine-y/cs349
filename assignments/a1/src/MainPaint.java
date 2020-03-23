import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class MainPaint extends JFrame implements ActionListener {

    private JSplitPane splitPane;
    private JPanel toolPanel, controlPanel, colorPanel, strokePanel;
    private Canvas canvasPanel;

    private ToolBar toolBar;

    private ArrayList<ButtonUI> controlButtons, colorButtons, strokeButtons;

    private static final String title = "My MainPaint";
    private static final int lCan = 700;
    private static final int wCan = 1200;
    private static final int minW = 500;
    private static final  int minH = 465;
    private static final int dividerLoc = 100;

    private boolean isDisplayCombo;
    private JComboBox comboStroke;

    public MainPaint() {
        this.setMinimumSize(new Dimension(minW, minH));
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                Component jframe = e.getComponent();
                Dimension minD= jframe.getSize();
                if (jframe.getHeight() <= minH){
                    minD.height = minH;
                } else if (jframe.getWidth() <= minW) {
                    minD.width = minW;
                }
                jframe.setSize(minD);
            }
        });
        this.setSize(new Dimension(wCan, lCan));
        this.setTitle(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setWindow();
    }

    public void resetCanvas() {
        if (canvasPanel != null) {
            canvasPanel.clearShapes();
            setDefaultActive();
        }
    }

    public void saveCanvas() {
        if (canvasPanel != null) {
            canvasPanel.saveShapes();
        }
    }

    public void loadCanvas() {
        if (canvasPanel != null) {
            canvasPanel.loadShapes();
        }
    }

    public void setColor(Color color) {
        setNotActive(colorButtons);
        int i;
        for (i = 0; i < colorButtons.size() - 1; i++) {
            ColorButton colorButton = (ColorButton) colorButtons.get(i);
            if (colorButton.getColor() == color) {
                colorButton.setActive();
                return;
            }
        }
        ColorSelectButton colorSelectButton = (ColorSelectButton) colorButtons.get(i);
        colorSelectButton.setActive();
        colorSelectButton.setColor(color);
    }

    public void setStroke(Integer thickness) {
        if (isDisplayCombo){
            comboStroke.setSelectedItem(thickness);
        } else {
            setNotActive(strokeButtons);
            for (int i = 0; i < strokeButtons.size() - 1; i++) {
                StrokeButton strokeButton = (StrokeButton) strokeButtons.get(i);
                if (strokeButton.getThickness() == thickness) {
                    strokeButton.setActive();
                    return;
                }
            }
        }

    }

    private void setWindow() {
//        Set ESC
        String escape = "ESC";
        JComponent rootPane = this.getRootPane();
        KeyStroke keyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0);
        rootPane.getActionMap().put(escape, escAction());
        rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(keyStroke, escape);

//        Toolbar
        toolBar = new ToolBar();
        this.setJMenuBar(toolBar);
        toolBar.setMainPaint(this);

//        Split Pane
        setSplitPane();
        getContentPane().add(splitPane);
    }

    private void setSplitPane(){
        if (toolPanel == null){
            setToolPanel();
        }

        if (canvasPanel == null){
            setCanvasPanel();
        }

        if (splitPane == null) {
            splitPane = new JSplitPane();
            splitPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
            splitPane.setLeftComponent(toolPanel);


            JPanel jPanel = new JPanel();
            jPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
            jPanel.add(canvasPanel);

            JScrollPane scrollPane = new JScrollPane(jPanel);
            scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            splitPane.setRightComponent(scrollPane);

//            splitPane.setRightComponent(jPanel);
            getContentPane().add(splitPane);
        }

        setDefaultActive();
    }

    private void setCanvasPanel() {
        canvasPanel = new Canvas();
        canvasPanel.setMainPaint(this);
    }

    private void setToolPanel(){
//        Main Tool Panel
        toolPanel = new JPanel();
        toolPanel.setLayout(new BoxLayout(toolPanel, BoxLayout.Y_AXIS));

        setControlPanel();
        setColorPanel();
        setThicknessPanel();

        toolPanel.addComponentListener(new ComponentListener() {
            @Override
            public void componentResized(ComponentEvent e) {
                Dimension dim = e.getComponent().getSize();
                if (dim.getHeight() <= 600){
                    if (!isDisplayCombo) {
                        comboStroke = new JComboBox(StrokeButton.sizes);
                        comboStroke.setSelectedItem(canvasPanel.getStroke());
                        comboStroke.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                JComboBox cb = (JComboBox) e.getSource();
                                int stroke = (int) cb.getSelectedItem();
                                canvasPanel.setStroke(stroke);
                                cb.updateUI();
//                                Update Active Shape
                                if (canvasPanel.hasActiveShape()) {
                                    canvasPanel.updateActiveShape();
                                }
                            }
                        });
                        for (ButtonUI strokeButton : strokeButtons) {
                            strokePanel.remove(strokeButton);
                        }
                        strokePanel.add(comboStroke);
                        isDisplayCombo = true;
                    }
                } else {
                    if (isDisplayCombo) {
                        strokePanel.remove(0);
                        for (ButtonUI button : strokeButtons) {
                            StrokeButton strokeButton = (StrokeButton) button;
                            if (strokeButton.getThickness() == canvasPanel.getStroke()){
                                setThicknessButton(strokeButton);
                            }
                            strokePanel.add(button);
                        }
                        isDisplayCombo = false;
                    }
                }
            }

            @Override
            public void componentMoved(ComponentEvent e) {

            }

            @Override
            public void componentShown(ComponentEvent e) {

            }

            @Override
            public void componentHidden(ComponentEvent e) {

            }
        });
    }

    private void setControlPanel() {
        controlPanel = new JPanel();
        controlPanel.setLayout(new GridLayout(3, 2));
        toolPanel.add(controlPanel);

        controlButtons = new ArrayList<>();
        controlButtons.add(new ControlButton(ControlButton.ControlAction.SELECT));
        controlButtons.add(new ControlButton(ControlButton.ControlAction.ERASE));
        controlButtons.add(new ControlButton(ControlButton.ControlAction.LINE));
        controlButtons.add(new ControlButton(ControlButton.ControlAction.CIRCLE));
        controlButtons.add(new ControlButton(ControlButton.ControlAction.RECTANGLE));
        controlButtons.add(new ControlButton(ControlButton.ControlAction.FILL));
        setButtonUIButtons(controlPanel, controlButtons);
    }

    private void setColorPanel() {
        colorPanel = new JPanel();
        colorPanel.setLayout(new GridLayout(4, 2));
        toolPanel.add(colorPanel);
        colorButtons = new ArrayList<>();

        Color[] colors = new Color[]{Color.BLUE, Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN, Color.PINK, Color.BLACK};
        for (Color color: colors
             ) {
            colorButtons.add(new ColorButton(color));
        }
        ColorSelectButton colorSelectButton = new ColorSelectButton();
        colorButtons.add(colorSelectButton);

        setButtonUIButtons(colorPanel, colorButtons);
    }

    private void setThicknessPanel() {
        strokePanel = new JPanel();
        strokePanel.setLayout(new GridLayout(4, 1));

        strokePanel.setBackground(Color.WHITE);
        toolPanel.add(strokePanel);

        strokeButtons = new ArrayList<>();
        Integer[] sizes = StrokeButton.sizes;
        for (Integer size: sizes
             ) {
            strokeButtons.add(new StrokeButton(size));
        }
        isDisplayCombo = false;
        setButtonUIButtons(strokePanel, strokeButtons);
    }

    private void setButtonUIButtons(JPanel panel, ArrayList<ButtonUI> buttons){
        for (ButtonUI btn: buttons) {
            btn.addActionListener(this);
            panel.add(btn);
            btn.setVisible(true);
        }
    }

    private void setDefaultActive(){
        setNotActive(controlButtons);
        ControlButton defaultControlButton = (ControlButton) controlButtons.get(0);
        defaultControlButton.setActive();
        canvasPanel.setControlAction(defaultControlButton.getControlAction());

        setNotActive(colorButtons);
        ColorButton defaultColorButton = (ColorButton) colorButtons.get(0);
        defaultColorButton.setActive();
        canvasPanel.setColor(defaultColorButton.getColor());

        setNotActive(strokeButtons);
        StrokeButton defaultThicknessButton = (StrokeButton) strokeButtons.get(0);
        defaultThicknessButton.setActive();
        canvasPanel.setStroke(defaultThicknessButton.getThickness());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object clickedButton = e.getSource();

        if (clickedButton.getClass() == ControlButton.class) {
            setNotActive(controlButtons);

            ControlButton controlButton = (ControlButton) clickedButton;
            controlButton.setActive();
            canvasPanel.setControlAction(controlButton.getControlAction());
            canvasPanel.deselectShapes();
        } else if (clickedButton.getClass() == ColorButton.class) {
            setNotActive(colorButtons);

            ColorButton colorButton = (ColorButton) clickedButton;
            colorButton.setActive();
            canvasPanel.setColor (colorButton.getColor());

        } else if (clickedButton.getClass() == StrokeButton.class) {
            setThicknessButton(clickedButton);
        } else if(clickedButton.getClass() == ColorSelectButton.class) {
            setNotActive(colorButtons);

            ColorSelectButton colorSelectButton = (ColorSelectButton) clickedButton;
            Color newColor = JColorChooser.showDialog(null, "Choose a color", canvasPanel.getColor());
            canvasPanel.setColor(newColor);
            colorSelectButton.setColor(newColor);
            colorSelectButton.setActive();
        }

        if (canvasPanel.hasActiveShape()) {
            canvasPanel.updateActiveShape();
        }
    }

    private void setThicknessButton(Object clickedButton){
        setNotActive(strokeButtons);

        StrokeButton thicknessButton = (StrokeButton) clickedButton;
        thicknessButton.setActive();
        canvasPanel.setStroke(thicknessButton.getThickness());
    }

    private void setNotActive(ArrayList<ButtonUI> buttons){
        for (ButtonUI button: buttons
             ) {
            button.setNotActive();
        }
        repaint();
    }

    private Action escAction() {
        Action pressEsc = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                canvasPanel.deselectShapes();
            }
        };
        return pressEsc;
    }
}
