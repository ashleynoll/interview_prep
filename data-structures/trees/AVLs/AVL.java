import java.util.*;

/**
 * Implementation of an AVL Tree.
 */
public class AVL<T extends Comparable<? super T>> implements AVLInterface<T> {
    private AVLNode<T> root;
    private int size;

    /**
     * A no argument constructor that should initialize an empty AVL tree.
     */
    public AVL() {
    }

    /**
     * Initializes the AVL tree with the data in the Collection. The data
     * should be added in the same order it is in the Collection.
     *
     * @param data the data to add to the tree
     * @throws IllegalArgumentException if data or any element in data is null
     */
    public AVL(Collection<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot use null data to instantiate BST.");
        }

        for (T datum : data) {
            add(datum);
        }
    }

    @Override
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot insert null data into tree.");
        }

        root = updateNode(add(root, data));
    }

    private AVLNode<T> add(AVLNode<T> curr, T data) {
        if (curr == null) {
            size++;
            return new AVLNode<>(data);
        }

        if (data.compareTo(curr.getData()) < 0) {
            curr.setLeft(add(curr.getLeft(), data));
            return updateNode(curr);
        } else if (data.compareTo(curr.getData()) > 0) {
            curr.setRight(add(curr.getRight(), data));
            return updateNode(curr);
        } else {
            return curr;
        }
    }

    @Override
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot remove null data.");
        }

        AVLNode<T> dummy = new AVLNode<>(null);

        root = updateNode(remove(root, data, dummy));

        return dummy.getData();
    }

    private AVLNode<T> remove(AVLNode<T> curr, T data, AVLNode<T> dummy) {
        if (curr == null) {
            throw new NoSuchElementException("Data does not exist in the tree.");
        }

        if (data.compareTo(curr.getData()) < 0) {
            curr.setLeft(remove(curr.getLeft(), data, dummy));
            return updateNode(curr);
        } else if (data.compareTo(curr.getData()) > 0) {
            curr.setRight(remove(curr.getRight(), data, dummy));
            return updateNode(curr);
        } else {
            dummy.setData(curr.getData());
            size--;
            if (curr.getLeft() == null || curr.getRight() == null) {
                if (curr.getLeft() == null) {
                    return curr.getRight();
                } else {
                    return curr.getLeft();
                }
            } else {
                AVLNode<T> predecessor = new AVLNode<>(null);

                curr.setLeft(removePredecessor(curr.getLeft(), predecessor));
                curr.setData(predecessor.getData());

                return updateNode(curr);
            }
        }
    }

    private AVLNode<T> removePredecessor(AVLNode<T> curr, AVLNode<T> dummy) {
        if (curr.getRight() == null) {
            dummy.setData(curr.getData());
            return curr.getLeft();
        }

        curr.setRight(removePredecessor(curr.getRight(), dummy));
        return updateNode(curr);
    }

    @Override
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot get null data.");
        }

        return get(root, data);
    }

    private T get(AVLNode<T> curr, T data) {
        if (curr == null) {
            throw new NoSuchElementException("The data does not exist in the tree");
        }

        if (data.compareTo(curr.getData()) < 0) {
            return get(curr.getLeft(), data);
        } else if (data.compareTo(curr.getData()) > 0) {
            return get(curr.getRight(), data);
        } else {
            return curr.getData();
        }
    }

    @Override
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot see if tree contains null data.");
        }
        return contains(root, data);
    }

    private boolean contains(AVLNode<T> curr, T data) {
        if (curr == null) {
            return false;
        }

        if (data.compareTo(curr.getData()) < 0) {
            return contains(curr.getLeft(), data);
        } else if (data.compareTo(curr.getData()) > 0) {
            return contains(curr.getRight(), data);
        } else {
            return true;
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public List<T> preorder() {
        List<T> list = new ArrayList<>(size);
        preorder(root, list);

        return list;
    }

    private void preorder(AVLNode<T> curr, List<T> list) {
        if (curr != null) {
            list.add(curr.getData());
            preorder(curr.getLeft(), list);
            preorder(curr.getRight(), list);
        }
    }

    @Override
    public List<T> postorder() {
        List<T> list = new ArrayList<>(size);
        postorder(root, list);

        return list;
    }

    private void postorder(AVLNode<T> curr, List<T> list) {
        if (curr != null) {
            postorder(curr.getLeft(), list);
            postorder(curr.getRight(), list);
            list.add(curr.getData());
        }
    }

    @Override
    public List<T> inorder() {
        List<T> list = new ArrayList<>(size);
        inorder(root, list);

        return list;
    }

    private void inorder(AVLNode<T> curr, List<T> list) {
        if (curr != null) {
            inorder(curr.getLeft(), list);
            list.add(curr.getData());
            inorder(curr.getRight(), list);
        }
    }

    @Override
    public List<T> levelorder() {
        Queue<AVLNode<T>> queue = new LinkedList<>();
        List<T> list = new ArrayList<>(size);
        queue.add(root);

        while (!queue.isEmpty()) {
            AVLNode<T> curr = queue.poll();
            if (curr != null) {
                list.add(curr.getData());
                queue.add(curr.getLeft());
                queue.add(curr.getRight());
            }
        }

        return list;
    }

    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    @Override
    public int height() {
        return root == null ? -1 : root.getHeight();
    }

    @Override
    public AVLNode<T> getRoot() {
        return root;
    }

    private AVLNode<T> updateNode(AVLNode<T> curr) {
        return rotate(updateHeightAndBF(curr));
    }

    private AVLNode<T> updateHeightAndBF(AVLNode<T> curr) {
        if (curr != null) {
            int rightHeight = curr.getRight() != null ? curr.getRight().getHeight() : -1,
                    leftHeight = curr.getLeft() != null ? curr.getLeft().getHeight() : -1;

            curr.setHeight(Math.max(rightHeight, leftHeight) + 1);
            curr.setBalanceFactor(leftHeight - rightHeight);

            return curr;
        }
        return null;
    }

    private AVLNode<T> rotate(AVLNode<T> curr) {
        if (curr != null) {
            if (curr.getBalanceFactor() == 2) {
                if (curr.getLeft().getBalanceFactor() <= 0) {
                    curr.setLeft(leftRotate(curr.getLeft()));
                }
                return rightRotate(curr);
            } else if (curr.getBalanceFactor() == -2) {
                if (curr.getRight().getBalanceFactor() >= 0) {
                    curr.setRight(rightRotate(curr.getRight()));
                }
                return leftRotate(curr);
            }
            return curr;
        }
        return null;
    }

    private AVLNode<T> leftRotate(AVLNode<T> curr) {
        AVLNode<T> temp = curr.getRight().getLeft(),
                newRoot = curr.getRight();

        curr.getRight().setLeft(curr);
        curr.setRight(temp);

        updateHeightAndBF(curr);
        return updateHeightAndBF(newRoot);
    }

    private AVLNode<T> rightRotate(AVLNode<T> curr) {
        AVLNode<T> temp = curr.getLeft().getRight(),
                newRoot = curr.getLeft();
        curr.getLeft().setRight(curr);
        curr.setLeft(temp);

        updateHeightAndBF(curr);
        return updateHeightAndBF(newRoot);
    }
}
