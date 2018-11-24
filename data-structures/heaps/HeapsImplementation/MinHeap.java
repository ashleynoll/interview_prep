import java.util.NoSuchElementException;

/**
 * Implementation of a min heap.
 */
public class MinHeap<T extends Comparable<? super T>>
    implements HeapInterface<T> {

    private T[] backingArray;
    private int size;

    /**
     * Creates a Heap with an initial size of {@code STARTING_SIZE} for the
     * backing array.
     */
    public MinHeap() {
        backingArray = (T[]) new Comparable[STARTING_SIZE];
    }

    @Override
    public void add(T item) {
        if (item == null) {
            throw new IllegalArgumentException("Cannot insert null data.");
        }
        size++;

        resizeBackingArray();

        backingArray[size] = item;

        int i = size;
        while(i > 1 && backingArray[i].compareTo(backingArray[i/2]) < 0) {
            T temp = backingArray[i/2];
            backingArray[i/2] = backingArray[i];
            backingArray[i] = temp;
            i /= 2;
        }
    }

    @Override
    public T remove() {
        if (isEmpty()) {
            throw new NoSuchElementException("Cannot remove from an empty heap.");
        }

        T ret = backingArray[1];
        backingArray[1] = backingArray[size];
        backingArray[size--] = null;

        downheap(1);

        return ret;
    }

    private void downheap(int i) {
        if (i > size / 2) {
            return;
        }

        T right = backingArray[2 * i],
                left = 2 * i + 1 <= size ? backingArray[2 * i + 1] : null;

        if (right.compareTo(backingArray[i]) < 0 || left != null && left.compareTo(backingArray[i]) < 0) {
            if (left == null || right.compareTo(left) < 0) {
                backingArray[2 * i] = backingArray[i];
                backingArray[i] = right;
                downheap(2 * i);
            } else {
                backingArray[2 * i + 1] = backingArray[i];
                backingArray[i] = left;
                downheap(2 * i + 1);
            }
        }
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
        backingArray = (T[]) new Comparable[STARTING_SIZE];
        size = 0;
    }

    @Override
    public Comparable[] getBackingArray() {
        return backingArray;
    }

    private void resizeBackingArray() {
        if (backingArray.length <= size) {
            T[] temp = (T[]) new Comparable[(int) (backingArray.length * 1.5)];

            for (int i = 1; i < backingArray.length; i++) {
                temp[i] = backingArray[i];
            }

            backingArray = temp;
        }
    }

}
