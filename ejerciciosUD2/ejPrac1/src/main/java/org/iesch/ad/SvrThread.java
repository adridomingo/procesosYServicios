package org.iesch.ad;

import org.iesch.ad.modelo.Persona;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class SvrThread implements Runnable {

    private final Socket socket;
    private final File workdir;

    public SvrThread(Socket socket, File workdir) {
        this.socket = socket;
        this.workdir = workdir;
    }

    @Override
    public void run() {
        try (ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())
        )
        {
            boolean running = true;

            while (running) {
                Object obj = in.readObject();

                if (!(obj instanceof Request)) {
                    Response r = Response.error("Invalido");
                    out.reset();
                    out.writeObject(r);
                    out.flush();
                    continue;
                }
                Request req = (Request) obj;
                switch (req.tipo) {
                    case ENTER:
                        out.reset();
                        out.writeObject(Response.okMsg("Bienvenido!"));
                        out.flush();
                        break;
                    case LIST:
                        listar(out);
                        break;
                    case PUT:
                        put(out, req.argument);
                        break;
                    case QUIT:
                        out.reset();
                        out.writeObject(Response.okMsg("Adios"));
                        out.flush();
                        running = false;
                        break;
                }

            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void listar(ObjectOutputStream out) {
        File archivo = workdir;
        List<Persona> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))){

            String line;
            while ((line = br.readLine()) != null) {
                if (line.isEmpty()) {
                    continue;
                }
                list.add(new Persona(line.split(",")[0],line.split(",")[1],Integer.parseInt(line.split(",")[2])));
            }

            out.reset();
            out.writeObject(Response.okWithData(list));
            out.flush();

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void put(ObjectOutputStream out, Persona persona) {
        if (persona == null) return;
        File archivo = workdir;
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo, true))) {
            
            bw.write(persona.getNombre()+","+persona.getApellido()+","+persona.getEdad());

            out.reset();
            out.writeObject(Response.okWithData(List.of(persona)));
            out.flush();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
