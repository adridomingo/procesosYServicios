package org.iesch.ad.ej6;

import java.util.Random;

public class Hilo6 implements Runnable {

    private int idHilo;  // 0 o 1

    public Hilo6(int idHilo) {
        this.idHilo = idHilo;
    }

    @Override
    public void run() {
        try {
            for (int i = 1; i <= 5; i++) {

                System.out.println(i + " - " + Thread.currentThread().getName());

                Thread.sleep(new Random().nextInt(4000));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
