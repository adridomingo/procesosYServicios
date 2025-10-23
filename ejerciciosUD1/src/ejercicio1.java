import java.io.File;
import java.io.IOException;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class ejercicio1 {
    public static void main(String[] args) {
        try {
            File file = new File("C:\\Users\\dam2\\Documents\\hola.txt");
            Process pNotepad = new ProcessBuilder("C:\\Program Files\\Notepad++\\notepad++.exe", file.getAbsolutePath()).start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}