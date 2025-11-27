package org.iesch.ad;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.time.LocalDateTime;

public class Server {
    public static void main(String[] args) throws IOException {
        int port = 12345;
        File workDir = new File("server_workdir");


        if (args.length >= 1) port = Integer.parseInt(args[0]);
        if (args.length >= 2) workDir = new File(args[1]);


        if (!workDir.exists()) workDir.mkdirs();


        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server listening on port " + port + " (workDir=" + workDir.getAbsolutePath() + ")");
            while (true) {
                Socket client = serverSocket.accept();
                Thread t = new Thread(new SrvThread(client, workDir));
                t.start();
            }
        }
    }
}
