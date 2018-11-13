import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.lang.reflect.Field;

import static junit.framework.TestCase.assertSame;
import static org.junit.Assert.*;

/**
 * Extensive testing for DoublyLinkedList
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DoublyLinkedListTests {
    private static final int TIMEOUT = 200;

    private DoublyLinkedList<String> expectedList = new DoublyLinkedList<>();
    private DoublyLinkedList<String> actualList = new DoublyLinkedList<>();
    private String[] nodeData = {"Yes", "I'm", "using", "an", "array", "to", "back", "node", "data", "for", "a", "LinkedList"};


    // ----- HELPER METHODS -----

    @Before
    public void setup() {
        actualList = new DoublyLinkedList<>();
        expectedList = new DoublyLinkedList<>();
    }

    /**
     * Sets the student's DLL to the following.
     *
     * @param list the list to be set
     * @param head the new head of the student's DLL
     * @param tail the new tail of the student's DLL
     * @param size the new size of the student's DLL
     * @param <T>  the type of the DLL
     */
    private <T> void setDLL(DoublyLinkedList<T> list, DoublyLinkedListNode<T> head, DoublyLinkedListNode<T> tail, int size) {
        try {
            Field field = DoublyLinkedList.class.getDeclaredField("head");
            field.setAccessible(true);
            field.set(list, head);

            field = DoublyLinkedList.class.getDeclaredField("tail");
            field.setAccessible(true);
            field.set(list, tail);

            field = DoublyLinkedList.class.getDeclaredField("size");
            field.setAccessible(true);
            field.set(list, size);

        } catch (IllegalAccessException | NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Creates a DLL
     *
     * @param nodeData data to populate list with
     * @param list     The list to set
     * @param start    Start index, in [0, 11]. Inclusive.
     * @param end      End index, in [0, 12]. Exclusive.
     */
    private void makeDLLRange(String[] nodeData, DoublyLinkedList<String> list, int start, int end) {
        DoublyLinkedListNode<String> head = new DoublyLinkedListNode<>(nodeData[start]);
        if (start == end - 1) {
            setDLL(list, head, head, end - start);
        } else {
            DoublyLinkedListNode<String> cur = head;
            DoublyLinkedListNode<String> temp = null;
            for (int i = start + 1; i < end; i++) {
                temp = new DoublyLinkedListNode<>(nodeData[i]);
                cur.setNext(temp);
                temp.setPrevious(cur);
                cur = cur.getNext();
            }
            setDLL(list, head, temp, end - start);
        }
    }

    /**
     * Checks the student's DLL against the expected DLL.
     *
     * @param expectedList the expected DLL
     * @param actualList   the student's DLL
     * @param <T>          the type of DLL
     */
    private <T> void assertDLLEquals(DoublyLinkedList<T> expectedList, DoublyLinkedList<T> actualList) {
        int expectedSize = expectedList.size();
        int actualSize = actualList.size();
        assertEquals("List is of incorrect size (Expected " + expectedSize + ")", expectedSize, actualSize);
        if (expectedSize == 0) {
            assertNull("Head is not null", actualList.getHead());
            assertNull("Tail is not null", actualList.getTail());
        } else {
            DoublyLinkedListNode<T> expectedNode = expectedList.getHead();
            DoublyLinkedListNode<T> actualNode = actualList.getHead();
            for (int i = 0; expectedNode != null; expectedNode = expectedNode.getNext(), actualNode = actualNode.getNext(), i++) {
                assertEquals("Incorrect element at index " + i, expectedNode.getData(), actualNode.getData());
                if (expectedNode.getPrevious() == null) {
                    assertNull("Previous pointer for head " + expectedNode.getData() + " is set " + "incorrectly", actualNode.getPrevious());
                } else {
                    assertEquals("Incorrect prev pointer at index " + i, expectedNode.getPrevious().getData(), actualNode.
                            getPrevious().getData());
                }
                if (expectedNode.getNext() == null) {
                    assertNull("Next pointer for tail set incorrectly", actualNode.getNext());
                } else {
                    assertEquals("Incorrect next pointer at index " + i, expectedNode.getNext().getData(), actualNode.
                            getNext().getData());
                }


            }

            if (expectedSize == 1) {
                assertSame("head and tail do not point to the same node", actualList.getHead(), actualList.getTail());
            }
        }
    }

    //AddAtIndex Tests

    //1 point
    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testAddAtIndexNullData() {
        try {
            actualList.addAtIndex(0, null);
        } catch (IllegalArgumentException e) {
            assertEquals(IllegalArgumentException.class, e.getClass());
            throw e;
        }
    }

    //1 point
    @Test(timeout = TIMEOUT, expected = IndexOutOfBoundsException.class)
    public void testAddAtIndexTooLargeIndex() {
        try {
            actualList.addAtIndex(7, "Strawberries");
        } catch (IndexOutOfBoundsException e) {
            assertEquals(IndexOutOfBoundsException.class, e.getClass());
            throw e;
        }
    }

    //1 point
    @Test(timeout = TIMEOUT, expected = IndexOutOfBoundsException.class)
    public void testAddAtIndexNegativeIndex() {
        try {
            actualList.addAtIndex(-3, "Watermelon");
        } catch (IndexOutOfBoundsException e) {
            assertEquals(IndexOutOfBoundsException.class, e.getClass());
            throw e;
        }
    }

    //1 point
    @Test(timeout = TIMEOUT)
    public void testAddAtIndexEmptyList() {
        String[] actualFruits1 = {"Grape"};
        makeDLLRange(actualFruits1, expectedList, 0, actualFruits1.length);
        actualList.addAtIndex(0, "Grape"); //add to index 0
        assertDLLEquals(expectedList, actualList);
        assertSame("Head and tail references do not match", actualList.getHead(), actualList.getTail());
    }

    //1 point
    @Test(timeout = TIMEOUT)
    public void testAddAtIndexSizeofDLL() {
        String[] expectedFruits5 = {"Banana", "Grape", "Orange", "Raspberry", "Pear"};
        String[] expectedFruits6 = {"Banana", "Grape", "Orange", "Raspberry", "Pear", "Cherry"};
        makeDLLRange(expectedFruits5, actualList, 0, expectedFruits5.length);
        actualList.addAtIndex(5, "Cherry"); //add to index 5

        makeDLLRange(expectedFruits6, expectedList, 0, expectedFruits6.length);

        assertDLLEquals(expectedList, actualList);
    }

    //2 points
    @Test(timeout = TIMEOUT)
    public void testAddAtIndexFrontAndBackOfDLL() {
        String[] actualFruits1 = {"Grape"};
        String[] actualFruits2 = {"Grape", "Raspberry"};
        String[] actualFruits3 = {"Grape", "Raspberry", "Pear"};
        String[] actualFruits4 = {"Orange", "Grape", "Raspberry", "Pear"};

        makeDLLRange(actualFruits1, expectedList, 0, actualFruits1.length);

        actualList.addAtIndex(0, "Grape"); //add to index 0
        assertDLLEquals(expectedList, actualList);

        makeDLLRange(actualFruits2, expectedList, 0, actualFruits2.length);

        actualList.addAtIndex(1, "Raspberry"); //add to back
        assertDLLEquals(expectedList, actualList);

        makeDLLRange(actualFruits3, expectedList, 0, actualFruits3.length);

        actualList.addAtIndex(2, "Pear"); //add to back
        assertDLLEquals(expectedList, actualList);

        makeDLLRange(actualFruits4, expectedList, 0, actualFruits4.length);

        actualList.addAtIndex(0, "Orange"); //add to front
        assertDLLEquals(expectedList, actualList);

    }

    //2 points
    @Test(timeout = TIMEOUT)
    public void testAddAtIndexMiddleOfDLL() {
        String[] actualFruits2 = {"Grape", "Raspberry"};
        String[] actualFruits3 = {"Grape", "Pear", "Raspberry"};
        String[] actualFruits4 = {"Grape", "Pear", "Orange", "Raspberry"};
        String[] actualFruits5 = {"Grape", "Pear", "Orange", "Mango", "Raspberry"};


        makeDLLRange(actualFruits3, expectedList, 0, actualFruits3.length);
        makeDLLRange(actualFruits2, actualList, 0, actualFruits2.length);


        actualList.addAtIndex(1, "Pear");
        assertDLLEquals(expectedList, actualList);

        makeDLLRange(actualFruits4, expectedList, 0, actualFruits4.length);
        actualList.addAtIndex(2, "Orange");
        assertDLLEquals(expectedList, actualList);

        makeDLLRange(actualFruits5, expectedList, 0, actualFruits5.length);
        actualList.addAtIndex(3, "Mango");
        assertDLLEquals(expectedList, actualList);

    }

    //addToFront Tests

    //1 point
    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testAddToFrontNullData() {
        try {
            actualList.addToFront(null);
        } catch (IllegalArgumentException e) {
            assertEquals(IllegalArgumentException.class, e.getClass());
            throw e;
        }
    }

    //1 point
    @Test(timeout = TIMEOUT)
    public void testAddToFront() {
        String[] actualFruits2 = {"Raspberry", "Grape"};
        String[] actualFruits3 = {"Pear", "Raspberry", "Grape"};
        String[] actualFruits4 = {"Orange", "Pear", "Raspberry", "Grape"};

        makeDLLRange(actualFruits2, actualList, 0, actualFruits2.length);
        makeDLLRange(actualFruits3, expectedList, 0, actualFruits3.length);

        actualList.addToFront("Pear");
        assertDLLEquals(expectedList, actualList);

        makeDLLRange(actualFruits4, expectedList, 0, actualFruits4.length);

        actualList.addToFront("Orange");
        assertDLLEquals(expectedList, actualList);
    }

    //1 point
    @Test(timeout = TIMEOUT)
    public void testAddToFrontEmptyList() {
        String[] actualFruits1 = {"Apple"};
        makeDLLRange(actualFruits1, expectedList, 0, actualFruits1.length);
        actualList.addToFront("Apple"); //add to index 0
        assertDLLEquals(expectedList, actualList);
    }

    //addToBack Tests

    //1 point
    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testAddToBackNullData() {
        try {
            actualList.addToBack(null);
        } catch (IllegalArgumentException e) {
            assertEquals(IllegalArgumentException.class, e.getClass());
            throw e;
        }
    }


    //1 point
    @Test(timeout = TIMEOUT)
    public void testAddToBackEmptyList() {
        String[] actualFruits1 = {"Lemon"};
        makeDLLRange(actualFruits1, expectedList, 0, actualFruits1.length);
        actualList.addToBack("Lemon"); //add to index 0
        assertDLLEquals(expectedList, actualList);
    }

    //1 point
    @Test(timeout = TIMEOUT)
    public void testAddToBack() {
        String[] actualFruits2 = {"Grape", "Raspberry"};
        String[] actualFruits3 = {"Grape", "Raspberry", "Pear"};
        String[] actualFruits4 = {"Grape", "Raspberry", "Pear", "Orange"};

        makeDLLRange(actualFruits2, actualList, 0, actualFruits2.length);
        makeDLLRange(actualFruits3, expectedList, 0, actualFruits3.length);

        actualList.addToBack("Pear");
        assertDLLEquals(expectedList, actualList);

        makeDLLRange(actualFruits4, expectedList, 0, actualFruits4.length);

        actualList.addToBack("Orange");
        assertDLLEquals(expectedList, actualList);

    }

    //get Tests

    //1 point
    @Test(timeout = TIMEOUT, expected = IndexOutOfBoundsException.class)
    public void testGetTooLargeIndex() {
        String[] actualFruits4 = {"Orange", "Pear", "Raspberry", "Grape"};
        makeDLLRange(actualFruits4, actualList, 0, actualFruits4.length);
        makeDLLRange(actualFruits4, expectedList, 0, actualFruits4.length);
        try {
            actualList.get(12);
        } catch (IndexOutOfBoundsException e) {
            try {
                actualList.get(actualFruits4.length);
            } catch (IndexOutOfBoundsException f) {
                assertEquals(IndexOutOfBoundsException.class, f.getClass());
                assertDLLEquals(expectedList, actualList);
            }
            assertEquals(IndexOutOfBoundsException.class, e.getClass());
            throw e;
        }
    }

    //1 point
    @Test(timeout = TIMEOUT, expected = IndexOutOfBoundsException.class)
    public void testGetNegativeIndex() {
        try {
            actualList.get(-1);
        } catch (IndexOutOfBoundsException e) {
            assertEquals(IndexOutOfBoundsException.class, e.getClass());
            throw e;
        }
    }


    //2 points
    @Test(timeout = TIMEOUT)
    public void testGetDLLSizeOfOne() {
        String[] actualFruits1 = {"Nectarine"};
        makeDLLRange(actualFruits1, actualList, 0, actualFruits1.length);
        assertEquals("Data should be " + actualFruits1[0], actualFruits1[0], actualList.get(0));
    }

    //4 points
    @Test(timeout = TIMEOUT)
    public void testGet() {
        String[] actualFruits4 = {"Orange", "Pear", "Raspberry", "Grape"};
        makeDLLRange(actualFruits4, actualList, 0, actualFruits4.length);
        makeDLLRange(actualFruits4, expectedList, 0, actualFruits4.length);

        assertEquals("Data should be " + actualFruits4[3], actualFruits4[3], actualList.get(3));
        assertDLLEquals(actualList, expectedList);
        assertEquals("Data should be " + actualFruits4[2], actualFruits4[2], actualList.get(2));
        assertDLLEquals(actualList, expectedList);
    }


    //toArray Tests

    //3 points
    @Test(timeout = TIMEOUT)
    public void testToArray() {
        String[] actualFruits4 = {"Orange", "Pear", "Raspberry", "Grape"};
        makeDLLRange(actualFruits4, actualList, 0, actualFruits4.length);
        makeDLLRange(actualFruits4, expectedList, 0, actualFruits4.length);

        assertArrayEquals("Arrays do not match", actualFruits4, actualList.toArray());
        assertDLLEquals(actualList, expectedList);
    }

    //1 point
    @Test(timeout = TIMEOUT)
    public void testToArrayEmpty() {
        assertArrayEquals("empty list must produce empty array", new Object[0], actualList.toArray());
        assertDLLEquals(new DoublyLinkedList<>(), actualList);
    }

    //clear Test

    //5 points
    @Test(timeout = TIMEOUT)
    public void testClear() {
        String[] actualFruits4 = {"Orange", "Pear", "Raspberry", "Grape"};
        makeDLLRange(actualFruits4, actualList, 0, actualFruits4.length);
        makeDLLRange(actualFruits4, expectedList, 0, actualFruits4.length);

        actualList.clear();
        assertDLLEquals(new DoublyLinkedList<>(), actualList);

    }

    //isEmpty Tests

    //1 point
    @Test(timeout = TIMEOUT)
    public void testisEmptyEmptyList() {
        assertEquals("Empty list should return true", actualList.isEmpty(), true);
        assertDLLEquals(new DoublyLinkedList<>(), actualList);

    }

    //3 points
    @Test(timeout = TIMEOUT)
    public void testIsEmpty() {
        String[] actualFruits4 = {"Orange", "Pear", "Raspberry", "Grape"};
        makeDLLRange(actualFruits4, actualList, 0, actualFruits4.length);
        makeDLLRange(actualFruits4, expectedList, 0, actualFruits4.length);

        assertEquals("List shouldn't be empty", actualList.isEmpty(), false);
        assertDLLEquals(actualList, expectedList);

    }

    // ----- REMOVEFROMFRONT TESTS -----

    @Test(timeout = TIMEOUT)
    public void removeFrontEmpty() {
        assertNull(actualList.removeFromFront());
        assertDLLEquals(new DoublyLinkedList<>(), actualList);
    }

    @Test(timeout = TIMEOUT)
    public void removeFrontGeneral() {
        makeDLLRange(nodeData, actualList, 0, nodeData.length);

        Assert.assertSame("Returned incorrect data on first remove", nodeData[0], actualList.removeFromFront());
        assertEquals("Incorrect size after first remove", 11, actualList.size());

        Assert.assertSame("Returned incorrect data", nodeData[1], actualList.removeFromFront());
        Assert.assertSame("Returned incorrect data", nodeData[2], actualList.removeFromFront());
        Assert.assertSame("Returned incorrect data", nodeData[3], actualList.removeFromFront());

        makeDLLRange(nodeData, expectedList, 4, nodeData.length);
        assertDLLEquals(expectedList, actualList);
    }

    @Test(timeout = TIMEOUT)
    public void removeFrontUntilEmpty() {
        makeDLLRange(nodeData, actualList, 0, nodeData.length);

        Assert.assertSame("Returned incorrect data on first remove", nodeData[0], actualList.removeFromFront());
        assertEquals("Incorrect size after first remove", 11, actualList.size());

        for (int i = 1; i < nodeData.length - 1; i++) {
            Assert.assertSame("Returned incorrect data on " + i + "th remove", nodeData[i], actualList.removeFromFront());
            assertEquals("Incorrect size", nodeData.length - i - 1, actualList.size());
        }

        // Check the DLL's state with one item left
        makeDLLRange(nodeData, expectedList, nodeData.length - 1, nodeData.length);
        assertDLLEquals(expectedList, actualList);

        Assert.assertSame("Returned incorrect data on last remove", nodeData[nodeData.length - 1], actualList.removeFromFront());
        assertDLLEquals(new DoublyLinkedList<>(), actualList);
    }

    // ----- REMOVEFROMBACK TESTS -----

    @Test(timeout = TIMEOUT)
    public void removeBackEmpty() {
        assertNull(actualList.removeFromBack());
        assertDLLEquals(new DoublyLinkedList<>(), actualList);
    }

    @Test(timeout = TIMEOUT)
    public void removeBackGeneral() {
        makeDLLRange(nodeData, actualList, 0, nodeData.length);

        Assert.assertSame("Returned incorrect data on first remove", nodeData[nodeData.length - 1], actualList.removeFromBack());
        assertEquals("Incorrect size after first remove", 11, actualList.size());

        Assert.assertSame("Returned incorrect data", nodeData[nodeData.length - 2], actualList.removeFromBack());
        Assert.assertSame("Returned incorrect data", nodeData[nodeData.length - 3], actualList.removeFromBack());
        Assert.assertSame("Returned incorrect data", nodeData[nodeData.length - 4], actualList.removeFromBack());

        makeDLLRange(nodeData, expectedList, 0, nodeData.length - 4);
        assertDLLEquals(expectedList, actualList);
    }

    @Test(timeout = TIMEOUT)
    public void removeBackUntilEmpty() {
        makeDLLRange(nodeData, actualList, 0, nodeData.length);

        Assert.assertSame("Returned incorrect data on first remove", nodeData[nodeData.length - 1], actualList.removeFromBack());
        assertEquals("Incorrect size after first remove", 11, actualList.size());

        for (int i = nodeData.length - 2; i >= 1; i--) {
            Assert.assertSame("Returned incorrect data on " + i + "th remove", nodeData[i], actualList.removeFromBack());
            assertEquals("Incorrect size", i, actualList.size());
        }

        // Check the DLL's state with one item left
        makeDLLRange(nodeData, expectedList, 0, 1);
        assertDLLEquals(expectedList, actualList);

        Assert.assertSame("Returned incorrect data on last remove", nodeData[0], actualList.removeFromBack());
        assertDLLEquals(new DoublyLinkedList<>(), actualList);
    }

    // ----- REMOVEATINDEX TESTS -----

    @Test(timeout = TIMEOUT, expected = IndexOutOfBoundsException.class)
    public void removeAtIndexExceptionNegative() {
        try {
            actualList.removeAtIndex(-10);
        } catch (IndexOutOfBoundsException e) {
            assertEquals(IndexOutOfBoundsException.class, e.getClass());
            assertDLLEquals(new DoublyLinkedList<>(), actualList);
            throw e;
        }
    }

    @Test(timeout = TIMEOUT, expected = IndexOutOfBoundsException.class)
    public void removeAtIndexExceptionLarge() {
        try {
            actualList.removeAtIndex(0);
        } catch (IndexOutOfBoundsException e) {
            assertEquals(IndexOutOfBoundsException.class, e.getClass());
            assertDLLEquals(new DoublyLinkedList<>(), actualList);
            throw e;
        }
    }

    @Test(timeout = TIMEOUT)
    public void removeAtIndex0thIndex() {
        makeDLLRange(nodeData, actualList, 0, nodeData.length);

        Assert.assertSame("Returned incorrect data", nodeData[0], actualList.removeAtIndex(0));

        makeDLLRange(nodeData, expectedList, 1, nodeData.length);
        assertDLLEquals(expectedList, actualList);
    }

    @Test(timeout = TIMEOUT)
    public void removeAtIndexBack() {
        makeDLLRange(nodeData, actualList, 0, nodeData.length);

        Assert.assertSame("Returned incorrect data", nodeData[nodeData.length - 1], actualList.removeAtIndex(actualList.size() - 1));

        makeDLLRange(nodeData, expectedList, 0, nodeData.length - 1);
        assertDLLEquals(expectedList, actualList);
    }

    @Test(timeout = TIMEOUT)
    public void removeAtIndexGeneral() {
        makeDLLRange(nodeData, actualList, 0, 5);

        Assert.assertSame("Returned incorrect data", nodeData[3], actualList.removeAtIndex(3));

        DoublyLinkedList<String> temp = new DoublyLinkedList<>();
        makeDLLRange(nodeData, expectedList, 0, 3);
        makeDLLRange(nodeData, temp, 4, 5);
        expectedList.getTail().setNext(temp.getHead());
        temp.getHead().setPrevious(expectedList.getTail());
        setDLL(expectedList, expectedList.getHead(), expectedList.getTail(), expectedList.size() + 1);

        assertDLLEquals(expectedList, actualList);
    }

    @Test(timeout = TIMEOUT)
    public void removeAtIndexUntilEmpty() {
        makeDLLRange(nodeData, actualList, 0, 5);

        Assert.assertSame("Returned incorrect data", nodeData[4], actualList.removeAtIndex(4)); // Back
        Assert.assertSame("Returned incorrect data", nodeData[2], actualList.removeAtIndex(2));
        Assert.assertSame("Returned incorrect data", nodeData[1], actualList.removeAtIndex(1));
        Assert.assertSame("Returned incorrect data", nodeData[0], actualList.removeAtIndex(0)); // Front
        makeDLLRange(nodeData, expectedList, 3, 4);
        assertDLLEquals(expectedList, actualList);

        // Remove the last element
        Assert.assertSame("Returned incorrect data", nodeData[3], actualList.removeAtIndex(0));
        assertDLLEquals(new DoublyLinkedList<>(), actualList);
    }

    // ----- REMOVEFIRSTOCCURRENCE TESTS -----

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void remove1stOccurNullData() {
        try {
            actualList.removeFirstOccurrence(null);
        } catch (IllegalArgumentException e) {
            assertEquals(IllegalArgumentException.class, e.getClass());
            assertDLLEquals(new DoublyLinkedList<>(), actualList);
            throw e;
        }
    }

    @Test(timeout = TIMEOUT)
    public void remove1stOccurHeadTail() {
        // Remove head
        makeDLLRange(nodeData, actualList, 0, nodeData.length);

        assertTrue("Returned incorrect boolean", actualList.removeFirstOccurrence(nodeData[0]));

        makeDLLRange(nodeData, expectedList, 1, nodeData.length);
        assertDLLEquals(expectedList, actualList);

        // Remove tail
        makeDLLRange(nodeData, actualList, 0, nodeData.length);

        assertTrue("Returned incorrect boolean", actualList.removeFirstOccurrence(nodeData[nodeData.length - 1]));

        makeDLLRange(nodeData, expectedList, 0, nodeData.length - 1);
        assertDLLEquals(expectedList, actualList);
    }

    @Test(timeout = TIMEOUT)
    public void remove1stOccurOneElement() {
        makeDLLRange(nodeData, actualList, 4, 5);

        assertTrue("Returned incorrect boolean", actualList.removeFirstOccurrence(nodeData[4]));

        assertDLLEquals(expectedList, actualList);
    }

    @Test(timeout = TIMEOUT)
    public void remove1stOccurGeneralTrue() {
        makeDLLRange(nodeData, actualList, 0, 9);

        assertTrue("Returned incorrect boolean", actualList.removeFirstOccurrence(nodeData[7]));

        DoublyLinkedList<String> temp = new DoublyLinkedList<>();
        makeDLLRange(nodeData, expectedList, 0, 7);
        makeDLLRange(nodeData, temp, 8, 9);
        expectedList.getTail().setNext(temp.getHead());
        temp.getHead().setPrevious(expectedList.getTail());
        setDLL(expectedList, expectedList.getHead(), expectedList.getTail(), expectedList.size() + 1);

        assertDLLEquals(expectedList, actualList);
    }

    @Test(timeout = TIMEOUT)
    public void remove1stOccurMultipleItems() {
        makeDLLRange(nodeData, actualList, 0, nodeData.length);

        assertTrue("Returned incorrect boolean", actualList.removeFirstOccurrence(nodeData[3]));

        DoublyLinkedList<String> temp = new DoublyLinkedList<>();
        makeDLLRange(nodeData, expectedList, 0, 3);
        makeDLLRange(nodeData, temp, 4, nodeData.length);
        expectedList.getTail().setNext(temp.getHead());
        temp.getHead().setPrevious(expectedList.getTail());
        setDLL(expectedList, expectedList.getHead(), expectedList.getTail(), expectedList.size() + nodeData.length - 4);

        assertDLLEquals(expectedList, actualList);
    }

    @Test(timeout = TIMEOUT)
    public void remove1stOccurEmptyList() {
        // Remove from an empty list
        assertFalse("Returned incorrect boolean", actualList.removeFirstOccurrence("Vanillte"));

        assertDLLEquals(expectedList, actualList);
    }

    @Test(timeout = TIMEOUT)
    public void remove1stOccurFalseGeneral() {
        makeDLLRange(nodeData, actualList, 0, nodeData.length);

        assertFalse("Returned incorrect boolean", actualList.removeFirstOccurrence("Vanillte"));

        makeDLLRange(nodeData, expectedList, 0, nodeData.length);
        assertDLLEquals(expectedList, actualList);
    }
}
