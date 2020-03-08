package src.com.commitpro.apps.inverter;

import java.util.concurrent.TimeUnit;

public class Wechselrichter {

    private static final int PIN_HALBWELLE_1_1 = 18;
    private static final int PIN_HALBWELLE_1_2 = 23;
    private static final int PIN_HALBWELLE_2_1 = 19;
    private static final int PIN_HALBWELLE_2_2 = 24;
    private static final int PIN_HALBWELLE_3_1 = 20;
    private static final int PIN_HALBWELLE_3_2 = 25;

    public static void startWechselrichter() {
        final PhaseGenerator phase1 = new PhaseGenerator(PIN_HALBWELLE_1_1, PIN_HALBWELLE_1_2);
        final PhaseGenerator phase2 = new PhaseGenerator(PIN_HALBWELLE_2_1, PIN_HALBWELLE_2_2);
        final PhaseGenerator phase3 = new PhaseGenerator(PIN_HALBWELLE_3_1, PIN_HALBWELLE_3_2);

        System.out.println("Thread state of phase 1: " + phase1.getState());
        if (phase1.getState().equals(Thread.State.NEW)) {
            System.out.println("========== Start phase1 ! ==========");
            phase1.start();
        }
        while (phase1.isAlive()) {
            if (phase1.getCurrentCircle() == 120) {
                System.out.println("Thread state of phase 2: " + phase2.getState());
                if (phase2.getState().equals(Thread.State.NEW)) {
                    System.out.println("========== Start phase2 after 120 degrees ! ==========");
                    phase2.start();
                }
                while (phase2.isAlive()) {
                    if (phase2.getCurrentCircle() == 120) {
                        System.out.println("Thread state of phase 3: " + phase3.getState());
                        if (phase3.getState().equals(Thread.State.NEW)) {
                            System.out.println("========== Start phase3 after 120 degrees ! ==========");
                            phase3.start();
                        }
                    }
                }
            }
        }

    }

    static class PhaseGenerator extends Thread {
        private final static int NUMBER_OF_SINUS = 2;

        private int halbWelle1;
        private int halbWelle2;
        private int currentCircle = 0;

        public PhaseGenerator(int halbWelle1, int halbWelle2) {
            this.halbWelle1 = halbWelle1;
            this.halbWelle2 = halbWelle2;
        }

        @Override
        public void run() {
            int numberOfSinus = 0;

            while (numberOfSinus < NUMBER_OF_SINUS) {
                try {
                    for (int i = 0; i <= 128; i++) {
                        //Rasperry
                        writeMessage(halbWelle1, i);
                        TimeUnit.MICROSECONDS.sleep(1000);
                        currentCircle++;
                    }
                    for (int i = 0; i <= 128; i++) {
                        //Rasperry
                        writeMessage(halbWelle2, i);
                        TimeUnit.MICROSECONDS.sleep(1000);
                    }
                } catch (InterruptedException e) {
                    System.out.println("Interrupted exception ! " + e.getMessage() + "");
                }
                numberOfSinus++;
            }
        }

        private void writeMessage(int halbwelle, int iteration) {
            switch (halbwelle) {
                case PIN_HALBWELLE_1_1:
                    System.out.println(
                            "-- Switch on Sinus 1 Halbwelle 1 Iteration " + iteration + " on Pin number " + halbWelle1 + ".");
                    break;
                case PIN_HALBWELLE_1_2:
                    System.out.println(
                            "-- Switch on Sinus 1 Halbwelle 2 Iteration " + iteration + " on Pin number " + halbWelle2 + ".");
                    break;
                case PIN_HALBWELLE_2_1:
                    System.out.println(
                            "---- Switch on Sinus 2 Halbwelle 1 Iteration " + iteration + " on Pin number " + halbWelle1 + ".");
                    break;
                case PIN_HALBWELLE_2_2:
                    System.out.println(
                            "---- Switch on Sinus 2 Halbwelle 2 Iteration " + iteration + " on Pin number " + halbWelle2 + ".");
                    break;
                case PIN_HALBWELLE_3_1:
                    System.out.println(
                            "-------- Switch on Sinus 3 Halbwelle 1 Iteration " + iteration + " on Pin number " + halbWelle1 + ".");
                    break;
                case PIN_HALBWELLE_3_2:
                    System.out.println(
                            "-------- Switch on Sinus 3 Halbwelle 2 Iteration " + iteration + " on Pin number " + halbWelle2 + ".");
                    break;
            }
        }

        public int getCurrentCircle() {
            return currentCircle;
        }
    }

}
