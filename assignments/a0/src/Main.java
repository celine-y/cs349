import javax.swing.JDialog;
import java.io.PrintWriter;

public class Main {
    public static void main(String[] args) {
        String version = System.getProperty("java.version");
        String output = "Java JDK version: " + version;

        try {
            PrintWriter writer = new PrintWriter("version.txt", "UTF-8");
            writer.println(output);
            writer.close();
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }

        System.out.println(output);
    }
}