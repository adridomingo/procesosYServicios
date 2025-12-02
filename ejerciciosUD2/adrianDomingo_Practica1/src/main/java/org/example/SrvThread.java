package org.example;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class SrvThread implements Runnable{
    private final Socket socket;
    private final File workDir;

    public SrvThread(Socket socket, File workDir) {
        this.socket = socket;
        this.workDir = workDir;
    }

    @Override
    public void run() {

        try (ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())
        ){
            boolean running = true;

            while (running) {
                Object obj = in.readObject();
                if (!(obj instanceof Request)) {
                    Response r = Response.error("Objecto Invalido");
                    out.reset();
                    out.writeObject(r);
                    out.flush();
                    continue;
                }
                Request req = (Request) obj;
                switch (req.type) {
                    case LIST:
                        listarImagenes(out);
                        break;
                    case SEND:
                        enviarImagen(out, req.argument);
                        break;
                    case QUIT:
                        out.reset();
                        out.writeObject(Response.okMessage("Adios"));
                        out.flush();
                        running = false;
                        break;
                    case ENTER:
                        out.reset();
                        out.writeObject(Response.okMessage("Bienvenido"));
                        out.flush();
                        break;
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void listarImagenes(ObjectOutputStream out) throws IOException {
        File[] files = workDir.listFiles(File::isFile);
        List<String> names = new ArrayList<>();
        if (files != null) {
            for (File f : files) {
                if (f.getName().contains(".png") || f.getName().contains(".jpg")) {
                    names.add(f.getName());
                }
            }
        }
        out.reset();
        out.writeObject(Response.okWithData(names));
        out.flush();
    }

    private void enviarImagen(ObjectOutputStream out, String filename) throws IOException {
        if (filename == null || filename.isEmpty()) {
            out.reset();
            out.writeObject(Response.error("Introduce una imagen a enviar"));
            out.flush();
            return;
        }
        File target = new File(workDir, filename);
        if (!target.exists() || !target.isFile()) {
            out.reset();
            out.writeObject(Response.error("Imagen no encontrada"));
            out.flush();
            return;
        }

        List<String> lista = new ArrayList<>();

        lista.add("Imagen ya Descargada!");

        out.reset();
        out.writeObject(Response.okWithData(lista));
        out.flush();
    }
}
