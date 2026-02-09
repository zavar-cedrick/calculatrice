package com.calculatrice;

import org.junit.Test;
import static org.junit.Assert.*;

public class CalculatriceTest {
    
    private Calculatrice calc = new Calculatrice();
    
    @Test
    public void testAdd() {
        assertEquals(15, calc.add(10, 5));
        assertEquals(0, calc.add(-5, 5));
    }
    
    @Test
    public void testSubtract() {
        assertEquals(5, calc.subtract(10, 5));
        assertEquals(-10, calc.subtract(5, 15));
    }
    
    @Test
    public void testMultiply() {
        assertEquals(50, calc.multiply(10, 5));
        assertEquals(-50, calc.multiply(10, -5));
    }
    
    @Test
    public void testDivide() {
        assertEquals(2.0, calc.divide(10, 5), 0.01);
        assertEquals(0.5, calc.divide(5, 10), 0.01);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testDivideByZero() {
        calc.divide(10, 0);
    }
}
