package bearmaps;

import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.*;

public class ArrayHeapMinPQTest {

    @Test
    public void testSanity() {
        ArrayHeapMinPQ<String> ahpq = new ArrayHeapMinPQ<>();
    }

    @Test
    public void testContains() {
        ArrayHeapMinPQ<String> ahpq = new ArrayHeapMinPQ<>();
        ahpq.add("a", 0.1);
        ahpq.add("b", 0.2);
        ahpq.add("c", 0.3);
        ahpq.add("d", 0.7);
        ahpq.add("e", 0.5);
        assertEquals(5, ahpq.size());
        assertTrue(ahpq.contains("a"));
        assertTrue(ahpq.contains("b"));
        assertTrue(ahpq.contains("c"));
        assertTrue(ahpq.contains("d"));
        assertTrue(ahpq.contains("e"));
    }

    @Test
    public void testRetrievals() {
        ArrayHeapMinPQ<String> ahpq = new ArrayHeapMinPQ<>();
        ahpq.add("a", 0.1);
        ahpq.add("b", 0.2);
        ahpq.add("c", 0.3);
        ahpq.add("d", 0.7);
        ahpq.add("e", 0.5);
        assertEquals("a", ahpq.removeSmallest());
        assertEquals(4, ahpq.size());
        assertEquals("b", ahpq.getSmallest());
        assertEquals(4, ahpq.size());
        assertEquals("b", ahpq.removeSmallest());
        assertEquals("c", ahpq.removeSmallest());
        assertEquals("e", ahpq.removeSmallest());
        assertEquals("d", ahpq.removeSmallest());
    }

    @Test
    public void testChange() {
        ArrayHeapMinPQ<String> ahpq = new ArrayHeapMinPQ<>();
        ahpq.add("a", 0.1);
        ahpq.add("b", 0.2);
        ahpq.add("c", 0.3);
        ahpq.add("d", 0.7);
        ahpq.add("e", 0.5);
        ahpq.changePriority("e", 0);
        assertEquals("e", ahpq.removeSmallest());
        assertEquals("a", ahpq.getSmallest());
        ahpq.changePriority("a", 1);
        assertEquals("b", ahpq.getSmallest());
    }

    @Test
    public void testRandomAdd() {
        int numOp = 4000000;
        ArrayHeapMinPQ<Double> ahpq = new ArrayHeapMinPQ<>();
        NaiveMinPQ<Double> nmpq = new NaiveMinPQ<>();
        Random itemGen = new Random(1);
        Random priorityGen = new Random(2);

        long start = System.currentTimeMillis();
        for (int i = 0; i < numOp; i++) {
            ahpq.add(itemGen.nextDouble(), priorityGen.nextDouble());
        }
        long end = System.currentTimeMillis();
        System.out.println("Total time elapsed for " + numOp + " ArrayHeapMinPQ.add operations "
                + (end - start) / 1000.0 +  " seconds.");

        itemGen = new Random(1);
        priorityGen = new Random(2);
        start = System.currentTimeMillis();
        for (int i = 0; i < numOp; i++) {
            nmpq.add(itemGen.nextDouble(), priorityGen.nextDouble());
        }
        end = System.currentTimeMillis();
        System.out.println("Total time elapsed for " + numOp + " NaiveMinPQ.add operations "
                + (end - start) / 1000.0 +  " seconds.");
    }

    @Test
    public void testRandomContains() {
        int numOp = 200000;
        int size = 10000;
        ArrayHeapMinPQ<Double> ahpq = new ArrayHeapMinPQ<>();
        NaiveMinPQ<Double> nmpq = new NaiveMinPQ<>();

        Random itemGen = new Random(1);
        Random priorityGen = new Random(2);
        Random indexGen = new Random(3);
        Double[] items = new Double[size];
        Double[] priorities = new Double[size];
        for (int i = 0; i < size; i++) {
            items[i] = itemGen.nextDouble();
            priorities[i] = priorityGen.nextDouble();
            ahpq.add(items[i], priorities[i]);
            nmpq.add(items[i], priorities[i]);
        }

        long start = System.currentTimeMillis();
        for (int i = 0; i < numOp; i++) {
            ahpq.contains(items[indexGen.nextInt(size)]);
        }
        long end = System.currentTimeMillis();
        System.out.println("Total time elapsed for " + numOp
                + " ArrayHeapMinPQ.contains operations "
                + (end - start) / 1000.0 +  " seconds.");

        indexGen = new Random(3);
        start = System.currentTimeMillis();
        for (int i = 0; i < numOp; i++) {
            nmpq.contains(items[indexGen.nextInt(size)]);
        }
        end = System.currentTimeMillis();
        System.out.println("Total time elapsed for " + numOp
                + " NaiveMinPQ.contains operations "
                + (end - start) / 1000.0 +  " seconds.");
    }

    @Test
    public void testRandomGetSmallest() {
        int numOp = 16000;
        int size = 20000;
        ArrayHeapMinPQ<Double> ahpq = new ArrayHeapMinPQ<>();
        NaiveMinPQ<Double> nmpq = new NaiveMinPQ<>();

        Random itemGen = new Random(1);
        Random priorityGen = new Random(2);
        Double[] items = new Double[size];
        Double[] priorities = new Double[size];
        for (int i = 0; i < size; i++) {
            items[i] = itemGen.nextDouble();
            priorities[i] = priorityGen.nextDouble();
            ahpq.add(items[i], priorities[i]);
            nmpq.add(items[i], priorities[i]);
        }

        long start = System.currentTimeMillis();
        for (int i = 0; i < numOp; i++) {
            ahpq.getSmallest();
        }
        long end = System.currentTimeMillis();
        System.out.println("Total time elapsed for " + numOp
                + " ArrayHeapMinPQ.getSmallest operations "
                + (end - start) / 1000.0 +  " seconds.");

        start = System.currentTimeMillis();
        for (int i = 0; i < numOp; i++) {
            nmpq.getSmallest();
        }
        end = System.currentTimeMillis();
        System.out.println("Total time elapsed for " + numOp
                + " NaiveMinPQ.getSmallest operations "
                + (end - start) / 1000.0 +  " seconds.");
    }

    @Test
    public void testRandomRemoveSmallest() {
        int numOp = 8000;
        int size = 20000;
        ArrayHeapMinPQ<Double> ahpq = new ArrayHeapMinPQ<>();
        NaiveMinPQ<Double> nmpq = new NaiveMinPQ<>();

        Random itemGen = new Random(1);
        Random priorityGen = new Random(2);
        Double[] items = new Double[size];
        Double[] priorities = new Double[size];
        for (int i = 0; i < size; i++) {
            items[i] = itemGen.nextDouble();
            priorities[i] = priorityGen.nextDouble();
            ahpq.add(items[i], priorities[i]);
            nmpq.add(items[i], priorities[i]);
        }

        long start = System.currentTimeMillis();
        for (int i = 0; i < numOp; i++) {
            ahpq.removeSmallest();
        }
        long end = System.currentTimeMillis();
        System.out.println("Total time elapsed for " + numOp
                + " ArrayHeapMinPQ.removeSmallest operations "
                + (end - start) / 1000.0 +  " seconds.");

        start = System.currentTimeMillis();
        for (int i = 0; i < numOp; i++) {
            nmpq.removeSmallest();
        }
        end = System.currentTimeMillis();
        System.out.println("Total time elapsed for " + numOp
                + " NaiveMinPQ.removeSmallest operations "
                + (end - start) / 1000.0 +  " seconds.");
    }
}
