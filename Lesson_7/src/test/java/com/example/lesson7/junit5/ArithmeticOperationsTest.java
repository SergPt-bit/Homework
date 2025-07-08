package com.example.lesson7.junit5;

import com.example.lesson7.ArithmeticOperations;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ArithmeticOperationsTest {
    @Test
    void testOperations() {
        assertEquals(8, ArithmeticOperations.add(3, 5));
        assertEquals(-2, ArithmeticOperations.subtract(3, 5));
        assertEquals(15, ArithmeticOperations.multiply(3, 5));
        assertEquals(2, ArithmeticOperations.divide(10, 5));
        assertThrows(ArithmeticException.class, () -> ArithmeticOperations.divide(10, 0));
    }
}
