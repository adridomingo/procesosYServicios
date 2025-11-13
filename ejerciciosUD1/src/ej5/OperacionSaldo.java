package ej5;

import ej5.modelo.Saldo;

class OperacionSaldo implements Runnable {
    private Saldo saldo;
    private double cantidad;

    public OperacionSaldo(Saldo saldo, double cantidad) {
        this.saldo = saldo;
        this.cantidad = cantidad;
    }

    @Override
    public void run() {
        saldo.subirSaldo(cantidad);
    }
}
