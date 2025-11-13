package ej5.modelo;

public class Saldo {
    private double saldo;

    public Saldo(double saldo) {
        this.saldo = saldo;
    }

    public double getSaldo() {
        try {
            Thread.sleep((long)(Math.random() * 100));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return saldo;
    }

    private void setSaldo(double nuevoSaldo) {
        try {
            Thread.sleep((long)(Math.random() * 100));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.saldo = nuevoSaldo;
    }

    public synchronized void subirSaldo(double cantidad) {
        String nombreHilo = Thread.currentThread().getName();
        double saldoInicial = getSaldo();

        System.out.println("Nombre: " + nombreHilo);
        System.out.println("Cantidad: " + cantidad);
        System.out.println("Saldo inicial: " + saldoInicial);

        setSaldo(saldoInicial + cantidad);

        System.out.println("Saldo final:" + getSaldo());
    }





    @Override
    public String toString() {
        return "Saldo{" +
                "saldo=" + saldo +
                '}';
    }
}


