import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.lang.reflect.Field;
import java.util.NoSuchElementException;

import static org.junit.Assert.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LinkedListTests {

    private static final long TIMEOUT = 200;

    private SinglyLinkedList<NewInteger> actual;
    private NewInteger[] expected;

    @Before
    public void setup() {
        actual = new SinglyLinkedList<>();
    }

    /**
     * Checks that the {@code actual} is correct by comparing it
     * to {@code expected}.
     */
    private void checkList() {
        LinkedListNode<NewInteger> current = actual.getHead();
        for (int i = 0; i < expected.length; i++) {
            assertNotNull(current);
            assertEquals(expected[i], current.getData());
            current = current.getNext();
        }
        assertNull(current);
        assertEquals(expected.length, actual.size());
        if (expected.length > 0) {
            assertNotNull(actual.getHead());
            assertNotNull(actual.getTail());
            assertEquals(expected[0], actual.getHead().getData());
            assertEquals(expected[expected.length - 1], actual.getTail().getData());
        } else {
            assertNull(actual.getHead());
            assertNull(actual.getTail());
        }
    }

    /**
     * Sets the fields of the LinkedList using reflection to
     * avoid dependencies in unit testing.
     *
     * @param list the list to be modified
     * @param head the new head
     * @param tail the new tail
     * @param size the new size
     */
    private void setLinkedList(SinglyLinkedList<NewInteger> list, LinkedListNode<NewInteger> head, LinkedListNode<NewInteger> tail, int size) {
        try {
            Field field = SinglyLinkedList.class.getDeclaredField("head");
            field.setAccessible(true);
            try {
                field.set(list, head);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        try {
            Field field = SinglyLinkedList.class.getDeclaredField("tail");
            field.setAccessible(true);
            try {
                field.set(list, tail);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        try {
            Field field = SinglyLinkedList.class.getDeclaredField("size");
            field.setAccessible(true);
            try {
                field.set(list, size);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    /**
     * Helper method to build a general list to remove from.
     * ListOne = (-5, 2, 4, -5, 10, 2, -5)
     */
    private void initListOne() {
        LinkedListNode<NewInteger> tail = new LinkedListNode<>(new NewInteger(-5, -5), null);
        LinkedListNode<NewInteger> head = new LinkedListNode<>(new NewInteger(-5, -5), new LinkedListNode<>(new NewInteger(2, 2), new LinkedListNode<>(new NewInteger(4, 4), new LinkedListNode<>(new NewInteger(-5, -5), new LinkedListNode<>(new NewInteger(10, 10), new LinkedListNode<>(new NewInteger(2, 2), tail))))));

        setLinkedList(actual, head, tail, 7);

    }

    /**
     * Helper method to build a general list to remove from.
     * ListTwo = (0, 1, 2, 3, 4)
     */
    private void initListTwo() {
        LinkedListNode<NewInteger> tail = new LinkedListNode<>(new NewInteger(4, 4), null);
        LinkedListNode<NewInteger> head = new LinkedListNode<>(new NewInteger(0, 0), new LinkedListNode<>(new NewInteger(1, 1), new LinkedListNode<>(new NewInteger(2, 2), new LinkedListNode<>(new NewInteger(3, 3), tail))));

        setLinkedList(actual, head, tail, 5);
    }

    // 2 points
    @Test(timeout = TIMEOUT, expected = IndexOutOfBoundsException.class)
    public void addAtIndex1NegativeIndexException() {
        try {
            actual.addAtIndex(-1, new NewInteger(1, 1));
        } catch (IndexOutOfBoundsException e) {
            if (e.getClass().equals(IndexOutOfBoundsException.class)) {
                throw e;
            }
        }
    }

    @Test(timeout = TIMEOUT, expected = IndexOutOfBoundsException.class)
    public void addAtIndex2LargeIndexException() {
        try {
            actual.addAtIndex(0, new NewInteger(1, 1));
            actual.addAtIndex(0, new NewInteger(2, 2));
            actual.addAtIndex(3, new NewInteger(4, 4));
        } catch (IndexOutOfBoundsException e) {
            if (e.getClass().equals(IndexOutOfBoundsException.class)) {
                throw e;
            }
        }
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void addAtIndex3NullDataException() {
        try {
            actual.addAtIndex(0, null);
        } catch (IllegalArgumentException e) {
            if (e.getClass().equals(IllegalArgumentException.class)) {
                throw e;
            }
        }
    }

    @Test(timeout = TIMEOUT)
    public void addAtIndex4InitialUpdateBothHeadTail() {
        expected = new NewInteger[]{new NewInteger(0, 0)};
        actual.addAtIndex(0, new NewInteger(0, 0));
        checkList();

    }

    @Test(timeout = TIMEOUT)
    public void addAtIndex5General() {
        expected = new NewInteger[]{new NewInteger(0, 0), new NewInteger(1, 1), new NewInteger(2, 2), new NewInteger(3, 3), new NewInteger(4, 4), new NewInteger(5, 5), new NewInteger(6, 6), new NewInteger(7, 7), new NewInteger(8, 8)};
        actual.addAtIndex(0, new NewInteger(4, 4));
        actual.addAtIndex(0, new NewInteger(2, 2));
        actual.addAtIndex(2, new NewInteger(6, 6));
        actual.addAtIndex(3, new NewInteger(8, 8));
        actual.addAtIndex(3, new NewInteger(7, 7));
        actual.addAtIndex(1, new NewInteger(3, 3));
        actual.addAtIndex(0, new NewInteger(0, 0));
        actual.addAtIndex(1, new NewInteger(1, 1));
        actual.addAtIndex(5, new NewInteger(5, 5));
        checkList();

    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void addToFront1NullDataException() {
        try {
            actual.addToFront(null);
        } catch (IllegalArgumentException e) {
            if (e.getClass().equals(IllegalArgumentException.class)) {
                throw e;
            }
        }
    }

    @Test(timeout = TIMEOUT)
    public void addToFront2InitialAdd() {
        expected = new NewInteger[]{new NewInteger(0, 0)};
        actual.addToFront(new NewInteger(0, 0));
        checkList();

    }

    @Test(timeout = TIMEOUT)
    public void addToFront3GeneralAddMultiple() {
        expected = new NewInteger[]{new NewInteger(0, 0), new NewInteger(1, 1), new NewInteger(2, 2), new NewInteger(3, 3), new NewInteger(4, 4)};
        actual.addToFront(new NewInteger(4, 4));
        actual.addToFront(new NewInteger(3, 3));
        actual.addToFront(new NewInteger(2, 2));
        actual.addToFront(new NewInteger(1, 1));
        actual.addToFront(new NewInteger(0, 0));
        checkList();

    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void addToBack1NullDataException() {
        try {
            actual.addToBack(null);
        } catch (IllegalArgumentException e) {
            if (e.getClass().equals(IllegalArgumentException.class)) {
                throw e;
            }
        }
    }

    @Test(timeout = TIMEOUT)
    public void addToBack2InitialAdd() {
        expected = new NewInteger[]{new NewInteger(0, 0)};
        actual.addToBack(new NewInteger(0, 0));
        checkList();

    }

    @Test(timeout = TIMEOUT)
    public void addToBack3GeneralAddMultiple() {
        expected = new NewInteger[]{new NewInteger(0, 0), new NewInteger(1, 1), new NewInteger(2, 2), new NewInteger(3, 3), new NewInteger(4, 4)};
        actual.addToBack(new NewInteger(0, 0));
        actual.addToBack(new NewInteger(1, 1));
        actual.addToBack(new NewInteger(2, 2));
        actual.addToBack(new NewInteger(3, 3));
        actual.addToBack(new NewInteger(4, 4));
        checkList();

    }

    @Test(timeout = TIMEOUT)
    public void toArray1EmptyList() {
        Object[] result = actual.toArray();
        assertArrayEquals(new Object[0], result);

        // Making sure that list isn't modified afterwards.
        expected = new NewInteger[0];
        checkList();

    }

    @Test(timeout = TIMEOUT)
    public void toArray2General() {
        Object[] answer = new Object[]{new NewInteger(0, 0), new NewInteger(1, 1), new NewInteger(2, 2), new NewInteger(3, 3), new NewInteger(4, 4)};

        // Terrible Formatting to Avoid Checkstyle
        // Basically makes a LL of size 5 with NewIntegers from 0 to 4
        LinkedListNode<NewInteger> tail = new LinkedListNode<>(new NewInteger(4, 4), null);
        LinkedListNode<NewInteger> head = new LinkedListNode<>(new NewInteger(0, 0), new LinkedListNode<>(new NewInteger(1, 1), new LinkedListNode<>(new NewInteger(2, 2), new LinkedListNode<>(new NewInteger(3, 3), tail))));
        setLinkedList(actual, head, tail, 5);
        Object[] result = actual.toArray();
        assertArrayEquals(answer, result);

        // Making sure that list isn't modified afterwards.
        expected = new NewInteger[]{new NewInteger(0, 0), new NewInteger(1, 1), new NewInteger(2, 2), new NewInteger(3, 3), new NewInteger(4, 4)};
        checkList();

    }

    // 1 point
    @Test(timeout = TIMEOUT)
    public void clear1EmptyList() {
        actual.clear();
        assertNull(actual.getHead());
        assertNull(actual.getTail());
        assertEquals(0, actual.size());

    }

    @Test(timeout = TIMEOUT)
    public void clear2OneElement() {
        LinkedListNode<NewInteger> head = new LinkedListNode<>(new NewInteger(0, 0), null);
        setLinkedList(actual, head, head, 1);
        actual.clear();
        assertNull(actual.getHead());
        assertNull(actual.getTail());
        assertEquals(0, actual.size());

    }

    @Test(timeout = TIMEOUT)
    public void clear3General() {
        // Terrible Formatting to Avoid Checkstyle
        // Basically makes a LL of size 5 with NewIntegers from 0 to 4
        LinkedListNode<NewInteger> tail = new LinkedListNode<>(new NewInteger(4, 4), null);
        LinkedListNode<NewInteger> head = new LinkedListNode<>(new NewInteger(0, 0), new LinkedListNode<>(new NewInteger(1, 1), new LinkedListNode<>(new NewInteger(2, 2), new LinkedListNode<>(new NewInteger(3, 3), tail))));
        setLinkedList(actual, head, tail, 5);
        actual.clear();
        assertNull(actual.getHead());
        assertNull(actual.getTail());
        assertEquals(0, actual.size());

    }

    @Test(timeout = TIMEOUT)
    public void isEmpty1True() {
        assertTrue(actual.isEmpty());

        // Making sure that list isn't modified afterwards.
        expected = new NewInteger[0];
        checkList();

    }

    // 1 point
    @Test(timeout = TIMEOUT)
    public void isEmpty2FalseSingleElement() {
        LinkedListNode<NewInteger> head = new LinkedListNode<>(new NewInteger(0, 0), null);
        setLinkedList(actual, head, head, 1);
        assertFalse(actual.isEmpty());

        // Making sure list hasn't been modified afterwards
        expected = new NewInteger[]{new NewInteger(0, 0)};
        checkList();

    }

    @Test(timeout = TIMEOUT)
    public void isEmpty3FalseMultiple() {
        // Terrible Formatting to Avoid Checkstyle
        // Basically makes a LL of size 5 with NewIntegers from 0 to 4
        LinkedListNode<NewInteger> tail = new LinkedListNode<>(new NewInteger(4, 4), null);
        LinkedListNode<NewInteger> head = new LinkedListNode<>(new NewInteger(0, 0), new LinkedListNode<>(new NewInteger(1, 1), new LinkedListNode<>(new NewInteger(2, 2), new LinkedListNode<>(new NewInteger(3, 3), tail))));
        setLinkedList(actual, head, tail, 5);
        assertFalse(actual.isEmpty());

        // Making sure list hasn't been modified afterwards
        expected = new NewInteger[]{new NewInteger(0, 0), new NewInteger(1, 1), new NewInteger(2, 2), new NewInteger(3, 3), new NewInteger(4, 4)};
        checkList();

    }

    @Test(timeout = TIMEOUT)
    public void testRemoveAtIndexGeneralOnce() {

        // -5, 2, 4, -5, 10, 2, -5
        NewInteger removed = new NewInteger(10, 10);
        LinkedListNode<NewInteger> tail = new LinkedListNode<>(new NewInteger(-5, -5), null);
        LinkedListNode<NewInteger> head = new LinkedListNode<>(new NewInteger(-5, -5), new LinkedListNode<>(new NewInteger(2, 2), new LinkedListNode<>(new NewInteger(4, 4), new LinkedListNode<>(new NewInteger(-5, -5), new LinkedListNode<>(removed, new LinkedListNode<>(new NewInteger(2, 2), tail))))));

        setLinkedList(actual, head, tail, 7);

        assertSame(removed, actual.removeAtIndex(4));

        expected = new NewInteger[]{new NewInteger(-5, -5), new NewInteger(2, 2), new NewInteger(4, 4), new NewInteger(-5, -5), new NewInteger(2, 2), new NewInteger(-5, -5),};

        checkList();
    }

    @Test(timeout = TIMEOUT)
    public void testRemoveAtIndexGeneralAll() {

        NewInteger zero = new NewInteger(0, 0);
        NewInteger one = new NewInteger(1, 1);
        NewInteger two = new NewInteger(2, 2);
        NewInteger three = new NewInteger(3, 3);
        NewInteger four = new NewInteger(4, 4);

        LinkedListNode<NewInteger> tail = new LinkedListNode<>(four, null);
        LinkedListNode<NewInteger> head = new LinkedListNode<>(zero, new LinkedListNode<>(one, new LinkedListNode<>(two, new LinkedListNode<>(three, tail))));

        setLinkedList(actual, head, tail, 5);

        assertSame(one, actual.removeAtIndex(1));
        expected = new NewInteger[]{new NewInteger(0, 0), new NewInteger(2, 2), new NewInteger(3, 3), new NewInteger(4, 4),};
        checkList();

        assertSame(zero, actual.removeAtIndex(0));
        expected = new NewInteger[]{new NewInteger(2, 2), new NewInteger(3, 3), new NewInteger(4, 4),};
        checkList();

        assertSame(three, actual.removeAtIndex(1));
        expected = new NewInteger[]{new NewInteger(2, 2), new NewInteger(4, 4),};
        checkList();

        assertSame(four, actual.removeAtIndex(1));
        expected = new NewInteger[]{new NewInteger(2, 2)};
        checkList();

        assertSame(two, actual.removeAtIndex(0));
        expected = new NewInteger[]{};
        checkList();


    }


    @Test(timeout = TIMEOUT, expected = IndexOutOfBoundsException.class)
    public void testRemoveAtNegativeIndex() {

        initListOne();

        try {
            actual.removeAtIndex(-1);
        } catch (IndexOutOfBoundsException e) {
            throw e;
        }
    }

    @Test(timeout = TIMEOUT, expected = IndexOutOfBoundsException.class)
    public void testRemoveAtLargeIndex() {

        initListTwo();
        try {
            actual.removeAtIndex(5);
        } catch (IndexOutOfBoundsException e) {
            throw e;
        }
    }


    @Test(timeout = TIMEOUT)
    public void testRemoveFromFrontOnce() {

        NewInteger zero = new NewInteger(0, 0);
        NewInteger one = new NewInteger(1, 1);
        NewInteger two = new NewInteger(2, 2);
        NewInteger three = new NewInteger(3, 3);
        NewInteger four = new NewInteger(4, 4);

        LinkedListNode<NewInteger> tail = new LinkedListNode<>(four, null);
        LinkedListNode<NewInteger> head = new LinkedListNode<>(zero, new LinkedListNode<>(one, new LinkedListNode<>(two, new LinkedListNode<>(three, tail))));

        setLinkedList(actual, head, tail, 5);

        assertSame(zero, actual.removeFromFront());
        expected = new NewInteger[]{new NewInteger(1, 1), new NewInteger(2, 2), new NewInteger(3, 3), new NewInteger(4, 4),};
        checkList();
    }

    @Test(timeout = TIMEOUT)
    public void testRemoveFromFrontAll() {

        NewInteger zero = new NewInteger(0, 0);
        NewInteger one = new NewInteger(1, 1);
        NewInteger two = new NewInteger(2, 2);
        NewInteger three = new NewInteger(3, 3);
        NewInteger four = new NewInteger(4, 4);

        LinkedListNode<NewInteger> tail = new LinkedListNode<>(four, null);
        LinkedListNode<NewInteger> head = new LinkedListNode<>(zero, new LinkedListNode<>(one, new LinkedListNode<>(two, new LinkedListNode<>(three, tail))));

        setLinkedList(actual, head, tail, 5);

        assertSame(zero, actual.removeFromFront());
        expected = new NewInteger[]{new NewInteger(1, 1), new NewInteger(2, 2), new NewInteger(3, 3), new NewInteger(4, 4),};
        checkList();

        assertSame(one, actual.removeFromFront());
        expected = new NewInteger[]{new NewInteger(2, 2), new NewInteger(3, 3), new NewInteger(4, 4),};
        checkList();

        assertSame(two, actual.removeFromFront());
        expected = new NewInteger[]{new NewInteger(3, 3), new NewInteger(4, 4),};
        checkList();

        assertSame(three, actual.removeFromFront());
        expected = new NewInteger[]{new NewInteger(4, 4)};
        checkList();

        assertSame(four, actual.removeFromFront());
        expected = new NewInteger[]{};
        checkList();

    }

    @Test(timeout = TIMEOUT)
    public void removeFromFrontReturnNull() {
        assertNull(actual.removeFromFront());
    }


    @Test(timeout = TIMEOUT)
    public void removeFromBackReturnNull() {
        assertNull(actual.removeFromBack());
    }

    @Test(timeout = TIMEOUT)
    public void testRemoveFromBackOnce() {

        NewInteger zero = new NewInteger(0, 0);
        NewInteger one = new NewInteger(1, 1);
        NewInteger two = new NewInteger(2, 2);
        NewInteger three = new NewInteger(3, 3);
        NewInteger four = new NewInteger(4, 4);

        LinkedListNode<NewInteger> tail = new LinkedListNode<>(four, null);
        LinkedListNode<NewInteger> head = new LinkedListNode<>(zero, new LinkedListNode<>(one, new LinkedListNode<>(two, new LinkedListNode<>(three, tail))));

        setLinkedList(actual, head, tail, 5);

        assertSame(four, actual.removeFromBack());
        expected = new NewInteger[]{new NewInteger(0, 0), new NewInteger(1, 1), new NewInteger(2, 2), new NewInteger(3, 3),};
        checkList();
    }

    @Test(timeout = TIMEOUT)
    public void testRemoveFromBackAll() {

        NewInteger zero = new NewInteger(0, 0);
        NewInteger one = new NewInteger(1, 1);
        NewInteger two = new NewInteger(2, 2);
        NewInteger three = new NewInteger(3, 3);
        NewInteger four = new NewInteger(4, 4);

        LinkedListNode<NewInteger> tail = new LinkedListNode<>(four, null);
        LinkedListNode<NewInteger> head = new LinkedListNode<>(zero, new LinkedListNode<>(one, new LinkedListNode<>(two, new LinkedListNode<>(three, tail))));

        setLinkedList(actual, head, tail, 5);

        assertSame(four, actual.removeFromBack());
        expected = new NewInteger[]{new NewInteger(0, 0), new NewInteger(1, 1), new NewInteger(2, 2), new NewInteger(3, 3),};
        checkList();

        assertSame(three, actual.removeFromBack());
        expected = new NewInteger[]{new NewInteger(0, 0), new NewInteger(1, 1), new NewInteger(2, 2),};
        checkList();

        assertSame(two, actual.removeFromBack());
        expected = new NewInteger[]{new NewInteger(0, 0), new NewInteger(1, 1),};
        checkList();

        assertSame(one, actual.removeFromBack());
        expected = new NewInteger[]{new NewInteger(0, 0)};
        checkList();

        assertSame(zero, actual.removeFromBack());
        expected = new NewInteger[]{};
        checkList();

    }

    @Test(timeout = TIMEOUT)
    public void testRemoveFirstOccurrenceGeneral() {

        // List = (-5, 2, 4, -5, 10, 2, -5)
        NewInteger removed = new NewInteger(2, 2);
        LinkedListNode<NewInteger> tail = new LinkedListNode<>(new NewInteger(-5, -5), null);
        LinkedListNode<NewInteger> head = new LinkedListNode<>(new NewInteger(-5, -5), new LinkedListNode<>(removed, new LinkedListNode<>(new NewInteger(4, 4), new LinkedListNode<>(new NewInteger(-5, -5), new LinkedListNode<>(new NewInteger(10, 10), new LinkedListNode<>(new NewInteger(2, 2), tail))))));

        setLinkedList(actual, head, tail, 7);
        assertSame(removed, actual.removeFirstOccurrence(new NewInteger(2, 2)));

        expected = new NewInteger[]{new NewInteger(-5, -5), new NewInteger(4, 4), new NewInteger(-5, -5), new NewInteger(10, 10), new NewInteger(2, 2), new NewInteger(-5, -5)};

        checkList();
    }

    @Test(timeout = TIMEOUT)
    public void testRemoveFirstOccurrenceFrontBack() {

        // List = (-5, 2, 4, -5, 10, 2, -5)
        NewInteger first = new NewInteger(-5, -5);
        NewInteger middle = new NewInteger(-5, -5);
        NewInteger last = new NewInteger(-5, -5);
        LinkedListNode<NewInteger> tail = new LinkedListNode<>(last, null);
        LinkedListNode<NewInteger> head = new LinkedListNode<>(first, new LinkedListNode<>(new NewInteger(2, 2), new LinkedListNode<>(new NewInteger(4, 4), new LinkedListNode<>(middle, new LinkedListNode<>(new NewInteger(10, 10), new LinkedListNode<>(new NewInteger(2, 2), tail))))));

        setLinkedList(actual, head, tail, 7);

        NewInteger value = new NewInteger(-5, -5);
        assertSame(first, actual.removeFirstOccurrence(value));
        expected = new NewInteger[]{new NewInteger(2, 2), new NewInteger(4, 4), new NewInteger(-5, -5), new NewInteger(10, 10), new NewInteger(2, 2), new NewInteger(-5, -5)};
        checkList();

        assertSame(middle, actual.removeFirstOccurrence(value));
        expected = new NewInteger[]{new NewInteger(2, 2), new NewInteger(4, 4), new NewInteger(10, 10), new NewInteger(2, 2), new NewInteger(-5, -5)};
        checkList();

        assertSame(last, actual.removeFirstOccurrence(value));
        expected = new NewInteger[]{new NewInteger(2, 2), new NewInteger(4, 4), new NewInteger(10, 10), new NewInteger(2, 2),};
        checkList();

    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testFirstOccurrenceNullArg() {
        initListOne();
        try {
            actual.removeFirstOccurrence(null);
        } catch (IllegalArgumentException e) {
            throw e;
        }
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void testFirstOccurrenceNoElement() {
        initListOne();
        try {
            actual.removeFirstOccurrence(new NewInteger(100, 100));
        } catch (NoSuchElementException e) {
            throw e;
        }
    }

    @Test(timeout = TIMEOUT)
    public void testGetGeneralMiddle() {

        NewInteger toAccess = new NewInteger(2, 2);
        LinkedListNode<NewInteger> tail = new LinkedListNode<>(new NewInteger(4, 4), null);
        LinkedListNode<NewInteger> head = new LinkedListNode<>(new NewInteger(0, 0), new LinkedListNode<>(new NewInteger(1, 1), new LinkedListNode<>(toAccess, new LinkedListNode<>(new NewInteger(3, 3), tail))));

        setLinkedList(actual, head, tail, 5);

        assertSame(toAccess, actual.get(2));

    }

    @Test(timeout = TIMEOUT)
    public void testGetFront() {

        // List = (-5, 2, 4, -5, 10, 2, -5)
        initListOne();
        assertSame(actual.getHead().getData(), actual.get(0));
    }

    @Test(timeout = TIMEOUT)
    public void testGetBack() {

        // List = (-5, 2, 4, -5, 10, 2, -5)
        initListOne();
        assertSame(actual.getTail().getData(), actual.get(6));
    }

    @Test(timeout = TIMEOUT, expected = IndexOutOfBoundsException.class)
    public void testGetNegativeIndex() {

        // List = (-5, 2, 4, -5, 10, 2, -5)
        initListOne();
        try {
            actual.get(-1);
        } catch (IndexOutOfBoundsException e) {
            throw e;
        }
    }

    @Test(timeout = TIMEOUT, expected = IndexOutOfBoundsException.class)
    public void testGetLargeIndex() {

        // List = (-5, 2, 4, -5, 10, 2, -5)
        initListOne();
        try {
            actual.get(7);
        } catch (IndexOutOfBoundsException e) {
            throw e;
        }
    }

    private class NewInteger {
        private int integer1;
        private int integer2;

        /**
         * Constructs NewInteger type. Useful primarily for generics and type
         * equality checking.
         *
         * @param integer1 the first integer
         * @param integer2 the second integer
         */
        public NewInteger(int integer1, int integer2) {
            this.integer1 = integer1;
            this.integer2 = integer2;
        }

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof NewInteger)) {
                return false;
            }
            NewInteger obj2 = (NewInteger) obj;
            return this.integer1 == obj2.integer1 && this.integer2 == obj2.integer2;
        }

        @Override
        public int hashCode() {
            return this.integer1 + this.integer2;
        }

        @Override
        public String toString() {
            return "Integer 1: " + this.integer1 + ", Integer 2: " + this.integer2;
        }
    }
}
