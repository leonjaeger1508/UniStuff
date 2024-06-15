package Tests;

import org.junit.Test;
import static org.junit.Assert.*;

public class SuccessfulTests {

    @Test
    public void testAddition() {
        int sum = 2 + 3;
        assertEquals(5, sum);
    }

    @Test
    public void testStringNotNull() {
        String str = "JUnit";
        assertNotNull(str);
    }

    @Test
    public void testBooleanTrue() {
        boolean isTrue = true;
        assertTrue(isTrue);
    }
}
