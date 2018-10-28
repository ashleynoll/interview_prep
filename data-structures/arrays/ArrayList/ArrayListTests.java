import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

/**
 * Extensive testing
 */
public class ArrayListTests {
    private static final long TIMEOUT = 200;
    private ArrayList<Integer> list;

    @Before
    public void setUp() {
        list = new ArrayList<>();
    }

    /**
     * Verifies that the backing array of list has the same elements as expected
     * and has length expectedSize.
     *
     * @param expected     the expected array
     * @param expectedSize the expected length of the backing array
     */
    private void checkBackingArray(Object[] expected, int expectedSize) {
        Object[] backingArray = list.getBackingArray();
        assertEquals(expectedSize, backingArray.length);
        for (int i = 0; i < expected.length; i++) {
            assertSame(expected[i], backingArray[i]);
        }

        for (int i = expected.length; i < backingArray.length; i++) {
            assertNull(backingArray[i]);
        }
    }

    /**
     * Sets the fields of the ArrayList using reflection to
     * avoid dependencies in unit testing.
     *
     * @param list         the list to be modified
     * @param backingArray the new backingArray
     * @param size         the new size
     */
    private void setBackingArray(ArrayList<Integer> list, Object[] backingArray, int size) {
        try {
            Field field = ArrayList.class.getDeclaredField("backingArray");
            field.setAccessible(true);
            try {
                field.set(list, backingArray);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        try {
            Field field = ArrayList.class.getDeclaredField("size");
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

    @Test(timeout = TIMEOUT, expected = IndexOutOfBoundsException.class)
    public void testAddThrowsNegativeIndex() {
        try {
            list.addAtIndex(-1, new Integer(1));
        } catch (IndexOutOfBoundsException ex) {
            throw ex;
        }
    }

    @Test(timeout = TIMEOUT, expected = IndexOutOfBoundsException.class)
    public void testAddThrowsLargeIndex() {
        assertEquals(0, list.size());
        try {
            list.addAtIndex(1, new Integer(1));
        } catch (IndexOutOfBoundsException ex) {
            throw ex;
        }
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testAddThrowsNullData() {
        try {
            list.addAtIndex(0, null);
        } catch (IllegalArgumentException ex) {
            throw ex;
        }
    }

    @Test(timeout = TIMEOUT)
    public void testAddAtIndexZero() {
        assertEquals(0, list.size());
        Integer[] expected = new Integer[]{new Integer(2), new Integer(4), new Integer(3)};

        list.addAtIndex(0, expected[2]);
        list.addAtIndex(0, expected[1]);
        list.addAtIndex(0, expected[0]);

        assertEquals(3, list.size());
        checkBackingArray(expected, ArrayListInterface.INITIAL_CAPACITY);
    }

    @Test(timeout = TIMEOUT)
    public void testAddAtIndexMiddle() {
        assertEquals(0, list.size());
        Integer[] expected = new Integer[]{new Integer(2), new Integer(4), new Integer(3)};

        list.addAtIndex(0, expected[0]);
        list.addAtIndex(1, expected[2]);
        list.addAtIndex(1, expected[1]);

        assertEquals(3, list.size());
        checkBackingArray(expected, ArrayListInterface.INITIAL_CAPACITY);
    }

    // 1 point
    @Test(timeout = TIMEOUT)
    public void testAddAtIndexBack() {
        assertEquals(0, list.size());
        Integer[] expected = new Integer[]{new Integer(2), new Integer(4), new Integer(3)};

        list.addAtIndex(0, expected[0]);
        list.addAtIndex(1, expected[1]);
        list.addAtIndex(2, expected[2]);

        assertEquals(3, list.size());
        checkBackingArray(expected, ArrayListInterface.INITIAL_CAPACITY);
    }

    @Test(timeout = TIMEOUT)
    public void testAddToFrontOnce() {
        assertEquals(0, list.size());
        Integer[] expected = new Integer[]{new Integer(2)};

        list.addToFront(expected[0]);

        assertEquals(1, list.size());
        checkBackingArray(expected, ArrayListInterface.INITIAL_CAPACITY);
    }

    @Test(timeout = TIMEOUT)
    public void testAddToFrontManyTimes() {
        assertEquals(0, list.size());
        Integer[] expected = new Integer[]{new Integer(2), new Integer(4), new Integer(3), new Integer(6)};

        list.addToFront(expected[3]);
        list.addToFront(expected[2]);
        list.addToFront(expected[1]);
        list.addToFront(expected[0]);

        assertEquals(4, list.size());
        checkBackingArray(expected, ArrayListInterface.INITIAL_CAPACITY);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testAddToFrontThrowsNullData() {
        try {
            list.addToFront(null);
        } catch (IllegalArgumentException ex) {
            throw ex;
        }
    }

    @Test(timeout = TIMEOUT)
    public void testAddToBackOnce() {
        assertEquals(0, list.size());
        Integer[] expected = new Integer[]{new Integer(2)};

        list.addToBack(expected[0]);

        assertEquals(1, list.size());
        checkBackingArray(expected, ArrayListInterface.INITIAL_CAPACITY);
    }

    @Test(timeout = TIMEOUT)
    public void testAddToBackManyTimes() {
        assertEquals(0, list.size());
        Integer[] expected = new Integer[]{new Integer(2), new Integer(4), new Integer(3), new Integer(6)};

        list.addToBack(expected[0]);
        list.addToBack(expected[1]);
        list.addToBack(expected[2]);
        list.addToBack(expected[3]);

        assertEquals(4, list.size());
        checkBackingArray(expected, ArrayListInterface.INITIAL_CAPACITY);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testAddToBackThrowsNullData() {
        try {
            list.addToBack(null);
        } catch (IllegalArgumentException ex) {
            throw ex;
        }
    }

    @Test(timeout = TIMEOUT, expected = IndexOutOfBoundsException.class)
    public void testRemoveAtIndexThrowsNegativeIndex() {
        try {
            list.removeAtIndex(-1);
        } catch (IndexOutOfBoundsException ex) {
            throw ex;
        }
    }

    @Test(timeout = TIMEOUT, expected = IndexOutOfBoundsException.class)
    public void testRemoveAtIndexThrowsLargeIndex() {
        try {
            list.removeAtIndex(0);
        } catch (IndexOutOfBoundsException ex) {
            throw ex;
        }
    }

    @Test(timeout = TIMEOUT)
    public void testRemoveAtIndexFront() {
        assertEquals(0, list.size());
        Integer[] expected = new Integer[]{new Integer(2), new Integer(4), new Integer(3), new Integer(6)};
        Integer toRemove = new Integer(12);

        Integer[] actual = new Integer[10];
        actual[0] = toRemove;
        actual[1] = expected[0];
        actual[2] = expected[1];
        actual[3] = expected[2];
        actual[4] = expected[3];
        setBackingArray(list, actual, 5);

        assertSame(toRemove, list.removeAtIndex(0));
        assertEquals(4, list.size());
        checkBackingArray(expected, ArrayListInterface.INITIAL_CAPACITY);
    }

    // 1 point
    @Test(timeout = TIMEOUT)
    public void testRemoveAtIndexMiddle() {
        assertEquals(0, list.size());
        Integer[] expected = new Integer[]{new Integer(2), new Integer(4), new Integer(3), new Integer(6)};
        Integer toRemove = new Integer(12);

        Integer[] actual = new Integer[10];
        actual[0] = expected[0];
        actual[1] = toRemove;
        actual[2] = expected[1];
        actual[3] = expected[2];
        actual[4] = expected[3];
        setBackingArray(list, actual, 5);

        assertSame(toRemove, list.removeAtIndex(1));
        assertEquals(4, list.size());
        checkBackingArray(expected, ArrayListInterface.INITIAL_CAPACITY);
    }

    // 1 point
    @Test(timeout = TIMEOUT)
    public void testRemoveAtIndexBack() {
        assertEquals(0, list.size());
        Integer[] expected = new Integer[]{new Integer(2), new Integer(4), new Integer(3), new Integer(6)};
        Integer toRemove = new Integer(12);

        Integer[] actual = new Integer[10];
        actual[0] = expected[0];
        actual[1] = expected[1];
        actual[2] = expected[2];
        actual[3] = expected[3];
        actual[4] = toRemove;
        setBackingArray(list, actual, 5);

        assertSame(toRemove, list.removeAtIndex(4));
        assertEquals(4, list.size());
        checkBackingArray(expected, ArrayListInterface.INITIAL_CAPACITY);
    }

    @Test(timeout = TIMEOUT)
    public void testRemoveFromFrontEmpty() {
        assertNull(list.removeFromFront());
        assertEquals("size is not 0", 0, list.size());
        checkBackingArray(new Object[ArrayListInterface.INITIAL_CAPACITY], ArrayListInterface.INITIAL_CAPACITY);
    }

    @Test(timeout = TIMEOUT)
    public void testRemoveFromFrontGeneral() {
        assertEquals(0, list.size());
        Integer[] expected = new Integer[]{new Integer(2), new Integer(4), new Integer(3), new Integer(6)};
        Integer toRemove = new Integer(12);

        Integer[] actual = new Integer[10];
        actual[0] = toRemove;
        actual[1] = expected[0];
        actual[2] = expected[1];
        actual[3] = expected[2];
        actual[4] = expected[3];
        setBackingArray(list, actual, 5);

        assertSame(toRemove, list.removeFromFront());
        assertEquals(4, list.size());
        checkBackingArray(expected, ArrayListInterface.INITIAL_CAPACITY);
    }

    @Test(timeout = TIMEOUT)
    public void testRemoveFromBackEmpty() {
        assertNull(list.removeFromBack());
        assertEquals("size is not 0", 0, list.size());
        checkBackingArray(new Object[ArrayListInterface.INITIAL_CAPACITY], ArrayListInterface.INITIAL_CAPACITY);
    }

    @Test(timeout = TIMEOUT)
    public void testRemoveFromBackGeneral() {
        assertEquals(0, list.size());
        Integer[] expected = new Integer[]{new Integer(2), new Integer(4), new Integer(3), new Integer(6)};
        Integer toRemove = new Integer(12);

        Integer[] actual = new Integer[10];
        actual[0] = expected[0];
        actual[1] = expected[1];
        actual[2] = expected[2];
        actual[3] = expected[3];
        actual[4] = toRemove;
        setBackingArray(list, actual, 5);

        assertSame(toRemove, list.removeFromBack());
        assertEquals(4, list.size());
        checkBackingArray(expected, ArrayListInterface.INITIAL_CAPACITY);
    }

    @Test(timeout = TIMEOUT)
    public void testAddToFrontRegrows() {
        assertEquals(0, list.size());
        Integer[] expected = new Integer[]{new Integer(2), new Integer(3), new Integer(5), new Integer(7), new Integer(11), new Integer(13), new Integer(17), new Integer(19), new Integer(23), new Integer(27), new Integer(29)};

        for (int i = expected.length - 1; i >= 0; i--) {
            list.addToFront(expected[i]);
            if (list.size() < 11) {
                assertEquals(10, list.getBackingArray().length);
            }
        }
        assertEquals(11, list.size());
        checkBackingArray(expected, ArrayListInterface.INITIAL_CAPACITY * 2);
    }

    @Test(timeout = TIMEOUT)
    public void testAddToFrontRegrowsTwice() {
        assertEquals(0, list.size());
        Integer[] expected = new Integer[]{new Integer(2), new Integer(3), new Integer(5), new Integer(7), new Integer(11), new Integer(13), new Integer(17), new Integer(19), new Integer(23), new Integer(27), new Integer(29)};

        for (int i = expected.length - 1; i >= 0; i--) {
            list.addToFront(expected[i]);
            if (list.size() < 11) {
                assertEquals(10, list.getBackingArray().length);
            }
        }
        assertEquals(11, list.size());
        checkBackingArray(expected, ArrayListInterface.INITIAL_CAPACITY * 2);
        expected = new Integer[]{new Integer(2), new Integer(7), new Integer(5), new Integer(4), new Integer(4), new Integer(1), new Integer(7), new Integer(9), new Integer(0), new Integer(394), expected[0], expected[1], expected[2], expected[3], expected[4], expected[5], expected[6], expected[7], expected[8], expected[9], expected[10]};
        for (int i = 9; i >= 0; i--) {
            list.addToFront(expected[i]);
            if (list.size() < 21) {
                assertEquals(20, list.getBackingArray().length);
            }
        }
        assertEquals(21, list.size());
        checkBackingArray(expected, ArrayListInterface.INITIAL_CAPACITY * 4);
    }

    @Test(timeout = TIMEOUT)
    public void testAddToBackRegrows() {
        assertEquals(0, list.size());
        Integer[] expected = new Integer[]{new Integer(2), new Integer(3), new Integer(5), new Integer(7), new Integer(11), new Integer(13), new Integer(17), new Integer(19), new Integer(23), new Integer(27), new Integer(29)};

        for (Integer i : expected) {
            list.addToBack(i);
            if (list.size() < 11) {
                assertEquals(10, list.getBackingArray().length);
            }
        }
        assertEquals(11, list.size());
        checkBackingArray(expected, ArrayListInterface.INITIAL_CAPACITY * 2);
    }

    @Test(timeout = TIMEOUT)
    public void testAddToBackRegrowsTwice() {
        assertEquals(0, list.size());
        Integer[] expected = new Integer[]{new Integer(2), new Integer(3), new Integer(5), new Integer(7), new Integer(11), new Integer(13), new Integer(17), new Integer(19), new Integer(23), new Integer(27), new Integer(29)};

        for (Integer i : expected) {
            list.addToBack(i);
            if (list.size() < 11) {
                assertEquals(10, list.getBackingArray().length);
            }
        }
        assertEquals(11, list.size());
        checkBackingArray(expected, ArrayListInterface.INITIAL_CAPACITY * 2);
        expected = new Integer[]{expected[0], expected[1], expected[2], expected[3], expected[4], expected[5], expected[6], expected[7], expected[8], expected[9], expected[10], new Integer(2), new Integer(7), new Integer(5), new Integer(4), new Integer(4), new Integer(1), new Integer(7), new Integer(9), new Integer(0), new Integer(394)};
        for (int i = 11; i < expected.length; i++) {
            list.addToBack(expected[i]);
            if (list.size() < 21) {
                assertEquals(20, list.getBackingArray().length);
            }
        }
        assertEquals(21, list.size());
        checkBackingArray(expected, ArrayListInterface.INITIAL_CAPACITY * 4);
    }

    // 1 point
    @Test(timeout = TIMEOUT)
    public void testAddAtIndexFrontRegrows() {
        assertEquals(0, list.size());
        Integer[] expected = new Integer[]{new Integer(2), new Integer(3), new Integer(5), new Integer(7), new Integer(11), new Integer(13), new Integer(17), new Integer(19), new Integer(23), new Integer(27), new Integer(29)};

        for (int i = expected.length - 1; i >= 0; i--) {
            list.addAtIndex(0, expected[i]);
            if (list.size() < 11) {
                assertEquals(10, list.getBackingArray().length);
            }
        }
        assertEquals(11, list.size());
        checkBackingArray(expected, ArrayListInterface.INITIAL_CAPACITY * 2);
    }

    // 1 point
    @Test(timeout = TIMEOUT)
    public void testAddAtIndexBackRegrows() {
        assertEquals(0, list.size());
        Integer[] expected = new Integer[]{new Integer(2), new Integer(3), new Integer(5), new Integer(7), new Integer(11), new Integer(13), new Integer(17), new Integer(19), new Integer(23), new Integer(27), new Integer(29)};

        for (Integer i : expected) {
            list.addAtIndex(list.size(), i);
            if (list.size() < 11) {
                assertEquals(10, list.getBackingArray().length);
            }
        }
        assertEquals(11, list.size());
        checkBackingArray(expected, ArrayListInterface.INITIAL_CAPACITY * 2);
    }

    // 1 point
    @Test(timeout = TIMEOUT)
    public void testAddAtIndexMiddleRegrows() {
        assertEquals(0, list.size());
        Integer[] expected = new Integer[]{new Integer(2), new Integer(3), new Integer(5), new Integer(7), new Integer(11), new Integer(13), new Integer(17), new Integer(19), new Integer(23), new Integer(27), new Integer(29)};

        for (int i = 0; i < expected.length; i++) {
            if (i == 5) { // skip element 5
                continue;
            }
            list.addAtIndex(list.size(), expected[i]);
        }
        assertEquals(10, list.getBackingArray().length);
        list.addAtIndex(5, expected[5]);

        assertEquals(11, list.size());
        checkBackingArray(expected, ArrayListInterface.INITIAL_CAPACITY * 2);
    }

    @Test(timeout = TIMEOUT)
    public void testRemoveDoesNotRegrow() {
        assertEquals(0, list.size());
        Integer[] expected = new Integer[]{new Integer(2), new Integer(3), new Integer(5), new Integer(7), new Integer(11), new Integer(13), new Integer(17), new Integer(19), new Integer(23), new Integer(27), new Integer(29)};

        Integer[] actual = new Integer[20];
        for (int i = 0; i < expected.length; i++) {
            actual[i] = expected[i];
        }
        setBackingArray(list, actual, 11);

        assertSame(expected[0], list.removeFromFront());
        assertEquals(10, list.size());
        assertEquals(20, list.getBackingArray().length);
        list.addToFront(expected[0]);
        checkBackingArray(expected, ArrayListInterface.INITIAL_CAPACITY * 2);

        assertSame(expected[10], list.removeFromBack());
        assertEquals(10, list.size());
        assertEquals(20, list.getBackingArray().length);
        list.addToBack(expected[10]);
        checkBackingArray(expected, ArrayListInterface.INITIAL_CAPACITY * 2);

        assertSame(expected[0], list.removeAtIndex(0));
        assertEquals(10, list.size());
        assertEquals(20, list.getBackingArray().length);
        list.addToFront(expected[0]);
        checkBackingArray(expected, ArrayListInterface.INITIAL_CAPACITY * 2);

        assertSame(expected[10], list.removeAtIndex(10));
        assertEquals(10, list.size());
        assertEquals(20, list.getBackingArray().length);
        list.addToBack(expected[10]);
        checkBackingArray(expected, ArrayListInterface.INITIAL_CAPACITY * 2);

        assertSame(expected[3], list.removeAtIndex(3));
        assertEquals(10, list.size());
        assertEquals(20, list.getBackingArray().length);

    }

    @Test(timeout = TIMEOUT, expected = IndexOutOfBoundsException.class)
    public void testGetThrowsNegativeIndex() {
        try {
            list.get(-1);
        } catch (IndexOutOfBoundsException ex) {
            throw ex;
        }
    }

    @Test(timeout = TIMEOUT, expected = IndexOutOfBoundsException.class)
    public void testGetThrowsLargeIndex() {
        try {
            list.get(0);
        } catch (IndexOutOfBoundsException ex) {
            throw ex;
        }
    }

    @Test(timeout = TIMEOUT)
    public void testGetGeneral() {
        assertEquals(0, list.size());
        Integer[] expected = new Integer[]{new Integer(2), new Integer(3), new Integer(5), new Integer(7), new Integer(11), new Integer(13), new Integer(17), new Integer(19), new Integer(23), new Integer(27)};

        setBackingArray(list, expected, 10);

        for (int i = 0; i < expected.length; i++) {
            assertSame(expected[i], list.get(i));
        }

        assertEquals(10, list.size());

    }

    @Test(timeout = TIMEOUT)
    public void testClear() {
        assertEquals(0, list.size());
        list.clear();
        assertEquals(0, list.size());
        assertNotNull(list.getBackingArray());

        Integer[] expected = new Integer[]{new Integer(2), new Integer(3), new Integer(5), new Integer(7), new Integer(11), new Integer(13), new Integer(17), new Integer(19), new Integer(23), new Integer(27), new Integer(29)};

        Integer[] actual = new Integer[20];
        for (int i = 0; i < expected.length; i++) {
            actual[i] = expected[i];
        }
        setBackingArray(list, actual, 11);

        list.clear();
        assertEquals(0, list.size());
        assertEquals(10, list.getBackingArray().length);
        checkBackingArray(new Integer[0], ArrayListInterface.INITIAL_CAPACITY);
        assertEquals(list.getBackingArray().getClass(), Object[].class);

    }

    @Test(timeout = TIMEOUT)
    public void testIsEmpty() {
        assertEquals(0, list.size());
        assertTrue(list.isEmpty());

        Integer[] expected = new Integer[]{new Integer(2)};

        Integer[] actual = new Integer[10];
        actual[0] = expected[0];
        setBackingArray(list, actual, 1);

        assertFalse(list.isEmpty());

    }
}
