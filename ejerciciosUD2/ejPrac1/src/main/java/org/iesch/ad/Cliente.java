package org.iesch.ad;

import org.iesch.ad.modelo.Persona;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
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
        }  catch (Exception ex) {
            ex.printStackTrace();
            return;
        }


        try (Socket cliente = new Socket(host, port);
             ObjectOutputStream out = new ObjectOutputStream(cliente.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(cliente.getInputStream());
             Scanner sc = new Scanner(System.in);)
        {
            Request req = Request.enter();
            out.reset();
            out.writeObject(req);
            out.flush();
            Response res = (Response) in.readObject();
            if (processResponse(res, sc)) {
                while(!stop) {
                    req = newRequest(sc);
                    out.reset();
                    out.writeObject(req);
                    out.flush();
                    res = (Response) in.readObject();
                    processResponse(res, sc);
                }
            }


        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static Request newRequest(Scanner sc) {
        System.out.println("Elige opcion:");
        System.out.println("1. LISTAR");
        System.out.println("2. PUT");
        System.out.println("3. EXIT");
        String opt = sc.nextLine().trim();

        switch (opt) {
            case "1":
                return Request.list();
            case "2":
                System.out.println("Introduce nombre:");
                String nombre = sc.next();
                System.out.println("Introduce apellido:");
                String apellido = sc.next();
                System.out.println("Introduce edad:");
                int edad = sc.nextInt();
                return Request.put(new Persona(nombre, apellido, edad));
            case "3":
                return Request.quit();
            default:
                System.out.println("Opcion invalida");
                break;
        }
        return newRequest(sc);
    }

    public static boolean processResponse(Response response, Scanner sc) {
        if (response == null) {
            System.out.println("Respuesta nula");
            return false;
        }

        if (!response.ok) {
            System.out.println("ERROR " + response.msg );
            return false;
        }

        if (response.data != null) {
            List<Persona> lista = response.data;
            if (lista.isEmpty()) {
                System.out.println("Respuesta no encontrada");
            } else {
                for (Persona persona : lista) System.out.println(persona);
            }
        } else if (response.msg != null) {
            System.out.println(response.msg);
            if (response.msg.equalsIgnoreCase("Adios")) {
                stop = true;
                return false;
            }
        }
        return true;
    }
}
