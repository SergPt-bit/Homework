package com.example.lesson7.testng;

import com.example.lesson7.NumberComparator;
import org.testng.annotations.Test;
import static org.testng.AssertJUnit.*;

public class NumberComparatorTest {
    @Test
    public void testComparison() {
        assertTrue(NumberComparator.areEqual(5, 5));
        assertFalse(NumberComparator.areEqual(5, 10));
        assertEquals("Второе число больше первого", NumberComparator.compareNumbers(5, 10));
        assertEquals("Числа равны", NumberComparator.compareNumbers(5, 5));
    }
}
