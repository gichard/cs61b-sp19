import org.junit.Test;
import static org.junit.Assert.*;

public class TestPalindrome {
    // You must use this palindrome, and not instantiate
    // new Palindromes, or the autograder might be upset.
    static Palindrome palindrome = new Palindrome();
    static CharacterComparator offByOne = new OffByOne();

    @Test
    public void testWordToDeque() {
        Deque d = palindrome.wordToDeque("persiflage");
        String actual = "";
        for (int i = 0; i < "persiflage".length(); i++) {
            actual += d.removeFirst();
        }
        assertEquals("persiflage", actual);
    }

    @Test
    public void testIsPalindrome() {
        String p1 = "a";
        assertTrue(palindrome.isPalindrome(p1));
        String p2 = "racecar";
        assertTrue(palindrome.isPalindrome(p2));
        String p3 = "noon";
        assertTrue(palindrome.isPalindrome(p3));
        String p4 = "";
        assertTrue(palindrome.isPalindrome(p4));
        String np1 = "horse";
        assertFalse(palindrome.isPalindrome(np1));
        String np2 = "rancor";
        assertFalse(palindrome.isPalindrome(np2));
        String np3 = "aaaaab";
        assertFalse(palindrome.isPalindrome(np3));

        // test overloaded isPalindrome()
        assertTrue(palindrome.isPalindrome("a", offByOne));
        assertTrue(palindrome.isPalindrome("", offByOne));
        assertTrue(palindrome.isPalindrome("flake", offByOne));
    }
}
