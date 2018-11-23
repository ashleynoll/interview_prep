import java.util.List;

/**
 * The interface for an AVL tree.
 */
public interface AVLInterface<T extends Comparable<? super T>> {
    /**
     * Add the data as a leaf to the AVL. Should traverse the tree to find the
     * appropriate location. If the data is already in the tree, then nothing
     * should be done (the duplicate shouldn't get added, and size should not be
     * incremented).
     *
     * Remember to recalculate heights going up the tree, rebalancing if
     * necessary.
     *
     * @throws java.lang.IllegalArgumentException if the data is null
     * @param data the data to be added
     */
    void add(T data);

    /**
     * Removes the data from the tree.  There are 3 cases to consider:
     * 1: the data is a leaf.  In this case, simply remove it.
     * 2: the data has one child.  In this case, simply replace the node with
     * the child node.
     * 3: the data has 2 children.  There are generally two approaches:
     * replacing the data with either the largest element in the left subtree
     * (commonly called the predecessor), or replacing it with the smallest
     * element in the right subtree (commonly called the successor). This implementation
     * will use the predecessor.
     *
     * Remember to recalculate heights going up the tree, rebalancing if
     * necessary.
     *
     * @throws java.lang.IllegalArgumentException if the data is null
     * @throws java.util.NoSuchElementException if the data is not in the tree
     * @param data data to remove from the tree
     * @return the data removed from the tree.  Do not return the same data
     * that was passed in.  Return the data that was stored in the tree.
     */
    T remove(T data);

    /**
     * Returns the data in the tree matching the parameter passed in.
     *
     * @throws java.lang.IllegalArgumentException if the data is null
     * @throws java.util.NoSuchElementException if the data is not found
     * @param data data to get in the AVL tree
     * @return the data in the tree equal to the parameter.  Do not return the
     * same data that was passed in.  Return the data that was stored in the
     * tree.
     */
    T get(T data);

    /**
     * Returns whether or not the parameter is contained within the tree.
     *
     * @throws java.lang.IllegalArgumentException if the data is null
     * @param data data to find in the AVL tree
     * @return whether or not the parameter is contained within the tree
     */
    boolean contains(T data);

    /**
     * Get the number of elements in the tree.
     *
     * @return the number of elements in the tree
     */
    int size();

    /**
     * Get the preorder traversal of the tree.
     *
     * @return a preorder traversal of the tree, or an empty list
     */
    List<T> preorder();

    /**
     * Get the postorder traversal of the tree.
     *
     * @return a postorder traversal of the tree, or an empty list
     */
    List<T> postorder();

    /**
     * Get the inorder traversal of the tree.
     *
     * @return an inorder traversal of the tree, or an empty list
     */
    List<T> inorder();

    /**
     * Get the level order traversal of the tree.
     *
     * @return a level order traversal of the tree, or an empty list
     */
    List<T> levelorder();

    /**
     * Clear the tree.
     */
    void clear();

    /**
     * Return the height of the root of the tree.
     * 
     * This method does not need to traverse the entire tree.
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    int height();
    
    /**
     * Use for testing purposes.
     *
     * @return the root of the tree
     */
    AVLNode<T> getRoot();
}
