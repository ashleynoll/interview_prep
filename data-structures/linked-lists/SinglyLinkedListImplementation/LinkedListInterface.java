/**
 * This interface describes the public methods needed for SinglyLinkedList,
 * which should be singly-linked and should have both a head and a tail pointer.
 *
 * The expected Big-O for each method has been given.
 *
 */
public interface LinkedListInterface<T> {

    /**
     * Adds the element to the index specified.
     *
     * Adding to indices 0 and {@code size} should be O(1), all other cases are
     * O(n).
     *
     * @param index The requested index for the new element.
     * @param data The data for the new element.
     * @throws java.lang.IndexOutOfBoundsException if index is negative or
     * index > size.
     * @throws java.lang.IllegalArgumentException if data is null.
     */
    public void addAtIndex(int index, T data);

    /**
     * Adds the element to the front of the list.
     *
     * Must be O(1) for all cases.
     *
     * @param data The data for the new element.
     * @throws java.lang.IllegalArgumentException if data is null.
     */
    public void addToFront(T data);

    /**
     * Adds the element to the back of the list.
     *
     * Must be O(1) for all cases.
     *
     * @param data The data for the new element.
     * @throws java.lang.IllegalArgumentException if data is null.
     */
    public void addToBack(T data);

    /**
     * Removes the node and returns the data from the index specified.
     *
     * Removing from index 0 should be O(1), all other cases are O(n).
     *
     * @param index The requested index to be removed.
     * @return The object formerly located at index.
     * @throws java.lang.IndexOutOfBoundsException if index is negative or
     * index >= size.
     */
    public T removeAtIndex(int index);

    /**
     * Removes the node and returns the data at the front of the list. If the
     * list is empty, return {@code null}.
     *
     * Must be O(1) for all cases.
     *
     * @return The object formerly located at the front.
     */
    public T removeFromFront();

    /**
     * Removes the node and returns the data at the back of the list. If the
     * list is empty, return {@code null}.
     *
     * Must be O(n) for all cases.
     *
     * @return The object formerly located at the back.
     */
    public T removeFromBack();

    /**
     * Removes the node and returns the first copy of the given data, with
     * 'first' meaning the copy with the smallest index. In other words, the
     * first copy encountered when traversing over the 'next' pointers (starting
     * at the head).
     *
     * Must be O(n) for all cases.
     *
     * @param data The data to be removed from the list.
     * @return The first occurrence of data in the list.
     * @throws java.lang.IllegalArgumentException if data is null.
     * @throws java.util.NoSuchElementException if data is not found.
     */
    public T removeFirstOccurrence(T data);

    /**
     * Returns the element at the specified index.
     *
     * Getting from indices 0 and {@code size} should be O(1), all other cases
     * are O(n).
     *
     * @param index The index of the requested element.
     * @return The object stored at index.
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or
     * index >= size.
     */
    public T get(int index);

    /**
     * Returns an array representation of the linked list.
     *
     * Must be O(n) for all cases.
     *
     * @return An array of length {@code size} holding all of the objects in
     * this list in the same order.
     */
    public Object[] toArray();

    /**
     * Returns a boolean value indicating if the list is empty.
     *
     * Must be O(1) for all cases.
     *
     * @return true if empty; false otherwise.
     */
    public boolean isEmpty();

    /**
     * Returns the number of elements in the list.
     *
     * Must be O(1) for all cases.
     *
     * @return The size of the list.
     */
    public int size();

    /**
     * Clears the list of all data.
     *
     * Must be O(1) for all cases.
     */
    public void clear();

    /**
     * Returns the head node of the linked list.
     * Used for testing purposes.
     *
     * @return Node at the head of the linked list.
     */
    public LinkedListNode<T> getHead();

    /**
     * Returns the tail node of the linked list.
     * Used for testing purposes.
     *
     * @return Node at the tail of the linked list.
     */
    public LinkedListNode<T> getTail();
}
