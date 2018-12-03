import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
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

        public Node(T data) {
            this(data, null, null);
        }
    }

    /**
     * Problem:
     * <p>
     * Given a sorted (increasing order) array with unique integer elements,
     * write an algorithm to create a binary search tree with minimal height.
     * <p>
     * Solution Explanation:
     * <p>
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
     * <p>
     * Implement a function to check if a binary tree is balanced. For the purposes of
     * this question, a balanced tree is defined to be a tree such that the heights of
     * the two subtrees of any node never differ by more than one.
     * <p>
     * Solution Explanation:
     * <p>
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
     * <p>
     * Implement a function to check if a binary tree is a binary search tree.
     * <p>
     * Solution Explanation:
     * <p>
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

    /**
     * Problem:
     * <p>
     * Design an algorithm and write code to find the first common ancestor of two nodes
     * in a binary tree. Avoid storing additional nodes in a data structure. NOTE: this
     * is not necessarily a binary search tree.
     * <p>
     * Solution Explanation:
     */
    public <T> Node<T> firstCommonAncestor(Node<T> root, T child1, T child2) {
        if (root == null || child1 == null || child2 == null) {
            return null;
        }

        Node<T> answer = new Node<>(null);

        firstCommonAncestor(root, child1, child2, answer);

        return answer.data == null ? null : answer;
    }

    private <T> boolean firstCommonAncestor(Node<T> curr, T child1, T child2, Node<T> answer) {
        if (curr == null) {
            return false;
        }
        boolean left = firstCommonAncestor(curr.left, child1, child2, answer),
                right = firstCommonAncestor(curr.right, child1, child2, answer),
                isCurrChild = curr.data.equals(child1) || curr.data.equals(child2);

        if (isCurrChild && (left || right) || (left && right)) {
            answer.data = curr.data;
            return false;
        }

        return isCurrChild || left || right;
    }

    /**
     * Problem:
     *
     * A binary search tree was created by traversing through an array from left to right
     * and inserting each element. Given a binary search tree with distinct elements, print
     * all possible arrays that could have led to the tree.
     *
     * Solution Explanation:
     *
     *
     */
    public <T> List<List<T>> bstBuildSequence(Node<T> root) {
        if (root == null) {
            return null;
        }

        List<List<T>> ret = new ArrayList<>();

        bstBuildSequence(root, new LinkedList<>(), new LinkedList<>(), ret);

        return ret;
    }

    private <T> void bstBuildSequence(Node<T> curr, List<T> prefix, List<Node<T>> poss, List<List<T>> allPoss) {
        prefix.add(curr.data);

        if (curr.left != null) {
            poss.add(curr.left);
        }

        if (curr.right != null) {
            poss.add(curr.right);
        }

        if (poss.isEmpty()) {
            allPoss.add(prefix);
        }

        for (Node<T> next : poss) {
            List<T> prefixClone = new LinkedList<>(prefix);
            List<Node<T>> possClone = new LinkedList<>(poss);
            possClone.remove(next);

            bstBuildSequence(next, prefixClone, possClone, allPoss);
        }
    }

    /**
     * Problem:
     *
     * T1 and T2 are two very large binary trees, with T1 much bigger than T2. Create an
     * algorithm to determine if T2 is a subtree of T1.
     *
     * A tree T2 is a subtree of T1 if there exists a node n in T1 such that the subtree
     * of n is identical to T2. That is, if you cut off the tree at node n, the two trees
     * would be identical.
     *
     * Solution Explanation:
     *
     * In this solution, a preorder traversal is used to check if the smaller tree is
     * contained in the subtree. At every step of the traversal it is checked to see
     * if the data in the current node from the larger tree is the same as the root
     * of the smaller tree. If so, the process is continued with its children to see
     * if the rest of the tree is contained as well.
     */
    public <T> boolean isSubtree(Node<T> tree, Node<T> subtree) {
        if (tree == null || subtree == null) {
            return false;
        }

        if (checkTreeEquals(tree, subtree)) {
            return true;
        }

        return isSubtree(tree.left, subtree) || isSubtree(tree.right, subtree);
    }

    private <T> boolean checkTreeEquals(Node<T> tree, Node<T> subtree) {
        if (tree == null && subtree == null) {
            return true;
        } else if (tree == null || subtree == null) {
            return false;
        } else if (!tree.data.equals(subtree.data)) {
            return false;
        } else {
            return checkTreeEquals(tree.left, subtree.left) && checkTreeEquals(tree.right, subtree.right);
        }
    }
}