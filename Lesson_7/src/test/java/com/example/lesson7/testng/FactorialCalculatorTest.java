package com.example.lesson7.testng;

import com.example.lesson7.FactorialCalculator;
import org.testng.annotations.Test;
import static org.testng.AssertJUnit.*;

public class FactorialCalculatorTest {
    @Test
    public void testFactorial() {
        assertEquals(120L, FactorialCalculator.factorial(5));
        assertEquals(1L, FactorialCalculator.factorial(0));
        try {
            FactorialCalculator.factorial(-1);
            fail("Ожидалось исключение для отрицательных аргументов");
        } catch (IllegalArgumentException e) {}
    }
}
