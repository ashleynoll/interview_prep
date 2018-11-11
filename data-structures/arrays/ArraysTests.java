import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertArrayEquals;

/**
 * Testing for various array, arraylist, and string problems
 */
public class ArraysTests {
    private static final long TIMEOUT = 200;
    private ArrayProblems problems;

    @Before
    public void setUp() {
        problems = new ArrayProblems();
    }
    /**
     * Helper method to print out an array of values
     * @param arr Array of values
     */
    private static void printArray(Object[] arr) {
        for (Object a : arr) {
            System.out.print(a);
        }
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //       allUniqueCharacters
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    @Test(timeout = TIMEOUT)
    public void testAllUniqueCharactersFalse() {
        assertFalse("'hello' should not be identified as having all unique characters",
                problems.allUniqueCharacters("hello"));

        assertFalse("'georgia' should not be identified as having all unique characters",
                problems.allUniqueCharacters("georgia"));

        assertFalse("'banana' should not be identified as having all unique characters",
                problems.allUniqueCharacters("banana"));
    }

    @Test(timeout = TIMEOUT)
    public void testAllUniqueCharactersTrue() {
        assertTrue("'rainbow' should be identified as having all unique characters",
                problems.allUniqueCharacters("rainbow"));

        assertTrue("'averybigspot' should be identified as having all unique characters",
                problems.allUniqueCharacters("averybigspot"));

    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //          compressString
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    @Test(timeout = TIMEOUT)
    public void testCompressStringNoCompression() {
        assertEquals("String 'abcd' should return 'abcd' after compression",
                "abcd", problems.compressString("abcd"));
        assertEquals("String 'aabcd' should return 'aabcd' after compression",
                "aabcd", problems.compressString("aabcd"));
    }

    @Test(timeout = TIMEOUT)
    public void testCompressStringCompressed() {
        assertEquals("String 'aaabb' should return 'a3b2' after compression",
                "a3b2", problems.compressString("aaabb"));
        assertEquals("String 'aaaaaaabbbbcddddeee' should return 'a7b4c1d4e3' after compression",
                "a7b4c1d4e3", problems.compressString("aaaaaaabbbbcddddeee"));
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //          firstDuplicate
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    @Test(timeout = TIMEOUT)
    public void testFirstDuplicateDoesNotExist() {
        assertEquals("Array [1, 2, 3, 4, 5, 6, 7, 8] does not have a duplicate value",
                -1, problems.firstDuplicate(new int[]{1, 2, 3, 4, 5, 6, 7, 8}));

        assertEquals("An empty array does not have a duplicate value",
                -1, problems.firstDuplicate(new int[]{}));
    }

    @Test(timeout = TIMEOUT)
    public void testFirstDuplicateSimple() {
        assertEquals("Array [1, 2, 1, 3] has a first duplicate of 1",
                1, problems.firstDuplicate(new int[]{1, 2, 1, 3}));

        assertEquals("Array [1, 2, 3, 2] has a first duplicate of 2",
                2, problems.firstDuplicate(new int[]{1, 2, 3, 2}));
    }

    @Test(timeout = TIMEOUT)
    public void testFirstDuplicateMultipleDuplicates() {
        assertEquals("Array [1, 2, 1, 2] has a first duplicate of 1",
                1, problems.firstDuplicate(new int[]{1, 2, 1, 2}));

        assertEquals("Array [1, 2, 3, 3, 2] has a first duplicate of 3",
                3, problems.firstDuplicate(new int[]{1, 2, 3, 3, 2}));
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //     firstNotRepeatingCharacter
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    @Test(timeout = TIMEOUT)
    public void testFirstNotRepeatingCharacterDoesNotExist() {
        assertEquals("'hannah' has all repeating characters",
                '_', problems.firstNotRepeatingCharacter("hannah"));

        assertEquals("An empty string has all repeating characters",
                '_', problems.firstNotRepeatingCharacter(""));
    }

    @Test(timeout = TIMEOUT)
    public void testFirstNotRepeatingCharacterFound() {
        assertEquals("'a' has a first non-repeating character of 'a'",
                'a', problems.firstNotRepeatingCharacter("a"));

        assertEquals("'pineapple' has a first non-repeating character of 'i'",
                'i', problems.firstNotRepeatingCharacter("pineapple"));
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //           isValidSudoku
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    @Test(timeout = TIMEOUT)
    public void testIsValidSudokuFalse() {
        char[][] chars = new char[][]{
                {'.', '.', '.', '.', '3', '6', '9', '.', '1'},
                {'.', '1', '.', '.', '.', '4', '.', '.', '5'},
                {'.', '8', '.', '.', '9', '.', '7', '.', '.'},
                {'2', '.', '1', '7', '8', '5', '.', '.', '.'},
                {'.', '5', '.', '.', '.', '9', '.', '.', '7'},
                {'9', '7', '4', '6', '.', '2', '3', '.', '.'},
                {'.', '4', '.', '.', '6', '.', '1', '.', '8'},
                {'3', '9', '2', '.', '.', '7', '6', '8', '.'}, // Extra 8 in box
                {'1', '.', '7', '.', '.', '.', '5', '9', '3'}
        };
        assertFalse("Test 1 should not be a valid sudoku. There is a duplicate in a 3 x 3.",
                problems.isValidSudoku(chars));

        chars = new char[][]{
                {'.', '.', '.', '.', '3', '6', '9', '.', '1'},
                {'.', '1', '.', '.', '.', '4', '.', '.', '5'},
                {'.', '8', '.', '.', '9', '.', '7', '.', '.'},
                {'2', '.', '1', '7', '8', '5', '.', '.', '.'},
                {'.', '5', '.', '.', '.', '9', '.', '.', '7'},
                {'9', '7', '4', '6', '.', '2', '3', '.', '.'},
                {'.', '4', '.', '.', '6', '5', '1', '.', '.'}, // Extra 5 in column
                {'3', '9', '2', '.', '.', '7', '6', '8', '.'},
                {'1', '.', '7', '.', '.', '.', '5', '9', '3'}
        };
        assertFalse("Test 2 should not be a valid sudoku. There is a duplicate in a column.",
                problems.isValidSudoku(chars));

        chars = new char[][]{
                {'.', '.', '.', '.', '3', '6', '9', '.', '1'},
                {'.', '1', '.', '.', '.', '4', '.', '.', '5'},
                {'.', '8', '.', '.', '9', '.', '7', '.', '.'},
                {'2', '.', '1', '7', '8', '5', '.', '.', '.'},
                {'.', '5', '.', '.', '.', '9', '.', '.', '7'},
                {'9', '7', '4', '6', '.', '2', '3', '.', '2'}, // Extra 2 in row
                {'.', '4', '.', '.', '6', '.', '1', '.', '.'},
                {'3', '9', '2', '.', '.', '7', '6', '8', '.'},
                {'1', '.', '7', '.', '.', '.', '5', '9', '3'}
        };
        assertFalse("Test 2 should not be a valid sudoku. There is a duplicate in a row.",
                problems.isValidSudoku(chars));
    }

    @Test(timeout = TIMEOUT)
    public void testIsValidSudokuTrue() {
        char[][] chars = new char[][]{
                {'9', '7', '.', '.', '.', '.', '.', '.', '.'},
                {'6', '1', '8', '.', '.', '9', '.', '5', '.'},
                {'.', '.', '.', '3', '7', '.', '.', '1', '.'},
                {'.', '3', '2', '8', '.', '.', '.', '6', '1'},
                {'.', '.', '4', '6', '5', '7', '2', '.', '.'},
                {'7', '8', '.', '.', '.', '2', '9', '4', '.'},
                {'.', '6', '.', '.', '8', '1', '.', '.', '.'},
                {'.', '2', '.', '4', '.', '.', '1', '9', '8'},
                {'.', '.', '.', '.', '.', '.', '.', '7', '4'}
        };
        assertTrue("Valid sudoku has marked as invalid",
                problems.isValidSudoku(chars));
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //            oneEditAway
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    @Test(timeout = TIMEOUT)
    public void testOneEditAwayFalse() {
        assertFalse("'banana' to 'panama' is not one edit away.",
                problems.oneEditAway("banana", "panama"));

        assertFalse("'apple' to 'pinapple' is not one edit away" ,
                problems.oneEditAway("apple", "pineapple"));
    }

    @Test(timeout = TIMEOUT)
    public void testOneEditAwayTrue() {
        assertTrue("'cat' is one insertion away from 'cart'",
                problems.oneEditAway("cat", "cart"));

        assertTrue("'part' is one change away from 'pact'",
                problems.oneEditAway("part", "pact"));

        assertTrue("'bread' is one deletion away from 'read'",
                problems.oneEditAway("bread", "read"));
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //        palindromePermutation
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    @Test(timeout = TIMEOUT)
    public void testPalindromePermutationFalse() {
        assertFalse("'an angry cat' cannot become a palindrome",
                problems.palindromePermutation("an angry cat"));
    }

    @Test(timeout = TIMEOUT)
    public void testPalindromePermutationTrue() {
        assertTrue("'tact coa' can become a palindrome",
                problems.palindromePermutation("tact coa"));

        assertTrue("'a man a plan a canal panama' is a palindrome" ,
                problems.palindromePermutation("a man a plan a canal panama"));

        assertTrue("An empty string can be a palindrome" ,
                problems.palindromePermutation(""));
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //           rotateImage
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    @Test(timeout = TIMEOUT)
    public void testRotateImageSmall() {
        /**
         * [  1,  2 ]
         * [  3, 4 ]
         *
         *  Rotates to =>
         *
         * [ 3, 1 ]
         * [ 4, 2 ]
         *
         */

        int[][] a = { { 1, 2 }, { 3, 4 } },
                ans = { { 3, 1 }, { 4, 2 } },
                ret = problems.rotateImage(a);

        for (int i = 0; i < ans.length && i < ret.length; i++) {
            assertArrayEquals("The large image is not properly rotated on row " + i,
                    ans[i], ret[i]);
        }
    }

    @Test(timeout = TIMEOUT)
    public void testRotateImageLarge() {
        /**
         * [  1,  2,  3,  4 ]
         * [  5,  6,  7,  8 ]
         * [  9, 10, 11, 12 ]
         * [ 13, 14, 15, 16 ]
         *
         *  Rotates to =>
         *
         * [ 13,  9, 5, 1 ]
         * [ 14, 10, 6, 2 ]
         * [ 15, 11, 7, 3 ]
         * [ 16, 12, 8, 4 ]
         */

        int[][] a = { { 1, 2, 3, 4 }, { 5, 6, 7, 8 }, { 9, 10, 11, 12}, { 13, 14, 15, 16 } },
                ans = { { 13, 9, 5, 1 }, { 14, 10, 6, 2 }, { 15, 11, 7, 3}, { 16, 12, 8, 4 } },
                ret = problems.rotateImage(a);

        for (int i = 0; i < ans.length && i < ret.length; i++) {
            assertArrayEquals("The large image is not properly rotated on row " + i,
                    ans[i], ret[i]);
        }

    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //        stringPermutation
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    @Test(timeout = TIMEOUT)
    public void testStringPermutationFalse() {
        assertFalse("'hello' is not a permutation of 'world'",
                problems.stringPermutation("hello", "world"));
    }

    @Test(timeout = TIMEOUT)
    public void testStringPermutationTrue() {
        assertTrue("'cat-dog' is a permutation of 'cota-dg'",
                problems.stringPermutation("cat-dog", "cota-dg"));
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //             URLify
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    @Test(timeout = TIMEOUT)
    public void testURLifyNoChange() {
        char[] chars = "nospace".toCharArray();
        assertArrayEquals("'nospace' should not change when url-ified",
                "nospace".toCharArray(), problems.urlify(chars, 7));
    }

    @Test(timeout = TIMEOUT)
    public void testurlifyMultipleSpaces() {
        char[] chars = new char[]{'m', 'r', ' ', 'j', 'o', 'h', 'n', ' ', 's', 'm', 'i', 't', 'h', ' ', ' ', ' ', ' '};
        assertArrayEquals("'mr john smith' should become 'mr%20john%20smith'",
                "mr%20john%20smith".toCharArray(), problems.urlify(chars, 13));
    }
}
