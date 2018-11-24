import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.Assert.*;

public class BSTTests {

    private static final long TIMEOUT = 200;

    /**
     * The size of the tree returned by {@link #buildGeneralTree()}
     */
    private static final int SIZE_OF_GENERAL_TREE = 8;

    private BST<Parrot> testTree;

    private final Parrot aussie = new Parrot("AussieReverseCongaParrot", 50);
    private final Parrot blonde = new Parrot("BlondeSassyParrot", 25);
    private final Parrot beer = new Parrot("BeerParrot", 0);
    private final Parrot conga = new Parrot("CongaParrot", 10);
    private final Parrot fiesta = new Parrot("FiestaParrot", 30);
    private final Parrot goth = new Parrot("GothParrot", 75);
    private final Parrot harry = new Parrot("HarryPotterParrot", 60);
    private final Parrot parrot = new Parrot("ParrotCop", 55);
    private final Parrot twenty = new Parrot("twenty", 20);
    private final Parrot fortyeight = new Parrot("fortyeight", 48);

    @Before
    public void setup() {
        testTree = new BST<>();
    }

    /**
     * Reflectively sets a BST's contents with the provided values
     *
     * @param tree The tree to have its contents set
     * @param root The new root of the tree
     * @param size The new size of the tree
     * @param <T>  The type of data the tree stores
     */
    private static <T extends Comparable<T>> void setTree(BST<T> tree, BSTNode<T> root, int size) {

        try {
            Field rootField = tree.getClass().getDeclaredField("root");
            rootField.setAccessible(true);
            rootField.set(tree, root);

            Field sizeField = tree.getClass().getDeclaredField("size");
            sizeField.setAccessible(true);
            sizeField.setInt(tree, size);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Recursively checks whether or not a BST's contents match up with the
     * structure of an expected root. If the trees are not equal, an
     * AssertionError will be thrown.
     *
     * THIS METHOD DOESN'T CHECK THE SIZE OF THE BST.
     *
     * @param expectedRoot The expected root of the BST
     * @param actualRoot   The actual root of the BST
     * @param <T>          The type of data the tree stores
     */
    private static <T extends Comparable<T>> void assertTreeEquals(BSTNode<T> expectedRoot, BSTNode<T> actualRoot) {

        if (expectedRoot == null && actualRoot != null) {
            fail("Actual tree has an unexpected node " + "(data = " + actualRoot.getData() + ")");
        } else if (actualRoot == null && expectedRoot != null) {
            fail("Actual tree is missing a node" + "(expected data = " + expectedRoot.getData() + ")");
        } else if (expectedRoot != null) {
            assertEquals("Tree node contained incorrect data", expectedRoot.getData(), actualRoot.getData());

            assertTreeEquals(expectedRoot.getLeft(), actualRoot.getLeft());
            assertTreeEquals(expectedRoot.getRight(), actualRoot.getRight());
        }
    }

    /**
     * Asserts if two lists are equal
     *
     * @param a first  list
     * @param b second list
     */
    private void assertListEquals(List<Parrot> a, List<Parrot> b) {
        assertEquals("Lists aren't the same size", a.size(), b.size());

        for (int i = 0; i < a.size(); i++) {
            assertEquals("The lists are not equal, Differs at index: " + i, a.get(i), b.get(i));
        }
    }

    /**
     * Generates a mostly balanced BST
     *
     * @return The root of the following BST:
     *
     *                    AussieReverseCongaParrot (50)
     *                        /                     \
     *                       /                       \
     *         BlondeSassyParrot (25)               GothParrot (75)
     *           /              \                     /
     *          /           FiestaParrot (30)        /
     *         /                                    /
     *  Beer Parrot (0)                      HarryPotterParrot (60)
     *         \                                /
     *     CongaParrot (10)           ParrotCop (55)
     */
    private BSTNode<Parrot> buildGeneralTree() {
        BSTNode<Parrot> root = new BSTNode<>(aussie);
        root.setLeft(new BSTNode<>(blonde));
        root.getLeft().setLeft(new BSTNode<>(beer));
        root.getLeft().getLeft().setRight(new BSTNode<>(conga));
        root.getLeft().setRight(new BSTNode<>(fiesta));
        root.setRight(new BSTNode<>(goth));
        root.getRight().setLeft(new BSTNode<>(harry));
        root.getRight().getLeft().setLeft(new BSTNode<>(parrot));
        return root;
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testConstructorThrowsNullCollection() {
        try {
            new BST<String>(null);
        } catch (IllegalArgumentException e) {
            assertEquals("Incorrect exception thrown", IllegalArgumentException.class, e.getClass());
            throw e;
        }
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testConstructorThrowsNullElement() {
        try {
            new BST<>(Arrays.asList(new Parrot("ThumbsUpParrot", 2), new Parrot("ShipItParrot", 1), null, new Parrot("NyanParrot", 3)));
        } catch (IllegalArgumentException e) {
            assertEquals("Incorrect exception thrown", IllegalArgumentException.class, e.getClass());
            throw e;
        }
    }

    @Test(timeout = TIMEOUT)
    public void testConstructorGeneral() {
        BST<Parrot> actual = new BST<>(Arrays.asList(new Parrot("ThumbsUpParrot", 2), new Parrot("ShipItParrot", 1), new Parrot("NyanParrot", 3)));

        BSTNode<Parrot> expected = new BSTNode<>(new Parrot("ThumbsUpParrot", 2));
        expected.setLeft(new BSTNode<>(new Parrot("ShipItParrot", 1)));
        expected.setRight(new BSTNode<>(new Parrot("NyanParrot", 3)));

        assertEquals("BST size is incorrect", 3, actual.size());
        assertTreeEquals(expected, actual.getRoot());
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testAddThrowsForNullData() {
        BST<Parrot> actual = new BST<>();
        try {
            actual.add(null);
        } catch (IllegalArgumentException e) {
            assertEquals("Incorrect exception thrown", IllegalArgumentException.class, e.getClass());

            assertEquals("Size was incremented before throwing exception", 0, actual.size());

            assertTreeEquals(null, actual.getRoot());
            throw e;
        }
    }

    @Test(timeout = TIMEOUT)
    public void testAddToEmptyTree() {
        BST<Parrot> actual = new BST<>();
        actual.add(new Parrot("PartyParrot", 0));

        BSTNode<Parrot> expected = new BSTNode<>(new Parrot("PartyParrot", 0));

        assertEquals("Incorrect size", 1, actual.size());
        assertTreeEquals(expected, actual.getRoot());
    }

    @Test(timeout = TIMEOUT)
    public void testAddToRoot() {
        BST<Parrot> actual = new BST<>();
        BSTNode<Parrot> root = new BSTNode<>(new Parrot("PartyParrot", 0));
        root.setLeft(new BSTNode<>(new Parrot("FastParrot", -1)));

        setTree(actual, root, 2);
        actual.add(new Parrot("CongaParrot", 1));

        BSTNode<Parrot> expected = new BSTNode<>(new Parrot("PartyParrot", 0));
        expected.setLeft(new BSTNode<>(new Parrot("FastParrot", -1)));
        expected.setRight(new BSTNode<>(new Parrot("CongaParrot", 1)));

        assertEquals("Incorrect size", 3, actual.size());
        assertTreeEquals(expected, actual.getRoot());
    }

    @Test(timeout = TIMEOUT)
    public void testAddToLevelTwo() {
        BST<Parrot> actual = new BST<>();
        BSTNode<Parrot> root = new BSTNode<>(new Parrot("ShuffleParrot", 50));
        root.setLeft(new BSTNode<>(new Parrot("ScienceParrot", 25)));

        setTree(actual, root, 2);
        actual.add(new Parrot("FiestaParrot", 35));

        BSTNode<Parrot> expected = new BSTNode<>(new Parrot("ShuffleParrot", 50));
        expected.setLeft(new BSTNode<>(new Parrot("ScienceParrot", 25)));
        expected.getLeft().setRight(new BSTNode<>(new Parrot("FiestaParrot", 35)));

        assertEquals("Incorrect size", 3, actual.size());
        assertTreeEquals(expected, actual.getRoot());

    }

    @Test(timeout = TIMEOUT)
    public void testAddDoesNotPutDuplicate() {
        BST<Parrot> actual = new BST<>();
        BSTNode<Parrot> root = new BSTNode<>(new Parrot("SassyParrot", 50));
        root.setLeft(new BSTNode<>(new Parrot("PizzaParrot", 25)));
        root.setRight(new BSTNode<>(new Parrot("SadParrot", 75)));

        setTree(actual, root, 3);
        actual.add(new Parrot("SadParrot", 75));

        BSTNode<Parrot> expected = new BSTNode<>(new Parrot("SassyParrot", 50));
        expected.setLeft(new BSTNode<>(new Parrot("PizzaParrot", 25)));
        expected.setRight(new BSTNode<>(new Parrot("SadParrot", 75)));

        assertEquals("Incorrect size", 3, actual.size());
        assertTreeEquals(expected, actual.getRoot());
    }

    @Test(timeout = TIMEOUT)
    public void testAddDegenerateTree() {
        BST<Parrot> actual = new BST<>();
        BSTNode<Parrot> root = new BSTNode<>(new Parrot("MoonWalkingParrot", 0));

        root.setRight(new BSTNode<>(new Parrot("ExplodyParrot", 100)));
        root.getRight().setLeft(new BSTNode<>(new Parrot("FidgetParrot", 1)));
        root.getRight().getLeft().setRight(new BSTNode<>(new Parrot("DealWithItParrot", 99)));
        root.getRight().getLeft().getRight().setLeft(new BSTNode<>(new Parrot("MargaritaParrot", 2)));

        setTree(actual, root, 5);
        actual.add(new Parrot("EvilParrot", 98));

        BSTNode<Parrot> expected = new BSTNode<>(new Parrot("MoonWalkingParrot", 0));

        expected.setRight(new BSTNode<>(new Parrot("ExplodyParrot", 100)));
        expected.getRight().setLeft(new BSTNode<>(new Parrot("FidgetParrot", 1)));
        expected.getRight().getLeft().setRight(new BSTNode<>(new Parrot("DealWithItParrot", 99)));
        expected.getRight().getLeft().getRight().setLeft(new BSTNode<>(new Parrot("MargaritaParrot", 2)));
        expected.getRight().getLeft().getRight().getLeft().setRight(new BSTNode<>(new Parrot("EvilParrot", 98)));

        assertEquals("Incorrect size", 6, actual.size());
        assertTreeEquals(expected, actual.getRoot());
    }

    @Test(timeout = TIMEOUT)
    public void testAddLeftOnly() {
        BST<Parrot> actual = new BST<>();
        actual.add(new Parrot("ParrotMustache", 100));
        actual.add(new Parrot("BeretParrot", 99));
        actual.add(new Parrot("RevolutionParrot", 98));
        actual.add(new Parrot("BoredParrot", 97));
        actual.add(new Parrot("FastParrot", 96));
        actual.add(new Parrot("LoveParrot", 95));

        BSTNode<Parrot> expected = new BSTNode<>(new Parrot("ParrotMustache", 100));
        expected.setLeft(new BSTNode<>(new Parrot("BeretParrot", 99)));
        expected.getLeft().setLeft(new BSTNode<>(new Parrot("RevolutionParrot", 98)));
        expected.getLeft().getLeft().setLeft(new BSTNode<>(new Parrot("BoredParrot", 97)));
        expected.getLeft().getLeft().getLeft().setLeft(new BSTNode<>(new Parrot("FastParrot", 96)));
        expected.getLeft().getLeft().getLeft().getLeft().setLeft(new BSTNode<>(new Parrot("LoveParrot", 95)));

        assertEquals("Incorrect size", 6, actual.size());
        assertTreeEquals(expected, actual.getRoot());
    }

    @Test(timeout = TIMEOUT)
    public void testAddManyTimes() {
        BST<Parrot> actual = new BST<>();
        actual.add(new Parrot("AussieReverseCongaParrot", 50));
        actual.add(new Parrot("BlondeSassyParrot", 25));
        actual.add(new Parrot("BeerParrot", 0));
        actual.add(new Parrot("CongaParrot", 10));
        actual.add(new Parrot("FiestaParrot", 30));
        actual.add(new Parrot("GothParrot", 75));
        actual.add(new Parrot("HarryPotterParrot", 60));
        actual.add(new Parrot("ParrotCop", 55));

        BSTNode<Parrot> expected = buildGeneralTree();

        assertEquals("Incorrect size", SIZE_OF_GENERAL_TREE, actual.size());
        assertTreeEquals(expected, actual.getRoot());
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void testGetThrowsForMissingData() {
        BST<Parrot> bst = new BST<>();
        try {
            setTree(bst, buildGeneralTree(), SIZE_OF_GENERAL_TREE);
            bst.get(new Parrot("EvilParrot", 66));
        } catch (Exception e) {
            assertEquals("Incorrect exception type", NoSuchElementException.class, e.getClass());

            assertEquals("Get should not change the size of the tree", SIZE_OF_GENERAL_TREE, bst.size());

            assertTreeEquals(buildGeneralTree(), bst.getRoot());
            throw e;
        }
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void testGetThrowsForEmptyTree() {
        BST<Parrot> bst = new BST<>();
        try {
            bst.get(new Parrot("PartyParrot", 0));
        } catch (Exception e) {
            assertEquals("Incorrect exception type", NoSuchElementException.class, e.getClass());

            assertEquals("Get should not change the size of the tree", 0, bst.size());

            assertTreeEquals(null, bst.getRoot());
            throw e;
        }
    }

    // 3 points
    @Test(timeout = TIMEOUT)
    public void testGetGeneral() {
        BST<Parrot> bst = new BST<>();
        setTree(bst, buildGeneralTree(), SIZE_OF_GENERAL_TREE);

        Parrot search = new Parrot("CongaParrot", 10);
        Parrot found = bst.get(search);

        assertNotSame("Get did not return the value from the tree", search, found);
        assertEquals("Get returned incorrect data", search, found);

        // Ensure get does not destroy the tree
        assertEquals("Get should not change the size of the tree", SIZE_OF_GENERAL_TREE, bst.size());
        assertTreeEquals(buildGeneralTree(), bst.getRoot());
    }

    @Test(timeout = TIMEOUT)
    public void testClear() {
        BST<Parrot> actual = new BST<>();
        setTree(actual, buildGeneralTree(), SIZE_OF_GENERAL_TREE);

        actual.clear();
        assertEquals("Clear did not reset size to 0", 0, actual.size());
        assertTreeEquals(null, actual.getRoot());
    }

    @Test(timeout = TIMEOUT)
    public void testClearEmptyTree() {
        BST<Parrot> actual = new BST<>();

        actual.clear();
        assertEquals("Clear did not reset size to 0", 0, actual.size());
        assertTreeEquals(null, actual.getRoot());
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void testRemoveFromEmptyTree() {
        try {
            testTree.remove(new Parrot("I don't prefer parrots.", -20));
        } catch (NoSuchElementException e) {
            assertNull("Root is not null after remove() with element not in tree", testTree.getRoot());
            assertEquals("Size modified after remove() with element not in tree", 0, testTree.size());
            if (e.getClass().equals(NoSuchElementException.class)) {
                throw e;
            }
        }
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void testRemoveNotThere() {
        BSTNode<Parrot> root = buildGeneralTree();
        setTree(testTree, root, SIZE_OF_GENERAL_TREE);
        try {
            testTree.remove(new Parrot("I don't prefer parrots.", -20));
        } catch (NoSuchElementException e) {
            assertTreeEquals(root, testTree.getRoot());
            assertEquals("Size modified after remove() with element not in tree", 8, SIZE_OF_GENERAL_TREE);
            if (e.getClass().equals(NoSuchElementException.class)) {
                throw e;
            }
        }
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testRemoveNull() {
        BSTNode<Parrot> root = buildGeneralTree();
        setTree(testTree, root, SIZE_OF_GENERAL_TREE);
        try {
            testTree.remove(null);
        } catch (IllegalArgumentException e) {
            assertTreeEquals(root, testTree.getRoot());
            assertEquals("Size changed when removing null", 8, testTree.size());
            if (e.getClass().equals(IllegalArgumentException.class)) {
                throw e;
            }
        }
    }

    @Test(timeout = TIMEOUT)
    public void testRemoveUntilEmpty() {
        BSTNode<Parrot> exRoot = new BSTNode<>(beer);
        BSTNode<Parrot> root = new BSTNode<>(blonde);
        root.setLeft(new BSTNode<>(beer));
        setTree(testTree, root, 2);

        assertSame("Incorrect remove return", blonde, testTree.remove(new Parrot("BlondeSassyParrot", 25)));

        assertTreeEquals(exRoot, testTree.getRoot());
        assertEquals("Incorrect size", 1, testTree.size());

        assertSame("Incorrect remove return", beer, testTree.remove(new Parrot("BeerParrot", 0)));

        assertEquals("Incorrect size", 0, testTree.size());
        assertNull(testTree.getRoot());
    }

    @Test(timeout = TIMEOUT)
    public void testRemoveOneChildNotRoot() {
        BSTNode<Parrot> exRoot = new BSTNode<>(aussie);
        exRoot.setLeft(new BSTNode<>(blonde));
        exRoot.getLeft().setLeft(new BSTNode<>(conga));
        exRoot.getLeft().setRight(new BSTNode<>(fiesta));
        exRoot.setRight(new BSTNode<>(goth));
        exRoot.getRight().setLeft(new BSTNode<>(harry));
        exRoot.getRight().getLeft().setLeft(new BSTNode<>(parrot));
        BSTNode<Parrot> root = buildGeneralTree();
        setTree(testTree, root, SIZE_OF_GENERAL_TREE);
        assertSame("Incorrect remove return", beer, testTree.remove(new Parrot("BeerParrot", 0)));

        assertTreeEquals(exRoot, testTree.getRoot());
        assertEquals("Wrong size", 7, testTree.size());
        exRoot.setRight(exRoot.getRight().getLeft());

        assertSame("Incorrect remove return", goth, testTree.remove(new Parrot("GothParrot", 75)));

        assertTreeEquals(exRoot, testTree.getRoot());
        assertEquals("Wrong size", 6, testTree.size());
    }

    @Test(timeout = TIMEOUT)
    public void testRemoveOneChildRoot() {
        BSTNode<Parrot> exRoot = new BSTNode<>(goth);
        exRoot.setLeft(new BSTNode<>(harry));
        exRoot.getLeft().setLeft(new BSTNode<>(parrot));
        BSTNode<Parrot> root = buildGeneralTree();
        root.setLeft(null);
        setTree(testTree, root, 4);
        assertSame("Incorrect remove return", aussie, testTree.remove(new Parrot("AussieReverseCongaParrot", 50)));

        assertTreeEquals(exRoot, testTree.getRoot());
        assertEquals("Wrong size", 3, testTree.size());

        exRoot = exRoot.getLeft();

        assertSame("Incorrect remove return", goth, testTree.remove(new Parrot("GothParrot", 75)));

        assertTreeEquals(exRoot, testTree.getRoot());
        assertEquals("Wrong size", 2, testTree.size());
    }

    @Test(timeout = TIMEOUT)
    public void testRemoveTwoChildNotRoot1() {
        BSTNode<Parrot> exRoot = new BSTNode<>(aussie);
        exRoot.setLeft(new BSTNode<>(beer));
        exRoot.getLeft().setRight(new BSTNode<>(fiesta));
        exRoot.setRight(new BSTNode<>(goth));
        exRoot.getRight().setLeft(new BSTNode<>(harry));
        exRoot.getRight().getLeft().setLeft(new BSTNode<>(parrot));
        BSTNode<Parrot> root = buildGeneralTree();
        root.getLeft().getLeft().setRight(null);
        setTree(testTree, root, SIZE_OF_GENERAL_TREE - 1);
        assertSame("Incorrect remove return", blonde, testTree.remove(new Parrot("BlondeSassyParrot", 25)));

        assertTreeEquals(exRoot, testTree.getRoot());
        assertEquals("Wrong size", 6, testTree.size());
    }

    @Test(timeout = TIMEOUT)
    public void testRemoveTwoChildNotRoot2() {
        BSTNode<Parrot> exRoot = new BSTNode<>(aussie);
        exRoot.setLeft(new BSTNode<>(twenty));
        exRoot.getLeft().setLeft(new BSTNode<>(beer));
        exRoot.getLeft().getLeft().setRight(new BSTNode<>(conga));
        exRoot.getLeft().setRight(new BSTNode<>(fiesta));
        exRoot.setRight(new BSTNode<>(goth));
        exRoot.getRight().setLeft(new BSTNode<>(harry));
        exRoot.getRight().getLeft().setLeft(new BSTNode<>(parrot));
        BSTNode<Parrot> root = buildGeneralTree();
        root.getLeft().getLeft().getRight().setRight(new BSTNode<>(twenty));
        setTree(testTree, root, SIZE_OF_GENERAL_TREE + 1);
        assertSame("Incorrect remove return", blonde, testTree.remove(new Parrot("BlondeSassyParrot", 25)));

        assertTreeEquals(exRoot, testTree.getRoot());
        assertEquals("Wrong size", 8, testTree.size());
    }

    @Test(timeout = TIMEOUT)
    public void testRemoveTwoChildRoot() {
        BSTNode<Parrot> exRoot = new BSTNode<>(fiesta);
        exRoot.setLeft(new BSTNode<>(blonde));
        exRoot.getLeft().setLeft(new BSTNode<>(beer));
        exRoot.getLeft().getLeft().setRight(new BSTNode<>(conga));
        exRoot.setRight(new BSTNode<>(goth));
        exRoot.getRight().setLeft(new BSTNode<>(harry));
        exRoot.getRight().getLeft().setLeft(new BSTNode<>(parrot));
        BSTNode<Parrot> root = buildGeneralTree();
        setTree(testTree, root, SIZE_OF_GENERAL_TREE);
        assertSame("Incorrect remove return", aussie, testTree.remove(new Parrot("AussieReverseCongaParrot", 50)));

        assertTreeEquals(exRoot, testTree.getRoot());
        assertEquals("Wrong size", 7, testTree.size());

        exRoot.getLeft().setRight(exRoot.getRight());
        exRoot = exRoot.getLeft();

        assertSame("Incorrect remove return", fiesta, testTree.remove(new Parrot("FiestaParrot", 30)));

        assertTreeEquals(exRoot, testTree.getRoot());
        assertEquals("Wrong size", 6, testTree.size());
    }

    @Test(timeout = TIMEOUT)
    public void testContains() {
        BSTNode<Parrot> root = buildGeneralTree();
        setTree(testTree, root, SIZE_OF_GENERAL_TREE);
        assertEquals("Incorrect boolean", true, testTree.contains(new Parrot("AussieReverseCongaParrot", 50)));
        assertEquals("Incorrect boolean", true, testTree.contains(new Parrot("CongaParrot", 10)));
        assertEquals("Incorrect boolean", true, testTree.contains(new Parrot("ParrotCop", 55)));
        assertEquals("Incorrect boolean", false, testTree.contains(new Parrot("I don't prefer parrots", -50)));
        assertEquals("Incorrect boolean", false, testTree.contains(new Parrot("I really don't prefer parrots", -150)));
        assertTreeEquals(root, testTree.getRoot());
        assertEquals(8, testTree.size());
    }

    @Test(timeout = TIMEOUT)
    public void testContainsEmpty() {
        assertEquals("Incorrect boolean", false, testTree.contains(new Parrot("AussieReverseCongaParrot", 50)));
        assertNull(testTree.getRoot());
        assertEquals(0, testTree.size());
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testContainsNull() {
        BSTNode<Parrot> root = buildGeneralTree();
        setTree(testTree, root, SIZE_OF_GENERAL_TREE);
        try {
            assertEquals("Incorrect boolean", false, testTree.contains(null));
        } catch (IllegalArgumentException e) {
            if (e.getClass().equals(IllegalArgumentException.class)) {
                assertTreeEquals(root, testTree.getRoot());
                assertEquals("Size was changed in contains", 8, testTree.size());
                throw e;
            }
        }
    }

    @Test(timeout = TIMEOUT)
    public void preOrderTraversal() {
        Parrot[] parrotArr = {aussie, blonde, beer, conga, fiesta, goth, harry, parrot};
        List<Parrot> parrotActual = new ArrayList<>(Arrays.asList(parrotArr));

        BSTNode<Parrot> root = buildGeneralTree();
        setTree(testTree, root, SIZE_OF_GENERAL_TREE);

        assertListEquals(parrotActual, testTree.preorder());
        assertTreeEquals(root, testTree.getRoot());
        assertEquals("Size changed in traversal", 8, testTree.size());
    }

    @Test(timeout = TIMEOUT)
    public void preOrderTraversalEmpty() {
        List<Parrot> empty = new ArrayList<>();
        assertListEquals(empty, testTree.preorder());
        assertNull(testTree.getRoot());
        assertEquals("Size changed in traversal", 0, testTree.size());
    }

    @Test(timeout = TIMEOUT)
    public void inOrderTraversal() {
        Parrot[] parrotArr = {beer, conga, blonde, fiesta, aussie, parrot, harry, goth};
        List<Parrot> parrotActual = new ArrayList<>(Arrays.asList(parrotArr));

        BSTNode<Parrot> root = buildGeneralTree();
        setTree(testTree, root, SIZE_OF_GENERAL_TREE);

        assertListEquals(parrotActual, testTree.inorder());
        assertTreeEquals(root, testTree.getRoot());
        assertEquals("Size changed in traversal", 8, testTree.size());
    }

    @Test(timeout = TIMEOUT)
    public void inOrderTraversalEmpty() {
        List<Parrot> empty = new ArrayList<>();
        assertListEquals(empty, testTree.inorder());
        assertNull(testTree.getRoot());
        assertEquals("Size changed in traversal", 0, testTree.size());
    }

    @Test(timeout = TIMEOUT)
    public void postOrderTraversal() {
        Parrot[] parrotArr = {conga, beer, fiesta, blonde, parrot, harry, goth, aussie};
        List<Parrot> parrotActual = new ArrayList<>(Arrays.asList(parrotArr));

        BSTNode<Parrot> root = buildGeneralTree();
        setTree(testTree, root, SIZE_OF_GENERAL_TREE);

        assertListEquals(parrotActual, testTree.postorder());
        assertTreeEquals(root, testTree.getRoot());
        assertEquals("Size changed in traversal", 8, testTree.size());
    }

    @Test(timeout = TIMEOUT)
    public void postOrderTraversalEmpty() {
        List<Parrot> empty = new ArrayList<>();
        assertListEquals(empty, testTree.postorder());
        assertNull(testTree.getRoot());
        assertEquals("Size changed in traversal", 0, testTree.size());
    }

    @Test(timeout = TIMEOUT)
    public void levelOrderTraversal() {
        Parrot[] parrotArr = {aussie, blonde, goth, beer, fiesta, harry, conga, parrot};
        List<Parrot> parrotActual = new ArrayList<>(Arrays.asList(parrotArr));

        BSTNode<Parrot> root = buildGeneralTree();
        setTree(testTree, root, SIZE_OF_GENERAL_TREE);

        assertListEquals(parrotActual, testTree.levelorder());
        assertTreeEquals(root, testTree.getRoot());
        assertEquals("Size changed in traversal", 8, testTree.size());
    }

    @Test(timeout = TIMEOUT)
    public void levelOrderTraversalEmpty() {
        List<Parrot> empty = new ArrayList<>();
        assertListEquals(empty, testTree.levelorder());
        assertNull(testTree.getRoot());
        assertEquals("Size changed in traversal", 0, testTree.size());
    }

    @Test(timeout = TIMEOUT)
    public void testHeight() {
        BSTNode<Parrot> root = buildGeneralTree();
        setTree(testTree, root, SIZE_OF_GENERAL_TREE);
        assertEquals("Height is incorrect", 3, testTree.height());
        assertTreeEquals(root, testTree.getRoot());
        assertEquals("Size changed in height", 8, testTree.size());
    }

    @Test(timeout = TIMEOUT)
    public void testHeightEmpty() {
        setTree(testTree, null, 0);
        assertEquals("Height of empty tree not -1", -1, testTree.height());
        assertEquals("Size changed in height", 0, testTree.size());
    }

    /**
     * The best data class.
     */
    private static class Parrot implements Comparable<Parrot> {

        final String parrotName;
        final int parrotNumber;

        /**
         * Constructor for PartyParrots
         *
         * @param name   The name of the parrot
         * @param number An arbitrary number for the parrot. This number is used
         *               directly when comparing parrots such that a parrot with
         *               a lower number is considered less than a parrot with
         *               a higher number. (In reality, of course, all parrots
         *               are equally as excellent).
         */
        Parrot(String name, int number) {
            parrotName = name;
            parrotNumber = number;
        }

        @Override
        public int compareTo(Parrot that) {
            return 2 * (this.parrotNumber - that.parrotNumber);
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof Parrot) {
                Parrot that = (Parrot) obj;
                return this.parrotName.equals(that.parrotName) && this.parrotNumber == that.parrotNumber;
            } else {
                return false;
            }
        }

        @Override
        public String toString() {
            return parrotName + " (" + parrotNumber + ")";
        }
    }

}
