import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class ControlButton extends ButtonUI {

    private static final int dim = 50;
    private static final Dimension dimension = new Dimension(dim, dim);

    public enum ControlAction{
        SELECT,
        ERASE,
        LINE,
        CIRCLE,
        RECTANGLE,
        FILL
    }

//    private String name;
    private ControlAction action;

    public ControlButton (ControlAction action){
        this.action = action;

        setBackground(Color.WHITE);
        setFocusPainted(false);
        setPreferredSize(dimension);

        String location = "/";
        try {
            if (action == ControlAction.SELECT) {
                location = location + "select.png";
            } else if (action == ControlAction.ERASE) {
                location = location + "erase.png";
            } else if (action == ControlAction.FILL) {
                location = location + "fill.png";
            } else if (action == ControlAction.RECTANGLE) {
                location = location + "rectangle.png";
            } else if (action == ControlAction.CIRCLE) {
                location = location + "circle.png";
            } else if (action == ControlAction.LINE) {
                location = location + "line.png";
            } else {
                location = location + "default.png";
            }

            Image image = ImageIO.read(getClass().getResource(location))
                    .getScaledInstance(-1, dim, Image.SCALE_SMOOTH);
            this.setIcon(new ImageIcon(image));
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void setActive(){
        this.setBorder(new LineBorder(Color.BLACK, 5));
    }

    @Override
    public void setNotActive() {
        this.setBorder(new LineBorder(Color.GRAY, 1));
    }

    public ControlAction getControlAction() {
        return action;
    }
}
