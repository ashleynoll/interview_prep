import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tests for the various Linked List problems
 */
public class LinkedListTests {
    private static final long TIMEOUT = 200;
    private LinkedListProblems problems;
    private LinkedListProblems.Node<Integer> head;
    private LinkedListProblems.Node<Integer> expected;

    @Before
    public void setUp() {
        problems = new LinkedListProblems();
    }

    /**
     * Makes a new linked list of 1 -> 2 -> 3 -> 2 -> 4 -> 1 -> 5
     *
     * @return the
     */
    private LinkedListProblems.Node<Integer> makeSimpleList() {
        return new LinkedListProblems.Node<>(1, new LinkedListProblems.Node<>(2,
                new LinkedListProblems.Node<>(3, new LinkedListProblems.Node<>(2,
                        new LinkedListProblems.Node<>(4, new LinkedListProblems.Node<>(1,
                                new LinkedListProblems.Node<>(5)))))));
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //      removeDuplicates (both)
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    @Test(timeout = TIMEOUT)
    public void testRemoveDuplicatesEmpty() {
        assertEquals("Null head should return null", null,
                problems.removeDuplicatesExtraSpace(null));

        assertEquals("Null head should return null", null,
                problems.removeDuplicatesMoreTime(null));
    }

    @Test(timeout = TIMEOUT)
    public void testRemoveDuplicatesOneNode() {
        head = new LinkedListProblems.Node<>(8);
        assertEquals("A single node should not be modified", head,
                problems.removeDuplicatesExtraSpace(head));

        assertEquals("A single node should not be modified", head,
                problems.removeDuplicatesMoreTime(head));
    }

    @Test(timeout = TIMEOUT)
    public void testRemoveDuplicatesOneDuplicate() {
        head = new LinkedListProblems.Node<>(8, new LinkedListProblems.Node<>(8));
        expected = new LinkedListProblems.Node<>(8);

        assertEquals("Duplicate was not removed when only one duplicate exists", expected,
                problems.removeDuplicatesExtraSpace(head));

        assertEquals("Duplicate was not removed when only one duplicate exists", expected,
                problems.removeDuplicatesMoreTime(head));
    }

    @Test(timeout = TIMEOUT)
    public void testRemoveDuplicatesSimpleList() {
        head = makeSimpleList();
        expected = new LinkedListProblems.Node<>(1, new LinkedListProblems.Node<>(2,
                        new LinkedListProblems.Node<>(3, new LinkedListProblems.Node<>(4,
                                new LinkedListProblems.Node<>(5)))));

        assertEquals("Duplicates were not removed from a list with multiple duplicates", expected,
                problems.removeDuplicatesExtraSpace(head));

        assertEquals("Duplicates were not removed from a list with multiple duplicates", expected,
                problems.removeDuplicatesMoreTime(head));
    }
}
