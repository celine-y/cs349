import java.awt.*;
import java.io.Serializable;

public class Rectangle implements CustomShape, Serializable {

    private Point origin;
    private int length, width;
    private Color color, fillColor;
    private Integer stroke;
    private boolean isActive;


    public Rectangle(Point point, int length, int width, Color color, Integer stroke) {
        this.origin = point;
        this.length = length;
        this.width = width;
        this.color = color;
        this.stroke = stroke;
    }

    public void setWidthHeight(Point point){
        this.length = point.y - origin.y;
        this.width = point.x - origin.x;
    }

    @Override
    public void moveOrigin(Point origin) {
        this.origin = origin;
    }

    @Override
    public Point getOrigin() {
        return origin;
    }

    @Override
    public Color getColor() {
        return this.color;
    }

    @Override
    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public void paintComponent(Graphics g) {

        Graphics2D g2 = (Graphics2D) g;
        if (fillColor != null) {
            g2.setColor(fillColor);
            g2.fill3DRect(origin.x, origin.y, width, length, true);
        }
        if (isActive) {
            g2.setColor(Color.GRAY);
            g2.setStroke(dashed);
            int x_outline = origin.x - (stroke/2 + padding);
            int y_outline = origin.y - (stroke/2 + padding);
            g2.draw3DRect(x_outline, y_outline, width + stroke + padding*2,
                    length + stroke + padding*2, false);
        }
        g2.setStroke(new BasicStroke(stroke));
        g2.setColor(color);
        g2.drawRect(origin.x, origin.y, width, length);
    }

    @Override
    public void setStroke(Integer thickness) {
        stroke = thickness;
    }

    @Override
    public Integer getStroke() {
        return stroke;
    }

    @Override
    public Boolean clickedOnShape(Point point) {
        int x_left = origin.x - stroke/2;
        int x_right = origin.x + width + stroke/2;

        int y_top = origin.y - stroke/2;
        int y_bottom = origin.y + length + stroke/2;

        if (x_left < point.x && x_right > point.x
                && y_top < point.y && y_bottom > point.y){
            return true;
        }
        return false;
    }

    @Override
    public void fillShape(Color color) {
        fillColor = color;
    }

    @Override
    public void setActive() {
        isActive = true;
    }

    @Override
    public void setInactive() {
        isActive = false;
    }
}
