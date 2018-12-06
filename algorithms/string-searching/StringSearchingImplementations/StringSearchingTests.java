package StringSearchingImplementations;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.*;

import static org.junit.Assert.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class StringSearchingTests {
    private static final int TIMEOUT = 200;

    private SearchableString pattern;
    private SearchableString text;
    private List<Integer> indices;
    private Map<Character, Integer> lt;

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void k0KMPNullPattern() {
        try {
            List<Integer> list = StringSearching.kmp(null, new SearchableString("abc"));
        } catch (IllegalArgumentException e) {
            assertEquals(e.getClass(), IllegalArgumentException.class);
            throw e;
        }
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void k0KMPNullText() {
        try {
            List<Integer> list = StringSearching.kmp(new SearchableString("abc"), null);
        } catch (IllegalArgumentException e) {
            assertEquals(e.getClass(), IllegalArgumentException.class);
            throw e;
        }
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void k0KMPEmptyPattern() {
        try {
            List<Integer> list = StringSearching.kmp(new SearchableString(""), new SearchableString("abc"));
        } catch (IllegalArgumentException e) {
            assertEquals(e.getClass(), IllegalArgumentException.class);
            throw e;
        }
    }

    @Test(timeout = TIMEOUT)
    public void k1KMPEmptyText() {
        SearchableString pattern = new SearchableString("abc");
        SearchableString text = new SearchableString("");
        List<Integer> list = StringSearching.kmp(pattern, text);
        assertEquals(new ArrayList<Integer>(), list);
        assertEquals(0, text.getCount());
    }

    @Test(timeout = TIMEOUT)
    public void k2KMPMultipleLocationsSingleLetterAlphabet() {
        SearchableString pattern = new SearchableString("aaa");
        SearchableString text = new SearchableString("aaaaaaaa");
        /*  0 1 2 3 4 5
            a a a a a a a a
            a a a | | | | |     3
              _ _ a | | | |     1
                _ _ a | | |     1
                  _ _ a | |     1
                    _ _ a |     1
                      _ _ a     1

            Total               8   [0, 1, 2, 3, 4, 5]
         */
        List<Integer> actual = StringSearching.kmp(pattern, text);
        List<Integer> expected = Arrays.asList(0, 1, 2, 3, 4, 5);
        assertEquals(8, text.getCount());
        assertEquals(expected, actual);
    }

    @Test(timeout = TIMEOUT)
    public void k3KMPPatternLongerThanText() {
        SearchableString pattern = new SearchableString("aaaaaa");
        SearchableString text = new SearchableString("aa");
        List<Integer> list = StringSearching.kmp(pattern, text);
        assertEquals(new ArrayList<Integer>(), list);
        assertEquals(0, text.getCount());
    }

    @Test(timeout = TIMEOUT)
    public void k4KMPMultipleLocationsRandom() {
        SearchableString pattern = new SearchableString("abacab");
        SearchableString text = new SearchableString("abacacababacabacabadabacab");
        /*  Capital letter = MISMATCH
            $ = match index
            _ = not compared due to KMP failure table

                            $       $               $
            a b a c a c a b a b a c a b a c a b a d a b a c a b
            a b a c a B |   |       |       |   | |                     6
                    _ B a c a b     |       |   | |                     1
                      A b a c a b   |       |   | |                     1
                        a b a C a b |       |   | |                     4
                            _ b a c a b     |   | |                     5
                                    _ _ a c a b | |                     4
                                            _ _ a C a b                 2
                                                _ B a c a b             1
                                                  A b a c a b           1
                                                    a b a c a b         6

            Total:                                                      31
         */
        List<Integer> actual = StringSearching.kmp(pattern, text);
        List<Integer> expected = Arrays.asList(8, 12, 20);
        assertEquals(expected, actual);
        assertEquals(31, text.getCount());
    }

    @Test(timeout = TIMEOUT)
    public void k5KMPEndLocation() {
        SearchableString pattern = new SearchableString("aah");
        SearchableString text = new SearchableString("aaaaaaaaaah");
        /*  Capital letter = MISMATCH
            a a a a a a a a a a h
            a a H | | | | | | | |       3
              _ a H | | | | | | |       2
                _ a H | | | | | |       2
                  _ a H | | | | |       2
                    _ a H | | | |       2
                      _ a H | | |       2
                        _ a H | |       2
                          _ a H |       2
                            _ a h       2
            Total                       19
         */
        List<Integer> actual = StringSearching.kmp(pattern, text);
        List<Integer> expected = Arrays.asList(8);
        assertEquals(expected, actual);
        assertEquals(19, text.getCount());
    }

    @Test(timeout = TIMEOUT)
    public void k6KMPMultipleLocationsDeterministic() {
        SearchableString pattern = new SearchableString("abab");
        SearchableString text = new SearchableString("ababababababababab");
        /*
            a b a b a b a b a b a b a b a b a b
            a b a b                                     4
                _ _ a b                                 2
                    _ _ a b                             2
                        _ _ a b                         2
                            _ _ a b                     2
                                _ _ a b                 2
                                    _ _ a b             2
                                        _ _ a b         2


            Total:                                      18
         */
        List<Integer> actual = StringSearching.kmp(pattern, text);
        List<Integer> expected = Arrays.asList(0, 2, 4, 6, 8, 10, 12, 14);
        assertEquals(18, text.getCount());
        assertEquals(expected, actual);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void k7FailureTableNullPattern() {
        try {
            int[] fail = StringSearching.buildFailureTable(null);
        } catch (IllegalArgumentException e) {
            assertEquals(e.getClass(), IllegalArgumentException.class);
            throw e;
        }
    }

    @Test(timeout = TIMEOUT)
    public void k8FailureTableNoPrefixSuffix() {
        SearchableString pattern = new SearchableString("ABCDE");
        int[] actualTable = StringSearching.buildFailureTable(pattern);
        int[] expectedTable = {0, 0, 0, 0, 0};
        assertArrayEquals(expectedTable, actualTable);
        assertTrue("Too many comparisons. Expected " + 8 + " but was " + pattern.getCount(), pattern.getCount() <= 8);
    }

    @Test(timeout = TIMEOUT)
    public void k9FailureTableSameLetter() {
        SearchableString pattern = new SearchableString("AAAAA");
        int[] actualTable = StringSearching.buildFailureTable(pattern);
        int[] expectedTable = {0, 1, 2, 3, 4};
        assertArrayEquals(expectedTable, actualTable);
        assertTrue("Too many comparisons. Expected " + 8 + " but was " + pattern.getCount(), pattern.getCount() <= 8);
    }

    @Test(timeout = TIMEOUT)
    public void k10FailureTableEasy() {
        SearchableString pattern = new SearchableString("abacab");
        int[] actualTable = StringSearching.buildFailureTable(pattern);
        int[] expectedTable = {0, 0, 1, 0, 1, 2};
        assertArrayEquals(expectedTable, actualTable);
        assertTrue("Too many comparisons. Expected " + 12 + " but was " + pattern.getCount(), pattern.getCount() <= 12);
    }

    @Test(timeout = TIMEOUT)
    public void k11FailureTableHard() {
        SearchableString pattern = new SearchableString("ABABCFDABABABCA");
        int[] actualTable = StringSearching.buildFailureTable(pattern);
        int[] expectedTable = {0, 0, 1, 2, 0, 0, 0, 1, 2, 3, 4, 3, 4, 5, 1};
        assertArrayEquals(expectedTable, actualTable);
        assertTrue("Too many comparisons. Expected " + 34 + " but was " + pattern.getCount(), pattern.getCount() <= 34);
    }

    @Test(timeout = TIMEOUT)
    public void k12FailureTableEmptyPattern() {
        SearchableString pattern = new SearchableString("");
        int[] actualTable = StringSearching.buildFailureTable(pattern);
        int[] expectedTable = {};
        assertArrayEquals(expectedTable, actualTable);
    }

    @Test(timeout = TIMEOUT)
    public void h0GenerateHashNullCurrentOrInvalidLength() {
        // null current
        try {
            int bad = StringSearching.generateHash(null, 0);
        } catch (IllegalArgumentException e) {

        } catch (Throwable e) {
            assertEquals("Didn't handle null current input", e.getClass(), IllegalArgumentException.class);
        }
        // negative length
        try {
            int bad = StringSearching.generateHash("abc", -1);
        } catch (IllegalArgumentException e) {

        } catch (Throwable e) {
            assertEquals("Didn't handle negative length input", e.getClass(), IllegalArgumentException.class);
        }
        // length longer than string length
        try {
            int bad = StringSearching.generateHash("abc", 7);
        } catch (IllegalArgumentException e) {

        } catch (Throwable e) {
            assertEquals("Didn't handle length input > pattern length", e.getClass(), IllegalArgumentException.class);
        }
        // length = 0
        try {
            int bad = StringSearching.generateHash("abc", 0);
        } catch (IllegalArgumentException e) {

        } catch (Throwable e) {
            assertEquals("Didn't handle length of 0", e.getClass(), IllegalArgumentException.class);
        }
    }

    @Test(timeout = TIMEOUT)
    public void u0UpdateHashNegativeOrLength() {
        // negative length
        try {
            int bad = StringSearching.updateHash(384843948, -1, 'k', 'l');
        } catch (IllegalArgumentException e) {

        } catch (Throwable e) {
            assertEquals("Didn't handle negative length", e.getClass(), IllegalArgumentException.class);
        }
        // length of 0
        try {
            int bad = StringSearching.updateHash(384843948, 0, 'k', 'l');
        } catch (IllegalArgumentException e) {

        } catch (Throwable e) {
            assertEquals("Didn't handle zero length", e.getClass(), IllegalArgumentException.class);
        }
    }

    @Test(timeout = TIMEOUT)
    public void h1GenerateHashGeneral() {
        int expected = 38457110;
        int actual = StringSearching.generateHash(new SearchableString("kmp"), 3);
        assertEquals(expected, actual);
    }

    @Test(timeout = TIMEOUT)
    public void u1UpdateHashGeneral() {
        int expected = 39176430;
        int actual = StringSearching.updateHash(38457110, 3, 'k', '!');
        assertEquals(expected, actual);
    }

    private SearchableString s(String str) {
        return new SearchableString(str);
    }

    private List<Integer> i(Integer... indices) {
        return new ArrayList<>(Arrays.asList(indices));
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void bm01PatternNull() {
        try {
            StringSearching.boyerMoore(null, s("aa"));
        } catch (IllegalArgumentException e) {
            throw e;
        }
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void bm02PatternEmpty() {
        try {
            StringSearching.boyerMoore(s(""), s("aa"));
        } catch (IllegalArgumentException e) {
            throw e;
        }
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void bm03TextNull() {
        try {
            StringSearching.boyerMoore(s("aa"), null);
        } catch (IllegalArgumentException e) {
            throw e;
        }
    }

    @Test(timeout = TIMEOUT)
    public void bm04TextEmpty() {
        text = s("");
        indices = StringSearching.boyerMoore(s("aa"), text);
        assertEquals(0, indices.size());
        assertEquals(0, text.getCount());
    }

    @Test(timeout = TIMEOUT)
    public void bm05OneAplhabetBothSingle() {
        pattern = s("a");
        text = s("a");
        indices = StringSearching.boyerMoore(pattern, text);
        assertEquals(i(0), indices);
        assertEquals(1, text.getCount());
    }

    @Test(timeout = TIMEOUT)
    public void bm06OneAlphabetSinglePatternLongText() {
        pattern = s("a");
        text = s("aaaaaaaa");
        indices = StringSearching.boyerMoore(pattern, text);
        assertEquals(i(0, 1, 2, 3, 4, 5, 6, 7), indices);
        assertTrue(text.getCount() <= 8);
    }

    @Test(timeout = TIMEOUT)
    public void bm07OneAlphabetMediumPatternLongText() {
        pattern = s("aaaa");
        text = s("aaaaaaaa");
        indices = StringSearching.boyerMoore(pattern, text);
        assertEquals(i(0, 1, 2, 3, 4), indices);
        assertTrue(text.getCount() <= 20);
    }

    @Test(timeout = TIMEOUT)
    public void bm08OneAlphabetBothLong() {
        pattern = s("aaaaaaaa");
        text = s("aaaaaaaa");
        indices = StringSearching.boyerMoore(pattern, text);
        assertEquals(i(0), indices);
        assertTrue(text.getCount() <= 8);
    }

    @Test(timeout = TIMEOUT)
    public void bm09NoOverlaps() {
        pattern = s("abc");
        text = s("abcdabc");
        indices = StringSearching.boyerMoore(pattern, text);
        assertEquals(i(0, 4), indices);
        assertTrue(text.getCount() <= 7);
    }

    @Test(timeout = TIMEOUT)
    public void bm10WithOverlapsAndShiftPastEnd() {
        pattern = s("aabaa");
        text = s("baabaabaacab");
        indices = StringSearching.boyerMoore(pattern, text);
        assertEquals(i(1, 4), indices);
        assertTrue(text.getCount() <= 14);
    }

    @Test(timeout = TIMEOUT)
    public void bm11LongTest() {
        pattern = s("BCB");
        text = s("ACBDABCADBCBCDADBBCDBCABCBDADCBBBCBAD");
        indices = StringSearching.boyerMoore(pattern, text);
        assertEquals(i(9, 23, 32), indices);
        assertTrue(text.getCount() <= 34);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void blt01NullPattern() {
        try {
            StringSearching.buildLastTable(null);
        } catch (IllegalArgumentException e) {
            throw e;
        }
    }

    @Test(timeout = TIMEOUT)
    public void blt02EmptyPattern() {
        pattern = s("");
        lt = StringSearching.buildLastTable(pattern);
        assertTrue(lt.isEmpty());
    }

    @Test(timeout = TIMEOUT)
    public void blt03NoRepeats() {
        pattern = s("ABCDE");
        lt = StringSearching.buildLastTable(pattern);
        Map<Character, Integer> expected = new HashMap<>();
        expected.put('A', 0);
        expected.put('B', 1);
        expected.put('C', 2);
        expected.put('D', 3);
        expected.put('E', 4);
        assertEquals(expected, lt);
        assertTrue("Too many comparisons. Expected " + 5 + " but was " + pattern.getCount(), pattern.getCount() <= 5);
    }

    @Test(timeout = TIMEOUT)
    public void blt04WithRepeats() {
        pattern = s("ABCADBEACD");
        lt = StringSearching.buildLastTable(pattern);
        Map<Character, Integer> expected = new HashMap<>();
        expected.put('A', 7);
        expected.put('B', 5);
        expected.put('C', 8);
        expected.put('D', 9);
        expected.put('E', 6);
        assertEquals(expected, lt);
        assertTrue("Too many comparisons. Expected " + 10 + " but was " + pattern.getCount(), pattern.getCount() <= 10);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void rk01PatternNull() {
        try {
            StringSearching.rabinKarp(null, s("aa"));
        } catch (IllegalArgumentException e) {
            throw e;
        }
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void rk02PatternEmpty() {
        try {
            StringSearching.rabinKarp(s(""), s("aa"));
        } catch (IllegalArgumentException e) {
            throw e;
        }
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void rk03TextNull() {
        try {
            StringSearching.rabinKarp(s("aa"), null);
        } catch (IllegalArgumentException e) {
            throw e;
        }
    }

    @Test(timeout = TIMEOUT)
    public void rk04TextEmpty() {
        text = s("");
        indices = StringSearching.rabinKarp(s("aa"), text);
        assertEquals(0, indices.size());
        assertEquals(0, text.getCount());
    }

    @Test(timeout = TIMEOUT)
    public void rk05OneAplhabet() {
        pattern = s("aaaa");
        text = s("aaaaaaaa");
        indices = StringSearching.rabinKarp(pattern, text);
        assertEquals(i(0, 1, 2, 3, 4), indices);
        assertEquals(24, pattern.getCount());
        assertEquals(32, text.getCount());
    }

    @Test(timeout = TIMEOUT)
    public void rk06MultipleMatches() {
        pattern = s("ABCAB");
        text = s("ABCABCABCACAD");
        indices = StringSearching.rabinKarp(pattern, text);
        List<Integer> expected = Arrays.asList(0, 3);
        assertEquals(expected, indices);
        assertEquals(15, pattern.getCount());
        assertEquals(31, text.getCount());
    }

    @Test(timeout = TIMEOUT)
    public void rk07NoMatches() {
        pattern = s("ABC");
        text = s("CBACDABAC");
        indices = StringSearching.rabinKarp(pattern, text);
        assertTrue(indices.isEmpty());
        assertEquals(3, pattern.getCount());
        assertEquals(15, text.getCount());
    }

    @Test(timeout = TIMEOUT)
    public void rk08HashCollision() {
        text = s("bu\u0002\u0005\u0009\u0002ffering");
        pattern = s("\u0002\u0005\u0008\u01B3");
        indices = StringSearching.rabinKarp(pattern, text);
        assertTrue(indices.isEmpty());
        assertTrue(text.getCount() <= 25);
    }
}
