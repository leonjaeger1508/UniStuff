package Tests;

import static org.junit.Assert.assertEquals;

import java.awt.Image;

import org.junit.BeforeClass;
import org.junit.Test;



public class ExceptionTests {

    @Test(expected = ArithmeticException.class)
    public void testDivisionByZero() {
        int result = 1 / 0;
    }

    @BeforeClass
    @Test
    public void testNoException() {
        int result = 1 / 1;
        assertEquals(1, result);
    }
}
