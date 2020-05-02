package bearmaps.proj2c;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.PriorityQueue;
import java.util.concurrent.ConcurrentMap;

public class MyTrieSet implements TrieSet61B {
    private Node head;

    public MyTrieSet() {
        head = new Node();
    }

    @Override
    /** Clears all items out of Trie */
    public void clear() {
        head = null;
    }

    private boolean containHelper(String key, int index, Node ptr) {
        if (index == key.length()) {
            return ptr.isKey;
        }
        char c = key.charAt(index);
        if (ptr.links.containsKey(c)) {
            ptr = ptr.links.get(c);
            index += 1;
            return containHelper(key, index, ptr);
        } else {
            return false;
        }
    }

    @Override
    /** Returns true if the Trie contains KEY, false otherwise */
    public boolean contains(String key) {
        if (head == null) {
            return false;
        }
        return containHelper(key, 0, head);
    }

    @Override
    /** Inserts string KEY into Trie */
    public void add(String key) {
        Node ptr = head;
        int kl = key.length();
        char c;
        for (int i = 0; i <= kl; i++) {
            if (i == kl) {
                ptr.isKey = true;
                continue;
            }
            c = key.charAt(i);
            if (ptr.links.containsKey(c)) {
                ptr = ptr.links.get(c);
                continue;
            } else {
                Node next = new Node();
                ptr.links.put(c, next);
                ptr = next;
            }
        }
    }

    private List<String> retriever(Node subTrie, String pre) {
        List<String> res = new ArrayList<>();
        if (subTrie.isKey) {
            res.add(pre);
        }
        for (char c: subTrie.links.keySet()
             ) {
            res.addAll(retriever(subTrie.links.get(c), pre + c));
        }
        return res;
    }

    @Override
    /** Returns a list of all words that start with PREFIX */
    public List<String> keysWithPrefix(String prefix) {
        List<String> res = new ArrayList<>();
        int pl = prefix.length();
        Node prePtr = head;
        for (int i = 0; i < pl; i++) {
            prePtr = prePtr.links.get(prefix.charAt(i));
        }

        res = retriever(prePtr, prefix);

        return res;
    }

    private String longestHelper(Node subTrie, String pre) {
        PriorityQueue<LString> spq = new PriorityQueue<>();
        if (subTrie.isKey) {
            spq.add(new LString(pre));
        }
        for (char c: subTrie.links.keySet()
        ) {
            spq.add(new LString(longestHelper(subTrie.links.get(c), pre + c)));
        }
        return spq.peek().getString();
    }


    /** Returns the longest prefix of KEY that exists in the Trie
     * Not required for Lab 9. If you don't implement this, throw an
     * UnsupportedOperationException.
     */
    @Override
    public String longestPrefixOf(String key) {
//        throw new UnsupportedOperationException();
        int pl = key.length();
        Node prePtr = head;
        for (int i = 0; i < pl; i++) {
            prePtr = prePtr.links.get(key.charAt(i));
            if (prePtr == null) {
                return null;
            }
        }

        return longestHelper(prePtr, key);
    }

    private class Node {
        boolean isKey;
        TreeMap<Character, Node> links;

        public Node(boolean isKey) {
            this.isKey = isKey;
            links = new TreeMap<>();
        }

        public Node() {
            this.isKey = false;
            links = new TreeMap<>();
        }

        public void put (char c, boolean isKey) {
            Node next = new Node(isKey);
            links.put(c, next);
        }
    }

    private class LString implements Comparable<LString> {
        String s;
        int length;

        public LString(String s) {
            this.s = s;
            length = s.length();
        }

        @Override
        public int compareTo(LString other) {
            return other.length - this.length; // for minHeap
        }

        String getString() {
            return s;
        }
    }
}
