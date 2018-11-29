import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TreesTests {
    private static final long TIMEOUT = 200;
    private TreesProblems problems;
    TreesProblems.Node<Integer> root;

    @Before
    public void setUp() {
        problems = new TreesProblems();
    }

    private static <T extends Comparable<T>> void assertTreeEquals(TreesProblems.Node<T> expectedRoot, TreesProblems.Node<T> actualRoot) {

        if (expectedRoot == null && actualRoot != null) {
            fail("Actual tree has an unexpected node " + "(data = " + actualRoot.data + ")");
        } else if (actualRoot == null && expectedRoot != null) {
            fail("Actual tree is missing a node" + "(expected data = " + expectedRoot.data + ")");
        } else if (expectedRoot != null) {
            assertEquals("Tree node contained incorrect data", expectedRoot.data, actualRoot.data);

            assertTreeEquals(expectedRoot.left, actualRoot.left);
            assertTreeEquals(expectedRoot.right, actualRoot.right);
        }
    }

    @Test(timeout = TIMEOUT)
    public void testMinimalTreeOdd() {
        Integer[] arr = { 1, 2, 3, 4, 5 };

        root = new TreesProblems.Node<>(3, new TreesProblems.Node<>(1, null,
                new TreesProblems.Node<>(2)), new TreesProblems.Node<>(4, null, new TreesProblems.Node<>(5)));

        assertTreeEquals(root, problems.minimalTree(arr));
    }

    @Test(timeout = TIMEOUT)
    public void testMinimalTreeEven() {
        Integer[] arr = { 1, 2, 3, 4, 5, 6 };

        root = new TreesProblems.Node<>(3, new TreesProblems.Node<>(1, null,
                new TreesProblems.Node<>(2)), new TreesProblems.Node<>(5, new TreesProblems.Node<>(4), new TreesProblems.Node<>(6)));

        assertTreeEquals(root, problems.minimalTree(arr));
    }

    @Test(timeout = TIMEOUT)
    public void testCheckBalancedFalse() {
        root = new TreesProblems.Node<>(1, null, new TreesProblems.Node<>(3,  new TreesProblems.Node<>(4), null));

        assertFalse("Tree should not be balanced.", problems.checkBalanced(root));

        root = new TreesProblems.Node<>(1, new TreesProblems.Node<>(2, new TreesProblems.Node<>(4), null),
                new TreesProblems.Node<>(3, new TreesProblems.Node<>(6, new TreesProblems.Node<>(7,
                        new TreesProblems.Node<>(8), null), null), new TreesProblems.Node<>(5)));

        assertFalse("Tree should not be balanced.", problems.checkBalanced(root));
    }

    @Test(timeout = TIMEOUT)
    public void testCheckBalancedTrue() {
        root = new TreesProblems.Node<>(1);

        assertTrue("One node tree should be balanced.", problems.checkBalanced(root));

        root = new TreesProblems.Node<>(1, new TreesProblems.Node<>(2), new TreesProblems.Node<>(3));

        assertTrue("Tree should be balanced.", problems.checkBalanced(root));

        root = new TreesProblems.Node<>(1, new TreesProblems.Node<>(2, new TreesProblems.Node<>(4), null),
                new TreesProblems.Node<>(3, null, new TreesProblems.Node<>(5)));

        assertTrue("Tree should be balanced.", problems.checkBalanced(root));
    }

    @Test(timeout = TIMEOUT)
    public void testValidateBSTTrue() {
        root = new TreesProblems.Node<>(1);

        assertTrue("One node is a BST.", problems.validateBST(root));

        assertTrue( "7 node tree should be a BST.", problems.validateBST(problems.minimalTree(new Integer[]{1, 2, 3, 4, 5, 6, 7})));

        root = new TreesProblems.Node<>(3, new TreesProblems.Node<>(2, new TreesProblems.Node<>(1), null),
                new TreesProblems.Node<>(4, null, new TreesProblems.Node<>(5)));

        assertTrue("Should be a BST.", problems.validateBST(root));
    }

    @Test(timeout = TIMEOUT)
    public void testValidateBSTFalse() {
        root = new TreesProblems.Node<>(1, new TreesProblems.Node<>(8), null);

        assertFalse("Should not be a BST.", problems.validateBST(root));

        assertFalse( "Should not be a BST.", problems.validateBST(problems.minimalTree(new Integer[]{1, 2, 1})));

        root = new TreesProblems.Node<>(3, new TreesProblems.Node<>(2, new TreesProblems.Node<>(1), null),
                new TreesProblems.Node<>(4, null, new TreesProblems.Node<>(1)));

        assertFalse("Should not be a BST.", problems.validateBST(root));
    }
}

