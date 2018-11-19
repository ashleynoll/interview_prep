import java.util.NoSuchElementException;

/**
 * Implementation of a linked queue.
 */
public class LinkedQueue<T> implements QueueInterface<T> {

    private LinkedNode<T> head;
    private LinkedNode<T> tail;
    private int size;

    @Override
    public T dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("Cannot dequeue from an empty queue.");
        }

        T data = head.getData();
        head = head.getNext();

        if (head == null) {
            tail = null;
        }

        size--;
        return data;
    }

    @Override
    public void enqueue(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot insert null into a queue");
        }

        if (tail == null) {
            head = new LinkedNode<>(data);
            tail = head;
        } else {
            tail.setNext(new LinkedNode<>(data));
            tail = tail.getNext();
        }

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
     * Returns the head of this queue.
     * Used for testing purposes.
     *
     * @return the head node
     */
    public LinkedNode<T> getHead() {
        return head;
    }

    /**
     * Returns the tail of this queue.
     * Used for testing purposes.
     *
     * @return the tail node
     */
    public LinkedNode<T> getTail() {
        return tail;
    }
}