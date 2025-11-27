package org.example;

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
            e.printStackTrace();
            return;
        }

        try (Socket cli = new Socket(host, port);
             ObjectOutputStream out = new ObjectOutputStream(cli.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(cli.getInputStream());
             Scanner sc = new Scanner(System.in, StandardCharsets.UTF_8)) {

            System.out.println("Conectado a  " + cli.getInetAddress() + " : " + cli.getPort());

            // Entra con el Request.enter
            Request req = Request.enter();
            out.reset();
            out.writeObject(req);
            Response response = (Response) in.readObject();
            // Procesa las response con los datos introducidos
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

        System.out.println("Desconectado");
    }

    // Manda el menu para que el usuario escoja opciones y crea nueva Request
    private static Request newRequest(Scanner sc) {
        System.out.println("\n1) Listar Imagenes\n2) Enviar Imagen\n3) Salir");
        System.out.print("Elige una opción: ");
        String opt = sc.nextLine().trim();
        switch (opt) {
            case "1":
                return Request.list();
            case "2":
                System.out.print("Nombre de la imagen: ");
                String name = sc.nextLine().trim();
                return Request.send(name);
            case "3":
                return Request.quit();
            default:
                System.out.println("Opción inválida, intentar de nuevo.");
                return newRequest(sc);
        }
    }
    // Procesa la respuesta y comprueba que haya datos
    private static boolean processResponse(Response response, Scanner sc) {
        if (response == null) {
            System.out.println("Respuesta nula del servidor.");
            return false;
        }
        if (!response.ok) {
            System.out.println("ERROR: " + response.message);
            return true;
        }
        // Si hay datos los muestra
        if (response.data != null) {
            List<String> data = response.data;
            if (data.isEmpty()) {
                System.out.println("sin resultados");
            } else {
                for (String line : data) System.out.println(line);
            }
        } else if (response.message != null) {
            // Muestra el mensaje de Adios
            System.out.println(response.message);
            // Al ser adios cambia stop a true y devuelve false al procesar la respuesta
            if (response.message.equalsIgnoreCase("Adios")) {
                stop = true;
                return false;
            }
        }
        // Si va correctamente devuelve true y se procesa la respuesta
        return true;
    }
}