public class Palindrome {
    public Deque<Character> wordToDeque(String word) {
        Deque<Character> result = new LinkedListDeque();
        int length = word.length();
        for (int i = 0; i < length; i++) {
            result.addLast(word.charAt(i));
        }
        return result;
    }

    private boolean isPalindromeHlpr(Deque<Character> cList1, Deque<Character> cList2) {
        if (cList1.size() == 0) {
            return true;
        } else {
            return (cList1.removeFirst() == cList2.removeLast()) && isPalindromeHlpr(cList1, cList2);
        }
    }
    /** Any word of length 1 or 0 is a palindrome;
     * ‘A’ and ‘a’ should NOT be considered equal */
    public boolean isPalindrome(String word) {
/*        if (word.length() == 0) {
            return true;
        }
        Deque<Character> cList1 = wordToDeque(word);
        Deque<Character> cList2 = wordToDeque(word);
        while (cList1.size() > 0) {
            if (cList1.removeFirst() != cList2.removeLast()) {
                return false;
            }
        }
        return true;*/
        Deque<Character> cList1 = wordToDeque(word);
        Deque<Character> cList2 = wordToDeque(word);
        return isPalindromeHlpr(cList1, cList2);
    }
}
