package com.example.lesson7.junit5;

import com.example.lesson7.FactorialCalculator;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FactorialCalculatorTest {
    @Test
    void testFactorial() {
        assertEquals(120, FactorialCalculator.factorial(5));
        assertEquals(1, FactorialCalculator.factorial(0));
        assertThrows(IllegalArgumentException.class, () -> FactorialCalculator.factorial(-1));
    }
}
