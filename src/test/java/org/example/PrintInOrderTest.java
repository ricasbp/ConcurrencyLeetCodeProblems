package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PrintInOrderTest {
    // Notes:
    // Haven't read a lot about testing Multi-Threading programs, and still learning about this type of tests.

    // Documentation:
    // How to write simple JUnit Tests: https://www.youtube.com/watch?v=vZm0lHciFsQ&ab_channel=CodingwithJohn
    // How to @Before setUp JUnit Tests: https://www.youtube.com/watch?v=en0qCTQ25oM&ab_channel=PaulGestwicki

    Thread thread1;
    Thread thread2;
    Thread thread3;
    StringBuilder output;

    @BeforeEach
    public void setUp() {
        PrintInOrder printInOrder = new PrintInOrder();

        // Create a StringBuilder to capture the output order
        this.output = new StringBuilder();

        // Create all the runnable's
        Runnable printFirst = () -> output.append("first");
        Runnable printSecond = () -> output.append("second");
        Runnable printThird = () -> output.append("third");

        // Create threads to run the corresponding method in the same
        //        instance of PrintInOrder
        this.thread1 = new Thread(() -> {
            try {
                printInOrder.first(printFirst);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        this.thread2 = new Thread(() -> {
            try {
                printInOrder.second(printSecond);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        this.thread3 = new Thread(() -> {
            try {
                printInOrder.third(printThird);
            } catch (InterruptedException e) {
                System.out.println("Caught InterruptedException");
                e.printStackTrace();
            }
        });
    }

    @Test
    void printInOrder123Test() {
        // Start threads in the specified test order
        thread1.start();
        thread2.start();
        thread3.start();

        // Wait for all threads to finish
        try {
            thread1.join();
            thread2.join();
            thread3.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        Assertions.assertEquals("firstsecondthird", output.toString());
    }

    @Test
    void printInOrder231Test() {
        // Start threads in the specified test order
        thread2.start();
        thread3.start();
        thread1.start();

        // Wait for all threads to finish
        try {
            thread1.join();
            thread2.join();
            thread3.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        Assertions.assertEquals("firstsecondthird", output.toString());
    }

    @Test
    void printInOrderEmptyStringTest() {
        Assertions.assertEquals("", output.toString());
    }

    @Test
    void printInOrder321Test() {
        // Start threads in the specified test order
        thread3.start();
        thread2.start();
        thread1.start();

        // Wait for all threads to finish
        try {
            thread1.join();
            thread2.join();
            thread3.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        Assertions.assertEquals("firstsecondthird", output.toString());
    }

    @Test
    void printInOrder21Test() {
        thread2.start();
        thread1.start();

        // Wait for all threads to finish
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        Assertions.assertNotEquals("firstsecondthird", output.toString());
    }

    @Test
    void printInOrder3Test() {
        // How to make Junit Throw Exceptions: https://www.youtube.com/watch?v=Q29PFZhErUU&ab_channel=JavaBrains
        thread3.start();

        InterruptedException e1 = null;
        // Wait for all threads to finish
        try {
            thread3.join();
        } catch (InterruptedException e) {
            System.out.println("Caught InterruptedException");
            e1 = e;
        }

        assert e1 != null;
        Assertions.assertEquals(InterruptedException.class, e1.getClass());
    }
}