/**
 * Node class used for implementing DoublyLinkedList.
 */
public class DoublyLinkedListNode<T> {
    private T data;
    private DoublyLinkedListNode<T> previous;
    private DoublyLinkedListNode<T> next;

    /**
     * Creates a new LinkedListNode with the given T object and node references.
     *
     * @param data The data stored in the new node.
     * @param previous The previous node in the list.
     * @param next The next node in the list.
     */
    public DoublyLinkedListNode(T data, DoublyLinkedListNode<T> previous,
                                DoublyLinkedListNode<T> next) {
        this.data = data;
        this.previous = previous;
        this.next = next;
    }

    /**
     * Creates a new LinkedListNode with only the given T object.
     *
     * @param data The data stored in the new node.
     */
    public DoublyLinkedListNode(T data) {
        this(data, null, null);
    }

    /**
     * Gets the data stored in the node.
     *
     * @return The data in this node.
     */
    public T getData() {
        return data;
    }

    /**
     * Gets the next node.
     *
     * @return The next node.
     */
    public DoublyLinkedListNode<T> getNext() {
        return next;
    }

    /**
     * Sets the next node.
     *
     * @param next The new next node.
     */
    public void setNext(DoublyLinkedListNode<T> next) {
        this.next = next;
    }

    /**
     * Gets the previous node.
     *
     * @return The previous node.
     */
    public DoublyLinkedListNode<T> getPrevious() {
        return previous;
    }

    /**
     * Sets the previous node.
     *
     * @param previous The new previous node.
     */
    public void setPrevious(DoublyLinkedListNode<T> previous) {
        this.previous = previous;
    }

    @Override
    public String toString() {
        return "Node containing: " + data;
    }
}
