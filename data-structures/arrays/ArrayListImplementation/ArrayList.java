/**
 * Implementation of an ArrayList.
 */
public class ArrayList<T> implements ArrayListInterface<T> {

    private T[] backingArray;
    private int size;

    /**
     * Constructs a new ArrayList.
     */
    public ArrayList() {
        backingArray = (T[]) new Object[INITIAL_CAPACITY];
    }

    @Override
    public void addAtIndex(int index, T data) {
        if (index > size) {
            throw new IndexOutOfBoundsException("Cannot add to index outside of range");
        } else if (data == null) {
            throw new IllegalArgumentException("Cannot add null data to ArrayList");
        }
        size++;
        regrowArray();

        for (int i = size - 1; i > index; i--) {
            backingArray[i] = backingArray[i - 1];
        }

        backingArray[index] = data;
    }

    /**
     * Helper method to double array size if necessary
     */
    private void regrowArray() {
        if (size > backingArray.length) {
            T[] temp = (T[]) new Object[backingArray.length * 2];

            for (int i = 0; i < backingArray.length; i++) {
                temp[i] = backingArray[i];
            }

            backingArray = temp;
        }
    }

    @Override
    public void addToFront(T data) {
        addAtIndex(0, data);
    }

    @Override
    public void addToBack(T data) {
        addAtIndex(size, data);
    }

    @Override
    public T removeAtIndex(int index) {
        if (index > size - 1 || size == 0) {
            throw new IndexOutOfBoundsException("Cannot remove beyond the range of the array");
        }
        size--;

        T ret = backingArray[index];
        for (int i = index; i < size; i++) {
            backingArray[i] = backingArray[i + 1];
        }
        backingArray[size] = null;

        return ret;
    }

    @Override
    public T removeFromFront() {
        return size > 0 ? removeAtIndex(0) : null;
    }

    @Override
    public T removeFromBack() {
        return size > 0 ? removeAtIndex(size - 1) : null;
    }

    @Override
    public T get(int index) {
        if (index >= size) {
            throw new IndexOutOfBoundsException("Cannot access data at index out of range.");
        }
        return backingArray[index];
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        backingArray = (T[]) new Object[INITIAL_CAPACITY];
        size = 0;
    }

    @Override
    public Object[] getBackingArray() {
        return backingArray;
    }
}
