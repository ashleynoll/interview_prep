import java.util.HashMap;

/**
 * Problem:
 *
 * Given two strings, write a method to decide if one is a permutation of the other.
 *
 * Solution Explanation:
 *
 * Assumptions - whitespace is not important and strings are case sensitive
 *
 * Permutations should be the same size, so check that the given strings are of the
 * same length. Next, create a hash map that will map the character to the number
 * of times it occurs in the first string. Iterate through the second string.
 * If there is no mapping for the character or if the count for the character is
 * zero, return false, else decrement the counter for the character and continue.
 * If the loop completes, return true.
 */
public class stringPermutation {
    public static boolean stringPermutation(String s, String t) {
        if (s.length() != t.length()) {
            return false;
        }
        HashMap<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < s.length(); i++) {
            map.put(s.charAt(i), map.getOrDefault(s.charAt(i), 0) + 1);
        }
        for (int i = 0; i < s.length(); i++) {
            if (map.getOrDefault(s.charAt(i), 0) == 0) {
                return false;
            }
            map.put(s.charAt(i), map.get(s.charAt(i)) - 1);
        }
        return true;
    }

    public static void main(String[] args) {
        boolean ans = stringPermutation("hello", "world");

        if (ans) {
            System.out.println("Expected false for 'hello' & 'world', but was true.");
        }

        ans = stringPermutation("cat-dog", "cota-dg");

        if (!ans) {
            System.out.println("Expected true for 'cat-dog' & 'cota-dg', but was false.");
        }

        System.out.println("All tests complete!");
    }
}
