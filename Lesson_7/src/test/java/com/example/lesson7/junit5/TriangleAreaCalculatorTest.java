package com.example.lesson7.junit5;

import com.example.lesson7.TriangleAreaCalculator;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TriangleAreaCalculatorTest {
    @Test
    void testCalculateTriangleArea() {
        double area = TriangleAreaCalculator.calculateTriangleArea(3, 4, 5);
        assertEquals(6.0, area, 0.001);
    }
}
