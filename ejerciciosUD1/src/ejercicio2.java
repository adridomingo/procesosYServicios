import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ejercicio2 {
    public static void main(String[] args) {
        try {
            String home = System.getProperty("user.home");
            Path documentos = Path.of(home, "Documents");
            Files.createDirectories(documentos);
            Path bat = documentos.resolve("comandos.psp.bat");

            String contenidoBat = String.join(System.lineSeparator(),
                    "ping www.dam2chomon.org",
                    "ping www.google.es",
                    "pring www.iesch.org"
            );
            Files.writeString(bat, contenidoBat);

            Path logSalida  = documentos.resolve("salida_cmd.txt");
            Path logErrores = documentos.resolve("errores_cmd.txt");

            Files.deleteIfExists(logSalida);
            Files.deleteIfExists(logErrores);

            Files.createFile(logSalida);
            Files.createFile(logErrores);

            ProcessBuilder pb = new ProcessBuilder("cmd.exe", "/c", bat.toString());

            pb.redirectOutput(logSalida.toFile());
            pb.redirectError(logErrores.toFile());

            Process p = pb.start();

            int codigo = p.waitFor();

            System.out.println("cmd finalizado con código: " + codigo);
            System.out.println("Log de salida:  " + logSalida);
            System.out.println("Log de errores: " + logErrores);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }


    }
}
