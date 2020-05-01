package bearmaps.proj2ab;

import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Map;

public class ArrayHeapMinPQ<T> implements ExtrinsicMinPQ<T> {
    private int size;
    private Node[] heap;
    private double loadFactor;
    private static final int INITSIZE = 4;
    private static final double LOADLIMIT = 0.75;
    private Map<T, Integer> elements;

    public ArrayHeapMinPQ() {
        size = 0;
        heap = new ArrayHeapMinPQ.Node[INITSIZE];
        loadFactor = 0;
        elements = new HashMap<T, Integer>();
    }

    /* Adds an item with the given priority value. Throws an
     * IllegalArgumentException if item is already present.
     * You may assume that item is never null. */
    public void add(T item, double priority) {
        if (contains(item)) {
            throw new IllegalArgumentException();
        }
        heap[size] = new Node(item, priority);
        elements.put(item, size);
        swimUp(size);
        size += 1;
        loadFactor = ((double) size) / heap.length;
        resize();
    }

    /* Returns true if the PQ contains the given item. */
    public boolean contains(T item) {
        return elements.containsKey(item);
    }

    /* Returns the minimum item. Throws NoSuchElementException if the PQ is empty. */
    public T getSmallest() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
        return heap[0].element;
    }

    /* Removes and returns the minimum item. Throws NoSuchElementException if the PQ is empty. */
    public T removeSmallest() {
        T res = heap[0].element;
        swap(0, size - 1);
        size -= 1;
        loadFactor = ((double) size) / heap.length;
        sinkDown(0);
        elements.remove(res);
        resize();
        return res;
    }

    /* Returns the number of items in the PQ. */
    public int size() {
        return size;
    }

    /* Changes the priority of the given item. Throws NoSuchElementException if the item
     * doesn't exist. */
    public void changePriority(T item, double priority) {
        if (!contains(item)) {
            throw new NoSuchElementException();
        }
        int itemIndex = get(item);
        heap[itemIndex].priority = priority;
        if (parent(itemIndex) != -1 && priority < heap[parent(itemIndex)].priority) {
            swimUp(itemIndex);
        } else {
            sinkDown(itemIndex);
        }
    }

    private int get(T item) {
        return elements.get(item);
    }

    private void swimUp(int i) {
        int parent = parent(i);
        if (parent != -1 && heap[i].priority < heap[parent].priority) {
            swap(i, parent);
            swimUp(parent);
        }
    }

    private void sinkDown(int i) {
        int lc = lChild(i);
        int rc = rChild(i);

        if (lc != -1 && rc != -1 && heap[i].priority > heap[lc].priority
                && heap[i].priority > heap[rc].priority) {
            swap(i, lc);
            sinkDown(lc);
            sinkDown(i); // in case node i's priority is greater than both of its child
        } else if (lc != -1 && heap[i].priority > heap[lc].priority) {
            swap(i, lc);
            sinkDown(lc);
        } else if (rc != -1 && heap[i].priority > heap[rc].priority) {
            swap(i, rc);
            sinkDown(rc);
        }
    }

    private void swap(int i, int j) {
        Node tmp = heap[i];
        heap[i] = heap[j];
        heap[j] = tmp;
        elements.put(heap[i].element, i);
        elements.put(heap[j].element, j);
    }

    private int parent(int i) {
        if (i == 0) {
            return -1;
        } else {
            return (i - 1) / 2;
        }
    }

    private int lChild(int i) {
        int res = 2 * i + 1;
        return res < size ? res : -1;
    }

    private int rChild(int i) {
        int res = 2 * i + 2;
        return res < size ? res : -1;
    }

    private void resize() {
        if (loadFactor >= LOADLIMIT) {
            Node[] newHeap = new ArrayHeapMinPQ.Node[heap.length * 2];
            System.arraycopy(heap, 0, newHeap, 0, size);
            this.heap = newHeap;
        } else if (loadFactor < 1 - LOADLIMIT && heap.length > INITSIZE) {
            Node[] newHeap = new ArrayHeapMinPQ.Node[heap.length / 2];
            System.arraycopy(heap, 0, newHeap, 0, size);
            this.heap = newHeap;
        }
    }

    private class Node implements Comparable<Node> {
        T element;
        double priority;

        Node(T element, double priority) {
            this.element = element;
            this.priority = priority;
        }

        @Override
        public int compareTo(Node other) {
            return Double.compare(this.priority, other.priority);
        }
    }
}
