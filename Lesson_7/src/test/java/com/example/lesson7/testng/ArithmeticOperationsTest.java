package com.example.lesson7.testng;

import com.example.lesson7.ArithmeticOperations;
import org.testng.annotations.Test;
import static org.testng.AssertJUnit.*;

public class ArithmeticOperationsTest {
    @Test
    public void testOperations() {
        assertEquals(8, ArithmeticOperations.add(3, 5));
        assertEquals(-2, ArithmeticOperations.subtract(3, 5));
        assertEquals(15, ArithmeticOperations.multiply(3, 5));
        assertEquals(2, ArithmeticOperations.divide(10, 5));
        try {
            ArithmeticOperations.divide(10, 0);
            fail("Ожидалось исключение при делении на ноль");
        } catch (ArithmeticException e) {}
    }
}
