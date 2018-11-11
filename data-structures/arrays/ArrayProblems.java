import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * A collection of various problems about arrays, array lists, and strings
 * along with an explanation of their solution.
 */
public class ArrayProblems {

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
    public boolean allUniqueCharacters(String s) {
        // Ask if the character set is ASCII (128)/ASCII-extended (256) or Unicode (1,114,112)
        if (s.length() > 128) {
            return false;
        }
        Set<Character> set = new HashSet<>();
        for (int i = 0; i < s.length(); i++) {
            if (set.contains(s.charAt(i))) {
                return false;
            }
            set.add(s.charAt(i));
        }
        return true;

    }

    /**
     * Problem:
     *
     * Implement a method to perform basic string compression using the counts of
     * repeated characters. For example, the string aabcccccaaa would become a2b1c5a3.
     * If the "compressed" string would not become smaller than the original string,
     * your method should return the original string. You can assume the string has
     * only uppercase and lowercase letters.
     *
     * Solution Explanation:
     *
     * Since concatenating onto a string is expensive, use StringBuilder to build
     * your compressed string. Loop through the array, adding in a character to the
     * string builder, then iterating to the next character that does not match the
     * current character. Add the count of how many letters were betweent the characters
     * and then add in the newly encountered character. If the string builder creates
     * a string that is smaller than the original, return it, else return the original
     * string.
     *
     */
    public String compressString(String str) {
        if (str.equals("")) {
            return str;
        }

        StringBuilder sb = new StringBuilder();
        int count = 1,
                i = 0;

        while (i < str.length()) {
            sb.append(str.charAt(i++));
            while (i < str.length() && str.charAt(i) == str.charAt(i - 1)) {
                count++;
                i++;
            }
            sb.append(count);
            count = 1;
        }

        String ret = sb.toString();
        return ret.length() >= str.length() ? str : ret;
    }

    /**
     * Problem:
     *
     * Given an array a that contains only numbers in the range from 1 to a.length,
     * find the first duplicate number for which the second occurrence has the
     * minimal index. In other words, if there are more than 1 duplicated numbers,
     * return the number for which the second occurrence has a smaller index than
     * the second occurrence of the other number does. If there are no such
     * elements, return -1.
     *
     * Solution Explanation:
     *
     * Loop through the array. As integers are encountered, put them in a HashSet.
     * Check if the value already existed in the HashSet. If so, return the value.
     * Return -1 if the loop terminates without finding a duplicate.
     */
    public int firstDuplicate(int[] a) {
        Set<Integer> encountered = new HashSet<>();

        for (int i = 0; i < a.length; i++) {
            if (encountered.contains(a[i])) {
                return a[i];
            }
            encountered.add(a[i]);
        }

        return -1;
    }

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
    public char firstNotRepeatingCharacter(String s) {
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (s.lastIndexOf(c) == s.indexOf(c)) {
                return c;
            }
        }
        return '_';
    }

    /**
     * Problem:
     *
     * Sudoku is a number-placement puzzle. The objective is to fill a 9 × 9 grid
     * with numbers in such a way that each column, each row, and each of the nine
     * 3 × 3 sub-grids that compose the grid all contain all of the numbers from
     * 1 to 9 one time.

     Implement an algorithm that will check whether the given grid of numbers
     represents a valid Sudoku puzzle according to the layout rules described above.
     Note that the puzzle represented by grid does not have to be solvable.
     *
     * Solution Explanation:
     * Maintain two maps of maps that track the column and row usage of numbers.
     * Go through every 3 x 3 box, making sure that there are no duplicates by
     * storing values in a hashmap. At the same time, add numbers to row/column
     * map and check for duplicates. After all boxes have been checked the sudoku
     * grid can be called valid.
     *
     */
    public boolean isValidSudoku(char[][] grid) {
        HashMap<Integer, HashSet<Character>> columns = new HashMap<>(),
                rows = new HashMap<>();

        // Check every 3x3 box
        for (int i = 0; i < 9; i++) {
            HashSet<Character> inBoxSet = new HashSet<>();
            for (int k = i % 3 * 3; k < i % 3 * 3 + 3; k++) {
                for (int j = i / 3 * 3; j < i / 3 * 3 + 3; j ++) {
                    char curr = grid[k][j];
                    if (curr != '.') {
                        // Check if valid for box
                        if (inBoxSet.contains(curr)) {
                            return false;
                        }
                        inBoxSet.add(curr);

                        // Check if valid for rows
                        rows.computeIfAbsent(k, key -> new HashSet<>());
                        if (rows.get(k).contains(curr)) {
                            return false;
                        }
                        rows.get(k).add(curr);

                        // Check if valid for rows
                        columns.computeIfAbsent(j, key-> new HashSet<>());
                        if (columns.get(j).contains(curr)) {
                            return false;
                        }
                        columns.get(j).add(curr);
                    }
                }
            }
        }

        return true;
    }

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
    public boolean oneEditAway(String str1, String str2) {
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
                if (bigger.charAt(i) != smaller.charAt(j)) {
                    if (foundChange) {
                        return false;
                    } else {
                        // Verify the change is an insertion/deletion
                        if (i + 1 < bigger.length() && bigger.charAt(i + 1) == smaller.charAt(j)) {
                            foundChange = true;
                            i++;
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
    public boolean palindromePermutation(String s) {
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

    /**
     * Problem:
     *
     * You are given an n x n 2D matrix that represents an image.
     * Rotate the image by 90 degrees (clockwise).
     *
     * Note: Try to solve this task in-place (with O(1) additional memory),
     * since this is what you'll be asked to do during an interview.
     *
     * Solution Explanation:
     *
     * Matrix rotation can be accomplished by mirroring the elements on the
     * upper-left to bottom-right diagonal and then mirroring the elements vertically.
     */
    public int[][] rotateImage(int[][] a) {
        // Mirror on the diagonal
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < i; j++) {
                int temp = a[i][j];
                a[i][j] = a[j][i];
                a[j][i] = temp;
            }
        }

        // Mirror on the vertical axis
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[i].length / 2; j++) {
                int temp = a[i][j];
                a[i][j] = a[i][a.length - 1 - j];
                a[i][a.length - 1 - j] = temp;
            }
        }

        return a;
    }

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
    public boolean stringPermutation(String s, String t) {
        if (s.length() != t.length()) {
            return false;
        }
        HashMap<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < s.length(); i++) {
            map.put(s.charAt(i), map.getOrDefault(s.charAt(i), 0) + 1);
        }
        for (int i = 0; i < s.length(); i++) {
            if (map.getOrDefault(t.charAt(i), 0) == 0) {
                return false;
            }
            map.put(t.charAt(i), map.get(t.charAt(i)) - 1);
        }
        return true;
    }

    /**
     * Problem:
     *
     * Write a method to replace all spaces in a string with '%20'. You many assume
     * that the string has sufficient space at the end to hold the additional characters,
     * and that you are given the "true" length of the string. Note: if implementing
     * in Java, please use a character array so that you can perform this operation
     * in place.
     *
     * Solution Explanation:
     *
     * Since there needs to be more characters inserted into the array, you'll need
     * to start from the end and shift characters to make room. Place a pointer at the
     * last available index and start a loop to iterate backwards over the array.
     * Copy the character to the end pointer and decrement the end pointer.
     * Whenever a space is encountered, use the end pointer to add
     * '0', '2', and '%' and continue on.
     *
     * Optional optimization - Use the difference between truelength and the length
     * of the character array to find the number of spaces and end after it is reached.
     */
    public char[] urlify(char[] characters, int trueLength) {
        if (characters.length == trueLength) {
            return characters;
        }
        int numSpacesRemaining = (characters.length - trueLength) / 2,
                endPointer = characters.length - 1;
        for (int i = trueLength - 1; i >= 0; i--) {
            char c = characters[i];
            if (c != ' ') {
                characters[endPointer--] = c;
            } else {
                characters[endPointer--] = '0';
                characters[endPointer--] = '2';
                characters[endPointer--] = '%';
                numSpacesRemaining--;
                if (numSpacesRemaining == 0) {
                    return characters;
                }
            }
        }
        return characters;
    }
}
