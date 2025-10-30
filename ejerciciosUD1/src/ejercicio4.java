public class ejercicio4 {

    public static void main(String[] args) {
        long tiempoInicio = System.currentTimeMillis();
        String nombreHiloPrincipal = Thread.currentThread().getName();

        System.out.println("[" + nombreHiloPrincipal + "] Iniciando aplicación");

        long tiempoEsperaMaximo = Long.MAX_VALUE;
        if (args.length > 0) {
            tiempoEsperaMaximo = Long.parseLong(args[0]);
            System.out.println("[" + nombreHiloPrincipal + "] Tiempo de espera máximo: " + tiempoEsperaMaximo + " segundos");
        }

        HiloMensajes hiloSecundario = new HiloMensajes();
        hiloSecundario.setName("Hilo-Secundario");
        hiloSecundario.start();

        long tiempoEsperado = 0;

        while (hiloSecundario.isAlive()) {
            try {
                Thread.sleep(1000);
                tiempoEsperado++;
                System.out.println("[" + nombreHiloPrincipal + "] Esperando... (" + tiempoEsperado + " segundos)");

                if (tiempoEsperado >= tiempoEsperaMaximo) {
                    System.out.println("[" + nombreHiloPrincipal + "] Interrumpiendo hilo secundario");
                    hiloSecundario.interrupt();
                }

            } catch (InterruptedException e) {
                break;
            }
        }

        try {
            hiloSecundario.join();
        } catch (InterruptedException e) {
        }

        long tiempoFin = System.currentTimeMillis();
        long tiempoEjecucion = (tiempoFin - tiempoInicio) / 1000;

        System.out.println("[" + nombreHiloPrincipal + "] Finalización de la ejecución del programa");
        System.out.println("[" + nombreHiloPrincipal + "] Tiempo total de ejecución: " + tiempoEjecucion + " segundos");
    }
}

class HiloMensajes extends Thread {
    private final String[] mensajes = {"Programas", "Procesos", "Servicios", "Hilos"};

    @Override
    public void run() {
        String nombreHilo = Thread.currentThread().getName();

        for (int i = 0; i < mensajes.length; i++) {
            System.out.println("[" + nombreHilo + "] " + mensajes[i]);

            if (i < mensajes.length - 1) {
                try {
                    Thread.sleep(4000);
                } catch (InterruptedException e) {
                    System.out.println("[" + nombreHilo + "] Interrumpido. Mensajes restantes sin esperas");
                    for (int j = i + 1; j < mensajes.length; j++) {
                        System.out.println("[" + nombreHilo + "] " + mensajes[j]);
                    }
                    break;
                }
            }
        }
    }
}