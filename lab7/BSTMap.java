import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

public class BSTMap<K extends Comparable, V> implements Map61B<K, V> {

    private int size;
    private Entry root;

    public BSTMap() {
        size = 0;
        root = null;
    }

    public BSTMap(K k, V v) {
        size = 1;
        root = new Entry(k, v);
    }

    @Override
    public void clear() {
        size = 0;
        root = null;
    }

    @Override
    public boolean containsKey(K key) {
        if (root == null) {
            return false;
        }
        return get(key) != null;
    }

    private Entry getHelper(K key, Entry node) {
        if (node == null) {
            return null;
        } else {
            if (key != null && key.equals(node.key)){
                return node;
            } else if(key == null) {
                return null;
            } else if (key.compareTo(node.key) < 0){
                return getHelper(key, node.left);
            } else {
                return getHelper(key, node.right);
            }
        }
    }

    @Override
    public V get(K key){
        Entry res = getHelper(key, root);
        return res==null?null:res.val;
    }

    @Override
    public int size(){
        return size;
    }

    private Entry putHelper(Entry newEntry, Entry node) {
        if (node == null) {
            size += 1;
            return newEntry;
        } else {
            int cmp = newEntry.key.compareTo(node.key);
            if (cmp < 0) {
                node.left = putHelper(newEntry, node.left);
                node.left.parent = node;
            } else if (cmp > 0) {
                node.right = putHelper(newEntry, node.right);
                node.right.parent = node;
            }
            return node;
        }
    }

    @Override
    public void put(K key, V value){
        Entry newN = new Entry(key, value);
        root = putHelper(newN, root);
    }

    private class Entry /*implements Iterable*/{
        K key;
        V val;
        Entry left;
        Entry right;
        Entry parent;

        public Entry(K k, V v){
            key = k;
            val = v;
            left = null;
            right = null;
            parent = null;
        }

        public boolean isLeaf() {
            return (this.left == null) && (this.right == null);
        }

        public boolean isLChild() {
            return this.parent != null && this == this.parent.left;
        }

        public boolean isRChild() {
            return this.parent != null && this == this.parent.right;
        }
    }

    private class BSTMapIterator implements Iterator<K> {
        // iterate in mid-order: left -> parent -> child
        private Entry ptr;

        private Entry midOrderFirst(Entry node) {
            if (node.isLeaf()) {
                return node;
            } else if (node.left != null) {
                return midOrderFirst(node.left);
            } else {
                return midOrderFirst(node.right);
            }
        }

        private Entry nextAncestor(Entry node) {
            if (node.isLChild()) {
                return node.parent;
            } else if (node.parent == null) {
                return null;
            } else {
                return nextAncestor(node.parent);
            }
        }

        public BSTMapIterator() {
            ptr = midOrderFirst(root);
        }

        @Override
        public boolean hasNext(){
            return ptr != null;
        }

        @Override
        public K next(){
            K result = (ptr == null ? null : ptr.key);
            // update ptr
            if (ptr.isLeaf() || ptr.right == null) {
                ptr = nextAncestor(ptr);
            } else {
                ptr = ptr.right;
            }

            return result;
        }
    }

    private class BSTSet implements Set<K> {
        @Override
        public int size(){
            return size;
        }

        @Override
        public void clear(){
            BSTMap.this.clear();
        }

        @Override
        public boolean isEmpty() {
            return size == 0;
        }

        @Override
        public boolean contains(Object o){
            return containsKey((K) o);
        }

        @Override
        public Iterator iterator() {
            return new BSTMapIterator();
        }

        @Override
        public Object[] toArray(){
            Object[] result = new Object[size];
            Iterator<K> iter = iterator();
            for (int i = 0; i < size; i++) {
                result[i] = iter.next();
            }

            return result;
        }

        @Override
        public <T> T[] toArray(T[] a) {
            if (a.length < size) {
                a = (T[]) new Object[size];
            }
            Iterator<K> iter = iterator();
            for (int i = 0; i < size; i++) {
                a[i] = (T) iter.next();
            }
            return a;
        }

        @Override
        public boolean add(K k) {
            if (contains(k)) {
                return false;
            } else {
                put(k, null);
                return true;
            }
        }

        @Override
        public boolean addAll(Collection<? extends K> c){
            if (containsAll(c)) {
                return false;
            }
            for (Object o: c) {
                add((K) o);
            }
            return true;
        }

        @Override
        public boolean remove(Object o){
            return BSTMap.this.remove((K) o) != null;
        }

        @Override
        public boolean removeAll(Collection<?> c){
            boolean result = true;
            for(Object e : c) {
                result = remove(e);
            }
            return result;
        }

        @Override
        public boolean containsAll(Collection<?> c) {
            for (Object element : c) {
                if (!contains(element)) {
                    return false;
                }
            }
            return true;
        }

        @Override
        public boolean retainAll(Collection<?> c){
            boolean result = false;
            for (K k : this) {
                if (!c.contains(k)) {
                    remove(k);
                    result = true;
                }
            }
            return result;
        }
    }

    @Override
    public Set<K> keySet(){
//        throw new UnsupportedOperationException();
        return new BSTSet();
    }

    private void removeHlp(Entry ptr) {
        if (ptr.isLeaf()) {
            if (ptr.isLChild()) {
                ptr.parent.left = null;
            } else if (ptr.isRChild()){
                ptr.parent.right = null;
            } else { // is root
                root = null;
            }
        } else if (ptr.left != null) {
            ptr.key = ptr.left.key;
            ptr.val = ptr.left.val;
            removeHlp(ptr.left);
        } else {
            ptr.key = ptr.right.key;
            ptr.val = ptr.right.val;
            removeHlp(ptr.right);
        }
    }

    @Override
    public V remove(K key){
//        throw new UnsupportedOperationException();
        Entry ptr = getHelper(key, root);
        if (ptr == null) {
            return null;
        }
        V resVal = ptr.val;
        removeHlp(ptr);
        size -= 1;

        return resVal;
    }

    @Override
    public V remove(K key, V value){
//        throw new UnsupportedOperationException();
        Entry ptr = getHelper(key, root);
        if (ptr == null || ptr.val != value) {
            return null;
        }
        removeHlp(ptr);
        size -= 1;

        return value;
    }

    @Override
    public Iterator<K> iterator() {
        return new BSTMapIterator();
    }
}
