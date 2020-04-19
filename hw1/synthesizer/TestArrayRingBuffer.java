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
}
