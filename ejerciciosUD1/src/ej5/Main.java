package ej5;

import ej5.modelo.Saldo;

public class Main {
    public static void main(String[] args) {

        Saldo saldo = new Saldo(1000);

        System.out.println("Valor inicial: " + saldo.getSaldo());

        Thread hilo1 = new Thread(new OperacionSaldo(saldo, 200.0), "Ingreso-Juan");
        Thread hilo2 = new Thread(new OperacionSaldo(saldo, 150.0), "Ingreso-María");
        Thread hilo3 = new Thread(new OperacionSaldo(saldo, 300.0), "Ingreso-Pedro");
        Thread hilo4 = new Thread(new OperacionSaldo(saldo, 100.0), "Ingreso-Ana");
        Thread hilo5 = new Thread(new OperacionSaldo(saldo, 250.0), "Ingreso-Luis");

        hilo1.start();
        hilo2.start();
        hilo3.start();
        hilo4.start();
        hilo5.start();

        try {
            hilo1.join();
            hilo2.join();
            hilo3.join();
            hilo4.join();
            hilo5.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Saldo final: " + saldo.getSaldo());
    }
}
