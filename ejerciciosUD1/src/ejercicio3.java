public class ejercicio3 {
    public static void main(String[] args) {
        Thread hilo1 = new Thread(new HiloHola(), "hola");
        Thread hilo2 = new Thread(new HiloMundo(), "mundo!");

        hilo1.start();

        try {
            Thread.sleep(20);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        hilo2.start();

        try {
            Thread.sleep(5000);
            System.out.println("Interrupiendo hilo 1");
            hilo1.interrupt();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    static class HiloHola implements Runnable {
        @Override
        public void run() {
            try {
                for (int i = 0; i<=15; i++) {
                    System.out.println("hola");
                    Thread.sleep(2000);
                }
                System.out.println("Terminado hola!");
            } catch (InterruptedException e) {
                System.out.println("Interrupiendo ejecucion hola");
            }
        }
    }

    static class HiloMundo implements Runnable {
        @Override
        public void run() {
            try {
                for (int i = 0; i<=15; i++) {
                    System.out.println("mundo");
                    Thread.sleep(2000);
                }
                System.out.println("Terminado mundo!");
            } catch (InterruptedException e) {
                System.out.println("Interrupiendo ejecucion mundo");
                throw new RuntimeException(e);
            }
        }
    }
}
