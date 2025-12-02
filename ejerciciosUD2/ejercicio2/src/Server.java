import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws IOException {

        int port = 12345;
        File workDir = new File("contactos.txt");

        if (args.length >= 1) port = Integer.parseInt(args[0]);
        if (args.length >= 2) workDir = new File(args[1]);


        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) {
                Socket client = serverSocket.accept();
                Thread t = new Thread(new SrvThread(client, workDir));
                t.start();
            }
        }
    }
}
