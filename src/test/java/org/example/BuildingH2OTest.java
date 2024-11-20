package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BuildingH2OTest {

    // Diogo: Achas que eles se vao importar se eu perguntar se faço coding da minha solution antes do testing?
    // Diogo: COmo faço para naão estar sempre a correr várias vezes o teste porque sei que threads podem executar em diferentes ordens;

    private BuildingH2O h2o;
    private StringBuilder sbResult;
    private long startTime;
    private static final long TIMEOUT = 2000; // 2 seconds timeout

    private Thread thread1H;
    private Thread thread2H;
    private Thread thread3O;
    private Thread thread4H;
    private Thread thread5H;
    private Thread thread6O;

    @BeforeEach
    void setUp() {
        h2o = new BuildingH2O();
        sbResult = new StringBuilder();

        this.thread1H = new Thread(() -> {
            try {
                h2o.hydrogen(() -> sbResult.append("H") );
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        this.thread2H = new Thread(() -> {
            try {
                h2o.hydrogen(() -> sbResult.append("H") );
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        this.thread3O = new Thread(() -> {
            try {
                h2o.oxygen(() -> sbResult.append("O") );
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        this.thread4H = new Thread(() -> {
            try {
                h2o.oxygen(() -> sbResult.append("H") );
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        this.thread5H = new Thread(() -> {
            try {
                h2o.oxygen(() -> sbResult.append("H") );
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        this.thread6O = new Thread(() -> {
            try {
                h2o.oxygen(() -> sbResult.append("O") );
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Test
    void testThreadOrderHHO() {

        thread1H.start();
        thread2H.start();
        thread3O.start();

        try {
            thread1H.join();
            thread2H.join();
            thread3O.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        Assertions.assertEquals("HHO", sbResult.toString());

    }

    @Test
    void testThreadOrderOHH() {
        thread3O.start();
        thread1H.start();
        thread2H.start();

        try {
            thread1H.join();
            thread2H.join();
            thread3O.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        Assertions.assertEquals("HHO", sbResult.toString());

    }

    @Test
    void testThreadOH() {
        startTime = System.currentTimeMillis();
        thread3O.start();
        thread1H.start();

        Assertions.assertThrows(RuntimeException.class, () -> {
            while (!thread3O.isInterrupted()) {
                // Simulate some work that could run infinitely

                // Check if thread has been running too long
                if (System.currentTimeMillis() - startTime > TIMEOUT) {
                    thread3O.interrupt();
                    throw new RuntimeException("Thread is running infinitely");
                }
            }
        });

    }

    //Doesn't work for this UseCase
    void testThreadOrderHHOOHH() {
        thread3O.start();
        thread1H.start();
        thread2H.start();
        thread4H.start();
        thread5H.start();
        thread6O.start();

        // We can re-use threads, or create others

        try {
            thread1H.join();
            thread2H.join();
            thread3O.join();
            thread4H.join();
            thread5H.join();
            thread6O.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        Assertions.assertEquals("HHOHHO", sbResult.toString());

    }


}