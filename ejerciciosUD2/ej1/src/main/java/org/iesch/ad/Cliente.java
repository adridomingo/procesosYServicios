package org.iesch.ad;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Scanner;

public class Cliente {
    private static boolean stop = false;

    public static void main(String[] args) {
        String host;
        int port;
        try {
            host = args[0];
            port = Integer.parseInt(args[1]);
        } catch (Exception e) {
            System.out.println("Missing server info");
            return;
        }

        try (Socket cli = new Socket(host, port);
             ObjectOutputStream out = new ObjectOutputStream(cli.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(cli.getInputStream());
             Scanner sc = new Scanner(System.in, StandardCharsets.UTF_8)) {

            System.out.println("Connected to " + cli.getInetAddress() + " : " + cli.getPort());

            // petición inicial tipo ENTER (como en tu ejemplo)
            Request req = Request.enter();
            out.reset();
            out.writeObject(req);
            Response response = (Response) in.readObject();
            if (processResponse(response, sc)) {
                while (!stop) {
                    req = newRequest(sc);
                    out.reset();
                    out.writeObject(req);
                    response = (Response) in.readObject();
                    processResponse(response, sc);
                }
            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        System.out.println("Disconnected");
    }

    // Genera la nueva petición a partir de la entrada del usuario
    private static Request newRequest(Scanner sc) {
        System.out.println("\n1) Listar ficheros\n2) Mostrar fichero\n3) Salir");
        System.out.print("Elige una opción: ");
        String opt = sc.nextLine().trim();
        switch (opt) {
            case "1":
                return Request.list();
            case "2":
                System.out.print("Nombre del fichero: ");
                String name = sc.nextLine().trim();
                return Request.get(name);
            case "3":
                return Request.quit();
            default:
                System.out.println("Opción inválida, intentar de nuevo.");
                return newRequest(sc); // sin preguntar más (recursivo)
        }
    }

    // Procesa la respuesta del servidor; devuelve true si seguir; puede setear stop=true
    private static boolean processResponse(Response response, Scanner sc) {
        if (response == null) {
            System.out.println("Respuesta nula del servidor.");
            return false;
        }
        if (!response.ok) {
            System.out.println("ERROR: " + response.message);
            // si el servidor indica error no paramos el cliente por defecto
            return true;
        }
        // Si hay datos, los mostramos
        if (response.data != null) {
            List<String> data = response.data;
            if (data.isEmpty()) {
                System.out.println("(sin resultados)");
            } else {
                for (String line : data) System.out.println(line);
            }
        } else if (response.message != null) {
            System.out.println(response.message);
            // si mensaje de despedida
            if (response.message.equalsIgnoreCase("Goodbye")) {
                stop = true;
                return false;
            }
        }
        return true;
    }
}
