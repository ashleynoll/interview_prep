import org.junit.Before;
import org.junit.Test;

import java.util.NoSuchElementException;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;


public class HeapPQTests {
    private static final int TIMEOUT = 200;
    private static final int STARTING_SIZE = HeapInterface.STARTING_SIZE;

    private HeapInterface<Integer> minHeap;
    private PriorityQueueInterface<String> minPQ;

    @Before
    public void setUp() {
        minHeap = new MinHeap<>();
        minPQ = new MinPriorityQueue<>();
    }

    /**
     * Verifies whether the backing array has elements equal to elements from
     * expected array and whether it's size is equal to expected size.
     *
     * @param expected       the expected array
     * @param actual         the backing array
     * @param expectedLength the expected length of the backing array
     */
    private void checkBackingArray(Object[] expected, Object[] actual, int expectedLength) {

        assertEquals("Incorrect backing array length", expectedLength, actual.length);

        for (int i = 0; i < expected.length; i++) {
            assertEquals("Array incorrect at index " + i, expected[i], actual[i]);
        }

        for (int i = expected.length; i < actual.length; i++) {
            assertNull(actual[i]);
        }
    }

    @Test(timeout = TIMEOUT)
    public void testAddToEmptyHeap() {
        assertEquals("Has size initialized to a non-0 integer", 0, minHeap.size());
        assertTrue("isEmpty was false, should be true", minHeap.isEmpty());
        minHeap.add(43);

        Integer[] expected = new Integer[10];
        expected[1] = 43;

        assertArrayEquals("BackingArray did not match expected array", expected, ((MinHeap<Integer>) minHeap).getBackingArray());

        assertEquals("Did not properly increment size on at least the first " + "element", 1, minHeap.size());
        assertFalse("isEmpty was true, should be false", minHeap.isEmpty());
    }

    @Test(timeout = TIMEOUT)
    public void testAddNewRoot() {
        assertEquals("Has size initialized to a non-0 integer", 0, minHeap.size());
        assertTrue("Did not properly implement isEmpty", minHeap.isEmpty());
        minHeap.add(43);
        minHeap.add(42);
        minHeap.add(23);
        minHeap.add(1);

        /*                  Final heap:

                                1
                              /    \
                          23          42
                        /
                      43
         */

        Integer[] expected = new Integer[10];
        expected[1] = 1;
        expected[2] = 23;
        expected[3] = 42;
        expected[4] = 43;

        assertArrayEquals("BackingArray did not match expected array", expected, ((MinHeap<Integer>) minHeap).getBackingArray());

        assertEquals("Did not properly increment size", 4, minHeap.size());
        assertFalse("Did not properly implement isEmpty", minHeap.isEmpty());
    }

    @Test(timeout = TIMEOUT)
    public void testAddNewLeaves() {
        assertEquals("Has size initialized to a non-0 integer", 0, minHeap.size());
        assertTrue("Did not properly implement isEmpty", minHeap.isEmpty());
        minHeap.add(43);
        minHeap.add(56);
        minHeap.add(73);
        minHeap.add(81);

        /*                  Final heap:

                                43
                              /    \
                          56          73
                        /
                      81
         */

        Integer[] expected = new Integer[10];
        expected[1] = 43;
        expected[2] = 56;
        expected[3] = 73;
        expected[4] = 81;

        assertArrayEquals("BackingArray did not match expected array", expected, ((MinHeap<Integer>) minHeap).getBackingArray());

        assertEquals("Did not properly increment size", 4, minHeap.size());
        assertFalse("isEmpty was true, should be false", minHeap.isEmpty());
    }

    @Test(timeout = TIMEOUT)
    public void testAddInMiddleOfHeap() {
        assertEquals("Has size initialized to a non-0 integer", 0, minHeap.size());
        assertTrue("isEmpty was false, should be true", minHeap.isEmpty());
        minHeap.add(43);
        minHeap.add(56);
        minHeap.add(73);
        minHeap.add(81);
        minHeap.add(46);
        minHeap.add(64);
        minHeap.add(65);
        minHeap.add(45);

        /*                  Final heap:

                                43
                              /    \
                          45          64
                        /    \      /    \
                      46      56  73     65
                    /
                  81
         */


        Integer[] expected = new Integer[10];
        expected[1] = 43;
        expected[2] = 45;
        expected[3] = 64;
        expected[4] = 46;
        expected[5] = 56;
        expected[6] = 73;
        expected[7] = 65;
        expected[8] = 81;

        assertArrayEquals("BackingArray did not match expected array", expected, ((MinHeap<Integer>) minHeap).getBackingArray());

        assertEquals("Did not properly increment size", 8, minHeap.size());
        assertFalse("isEmpty was true, should be false", minHeap.isEmpty());
    }

    @Test(timeout = TIMEOUT)
    public void testAddMultipleData() {
        assertEquals("Has size initialized to a non-0 integer", 0, minHeap.size());
        assertTrue("isEmpty was false, should be true", minHeap.isEmpty());
        minHeap.add(43);
        minHeap.add(56);
        minHeap.add(73);
        minHeap.add(81);
        minHeap.add(46);
        minHeap.add(64);
        minHeap.add(65);
        minHeap.add(45);
        minHeap.add(40);

        /*                  Final heap:

                                40
                              /    \
                          43          64
                        /    \      /    \
                      45      56  73     65
                    /    \
                  81      46
         */


        Integer[] expected = new Integer[10];
        expected[1] = 40;
        expected[2] = 43;
        expected[3] = 64;
        expected[4] = 45;
        expected[5] = 56;
        expected[6] = 73;
        expected[7] = 65;
        expected[8] = 81;
        expected[9] = 46;

        assertArrayEquals("BackingArray did not match expected array", expected, ((MinHeap<Integer>) minHeap).getBackingArray());

        assertEquals("Did not properly increment size", 9, minHeap.size());
        assertFalse("isEmpty is true, should be false", minHeap.isEmpty());
    }

    @Test(timeout = TIMEOUT)
    public void testAddRegrowthOnce() {
        assertEquals("Has size initialized to a non-0 integer", 0, minHeap.size());
        assertTrue("isEmpty is false, should be true", minHeap.isEmpty());

        for (int i = 1; i <= 10; i++) {
            minHeap.add(i * 2);
        }

        Integer[] expected = new Integer[15];
        expected[1] = new Integer(2);
        expected[2] = new Integer(4);
        expected[3] = new Integer(6);
        expected[4] = new Integer(8);
        expected[5] = new Integer(10);
        expected[6] = new Integer(12);
        expected[7] = new Integer(14);
        expected[8] = new Integer(16);
        expected[9] = new Integer(18);
        expected[10] = new Integer(20);

        assertArrayEquals("BackingArray did not match expected array", expected, ((MinHeap<Integer>) minHeap).getBackingArray());

        assertEquals("Did not properly increment size", 10, minHeap.size());
        assertFalse("isEmpty was true, should be false", minHeap.isEmpty());
    }

    @Test(timeout = TIMEOUT)
    public void testAddRegrowthMulitpleTimes() {
        assertEquals("Has size initialized to a non-0 integer", 0, minHeap.size());
        assertTrue("Did not properly implement isEmpty", minHeap.isEmpty());

        for (int i = 1; i <= 10; i++) {
            minHeap.add(i * 2);
        }

        Integer[] expected = new Integer[15];
        expected[1] = new Integer(2);
        expected[2] = new Integer(4);
        expected[3] = new Integer(6);
        expected[4] = new Integer(8);
        expected[5] = new Integer(10);
        expected[6] = new Integer(12);
        expected[7] = new Integer(14);
        expected[8] = new Integer(16);
        expected[9] = new Integer(18);
        expected[10] = new Integer(20);

        assertArrayEquals("BackingArray did not match expected array", expected, ((MinHeap<Integer>) minHeap).getBackingArray());

        assertEquals("Did not properly increment size", 10, minHeap.size());
        assertFalse("isEmpty was false, should be true", minHeap.isEmpty());

        for (int i = 19; i >= 1; i -= 2) {
            minHeap.add(i);
        }

        expected = new Integer[22];
        expected[1] = new Integer(1);
        expected[2] = new Integer(2);
        expected[3] = new Integer(6);
        expected[4] = new Integer(4);
        expected[5] = new Integer(3);
        expected[6] = new Integer(12);
        expected[7] = new Integer(11);
        expected[8] = new Integer(8);
        expected[9] = new Integer(5);
        expected[10] = new Integer(10);
        expected[11] = new Integer(19);
        expected[12] = new Integer(17);
        expected[13] = new Integer(15);
        expected[14] = new Integer(14);
        expected[15] = new Integer(13);
        expected[16] = new Integer(16);
        expected[17] = new Integer(9);
        expected[18] = new Integer(18);
        expected[19] = new Integer(7);
        expected[20] = new Integer(20);

        assertArrayEquals("BackingArray did not match expected array", expected, ((MinHeap<Integer>) minHeap).getBackingArray());

        assertEquals("Did not properly increment size", 20, minHeap.size());
        assertFalse("isEmpty was false, should be true", minHeap.isEmpty());
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testExceptionHeapAddNullData() {
        try {
            minHeap.add(null);
        } catch (IllegalArgumentException e) {
            // Don't allow descendants of IllegalArgumentException
            if (e.getClass().equals(IllegalArgumentException.class)) {
                throw e;
            }
        }
    }

    @Test(timeout = TIMEOUT)
    public void testRemoveRootIsOnlyElement() {
        assertEquals("Has size initialized to a non-0 integer", 0, minHeap.size());
        assertTrue("isEmpty was false, should be true", minHeap.isEmpty());

        minHeap.add(10);

        Integer[] expected = new Integer[10];
        expected[1] = new Integer(10);
        assertEquals("Did not properly increment size", 1, minHeap.size());
        assertFalse("isEmpty was true, should be false", minHeap.isEmpty());
        assertArrayEquals("BackingArray did not match expected array", expected, ((MinHeap<Integer>) minHeap).getBackingArray());

        assertEquals("The incorrect data was removed", (Integer) 10, minHeap.remove());
        expected = new Integer[10];
        assertEquals("Did not properly decrement size", 0, minHeap.size());
        assertTrue("isEmpty was true, should be false", minHeap.isEmpty());
        assertArrayEquals("BackingArray did not match expected array", expected, ((MinHeap<Integer>) minHeap).getBackingArray());

    }

    @Test(timeout = TIMEOUT)
    public void testRemoveOnceWithTraversal() {
        assertEquals("Has size initialized to a non-0 integer", 0, minHeap.size());
        assertTrue("isEmpty was false, should be true", minHeap.isEmpty());
        Integer[] expected = new Integer[10];

        for (int i = 1; i <= 5; i++) {
            minHeap.add(i);
            expected[i] = new Integer(i);
        }

        assertEquals("Did not properly increment size", 5, minHeap.size());
        assertFalse("isEmpty was true, should be false", minHeap.isEmpty());
        assertArrayEquals(expected, ((MinHeap<Integer>) minHeap).getBackingArray());

        assertEquals("The incorrect data was removed", new Integer(1), minHeap.remove());
        expected = new Integer[10];
        assertFalse("isEmpty was true, should be false", minHeap.isEmpty());
        expected[1] = new Integer(2);
        expected[2] = new Integer(4);
        expected[3] = new Integer(3);
        expected[4] = new Integer(5);

        assertArrayEquals("BackingArray did not match expected array", expected, ((MinHeap<Integer>) minHeap).getBackingArray());
        assertEquals("Did not properly decrement size", 4, minHeap.size());
        assertFalse("isEmpty was true, should be false", minHeap.isEmpty());

    }

    @Test(timeout = TIMEOUT)
    public void testRemoveMultiple() {
        assertEquals("Has size initialized to a non-0 integer", 0, minHeap.size());
        assertTrue("isEmpty was false, should be true", minHeap.isEmpty());
        Integer[] expected = new Integer[10];

        for (int i = 1; i <= 9; i++) {
            minHeap.add(i);
            expected[i] = new Integer(i);
        }

        assertEquals("Did not properly increment size", 9, minHeap.size());
        assertFalse("isEmpty was true, should be false", minHeap.isEmpty());
        assertArrayEquals("BackingArray did not match expected array", expected, ((MinHeap<Integer>) minHeap).getBackingArray());

        assertEquals("The incorrect data was removed", new Integer(1), minHeap.remove());
        expected = new Integer[10];
        assertFalse("Did not properly implement isEmpty", minHeap.isEmpty());
        expected[1] = new Integer(2);
        expected[2] = new Integer(4);
        expected[3] = new Integer(3);
        expected[4] = new Integer(8);
        expected[5] = new Integer(5);
        expected[6] = new Integer(6);
        expected[7] = new Integer(7);
        expected[8] = new Integer(9);

        assertArrayEquals("BackingArray did not match expected array", expected, ((MinHeap<Integer>) minHeap).getBackingArray());
        assertEquals("Did not properly decrement size", 8, minHeap.size());
        assertFalse("isEmpty was true, should be false", minHeap.isEmpty());

        assertEquals("The incorrect data was removed", new Integer(2), minHeap.remove());
        expected = new Integer[10];
        assertFalse("isEmpty was true, should be false", minHeap.isEmpty());
        expected[1] = new Integer(3);
        expected[2] = new Integer(4);
        expected[3] = new Integer(6);
        expected[4] = new Integer(8);
        expected[5] = new Integer(5);
        expected[6] = new Integer(9);
        expected[7] = new Integer(7);

        assertArrayEquals("BackingArray did not match expected array", expected, ((MinHeap<Integer>) minHeap).getBackingArray());
        assertEquals("Did not properly decrement size", 7, minHeap.size());
        assertFalse("isEmpty was true, should be false", minHeap.isEmpty());

        assertEquals("The incorrect data was removed", new Integer(3), minHeap.remove());
        expected = new Integer[10];
        assertFalse("Did not properly implement isEmpty", minHeap.isEmpty());
        expected[1] = new Integer(4);
        expected[2] = new Integer(5);
        expected[3] = new Integer(6);
        expected[4] = new Integer(8);
        expected[5] = new Integer(7);
        expected[6] = new Integer(9);


        assertArrayEquals("BackingArray did not match expected array", expected, ((MinHeap<Integer>) minHeap).getBackingArray());
        assertEquals("Did not properly decrement size", 6, minHeap.size());
        assertFalse("isEmpty was true, should be false", minHeap.isEmpty());

        assertEquals("The incorrect data was removed", new Integer(4), minHeap.remove());
        expected = new Integer[10];
        assertFalse("Did not properly implement isEmpty", minHeap.isEmpty());
        expected[1] = new Integer(5);
        expected[2] = new Integer(7);
        expected[3] = new Integer(6);
        expected[4] = new Integer(8);
        expected[5] = new Integer(9);

        assertArrayEquals("BackingArray did not match expected array", expected, ((MinHeap<Integer>) minHeap).getBackingArray());
        assertEquals("Did not properly decrement size", 5, minHeap.size());
        assertFalse("isEmpty was true, should be false", minHeap.isEmpty());

        assertEquals("The incorrect data was removed", new Integer(5), minHeap.remove());
        expected = new Integer[10];
        assertFalse("isEmpty was true, should be false", minHeap.isEmpty());
        expected[1] = new Integer(6);
        expected[2] = new Integer(7);
        expected[3] = new Integer(9);
        expected[4] = new Integer(8);

        assertArrayEquals("BackingArray did not match expected array", expected, ((MinHeap<Integer>) minHeap).getBackingArray());
        assertEquals("Did not properly decrement size", 4, minHeap.size());
        assertFalse("isEmpty was true, should be false", minHeap.isEmpty());

        assertEquals("The incorrect data was removed", new Integer(6), minHeap.remove());
        expected = new Integer[10];
        assertFalse("isEmpty was true, should be false", minHeap.isEmpty());
        expected[1] = new Integer(7);
        expected[2] = new Integer(8);
        expected[3] = new Integer(9);

        assertArrayEquals("BackingArray did not match expected array", expected, ((MinHeap<Integer>) minHeap).getBackingArray());
        assertEquals("Did not properly decrement size", 3, minHeap.size());
        assertFalse("isEmpty was true, should be false", minHeap.isEmpty());

        assertEquals("The incorrect data was removed", new Integer(7), minHeap.remove());
        expected = new Integer[10];
        assertFalse("isEmpty was true, should be false", minHeap.isEmpty());
        expected[1] = new Integer(8);
        expected[2] = new Integer(9);

        assertArrayEquals("BackingArray did not match expected array", expected, ((MinHeap<Integer>) minHeap).getBackingArray());
        assertEquals("Did not properly decrement size", 2, minHeap.size());
        assertFalse("isEmpty was true, should be false", minHeap.isEmpty());

        assertEquals("The incorrect data was removed", new Integer(8), minHeap.remove());
        expected = new Integer[10];
        assertFalse("isEmpty was true, should be false", minHeap.isEmpty());
        expected[1] = new Integer(9);


        assertArrayEquals("BackingArray did not match expected array", expected, ((MinHeap<Integer>) minHeap).getBackingArray());
        assertEquals("Did not properly decrement size", 1, minHeap.size());
        assertFalse("isEmpty was true, should be false", minHeap.isEmpty());

        assertEquals("The incorrect data was removed", new Integer(9), minHeap.remove());
        expected = new Integer[10];

        assertArrayEquals("BackingArray did not match expected array", expected, ((MinHeap<Integer>) minHeap).getBackingArray());
        assertEquals("Did not properly decrement size", 0, minHeap.size());
        assertTrue("isEmpty was false, should be true", minHeap.isEmpty());

    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void testExceptionRemoveEmptyHeap() {
        try {
            minHeap.remove();
        } catch (NoSuchElementException e) {
            // Don't allow descendants of IllegalArgumentException
            if (e.getClass().equals(NoSuchElementException.class)) {
                throw e;
            }
        }
    }

    @Test(timeout = TIMEOUT)
    public void testClearHeap() {
        assertEquals("Has size initialized to a non-0 integer", 0, minHeap.size());
        assertTrue("isEmpty was false, should be true", minHeap.isEmpty());

        Comparable[] initial = ((MinHeap<Integer>) minHeap).getBackingArray();

        minHeap.add(10);
        minHeap.add(20);
        minHeap.add(30);

        Integer[] expected = new Integer[10];
        expected[1] = new Integer(10);
        expected[2] = new Integer(20);
        expected[3] = new Integer(30);

        assertEquals("Did not properly increment size", 3, minHeap.size());
        assertArrayEquals("BackingArray did not match expected array", expected, ((MinHeap<Integer>) minHeap).getBackingArray());
        assertFalse("isEmpty was true, should be false", minHeap.isEmpty());

        minHeap.clear();
        Comparable[] resultant = ((MinHeap<Integer>) minHeap).getBackingArray();
        assertEquals("Has size initialized to a non-0 integer", 0, minHeap.size());
        assertTrue("isEmpty was false, should be true", minHeap.isEmpty());
        expected = new Integer[10];
        assertArrayEquals("BackingArray did not match expected array", expected, ((MinHeap<Integer>) minHeap).getBackingArray());
        assertNotSame("Did not reset backingArray in O(1)", initial, resultant);
    }

    @Test(timeout = TIMEOUT)
    public void pqEnqueueOneElement() {
        String[] expected = {null, "4"};

        minPQ.enqueue(expected[1]);

        assertEquals(1, minPQ.size());
        assertFalse(minPQ.isEmpty());
        checkBackingArray(expected, minPQ.getBackingHeap().getBackingArray(), STARTING_SIZE);
    }

    @Test(timeout = TIMEOUT)
    public void pqEnqueueNoResize() {
        String[] values = {"40", "30", "20", "08", "12", "09", "04", "05", "02",};
        String[] expected = {null, "02", "04", "08", "05", "20", "30", "09", "40", "12"};

        for (String value : values) {
            minPQ.enqueue(value);
        }

        assertEquals(9, minPQ.size());
        assertFalse(minPQ.isEmpty());
        checkBackingArray(expected, minPQ.getBackingHeap().getBackingArray(), STARTING_SIZE);
    }

    @Test(timeout = TIMEOUT)
    public void pqEnqueueResizeOnce() {
        String[] values = {"40", "30", "20", "08", "12", "09", "04", "05", "02", "03", "01"};
        String[] expected = {null, "01", "02", "08", "05", "03", "30", "09", "40", "12", "20", "04"};

        for (String value : values) {
            minPQ.enqueue(value);
        }

        int newSize = STARTING_SIZE;
        newSize = (int) (1.5 * (double) newSize);

        assertEquals(11, minPQ.size());
        assertFalse(minPQ.isEmpty());
        checkBackingArray(expected, minPQ.getBackingHeap().getBackingArray(), newSize);
    }

    @Test(timeout = TIMEOUT)
    public void pqEnqueueMultipleResize() {
        String[] values = {"40", "30", "20", "08", "12", "09", "04", "05", "02", "03", "01", "36", "38", "39", "06", "00"

        };
        String[] expected = {null, "00", "01", "06", "02", "03", "30", "08", "05", "12", "20", "04", "36", "38", "39", "09", "40"};

        for (String value : values) {
            minPQ.enqueue(value);
        }

        int newSize = STARTING_SIZE;
        newSize = (int) (1.5 * (double) newSize);
        newSize = (int) (1.5 * (double) newSize);

        assertEquals(16, minPQ.size());
        assertFalse(minPQ.isEmpty());
        checkBackingArray(expected, minPQ.getBackingHeap().getBackingArray(), newSize);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void pqEnqueueThrowsException() {
        try {
            minPQ.enqueue(null);
        } catch (IllegalArgumentException e) {
            assertEquals(IllegalArgumentException.class, e.getClass());
            throw e;
        }
    }

    @Test(timeout = TIMEOUT)
    public void pqDequeueOneElement() {
        String[] values = {"4"};
        String[] expected = {};


        minPQ.enqueue(values[0]);
        assertSame(values[0], minPQ.dequeue());

        assertEquals(0, minPQ.size());
        assertTrue(minPQ.isEmpty());
        checkBackingArray(expected, minPQ.getBackingHeap().getBackingArray(), STARTING_SIZE);
    }

    @Test(timeout = TIMEOUT)
    public void pqDequeueMultipleElements() {
        String[] values = {"01", "02", "03", "04", "05", "06", "07", "08"};
        String[] expected = {null, "03", "04", "06", "08", "05", "07"};

        for (String value : values) {
            minPQ.enqueue(value);
        }

        assertSame(values[0], minPQ.dequeue());
        assertSame(values[1], minPQ.dequeue());

        assertEquals(6, minPQ.size());
        assertFalse(minPQ.isEmpty());
        checkBackingArray(expected, minPQ.getBackingHeap().getBackingArray(), STARTING_SIZE);
    }

    @Test(timeout = TIMEOUT)
    public void pqDequeueUntilEmpty() {
        String[] values = {"01", "02", "03", "04", "05", "06", "07", "08"};
        String[] expected = {};

        for (String value1 : values) {
            minPQ.enqueue(value1);
        }

        for (String value : values) {
            assertSame(value, minPQ.dequeue());
        }

        assertEquals(0, minPQ.size());
        assertTrue(minPQ.isEmpty());
        checkBackingArray(expected, minPQ.getBackingHeap().getBackingArray(), STARTING_SIZE);
    }

    @Test(timeout = TIMEOUT)
    public void pqDequeueAfterResize() {
        String[] values = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11",};
        String[] expected = {null, "06", "08", "07", "11", "09", "10"};

        for (String value : values) {
            minPQ.enqueue(value);
        }

        assertSame(values[0], minPQ.dequeue());
        assertSame(values[1], minPQ.dequeue());
        assertSame(values[2], minPQ.dequeue());
        assertSame(values[3], minPQ.dequeue());
        assertSame(values[4], minPQ.dequeue());

        int newSize = STARTING_SIZE;
        newSize = (int) (1.5 * (double) newSize);

        assertEquals(6, minPQ.size());
        assertFalse(minPQ.isEmpty());
        checkBackingArray(expected, minPQ.getBackingHeap().getBackingArray(), newSize);
    }

    @Test(timeout = TIMEOUT)
    public void pqDequeueEnqueueAfterResize() {
        String[] values = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11",};
        String[] values2 = {"20", "21", "22", "23", "24",};
        String[] expected = {null, "06", "08", "07", "11", "09", "10", "20", "21", "22", "23", "24",};

        for (String value : values) {
            minPQ.enqueue(value);
        }

        assertSame(values[0], minPQ.dequeue());
        assertSame(values[1], minPQ.dequeue());
        assertSame(values[2], minPQ.dequeue());
        assertSame(values[3], minPQ.dequeue());
        assertSame(values[4], minPQ.dequeue());

        for (String value : values2) {
            minPQ.enqueue(value);
        }

        int newSize = STARTING_SIZE;
        newSize = (int) (1.5 * (double) newSize);

        assertEquals(11, minPQ.size());
        assertFalse(minPQ.isEmpty());
        checkBackingArray(expected, minPQ.getBackingHeap().getBackingArray(), newSize);
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void pqThrowsException() {
        try {
            minPQ.dequeue();
        } catch (NoSuchElementException e) {
            assertEquals(NoSuchElementException.class, e.getClass());
            throw e;
        }
    }

    @Test(timeout = TIMEOUT)
    public void pqClearEmptyQueue() {
        String[] expected = {};

        minPQ.clear();

        assertEquals(0, minPQ.size());
        assertTrue(minPQ.isEmpty());
        checkBackingArray(expected, minPQ.getBackingHeap().getBackingArray(), STARTING_SIZE);
    }

    @Test(timeout = TIMEOUT)
    public void pqClearNoResize() {
        String[] values = {"01", "02", "03", "04", "05", "06", "07", "08"};
        String[] expected = {};

        for (String value : values) {
            minPQ.enqueue(value);
        }

        minPQ.clear();

        assertEquals(0, minPQ.size());
        assertTrue(minPQ.isEmpty());
        checkBackingArray(expected, minPQ.getBackingHeap().getBackingArray(), STARTING_SIZE);
    }

    @Test(timeout = TIMEOUT)
    public void pqClearRestoreInitialSize() {
        String[] values = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
        String[] expected = {};

        for (String value : values) {
            minPQ.enqueue(value);
        }

        minPQ.clear();

        assertEquals(0, minPQ.size());
        assertTrue(minPQ.isEmpty());
        checkBackingArray(expected, minPQ.getBackingHeap().getBackingArray(), STARTING_SIZE);
    }
}
