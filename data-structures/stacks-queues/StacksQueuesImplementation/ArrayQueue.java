import java.util.NoSuchElementException;

/**
 * Implementation of an array-backed queue.
 */
public class ArrayQueue<T> implements QueueInterface<T> {

    private T[] backingArray;
    private int front;
    private int back;
    private int size;

    /**
     * Constructs a new ArrayQueue.
     */
    public ArrayQueue() {
        backingArray = (T[]) new Object[INITIAL_CAPACITY];
    }

    /**
     * Dequeue from the front of the queue.
     *
     * Do not shrink the backing array.
     * If the queue becomes empty as a result of this call, you must not
     * explicitly reset front or back to 0.
     *
     * @see QueueInterface#dequeue()
     */
    @Override
    public T dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("Cannot dequeue from an empty queue");
        }

        T data = backingArray[front];
        backingArray[front++] = null;
        front %= backingArray.length;

        size--;
        return data;
    }

    /**
     * Add the given data to the queue.
     *
     * If sufficient space is not available in the backing array, you should
     * regrow it to (double the current length) + 1; in essence, 2n + 1, where n
     * is the current capacity. If a regrow is necessary, you should copy
     * elements to the front of the new array and reset front to 0.
     *
     * @see QueueInterface#enqueue(T)
     */
    @Override
    public void enqueue(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot insert null into a queue");
        }

        regrowBackingArray();

        backingArray[back++] = data;
        back %= backingArray.length;

        size++;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    /**
     * Returns the backing array of this queue.
     * Used for testing purposes.
     *
     * @return the backing array
     */
    public Object[] getBackingArray() {
        return backingArray;
    }

    /**
     * Checks backing array and resizes if necessary. Rewinds the data
     * to the front of the array.
     */
    private void regrowBackingArray() {
        if (size >= backingArray.length) {
            T[] temp = (T[]) new Object[backingArray.length * 2 + 1];

            for (int i = 0, j = front; i < backingArray.length; i++, j = (j + 1) % backingArray.length) {
                temp[i] = backingArray[j];
            }

            front = 0;
            back = size;
            backingArray = temp;
        }
    }
}