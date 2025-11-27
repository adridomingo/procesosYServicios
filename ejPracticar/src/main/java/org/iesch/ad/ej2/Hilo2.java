package org.iesch.ad.ej2;

public class Hilo2 implements Runnable {

    Contador contador;
    int id;

    public Hilo2(Contador contador, int id) {
        this.contador = contador;
        this.id = id;
    }

    @Override
    public void run() {
        for (int i = 0; i < 4; i++) {
            contador.incrementar();
            System.out.println("Hilo " + id + " incrementó el contador. Valor actual: " + contador.getValor());

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
