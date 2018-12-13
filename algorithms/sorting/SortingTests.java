import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertArrayEquals;

public class SortingTests {
    private static final long TIMEOUT = 200;
    private SortingProblems problems;
    private Integer[] arr;

    @Before
    public void setUp() {
        problems = new SortingProblems();
    }

    @Test(timeout = TIMEOUT)
    public void testMerge() {
        arr = new Integer[]{ 3, 5, 7, 8, 10, null, null, null, null };
        Integer[] arr2 = new Integer[]{ 1, 2, 4, 9 };

        problems.merge(arr, arr2);
        assertArrayEquals("Expected sorted merged list", new Integer[]{ 1, 2, 3, 4, 5, 7, 8, 9, 10 }, arr);
    }

    @Test(timeout = TIMEOUT)
    public void testSortAnagram() {
        String[] s = new String[]{ "abc", "bba", "bac", "cba", "bab", "ccc" },
                expected = new String[]{ "bba", "bab", "ccc", "abc", "bac", "cba" };

        problems.sortAnagrams(s);
        assertArrayEquals("Expected grouped anagrams", expected, s);
    }

    @Test(timeout = TIMEOUT)
    public void testFindElement() {
        arr = new Integer[]{ 15, 16, 19, 20, 25, 1, 3, 4, 5, 7, 10, 14 };

        assertEquals("Should find element.", 8, problems.findElement(arr, 5));

        assertEquals("Should not find element.", -1, problems.findElement(arr, 21));
    }
}
