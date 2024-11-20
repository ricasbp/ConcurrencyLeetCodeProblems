package org.example;

import java.util.concurrent.Callable;

public class PrintInOrder {

    private boolean oneDone;
    private boolean twoDone;

    long timeout = 5000; // Timeout in milliseconds (5 seconds)


    public PrintInOrder() {
        oneDone = false;
        twoDone = false;
    }

    public synchronized void first(Runnable printFirst) {
        printFirst.run();
        oneDone = true;
        notifyAll();
    }

    public synchronized void second(Runnable printSecond) throws InterruptedException {
        while (!oneDone) {
            wait();
        }
        printSecond.run();
        twoDone = true;
        notifyAll();
    }

    public synchronized void third(Runnable printThird) throws InterruptedException {
        while (!twoDone) {
            wait(timeout);
        }
        printThird.run();
    }
}
