public class Palindrome {
    public Deque<Character> wordToDeque(String word) {
        Deque<Character> result = new LinkedListDeque();
        int length = word.length();
        for (int i = 0; i < length; i++) {
            result.addLast(word.charAt(i));
        }
        return result;
    }

    private boolean isPalindromeHlpr(Deque<Character> cList) {
        if (cList.size() == 0 || cList.size() == 1) {
            return true;
        } else {
            return (cList.removeFirst() == cList.removeLast()) && isPalindromeHlpr(cList);
        }
    }
    /** Any word of length 1 or 0 is a palindrome;
     * ‘A’ and ‘a’ should NOT be considered equal */
    public boolean isPalindrome(String word) {
        Deque<Character> cList = wordToDeque(word);
        return isPalindromeHlpr(cList);
    }

    private boolean isPalindromeHlpr(Deque<Character> cList, CharacterComparator cc) {
        if (cList.size() == 0 || cList.size() == 1) {
            return true;
        } else {
            return cc.equalChars(cList.removeFirst(),
                    cList.removeLast()) && isPalindromeHlpr(cList, cc);
        }
    }

    public boolean isPalindrome(String word, CharacterComparator cc) {
        Deque<Character> cList = wordToDeque(word);
        return isPalindromeHlpr(cList, cc);
    }
}
