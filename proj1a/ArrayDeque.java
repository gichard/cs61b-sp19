public class ArrayDeque<T> {
    private int size;
    private T[] items;
    private int nextFirst;
    private int nextLast;

    public ArrayDeque() {
        items = (T[]) new Object[8];
        size = 0;
        nextFirst = 0;
        nextLast = 1;
    }

/*    public ArrayDeque(ArrayDeque other) {
        size = other.size();
        items = (T[]) new Object[size];
        for (int i = 0; i < size; i++) {
            items[i] = (T) other.get(i);
        }
        nextFirst = items.length - 1;
        nextLast = size;
    }*/

    private void resize(boolean upSize) {
        T[] newItems;
        if (upSize) {
            newItems = (T[]) new Object[items.length * 2];
        } else {
            newItems = (T[]) new Object[items.length / 2];
        }
        for (int i = 0; i < size; i++) {
            newItems[i] = this.get(i);
        }
        items = newItems;
        nextFirst = items.length - 1;
        nextLast = size;
    }

    /** adds an item to the front of the deque */
    public void addFirst(T item) {
        if (size == items.length) {
            resize(true);
        }
        items[nextFirst] = item;
        nextFirst -= 1;
        if (nextFirst < 0) {
            nextFirst += items.length;
        }
        size += 1;
    }

    /** adds an item to the last of the deque */
    public void addLast(T item) {
        if (size == items.length) {
            resize(true);
        }
        items[nextLast] = item;
        nextLast += 1;
        if (nextLast >= items.length) {
            nextLast -= items.length;
        }
        size += 1;
    }

    /** returns true if the deque is empty, false otherwise */
    public boolean isEmpty() {
        return size == 0;
    }

    /** return the number of items in the deque */
    public int size() {
        return size;
    }

    /** prints the items in the deque from first to last,
     * separated by a space. Once all the elements have been
     printed, print out a new line */
    public void printDeque() {
        for (int i = 0; i < size; i++) {
            System.out.print(this.get(i));
            if (i == size - 1) {
                System.out.println();
            } else {
                System.out.print(' ');
            }
        }
    }

    /** removes and returns the item at the front of the deque.
     * if no such item exists, returns null*/
    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        T firstItem = this.get(0);
        size -= 1;
        nextFirst += 1;
        if (nextFirst >= items.length) {
            nextFirst -= items.length;
        }
        if (size * 4 < items.length && items.length > 8) {
            resize(false);
        }
        return firstItem;
    }

    /** removes and returns the item at the back of the deque.
     * If no such item exists, returns null*/
    public T removeLast() {
        if (size == 0) {
            return null;
        }
        T lastItem = this.get(size - 1);
        size -= 1;
        nextLast -= 1;
        if (nextLast < 0) {
            nextLast += items.length;
        }
        if (size * 4 < items.length && items.length > 8) {
            resize(false);
        }
        return lastItem;
    }

    /** Gets the item at the given index, where 0 is the front, 1 is
     * the next item, and so forth. If no such item exists, returns
     * null. Must Not alter the deque! */
    public T get(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        int realIndex = (nextFirst + 1 + index) % items.length;
        return items[realIndex];
    }
}
