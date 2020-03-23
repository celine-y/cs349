import java.awt.*;
import java.io.Serializable;

public class Circle implements CustomShape, Serializable {

    private Point origin;
    private double radius;
    private Color color, fillColor;
    private Integer stroke;

    private boolean isActive;

    public Circle(Point origin, float radius, Color color, Integer stroke) {
        this.origin = origin;
        this.radius = radius;
        this.color = color;
        this.stroke = stroke;
    }

    public void setRadius(Point newpoint) {
        int x = newpoint.x - origin.x;
        int y = newpoint.y - origin.y;
        this.radius = Math.sqrt(x*x+y*y);
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
        return color;
    }

    @Override
    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public void paintComponent(Graphics g) {
        int diameter = (int) (this.radius * 2);
        int x = (int) (origin.x - this.radius);
        int y = (int) (origin.y - this.radius);

        Graphics2D g2 = (Graphics2D) g;
        if (fillColor != null) {
            g2.setColor(fillColor);
            g2.fillOval(x, y, diameter, diameter);
        }
        if (isActive){
            g2.setColor(Color.GRAY);
            g2.setStroke(dashed);
            g2.draw3DRect(x - stroke/2, y - stroke/2,
                    diameter + stroke, diameter + stroke, false);
        }
        g2.setStroke(new BasicStroke(stroke));
        g2.setColor(this.color);
        g2.drawOval(x, y, diameter, diameter);
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
        int x_diff = point.x - origin.x;
        int y_diff = point.y - origin.y;

        double distance = Math.sqrt((x_diff*x_diff)+(y_diff*y_diff));
        if (distance < radius + stroke/2) {
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
