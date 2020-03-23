import java.awt.*;

public interface Element {

     void resetState();
     void setWindow(int width, int height);

     void setDx(Direction direction);

     Rectangle getRect();
}
