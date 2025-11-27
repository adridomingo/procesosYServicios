package org.iesch.ad.ej5;

import java.util.concurrent.Semaphore;

public class Hilo5 implements Runnable {

    public static Semaphore sem0 = new Semaphore(1);
    public static Semaphore sem1 = new Semaphore(0);

    private int idHilo;  // 0 o 1

    public Hilo5(int idHilo) {
        this.idHilo = idHilo;
    }

    @Override
    public void run() {
        try {
            for (int i = 1; i <= 5; i++) {

                if (idHilo == 0) {
                    sem0.acquire();
                } else {
                    sem1.acquire();
                }

                System.out.println(i + " - " + Thread.currentThread().getName());

                if (idHilo == 0) {
                    sem1.release();
                } else {
                    sem0.release();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}