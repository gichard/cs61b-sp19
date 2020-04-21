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
        return root.get(key) != null;
    }

    @Override
    public V get(K key){
        if (root == null) {
            return null;
        }
        BSTMap.Entry lookup = root.get(key);
        if (lookup == null) {
            return null;
        }
        return (V) lookup.val;
    }

    @Override
    public int size(){
        return size;
    }

    @Override
    public void put(K key, V value){
        Entry newN = new Entry(key, value);
        if (root == null) {
            root = newN;
            size += 1;
        }else {
            root.insert(newN);
        }
    }

    private class Entry {
        private K key;
        private V val;
        private Entry left;
        private Entry right;

        public Entry(K k, V v){
            key = k;
            val = v;
            left = null;
            right = null;
        }

        public Entry get(K k) {
            if (k != null && k.equals(this.key)){
                return this;
            } else if(k == null) {
                return null;
            } else if (k.compareTo(this.key) < 0){
                return this.left.get(k);
            } else {
                return this.right.get(k);
            }
        }

        private Entry insertHlper(Entry e, Entry node) {
            if (node == null) {
                size += 1;
                return e;
            } else {
                int cmp = e.key.compareTo(node.key);
                if (cmp < 0) {
                    node.left = insertHlper(e, node.left);
                } else if (cmp > 0) {
                    node.right = insertHlper(e, node.right);
                }
                return node;
            }
        }

        public void insert(Entry e) {
            insertHlper(e, root);
        }
    }

    // following methods are not supported
    @Override
    public Set<K> keySet(){
        throw new UnsupportedOperationException();
    }

    @Override
    public V remove(K key){
        throw new UnsupportedOperationException();
    }

    @Override
    public V remove(K key, V value){
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<K> iterator() {
        throw new UnsupportedOperationException();
    }
    // previous 4 methods are not supported
}
