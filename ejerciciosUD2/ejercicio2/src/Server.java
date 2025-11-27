import java.net.ServerSocket;

public class Server {
    public static void main(String[] args) {
        boolean[] shutdown = {false};
        int port;

        try {
            port = Integer.parseInt(args[0]);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        try (ServerSocket svr = new ServerSocket(port)) {
            while (!shutdown[0]) {
                try {
                    new Thread(new SrvThread(svr.accept(), shutdown))
                            .start();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
