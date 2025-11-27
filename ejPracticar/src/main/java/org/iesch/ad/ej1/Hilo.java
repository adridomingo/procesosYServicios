package org.iesch.ad.ej1;

public class Hilo implements Runnable {

    private int numHilos;

    public Hilo(int numHilos) {
        this.numHilos = numHilos;
    }

    @Override
    public void run() {
        for (int i = 1; i <= 2; i++) {
            System.out.println("Hilo " + numHilos + " iniciando...");
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        System.out.println("Hilo " + numHilos + " finalizando...");

        if (numHilos < 5) {
            Thread siguiente = new Thread(new Hilo(numHilos+1));
            siguiente.start();
        }
    }
}
