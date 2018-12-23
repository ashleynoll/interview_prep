import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class StringSearchingTests {
    private static final long TIMEOUT = 200;
    private StringSearchingProblems problems;
    private Integer[] arr;

    @Before
    public void setUp() {
        problems = new StringSearchingProblems();
    }

    @Test(timeout = TIMEOUT)
    public void testFindElement() {
        arr = new Integer[]{ 15, 16, 19, 20, 25, 1, 3, 4, 5, 7, 10, 14 };

        assertEquals("Should find element.", 8, problems.findElement(arr, 5));

        assertEquals("Should not find element.", -1, problems.findElement(arr, 21));
    }

    @Test(timeout = TIMEOUT)
    public void testFindInListy() {
        StringSearchingProblems.Listy listy = new StringSearchingProblems.Listy(new int[]{ 1, 2, 3, 4, 5, 6 });

        assertEquals("Should find correct index.", 4, problems.findInListy(listy, 5));
    }

    @Test(timeout = TIMEOUT)
    public void testFindStringWithEmptyStrings() {
        String[] arr = new String[]{ "", "", "", "", "b", "c", "", "", "d", "", "" };

        assertEquals("Should find b's index", 4, problems.findStringWithEmptyStrings(arr, "b"));
    }

    @Test(timeout = TIMEOUT)
    public void testRankTracker() {
        StringSearchingProblems.RankTracker<Integer> tracker = new StringSearchingProblems.RankTracker<>();

        tracker.track(5);
        tracker.track(10);
        tracker.track(15);
        tracker.track(20);
        tracker.track(1);
        tracker.track(4);

        assertEquals("Rank should match", 0, tracker.getRank(1));
        assertEquals("Rank should match", 1, tracker.getRank(4));
        assertEquals("Rank should match", 2, tracker.getRank(5));
        assertEquals("Rank should match", 3, tracker.getRank(10));
        assertEquals("Rank should match", 4, tracker.getRank(15));
        assertEquals("Rank should match", 5, tracker.getRank(20));
        assertEquals("Rank should match", -1, tracker.getRank(35));
    }
}
