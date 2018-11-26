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
     *
     */
    public <T> boolean checkBalanced(Node<T> root) {
        if (root == null) {
            return true;
        }

        int difference = checkHeights(root);

        return difference != Integer.MIN_VALUE;
    }

    private <T> int checkHeights(Node<T> curr) {
        if (curr == null) {
            return -1;
        }

        int leftHeight = checkHeights(curr.left),
                rightHeight = checkHeights(curr.right);

        if (leftHeight == Integer.MIN_VALUE || rightHeight == Integer.MIN_VALUE
                || Math.abs(leftHeight - rightHeight) > 1) {
            return Integer.MIN_VALUE;
        }

        return Math.max(leftHeight, rightHeight) + 1;
    }
}