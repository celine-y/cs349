import java.awt.*;

public interface CustomShape {

    float dash1[] = { 10.0f };
    BasicStroke dashed = new BasicStroke(1.0f,
            BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dash1, 0.0f);
    int padding = 2;

    void moveOrigin(Point origin);
    Point getOrigin();

    Color getColor();
    void setColor(Color color);

    void paintComponent(Graphics g);

    void setStroke(Integer thickness);
    Integer getStroke();

    Boolean clickedOnShape(Point point);

    void fillShape(Color color);
    void setActive();
    void setInactive();
}
