import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.io.Serializable;

public class Line implements CustomShape, Serializable {

    private Color color;
    private Integer stroke;
    private Line2D line;

    private Point p1;
    private Point p2;

    private boolean isActive;


    public Line(Point p1, Point p2, Color color, Integer stroke){
        this.p1 = p1;
        this.p2 = p2;
        this.color = color;
        this.stroke = stroke;
    }

    public void setP2(Point p2) {
        this.p2 = p2;
    }

    @Override
    public void moveOrigin(Point point) {
        int diffx =  point.x - p1.x;
        int diffy = point.y - p1.y;
        this.p1 = point;
        this.p2.x += diffx;
        this.p2.y += diffy;
    }

    @Override
    public Point getOrigin() {
        return p1;
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
        Graphics2D g2 = (Graphics2D) g;

        int width = (int) getDistance(p1, p2);
        double theta = getAngle(p1, p2);

        AffineTransform old = g2.getTransform();
//        draw with rotated angle
        g2.rotate(Math.toRadians(theta), p1.x, p1.y);

        if (isActive) {
            g2.setColor(Color.GRAY);
            g2.setStroke(dashed);
            int x_outline = p1.x - padding;
            int y_outline = p1.y - (stroke/2 + padding);
            g2.draw3DRect(x_outline, y_outline, width + padding*2,
                    stroke + padding*2, false);
        }
//        draw "line"
        g2.setColor(color);
        g2.fill3DRect(p1.x, p1.y - stroke/2, width, stroke, false);

//        return back to old g2 settings
        g2.setTransform(old);

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
        double distFromP1 = getDistance(point, p1);
        double distFromP2 = getDistance(point, p2);
        double totalDist = distFromP1 + distFromP2;
        double length = getDistance(p1, p2);

        double buffer = stroke/2 + 2;
        if (totalDist >=  length - buffer && totalDist <= length + buffer) {
            return true;
        }

        return false;

    }

    private double getDistance(Point p1, Point p2) {
        return Math.sqrt(Math.pow(p1.x - p2.x, 2) + Math.pow(p1.y - p2.y, 2));
    }

    private double getAngle(Point p1, Point p2) {
        int x = p2.x - p1.x;
        int y = p2.y - p1.y;

        double angle = Math.toDegrees(Math.atan2(y, x));
        return angle;
    }

    @Override
    public void fillShape(Color color) {
        return;
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
