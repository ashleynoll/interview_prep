import java.util.HashSet;

/**
 * Problem:
 *
 * Implement an algorithm to determine if a string has all unique characters.
 *
 * What if you cannot use additional data structures?
 *
 * Solution Explanation:
 *
 * With data structure (implemented): Iterate over string characters, placing them in a hash
 * set. If the character already exists in the hash set, return false, else return
 * true after the full iteration.
 *
 * Without data structure (two ways): Sort the string and compare adjacent
 * characters. O(nlogn) time, but O(1) space. Optionally, if the character set
 * can be defined as 26 possibilities, you can use an integer bit vector to store
 * whether a character has been found.
 *
 */
public class allUniqueCharacters {
    public static boolean allUniqueCharacters(String s) {
        // Ask if the character set is ASCII (128)/ASCII-extended (256) or Unicode (1,114,112)
        if (s.length() > 128) {
            return false;
        }
        HashSet<Character> set = new HashSet<>();
        for (int i = 0; i < s.length(); i++) {
            if (set.contains(s.charAt(i))) {
                return false;
            }
            set.add(s.charAt(i));
        }
        return true;

    }

    public static void main(String[] args) {
        boolean ans = allUniqueCharacters("hello");

        if (ans) {
            System.out.println("Expected false for 'hello', but was true.");
        }

        ans = allUniqueCharacters("cat-dog");

        if (!ans) {
            System.out.println("Expected true for 'cat-dog', but was false.");
        }

        System.out.println("All tests complete!");
    }
}
