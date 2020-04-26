package hw3.hash;

import org.junit.Test;
import static org.junit.Assert.*;

//import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;


public class TestSimpleOomage {

    @Test
    public void testHashCodeDeterministic() {
        SimpleOomage so = SimpleOomage.randomSimpleOomage();
        int hashCode = so.hashCode();
        for (int i = 0; i < 100; i += 1) {
            assertEquals(hashCode, so.hashCode());
        }
    }

    private int lumpRGB(int r, int g, int b) {
        return r * 52 * 52 + g * 52 + b;
    }

    private int[] fracRGB(int lump) {
        int[] rgb = new int[3];
        for (int i = 2; i >= 0; i--) {
            rgb[i] = lump % 52;
            lump = lump / 52;
        }
        return rgb;
    }

    @Test
    public void testHashCodePerfect() {
        /* Write a test that ensures the hashCode is perfect,
          meaning no two SimpleOomages should EVER have the same
          hashCode UNLESS they have the same red, blue, and green values!
         */
        SimpleOomage ooA;
        SimpleOomage ooB;
        int[] a;
        int[] b;
        for (int i = 0; i <= lumpRGB(51, 51, 51); i++) {
            a = fracRGB(i);
            ooA = new SimpleOomage(a[0] * 5, a[1] * 5, a[2] * 5);
            for (int j = i; j <= lumpRGB(51, 51, 51); j++) {
                b = fracRGB(j);
                ooB = new SimpleOomage(b[0] * 5, b[1] * 5, b[2] * 5);
                if (i == j) {
                    assertTrue(ooA.hashCode() == ooB.hashCode());
                } else {
                    assertFalse(ooA.hashCode() == ooB.hashCode());
                }
            }
        }
    }

    @Test
    public void testEquals() {
        SimpleOomage ooA = new SimpleOomage(5, 10, 20);
        SimpleOomage ooA2 = new SimpleOomage(5, 10, 20);
        SimpleOomage ooB = new SimpleOomage(50, 50, 50);
        assertEquals(ooA, ooA2);
        assertNotEquals(ooA, ooB);
        assertNotEquals(ooA2, ooB);
        assertNotEquals(ooA, "ketchup");
    }

    @Test
    public void testHashCodeAndEqualsConsistency() {
        SimpleOomage ooA = new SimpleOomage(5, 10, 20);
        SimpleOomage ooA2 = new SimpleOomage(5, 10, 20);
        HashSet<SimpleOomage> hashSet = new HashSet<>();
        hashSet.add(ooA);
        assertTrue(hashSet.contains(ooA2));
    }

    @Test
    public void testRandomOomagesHashCodeSpread() {
        List<Oomage> oomages = new ArrayList<>();
        int N = 10000;

        for (int i = 0; i < N; i += 1) {
            oomages.add(SimpleOomage.randomSimpleOomage());
        }

        assertTrue(OomageTestUtility.haveNiceHashCodeSpread(oomages, 10));
    }

    /** Calls tests for SimpleOomage. */
    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(TestSimpleOomage.class);
    }
}
