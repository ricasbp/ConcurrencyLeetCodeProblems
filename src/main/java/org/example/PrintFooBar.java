package org.example;

public class PrintFooBar {
    private final int n;
    private boolean printedFoo;

    public PrintFooBar(int n) {
        this.n = n;
        this.printedFoo = false;
    }

    public synchronized void printFoo(Runnable printFoo) throws InterruptedException {
        for(int i = 0; i < n; i++) {
            while(printedFoo){
                wait();
            }
            printedFoo = true;
            printFoo.run();
            notifyAll();
        }
    }

    public synchronized void printBar(Runnable printBar) throws InterruptedException {
        for(int i = 0; i < n; i++) {
            while(!printedFoo){
                wait();
            }
            printedFoo = false;
            printBar.run();
            notifyAll();
        }
    }

}
