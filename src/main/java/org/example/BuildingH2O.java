package org.example;

import java.util.concurrent.Semaphore;

public class BuildingH2O {

    Semaphore semH, semO;

    public BuildingH2O() {
        semH = new Semaphore(2, true);  // fairness. In a fair semaphore, threads acquire permits in the order they request them (FIFO).
        semO = new Semaphore(0, true);
    }

    public void hydrogen(Runnable releaseHydrogen) throws InterruptedException {
        semH.acquire();
        releaseHydrogen.run();
        semO.release();
    }


    public void oxygen(Runnable releaseOxygen) throws InterruptedException {
        semO.acquire(2);
        releaseOxygen.run();
        semH.release(2);
    }
}