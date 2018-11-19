import java.util.NoSuchElementException;

/**
 * Implementation of an array-backed stack.
 */
public class ArrayStack<T> implements StackInterface<T> {

    private T[] backingArray;
    private int size;

    /**
     * Constructs a new ArrayStack.
     */
    public ArrayStack() {
        backingArray = (T[]) new Object[INITIAL_CAPACITY];
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Pop from the stack.
     *
     * Do not shrink the backing array.
     *
     * @see StackInterface#pop()
     */
    @Override
    public T pop() {
        if (isEmpty()) {
            throw new NoSuchElementException("Cannot pop from an empty stack.");
        }

        T data = backingArray[--size];
        backingArray[size] = null;

        return data;
    }

    /**
     * Push the given data onto the stack.
     *
     * If sufficient space is not available in the backing array, you should
     * regrow it to (double the current length) + 1; in essence, 2n + 1, where n
     * is the current capacity.
     *
     * @see StackInterface#push(T)
     */
    @Override
    public void push(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot insert null data into stack.");
        }

        regrowBackingArray();

        backingArray[size++] = data;
    }

    @Override
    public int size() {
        return size;
    }

    /**
     * Returns the backing array of this stack.
     * Used for testing purposes.
     *
     * @return the backing array
     */
    public Object[] getBackingArray() {
        return backingArray;
    }

    /**
     * Checks backing array and resizes if necessary
     */
    private void regrowBackingArray() {
        if (size >= backingArray.length) {
            T[] temp = (T[]) new Object[backingArray.length * 2 + 1];

            for (int i = 0; i < backingArray.length; i++) {
                temp[i] = backingArray[i];
            }

            backingArray = temp;
        }
    }
}
