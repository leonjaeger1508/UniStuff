package Tests;

import org.junit.Ignore;
import org.junit.Test;
import static org.junit.Assert.*;

public class IgnoredTests {

    @Test
    @Ignore("Dieser Test wird ignoriert")
    public void testIgnored() {
        int sum = 1 + 1;
        assertEquals(2, sum);
    }

    @Test
    public void testNotIgnored() {
        String str = "Hello, World!";
        assertEquals("Hello, World!", str);
    }
}
