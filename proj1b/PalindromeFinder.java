/*import sun.awt.windows.WPrinterJob;*/

/** This class outputs all palindromes off by N in the words file in the current directory. */
public class PalindromeFinder {
    public static void findPN(int N) {
        int minLength = 4;
        In in = new In("../library-sp19/data/words.txt");
        Palindrome palindrome = new Palindrome();
        CharacterComparator offByN = new OffByN(N);

        while (!in.isEmpty()) {
            String word = in.readString();
            if (word.length() >= minLength && palindrome.isPalindrome(word, offByN)) {
                System.out.println(word);
            }
        }
    }

    public static String findLongestPN(int N) {
        In in = new In("../library-sp19/data/words.txt");
        Palindrome palindrome = new Palindrome();
        CharacterComparator offByN = new OffByN(N);
        int longestLen = 0;
        String longestWord = "";

        while (!in.isEmpty()) {
            String word = in.readString();
            if (word.length() >= longestLen && palindrome.isPalindrome(word, offByN)) {
                longestWord = word;
            }
        }

        return longestWord;
    }

    /** finds longest Palindrome offset by N for any N*/
    public static void longestPN() {
        for (int i = 0; i < 26; i++) {
            String longestByN =  findLongestPN(i);
            if (longestByN.equals("")) {
                System.out.println("longest Palindrome offset by " + i + " doesn't exist");
            } else {
                System.out.println("longest Palindrome offset by " + i + " is: " + longestByN);
            }
        }
    }

    public static void main(String[] args) {
//            findPN(5);
        longestPN();
    }
}

/** This class outputs all palindromes off by one in the words file in the current directory. */
/*
public class PalindromeFinder {
    public static void main(String[] args) {
        int minLength = 4;
        In in = new In("../library-sp19/data/words.txt");
        Palindrome palindrome = new Palindrome();
        CharacterComparator offByOne = new OffByOne();

        while (!in.isEmpty()) {
            String word = in.readString();
            if (word.length() >= minLength && palindrome.isPalindrome(word, offByOne)) {
                System.out.println(word);
            }
        }
    }
}*/

/** This class outputs all palindromes in the words file in the current directory. */
/*
public class PalindromeFinder {
    public static void main(String[] args) {
        int minLength = 4;
        In in = new In("../library-sp19/data/words.txt");
        Palindrome palindrome = new Palindrome();

        while (!in.isEmpty()) {
            String word = in.readString();
            if (word.length() >= minLength && palindrome.isPalindrome(word)) {
                System.out.println(word);
            }
        }
    }
}*/
