import java.util.Map;

public class TreesProblems {
    public static class Node<T> {
        public T data;
        public Node<T>[] children;
        public Node<T> right;
        public Node<T> left;

        public Node(T data, Node<T> left, Node<T> right) {
            this.data = data;
            this.left = left;
            this.right = right;
        }

        public Node (T data) {
            this(data, null, null);
        }
    }

    /**
     * Problem:
     *
     * Given a sorted (increasing order) array with unique integer elements,
     * write an algorithm to create a binary search tree with minimal height.
     *
     * Solution Explanation:
     *
     * We will be using binary search to create an optimally balanced BST.
     * The middle element of the array must be the root as it has equal
     * elements to either side. Additionally, the now halved array has middle
     * points which will be the optimal sub-tree root for their side.
     */
    public <T> Node<T> minimalTree(T[] arr) {
        if (arr == null || arr.length == 0) {
            return null;
        }

        return minimalTree(arr, 0, arr.length - 1);
    }

    private <T> Node<T> minimalTree(T[] arr, int left, int right) {
        if (left > right) {
            return null;
        }

        int mid = (left + right) / 2;
        Node<T> node = new Node<>(arr[mid]);
        node.left = minimalTree(arr, left, mid - 1);
        node.right = minimalTree(arr, mid + 1, right);

        return node;
    }

    /**
     * Problem:
     *
     * Implement a function to check if a binary tree is balanced. For the purposes of
     * this question, a balanced tree is defined to be a tree such that the heights of
     * the two subtrees of any node never differ by more than one.
     *
     * Solution Explanation:
     *
     * Go through both the left and right sides of the tree recursively. When null is
     * found, return a height of -1. On each return, compare the left and right respective
     * heights. If they are Integer.MIN_VALUE an imbalance has already been found. If not,
     * see if they are more than one apart. If so, return Integer.MIN_VALUE, else return
     * the max of the left and right height + 1. Return at the top of the recursive call
     * if the returned value was not Integer.MIN_VALUE.
     */
    public <T> boolean checkBalanced(Node<T> root) {
        if (root == null) {
            return true;
        }
        return checkHeights(root) != Integer.MIN_VALUE;
    }

    private <T> int checkHeights(Node<T> curr) {
        if (curr == null) {
            return -1;
        }

        int leftHeight = checkHeights(curr.left);
        if (leftHeight == Integer.MIN_VALUE) {
            return Integer.MIN_VALUE;
        }

        int rightHeight = checkHeights(curr.right);
        if (rightHeight == Integer.MIN_VALUE) {
            return Integer.MIN_VALUE;
        }

        if (Math.abs(leftHeight - rightHeight) > 1) {
            return Integer.MIN_VALUE;
        }

        return Math.max(leftHeight, rightHeight) + 1;
    }

    /**
     * Problem:
     *
     * Implement a function to check if a binary tree is a binary search tree.
     *
     * Solution Explanation:
     *
     * BSTs have the property that everything to the left of a node must be smaller
     * than it and everything to the right has to be greater than. As you traverse left
     * down the tree, ensure that everything remains less than the parent node and greater
     * than the furthest right parent node. Likewise, while recursing right, ensure that
     * everything is greater than the last parent node and less than the furthest left
     * most node.
     */
    public boolean validateBST(Node<Integer> root) {
        return root == null || validateBST(root, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    private boolean validateBST(Node<Integer> curr, int greaterThan, int lessThan) {
        if (curr == null) {
            return true;
        } else if (curr.data <= greaterThan || curr.data >= lessThan) {
            return false;
        }

        return validateBST(curr.left, greaterThan, curr.data) && validateBST(curr.right, curr.data, lessThan);
    }
}