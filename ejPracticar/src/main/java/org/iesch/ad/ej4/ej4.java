package org.iesch.ad.ej4;

public class ej4 {

    public enum Tipo {
        PARES, IMPARES, TERMINADOS_2_3
    }

    public static void main(String[] args) throws InterruptedException {

        Thread pares = new Thread(new Hilo4(Tipo.PARES));
        Thread impares = new Thread(new Hilo4(Tipo.IMPARES));
        Thread acabados = new Thread(new Hilo4(Tipo.TERMINADOS_2_3));

        pares.start();
        impares.start();
        acabados.start();

        pares.join();
        impares.join();
        acabados.join();

    }
}
