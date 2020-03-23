import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import java.awt.event.ActionEvent;

public class ToolBar extends JMenuBar {

    private JMenu mainMenu;
    private JMenuItem newMI, loadMI, saveMI;

    private MainPaint mainPaint;

    private static final String fileString = "File";
    private static final String newString = "New";
    private static final String loadString = "Load";
    private static final String saveString = "Save";

    public ToolBar() {

        menuItems();
    }

    public void setMainPaint(MainPaint mainPaint){
        this.mainPaint = mainPaint;
    }

    private void menuItems() {
//        File menu
        mainMenu = new JMenu(fileString);
        this.add(mainMenu);
//        New option
        newMI = new JMenuItem(new AbstractAction(newString) {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainPaint.resetCanvas();
            }
        });
        mainMenu.add(newMI);
//        Load option
        loadMI = new JMenuItem(new AbstractAction(loadString) {
            @Override
            public void actionPerformed(ActionEvent e) {
//                TODO: load
                mainPaint.loadCanvas();
            }
        });
        mainMenu.add(loadMI);
//        Save option
        saveMI = new JMenuItem(new AbstractAction(saveString) {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainPaint.saveCanvas();
            }
        });
        mainMenu.add(saveMI);
    }

}
