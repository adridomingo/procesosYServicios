package org.iesch.ad.ej6;

public class ej6 {
    public static void main(String[] args) {

        Thread[] hilos = new Thread[2];

        // Creamos los dos hilos con IDs 0 y 1
        hilos[0] = new Thread(new Hilo6(0), "Thread-0");
        hilos[1] = new Thread(new Hilo6(1), "Thread-1");

        hilos[0].start();
        hilos[1].start();
    }
}
