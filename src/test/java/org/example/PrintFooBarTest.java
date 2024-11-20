package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PrintFooBarTest {

    Thread thread1;
    Thread thread2;
    StringBuilder sbResult;
    PrintFooBar printFooBar;

    private static final long TIMEOUT = 2000; // 2 seconds of timeout for all wainting threads.
    private long startTime;


    @BeforeEach
    void setUp() {
        sbResult = new StringBuilder();

        // Lambda expression for an anonymous implementation of Runnable (a task), that runs printFoo.
        thread1 = new Thread(() -> {
            // Code to be executed by the thread (if any)
            try {
                printFooBar.printFoo(() -> sbResult.append("Foo"));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        thread2 = new Thread(() -> {
            try {
                printFooBar.printBar(() -> sbResult.append("Bar"));
            } catch (InterruptedException e) {
                throw new RuntimeException("");
            }
        });

    }

    @Test
    void print1FooBarTest() {
        int n = 1;
        printFooBar = new PrintFooBar(n);
        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        Assertions.assertEquals("FooBar", sbResult.toString());
    }

    @Test
    void print2FooBarTest() {
        int n = 2;
        printFooBar = new PrintFooBar(n);
        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        Assertions.assertEquals("FooBarFooBar", sbResult.toString());
    }

    @Test
    void print5FooBarTest() {
        int n = 5;
        printFooBar = new PrintFooBar(n);
        thread1.start();
        thread2.start();
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        Assertions.assertEquals("FooBarFooBarFooBarFooBarFooBar", sbResult.toString());
    }

    @Test
    void print0FooBarTest() {
        int n = 0;
        printFooBar = new PrintFooBar(n);
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
    void onlyPrintBarTest() {
        int n = 1;
        printFooBar = new PrintFooBar(n);

        Assertions.assertThrows(RuntimeException.class, () -> {
            startTime = System.currentTimeMillis();
            thread2.start();
            while (!thread2.isInterrupted()) {
                // Simulate some work that could run infinitely

                // Check if thread has been running too long
                if (System.currentTimeMillis() - startTime > TIMEOUT) {
                    throw new RuntimeException("Thread is running infinitely");
                }
            }
        });
    }

}