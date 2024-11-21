package org.example;


import java.util.function.IntConsumer;

public class PrintZeroEvenOdd {
    private int n;

    // Solution Inspired by: https://leetcode.com/problems/print-zero-even-odd/solutions/5843661/wait-and-notify-solution/

    public PrintZeroEvenOdd(int n) {
        this.n = n;
    }

    private boolean zeroPrinted = true;
    private boolean evenPrinted = false;
    private boolean oddPrinted = false;

    // printNumber.accept(x) outputs "x", where x is an integer.
    public synchronized void zero(IntConsumer printNumber) throws InterruptedException {
        for (int i = 1; i <= n; i ++) {
            if (!zeroPrinted) {
                wait();
            }
            if (i % 2 == 0) {
                evenPrinted = true; // we want to print an even number now
                oddPrinted = false;
            } else {
                evenPrinted = false;
                oddPrinted = true; // we want to print an odd number now
            }
            printNumber.accept(0);
            zeroPrinted = false;
            notifyAll();
        }
    }

    public synchronized void even(IntConsumer printNumber) throws InterruptedException {
        for (int i = 2; i <= n; i += 2) {
            while (!evenPrinted) {
                wait();
            }
            evenPrinted = false;
            zeroPrinted = true; // we want to print a 0 now
            printNumber.accept(i);
            notifyAll();
        }
    }

    public synchronized void odd(IntConsumer printNumber) throws InterruptedException {
        for (int i = 1; i <= n; i += 2) {
            while (!oddPrinted) {
                wait();
            }
            oddPrinted = false;
            zeroPrinted = true; // we want to print a 0 now
            printNumber.accept(i);
            notifyAll();
        }
    }
}