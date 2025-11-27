package org.iesch.ad.ej4;

public class Hilo4 implements Runnable {

    private ej4.Tipo tipo;

    public Hilo4(ej4.Tipo tipo) {
        this.tipo = tipo;
    }

    @Override
    public void run() {
        int contador = 0;

        for (int i = 0; i < 1000; i++) {
            if (tipo == ej4.Tipo.PARES) {
                if (i % 2 == 0) {
                    contador++;
                }
            }

            if (tipo == ej4.Tipo.IMPARES) {
                if (i % 2 != 0) {
                    contador++;
                }
            }

            if (tipo == ej4.Tipo.TERMINADOS_2_3) {
                if (String.valueOf(i).endsWith("2") || String.valueOf(i).endsWith("3")) {
                    contador++;
                }
            }
        }

        System.out.println(tipo + " - " + contador);
    }
}
