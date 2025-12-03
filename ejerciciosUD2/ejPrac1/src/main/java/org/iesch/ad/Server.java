package org.iesch.ad;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        int port = 12345;
        File workDir = new File("archivo.txt");

        if (args.length >= 1) port = Integer.parseInt(args[0]);
        if (args.length >= 2) workDir = new File(args[1]);

        try (ServerSocket server = new ServerSocket(port)) {

            while (true) {
                Socket client = server.accept();
                new Thread(new SvrThread(client, workDir))
                        .start();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
