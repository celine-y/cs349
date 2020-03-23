import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;

public class ToolPanel extends JPanel {

    private GameFrame gameFrame;

    private JLabel name;
    private JLabel userid;
    private JLabel pointLabel;

    public ToolPanel(GameFrame gameFrame){
        setLayout(new BorderLayout());
        setFocusable(false);
        setBackground(new Color(181, 242, 255));
        this.gameFrame = gameFrame;

        try {
            ImageIcon settingIcon = getImageIcon("gear.png", 40);
            JLabel settingLabel = new JLabel(settingIcon);

            settingLabel.addMouseListener(gameFrame);
            this.add(settingLabel, BorderLayout.LINE_END);
        } catch (Exception e){
            e.printStackTrace();
        }

        name = new JLabel("CLCYAU");
        userid = new JLabel("20610891");
        JPanel details = new JPanel(new FlowLayout());
        details.setOpaque(false);
        details.add(name);
        details.add(userid);
        this.add(details, BorderLayout.CENTER);

        pointLabel = new JLabel();
        JPanel pointPanel = new JPanel(new FlowLayout());
        pointPanel.setOpaque(false);
        pointPanel.add(pointLabel);
        this.add(pointPanel, BorderLayout.LINE_START);
    }

    public void setUserId(String id){
        userid.setText(id);
    }

    public void setUserName(String name){
        this.name.setText(name);
    }

    public void setPoints(int points){
        pointLabel.setText(String.valueOf(points));
    }

    private ImageIcon getImageIcon(String resourceName, int height) throws Exception{
        String location = "/" + resourceName;

        Image image = ImageIO.read(getClass().getResource(location))
                .getScaledInstance(-1, height, Image.SCALE_SMOOTH);
        return new ImageIcon(image);
    }


}
