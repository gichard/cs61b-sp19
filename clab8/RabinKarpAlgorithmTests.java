import edu.princeton.cs.algs4.Stopwatch;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.*;

public class RabinKarpAlgorithmTests {
    @Test
    public void basic() {
        String input = "hello";
        String pattern = "ell";
        assertEquals(1, RabinKarpAlgorithm.rabinKarp(input, pattern));
    }

    @Test
    public void single() {
        String input = "hello";
        String pattern = "o";

        assertEquals(4, RabinKarpAlgorithm.rabinKarp(input, pattern));
    }

    @Test
    public void measureHashTime() {
        int[] ops = new int[]{10, 100, 1000, 10000, 100000, 1000000, 10000000};
        int[] RSLen = new int[]{10, 100, 1000};
//        int sLen = 50;
        int sSeed = 198;
        Random sRand = new Random(sSeed);
        int cSeed = 290;
        Random cRand = new Random(cSeed);
        for (int sLen: RSLen
             ) {
            for (int numOp: ops
            ) {
                RollingString testRS = new RollingString(randString(sLen, sRand), sLen);
                Stopwatch sw = new Stopwatch();
                for (int i = 0; i < numOp; i++) {
                    testRS.addChar(randChar(cRand));
                }
                System.out.println("adding " + numOp + " chars to a " + sLen + " characters long RollingString takes " + sw.elapsedTime() + " seconds");
            }
        }

    }

    private String randString(int length, Random r) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append((char) r.nextInt(RollingString.UNIQUECHARS));
        }

        return sb.toString();
    }

    private char randChar(Random rand) {
        return (char) rand.nextInt(RollingString.UNIQUECHARS);
    }
}
