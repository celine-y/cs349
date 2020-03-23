import javax.swing.*;
import java.awt.*;

public class StrokeButton extends ButtonUI {

    public static final Integer[] sizes = new Integer[]{5, 20, 40, 50};
    private Integer size;

    public StrokeButton(Integer thickness, Color color) {
        size = thickness;

        setOpaque(false);
        setContentAreaFilled(false);
        setFocusPainted(false);
        setIcon(new LineIcon(thickness, color));
    }

    public StrokeButton(Integer thickness){
        this(thickness, Color.BLACK);
    }

    public Integer getThickness() {
        return size;
    }

    @Override
    void setNotActive() {
        setIcon(new LineIcon(size, Color.BLACK));
    }

    public void setActive(){
        setIcon(new LineIcon(size, Color.RED));
    }
}

class LineIcon implements Icon {

    private Integer stroke;
    private Color color;

    public LineIcon(Integer stroke, Color color) {
        this.stroke = stroke;
        this.color = color;
    }

    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(color);
        g2.setStroke(new BasicStroke(stroke));
        g2.drawLine(0, y, getIconWidth(), y);

    }

    @Override
    public int getIconWidth() {
        return 100;
    }

    @Override
    public int getIconHeight() {
        return 50;
    }
}
