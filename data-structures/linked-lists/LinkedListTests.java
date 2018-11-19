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
     * Make a linked list from the given data in the array
     *
     * @param data array of values to make into a linked list
     * @param <T> data type
     * @return a new linked list
     */
    private <T> LinkedListProblems.Node<T> makeLinkedList(T[] data) {
        return makeLinkedList(data, -1);
    }

    /**
     * Make a linked list from the given data in the array and create a cycle
     * starting at the given index
     *
     * @param data array of values to make into a linked list
     * @param cycleStart the starting index for the cycle, -1 if no cycle
     * @param <T> data type
     * @return a new linked list
     */
    private <T> LinkedListProblems.Node<T> makeLinkedList(T[] data, int cycleStart) {
        if (data.length < 1) {
            return null;
        }

        LinkedListProblems.Node<T> head = new LinkedListProblems.Node<>(data[0]),
                temp = head,
                cycleHead = head;
        for (int i = 1; i < data.length; i++) {
            temp.next = new LinkedListProblems.Node<>(data[i]);
            temp = temp.next;
            if (i == cycleStart) {
                cycleHead = temp;
            }
        }
        if (cycleStart != -1) {
            temp.next = cycleHead;
        }

        return head;
    }

    /**
     * Check that all values in a linked list are identical
     */
    private <T> void assertLinkedListEquals(String message, LinkedListProblems.Node<T> expected,
                                        LinkedListProblems.Node<T> actual) {
        if (actual == null && expected == null) {
            return;
        } else if (actual == null || expected == null) {
            System.out.println(message);
        } else if (actual.data != expected.data) {
            System.out.println(message + " - Expected was " + expected.data + " but was actually " + actual.data);
        } else {
            assertLinkedListEquals(message, expected.next, actual.next);
        }
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
        head = new LinkedListProblems.Node<Integer>(8);
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
        head = makeLinkedList(new Integer[]{ 1, 2, 3, 2, 4, 1, 5 });
        expected = makeLinkedList(new Integer[]{ 1, 2, 3, 4, 5});

        assertEquals("Duplicates were not removed from a list with multiple duplicates", expected,
                problems.removeDuplicatesExtraSpace(head));

        assertEquals("Duplicates were not removed from a list with multiple duplicates", expected,
                problems.removeDuplicatesMoreTime(head));
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //           returnKthToLast
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    @Test(timeout = TIMEOUT)
    public void testReturnKthToLastInvalid() {
        head = makeLinkedList(new Integer[]{ 1, 2, 3, 2, 4, 1, 5 });

        assertEquals("If the provided index is negative, -1 should be returned",
                -1, problems.returnKthToLast(head, -2));

        assertEquals("If the provided head is null, -1 should be returned",
                -1, problems.returnKthToLast(null, 1));

        assertEquals("If there are not k elements in the list, -1 should be returned",
                -1, problems.returnKthToLast(head, 20));
    }

    @Test(timeout = TIMEOUT)
    public void testReturnKthToLast() {
        head = makeLinkedList(new Integer[]{ 1, 2, 3, 2, 4, 1, 5 });

        assertEquals("0th to last element of a one node list should be data from head",
                2, problems.returnKthToLast(new LinkedListProblems.Node<>(2), 0));

        assertEquals("0th to last element of a list should be the data from the last node",
                5, problems.returnKthToLast(head, 0));

        assertEquals("6th to last node of 7 node list should be first element",
                1, problems.returnKthToLast(head, 6));

        assertEquals("Should return 2nd to last element",
                4, problems.returnKthToLast(head, 2));
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //       deleteMiddleNode
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    @Test(timeout = TIMEOUT)
    public void testDeleteMiddleNode() {
        head = makeLinkedList(new Integer[]{ 1, 2, 3, 2, 4, 1, 5 });
        LinkedListProblems.Node<Integer> removed = makeLinkedList(new Integer[]{ 1, 2, 3, 2, 1, 5 });

        problems.deleteMiddleNode(head.next.next.next.next);
        assertLinkedListEquals("Should remove from the middle of the given list",
                removed, head);
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //       partitionLinkedList
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    @Test(timeout = TIMEOUT)
    public void testPartitionLinkedList() {
        head = new LinkedListProblems.Node<>(3);
        problems.partitionLinkedList(head, 3);

        assertLinkedListEquals("List of size one should not change",
                new LinkedListProblems.Node<>(3), head);

        head = makeLinkedList(new Integer[]{ 1, 2, 3, 2, 4, 1, 5 });
        problems.partitionLinkedList(head, 3);
        LinkedListProblems.Node<Integer> expected = makeLinkedList(new Integer[]{ 1, 2, 2, 1, 4, 3, 5 });

        assertLinkedListEquals("List should be correctly partitioned around 3", expected, head);

        head = makeLinkedList(new Integer[]{ 1, 2, 3, 2, 4, 1, 5 });
        problems.partitionLinkedList(head, 2);
        expected = makeLinkedList(new Integer[]{ 1, 1, 3, 2, 4, 2, 5 });

        assertLinkedListEquals("List should be correctly partitioned around 2", expected, head);
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //            sumLists
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    @Test(timeout = TIMEOUT)
    public void testSumListsBackwards() {
        LinkedListProblems.Node<Integer> list1 = makeLinkedList(new Integer[]{ 1, 1, 1 }),
                list2 = makeLinkedList(new Integer[]{ 2, 2, 2 });
                expected = makeLinkedList(new Integer[]{ 3, 3, 3 });

        assertLinkedListEquals("111 + 222 = 333", expected, problems.sumListsBackwards(list1, list2));

        list1 = makeLinkedList(new Integer[]{ 9, 9, 9 });
        list2 = new LinkedListProblems.Node<>(1);
        expected = makeLinkedList(new Integer[]{ 0, 0, 0, 1 });

        assertLinkedListEquals("999 + 1 = 1000", expected, problems.sumListsBackwards(list1, list2));

        list1 = makeLinkedList(new Integer[]{ 7, 2, 4 });
        list2 = makeLinkedList(new Integer[]{ 2, 3, 7 });
        expected = makeLinkedList(new Integer[]{ 9, 5, 1, 1 });

        assertLinkedListEquals("427 + 732 = 1159", expected, problems.sumListsBackwards(list1, list2));
    }

    @Test(timeout = TIMEOUT)
    public void testSumListsForwards() {
        LinkedListProblems.Node<Integer> list1 = makeLinkedList(new Integer[]{ 1, 1, 1 }),
                list2 = makeLinkedList(new Integer[]{ 2, 2, 2 });
        expected = makeLinkedList(new Integer[]{ 3, 3, 3 });

        assertLinkedListEquals("111 + 222 = 333", expected, problems.sumListsForwards(list1, list2));

        list1 = makeLinkedList(new Integer[]{ 9, 9, 9 });
        list2 = new LinkedListProblems.Node<>(1);
        expected = makeLinkedList(new Integer[]{ 1, 0, 0, 0 });

        assertLinkedListEquals("999 + 1 = 1000", expected, problems.sumListsForwards(list1, list2));

        list1 = makeLinkedList(new Integer[]{ 7, 2, 4 });
        list2 = makeLinkedList(new Integer[]{ 2, 3, 7 });
        expected = makeLinkedList(new Integer[]{ 9, 6, 1 });

        assertLinkedListEquals("724 + 237 = 961", expected, problems.sumListsForwards(list1, list2));
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //      isLinkedListPalindrome
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    @Test(timeout = TIMEOUT)
    public void testIsLinkedListPalindromeTrue() {
        assertTrue("Empty linked list should be a palindrome.", problems.isLinkedListPalindrome(null));

        // Even length
        LinkedListProblems.Node<Character> head = makeLinkedList(new Character[]{ 'a', 'b', 'b', 'a' });
        assertTrue("abba should be a palindrome.", problems.isLinkedListPalindrome(head));

        // Odd length
        head = makeLinkedList(new Character[]{ 'c', 'b', 'a', 'b', 'c' });
        assertTrue("cbabc should be a palindrome.", problems.isLinkedListPalindrome(head));

    }

    @Test(timeout = TIMEOUT)
    public void testIsLinkedListPalindromeFalse() {
        // Even length
        LinkedListProblems.Node<Character> head = makeLinkedList(new Character[]{ 'a', 'b', 'b', 'c' });
        assertFalse("abbc should not be a palindrome.", problems.isLinkedListPalindrome(head));

        // Odd length
        head = makeLinkedList(new Character[]{ 'c', 'a', 'a', 'b', 'c' });
        assertFalse("cbabc should not be a palindrome.", problems.isLinkedListPalindrome(head));
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //    areLinkedListsIntersecting
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    @Test(timeout = TIMEOUT)
    public void testAreLinkedListsIntersectingNo() {
        head = makeLinkedList(new Integer[]{ 1, 2, 3 });

        assertEquals("Two non-intersecting lists should return null.", null,
                problems.areLinkedListsIntersecting(head, makeLinkedList(new Integer[]{ 1, 2, 3 })));
    }

    @Test(timeout = TIMEOUT)
    public void testAreLinkedListsIntersectingYes() {
        head = makeLinkedList(new Integer[]{ 1, 2, 3 });
        LinkedListProblems.Node<Integer> intersecting = makeLinkedList(new Integer[]{ 1, 2 });
        intersecting.next.next = head;

        assertEquals("Two non-intersecting lists should return null.", head,
                problems.areLinkedListsIntersecting(head, intersecting));
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //       findHeadOfCycle
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    @Test(timeout = TIMEOUT)
    public void testFindCycleStartNoCycle() {
        head = makeLinkedList(new Integer[]{ 1, 2, 3, 4, 5, 6, 7 });

        assertEquals("Linear list should return null", null,
                problems.findHeadOfCycle(head));
    }

    @Test(timeout = TIMEOUT)
    public void testFindCycleStart() {
        head = makeLinkedList(new Integer[]{ 1, 2, 3, 4, 5, 6, 7 }, 0);

        assertEquals("Cycle incorrectly detected", head,
                problems.findHeadOfCycle(head));

        head = makeLinkedList(new Integer[]{ 1, 2, 3, 4, 5, 6, 7 }, 1);

        assertEquals("Cycle incorrectly detected", head.next,
                problems.findHeadOfCycle(head));

        head = makeLinkedList(new Integer[]{ 1, 2, 3, 4, 5, 6, 7 }, 6);

        assertEquals("Cycle incorrectly detected", head.next.next.next.next.next.next,
                problems.findHeadOfCycle(head));
    }
}
