package org.iesch.ad.ej3;

import org.iesch.ad.ej2.Contador;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ej3 {
    public static void main(String[] args) {

        AtomicInteger contador = new AtomicInteger(0);

        List<Thread> hilos = List.of(
            new Thread(new Hilo3(contador, 'a')),
            new Thread(new Hilo3(contador,'e')),
            new Thread(new Hilo3(contador,'i')),
            new Thread(new Hilo3(contador,'o')),
            new Thread(new Hilo3(contador,'u'))
        );

        for (Thread hilo : hilos)
            hilo.start();

        for (Thread t : hilos) {
            try {
                t.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        System.out.println(contador.get());
    }
}
