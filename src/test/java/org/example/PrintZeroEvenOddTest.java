package org.example;

import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.function.IntConsumer;

class PrintZeroEvenOddTest {
    PrintZeroEvenOdd printZeroEvenOdd;
    StringBuilder sbResult;

    Thread thread1;
    Thread thread2;
    Thread thread3;
    private final static long timeout = 5000; // 5 seconds of waiting for thread execution

    @BeforeEach
    void setUp() {
        sbResult = new StringBuilder();

        // Lambda expression:
        IntConsumer print = value -> sbResult.append(value);

        this.thread1 = new Thread(() -> {
            try {
                printZeroEvenOdd.zero(print);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        this.thread2 = new Thread(() -> {
            try {
                printZeroEvenOdd.even(print);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        this.thread3 = new Thread(() -> {
            try {
                printZeroEvenOdd.odd(print);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Test
    void print01Test() {
        int n = 1;
        printZeroEvenOdd = new PrintZeroEvenOdd(n);

        thread1.start();
        thread2.start();
        thread3.start();

        try {
            thread1.join();
            thread2.join();
            thread3.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        Assertions.assertEquals("01", sbResult.toString());
    }

    @Test
    void print0102030405Test() {
        int n = 5;
        printZeroEvenOdd = new PrintZeroEvenOdd(n);

        thread1.start();
        thread2.start();
        thread3.start();

        try {
            thread1.join();
            thread2.join();
            thread3.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        Assertions.assertEquals("0102030405", sbResult.toString());
    }

    @Test
    void print0Test() {
        int n = 0;
        printZeroEvenOdd = new PrintZeroEvenOdd(n);

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
            thread3.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        Assertions.assertEquals("", sbResult.toString());

    }

    @Test
    void print0runThreadZeroAndThreadEvenTest() {
        int n = 0;
        printZeroEvenOdd = new PrintZeroEvenOdd(n);

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        Assertions.assertEquals("", sbResult.toString());

    }

    @Test
    void print0runThreadEvenAndThreadOddTest() {
        int n = 0;
        printZeroEvenOdd = new PrintZeroEvenOdd(n);

        thread2.start();
        thread3.start();

        try {
            thread2.join();
            thread3.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        Assertions.assertEquals("", sbResult.toString());
    }

    @Test
    void print01RunThreadZeroAndThreadEvenTest() {
        int n = 1;
        printZeroEvenOdd = new PrintZeroEvenOdd(n);

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        Assertions.assertEquals("0", sbResult.toString());
    }

    @Test
    void print01RunThreadEvenAndThreadOddTest() {
        int n = 1;
        printZeroEvenOdd = new PrintZeroEvenOdd(n);

        thread2.start();

        try {
            thread2.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        Assertions.assertThrows(RuntimeException.class, () -> {
                long startingTime = System.currentTimeMillis();
                thread3.start();
                while(!thread3.isInterrupted()){
                    if(System.currentTimeMillis() - startingTime > timeout){
                        thread3.interrupt();
                        throw new RuntimeException(Thread.currentThread().getName() + " has been executing for more than " + timeout + " ms.");
                    }
                }
        });
    }

    @Test
    void print01RunThreadOddAndThreadZeroTest() {
        int n = 1;
        printZeroEvenOdd = new PrintZeroEvenOdd(n);


        thread1.start();

        try {
            thread1.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        Assertions.assertThrows(RuntimeException.class, () -> {
            long startingTime = System.currentTimeMillis();
            thread3.start();
            while(!thread3.isInterrupted()){
                if(System.currentTimeMillis() - startingTime > timeout){
                    thread3.interrupt();
                    throw new RuntimeException(Thread.currentThread().getName() + " has been executing for more than " + timeout + " ms.");
                }
            }
        });
    }

    // Should have done this edge case too
    void print0102RunThreadOddAndThreadZeroTest() {
        int n = 2;
        printZeroEvenOdd = new PrintZeroEvenOdd(n);

        thread3.start();
        thread1.start();

        try {
            thread3.join();
            thread1.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        Assertions.assertEquals("", sbResult.toString());
    }


}