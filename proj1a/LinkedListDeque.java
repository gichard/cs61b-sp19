public class LinkedListDeque<T> {
    private class ItemNode {
        private T first;
        private ItemNode next;
        private ItemNode prev;

        public ItemNode() {
            next = this;
            prev = this;
        }

        public ItemNode(T f) {
            first = f;
            next = this;
            prev = this;
        }
    }

    private ItemNode sentinel;
    private int size;

    public LinkedListDeque() {
        size = 0;
        sentinel = new ItemNode();

    }

    /** 18summer requires no copy constructor*/
/*    public LinkedListDeque(LinkedListDeque other) {
        size = 0;
        sentinel = new ItemNode();
        while (!other.isEmpty()) {
            this.addLast((T) other.removeFirst());
        }
    }*/

    /** adds an item to the front of the deque */
    public void addFirst(T item) {
        ItemNode oldFirst = sentinel.next;
        sentinel.next = new ItemNode(item);
        sentinel.next.prev = sentinel;
        sentinel.next.next = oldFirst;
        oldFirst.prev = sentinel.next;
        size += 1;
    }

    /** adds an item to the last of the deque */
    public void addLast(T item) {
        ItemNode oldLast = sentinel.prev;
        sentinel.prev = new ItemNode(item);
        sentinel.prev.prev = oldLast;
        sentinel.prev.next = sentinel;
        oldLast.next = sentinel.prev;
        size += 1;
    }

    /** returns true if the deque is empty, false otherwise */
    public boolean isEmpty() {
        return size == 0;
    }

    /** return the number of items in the deque */
    public int size() {
        return this.size;
    }

    /** prints the items in the deque from first to last,
     * separated by a space. Once all the elements have been
      printed, print out a new line */
    public void printDeque() {
        ItemNode ptr = sentinel;
        for (int i = 0; i < size; i++) {
            ptr = ptr.next;
            System.out.print(ptr.first);
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
        ItemNode firstNode = null;
        if (size > 0) {
            firstNode = sentinel.next;
            sentinel.next = firstNode.next;
            firstNode.next.prev = sentinel;
            size -= 1;
            return  firstNode.first;
        } else {
            return null;
        }
    }

    /** removes and returns the item at the back of the deque.
     * If no such item exists, returns null*/
    public T removeLast() {
        ItemNode lastNode = null;
        if (size > 0) {
            lastNode = sentinel.prev;
            sentinel.prev = lastNode.prev;
            lastNode.prev.next = sentinel;
            size -= 1;
            return lastNode.first;
        } else {
            return null;
        }
    }

    /** Gets the item at the given index, where 0 is the front, 1 is
     * the next item, and so forth. If no such item exists, returns
     * null. Must Not alter the deque! */
    public T get(int index) {
        if (index >= size || index < 0) {
            return null;
        } else {
            ItemNode ptr = sentinel;
            if (index <= size / 2) {
                for (int i = 0; i <= index; i++) {
                    ptr = ptr.next;
                }
            } else {
                for (int i = 0; i < size - index; i++) {
                    ptr = ptr.prev;
                }
            }

            return ptr.first;
        }
    }

    private T getRecHelper(ItemNode head, int index) {
        if (index == 0) {
            return head.first;
        } else {
            return getRecHelper(head.next, index - 1);
        }
    }

    /** same as get, except using recursion*/
    public T getRecursive(int index) {
        if (index >= size || index < 0) {
            return null;
        } else {
            return getRecHelper(sentinel.next, index);
        }
    }
}
