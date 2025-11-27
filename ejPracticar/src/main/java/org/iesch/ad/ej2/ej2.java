package org.iesch.ad.ej2;

import org.iesch.ad.ej1.Hilo;

public class ej2 {
    public static void main(String[] args) {

        Contador contador = new Contador();

        Thread[] hilos = new Thread[4];

        for (int i = 0;i<4;i++) {
            hilos[i] = new Thread(new Hilo2(contador, i + 1));
            hilos[i].start();
        }

        for (int i = 0; i<4; i++) {
            try {
                hilos[i].join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        System.out.println("Valor final del contador: " + contador.getValor());

    }
}
