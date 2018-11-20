import org.junit.Before;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertNull;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class StacksQueuesImplementationTests {

    private static final int TIMEOUT = 200;
    private static final int INITIAL_CAPACITY = StackInterface.INITIAL_CAPACITY;

    private ArrayQueue<String> arrayQueue;
    private LinkedQueue<String> linkedQueue;
    private LinkedStack<String> actualLinkedStack;
    private ArrayStack<String> actualArrayStack;

    private final String a = new String("a");
    private final String b = new String("b");
    private final String c = new String("c");
    private final String d = new String("d");
    private final String e = new String("e");
    private final String f = new String("f");
    private final String g = new String("g");
    private final String h = new String("h");
    private final String i = new String("i");

    @Before
    public void setup() {
        arrayQueue = new ArrayQueue<>();
        linkedQueue = new LinkedQueue<>();
        actualLinkedStack = new LinkedStack<>();
        actualArrayStack = new ArrayStack<>();
    }

    /**
     * Fills the array-backed queue with the given String array.
     *
     * @param queue        The array-backed queue.
     * @param backingArray The backing array.
     * @param size         The size.
     * @param front        The front index.
     * @param back         The back index.
     */
    private void setArrayQueue(ArrayQueue<String> queue, String[] backingArray, int size, int front, int back) {
        Arrays.stream(backingArray, front, back).forEach(queue::enqueue);
    }

    /**
     * Set the linked list queue with parameters: head, tail and size.
     *
     * @param queue The linked list backed queue.
     * @param head  The head node.
     * @param tail  The tail node.
     * @param size  The size.
     */
    private void setLinkedQueue(LinkedQueue<String> queue, LinkedNode<String> head, LinkedNode<String> tail, int size) {
        try {
            Field field = LinkedQueue.class.getDeclaredField("head");
            field.setAccessible(true);
            field.set(queue, head);

            field = LinkedQueue.class.getDeclaredField("tail");
            field.setAccessible(true);
            field.set(queue, tail);

            field = LinkedQueue.class.getDeclaredField("size");
            field.setAccessible(true);
            field.set(queue, size);

        } catch (IllegalAccessException | NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Set the linked list stack with parameters: head and size.
     *
     * @param stack the linked list backed stack
     * @param head  the head node
     * @param size  the size
     */
    private void setLinkedStack(LinkedStack<String> stack, LinkedNode<String> head, int size) {

        try {
            Field field = LinkedStack.class.getDeclaredField("head");
            field.setAccessible(true);
            try {
                field.set(stack, head);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        try {
            Field field = LinkedStack.class.getDeclaredField("size");
            field.setAccessible(true);
            try {
                field.set(stack, size);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    /**
     * Set the array stack with parameters: backingArray and size.
     *
     * @param stack        the array backed stack
     * @param backingArray the backing array
     * @param size         the size
     */
    private void setArrayStack(ArrayStack<String> stack, String[] backingArray, int size) {

        try {
            Field field = ArrayStack.class.getDeclaredField("backingArray");
            field.setAccessible(true);
            try {
                field.set(stack, backingArray);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        try {
            Field field = ArrayStack.class.getDeclaredField("size");
            field.setAccessible(true);
            try {
                field.set(stack, size);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    /**
     * Asserts whether the array queue corresponds to arguments passed in.
     *
     * @param queue         The queue to check.
     * @param expectedArray The array to compare.
     * @param size          The size to compare.
     */
    private void assertArrayQueueEqual(ArrayQueue<String> queue, Object[] expectedArray, int size) {
        assertEquals("Incorrect size", size, queue.size());
        Object[] actualArray = queue.getBackingArray();
        assertEquals("Incorrect array length", expectedArray.length, actualArray.length);
        assertArrayEquals(expectedArray, actualArray);
    }

    /**
     * Asserts whether the linked queue corresponds to arguments passed in.
     *
     * @param queue The queue to check.
     * @param size  The size to compare.
     * @param data  The data to compare.
     */
    private void assertLinkedQueueEqual(LinkedQueue<String> queue, int size, String... data) {
        assertEquals("Incorrect size", size, queue.size());
        LinkedNode<String> curr = queue.getHead();
        for (int i = 0; i < data.length - 1; ++i) {
            assertEquals("Incorrect data in a node at index " + i, data[i], curr.getData());
            curr = curr.getNext();
        }
        if (size == 0) {
            assertNull("Empty linked queue should have null head", queue.getHead());
            assertNull("Empty linked queue should have null tail", queue.getTail());
        } else {
            assertEquals("Incorrect data in a node at index " + i, data[data.length - 1], curr.getData());
            assertEquals("Tail points to incorrect node", data[data.length - 1], queue.getTail().getData());
            assertNull("Tail is not the last node in the list", curr.getNext());
        }
    }

    /**
     * Compares the properties and backing data structure of
     * {@link #actualLinkedStack} to the contents of {@code expectedValues}
     *
     * @param expectedValues The expected contents of the linked list backing
     *                       {@link #actualLinkedStack}. This value is compared
     *                       against the backing data structure such that lower
     *                       indices are towards the bottom of the stack (the
     *                       end of the backing linked list), and higher indices
     *                       are towards the top of the stack (the front of the
     *                       backing linked list)
     */
    private void assertBackingListEquals(String... expectedValues) {

        assertEquals("Stack returned incorrect value for size", expectedValues.length, actualLinkedStack.size());

        LinkedNode<String> curr = actualLinkedStack.getHead();
        for (int i = expectedValues.length - 1; i >= 0; i--) {
            Assert.assertNotNull("Stack is missing a node", curr);

            Assert.assertEquals("Stack has incorrect data at index " + i, expectedValues[i], curr.getData());
            curr = curr.getNext();
        }

        Assert.assertNull("Stack has too many nodes", curr);
    }

    /**
     * Compares the properties and contents of {@link #actualArrayStack} to the
     * contents of {@code expectedItems}
     *
     * @param expectedSize  The expected value of
     *                      {@link ArrayStack#getBackingArray()
     *                      ArrayStack.backingArray's} length
     * @param expectedItems The expected values in the stack. This value is
     *                      compared directly to the contents of
     *                      {@link ArrayStack#getBackingArray()}, so an item in
     *                      a lower index will be at the bottom of the stack,
     *                      and a higher index will be at the top of the stack
     */
    private void assertBackingArrayEquals(int expectedSize, String... expectedItems) {

        Object[] backingArray = actualArrayStack.getBackingArray();

        assertEquals("Stack returned incorrect value for size", expectedItems.length, actualArrayStack.size());

        assertEquals("Incorrect backing array length", expectedSize, backingArray.length);

        for (int i = 0; i < expectedItems.length; i++) {
            assertEquals("Incorrect item at index " + i, expectedItems[i], backingArray[i]);
        }

        for (int i = expectedItems.length; i < expectedSize; i++) {
            assertNull("Did not null out element at index " + i, backingArray[i]);
        }
    }

    /**
     * Compares the properties and contents of {@link #actualArrayStack} to the
     * contents of {@code expectedItems}
     *
     * @param expectedSize  The expected value of
     *                      {@link ArrayStack#getBackingArray()
     *                      ArrayStack.backingArray's} length
     * @param expectedItems The expected values in the stack. This value is
     *                      compared directly to the contents of
     *                      {@link ArrayStack#getBackingArray()}, so an item in
     *                      a lower index will be at the bottom of the stack,
     *                      and a higher index will be at the top of the stack
     * @see #assertBackingArrayEquals(int, String...) for assertion
     * implementation
     */
    private void assertBackingArrayEquals(int expectedSize, List<String> expectedItems) {

        String[] expectedArray = new String[expectedItems.size()];
        expectedItems.toArray(expectedArray);

        assertBackingArrayEquals(expectedSize, expectedArray);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void a00ArrayQueueEnqueueThrowsException() {
        try {
            arrayQueue.enqueue(null);
        } catch (IllegalArgumentException e) {
            assertEquals(IllegalArgumentException.class, e.getClass());
            throw e;
        }
    }

    @Test(timeout = TIMEOUT)
    public void a01ArrayQueueEnqueueOneElement() {
        assertEquals(0, arrayQueue.size());
        arrayQueue.enqueue(a);
        Object[] expectedArray = new Object[QueueInterface.INITIAL_CAPACITY];
        expectedArray[0] = a;
        assertArrayQueueEqual(arrayQueue, expectedArray, 1);
    }

    @Test(timeout = TIMEOUT)
    public void a02ArrayQueueEnqueueNoResize() {
        assertEquals(0, arrayQueue.size());
        arrayQueue.enqueue(a);
        arrayQueue.enqueue(b);
        arrayQueue.enqueue(c);
        arrayQueue.enqueue(d);
        arrayQueue.enqueue(e);
        arrayQueue.enqueue(f);
        arrayQueue.enqueue(g);
        arrayQueue.enqueue(h);
        arrayQueue.enqueue(i);
        Object[] expectedArray = new String[QueueInterface.INITIAL_CAPACITY];
        expectedArray[0] = a;
        expectedArray[1] = b;
        expectedArray[2] = c;
        expectedArray[3] = d;
        expectedArray[4] = e;
        expectedArray[5] = f;
        expectedArray[6] = g;
        expectedArray[7] = h;
        expectedArray[8] = i;
        assertArrayQueueEqual(arrayQueue, expectedArray, 9);
    }

    @Test(timeout = TIMEOUT)
    public void a03ArrayQueueEnqueueOneResize() {
        assertEquals(0, arrayQueue.size());
        Object[] expectedArray = new String[QueueInterface.INITIAL_CAPACITY * 2 + 1];
        for (int i = 0; i < 14; i += 2) {
            arrayQueue.enqueue(a);
            arrayQueue.enqueue(b);
            expectedArray[i] = a;
            expectedArray[i + 1] = b;

        }
        assertArrayQueueEqual(arrayQueue, expectedArray, 14);
    }

    @Test(timeout = TIMEOUT)
    public void a04ArrayQueueEnqueueMultipleResize() {
        assertEquals(0, arrayQueue.size());
        Object[] expectedArray = new String[(QueueInterface.INITIAL_CAPACITY * 2 + 1) * 2 + 1];
        for (int i = 0; i < 30; i += 2) {
            arrayQueue.enqueue(a);
            arrayQueue.enqueue(b);
            expectedArray[i] = a;
            expectedArray[i + 1] = b;

        }
        assertArrayQueueEqual(arrayQueue, expectedArray, 30);
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void b00ArrayQueueDequeueThrowsException() {
        try {
            arrayQueue.dequeue();
        } catch (NoSuchElementException e) {
            assertEquals(NoSuchElementException.class, e.getClass());
            throw e;
        }
    }

    @Test(timeout = TIMEOUT)
    public void b01ArrayQueueDequeueOneElement() {
        String[] backingArray = new String[QueueInterface.INITIAL_CAPACITY];
        backingArray[0] = a;
        setArrayQueue(arrayQueue, backingArray, 1, 0, 1);
        assertSame("Incorrect dequeue returned value", a, arrayQueue.dequeue());
        Object[] expectedArray = new Object[QueueInterface.INITIAL_CAPACITY];
        assertArrayQueueEqual(arrayQueue, expectedArray, 0);
    }

    @Test(timeout = TIMEOUT)
    public void b02ArrayQueueDequeueMultipleElements() {
        String[] backingArray = new String[QueueInterface.INITIAL_CAPACITY];
        backingArray[0] = a;
        backingArray[1] = b;
        backingArray[2] = c;
        backingArray[3] = d;
        backingArray[4] = e;
        backingArray[5] = f;
        backingArray[6] = g;
        backingArray[7] = h;
        backingArray[8] = i;
        setArrayQueue(arrayQueue, backingArray, 9, 0, 9);
        assertSame("Incorrect dequeue returned value", a, arrayQueue.dequeue());
        assertSame("Incorrect dequeue returned value", b, arrayQueue.dequeue());
        assertSame("Incorrect dequeue returned value", c, arrayQueue.dequeue());
        assertSame("Incorrect dequeue returned value", d, arrayQueue.dequeue());
        assertSame("Incorrect dequeue returned value", e, arrayQueue.dequeue());
        assertSame("Incorrect dequeue returned value", f, arrayQueue.dequeue());
        Object[] expectedArray = new Object[QueueInterface.INITIAL_CAPACITY];
        expectedArray[6] = g;
        expectedArray[7] = h;
        expectedArray[8] = i;
        assertArrayQueueEqual(arrayQueue, expectedArray, 3);
    }

    @Test(timeout = TIMEOUT)
    public void b03ArrayQueueDequeueEnqueueWraparound() {
        arrayQueue.enqueue(a);
        assertSame("Incorrect dequeue returned value", a, arrayQueue.dequeue());
        arrayQueue.enqueue(a);
        for (int i = 0; i < 30; i += 3) {
            arrayQueue.enqueue(b);
            arrayQueue.enqueue(c);
            arrayQueue.enqueue(a);
            assertSame("Incorrect dequeue returned value", a, arrayQueue.dequeue());
            assertSame("Incorrect dequeue returned value", b, arrayQueue.dequeue());
            assertSame("Incorrect dequeue returned value", c, arrayQueue.dequeue());
        }
        Object[] expectedArray = new Object[QueueInterface.INITIAL_CAPACITY];
        expectedArray[9] = a;
        assertArrayQueueEqual(arrayQueue, expectedArray, 1);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void c00LinkedQueueEnqueueThrowsException() {
        try {
            linkedQueue.enqueue(null);
        } catch (IllegalArgumentException e) {
            assertEquals(IllegalArgumentException.class, e.getClass());
            throw e;
        }
    }

    @Test(timeout = TIMEOUT)
    public void c01LinkedQueueEnqueueOneElement() {
        assertEquals(0, linkedQueue.size());
        linkedQueue.enqueue(a);
        assertLinkedQueueEqual(linkedQueue, 1, a);
    }

    @Test(timeout = TIMEOUT)
    public void c02LinkedQueueEnqueueMultipleElements() {
        assertEquals(0, linkedQueue.size());
        linkedQueue.enqueue(a);
        linkedQueue.enqueue(b);
        linkedQueue.enqueue(c);
        linkedQueue.enqueue(d);
        linkedQueue.enqueue(e);
        linkedQueue.enqueue(f);
        linkedQueue.enqueue(g);
        linkedQueue.enqueue(h);
        linkedQueue.enqueue(i);
        assertLinkedQueueEqual(linkedQueue, 9, a, b, c, d, e, f, g, h, i);
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void d00LinkedQueueDequeueThrowsException() {
        try {
            linkedQueue.dequeue();
        } catch (NoSuchElementException e) {
            assertEquals(NoSuchElementException.class, e.getClass());
            throw e;
        }
    }

    @Test(timeout = TIMEOUT)
    public void d01LinkedQueueDequeueOneElement() {
        LinkedNode<String> head = new LinkedNode<String>(a);
        setLinkedQueue(linkedQueue, head, head, 1);
        assertSame("Incorrect dequeue returned value", a, linkedQueue.dequeue());
        assertLinkedQueueEqual(linkedQueue, 0);
    }

    @Test(timeout = TIMEOUT)
    public void d02LinkedQueueDequeueMultipleElement() {
        LinkedNode<String> tail = new LinkedNode<String>(i);
        LinkedNode<String> head = new LinkedNode<String>(a, new LinkedNode<String>(b,
                new LinkedNode<String>(c, new LinkedNode<String>(d, new LinkedNode<String>(e,
                        new LinkedNode<String>(f, new LinkedNode<String>(g, new LinkedNode<String>(h, tail))))))));

        setLinkedQueue(linkedQueue, head, tail, 9);
        assertSame("Incorrect dequeue returned value", a, linkedQueue.dequeue());
        assertSame("Incorrect dequeue returned value", b, linkedQueue.dequeue());
        assertSame("Incorrect dequeue returned value", c, linkedQueue.dequeue());
        assertSame("Incorrect dequeue returned value", d, linkedQueue.dequeue());
        assertSame("Incorrect dequeue returned value", e, linkedQueue.dequeue());
        assertSame("Incorrect dequeue returned value", f, linkedQueue.dequeue());
        assertLinkedQueueEqual(linkedQueue, 3, g, h, i);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testLinkedPushThrowsForNullData() {
        try {
            actualLinkedStack.push(null);
        } catch (IllegalArgumentException e) {
            assertEquals(IllegalArgumentException.class, e.getClass());
            throw e;
        }
    }

    @Test(timeout = TIMEOUT)
    public void testLinkedPushOnce() {
        String expected = "2muchSwag";

        actualLinkedStack.push(expected);
        assertBackingListEquals(expected);

    }

    @Test(timeout = TIMEOUT)
    public void testLinkedPushManyTimes() {
        String[] values = {"I", "don't", "want", "another", "pretty", "face"};

        for (int i = 0; i < values.length; i++) {
            String[] expected = Arrays.copyOfRange(values, 0, i + 1);

            actualLinkedStack.push(values[i]);
            assertBackingListEquals(expected);
        }

    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void testLinkedPopThrowsWhenEmpty() {
        try {
            actualLinkedStack.pop();
        } catch (NoSuchElementException e) {
            assertEquals(NoSuchElementException.class, e.getClass());
            throw e;
        }
    }

    @Test(timeout = TIMEOUT)
    public void testLinkedPopOnce() {
        String expected = "It was technically $150, not $300";

        LinkedNode<String> head = new LinkedNode<>(expected, null);
        setLinkedStack(actualLinkedStack, head, 1);

        assertBackingListEquals(expected);
        assertEquals("pop did not return correct value", expected, actualLinkedStack.pop());

        assertBackingListEquals();

    }

    @Test(timeout = TIMEOUT)
    public void testLinkedPopManyTimes() {
        String[] values = {"C's", "the", "reason", "for", "the", "teardrops", "on", "my", "spacebar"};

        LinkedNode<String> head = null;

        for (int i = 0; i < values.length; i++) {
            head = new LinkedNode<>(values[i], head);
        }

        setLinkedStack(actualLinkedStack, head, values.length);
        assertBackingListEquals(values);

        for (int i = values.length - 1; i >= 0; i--) {
            assertEquals("pop did not return correct value", values[i], actualLinkedStack.pop());

            String[] expected = Arrays.copyOfRange(values, 0, i);
            assertBackingListEquals(expected);
        }

    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testPushToArrayStackThrowsForNullData() {
        try {
            actualArrayStack.push(null);
        } catch (IllegalArgumentException e) {
            assertEquals(IllegalArgumentException.class, e.getClass());
            throw e;
        }
    }

    @Test(timeout = TIMEOUT)
    public void testPushToEmptyArrayStack() {
        actualArrayStack.push("Yo");
        assertBackingArrayEquals(INITIAL_CAPACITY, "Yo");

    }

    @Test(timeout = TIMEOUT)
    public void testPushToEmptyArrayStackWithoutRegrowing() {
        actualArrayStack.push("It");
        actualArrayStack.push("was");
        actualArrayStack.push("only");
        actualArrayStack.push("$300");

        assertBackingArrayEquals(INITIAL_CAPACITY, "It", "was", "only", "$300");

    }


    @Test(timeout = TIMEOUT)
    public void testPushToArrayStackRegrowsOnce() {
        String[] expected = {"Maybe", "it", "was", "$300,", "but", "money's", "the", "root", "of", "all", "evil", "anyway"};

        for (int i = 0; i < expected.length; i++) {
            actualArrayStack.push(expected[i]);

            if (i < INITIAL_CAPACITY) {
                assertEquals("Backing array regrew too early", INITIAL_CAPACITY, actualArrayStack.getBackingArray().length);
            }
        }

        int expectedCapacity = 2 * INITIAL_CAPACITY + 1;
        assertBackingArrayEquals(expectedCapacity, expected);

    }

    @Test(timeout = TIMEOUT)
    public void testPushRegrowsManyTimes() {
        List<String> expected = new ArrayList<>();
        int previousArrayLength = INITIAL_CAPACITY;

        for (int i = 0; i < 50; i++) {
            String value = String.valueOf(i);

            expected.add(value);
            actualArrayStack.push(value);

            if (previousArrayLength == i) {
                int newArrayLength = 2 * previousArrayLength + 1;

                assertBackingArrayEquals(newArrayLength, expected);
                previousArrayLength = newArrayLength;
            }
        }

    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void testArrayPopThrowsWhenEmpty() {
        try {
            actualArrayStack.pop();
        } catch (NoSuchElementException e) {
            // Don't allow descendants of NoSuchElementException
            assertEquals(NoSuchElementException.class, e.getClass());
            throw e;
        }
    }

    @Test(timeout = TIMEOUT)
    public void testArrayPopOnce() {
        String[] expected = {"SWAG"};

        String[] backingArray = new String[INITIAL_CAPACITY];
        backingArray[0] = "SWAG";

        setArrayStack(actualArrayStack, backingArray, 1);

        assertBackingArrayEquals(INITIAL_CAPACITY, expected);

        assertEquals("Pop did not return correct value", expected[0], actualArrayStack.pop());
        assertBackingArrayEquals(INITIAL_CAPACITY);

    }

    @Test(timeout = TIMEOUT)
    public void testArrayPopManyTimes() {
        String[] expected = {"Teardrops", "on", "my", "Guitar"};

        String[] backingArray = new String[INITIAL_CAPACITY];
        for (int i = 0; i < expected.length; i++) {
            backingArray[i] = expected[i];
        }

        setArrayStack(actualArrayStack, backingArray, 4);

        assertBackingArrayEquals(INITIAL_CAPACITY, expected);

        for (int i = expected.length - 1; i >= 0; i--) {
            assertEquals("Pop did not return correct value", expected[i], actualArrayStack.pop());

            assertBackingArrayEquals(INITIAL_CAPACITY, Arrays.copyOf(expected, i));
        }

    }
}
