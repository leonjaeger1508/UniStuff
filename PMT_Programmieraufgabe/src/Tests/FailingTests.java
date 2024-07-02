package Tests;

import org.junit.Test;

import static org.junit.Assert.*;

import java.awt.Image;

public class FailingTests {


    @Test
    public void testAdditionFail() {
        int sum = 2 + 2;
        assertEquals(5, sum);  // Dieser Test schlägt fehl
    }

    @Test
    public void testStringNull() {
        String str = null;
        assertNotNull(str);  // Dieser Test schlägt fehl
    }

    @Test
    public void testBooleanFalse() {
        boolean isFalse = false;
        assertTrue(isFalse);  // Dieser Test schlägt fehl
    }

}
