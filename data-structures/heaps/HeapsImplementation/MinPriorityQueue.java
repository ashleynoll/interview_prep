/**
 * Implementation of a min priority queue.
 */
public class MinPriorityQueue<T extends Comparable<? super T>>
    implements PriorityQueueInterface<T> {

    private HeapInterface<T> backingHeap;

    /**
     * Creates a priority queue.
     */
    public MinPriorityQueue() {
        backingHeap = new MinHeap<>();
    }

    @Override
    public void enqueue(T item) {
        backingHeap.add(item);
    }

    @Override
    public T dequeue() {
        return backingHeap.remove();
    }

    @Override
    public boolean isEmpty() {
        return backingHeap.isEmpty();
    }

    @Override
    public int size() {
        return backingHeap.size();
    }

    @Override
    public void clear() {
        backingHeap.clear();
    }

    @Override
    public HeapInterface<T> getBackingHeap() {
        return backingHeap;
    }
}
