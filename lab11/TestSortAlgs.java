import edu.princeton.cs.algs4.Queue;

import org.junit.Test;
import static org.junit.Assert.*;

public class TestSortAlgs {

    @Test
    public void testQuickSort() {
        Queue<String> tas = new Queue<>();
        tas.enqueue("Joe");
        tas.enqueue("Omar");
        tas.enqueue("Itai");

        Queue<String> actual = QuickSort.quickSort(tas);
        assertTrue(isSorted(actual));
    }

    @Test
    public void testMergeSort() {
        Queue<String> tas = new Queue<>();
        tas.enqueue("Joe");
        tas.enqueue("Omar");
        tas.enqueue("Itai");

        Queue<String> actual = MergeSort.mergeSort(tas);
        assertTrue(isSorted(actual));
    }

    /**
     * Returns whether a Queue is sorted or not.
     *
     * @param items  A Queue of items
     * @return       true/false - whether "items" is sorted
     */
    private <Item extends Comparable> boolean isSorted(Queue<Item> items) {
        if (items.size() <= 1) {
            return true;
        }
        Item curr = items.dequeue();
        Item prev = curr;
        while (!items.isEmpty()) {
            prev = curr;
            curr = items.dequeue();
            if (curr.compareTo(prev) < 0) {
                return false;
            }
        }
        return true;
    }
}
