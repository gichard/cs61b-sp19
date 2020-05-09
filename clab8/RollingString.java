/**
 * A String-like class that allows users to add and remove characters in the String
 * in constant time and have a constant-time hash function. Used for the Rabin-Karp
 * string-matching algorithm.
 */
class RollingString{

    /**
     * Number of total possible int values a character can take on.
     * DO NOT CHANGE THIS.
     */
    static final int UNIQUECHARS = 128;

    /**
     * The prime base that we are using as our mod space. Happens to be 61B. :)
     * DO NOT CHANGE THIS.
     */
    static final int PRIMEBASE = 6113;

    private char[] content;
    private int start;
    private int next;
    private int size;
    private int hash;
    private int MSH; // hash of the most significant 'digit' c is c * MSH % PRIMEBASE

    /**
     * Initializes a RollingString with a current value of String s.
     * s must be the same length as the maximum length.
     */
    public RollingString(String s, int length) {
        assert(s.length() == length);
        /* FIX ME */
        content = new char[length];
        size = 0;
        start = 0;
        next = 0;
        hash = 0;
        MSH = 1;
        while (size < length) {
            content[next] = s.charAt(size);
            size += 1;
            // update hashcode in constant time
            hash = (hash * UNIQUECHARS % PRIMEBASE + (int) content[next]) % PRIMEBASE;
            next = (next + 1) % length;
            MSH = (MSH * UNIQUECHARS) % PRIMEBASE;
        }
    }

    /**
     * Adds a character to the back of the stored "string" and 
     * removes the first character of the "string". 
     * Should be a constant-time operation.
     */
    public void addChar(char c) {
        /* FIX ME */
        char oldFirst = content[start];
        start = (start + 1) % size;
        content[next] = c;
        hash = Math.floorMod(Math.floorMod(hash * UNIQUECHARS, PRIMEBASE)
                - Math.floorMod((int) oldFirst * MSH, PRIMEBASE) + (int) c, PRIMEBASE);
        next = start;
    }

    /**
     * Returns the "string" stored in this RollingString, i.e. materializes
     * the String. Should take linear time in the number of characters in
     * the string.
     */
    public String toString() {
        StringBuilder strb = new StringBuilder();
        /* FIX ME */
        for (int i = 0; i < size; i++) {
            strb.append(content[(start + i) % size]);
        }
        return strb.toString();
    }

    /**
     * Returns the fixed length of the stored "string".
     * Should be a constant-time operation.
     */
    public int length() {
        /* FIX ME */
        return size;
    }


    /**
     * Checks if two RollingStrings are equal.
     * Two RollingStrings are equal if they have the same characters in the same
     * order, i.e. their materialized strings are the same.
     */
    @Override
    public boolean equals(Object o) {
        /* FIX ME */
        return this.toString().equals(o.toString());
    }

    /**
     * Returns the hashcode of the stored "string".
     * Should take constant time.
     */
    @Override
    public int hashCode() {
        /* FIX ME */
        return hash;
    }
}
