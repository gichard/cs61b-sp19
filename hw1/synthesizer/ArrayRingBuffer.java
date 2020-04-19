package synthesizer;
import java.util.Iterator;

/*public class ArrayRingBuffer<T> implements BoundedQueue<T> {*/
public class ArrayRingBuffer<T> extends AbstractBoundedQueue<T> {
    /* Index for the next dequeue or peek. */
    private int first;
    /* Index for the next enqueue. */
    private int last;
/*    *//* Variable for the fillCount. *//*
    private int fillCount; Commented for 18summer autograder*/
    /* Array for storing the buffer data. */
    private T[] rb;

    /**
     * Create a new ArrayRingBuffer with the given capacity.
     */
    public ArrayRingBuffer(int capacity) {
        // Create new array with capacity elements.
        // first, last, and fillCount should all be set to 0.
        rb = (T[]) new Object[capacity];
        first = 0;
        last = 0;
        super.fillCount = 0;
        super.capacity = capacity; // added for 18summer autograder
    }

    /**
     * Adds x to the end of the ring buffer. If there is no room, then
     * throw new RuntimeException("Ring buffer overflow").
     */
    @Override
    public void enqueue(T x) {
        // Enqueue the item. Don't forget to increase fillCount and update
        // last.
        if (isFull()) {
            throw new RuntimeException("Ring buffer overflow");
        } else {
            rb[last] = x;
            last = (last + 1) % rb.length;
            fillCount += 1;
        }
    }

    /**
     * Dequeue oldest item in the ring buffer. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow").
     */
    @Override
    public T dequeue() {
        // Dequeue the first item. Don't forget to decrease fillCount and
        // update first.
        if (isEmpty()) {
            throw new RuntimeException("Ring buffer underflow");
        } else {
            T result;
            result = rb[first];
            first = (first + 1) % rb.length;
            fillCount -= 1;
            return result;
        }
    }

    /**
     * Return oldest item, but don't remove it. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow").
     */
    @Override
    public T peek() {
        // Return the first item. None of your instance variables should
        // change.
        if (isEmpty()) {
            throw new RuntimeException("Ring buffer underflow");
        } else {
            return rb[first];
        }
    }

/*    // return size of the buffer
    @Override
    public int capacity() {
        return rb.length;
    }

    // return number of items currently in the buffer
    @Override
    public int fillCount() {
        return fillCount;
    }*/


    // When you get to part 4, implement the needed code to support
    // iteration and equals.
    @Override
    public Iterator<T> iterator() {
        return new ArrayRingBufferIterator();
    }

    /** reflective, transitive, commutable
     * no object equals null*/
    /*
    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        } else if (this == o) {
            return true;
        } else if (!o.getClass().getSimpleName().equals(this.getClass().getSimpleName())) {
            return false;
        } else {
            Iterator<T> thisIter = this.iterator();
            Iterator<T> otherIter = ((ArrayRingBuffer) o).iterator();
            while (thisIter.hasNext() && otherIter.hasNext()) {
                if (thisIter.next() != otherIter.next()) {
                    return false; // item not equal
                }
            }
            return !(thisIter.hasNext() || otherIter.hasNext()); // if equal length return true
        }
    } Commented for 18summer autograder*/

    private class ArrayRingBufferIterator implements Iterator<T> {
        private int current;
        private int visited;
        public ArrayRingBufferIterator() {
            current = first;
            visited = 0;
        }

        @Override
        public boolean hasNext() {
            return visited < fillCount;
        }

        @Override
        public T next() {
            if (hasNext()) {
                T result = rb[current];
                current = (current + 1) % rb.length;
                visited += 1;
                return result;
            } else {
                return null;
            }
        }
    }
}
