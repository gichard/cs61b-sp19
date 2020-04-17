import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffByOne {
    // You must use this CharacterComparator and not instantiate
    // new ones, or the autograder might be upset.
    static CharacterComparator offByOne = new OffByOne();

    // Your tests go here.
    @Test
    public void testEqualChars() {
        char x = 'a';
        char y = 'b';
        assertTrue(offByOne.equalChars(x, y));
        assertFalse(offByOne.equalChars(x, 'a'));
        assertFalse(offByOne.equalChars(y, 'b'));
        assertTrue(offByOne.equalChars(y, x));
        assertTrue(offByOne.equalChars('&', '%'));
    }
}