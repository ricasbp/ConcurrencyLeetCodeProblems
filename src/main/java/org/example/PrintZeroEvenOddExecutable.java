package org.example;

import java.util.function.IntConsumer;

public class PrintZeroEvenOddExecutable {
    public static void main(String[] args) {
        int n = 3;
        PrintZeroEvenOdd printZeroEvenOdd = new PrintZeroEvenOdd(n);
        StringBuilder sbResult = new StringBuilder();

        // Lambda expression:
        IntConsumer print = value -> sbResult.append(value);

        Thread thread1 = new Thread(() -> {
            try {
                printZeroEvenOdd.zero(print);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        Thread thread2 = new Thread(() -> {
            try {
                printZeroEvenOdd.even(print);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        Thread thread3 = new Thread(() -> {
            try {
                printZeroEvenOdd.odd(print);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

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

        System.out.println(sbResult);
    }
}
