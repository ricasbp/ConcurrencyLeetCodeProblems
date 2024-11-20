package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DefaultTest {
    @Test
    void getNameTestDefault() {
        Default defaultFoo = new Default();
        String result = defaultFoo.getName(-1);
        Assertions.assertEquals("a", result);
    }
}