import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

public class Canvas extends JPanel implements MouseListener, MouseMotionListener {

    private final int jWidth = 1100;
    private final int jLen = 800;

    private ControlButton.ControlAction controlAction;
    private Color color;
    private Integer stroke;
    private MainPaint mainPaint;

    private ArrayList<CustomShape> customShapes;
    private CustomShape activeCustomShape, drawingCustomShape;

    private Point diffOrigin;

    public Canvas(){
        this.customShapes = new ArrayList<>();
        this.setBackground(Color.WHITE);
        this.setPreferredSize(new Dimension(jWidth, jLen));
        addMouseListener(this);
        addMouseMotionListener(this);
        setFocusable(true);
    }

    public void clearShapes() {
        customShapes = new ArrayList<>();
        repaint();
    }

    public void saveShapes() {
        CRUDFileShape crudFileShape = new CRUDFileShape(this);
        crudFileShape.saveShapes(customShapes);
        repaint();
    }

    public void loadShapes() {
        CRUDFileShape crudFileShape = new CRUDFileShape(this);
        ArrayList<CustomShape> newCustomShapes = crudFileShape.loadShapes();

        if (newCustomShapes != null) {
            customShapes = newCustomShapes;
            repaint();
        }
    }

    public int getStroke(){
        return stroke;
    }

    public void setStroke(Integer stroke) {
        this.stroke = stroke;
    }

    public void setColor(Color color){
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public void setMainPaint(MainPaint mainPaint) {
        this.mainPaint = mainPaint;
    }

    public void setControlAction(ControlButton.ControlAction action){
        controlAction = action;
//        TODO: remove active shape
    }

    private Point setDistanceFromOrigin(Point clickedPoint, CustomShape customShape){
        Point shapeOrigin = customShape.getOrigin();
        int x = clickedPoint.x - shapeOrigin.x;
        int y = clickedPoint.y - shapeOrigin.y;

        return new Point(x, y);
    }

    private Point getOriginFromDiff(Point draggedPoint){
        int x = draggedPoint.x - diffOrigin.x;
        int y = draggedPoint.y - diffOrigin.y;

        return new Point(x, y);
    }

    private void selectIfShape(Point point) {
        deselectShapes();
        for (int i = customShapes.size() - 1; i >= 0; i--) {
            CustomShape customShape = customShapes.get(i);
            if (customShape.clickedOnShape(point)) {
                activeCustomShape = customShape;
                customShape.setActive();
                color = customShape.getColor();
                mainPaint.setColor(color);
                stroke = customShape.getStroke();
                mainPaint.setStroke(stroke);
                return;
            }
        }
    }

    public boolean hasActiveShape() {
        return activeCustomShape != null;
    }

    public void updateActiveShape() {
        activeCustomShape.setStroke(stroke);
        activeCustomShape.setColor(color);
        repaint();
    }

    public void deselectShapes() {
        activeCustomShape = null;
        for (CustomShape customShape : customShapes
             ) {
            customShape.setInactive();
        }
        repaint();
    }

    private void fillIfShape(Point point) {
        for (int i = customShapes.size() - 1; i >= 0; i--) {
            CustomShape customShape = customShapes.get(i);
            if (customShape.clickedOnShape(point)) {
                customShape.fillShape(color);
                return;
            }
        }

    }

    private void eraseIfShape(Point point){
        for (int i = customShapes.size() - 1; i >= 0; i--) {
            CustomShape customShape = customShapes.get(i);
            if (customShape.clickedOnShape(point)) {
                customShapes.remove(customShape);
                return;
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        for (CustomShape customShape : customShapes) {
            customShape.paintComponent(g2);
        }

        if (drawingCustomShape != null){
            drawingCustomShape.paintComponent(g2);
        }

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (controlAction == ControlButton.ControlAction.ERASE) {
            eraseIfShape(e.getPoint());
        } else if (controlAction  == ControlButton.ControlAction.FILL) {
            fillIfShape(e.getPoint());
        } else if (controlAction == ControlButton.ControlAction.SELECT) {
            selectIfShape(e.getPoint());
        }
        repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        diffOrigin = null;
        if (controlAction == ControlButton.ControlAction.CIRCLE ||
                controlAction == ControlButton.ControlAction.RECTANGLE ||
                controlAction == ControlButton.ControlAction.LINE
        ){
            if (drawingCustomShape != null) {
                customShapes.add(drawingCustomShape);
                drawingCustomShape = null;
            }
        }
        repaint();
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (controlAction == ControlButton.ControlAction.CIRCLE){
            if (drawingCustomShape == null){
                drawingCustomShape = new Circle(e.getPoint(), 0, color, stroke);
            } else {
                ((Circle) drawingCustomShape).setRadius(e.getPoint());
            }
        } else if (controlAction == ControlButton.ControlAction.RECTANGLE) {
            if (drawingCustomShape == null){
                drawingCustomShape = new Rectangle(e.getPoint(), 0, 0, color, stroke);
            } else {
                ((Rectangle) drawingCustomShape).setWidthHeight(e.getPoint());
            }
        } else if (controlAction == ControlButton.ControlAction.LINE) {
            if (drawingCustomShape == null){
                drawingCustomShape = new Line(e.getPoint(), e.getPoint(), color, stroke);
            } else {
                ((Line) drawingCustomShape).setP2(e.getPoint());
            }
        } else if (controlAction == ControlButton.ControlAction.SELECT){
            if (activeCustomShape != null) {
                if (activeCustomShape.clickedOnShape(e.getPoint())) {
                    if (diffOrigin == null) {
                        diffOrigin = setDistanceFromOrigin(e.getPoint(), activeCustomShape);
                    }
                    activeCustomShape.moveOrigin(getOriginFromDiff(e.getPoint()));
                }
            }
        }
        repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
