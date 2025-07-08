package com.example.lesson7.testng;

import com.example.lesson7.TriangleAreaCalculator;
import org.testng.annotations.Test;
import static org.testng.AssertJUnit.assertEquals;

public class TriangleAreaCalculatorTest {
    @Test
    public void testCalculateTriangleArea() {
        double area = TriangleAreaCalculator.calculateTriangleArea(3, 4, 5);
        assertEquals(6.0, area, 0.001);
    }
}
