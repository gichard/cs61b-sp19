package synthesizer;
import org.junit.Test;
import static org.junit.Assert.*;

/** Tests the ArrayRingBuffer class.
 *  @author Josh Hug
 */

public class TestArrayRingBuffer {
    @Test
    public void someTest() {
        ArrayRingBuffer<Integer> arb = new ArrayRingBuffer<>(10);
        assertEquals(10, arb.capacity());
        assertTrue(arb.isEmpty());
        assertFalse(arb.isFull());
        assertEquals(0, arb.fillCount());

        arb.enqueue(1);
        arb.enqueue(2);
        arb.enqueue(3);
        arb.enqueue(4);
        arb.enqueue(5);
        arb.enqueue(6);
        arb.enqueue(7);
        arb.enqueue(8);
        arb.enqueue(9);
        arb.enqueue(10);

        assertTrue(arb.isFull());

        int oldest = arb.dequeue();
        assertEquals(1, oldest);
        assertEquals(9, arb.fillCount());

        assertEquals(2, (int) arb.peek());
        assertEquals(9, arb.fillCount());
    }

    @Test
    public void testIter() {
        ArrayRingBuffer<Integer> arb = new ArrayRingBuffer<>(10);
        arb.enqueue(1);
        arb.enqueue(2);
        arb.enqueue(3);
        arb.enqueue(4);
        arb.enqueue(5);
        arb.enqueue(6);
        arb.enqueue(7);
        arb.enqueue(8);
        arb.enqueue(9);
        arb.enqueue(10);

        for (Integer a : arb) {
            for (Integer b : arb) {
                System.out.println("a: " + a + ", b: " + b);
            }
        }
    }

/*    @Test
    public void testEqual() {
        ArrayRingBuffer<Integer> arb = new ArrayRingBuffer<>(10);
        arb.enqueue(1);
        arb.enqueue(2);
        arb.enqueue(3);
        arb.enqueue(4);
        arb.enqueue(5);
        arb.enqueue(6);
        arb.enqueue(7);
        arb.enqueue(8);
        arb.enqueue(9);
        arb.enqueue(10);

        ArrayRingBuffer<Integer> arb2 = new ArrayRingBuffer<>(10);
        arb2.enqueue(1);
        arb2.enqueue(2);
        arb2.enqueue(3);
        arb2.enqueue(4);
        arb2.enqueue(5);
        arb2.enqueue(6);
        arb2.enqueue(7);
        arb2.enqueue(8);
        arb2.enqueue(9);
        arb2.enqueue(10);

        boolean isEq = arb.equals(arb2);
        assertTrue(isEq);
        assertFalse(arb.equals(null));
        assertTrue(arb.equals(arb));
    }Commented because overrided equal(Object) is commented for 18summer autograder*/
}
