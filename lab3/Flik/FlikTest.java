import static org.junit.Assert.*;

import org.junit.Test;

public class FlikTest {
    @Test
    public void testEqual() {
        int i = 128;
        int j = 128;
        assertTrue(Flik.isSameNumber(i, j));
    }
}
