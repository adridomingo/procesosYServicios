package org.iesch.ad;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

public class FileProcessor implements Runnable {

    private final List<Path> files;
    private final Map<String, EstadisticasMensuales> statsMap;
    private final Gson gson = new Gson();

    public FileProcessor(List<Path> files, Map<String, EstadisticasMensuales> statsMap) {
        this.files = files;
        this.statsMap = statsMap;
    }

    @Override
    public void run() {
        for (Path path : files) {
            try {
                String content = new String(Files.readAllBytes(path));

                try {
                    Temperaturas single = gson.fromJson(content, Temperaturas.class);
                    if (single != null) processRecord(single);
                } catch (JsonSyntaxException e) {
                    System.err.println("JSON inválido en " + path + ": " + e.getMessage());
                }

            } catch (IOException e) {
                System.err.println("Error leyendo " + path + ": " + e.getMessage());
            }
        }
    }

    private void processRecord(Temperaturas r) {
        if (r == null || r.getFecha() == null) return;
        String monthKey = r.getFecha().length() >= 7 ? r.getFecha().substring(0, 7) : r.getFecha();

        statsMap.putIfAbsent(monthKey, new EstadisticasMensuales());
        EstadisticasMensuales s = statsMap.get(monthKey);
        s.updateWithRecord(r);
    }
}