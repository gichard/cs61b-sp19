import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

public class MyHashMap<K, V> implements Map61B<K, V> {
    private static final int INITSIZE = 16;
    private static final double LOADFACTOR = 0.75;
    private Entry<K, V>[] buckets;
    private int bSize;
    private double loadFactor;
    private int load;

    public MyHashMap() {
        this(INITSIZE, LOADFACTOR);
    }

    public MyHashMap(int initialSize) {
        this(initialSize, LOADFACTOR);
    }

    public MyHashMap(int initialSize, double loadFactor) {

        buckets = (Entry<K, V>[]) new Entry[initialSize];
        this.loadFactor = loadFactor;
        load = 0;
        bSize = initialSize;
    }

    private class Entry<Kt, Vt> {
        private Kt key;
        private Vt value;
        private int length;
        private Entry<Kt, Vt> next;

        public Entry(Kt key, Vt value, Entry<Kt, Vt> next) {
            this.key = key;
            this.value = value;
            this.next = next;
            length = 1;
            if (next != null) {
                length += next.length();
            }
        }

        public Entry(Kt key, Vt value) {
            this(key, value, null);
        }

        public Entry() {
            length = 0;
        }

        public int length() {
            return length;
        }

        public Kt key() {
            return key;
        }

        public Vt value() {
            return value;
        }

        private Entry<Kt, Vt> deleteHlp(Kt key) {
            length -= 1;
            if (this.key.equals(key)) {
                return next;
            } else {
                this.next = next.deleteHlp(key);
                return this;
            }
        }

        public void delete(Kt key) {
            deleteHlp(key);
        }
    }

    private int keyHash(K key) {
        // from https://algs4.cs.princeton.edu/34hash/SeparateChainingHashST.java.html
        return (key.hashCode() & 0x7fffffff) % bSize;
    }

    /** Removes all of the mappings from this map. */
    public void clear() {
        load = 0;
        for (int i = 0; i < bSize; i++) {
            buckets[i] = null;
        }
    }

    /** Returns true if this map contains a mapping for the specified key. */
    public boolean containsKey(K key) {
        return get(key) != null;
    }

    /**
     * Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     */
    public V get(K key) {
        Entry<K, V> chain = buckets[keyHash(key)];
        while (chain != null) {
            if (chain.key.equals(key)) {
                return chain.value;
            }
            chain = chain.next;
        }
        return null;
    }

    /** Returns the number of key-value mappings in this map. */
    public int size(){
        return load;
    }

    /**
     * Associates the specified value with the specified key in this map.
     * If the map previously contained a mapping for the key,
     * the old value is replaced.
     */
    public void put(K key, V value) {
        Entry<K, V> chain = buckets[keyHash(key)];
        if (!containsKey(key)) {
            Entry<K, V> newEntry = new Entry<>(key, value, chain);
            buckets[keyHash(key)] = newEntry;
            load += 1;
        } else {
            while (chain != null) {
                if (chain.key.equals(key)) {
                    chain.value = value;
                    break;
                }
                chain = chain.next;
            }
        }

        resize();
    }

    private void resize() {
        if (((double) load) / bSize >= loadFactor) {
            MyHashMap<K, V> newHT = new MyHashMap<>(bSize * 2, loadFactor);
            for (K origKey: this
                 ) {
                newHT.put(origKey, this.get(origKey));
            }
            this.buckets = newHT.getBucket();
            this.bSize = bSize * 2;
            newHT = null;
        }
    }

    public Entry<K, V>[] getBucket() {
        return buckets;
    }

    /** Returns a Set view of the keys contained in this map. */
    public Set<K> keySet() {
        Set<K> res = new HashSet<>();
        for (K k: this){
            res.add(k);
        }
        return res;
    }

    public Iterator<K> iterator() {
        return new myHTIterator();
    }

    private class myHTIterator implements Iterator<K> {
        private int visited;
        private Entry<K, V> chainIterator;
        private int chainRemain;
        private int chain;

        public myHTIterator() {
            visited = 0;
            chain = 0;
            chainIterator = buckets[chain];
            chainRemain = buckets[chain] == null ? 0: buckets[chain].length();
        }

        public boolean hasNext() {
            return visited < load;
        }

        public K next() {
            while (chainRemain == 0) {
                nextChain();
            }

            visited += 1;
            chainRemain -= 1;
            K nextKey = chainIterator.key();
            chainIterator = chainIterator.next;
            return nextKey;

        }

        private void nextChain() {
            chain += 1;
            chainIterator = buckets[chain];
            chainRemain = buckets[chain] == null ? 0: buckets[chain].length();
        }
    }

    /**
     * Removes the mapping for the specified key from this map if present.
     * Not required for Lab 8. If you don't implement this, throw an
     * UnsupportedOperationException.
     */
    public V remove(K key) {
        V res = get(key);
        if (res == null) {
            return null;
        }
        Entry<K, V> chain = buckets[keyHash(key)];
        chain.delete(key);
        if (chain.key().equals(key)) {
            buckets[keyHash(key)] = chain.next;
        }
        return res;
    }

    /**
     * Removes the entry for the specified key only if it is currently mapped to
     * the specified value. Not required for Lab 8. If you don't implement this,
     * throw an UnsupportedOperationException.
     */
    public V remove(K key, V value) {
        V res = get(key);
        if (res == null || !res.equals(value)) {
            return null;
        }
        Entry<K, V> chain = buckets[keyHash(key)];
        chain.delete(key);
        if (chain.key().equals(key)) {
            buckets[keyHash(key)] = chain.next;
        }
        return res;
    }
}
