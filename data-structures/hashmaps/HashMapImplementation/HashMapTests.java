import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@SuppressWarnings("unchecked")
public class HashMapTests {

    private HashMap<Person, String> testMap;
    private MapEntry<Person, String>[] correctTable;
    private MapEntry<NegPerson, String>[] correctTableNeg;
    private HashMap<MyInteger, String> hashMap;
    private static final int TIMEOUT = 200;

    @Before
    public void setup() {
        testMap = new HashMap<>();
        correctTable = (MapEntry<Person, String>[]) new MapEntry[HashMapInterface.INITIAL_CAPACITY];
        correctTableNeg = (MapEntry<NegPerson, String>[]) new MapEntry[HashMapInterface.INITIAL_CAPACITY];
        hashMap = new HashMap<>();
    }


    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testRemoveNullKey() {
        try {
            hashMap.remove(null);
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("Incorrect exception thrown", java.lang.IllegalArgumentException.class, e.getClass());
            throw e;
        }
    }


    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void testRemoveNoKeySimple() {
        makeSimpleHashMap();
        try {
            hashMap.remove(new MyInteger(2));
        } catch (NoSuchElementException e) {
            Assert.assertEquals("Incorrect exception thrown", java.util.NoSuchElementException.class, e.getClass());
            assertSimpleHashMapUnchanged();
            throw e;
        }
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void testRemoveNoKeyGeneral() {
        makeLargeHashMap();
        int caught = 0;
        for (int i = -1000; i <= 1000; i++) {
            MyInteger testEntry = new MyInteger(i);
            if (!testEntry.equals(new MyInteger(0)) && !testEntry.equals(new MyInteger(32)) && !testEntry.equals(new MyInteger(64)) && !testEntry.equals(new MyInteger(45)) && !testEntry.equals(new MyInteger(-45)) && !testEntry.equals(new MyInteger(369)) && !testEntry.equals(new MyInteger(-506)) && !testEntry.equals(new MyInteger(-23)) && !testEntry.equals(new MyInteger(53))) {
                try {
                    hashMap.remove(testEntry);
                } catch (NoSuchElementException e) {
                    Assert.assertEquals("Incorrect exception thrown", java.util.NoSuchElementException.class, e.getClass());
                    caught++;
                    assertLargeHashMapUnchanged();
                }
            }
        }
        assertEquals(caught, 1992);
        try {
            hashMap.remove(new MyInteger(Integer.MAX_VALUE));
        } catch (NoSuchElementException e) {
            Assert.assertEquals("Incorrect exception thrown", java.util.NoSuchElementException.class, e.getClass());
            try {
                assertLargeHashMapUnchanged();
                hashMap.remove(new MyInteger(Integer.MIN_VALUE + 1));
            } catch (NoSuchElementException e2) {
                Assert.assertEquals("Incorrect exception thrown", java.util.NoSuchElementException.class, e2.getClass());
                assertLargeHashMapUnchanged();
                try {
                    makeFilledHashMap();
                    hashMap.remove(new MyInteger(14));
                } catch (NoSuchElementException e3) {
                    Assert.assertEquals("Incorrect exception thrown", java.util.NoSuchElementException.class, e3.getClass());
                    assertFilledHashMapUnchanged();
                    throw e3;
                }
            }
        }
    }

    @Test(timeout = TIMEOUT)
    public void removeMultipleSimple() {
        makeSimpleHashMap();
        ArrayList<Integer> removedIndices = new ArrayList<>();
        removeAndAssertFullySimpleHashMap(removedIndices, 0, 26);
        removeAndAssertFullySimpleHashMap(removedIndices, 4, -30);
        removeAndAssertFullySimpleHashMap(removedIndices, 10, 335);
        removeAndAssertFullySimpleHashMap(removedIndices, 7, -33);
        removeAndAssertFullySimpleHashMap(removedIndices, 1, -1);
        removeAndAssertFullySimpleHashMap(removedIndices, 11, 336);
        removeAndAssertFullySimpleHashMap(removedIndices, 5, 5);

    }

    @Test(timeout = TIMEOUT)
    public void removeDoesNotShrink() {
        makeLargeHashMap();
        ArrayList<Integer> removedIntegers = new ArrayList<>();
        removeAndAssertFullyLargeHashMap(removedIntegers, 10, 64);
        removeAndAssertFullyLargeHashMap(removedIntegers, 26, 53);

    }

    @Test(timeout = TIMEOUT)
    public void removeWithSingleCollision() {
        makeLargeHashMap();
        ArrayList<Integer> removedIntegers = new ArrayList<>();
        removeAndAssertFullyLargeHashMap(removedIntegers, 19, -45);
    }

    @Test(timeout = TIMEOUT)
    public void removeWithSingleRemovedCollision() {
        makeLargeHashMap();
        ArrayList<Integer> removedIntegers = new ArrayList<>();
        removeAndAssertFullyLargeHashMap(removedIntegers, 1, 0);
    }

    @Test(timeout = TIMEOUT)
    public void removeWithMultipleRemovedCollisions() {
        makeLargeHashMap();
        ArrayList<Integer> removedIntegers = new ArrayList<>();
        removeAndAssertFullyLargeHashMap(removedIntegers, 23, -23);
        removeAndAssertFullyLargeHashMap(removedIntegers, 21, 369);
        removeAndAssertFullyLargeHashMap(removedIntegers, 22, -506);
        removeAndAssertFullyLargeHashMap(removedIntegers, 19, -45);
        removeAndAssertFullyLargeHashMap(removedIntegers, 18, 45);
        removeAndAssertFullyLargeHashMap(removedIntegers, 6, 32);
        removeAndAssertFullyLargeHashMap(removedIntegers, 10, 64);
        removeAndAssertFullyLargeHashMap(removedIntegers, 1, 0);

    }


    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testContainsNullKey() {
        try {
            hashMap.containsKey(null);
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("Incorrect exception thrown", java.lang.IllegalArgumentException.class, e.getClass());
            throw e;
        }
    }

    @Test(timeout = TIMEOUT)
    public void testContainsFalseBasic() {
        for (int i = -1000; i <= 1000; i++) {
            assertFalse(hashMap.containsKey(new MyInteger(i)));
        }
        assertFalse(hashMap.containsKey(new MyInteger(Integer.MAX_VALUE)));
        assertFalse(hashMap.containsKey(new MyInteger(Integer.MIN_VALUE + 1)));

        makeSimpleHashMap();
        for (int i = -1000; i <= 1000; i++) {
            MyInteger test = new MyInteger(i);
            boolean actual = hashMap.containsKey(new MyInteger(i));
            if (!test.equals(new MyInteger(26)) && !test.equals(new MyInteger(-1)) && !test.equals(new MyInteger(-30)) && !test.equals(new MyInteger(5)) && !test.equals(new MyInteger(-33)) && !test.equals(new MyInteger(335)) && !test.equals(new MyInteger(336))) {
                assertFalse(actual);
                assertSimpleHashMapUnchanged();
            }
        }
        assertFalse(hashMap.containsKey(new MyInteger(Integer.MAX_VALUE)));
        assertSimpleHashMapUnchanged();
        assertFalse(hashMap.containsKey(new MyInteger(Integer.MIN_VALUE + 1)));
        assertSimpleHashMapUnchanged();

    }

    @Test(timeout = TIMEOUT)
    public void testContainsTrueBasic() {
        makeSimpleHashMap();
        assertTrue(hashMap.containsKey(new MyInteger(26)));
        assertSimpleHashMapUnchanged();
        assertTrue(hashMap.containsKey(new MyInteger(-1)));
        assertSimpleHashMapUnchanged();
        assertTrue(hashMap.containsKey(new MyInteger(-30)));
        assertSimpleHashMapUnchanged();
        assertTrue(hashMap.containsKey(new MyInteger(5)));
        assertSimpleHashMapUnchanged();
        assertTrue(hashMap.containsKey(new MyInteger(-33)));
        assertSimpleHashMapUnchanged();
        assertTrue(hashMap.containsKey(new MyInteger(335)));
        assertSimpleHashMapUnchanged();
        assertTrue(hashMap.containsKey(new MyInteger(336)));
        assertSimpleHashMapUnchanged();

    }

    @Test(timeout = TIMEOUT)
    public void testContainsGeneral() {
        makeLargeHashMap();
        for (int i = -1000; i <= 1000; i++) {
            MyInteger testEntry = new MyInteger(i);
            boolean actual = hashMap.containsKey(testEntry);
            if (testEntry.equals(new MyInteger(0)) || testEntry.equals(new MyInteger(32)) || testEntry.equals(new MyInteger(64)) || testEntry.equals(new MyInteger(45)) || testEntry.equals(new MyInteger(-45)) || testEntry.equals(new MyInteger(369)) || testEntry.equals(new MyInteger(-506)) || testEntry.equals(new MyInteger(-23)) || testEntry.equals(new MyInteger(53))) {
                assertTrue(actual);
                assertLargeHashMapUnchanged();
            } else {
                assertFalse(actual);
                assertLargeHashMapUnchanged();
            }
        }
        assertFalse(hashMap.containsKey(new MyInteger(Integer.MAX_VALUE)));
        assertLargeHashMapUnchanged();
        assertFalse(hashMap.containsKey(new MyInteger(Integer.MIN_VALUE + 1)));
        assertLargeHashMapUnchanged();


        makeFilledHashMap();
        for (int i = -1000; i <= 1000; i++) {
            MyInteger testEntry = new MyInteger(i);
            boolean actual = hashMap.containsKey(testEntry);
            if (i < 6 || i >= 13) {
                assertFalse(actual);
                assertFilledHashMapUnchanged();
            } else {
                assertTrue(actual);
                assertFilledHashMapUnchanged();
            }
        }
    }


    @Test(timeout = TIMEOUT)
    public void testValuesEmptyMap() {
        assertEquals(new ArrayList<String>(), hashMap.values());
        assertEquals(0, hashMap.size());
    }


    @Test(timeout = TIMEOUT)
    public void testValuesNoRemoval() {
        makeSimpleHashMap();

        ArrayList<String> expected = new ArrayList<>();
        for (int i = 0; i < hashMap.getTable().length; i++) {
            if (hashMap.getTable()[i] != null) {
                expected.add(hashMap.getTable()[i].getValue());
            }
        }

        assertEquals(expected, hashMap.values());
        assertEquals(7, hashMap.size());

        assertSimpleHashMapUnchanged();
    }

    @Test(timeout = TIMEOUT)
    public void testValuesGeneral() {
        makeLargeHashMap();
        ArrayList<String> expected = new ArrayList<>();
        for (int i = 0; i < hashMap.getTable().length; i++) {
            if (hashMap.getTable()[i] != null && !hashMap.getTable()[i].isRemoved()) {
                expected.add(hashMap.getTable()[i].getValue());
            }
        }

        assertEquals(expected, hashMap.values());

        assertLargeHashMapUnchanged();
    }

    @Test(timeout = TIMEOUT)
    public void testClear() {
        hashMap.clear();
        assertArrayEquals((MapEntry<MyInteger, String>[]) new MapEntry[HashMapInterface.INITIAL_CAPACITY], hashMap.getTable());
        assertEquals(0, hashMap.size());
        makeLargeHashMap();
        hashMap.clear();
        assertArrayEquals((MapEntry<MyInteger, String>[]) new MapEntry[HashMapInterface.INITIAL_CAPACITY], hashMap.getTable());
        assertEquals(0, hashMap.size());
    }

    @Test(timeout = TIMEOUT)
    public void testResizeBackingTableNegativeLength() {
        makeSimpleHashMap();
        int caught = 0;
        for (int i = -1000; i < 0; i++) {
            try {
                hashMap.resizeBackingTable(i);
            } catch (IllegalArgumentException e) {
                Assert.assertEquals("Incorrect exception thrown", java.lang.IllegalArgumentException.class, e.getClass());
                assertSimpleHashMapUnchanged();
                caught++;
            }
        }
        assertEquals(caught, 1000);
    }

    @Test(timeout = TIMEOUT)
    public void testResizeBackingTableSmallSize() {
        makeSimpleHashMap();
        int caught = 0;
        for (int i = 0; i < 13; i++) {
            try {
                hashMap.resizeBackingTable(i);
            } catch (IllegalArgumentException e) {
                Assert.assertEquals("Incorrect exception thrown", java.lang.IllegalArgumentException.class, e.getClass());
                assertSimpleHashMapUnchanged();
                caught++;
            }
        }
        assertEquals(caught, 7);
        caught = 0;
        makeLargeHashMap();
        for (int i = 0; i < 27; i++) {
            try {
                hashMap.resizeBackingTable(i);
            } catch (IllegalArgumentException e) {
                Assert.assertEquals("Incorrect exception thrown", java.lang.IllegalArgumentException.class, e.getClass());
                assertLargeHashMapUnchanged();
                caught++;
            }
        }
        assertEquals(caught, 9);

    }

    @Test(timeout = TIMEOUT)
    public void testResizeBackingTable() {
        makeLargeHashMap();
        hashMap.resizeBackingTable(29);
        MapEntry<MyInteger, String>[] table = (MapEntry<MyInteger, String>[]) new MapEntry[29];

        table[0] = new MapEntry<>(new MyInteger(0), "Mixed Values");
        table[3] = new MapEntry<>(new MyInteger(32), "Mixed Values");
        table[6] = new MapEntry<>(new MyInteger(64), "Mixed Values");
        table[16] = new MapEntry<>(new MyInteger(45), "Mixed Values");
        table[17] = new MapEntry<>(new MyInteger(-45), "Mixed Values");
        table[21] = new MapEntry<>(new MyInteger(369), "Mixed Values");
        table[13] = new MapEntry<>(new MyInteger(-506), "Mixed Values");
        table[23] = new MapEntry<>(new MyInteger(-23), "Mixed Values");
        table[24] = new MapEntry<>(new MyInteger(53), "Mixed Values");

        assertArrayEquals(table, hashMap.getTable());
        assertEquals(hashMap.size(), 9);

    }

    /**
     * Makes a HashMap having a non-null entry in every cell.
     */
    private void makeFilledHashMap() {
        MapEntry<MyInteger, String>[] table = (MapEntry<MyInteger, String>[]) new MapEntry[HashMapInterface.INITIAL_CAPACITY];

        table[0] = new MapEntry<>(new MyInteger(0), "Test");
        table[1] = new MapEntry<>(new MyInteger(1), "Test");
        table[2] = new MapEntry<>(new MyInteger(2), "Test");
        table[3] = new MapEntry<>(new MyInteger(3), "Test");
        table[4] = new MapEntry<>(new MyInteger(4), "Test");
        table[5] = new MapEntry<>(new MyInteger(5), "Test");
        table[6] = new MapEntry<>(new MyInteger(6), "Test");
        table[7] = new MapEntry<>(new MyInteger(7), "Test");
        table[8] = new MapEntry<>(new MyInteger(8), "Test");
        table[9] = new MapEntry<>(new MyInteger(9), "Test");
        table[10] = new MapEntry<>(new MyInteger(10), "Test");
        table[11] = new MapEntry<>(new MyInteger(11), "Test");
        table[12] = new MapEntry<>(new MyInteger(12), "Test");

        for (int j = 0; j < 6; j++) {
            table[j].setRemoved(true);
        }
        setTableAndSize(hashMap, table, 7);
    }

    /**
     * Ensures that the FilledHashMap did not change.
     */
    private void assertFilledHashMapUnchanged() {
        MapEntry<MyInteger, String>[] expectedArr = (MapEntry<MyInteger, String>[]) new MapEntry[HashMapInterface.INITIAL_CAPACITY];


        expectedArr[0] = new MapEntry<>(new MyInteger(0), "Test");
        expectedArr[1] = new MapEntry<>(new MyInteger(1), "Test");
        expectedArr[2] = new MapEntry<>(new MyInteger(2), "Test");
        expectedArr[3] = new MapEntry<>(new MyInteger(3), "Test");
        expectedArr[4] = new MapEntry<>(new MyInteger(4), "Test");
        expectedArr[5] = new MapEntry<>(new MyInteger(5), "Test");
        expectedArr[6] = new MapEntry<>(new MyInteger(6), "Test");
        expectedArr[7] = new MapEntry<>(new MyInteger(7), "Test");
        expectedArr[8] = new MapEntry<>(new MyInteger(8), "Test");
        expectedArr[9] = new MapEntry<>(new MyInteger(9), "Test");
        expectedArr[10] = new MapEntry<>(new MyInteger(10), "Test");
        expectedArr[11] = new MapEntry<>(new MyInteger(11), "Test");
        expectedArr[12] = new MapEntry<>(new MyInteger(12), "Test");

        for (int j = 0; j < 6; j++) {
            expectedArr[j].setRemoved(true);
        }

        assertArrayEquals(expectedArr, hashMap.getTable());
        assertEquals(hashMap.size(), 7);
    }

    /**
     * Makes a simple HashMap with no collisions and elements removed.
     */
    private void makeSimpleHashMap() {
        MapEntry<MyInteger, String>[] table = (MapEntry<MyInteger, String>[]) new MapEntry[HashMapInterface.INITIAL_CAPACITY];

        table[0] = new MapEntry<>(new MyInteger(26), "Test");
        table[1] = new MapEntry<>(new MyInteger(-1), "Test");
        table[4] = new MapEntry<>(new MyInteger(-30), "Test");
        table[5] = new MapEntry<>(new MyInteger(5), "Test");
        table[7] = new MapEntry<>(new MyInteger(-33), "Test");
        table[10] = new MapEntry<>(new MyInteger(335), "Test");
        table[11] = new MapEntry<>(new MyInteger(336), "Test");
        setTableAndSize(hashMap, table, 7);
    }

    /**
     * Used to keep track a list of removed indices from the original simple
     * HashMap along with asserting that the backing table and size are correct
     * after removing the provided key that hashes to the provided index.
     *
     * @param removedIndices list of indices removed from the original
     *                       simple HashMap.
     * @param removeIndex    The index of the element to be removed.
     * @param removeKey      The key of the element to be removed.
     */
    private void removeAndAssertFullySimpleHashMap(ArrayList<Integer> removedIndices, int removeIndex, int removeKey) {
        String removedValue = hashMap.getTable()[removeIndex].getValue();
        assertTrue(removedValue.equals(hashMap.remove(new MyInteger(removeKey))));
        removedIndices.add(removeIndex);
        removeAndAssertSimpleHashMapExpected(removedIndices);
    }

    /**
     * Asserts that the original simple HashMap has been changed.
     */
    private void assertSimpleHashMapUnchanged() {
        removeAndAssertSimpleHashMapExpected(new ArrayList<>());
    }

    /**
     * Removes all the elements at the provided indices in the backing table
     * of the simple HashMap
     *
     * @param removed the list of indices to be removed.
     */
    private void removeAndAssertSimpleHashMapExpected(ArrayList<Integer> removed) {
        MapEntry<MyInteger, String>[] expectedArr = (MapEntry<MyInteger, String>[]) new MapEntry[HashMapInterface.INITIAL_CAPACITY];

        expectedArr[0] = new MapEntry<>(new MyInteger(26), "Test");
        expectedArr[1] = new MapEntry<>(new MyInteger(-1), "Test");
        expectedArr[4] = new MapEntry<>(new MyInteger(-30), "Test");
        expectedArr[5] = new MapEntry<>(new MyInteger(5), "Test");
        expectedArr[7] = new MapEntry<>(new MyInteger(-33), "Test");
        expectedArr[10] = new MapEntry<>(new MyInteger(335), "Test");
        expectedArr[11] = new MapEntry<>(new MyInteger(336), "Test");
        for (Integer current : removed) {
            expectedArr[current].setRemoved(true);
        }
        assertArrayEquals(expectedArr, hashMap.getTable());
        assertEquals(hashMap.size(), 7 - removed.size());
    }


    /**
     * Makes a large hashmap with some elements removed and lots of collisions.
     */
    private void makeLargeHashMap() {
        MapEntry<MyInteger, String>[] table = (MapEntry<MyInteger, String>[]) new MapEntry[2 * HashMapInterface.INITIAL_CAPACITY + 1];

        table[0] = new MapEntry<>(new MyInteger(27), "Mixed Values");
        table[0].setRemoved(true);
        table[1] = new MapEntry<>(new MyInteger(0), "Mixed Values");
        table[5] = new MapEntry<>(new MyInteger(-32), "Mixed Values");
        table[5].setRemoved(true);
        table[6] = new MapEntry<>(new MyInteger(32), "Mixed Values");
        table[10] = new MapEntry<>(new MyInteger(64), "Mixed Values");
        table[11] = new MapEntry<>(new MyInteger(-64), "Mixed Values");
        table[11].setRemoved(true);
        table[14] = new MapEntry<>(new MyInteger(14), "Mixed Values");
        table[14].setRemoved(true);
        table[18] = new MapEntry<>(new MyInteger(45), "Mixed Values");
        table[19] = new MapEntry<>(new MyInteger(-45), "Mixed Values");
        table[20] = new MapEntry<>(new MyInteger(-46), "Mixed Values");
        table[20].setRemoved(true);
        table[21] = new MapEntry<>(new MyInteger(369), "Mixed Values");
        table[22] = new MapEntry<>(new MyInteger(-506), "Mixed Values");
        table[23] = new MapEntry<>(new MyInteger(-23), "Mixed Values");
        table[26] = new MapEntry<>(new MyInteger(53), "Mixed Values");

        setTableAndSize(hashMap, table, 9);

    }

    /**
     * Used to keep track a list of removed indices from the original large
     * HashMap along with asserting that the backing table and size are correct
     * after removing the provided key that hashes to the provided index.
     *
     * @param removedIndices list of indices removed from the original
     *                       large HashMap.
     * @param removeIndex    The index of the element to be removed.
     * @param removeKey      The key of the element to be removed.
     */
    private void removeAndAssertFullyLargeHashMap(ArrayList<Integer> removedIndices, int removeIndex, int removeKey) {
        String removedValue = hashMap.getTable()[removeIndex].getValue();
        assertTrue(removedValue.equals(hashMap.remove(new MyInteger(removeKey))));
        removedIndices.add(removeIndex);
        removeAndAssertLargeHashMapExpected(removedIndices);
    }

    /**
     * Asserts that the original simple HashMap has been changed.
     */
    private void assertLargeHashMapUnchanged() {
        removeAndAssertLargeHashMapExpected(new ArrayList<>());
    }

    /**
     * Removes all the elements at the provided indices in the backing table
     * of the large HashMap
     *
     * @param removed the list of indices to be removed.
     */
    private void removeAndAssertLargeHashMapExpected(ArrayList<Integer> removed) {
        MapEntry<MyInteger, String>[] expectedArr = (MapEntry<MyInteger, String>[]) new MapEntry[2 * HashMapInterface.INITIAL_CAPACITY + 1];
        expectedArr[0] = new MapEntry<>(new MyInteger(27), "Mixed Values");
        expectedArr[0].setRemoved(true);
        expectedArr[1] = new MapEntry<>(new MyInteger(0), "Mixed Values");
        expectedArr[5] = new MapEntry<>(new MyInteger(-32), "Mixed Values");
        expectedArr[5].setRemoved(true);
        expectedArr[6] = new MapEntry<>(new MyInteger(32), "Mixed Values");
        expectedArr[10] = new MapEntry<>(new MyInteger(64), "Mixed Values");
        expectedArr[11] = new MapEntry<>(new MyInteger(-64), "Mixed Values");
        expectedArr[11].setRemoved(true);
        expectedArr[14] = new MapEntry<>(new MyInteger(14), "Mixed Values");
        expectedArr[14].setRemoved(true);
        expectedArr[18] = new MapEntry<>(new MyInteger(45), "Mixed Values");
        expectedArr[19] = new MapEntry<>(new MyInteger(-45), "Mixed Values");
        expectedArr[20] = new MapEntry<>(new MyInteger(-46), "Mixed Values");
        expectedArr[20].setRemoved(true);
        expectedArr[21] = new MapEntry<>(new MyInteger(369), "Mixed Values");
        expectedArr[22] = new MapEntry<>(new MyInteger(-506), "Mixed Values");
        expectedArr[23] = new MapEntry<>(new MyInteger(-23), "Mixed Values");
        expectedArr[26] = new MapEntry<>(new MyInteger(53), "Mixed Values");
        for (Integer current : removed) {
            expectedArr[current].setRemoved(true);
        }
        assertArrayEquals(expectedArr, hashMap.getTable());
        assertEquals(hashMap.size(), 9 - removed.size());
    }

    //put() tests

    // put null key
    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testPutNullKey() {
        try {
            testMap.put(null, "Jojo");
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("Incorrect exception thrown", java.lang.IllegalArgumentException.class, e.getClass());
            assertArrayEquals(correctTable, testMap.getTable());
            throw e;
        }
    }

    // put null value
    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testPutNullValue() {
        try {
            testMap.put((new Person("Jojo")), null);
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("Incorrect exception thrown", java.lang.IllegalArgumentException.class, e.getClass());
            assertArrayEquals(correctTable, testMap.getTable());
            throw e;
        }
    }

    // put to empty
    @Test(timeout = TIMEOUT)
    public void testPutEmpty() {
        correctTable[0] = new MapEntry<>(new Person("Ash"), "Ashley");

        assertNull("Put does not return null if new key", testMap.put(new Person("Ash"), "Ashley"));

        assertArrayEquals(correctTable, testMap.getTable());
        assertEquals("Size is incorrect", 1, testMap.size());

    }

    // general case, no key overlap (i.e.
    // hash codes are different), no resizing
    @Test(timeout = TIMEOUT)
    public void testPutGeneral() {
        populateHashMap();
        correctTable['A' % correctTable.length] = new MapEntry<>(new Person("Ash"), "Ashley");
        correctTable['B' % correctTable.length] = new MapEntry<>(new Person("Bob"), "Robert");
        correctTable['C' % correctTable.length] = new MapEntry<>(new Person("Chris"), "Christopher");
        correctTable['D' % correctTable.length] = new MapEntry<>(new Person("Dill"), "Dilbert");
        correctTable['E' % correctTable.length] = new MapEntry<>(new Person("Ed"), "Edward");

        correctTable['F' % correctTable.length] = new MapEntry<>(new Person("Frank"), "Franklyn");

        assertNull("Put does not return null if new key", testMap.put(new Person("Frank"), "Franklyn"));

        assertArrayEquals(correctTable, testMap.getTable());
        assertEquals("Size is incorrect", 6, testMap.size());

        correctTable['G' % correctTable.length] = new MapEntry<>(new Person("Gina"), "Georgina");

        assertNull("Put does not return null if new key", testMap.put(new Person("Gina"), "Georgina"));

        assertArrayEquals(correctTable, testMap.getTable());
        assertEquals("Size is incorrect", 7, testMap.size());

        correctTable['H' % correctTable.length] = new MapEntry<>(new Person("Harry"), "Harriet");

        assertNull("Put does not return null if new key", testMap.put(new Person("Harry"), "Harriet"));


        assertArrayEquals(correctTable, testMap.getTable());
        assertEquals("Size is incorrect", 8, testMap.size());

    }

    // put single collision
    @Test(timeout = TIMEOUT)
    public void testPutSingleCollision() {
        populateHashMap();
        correctTable['A' % correctTable.length] = new MapEntry<>(new Person("Ash"), "Ashley");
        correctTable['B' % correctTable.length] = new MapEntry<>(new Person("Bob"), "Robert");
        correctTable['C' % correctTable.length] = new MapEntry<>(new Person("Chris"), "Christopher");
        correctTable['D' % correctTable.length] = new MapEntry<>(new Person("Dill"), "Dilbert");
        correctTable['E' % correctTable.length] = new MapEntry<>(new Person("Ed"), "Edward");
        correctTable['F' % correctTable.length] = new MapEntry<>(new Person("Eliza"), "Elizabeth");

        assertNull("Put does not return null if new key", testMap.put(new Person("Eliza"), "Elizabeth"));
        assertArrayEquals(correctTable, testMap.getTable());
        assertEquals("Size is incorrect", 6, testMap.size());

    }

    // put multiple collisions
    @Test(timeout = TIMEOUT)
    public void testPutMultipleCollisions() {
        populateHashMap();
        correctTable['A' % correctTable.length] = new MapEntry<>(new Person("Ash"), "Ashley");
        correctTable['B' % correctTable.length] = new MapEntry<>(new Person("Bob"), "Robert");
        correctTable['C' % correctTable.length] = new MapEntry<>(new Person("Chris"), "Christopher");
        correctTable['D' % correctTable.length] = new MapEntry<>(new Person("Dill"), "Dilbert");
        correctTable['E' % correctTable.length] = new MapEntry<>(new Person("Ed"), "Edward");
        correctTable['F' % correctTable.length] = new MapEntry<>(new Person("Elizabeth"), "Eliza");

        assertNull("Put does not return null if new key", testMap.put(new Person("Elizabeth"), "Eliza"));
        assertArrayEquals(correctTable, testMap.getTable());
        assertEquals("Size is incorrect", 6, testMap.size());

    }

    // put key after removing
    @Test(timeout = TIMEOUT)
    public void testPutAfterRemove() {
        populateHashMap2();
        correctTable['A' % correctTable.length] = new MapEntry<>(new Person("Ash"), "Ashley");
        correctTable['B' % correctTable.length] = new MapEntry<>(new Person("Bob"), "Robert");
        correctTable['C' % correctTable.length] = new MapEntry<>(new Person("Chris"), "Christopher");
        correctTable['D' % correctTable.length] = new MapEntry<>(new Person("Dill"), "Dilbert");
        correctTable['E' % correctTable.length] = new MapEntry<>(new Person("Ed"), "Edward");
        correctTable['B' % correctTable.length].setRemoved(true);
        correctTable['C' % correctTable.length].setRemoved(true);

        assertNull("Put does not return null if new key", testMap.put(new Person("Chris"), "Christopher"));
        correctTable['C' % correctTable.length] = new MapEntry<>(new Person("Chris"), "Christopher");

        assertArrayEquals(correctTable, testMap.getTable());
        assertEquals("Size is incorrect", 4, testMap.size());

    }

    @Test(timeout = TIMEOUT)
    public void testPutDuplicateKey() {
        populateHashMap();
        correctTable['A' % correctTable.length] = new MapEntry<>(new Person("Ash"), "Ashley");
        correctTable['B' % correctTable.length] = new MapEntry<>(new Person("Bob"), "Robert");
        correctTable['C' % correctTable.length] = new MapEntry<>(new Person("Chris"), "Christopher");
        correctTable['D' % correctTable.length] = new MapEntry<>(new Person("Dill"), "Dilbert");
        correctTable['E' % correctTable.length] = new MapEntry<>(new Person("Ed"), "Edward");

        assertArrayEquals(correctTable, testMap.getTable());
        assertEquals("Size is incorrect", 5, testMap.size());

        correctTable['D' % correctTable.length] = new MapEntry<>(new Person("Dill"), "Dylan");

        assertEquals("Dilbert", testMap.put((new Person("Dill")), "Dylan"));
        assertArrayEquals(correctTable, testMap.getTable());
        assertEquals("Size is incorrect", 5, testMap.size());
    }

    // put with negative hash
    @Test(timeout = TIMEOUT)
    public void testPutNegativeHash() {
        correctTableNeg[0] = new MapEntry<>(new NegPerson("Ash"), "Ashley");

        assertNull("Put does not return null if new key", testMap.put(new NegPerson("Ash"), "Ashley"));

        assertArrayEquals(correctTableNeg, testMap.getTable());
        assertEquals("Size is incorrect", 1, testMap.size());
    }

    // put resize once
    @Test(timeout = TIMEOUT)
    public void testPutResizeExact() {
        // Should check that map regrows at strictly > .67 load factor
        MapEntry<Person, String>[] characterTable = (MapEntry<Person, String>[]) new MapEntry[9];
        setTableAndSize(testMap, new MapEntry[9], 0);

        //Make sure resize doesn't happen in the first 8 puts
        assertNull("Put does not return null if new key", testMap.put(new Person("Ash"), "Ashley"));
        assertNull("Put does not return null if new key", testMap.put(new Person("Bob"), "Robert"));
        assertNull("Put does not return null if new key", testMap.put(new Person("Chris"), "Christopher"));
        assertNull("Put does not return null if new key", testMap.put(new Person("Dill"), "Dilbert"));
        assertNull("Put does not return null if new key", testMap.put(new Person("Ed"), "Edward"));
        assertNull("Put does not return null if new key", testMap.put(new Person("Fred"), "Fredrick"));

        characterTable['A' % 9] = new MapEntry<>(new Person("Ash"), "Ashley");
        characterTable['B' % 9] = new MapEntry<>(new Person("Bob"), "Robert");
        characterTable['C' % 9] = new MapEntry<>(new Person("Chris"), "Christopher");
        characterTable['D' % 9] = new MapEntry<>(new Person("Dill"), "Dilbert");
        characterTable['E' % 9] = new MapEntry<>(new Person("Ed"), "Edward");
        characterTable['F' % 9] = new MapEntry<>(new Person("Fred"), "Fredrick");

        assertArrayEquals(characterTable, testMap.getTable());
        assertEquals("Size is incorrect", 6, testMap.size());

        //Final put that should trigger regrow
        MapEntry<Person, String>[] correctTable = (MapEntry<Person, String>[]) new MapEntry[19];
        correctTable['A' % correctTable.length] = new MapEntry<>(new Person("Ash"), "Ashley");
        correctTable['B' % correctTable.length] = new MapEntry<>(new Person("Bob"), "Robert");
        correctTable['C' % correctTable.length] = new MapEntry<>(new Person("Chris"), "Christopher");
        correctTable['D' % correctTable.length] = new MapEntry<>(new Person("Dill"), "Dilbert");
        correctTable['E' % correctTable.length] = new MapEntry<>(new Person("Ed"), "Edward");
        correctTable['F' % correctTable.length] = new MapEntry<>(new Person("Fred"), "Fredrick");

        assertNull("Put does not return null if new key", testMap.put(new Person("Iv"), "Ivan"));
        correctTable['I' % correctTable.length] = new MapEntry<>(new Person("Iv"), "Ivan");

        assertArrayEquals(correctTable, testMap.getTable());
        assertEquals("Size is incorrect", 7, testMap.size());
    }

    // put and resize with defunct keys
    @Test(timeout = TIMEOUT)
    public void testPutResizeRemoved() {
        populateHashMapResize2();
        MapEntry<Person, String>[] correctTable = (MapEntry<Person, String>[]) new MapEntry[HashMapInterface.INITIAL_CAPACITY * 2 + 1];
        correctTable['A' % correctTable.length] = new MapEntry<>(new Person("Ash"), "Ashley");
        correctTable['B' % correctTable.length] = new MapEntry<>(new Person("Bob"), "Robert");
        correctTable['C' % correctTable.length] = new MapEntry<>(new Person("Chris"), "Christopher");
        correctTable['D' % correctTable.length] = new MapEntry<>(new Person("Dill"), "Dilbert");
        correctTable['E' % correctTable.length] = new MapEntry<>(new Person("Ed"), "Edward");

        correctTable['H' % correctTable.length] = new MapEntry<Person, String>(new Person("Han"), "Hannah");
        correctTable['I' % correctTable.length] = new MapEntry<Person, String>(new Person("Iv"), "Ivan");
        correctTable['J' % correctTable.length] = new MapEntry<Person, String>(new Person("Joe"), "Joseph");

        assertNull("Put does not return null if new key", testMap.put(new Person("Kate"), "Katlyn"));
        correctTable['K' % correctTable.length] = new MapEntry<Person, String>(new Person("Kate"), "Katlyn");

        assertArrayEquals(correctTable, testMap.getTable());
        assertEquals("Size is incorrect", 9, testMap.size());
    }

    // put and resize with duplicate keys
    @Test(timeout = TIMEOUT)
    public void testPutResizeWithDuplicate() {
        populateHashMapResize();
        MapEntry<Person, String>[] correctTable = (MapEntry<Person, String>[]) new MapEntry[HashMapInterface.INITIAL_CAPACITY * 2 + 1];
        correctTable['A' % correctTable.length] = new MapEntry<Person, String>(new Person("Ash"), "Ashley");
        correctTable['B' % correctTable.length] = new MapEntry<Person, String>(new Person("Bob"), "Robert");
        correctTable['C' % correctTable.length] = new MapEntry<Person, String>(new Person("Chris"), "Christopher");
        correctTable['D' % correctTable.length] = new MapEntry<Person, String>(new Person("Dill"), "Dilbert");
        correctTable['E' % correctTable.length] = new MapEntry<Person, String>(new Person("Ed"), "Edward");
        correctTable['F' % correctTable.length] = new MapEntry<Person, String>(new Person("Fred"), "Fredrick");
        correctTable['G' % correctTable.length] = new MapEntry<Person, String>(new Person("Gigi"), "Georgina");
        correctTable['H' % correctTable.length] = new MapEntry<Person, String>(new Person("Han"), "Hannah");

        assertEquals("Put does not return duplicate's value", "Hannah", testMap.put(new Person("Han"), "Hando"));
        correctTable['H' % correctTable.length] = new MapEntry<Person, String>(new Person("Han"), "Hando");

        assertArrayEquals(correctTable, testMap.getTable());
        assertEquals("Size is incorrect", 8, testMap.size());
    }

    // put and resize twice
    @Test(timeout = TIMEOUT)
    public void testPutResizeTwice() {
        populateHashMapResizeTwice();

        correctTable = (MapEntry<Person, String>[]) new MapEntry[(testMap.getTable().length) * 2 + 1];
        correctTable['A' % correctTable.length] = new MapEntry<>(new Person("Ash"), "Ashley");
        correctTable['B' % correctTable.length] = new MapEntry<>(new Person("Bob"), "Robert");
        correctTable['C' % correctTable.length] = new MapEntry<>(new Person("Chris"), "Christopher");
        correctTable['D' % correctTable.length] = new MapEntry<>(new Person("Dill"), "Dilbert");
        correctTable['E' % correctTable.length] = new MapEntry<>(new Person("Ed"), "Edward");
        correctTable['F' % correctTable.length] = new MapEntry<>(new Person("Fred"), "Fredrick");
        correctTable['G' % correctTable.length] = new MapEntry<>(new Person("Gigi"), "Georgina");
        correctTable['H' % correctTable.length] = new MapEntry<>(new Person("Han"), "Hannah");
        correctTable['I' % correctTable.length] = new MapEntry<>(new Person("Iv"), "Ivan");
        correctTable['J' % correctTable.length] = new MapEntry<>(new Person("Joe"), "Joseph");
        correctTable['K' % correctTable.length] = new MapEntry<>(new Person("Kat"), "Katherine");
        correctTable['L' % correctTable.length] = new MapEntry<>(new Person("Leo"), "Leonardo");
        correctTable['M' % correctTable.length] = new MapEntry<>(new Person("Matt"), "Matthew");
        correctTable['N' % correctTable.length] = new MapEntry<>(new Person("Nathan"), "Nathaniel");
        correctTable['O' % correctTable.length] = new MapEntry<>(new Person("Ollie"), "Oliver");
        correctTable['P' % correctTable.length] = new MapEntry<>(new Person("Pete"), "Peter");
        correctTable['Q' % correctTable.length] = new MapEntry<>(new Person("Quinn"), "Quincy");
        correctTable['R' % correctTable.length] = new MapEntry<>(new Person("Ray"), "Raymond");

        assertNull("Put does not return null if new key", testMap.put(new Person("Steph"), "Stephanie"));
        correctTable['S' % correctTable.length] = new MapEntry<>(new Person("Steph"), "Stephanie");

        assertArrayEquals(correctTable, testMap.getTable());
        assertEquals("Size is incorrect", 19, testMap.size());
    }

    // put in previously defunct place
    @Test(timeout = TIMEOUT)
    public void testPutDefunctNew() {
        populateHashMapSameHash();

        correctTable['B' % correctTable.length] = new MapEntry<>(new Person("Bill"), "William");
        correctTable['C' % correctTable.length] = new MapEntry<>(new Person("Brook"), "Brooklyn");
        correctTable['D' % correctTable.length] = new MapEntry<>(new Person("Bart"), "Bartholomew");

        assertNull("Put does not return null if new key", testMap.put(new Person("Brook"), "Brooklyn"));

        assertArrayEquals(correctTable, testMap.getTable());
        assertEquals("Size is incorrect", 4, testMap.size());
    }

    // put pass defunct and duplicate
    @Test(timeout = TIMEOUT)
    public void testPutDefunctAndDuplicate() {
        populateHashMapSameHash();
        correctTable['B' % correctTable.length] = new MapEntry<>(new Person("Bill"), "William");
        correctTable['C' % correctTable.length] = new MapEntry<>(new Person("Bob"), "Robert");
        correctTable['D' % correctTable.length] = new MapEntry<>(new Person("Bart"), "Barty");

        correctTable['C' % correctTable.length].setRemoved(true);

        assertEquals("Put does not return old value from replaced key", "Bartholomew", testMap.put(new Person("Bart"), "Barty"));

        assertArrayEquals(correctTable, testMap.getTable());
        assertEquals("Size is incorrect", 3, testMap.size());
    }

    // put doesn't break if there are all defunct
    @Test(timeout = TIMEOUT)
    public void testPutTerminateIfNoNulls() {
        populateHashMapDefunct();

        correctTable['A' % correctTable.length] = new MapEntry<>(new Person("Ash"), "Ashley");
        correctTable['B' % correctTable.length] = new MapEntry<>(new Person("Bob"), "Robert");
        correctTable['C' % correctTable.length] = new MapEntry<>(new Person("Chris"), "Christopher");
        correctTable['D' % correctTable.length] = new MapEntry<>(new Person("Dill"), "Dilbert");
        correctTable['E' % correctTable.length] = new MapEntry<>(new Person("Ed"), "Edward");
        correctTable['A' % correctTable.length].setRemoved(true);
        correctTable['B' % correctTable.length].setRemoved(true);
        correctTable['C' % correctTable.length].setRemoved(true);
        correctTable['D' % correctTable.length].setRemoved(true);
        correctTable['E' % correctTable.length].setRemoved(true);
        correctTable['F' % correctTable.length] = new MapEntry<>(new Person("Fred"), "Fredrick");
        correctTable['G' % correctTable.length] = new MapEntry<>(new Person("Gigi"), "Georgina");
        correctTable['H' % correctTable.length] = new MapEntry<>(new Person("Han"), "Hannah");
        correctTable['I' % correctTable.length] = new MapEntry<>(new Person("Iv"), "Ivan");
        correctTable['J' % correctTable.length] = new MapEntry<>(new Person("Joe"), "Joseph");
        correctTable['K' % correctTable.length] = new MapEntry<>(new Person("Kat"), "Katherine");
        correctTable['F' % correctTable.length].setRemoved(true);
        correctTable['G' % correctTable.length].setRemoved(true);
        correctTable['H' % correctTable.length].setRemoved(true);
        correctTable['I' % correctTable.length].setRemoved(true);
        correctTable['J' % correctTable.length].setRemoved(true);
        correctTable['K' % correctTable.length].setRemoved(true);

        correctTable['L' % correctTable.length] = new MapEntry<>(new Person("Leah"), "Leandra");

        assertNull("Put does not return null if new key", testMap.put(new Person("Leah"), "Leandra"));

        assertArrayEquals(correctTable, testMap.getTable());
        assertEquals("Size is incorrect", 1, testMap.size());
    }

    //get

    // get null key
    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testGetNullKey() {
        try {
            testMap.get(null);
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("Incorrect exception thrown", java.lang.IllegalArgumentException.class, e.getClass());
            assertArrayEquals(correctTable, testMap.getTable());
            throw e;
        }
    }

    // get on empty map
    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void testGetNotFoundEmpty() {
        try {
            testMap.get(new Person("Sarah"));
        } catch (NoSuchElementException e) {
            Assert.assertEquals("Incorrect exception thrown", java.util.NoSuchElementException.class, e.getClass());
            assertArrayEquals(correctTable, testMap.getTable());
            throw e;
        }
    }

    // get on removed key
    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void testGetNotFoundRemoved() {
        populateHashMap2();
        correctTable['A' % correctTable.length] = new MapEntry<Person, String>(new Person("Ash"), "Ashley");
        correctTable['B' % correctTable.length] = new MapEntry<Person, String>(new Person("Bob"), "Robert");
        correctTable['C' % correctTable.length] = new MapEntry<Person, String>(new Person("Chris"), "Christopher");
        correctTable['D' % correctTable.length] = new MapEntry<Person, String>(new Person("Dill"), "Dilbert");
        correctTable['E' % correctTable.length] = new MapEntry<Person, String>(new Person("Ed"), "Edward");

        correctTable['B' % correctTable.length].setRemoved(true);
        correctTable['C' % correctTable.length].setRemoved(true);

        try {
            testMap.get(new Person("Sarah"));
        } catch (NoSuchElementException e) {
            Assert.assertEquals("Incorrect exception thrown", java.util.NoSuchElementException.class, e.getClass());
            assertArrayEquals(correctTable, testMap.getTable());
            throw e;
        }
    }

    // get general
    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void testGetNotFoundGeneral() {
        populateHashMap();
        correctTable['A' % correctTable.length] = new MapEntry<Person, String>(new Person("Ash"), "Ashley");
        correctTable['B' % correctTable.length] = new MapEntry<Person, String>(new Person("Bob"), "Robert");
        correctTable['C' % correctTable.length] = new MapEntry<Person, String>(new Person("Chris"), "Christopher");
        correctTable['D' % correctTable.length] = new MapEntry<Person, String>(new Person("Dill"), "Dilbert");
        correctTable['E' % correctTable.length] = new MapEntry<Person, String>(new Person("Ed"), "Edward");

        try {
            testMap.get(new Person("Ted"));
        } catch (NoSuchElementException e) {
            Assert.assertEquals("Incorrect exception thrown", java.util.NoSuchElementException.class, e.getClass());
            assertArrayEquals(correctTable, testMap.getTable());
            throw e;
        }
    }

    // get found after a collision
    @Test(timeout = TIMEOUT)
    public void testGetFoundCollision() {
        populateHashMapElizabeth();
        correctTable['A' % correctTable.length] = new MapEntry<Person, String>(new Person("Ash"), "Ashley");
        correctTable['B' % correctTable.length] = new MapEntry<Person, String>(new Person("Bob"), "Robert");
        correctTable['C' % correctTable.length] = new MapEntry<Person, String>(new Person("Chris"), "Christopher");
        correctTable['D' % correctTable.length] = new MapEntry<Person, String>(new Person("Dill"), "Dilbert");
        correctTable['E' % correctTable.length] = new MapEntry<Person, String>(new Person("Ed"), "Edward");
        correctTable['F' % correctTable.length] = new MapEntry<Person, String>(new Person("Eliza"), "Elizabeth");

        assertEquals("Elizabeth", testMap.get(new Person("Eliza")));
    }

    // test get found after a defunct
    @Test(timeout = TIMEOUT)
    public void testGetFoundDefunct() {
        populateHashMapSameHashDefunct();

        correctTable['B' % correctTable.length] = new MapEntry<Person, String>(new Person("Bill"), "William");
        correctTable['C' % correctTable.length] = new MapEntry<Person, String>(new Person("Brook"), "Brooklyn");
        correctTable['D' % correctTable.length] = new MapEntry<Person, String>(new Person("Bart"), "Bartholomew");
        correctTable['C' % correctTable.length].setRemoved(true);

        assertEquals("Bartholomew", testMap.get(new Person("Bart")));
    }

    // test get terminating in a hashmap of all defunct
    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void testGetTerminatesIfNoNulls() {
        populateHashMapDefunct();

        correctTable['A' % correctTable.length] = new MapEntry<>(new Person("Ash"), "Ashley");
        correctTable['B' % correctTable.length] = new MapEntry<>(new Person("Bob"), "Robert");
        correctTable['C' % correctTable.length] = new MapEntry<>(new Person("Chris"), "Christopher");
        correctTable['D' % correctTable.length] = new MapEntry<>(new Person("Dill"), "Dilbert");
        correctTable['E' % correctTable.length] = new MapEntry<>(new Person("Ed"), "Edward");
        correctTable['A' % correctTable.length].setRemoved(true);
        correctTable['B' % correctTable.length].setRemoved(true);
        correctTable['C' % correctTable.length].setRemoved(true);
        correctTable['D' % correctTable.length].setRemoved(true);
        correctTable['E' % correctTable.length].setRemoved(true);
        correctTable['F' % correctTable.length] = new MapEntry<>(new Person("Fred"), "Fredrick");
        correctTable['G' % correctTable.length] = new MapEntry<>(new Person("Gigi"), "Georgina");
        correctTable['H' % correctTable.length] = new MapEntry<>(new Person("Han"), "Hannah");
        correctTable['I' % correctTable.length] = new MapEntry<>(new Person("Iv"), "Ivan");
        correctTable['J' % correctTable.length] = new MapEntry<>(new Person("Joe"), "Joseph");
        correctTable['K' % correctTable.length] = new MapEntry<>(new Person("Kat"), "Katherine");
        correctTable['F' % correctTable.length].setRemoved(true);
        correctTable['G' % correctTable.length].setRemoved(true);
        correctTable['H' % correctTable.length].setRemoved(true);
        correctTable['I' % correctTable.length].setRemoved(true);
        correctTable['J' % correctTable.length].setRemoved(true);
        correctTable['K' % correctTable.length].setRemoved(true);

        try {
            testMap.get(new Person("Leah"));
        } catch (NoSuchElementException e) {
            Assert.assertEquals("Incorrect exception thrown", java.util.NoSuchElementException.class, e.getClass());
            assertArrayEquals(correctTable, testMap.getTable());
            throw e;
        }
    }


    //keyset tests

    // keyset empty
    @Test(timeout = TIMEOUT)
    public void keySetEmpty() {
        Set<Person> newSet = new HashSet<>();
        assertEquals(newSet, testMap.keySet());
        assertEquals("Size is incorrect", newSet.size(), testMap.size());

    }

    // keyset general
    @Test(timeout = TIMEOUT)
    public void keySetGeneralNoRemovedItems() {
        populateHashMap();
        Set<Person> newSet = new HashSet<>();
        newSet.add(new Person("Ash"));
        newSet.add(new Person("Bob"));
        newSet.add(new Person("Chris"));
        newSet.add(new Person("Dill"));
        newSet.add(new Person("Ed"));
        assertEquals(newSet, testMap.keySet());
        assertEquals("Size is incorrect", newSet.size(), testMap.size());

    }

    // keyset removed items
    @Test(timeout = TIMEOUT)
    public void keySetGeneralRemovedItems() {
        populateHashMap2();
        Set<Person> newSet = new HashSet<>();
        newSet.add(new Person("Ash"));
        newSet.add(new Person("Dill"));
        newSet.add(new Person("Ed"));
        assertEquals(newSet, testMap.keySet());
        assertEquals("Size is incorrect", newSet.size(), testMap.size());
    }

    //supplementary private classes

    /**
     * Initializes testMap using reflexion. This method is used
     * for general cases, collisions, and duplicate cases.
     */
    private void populateHashMap() {
        testMap = new HashMap<>();
        MapEntry<Person, String>[] characterTable = (MapEntry<Person, String>[]) new MapEntry[HashMapInterface.INITIAL_CAPACITY];
        characterTable[0] = new MapEntry<>(new Person("Ash"), "Ashley");
        characterTable[1] = new MapEntry<>(new Person("Bob"), "Robert");
        characterTable[2] = new MapEntry<>(new Person("Chris"), "Christopher");
        characterTable[3] = new MapEntry<>(new Person("Dill"), "Dilbert");
        characterTable[4] = new MapEntry<>(new Person("Ed"), "Edward");
        setTableAndSize(testMap, characterTable, 5);
        assertArrayEquals(characterTable, testMap.getTable());
        assertEquals("Size is incorrect", 5, testMap.size());
    }

    /**
     * Initializes testMap using reflexion. This method is used
     * in testGetFoundCollision().
     */
    private void populateHashMapElizabeth() {
        testMap = new HashMap<>();
        MapEntry<Person, String>[] characterTable = (MapEntry<Person, String>[]) new MapEntry[HashMapInterface.INITIAL_CAPACITY];
        characterTable[0] = new MapEntry<>(new Person("Ash"), "Ashley");
        characterTable[1] = new MapEntry<>(new Person("Bob"), "Robert");
        characterTable[2] = new MapEntry<>(new Person("Chris"), "Christopher");
        characterTable[3] = new MapEntry<>(new Person("Dill"), "Dilbert");
        characterTable[4] = new MapEntry<>(new Person("Ed"), "Edward");
        characterTable[5] = new MapEntry<>(new Person("Eliza"), "Elizabeth");

        setTableAndSize(testMap, characterTable, 5);
        assertArrayEquals(characterTable, testMap.getTable());
        assertEquals("Size is incorrect", 5, testMap.size());
    }

    /**
     * Initializes testMap using reflexion. This method is used
     * in cases involving removing.
     */
    private void populateHashMap2() {
        testMap = new HashMap<>();
        MapEntry<Person, String>[] characterTable = (MapEntry<Person, String>[]) new MapEntry[HashMapInterface.INITIAL_CAPACITY];
        characterTable[0] = new MapEntry<>(new Person("Ash"), "Ashley");
        characterTable[1] = new MapEntry<>(new Person("Bob"), "Robert");
        characterTable[2] = new MapEntry<>(new Person("Chris"), "Christopher");
        characterTable[3] = new MapEntry<>(new Person("Dill"), "Dilbert");
        characterTable[4] = new MapEntry<>(new Person("Ed"), "Edward");
        characterTable[1].setRemoved(true);
        characterTable[2].setRemoved(true);
        setTableAndSize(testMap, characterTable, 3);
        assertArrayEquals(characterTable, testMap.getTable());
        assertEquals("Size is incorrect", 3, testMap.size());
    }

    /**
     * Initializes testMap using reflexion. This method is used
     * for tests involving resize without remove.
     */
    private void populateHashMapResize() {
        testMap = new HashMap<>();
        MapEntry<Person, String>[] characterTable = (MapEntry<Person, String>[]) new MapEntry[HashMapInterface.INITIAL_CAPACITY];
        characterTable[0] = new MapEntry<>(new Person("Ash"), "Ashley");
        characterTable[1] = new MapEntry<>(new Person("Bob"), "Robert");
        characterTable[2] = new MapEntry<>(new Person("Chris"), "Christopher");
        characterTable[3] = new MapEntry<>(new Person("Dill"), "Dilbert");
        characterTable[4] = new MapEntry<>(new Person("Ed"), "Edward");
        characterTable[5] = new MapEntry<>(new Person("Fred"), "Fredrick");
        characterTable[6] = new MapEntry<>(new Person("Gigi"), "Georgina");
        characterTable[7] = new MapEntry<>(new Person("Han"), "Hannah");

        setTableAndSize(testMap, characterTable, 8);
        assertArrayEquals(characterTable, testMap.getTable());
        assertEquals("Size is incorrect", 8, testMap.size());
    }

    /**
     * Initializes testMap using reflexion. This method is used
     * for tests involving resize with remove.
     */
    private void populateHashMapResize2() {
        testMap = new HashMap<>();
        MapEntry<Person, String>[] characterTable = (MapEntry<Person, String>[]) new MapEntry[HashMapInterface.INITIAL_CAPACITY];
        characterTable[0] = new MapEntry<>(new Person("Ash"), "Ashley");
        characterTable[1] = new MapEntry<>(new Person("Bob"), "Robert");
        characterTable[2] = new MapEntry<>(new Person("Chris"), "Christopher");
        characterTable[3] = new MapEntry<>(new Person("Dill"), "Dilbert");
        characterTable[4] = new MapEntry<>(new Person("Ed"), "Edward");
        characterTable[5] = new MapEntry<>(new Person("Fred"), "Fredrick");
        characterTable[6] = new MapEntry<>(new Person("Gigi"), "Georgina");

        characterTable[5].setRemoved(true);
        characterTable[6].setRemoved(true);

        characterTable[7] = new MapEntry<>(new Person("Han"), "Hannah");
        characterTable[8] = new MapEntry<>(new Person("Iv"), "Ivan");
        characterTable[9] = new MapEntry<>(new Person("Joe"), "Joseph");


        setTableAndSize(testMap, characterTable, 8);
        assertArrayEquals(characterTable, testMap.getTable());
        assertEquals("Size is incorrect", 8, testMap.size());
    }

    /**
     * Initializes testMap using reflexion. This method is used
     * for testPutResizeTwice().
     */
    private void populateHashMapResizeTwice() {
        testMap = new HashMap<>();
        MapEntry<Person, String>[] characterTable = (MapEntry<Person, String>[]) new MapEntry[HashMapInterface.INITIAL_CAPACITY * 2 + 1];
        characterTable[0] = new MapEntry<>(new Person("Ash"), "Ashley");
        characterTable[1] = new MapEntry<>(new Person("Bob"), "Robert");
        characterTable[2] = new MapEntry<>(new Person("Chris"), "Christopher");
        characterTable[3] = new MapEntry<>(new Person("Dill"), "Dilbert");
        characterTable[4] = new MapEntry<>(new Person("Ed"), "Edward");
        characterTable[5] = new MapEntry<>(new Person("Fred"), "Fredrick");
        characterTable[6] = new MapEntry<>(new Person("Gigi"), "Georgina");

        characterTable[7] = new MapEntry<>(new Person("Han"), "Hannah");
        characterTable[8] = new MapEntry<>(new Person("Iv"), "Ivan");
        characterTable[9] = new MapEntry<>(new Person("Joe"), "Joseph");
        characterTable[10] = new MapEntry<>(new Person("Kat"), "Katherine");
        characterTable[11] = new MapEntry<>(new Person("Leo"), "Leonardo");
        characterTable[12] = new MapEntry<>(new Person("Matt"), "Matthew");
        characterTable[13] = new MapEntry<>(new Person("Nathan"), "Nathaniel");
        characterTable[14] = new MapEntry<>(new Person("Ollie"), "Oliver");
        characterTable[15] = new MapEntry<>(new Person("Pete"), "Peter");
        characterTable[16] = new MapEntry<>(new Person("Quinn"), "Quincy");
        characterTable[17] = new MapEntry<>(new Person("Ray"), "Raymond");

        setTableAndSize(testMap, characterTable, 18);
        assertArrayEquals(characterTable, testMap.getTable());
        assertEquals("Size is incorrect", 18, testMap.size());
    }

    /**
     * Initializes testMap using reflexion. This method is used
     * for testGetFoundDefunct().
     */
    private void populateHashMapSameHashDefunct() {
        testMap = new HashMap<>();
        MapEntry<Person, String>[] characterTable = (MapEntry<Person, String>[]) new MapEntry[HashMapInterface.INITIAL_CAPACITY];
        characterTable[1] = new MapEntry<>(new Person("Bill"), "William");
        characterTable[2] = new MapEntry<>(new Person("Bob"), "Robert");
        characterTable[3] = new MapEntry<>(new Person("Bart"), "Bartholomew");

        characterTable[2].setRemoved(true);
        setTableAndSize(testMap, characterTable, 2);
        assertArrayEquals(characterTable, testMap.getTable());
        assertEquals("Size is incorrect", 2, testMap.size());
    }

    /**
     * Initializes testMap using reflexion. This method is used
     * for tests involving defunct and duplicate keys.
     */
    private void populateHashMapSameHash() {
        testMap = new HashMap<>();
        MapEntry<Person, String>[] characterTable = (MapEntry<Person, String>[]) new MapEntry[HashMapInterface.INITIAL_CAPACITY];
        characterTable[1] = new MapEntry<>(new Person("Bill"), "William");
        characterTable[2] = new MapEntry<>(new Person("Bob"), "Robert");
        characterTable[3] = new MapEntry<>(new Person("Bart"), "Bartholomew");

        characterTable[2].setRemoved(true);

        correctTable[2] = new MapEntry<>(new Person("Brook"), "Brooklyn");
        setTableAndSize(testMap, characterTable, 3);
        assertArrayEquals(characterTable, testMap.getTable());
        assertEquals("Size is incorrect", 3, testMap.size());
    }

    /**
     * Initializes testMap using reflexion. This method is used
     * for tests involving hashmaps filled with defunct keys.
     */
    private void populateHashMapDefunct() {
        testMap = new HashMap<>();
        MapEntry<Person, String>[] characterTable = (MapEntry<Person, String>[]) new MapEntry[HashMapInterface.INITIAL_CAPACITY];
        characterTable[0] = new MapEntry<>(new Person("Ash"), "Ashley");
        characterTable[1] = new MapEntry<>(new Person("Bob"), "Robert");
        characterTable[2] = new MapEntry<>(new Person("Chris"), "Christopher");
        characterTable[3] = new MapEntry<>(new Person("Dill"), "Dilbert");
        characterTable[4] = new MapEntry<>(new Person("Ed"), "Edward");
        characterTable[5] = new MapEntry<>(new Person("Fred"), "Fredrick");
        characterTable[6] = new MapEntry<>(new Person("Gigi"), "Georgina");
        characterTable[7] = new MapEntry<>(new Person("Han"), "Hannah");
        characterTable[8] = new MapEntry<>(new Person("Iv"), "Ivan");
        characterTable[9] = new MapEntry<>(new Person("Joe"), "Joseph");
        characterTable[10] = new MapEntry<>(new Person("Kat"), "Katherine");

        characterTable[0].setRemoved(true);
        characterTable[1].setRemoved(true);
        characterTable[2].setRemoved(true);
        characterTable[3].setRemoved(true);
        characterTable[4].setRemoved(true);
        characterTable[5].setRemoved(true);
        characterTable[6].setRemoved(true);
        characterTable[7].setRemoved(true);
        characterTable[8].setRemoved(true);
        characterTable[9].setRemoved(true);
        characterTable[10].setRemoved(true);
        setTableAndSize(testMap, characterTable, 0);
        assertArrayEquals(characterTable, testMap.getTable());
        assertEquals("Size is incorrect", 0, testMap.size());
    }

    /**
     * Reflection method to modify backing array and size
     *
     * @param <K>      key generic type
     * @param <V>      value generic type
     * @param oldMap   The original HashMap
     * @param newTable The table to be substituted in
     * @param newSize  The number of elements in the new table
     */
    private <K, V> void setTableAndSize(HashMap<K, V> oldMap, MapEntry<K, V>[] newTable, int newSize) {
        try {
            Field table = HashMap.class.getDeclaredField("table");
            table.setAccessible(true);
            table.set(oldMap, newTable);
            Field size = HashMap.class.getDeclaredField("size");
            size.setAccessible(true);
            size.setInt(oldMap, newSize);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private static class Person {
        private String name;

        /**
         * Wrapper for String that uses first letter as the hash code.
         *
         * @param name the core String
         */
        public Person(String name) {
            this.name = name;
        }

        /**
         * Initializes a person
         */
        private Person() {
        }

        @Override
        public int hashCode() {
            return name.charAt(0);
        }

        @Override
        public boolean equals(Object other) {
            if (other == null || !(other instanceof Person)) {
                return false;
            }
            return name.equals(((Person) other).name);
        }

        @Override
        public String toString() {
            return name + " - hash: " + hashCode();
        }
    }

    private static class NegPerson extends Person {
        private String name;

        /**
         * Wrapper for string that uses the negative ascii
         * of the first character as the hashcode.
         *
         * @param name the core String
         */
        public NegPerson(String name) {
            this.name = name;
        }

        @Override
        public int hashCode() {
            return -name.charAt(0);
        }

        @Override
        public boolean equals(Object other) {
            if (other == null || !(other instanceof NegPerson)) {
                return false;
            }
            return name.equals(((NegPerson) other).name);
        }

        @Override
        public String toString() {
            return name + " - hash: " + hashCode();
        }
    }

    /**
     * To ensure that the students use equals() instead of ==.
     */
    private static class MyInteger {
        private Integer integer;

        /**
         * Creates a MyInteger object wrapper for Integers.
         *
         * @param integer The integer being wrapped.
         */
        public MyInteger(Integer integer) {
            this.integer = integer;
        }

        @Override
        public int hashCode() {
            return integer;
        }

        @Override
        public boolean equals(Object other) {
            if (!(other instanceof MyInteger)) {
                return false;
            }
            MyInteger otherInteger = (MyInteger) other;
            return this.integer.equals(otherInteger.integer);
        }
    }
}
