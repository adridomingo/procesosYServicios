package org.iesch.ad.ej3;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

public class Hilo3 implements Runnable {

    AtomicInteger contador;
    char vocal;

    public Hilo3(AtomicInteger contador, char vocal) {
        this.contador = contador;
        this.vocal = vocal;
    }

    @Override
    public void run() {
        acceder();
    }

    synchronized void acceder() {
        try (BufferedReader br = new BufferedReader(new FileReader("frase.txt"))){
            String line;
            while ((line = br.readLine()) != null) {
                for (int i = 0; i < line.length(); i++) {
                    if (line.charAt(i) == vocal) {
                        contador.incrementAndGet();
                    }
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
