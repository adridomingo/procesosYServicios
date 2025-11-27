import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SrvThread implements Runnable{

    private Socket socket;
    private boolean[] shutdown;
    private boolean stop = false;

    public SrvThread(Socket socket, boolean[] shutdown) {
        this.socket = socket;
        this.shutdown = shutdown;
    }

    @Override
    public void run() {
        try (ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())
        ){
            while (!stop && !shutdown[0]) {

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
