import java.util.NoSuchElementException;

/**
 * Implementation of a linked stack.
 */
public class LinkedStack<T> implements StackInterface<T> {

    private LinkedNode<T> head;
    private int size;

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public T pop() {
        if (isEmpty()) {
            throw new NoSuchElementException("Cannot pop from an empty stack.");
        }

        T data = head.getData();
        head = head.getNext();

        size--;
        return data;
    }

    @Override
    public void push(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot insert null into a stack");
        }

        head = new LinkedNode<>(data, head);
        size++;
    }

    @Override
    public int size() {
        return size;
    }

    /**
     * Returns the head of this stack.
     * Used for testing purposes.
     *
     * @return the head node
     */
    public LinkedNode<T> getHead() {
        return head;
    }
}