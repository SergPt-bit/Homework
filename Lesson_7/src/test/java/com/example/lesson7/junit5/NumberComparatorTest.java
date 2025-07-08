package com.example.lesson7.junit5;

import com.example.lesson7.NumberComparator;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class NumberComparatorTest {
    @Test
    void testComparison() {
        assertTrue(NumberComparator.areEqual(5, 5));
        assertFalse(NumberComparator.areEqual(5, 10));
        assertEquals("Второе число больше первого", NumberComparator.compareNumbers(5, 10));
        assertEquals("Числа равны", NumberComparator.compareNumbers(5, 5));
    }
}
