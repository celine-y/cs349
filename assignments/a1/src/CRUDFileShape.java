import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;

public class CRUDFileShape {

    final JFileChooser jfc = new JFileChooser();
    final String extension = ".txt";
    private Component parent;

    public CRUDFileShape(Component parent) {
        FileNameExtensionFilter filter = new FileNameExtensionFilter("TEXT FILES", "txt", "text");
        jfc.setFileFilter(filter);
        this.parent = parent;
    }

    public void saveShapes(ArrayList<CustomShape> customShapes) {
        int returnVal = jfc.showDialog(parent, "Save");

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = addFileExtIfNecessary();
            try {
                if (!file.createNewFile()) {
                    System.out.println("File already exists.");
                }
                FileOutputStream fos = new FileOutputStream(file);
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(customShapes);
                oos.close();
                fos.close();
            } catch (Exception e){
                e.printStackTrace();
            }
        } else {
            System.out.println("Cancelled by user");
        }
    }

    public ArrayList<CustomShape> loadShapes(){
        ArrayList<CustomShape> customShapes;
        int returnVal = jfc.showDialog(parent, "Open");

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = jfc.getSelectedFile();

            try
            {
                FileInputStream fis = new FileInputStream(file);
                ObjectInputStream ois = new ObjectInputStream(fis);

                customShapes = (ArrayList) ois.readObject();

                ois.close();
                fis.close();

                return customShapes;
            }
            catch (IOException ioe)
            {
                ioe.printStackTrace();
                return null;
            }
            catch (ClassNotFoundException c)
            {
                System.out.println("Class not found");
                c.printStackTrace();
                return null;
            }
        } else {
            System.out.println("Cancelled by user");
            return null;
        }
    }

    private File addFileExtIfNecessary() {
        File file = jfc.getSelectedFile();
        if (file.getName().lastIndexOf(extension) == -1){
            file = new File(file.toString() + extension);
        }

        return file;
    }

}
