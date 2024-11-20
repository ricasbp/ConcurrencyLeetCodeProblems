package org.example;

public class ExecuteBuildingH2O {

    public static void main(String[] args) {
        // Diogo: Achas que eles se vao importar se eu perguntar se faço coding da minha solution antes do testing?

        BuildingH2O h2o = new BuildingH2O();

        StringBuilder sbResult = new StringBuilder();

        Thread thread1 = new Thread(() -> {
            try {
                h2o.hydrogen(() ->  sbResult.append("H"));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        Thread thread2 = new Thread(() -> {
            try {
                h2o.hydrogen(() ->  sbResult.append("H"));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        Thread thread3 = new Thread(() -> {
            try {
                h2o.oxygen(() ->  sbResult.append("O"));
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

        System.out.println(sbResult.toString());

    }
}
