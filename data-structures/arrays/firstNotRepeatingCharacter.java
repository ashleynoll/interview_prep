/**
 * Problem:
 *
 * Given a string s, find and return the first instance of a non-repeating
 * character in it. If there is no such character, return '_'.
 *
 * Note: Write a solution that only iterates over the string once and uses O(1)
 * additional memory, since this is what you would be asked to do during a
 * real interview.
 *
 * Solution Explanation:
 *
 * Iterate over the characters in the string. Check if the last occurrence of
 * the character is also the first occurrence. Since iterating from front to back
 * this will be the first non-repeating character. If the loop ends without
 * returning, return '_'.
 */
public class firstNotRepeatingCharacter {
    public static char firstNotRepeatingCharacter(String s) {
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (s.lastIndexOf(c) == s.indexOf(c)) {
                return c;
            }
        }
        return '_';
    }

    public static void main(String[] args) {
        String s = "hello world!";
        char ans = firstNotRepeatingCharacter(s);

        if (ans != 'h') {
            System.out.println("For " + s + ", expected 'h' but was " + ans + ".");
        }

        s = "babas";
        ans = firstNotRepeatingCharacter(s);

        if (ans != 's') {
            System.out.println("For " + s + ", expected 's' but was " + ans + ".");
        }

        s = "hannah";
        ans = firstNotRepeatingCharacter(s);

        if (ans != '_') {
            System.out.println("For " + s + ", expected '_' but was " + ans + ".");
        }

        System.out.println("All tests complete!");
    }
}
