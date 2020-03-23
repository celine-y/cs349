import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class ColorSelectButton extends ButtonUI {

    public ColorSelectButton() {
        setFocusPainted(false);
        setPreferredSize(new Dimension(50, 50));
        try {
            Image image = ImageIO.read(getClass().getResource("/color_picker.png"))
                    .getScaledInstance(-1, 50, Image.SCALE_SMOOTH);
            setIcon(new ImageIcon(image));
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void setColor(Color color) {
        this.setBackground(color);
    }

    @Override
    void setNotActive() {
        this.setBorder(new LineBorder(Color.GRAY, 1));
        this.setBackground(null);
    }

    @Override
    void setActive() {
        this.setBorder(new LineBorder(Color.BLACK, 5));
    }
}
