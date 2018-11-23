import java.util.*;

/**
 * Implementation of a binary search tree.
 */
public class BST<T extends Comparable<? super T>> implements BSTInterface<T> {
    private BSTNode<T> root;
    private int size;

    /**
     * A no argument constructor that should initialize an empty BST.
     */
    public BST() {
    }

    /**
     * Initializes the BST with the data in the Collection. The data in the BST
     * should be added in the same order it is in the Collection.
     *
     * @param data the data to add to the tree
     * @throws IllegalArgumentException if data or any element in data is null
     */
    public BST(Collection<T> data) {
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

        root = add(root, data);
    }

    private BSTNode<T> add(BSTNode<T> curr, T data) {
        if (curr == null) {
            size++;
            return new BSTNode<>(data);
        }

        if (data.compareTo(curr.getData()) < 0) {
            curr.setLeft(add(curr.getLeft(), data));
            return curr;
        } else if (data.compareTo(curr.getData()) > 0) {
            curr.setRight(add(curr.getRight(), data));
            return curr;
        } else {
            return curr;
        }
    }

    @Override
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot remove null data.");
        }

        BSTNode<T> dummy = new BSTNode<>(null);

        root = remove(root, data, dummy);

        return dummy.getData();
    }

    private BSTNode<T> remove(BSTNode<T> curr, T data, BSTNode<T> dummy) {
        if (curr == null) {
            throw new NoSuchElementException("Data does not exist in the tree.");
        }

        if (data.compareTo(curr.getData()) < 0) {
            curr.setLeft(remove(curr.getLeft(), data, dummy));
            return curr;
        } else if (data.compareTo(curr.getData()) > 0) {
            curr.setRight(remove(curr.getRight(), data, dummy));
            return curr;
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
                BSTNode<T> predecessor = new BSTNode<>(null);

                curr.setLeft(removePredecessor(curr.getLeft(), predecessor));
                curr.setData(predecessor.getData());

                return curr;
            }
        }
    }

    private BSTNode<T> removePredecessor(BSTNode<T> curr, BSTNode<T> dummy) {
        if (curr.getRight() == null) {
            dummy.setData(curr.getData());
            return curr.getLeft();
        }

        curr.setRight(removePredecessor(curr.getRight(), dummy));
        return curr;
    }

    @Override
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot get null data.");
        }

        return get(root, data);
    }

    private T get(BSTNode<T> curr, T data) {
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

    private boolean contains(BSTNode<T> curr, T data) {
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

    private void preorder(BSTNode<T> curr, List<T> list) {
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

    private void postorder(BSTNode<T> curr, List<T> list) {
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

    private void inorder(BSTNode<T> curr, List<T> list) {
        if (curr != null) {
            inorder(curr.getLeft(), list);
            list.add(curr.getData());
            inorder(curr.getRight(), list);
        }
    }

    @Override
    public List<T> levelorder() {
        Queue<BSTNode<T>> queue = new LinkedList<>();
        List<T> list = new ArrayList<>(size);
        queue.add(root);

        while (!queue.isEmpty()) {
            BSTNode<T> curr = queue.poll();
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
        return height(root);
    }

    private int height(BSTNode<T> curr) {
        if (curr == null) {
            return -1;
        }

        return Math.max(height(curr.getLeft()), height(curr.getRight())) + 1;
    }

    @Override
    public BSTNode<T> getRoot() {
        return root;
    }
}
