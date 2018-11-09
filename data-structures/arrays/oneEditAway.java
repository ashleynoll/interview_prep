/**
 * Problem:
 *
 * There are three types of edits that can be performed on strings: insert a
 * character, remove a character, or replace a character. Given two strings,
 * write a function to check if they are one edit (or zero edits) away.
 *
 * Solution Explanation:
 *
 * If the strings are the same length, to be only one edit away they must have
 * underwent a replacement of character. Check if all or all but one of the
 * characters are the same to ensure this is true. If the strings are only
 * one away from each other, then either an insertion or a deletion must have
 * occurred. To simplify this, mark the bigger of the two strings and the smaller.
 * Search for a mismatched character and then check whether the next character
 * in the larger of the two strings matches the mismatched character from the
 * smaller string. This means that an insertion has happened from the smaller to
 * the larger string. If another mismatched character is encountered, then return
 * false. If the lengths are anything other than the same or one away then return
 * false.
 */
public class oneEditAway {
    public static boolean oneEditAway(String str1, String str2) {
        if (str1.length() == str2.length()) {
            // Check for replacement of character
            boolean foundChange = false;
            for (int i = 0; i < str1.length(); i++) {
                if (str1.charAt(i) != str2.charAt(i)) {
                    if (foundChange) {
                        return false;
                    } else {
                        foundChange = true;
                    }
                }
            }
            return true;
        } else if (str2.length() == str1.length() - 1 || str2.length() == str1.length() + 1) {
            // Check for deletion or insertion
            boolean foundChange = false;
            String bigger = str1.length() > str2.length() ? str1 : str2,
                    smaller = str1.length() < str2.length() ? str1 : str2;
            for (int i = 0, j = 0; i < bigger.length() && j < smaller.length(); i++, j++) {
                if (bigger.charAt(i) != smaller.charAt(i)) {
                    if (foundChange) {
                        return false;
                    } else {
                        // Verify the change is an insertion/deletion
                        if (i + 1 < bigger.length() && bigger.charAt(i + 1) == smaller.charAt(j)) {
                            foundChange = true;
                        } else {
                            return false;
                        }
                    }
                }
            }
            return true;
        } else {
            return false;
        }
    }

    public static void main(String[] args) {
        String str1 = "cat",
                str2 = "cart";
        boolean ans = oneEditAway(str1, str2);

        if (!ans) {
            System.out.println("Answer was true for " + str1 + " -> " + str2 + " but received false.");
        }

        str1 = "part";
        str2 = "pact";
        ans = oneEditAway(str1, str2);

        if (!ans) {
            System.out.println("Answer was true for " + str1 + " -> " + str2 + " but received false.");
        }

        str1 = "banana";
        str2 = "panama";
        ans = oneEditAway(str1, str2);

        if (ans) {
            System.out.println("Answer was false for " + str1 + " -> " + str2 + " but received true.");
        }

        str1 = "banana";
        str2 = "banan";
        ans = oneEditAway(str1, str2);

        if (!ans) {
            System.out.println("Answer was true for " + str1 + " -> " + str2 + " but received false.");
        }

        System.out.println("All tests complete!");
    }
}
