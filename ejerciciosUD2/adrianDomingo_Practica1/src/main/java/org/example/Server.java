package org.example;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws IOException {
        // Puerto
        int port = 12345;
        // Directorio de trabajo
        File workDir = new File("directorioTrabajo");

        if (args.length >= 1) port = Integer.parseInt(args[0]);
        if (args.length >= 2) workDir = new File(args[1]);

        // Si el directorio no existe lo crea
        if (!workDir.exists()) workDir.mkdirs();

        // Creamos el serverSocket con el puerto
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) {
                Socket client = serverSocket.accept();
                Thread t = new Thread(new SrvThread(client, workDir));
                t.start();
            }
        }
    }
}
