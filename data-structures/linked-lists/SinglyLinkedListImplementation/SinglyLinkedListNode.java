/**
 * Node class used for implementing SinglyLinkedList.
 */
public class SinglyLinkedListNode<T> {
    private T data;
    private SinglyLinkedListNode<T> next;

    /**
     * Creates a new LinkedListNode with the given T object and node reference.
     *
     * @param data The data stored in the new node.
     * @param next The next node in the list.
     */
    public SinglyLinkedListNode(T data, SinglyLinkedListNode<T> next) {
        this.data = data;
        this.next = next;
    }

    /**
     * Creates a new LinkedListNode with only the given T object.
     *
     * @param data The data stored in the new node.
     */
    public SinglyLinkedListNode(T data) {
        this(data, null);
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
    public SinglyLinkedListNode<T> getNext() {
        return next;
    }

    /**
     * Set the next node.
     *
     * @param next The new next node.
     */
    public void setNext(SinglyLinkedListNode<T> next) {
        this.next = next;
    }

    @Override
    public String toString() {
        return "Node containing: " + data;
    }
}
