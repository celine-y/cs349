import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class ColorButton extends ButtonUI {

    private Color color;

    public ColorButton(Color color) {
        this.color = color;
        setFocusPainted(false);
        setBackground(color);
        setPreferredSize(new Dimension(50, 50));
    }

    @Override
    public void setActive(){
        this.setBorder(new LineBorder(Color.BLACK, 5));
    }

    public Color getColor() {
        return color;
    }

    @Override
    public void setNotActive() {
        this.setBorder(new LineBorder(Color.GRAY, 1));
    }
}
