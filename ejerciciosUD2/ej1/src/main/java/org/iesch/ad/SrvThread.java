package org.iesch.ad;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class SrvThread implements Runnable {
    private final Socket socket;
    private final File workDir;

    public SrvThread(Socket socket, File workDir) {
        this.socket = socket;
        this.workDir = workDir;
    }

    @Override
    public void run() {
        String clientInfo = socket.getRemoteSocketAddress().toString();
        System.out.println("Connected: " + clientInfo);
        try (ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

            // Protocolo: cliente envía objetos Request; servidor responde con Response
            boolean running = true;
            while (running) {
                Object obj = in.readObject();
                if (!(obj instanceof Request)) {
                    // petición inválida
                    Response r = Response.error("Invalid request object");
                    out.reset();
                    out.writeObject(r);
                    out.flush();
                    continue;
                }
                Request req = (Request) obj;
                switch (req.type) {
                    case LIST:
                        handleList(out);
                        break;
                    case GET:
                        handleGet(out, req.argument);
                        break;
                    case QUIT:
                        out.reset();
                        out.writeObject(Response.okMessage("Goodbye"));
                        out.flush();
                        running = false;
                        break;
                    case ENTER:
                        out.reset();
                        out.writeObject(Response.okMessage("Welcome"));
                        out.flush();
                        break;
                }
            }
        } catch (EOFException eof) {
            System.out.println("Client disconnected abruptly: " + clientInfo);
        } catch (Exception e) {
            System.out.println("Connection error with " + clientInfo + ": " + e.getMessage());
        } finally {
            try { socket.close(); } catch (IOException ignored) {}
            System.out.println("Closed: " + clientInfo);
        }
    }

    private void handleList(ObjectOutputStream out) throws IOException {
        File[] files = workDir.listFiles(File::isFile);
        List<String> names = new ArrayList<>();
        if (files != null) {
            for (File f : files) names.add(f.getName());
        }
        out.reset();
        out.writeObject(Response.okWithData(names));
        out.flush();
    }

    private void handleGet(ObjectOutputStream out, String filename) throws IOException {
        if (filename == null || filename.isEmpty()) {
            out.reset();
            out.writeObject(Response.error("Missing filename"));
            out.flush();
            return;
        }
        // Validación básica para evitar directory traversal
        if (filename.contains("..") || filename.contains(File.separator)) {
            out.reset();
            out.writeObject(Response.error("Invalid filename"));
            out.flush();
            return;
        }
        File target = new File(workDir, filename);
        if (!target.exists() || !target.isFile()) {
            out.reset();
            out.writeObject(Response.error("File not found"));
            out.flush();
            return;
        }
        // Leer fichero línea a línea (UTF-8)
        List<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(target), StandardCharsets.UTF_8))) {
            String l;
            while ((l = br.readLine()) != null) lines.add(l);
        } catch (IOException e) {
            out.reset();
            out.writeObject(Response.error("Error reading file"));
            out.flush();
            return;
        }
        out.reset();
        out.writeObject(Response.okWithData(lines));
        out.flush();
    }
}
