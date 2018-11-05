import java.util.HashSet;

/**
 * Problem:
 *
 * Given a string, write a function to check if it is a permutation of a palindrome.
 * A palindrome is a word or phrase that is the same forwards and backwards. A
 * permutation is a rearrangement of letters. The palindrome does not need to
 * be limited to just dictionary words.
 *
 * Solution Explanation:
 *
 * Palindromes must have all except maybe one character in a multiple of two.
 * Iterate through the string, adding to a hash set the character if it does
 * not currently exist in the set, or removing it if it does. If at the end
 * of the iteration there is more than 1 character remaining in the set, it
 * cannot create a palindrome.
 */
public class palindromePermutation {
    public static boolean palindromePermutation(String s) {
        HashSet<Character> set = new HashSet<>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c != ' ') {
                if (set.contains(c)) {
                    set.remove(c);
                } else {
                    set.add(c);
                }
            }
        }
        return set.size() < 2;
    }

    public static void main(String[] args) {
        String input = "tact coa";
        boolean ans = palindromePermutation(input);

        if (!ans) {
            System.out.println("Answer was true for " + input + " but received false.");
        }

        input = "an angry cat";
        ans = palindromePermutation(input);

        if (ans) {
            System.out.println("Answer was false for " + input + " but received true.");
        }

        System.out.println("All tests complete!");
    }
}
