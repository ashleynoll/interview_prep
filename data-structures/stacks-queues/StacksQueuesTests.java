import org.junit.Before;
import org.junit.Test;

import java.util.Stack;

import static org.junit.Assert.*;

/**
 * Testing for Stacks and Queues Problems
 */
public class StacksQueuesTests {
    private static final long TIMEOUT = 200;
    private StacksQueuesProblems problems;

    @Before
    public void setUp() {
        problems = new StacksQueuesProblems();
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //           SetOfStacks
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    @Test(timeout = TIMEOUT)
    public void testSetOfStacks() {
        StacksQueuesProblems.SetOfStacks<Integer> stacks = new StacksQueuesProblems.SetOfStacks<>();

        int[] arr = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };

        for (int i : arr) {
            stacks.push(i);
        }

        assertEquals("First pop should be 10", 10, (int) stacks.pop());
        assertEquals("Popping from index 0 should return 4", 4, (int) stacks.popAt(0));
        assertEquals("Popping from end should return 9 ", 9, (int) stacks.pop());
    }

    @Test(timeout = TIMEOUT)
    public void testMyQueue() {
        StacksQueuesProblems.MyQueue<Integer> myQueue = new StacksQueuesProblems.MyQueue<>();

        int[] arr = { 0, 1, 2, 3, 4 };

        for (int i : arr) {
            myQueue.enqueue(i);
        }

        assertEquals("First dequeue should be 0", 0, (int) myQueue.dequeue());

        arr = new int[]{ 5, 6, 7, 8 };

        for (int i : arr) {
            myQueue.enqueue(i);
        }

        assertEquals("Second dequeue should be 1", 1, (int) myQueue.dequeue());

        for (int i = 2; i < 9; i++) {
            assertEquals("Dequeue should be " + i, i, (int) myQueue.dequeue());
        }
    }

    @Test(timeout = TIMEOUT)
    public void testSortStack() {
        Stack<Integer> stack = new Stack<>();
        int[] arr = { 4, 3, 6, 1, 5, 0, 2 };

        for (int i : arr) {
            stack.push(i);
        }

        stack = problems.sortStack(stack);

        for (int i = 0; i < 7; i++) {
            assertEquals("Element should be " + i, i, (int) stack.pop());
        }
    }

}
