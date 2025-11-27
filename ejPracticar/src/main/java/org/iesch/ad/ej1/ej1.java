package org.iesch.ad.ej1;

public class ej1 {
    public static void main(String[] args) {
        Thread t1 = new Thread(new Hilo(1));
        t1.start();
    }
}
