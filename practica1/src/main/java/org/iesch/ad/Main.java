import org.iesch.ad.EstadisticasMensuales;
import org.iesch.ad.FileProcessor;

import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public static void main (String[] args) throws Exception {

        Path dir = Path.of("src/main/java/org/iesch/ad/items");
        List<Path> files = new ArrayList<>();

        // Obtener todos los ficheros
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
            for (Path p : stream)
                files.add(p);
        }

        int numHilos = Runtime.getRuntime().availableProcessors();
        System.out.println("Usando hilos: " + numHilos);

        Map<String, EstadisticasMensuales> statsMap = new ConcurrentHashMap<>();

        List<Thread> hilos = new ArrayList<>();
        int chunk = files.size() / numHilos;

        for (int i = 0; i < numHilos; i++) {
            int start = i * chunk;
            int end = (i == numHilos - 1) ? files.size() : start + chunk;

            List<Path> subset = files.subList(start, end);

            Thread t = new Thread(new FileProcessor(subset, statsMap));
            hilos.add(t);
            t.start();
        }

        for (Thread t : hilos) t.join();

        // Mostrar resultados
        statsMap.forEach((mes, stats) -> {
            System.out.println("\nMES: " + mes);
            System.out.println("Máxima: " + stats.getMaxTemp() + " (" + stats.getMaxDate() + ", " + stats.getMaxHour() + ", " + stats.getMaxStation() + ")");
            System.out.println("Mínima: " + stats.getMinTemp() + " (" + stats.getMinDate() + ", " + stats.getMinHour() + ", " + stats.getMinStation() + ")");
        });
    }