import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.*;

import static org.junit.Assert.*;

public class AVLTests {

    private static final long TIMEOUT = 200;

    private IceCream chocolate = new IceCream("Chocolate", 50);
    private IceCream frenchVanilla = new IceCream("FrenchVanilla", 22);
    private IceCream fishFood = new IceCream("FishFood", 65);
    private IceCream chocolateChip = new IceCream("ChocolateChip", 3);
    private IceCream mint = new IceCream("Mint", 30);
    private IceCream orange = new IceCream("Orange", 2);
    private IceCream malt = new IceCream("Malt", 55);
    private IceCream strawberry = new IceCream("Strawberry", 70);
    private IceCream chocolateBrownie = new IceCream("ChocolateBrownie", 100);

    private AVL<NewInteger> avl;
    private AVLNode<NewInteger> root;
    private TestAVL<NewInteger> tAVL;
    private List<NewInteger> testList;
    private List<NewInteger> path;
    private NewInteger[] expectedV;
    private int[] expectedH;
    private int[] expectedBF;
    private List<NewInteger> v;
    private List<Integer> h;
    private List<Integer> bF;

    @Before
    public void setUp() {
        avl = new AVL<>();
        testList = new LinkedList<>();
        path = new LinkedList<>();
    }

    /**
     * Reflectively sets a AVL's contents with the provided values
     *
     * @param tree The tree to have its contents set
     * @param root The new root of the tree
     * @param size The new size of the tree
     * @param <T>  The type of data the tree stores
     */
    private static <T extends Comparable<T>> void setTree(
            AVL<T> tree, AVLNode<T> root, int size) {

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
     * Recursively checks whether or not a AVL's contents match up with the
     * structure of an expected root. If the trees are not equal, an
     * AssertionError will be thrown.
     *
     * THIS METHOD DOESN'T CHECK THE SIZE OF THE AVL.
     *
     * @param expectedRoot The expected root of the AVL
     * @param actualRoot   The actual root of the AVL
     * @param <T>          The type of data the tree stores
     */
    private static <T extends Comparable<T>> void assertTreeEquals(
            AVLNode<T> expectedRoot, AVLNode<T> actualRoot) {

        if (expectedRoot == null && actualRoot != null) {
            fail("Actual tree has an unexpected node "
                    + "(data = " + actualRoot.getData() + ")");
        } else if (actualRoot == null && expectedRoot != null) {
            fail("Actual tree is missing a node"
                    + "(expected data = " + expectedRoot.getData() + ")");
        } else if (expectedRoot != null) {
            Assert.assertEquals("Tree node contained incorrect data",
                    expectedRoot.getData(), actualRoot.getData());
            Assert.assertEquals(expectedRoot.getData()
                            + " contained incorrect balance factor",
                    expectedRoot.getBalanceFactor(),
                    actualRoot.getBalanceFactor());
            Assert.assertEquals(expectedRoot.getData()
                            + " contained incorrect height",
                    expectedRoot.getHeight(), actualRoot.getHeight());

            assertTreeEquals(expectedRoot.getLeft(), actualRoot.getLeft());
            assertTreeEquals(expectedRoot.getRight(), actualRoot.getRight());
        }
    }

    /**
     * Builds a general balanced AVL
     *
     * @return The root of the AVL
     *
     *                                  50 scoops of Chocolate
     *                              /                              \
     *                             /                                \
     *         22 scoops of FrenchVanilla                       65 scoops of FishFood
     *                      /       \                                  /             \
     *                     /         \                                /               \
     *  3 scoops of ChocolateChip     30 scoops of Mint      55 scoops of Malt        70 scoops of Strawberry
     *                  /                                                                   \
     *                 /                                                                     \
     *      2 scoops of Orange                                                    100 scoops of ChocolateBrownie
     *
     *
     *
     */
    private AVLNode<IceCream> buildGeneralBalancedAVL() {

        AVLNode<IceCream> root = new AVLNode<>(chocolate);
        root.setHeight(3);
        root.setBalanceFactor(0);
        root.setLeft(new AVLNode<>(frenchVanilla));
        root.getLeft().setHeight(2);
        root.getLeft().setBalanceFactor(1);
        root.setRight(new AVLNode<>(fishFood));
        root.getRight().setHeight(2);
        root.getRight().setBalanceFactor(-1);
        //Sets the left half of the AVL
        root.getLeft().setLeft(new AVLNode<>(chocolateChip));
        root.getLeft().getLeft().setHeight(1);
        root.getLeft().getLeft().setBalanceFactor(1);
        root.getLeft().setRight(new AVLNode<>(mint));
        root.getLeft().getRight().setHeight(0);
        root.getLeft().getRight().setBalanceFactor(0);
        root.getLeft().getLeft().setLeft(new AVLNode<>(orange));
        root.getLeft().getLeft().getLeft().setHeight(0);
        root.getLeft().getLeft().getLeft().setBalanceFactor(0);
        //Sets the right half of the AVL
        root.getRight().setLeft(new AVLNode<>(malt));
        root.getRight().getLeft().setHeight(0);
        root.getRight().getLeft().setBalanceFactor(0);
        root.getRight().setRight(new AVLNode<>(strawberry));
        root.getRight().getRight().setHeight(1);
        root.getRight().getRight().setBalanceFactor(-1);
        root.getRight().getRight().setRight(new AVLNode<>(chocolateBrownie));
        root.getRight().getRight().getRight().setHeight(0);
        root.getRight().getRight().getRight().setBalanceFactor(0);

        return root;
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void testRemoveEmptyAndOneNode() {
        AVL<IceCream> avl = new AVL<>();
        try {
            avl.remove(new IceCream("Banana", 10));
        } catch (Exception e) {
            assertEquals("Incorrect exception type",
                    NoSuchElementException.class,
                    e.getClass());

            Assert.assertEquals("Removing from an empty tree should not "
                            + "change the tree's size",
                    0, avl.size());
            assertTreeEquals(null, avl.getRoot());

            setTree(avl, new AVLNode<>(chocolate), 1);
            try {
                avl.remove(new IceCream("Salted Caramel", 88));
            } catch (NoSuchElementException e1) {
                assertEquals("Incorrect exception type",
                        NoSuchElementException.class,
                        e1.getClass());

                Assert.assertEquals(
                        "Removing from a tree where the element is not found"
                                + " should not change the tree's size",
                        1, avl.size());
                assertTreeEquals(new AVLNode<>(chocolate), avl.getRoot());

                throw e1;
            }
            throw e;
        }
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void testRemoveNotFoundGeneral() {
        AVL<IceCream> avl = new AVL<>();
        setTree(avl, buildGeneralBalancedAVL(), 9);
        try {
            avl.remove(new IceCream("Banana", 10));
        } catch (NoSuchElementException e) {
            assertEquals("Incorrect exception type",
                    NoSuchElementException.class,
                    e.getClass());

            Assert.assertEquals(
                    "Removing from a tree where the element is not found"
                            + " should not change the tree's size",
                    9, avl.size());
            assertTreeEquals(buildGeneralBalancedAVL(), avl.getRoot());

            try {
                avl.remove(new IceCream("Salted Caramel", 88));
            } catch (NoSuchElementException e1) {
                assertEquals("Incorrect exception type",
                        NoSuchElementException.class,
                        e1.getClass());

                Assert.assertEquals(
                        "Removing from a tree where the element is not found"
                                + " should not change the tree's size",
                        9, avl.size());
                assertTreeEquals(buildGeneralBalancedAVL(), avl.getRoot());
                throw e1;
            }

            throw e;
        }
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testRemoveNullData() {
        AVL<IceCream> avl = new AVL<>();
        setTree(avl, buildGeneralBalancedAVL(), 9);
        try {
            avl.remove(null);
        } catch (IllegalArgumentException e) {
            assertEquals("Incorrect exception type",
                    IllegalArgumentException.class,
                    e.getClass());

            Assert.assertEquals("Removing from a tree where the element you "
                            + "are trying to remove is null should not change "
                            + "the tree's size",
                    9, avl.size());
            assertTreeEquals(buildGeneralBalancedAVL(), avl.getRoot());
            throw e;
        }
    }

    @Test (timeout = TIMEOUT)
    public void testRemoveUntilEmptyNoRotations() {
        AVL<IceCream> actual = new AVL<>();
        setTree(actual, buildGeneralBalancedAVL(), 9);

        AVLNode<IceCream> expected = buildGeneralBalancedAVL();

        //Two child case
        assertSame("Did not return correct data from tree removal",
                frenchVanilla, actual.remove(
                        new IceCream("FrenchVanilla", 22)));
        expected.getLeft().setData(chocolateChip);
        expected.getLeft().setLeft(expected.getLeft().getLeft().getLeft());
        expected.getLeft().setHeight(1);
        expected.getLeft().setBalanceFactor(0);
        expected.setBalanceFactor(-1);
        Assert.assertEquals("Removing does not decrement size",
                8, actual.size());
        assertTreeEquals(expected, actual.getRoot());

        //One child case
        assertSame("Did not return correct data from tree removal",
                strawberry, actual.remove(new IceCream("Strawberry", 70)));
        expected.getRight().setRight(expected.getRight().getRight().getRight());
        expected.getRight().setHeight(1);
        expected.getRight().setBalanceFactor(0);
        expected.setHeight(2);
        expected.setBalanceFactor(0);
        Assert.assertEquals("Removing does not decrement size",
                7, actual.size());
        assertTreeEquals(expected, actual.getRoot());

        //Root two child
        assertSame("Did not return correct data from tree removal",
                chocolate, actual.remove(new IceCream("Chocolate", 50)));
        expected.getLeft().setRight(null);
        expected.getLeft().setHeight(1);
        expected.getLeft().setBalanceFactor(1);
        expected.setData(mint);
        Assert.assertEquals("Removing does not decrement size",
                6, actual.size());
        assertTreeEquals(expected, actual.getRoot());

        //No child case
        assertSame("Did not return correct data from tree removal",
                malt, actual.remove(new IceCream("Malt", 55)));
        expected.getRight().setLeft(null);
        expected.getRight().setBalanceFactor(-1);
        Assert.assertEquals("Removing does not decrement size",
                5, actual.size());
        assertTreeEquals(expected, actual.getRoot());

        //Two child case
        assertSame("Did not return correct data from tree removal",
                chocolateChip, actual.remove(new IceCream("ChocolateChip", 3)));
        expected.getLeft().setLeft(null);
        expected.getLeft().setData(orange);
        expected.getLeft().setHeight(0);
        expected.getLeft().setBalanceFactor(0);
        expected.setBalanceFactor(-1);
        Assert.assertEquals("Removing does not decrement size",
                4, actual.size());
        assertTreeEquals(expected, actual.getRoot());

        //Leaf node
        assertSame("Did not return correct data from tree removal",
                chocolateBrownie, actual.remove(
                        new IceCream("ChocolateBrownie", 100)));
        expected.getRight().setRight(null);
        expected.getRight().setHeight(0);
        expected.getRight().setBalanceFactor(0);
        expected.setBalanceFactor(0);
        expected.setHeight(1);
        Assert.assertEquals("Removing does not decrement size",
                3, actual.size());
        assertTreeEquals(expected, actual.getRoot());

        //Root two child with immediate predecessor
        assertSame("Did not return correct data from tree removal",
                mint, actual.remove(new IceCream("Mint", 30)));
        expected.setLeft(expected.getLeft().getLeft());
        expected.setBalanceFactor(-1);
        expected.setData(orange);
        Assert.assertEquals("Removing does not decrement size",
                2, actual.size());
        assertTreeEquals(expected, actual.getRoot());

        //Root one child
        assertSame("Did not return correct data from tree removal",
                orange, actual.remove(new IceCream("Orange", 2)));
        expected.setRight(null);
        expected.setBalanceFactor(0);
        expected.setHeight(0);
        expected.setData(fishFood);
        Assert.assertEquals("Removing does not decrement size",
                1, actual.size());
        assertTreeEquals(expected, actual.getRoot());

        //Root no children
        assertSame("Did not return correct data from tree removal",
                fishFood, actual.remove(new IceCream("FishFood", 65)));
        expected = null;
        Assert.assertEquals("Removing does not decrement size",
                0, actual.size());
        assertTreeEquals(expected, actual.getRoot());
    }


    /**
     * Before
     *                                              50 scoops of Chocolate
     *                                            /                        \
     *                                           /                          \
     *                      20 scoops of ButterBrittle                      70 scoops of CookiesAndCream
     *                    /                     \                                      /                 \
     *                  /                       \                                     /                   \
     *        10 scoops of MooseTracks      40 scoops of RockyRoad       60 scoops of ButterPecan      80 scoops of BlueMoon
     *                                                  |
     *                                                   |
     *                                                  45 scoops of Mint
     *
     *
     * After removing 20 scoops of ButterBrittle - Left Rotation
     *
     *                                              50 scoops of Chocolate
     *                                            /                        \
     *                                           /                          \
     *                    40 scoops of RockyRoad                                 70 scoops of CookiesAndCream
     *                    /                     \                                      /                 \
     *                  /                       \                                     /                   \
     *        10 scoops of MooseTracks      45 scoops of Mint       60 scoops of ButterPecan      80 scoops of BlueMoon
     *
     *
     */
    @Test(timeout = TIMEOUT)
    public void testLeftRotation() {
        AVL<IceCream> actual = new AVL<>();

        AVLNode<IceCream> root = new AVLNode<>(new IceCream("Chocolate", 50));
        root.setHeight(3);
        root.setBalanceFactor(1);

        //Set left side of tree
        root.setLeft(new AVLNode<>(new IceCream("ButterBrittle", 20)));
        root.getLeft().setHeight(2);
        root.getLeft().setBalanceFactor(-1);

        root.getLeft().setLeft(new AVLNode<>(new IceCream("MooseTracks", 10)));
        root.getLeft().getLeft().setHeight(0);
        root.getLeft().getLeft().setBalanceFactor(0);

        root.getLeft().setRight(new AVLNode<>(new IceCream("RockyRoad", 40)));
        root.getLeft().getRight().setHeight(1);
        root.getLeft().getRight().setBalanceFactor(-1);

        root.getLeft().getRight().setRight(new AVLNode<>(
                new IceCream("Mint", 45)));
        root.getLeft().getRight().getRight().setHeight(0);
        root.getLeft().getRight().getRight().setBalanceFactor(0);

        //Set right side of tree
        root.setRight(new AVLNode<>(new IceCream("CookiesAndCream", 70)));
        root.getRight().setHeight(1);
        root.getRight().setBalanceFactor(0);

        root.getRight().setLeft(new AVLNode<>(new IceCream("ButterPecan", 60)));
        root.getRight().getLeft().setHeight(0);
        root.getRight().getLeft().setBalanceFactor(0);

        root.getRight().setRight(new AVLNode<>(new IceCream("BlueMoon", 80)));
        root.getRight().getRight().setHeight(0);
        root.getRight().getRight().setBalanceFactor(0);

        setTree(actual, root, 8);

        AVL<IceCream> expected = new AVL<>();

        AVLNode<IceCream> expectedRoot = new AVLNode<>(
                new IceCream("Chocolate", 50));
        expectedRoot.setHeight(2);
        expectedRoot.setBalanceFactor(0);

        //Set left side of tree
        expectedRoot.setLeft(new AVLNode<>(new IceCream("RockyRoad", 40)));
        expectedRoot.getLeft().setHeight(1);
        expectedRoot.getLeft().setBalanceFactor(0);

        expectedRoot.getLeft().setLeft(new AVLNode<>(
                new IceCream("MooseTracks", 10)));
        expectedRoot.getLeft().getLeft().setHeight(0);
        expectedRoot.getLeft().getLeft().setBalanceFactor(0);

        expectedRoot.getLeft().setRight(new AVLNode<>(
                new IceCream("Mint", 45)));
        expectedRoot.getLeft().getRight().setHeight(0);
        expectedRoot.getLeft().getRight().setBalanceFactor(0);

        //Set right side of tree
        expectedRoot.setRight(new AVLNode<>(
                new IceCream("CookiesAndCream", 70)));
        expectedRoot.getRight().setHeight(1);
        expectedRoot.getRight().setBalanceFactor(0);

        expectedRoot.getRight().setLeft(new AVLNode<>(
                new IceCream("ButterPecan", 60)));
        expectedRoot.getRight().getLeft().setHeight(0);
        expectedRoot.getRight().getLeft().setBalanceFactor(0);

        expectedRoot.getRight().setRight(new AVLNode<>(
                new IceCream("BlueMoon", 80)));
        expectedRoot.getRight().getRight().setHeight(0);
        expectedRoot.getRight().getRight().setBalanceFactor(0);

        actual.remove(new IceCream("ButterBrittle", 20));

        Assert.assertEquals(
                "Removing when size is 8 did not reset the size to 7",
                7, actual.size());
        assertTreeEquals(expectedRoot, actual.getRoot());

    }

    /**
     * Before Removal / Rotation
     *                              62 scoops of Chocolate
     *                              /                       \
     *                             /                         \
     *        34 scoops of CookiesAndCream                  81 scoops of ButterBrittle
     *                      \                                    /
     *                       \                                  /
     *                57 scoops of BlueMoon       79 scoops of MooseTracks
     *                                                  /               \
     *                                                 /                 \
     *                                  64 scoops of Coffee             80 scoops of CookieDough
     *
     * After Removal of 80 scoops of CookieDough -- Right Rotation
     *
     *                                62 scoops of Chocolate
     *                              /                         \
     *                             /                           \
     *          34 scoops of CookiesAndCream                     79 scoops of MooseTracks
     *                             \                            /                         \
     *                             \                           /                           \
     *                       57 scoops of BlueMoon     64 scoops of Coffeee         81 scoops of ButterBrittle
     *
     */
    @Test (timeout = TIMEOUT)
    public void testRightRotation() {
        AVL<IceCream> actual = new AVL<>();

        //creates the before tree described above
        AVLNode<IceCream> root = new AVLNode<>(new IceCream("Chocolate", 62));
        root.setHeight(3);
        root.setBalanceFactor(-1);
        //Sets the left half of the AVL
        root.setLeft(new AVLNode<>(new IceCream("CookiesAndCream", 34)));
        root.getLeft().setHeight(1);
        root.getLeft().setBalanceFactor(-1);

        root.getLeft().setRight(new AVLNode<>(new IceCream("BlueMoon", 57)));
        root.getLeft().getRight().setHeight(0);
        root.getLeft().getRight().setBalanceFactor(0);
        //Sets the right half of the AVL
        root.setRight(new AVLNode<>(new IceCream("ButterBrittle", 81)));
        root.getRight().setHeight(2);
        root.getRight().setBalanceFactor(1);

        root.getRight().setLeft(new AVLNode<>(new IceCream("MooseTracks", 79)));
        root.getRight().getLeft().setHeight(1);
        root.getRight().getLeft().setBalanceFactor(0);

        root.getRight().getLeft().setRight(new AVLNode<>(
                new IceCream("CookieDough", 80)));
        root.getRight().getLeft().getRight().setHeight(0);
        root.getRight().getLeft().getRight().setBalanceFactor(0);

        root.getRight().getLeft().setLeft(new AVLNode<>(
                new IceCream("Coffee", 64)));
        root.getRight().getLeft().getLeft().setHeight(0);
        root.getRight().getLeft().getLeft().setBalanceFactor(0);

        setTree(actual, root, 7);

        //creates the expected tree
        AVL<IceCream> expected = new AVL<>();

        AVLNode<IceCream> expectedRoot = new AVLNode<>(
                new IceCream("Chocolate", 62));
        expectedRoot.setHeight(2);
        expectedRoot.setBalanceFactor(0);

        //Sets the left half of the AVL
        expectedRoot.setLeft(new AVLNode<>(
                new IceCream("CookiesAndCream", 34)));
        expectedRoot.getLeft().setHeight(1);
        expectedRoot.getLeft().setBalanceFactor(-1);

        expectedRoot.getLeft().setRight(new AVLNode<>(
                new IceCream("BlueMoon", 57)));
        expectedRoot.getLeft().getRight().setHeight(0);
        expectedRoot.getLeft().getRight().setBalanceFactor(0);

        //Sets the right half of the AVL
        expectedRoot.setRight(new AVLNode<>(
                new IceCream("MooseTracks", 79)));
        expectedRoot.getRight().setHeight(1);
        expectedRoot.getRight().setBalanceFactor(0);

        expectedRoot.getRight().setLeft(new AVLNode<>(
                new IceCream("Coffee", 64)));
        expectedRoot.getRight().getLeft().setHeight(0);
        expectedRoot.getRight().getLeft().setBalanceFactor(0);

        expectedRoot.getRight().setRight(new AVLNode<>(
                new IceCream("ButterBrittle", 81)));
        expectedRoot.getRight().getRight().setHeight(0);
        expectedRoot.getRight().getRight().setBalanceFactor(0);

        actual.remove(new IceCream("CookieDough", 80));

        Assert.assertEquals(
                "Removing when size is 7 did not reset the size to 6",
                6, actual.size());
        assertTreeEquals(expectedRoot, actual.getRoot());
    }

    /**
     * Before Removal / Rotation
     *                              62 scoops of Chocolate
     *                              /                       \
     *                             /                         \
     *        34 scoops of CookiesAndCream                  81 scoops of ButterBrittle
     *                      \                                    /                      \
     *                       \                                  /                        \
     *                57 scoops of BlueMoon       79 scoops of MooseTracks          99 scoops of RockyRoad
     *                                                  /               \
     *                                                 /                 \
     *                                  64 scoops of Coffee             80 scoops of CookieDough
     *
     * After Removal of 34 scoops of CookiesAndCream -- Right Left Rotation
     *
     *                              79 scoops of MooseTracks
     *                              /                       \
     *                             /                         \
     *          62 scoops of Chocolate                         81 scoops of ButterBrittle
     *           /                 \                            /                         \
     *          /                  \                           /                           \
     *  57 scoops of BlueMoon     64 scoops of Coffee     80 scoops of CookieDough         99 scoops of RockyRoad
     *
     *
     */
    @Test(timeout = TIMEOUT)
    public void testRightLeftRotation() {
        AVL<IceCream> actual = new AVL<>();

        //creates the before tree described above
        AVLNode<IceCream> root = new AVLNode<>(new IceCream("Chocolate", 62));
        root.setHeight(3);
        root.setBalanceFactor(-1);
        //Sets the left half of the AVL
        root.setLeft(new AVLNode<>(new IceCream("CookiesAndCream", 34)));
        root.getLeft().setHeight(1);
        root.getLeft().setBalanceFactor(-1);

        root.getLeft().setRight(new AVLNode<>(new IceCream("BlueMoon", 57)));
        root.getLeft().getRight().setHeight(0);
        root.getLeft().getRight().setBalanceFactor(0);
        //Sets the right half of the AVL
        root.setRight(new AVLNode<>(new IceCream("ButterBrittle", 81)));
        root.getRight().setHeight(2);
        root.getRight().setBalanceFactor(1);

        root.getRight().setLeft(new AVLNode<>(new IceCream("MooseTracks", 79)));
        root.getRight().getLeft().setHeight(1);
        root.getRight().getLeft().setBalanceFactor(0);

        root.getRight().setRight(new AVLNode<>(new IceCream("RockyRoad", 99)));
        root.getRight().getRight().setHeight(0);
        root.getRight().getRight().setBalanceFactor(0);

        root.getRight().getLeft().setRight(new AVLNode<>(
                new IceCream("CookieDough", 80)));
        root.getRight().getLeft().getRight().setHeight(0);
        root.getRight().getLeft().getRight().setBalanceFactor(0);

        root.getRight().getLeft().setLeft(new AVLNode<>(
                new IceCream("Coffee", 64)));
        root.getRight().getLeft().getLeft().setHeight(0);
        root.getRight().getLeft().getLeft().setBalanceFactor(0);

        setTree(actual, root, 8);

        //creates the expected tree
        AVL<IceCream> expected = new AVL<>();

        AVLNode<IceCream> expectedRoot = new AVLNode<>(
                new IceCream("MooseTracks", 79));
        expectedRoot.setHeight(2);
        expectedRoot.setBalanceFactor(0);

        //Sets the left half of the AVL
        expectedRoot.setLeft(new AVLNode<>(new IceCream("Chocolate", 62)));
        expectedRoot.getLeft().setHeight(1);
        expectedRoot.getLeft().setBalanceFactor(0);

        expectedRoot.getLeft().setLeft(new AVLNode<>(
                new IceCream("BlueMoon", 57)));
        expectedRoot.getLeft().getLeft().setHeight(0);
        expectedRoot.getLeft().getLeft().setBalanceFactor(0);

        expectedRoot.getLeft().setRight(new AVLNode<>(
                new IceCream("Coffee", 64)));
        expectedRoot.getLeft().getRight().setHeight(0);
        expectedRoot.getLeft().getRight().setBalanceFactor(0);

        //Sets the right half of the AVL
        expectedRoot.setRight(new AVLNode<>(
                new IceCream("ButterBrittle", 81)));
        expectedRoot.getRight().setHeight(1);
        expectedRoot.getRight().setBalanceFactor(0);

        expectedRoot.getRight().setLeft(new AVLNode<>(
                new IceCream("CookieDough", 80)));
        expectedRoot.getRight().getLeft().setHeight(0);
        expectedRoot.getRight().getLeft().setBalanceFactor(0);

        expectedRoot.getRight().setRight(new AVLNode<>(
                new IceCream("RockyRoad", 99)));
        expectedRoot.getRight().getRight().setHeight(0);
        expectedRoot.getRight().getRight().setBalanceFactor(0);

        actual.remove(new IceCream("CookiesAndCream", 34));

        Assert.assertEquals(
                "Removing when size is 8 did not reset the size to 7",
                7, actual.size());
        assertTreeEquals(expectedRoot, actual.getRoot());
    }

    /**
     * Before Removal / Rotation
     *                              62 scoops of Chocolate
     *                              /                       \
     *                             /                         \
     *        34 scoops of CookiesAndCream                  81 scoops of ButterBrittle
     *                      \                                    /
     *                       \                                  /
     *                57 scoops of BlueMoon       79 scoops of MooseTracks
     *                                                  /               \
     *                                                 /                 \
     *                                  64 scoops of Coffee             80 scoops of CookieDough
     *
     * After Removal of 34 scoops of Coffee -- Left Right Rotation
     *
     *                               62 scoops of Chocolate
     *                              /                       \
     *                             /                         \
     *          62 scoops of CookiesAndCream                    80 scoops of CookieDough
     *                             \                            /                       \
     *                             \                           /                         \
     *                       57 scoops of BlueMoon     79 scoops of MooseTracks      81 scoops of ButterBrittle
     *
     *
     */
    @Test(timeout = TIMEOUT)
    public void testLeftRightRotation() {
        AVL<IceCream> actual = new AVL<>();

        //creates the before tree described above
        AVLNode<IceCream> root = new AVLNode<>(new IceCream("Chocolate", 62));
        root.setHeight(3);
        root.setBalanceFactor(-1);

        //Sets the left half of the AVL
        root.setLeft(new AVLNode<>(new IceCream("CookiesAndCream", 34)));
        root.getLeft().setHeight(1);
        root.getLeft().setBalanceFactor(-1);

        root.getLeft().setRight(new AVLNode<>(new IceCream("BlueMoon", 57)));
        root.getLeft().getRight().setHeight(0);
        root.getLeft().getRight().setBalanceFactor(0);

        //Sets the right half of the AVL
        root.setRight(new AVLNode<>(new IceCream("ButterBrittle", 81)));
        root.getRight().setHeight(2);
        root.getRight().setBalanceFactor(1);

        root.getRight().setLeft(new AVLNode<>(new IceCream("MooseTracks", 79)));
        root.getRight().getLeft().setHeight(1);
        root.getRight().getLeft().setBalanceFactor(0);

        root.getRight().getLeft().setRight(new AVLNode<>(
                new IceCream("CookieDough", 80)));
        root.getRight().getLeft().getRight().setHeight(0);
        root.getRight().getLeft().getRight().setBalanceFactor(0);

        root.getRight().getLeft().setLeft(new AVLNode<>(
                new IceCream("Coffee", 64)));
        root.getRight().getLeft().getLeft().setHeight(0);
        root.getRight().getLeft().getLeft().setBalanceFactor(0);

        setTree(actual, root, 7);

        //creates the expected tree
        AVL<IceCream> expected = new AVL<>();

        AVLNode<IceCream> expectedRoot = new AVLNode<>(
                new IceCream("Chocolate", 62));
        expectedRoot.setHeight(2);
        expectedRoot.setBalanceFactor(0);

        //Sets the left half of the AVL
        expectedRoot.setLeft(new AVLNode<>(
                new IceCream("CookiesAndCream", 34)));
        expectedRoot.getLeft().setHeight(1);
        expectedRoot.getLeft().setBalanceFactor(-1);

        expectedRoot.getLeft().setRight(new AVLNode<>(
                new IceCream("BlueMoon", 57)));
        expectedRoot.getLeft().getRight().setHeight(0);
        expectedRoot.getLeft().getRight().setBalanceFactor(0);

        //Sets the right half of the AVL
        expectedRoot.setRight(new AVLNode<>(
                new IceCream("CookieDough", 80)));
        expectedRoot.getRight().setHeight(1);
        expectedRoot.getRight().setBalanceFactor(0);

        expectedRoot.getRight().setLeft(new AVLNode<>(
                new IceCream("MooseTracks", 79)));
        expectedRoot.getRight().getLeft().setHeight(0);
        expectedRoot.getRight().getLeft().setBalanceFactor(0);

        expectedRoot.getRight().setRight(new AVLNode<>(
                new IceCream("ButterBrittle", 81)));
        expectedRoot.getRight().getRight().setHeight(0);
        expectedRoot.getRight().getRight().setBalanceFactor(0);

        actual.remove(new IceCream("Coffee", 64));

        Assert.assertEquals(
                "Removing when size is 7 did not reset the size to 6",
                6, actual.size());
        assertTreeEquals(expectedRoot, actual.getRoot());
    }

    /**
     * Before Removing
     *                                             50 scoops of Chocolate
     *                                            /                        \
     *                                           /                          \
     *                      30 scoops of ButterBrittle                      100 scoops of CookiesAndCream
     *                    /                     \                                      /                 \
     *                  /                       \                                     /                   \
     *        10 scoops of MooseTracks      40 scoops of RockyRoad       80 scoops of ButterPecan      110 scoops of BlueMoon
     *                   \                                                     /               \                          \
     *                    \                                                   /                 \                          \
     *                20 scoops of CookieDough            70 scoops of MintSwirl         90 scoops of Strawberry       120 scoops of cherry
     *                                                              /
     *                                                             /
     *                                                  60 scoops of LemonSherbet
     *
     *
     * After removing 50 scoops of Chocolate
     *
     *
     *
     *                                               80 scoops of ButterPecan
     *                                            /                        \
     *                                           /                          \
     *                       40 scoops of RockyRoad                      100 scoops of CookiesAndCream
     *                    /                     \                                      /                 \
     *                   /                       \                                    /                   \
     *        20 scoops of CookieDough         70 scoops of MintSwirl       90 scoops of Strawberry      110 scoops of BlueMoon
     *             /            \                              /                                                              \
     *            /              \                            /                                                               \
     * 10 scoops of MooseTracks  30 scoops of ButterBrittle  60 scoops of LemonSherbet                                     120 scoops of Cherry
     *
     */
    @Test(timeout = TIMEOUT)
    public void testRemoveMultipleRotations() {
        AVL<IceCream> actual = new AVL<>();

        AVLNode<IceCream> root = new AVLNode<>(new IceCream("Chocolate", 50));
        root.setHeight(4);
        root.setBalanceFactor(-1);

        //Set left side of tree
        root.setLeft(new AVLNode<>(new IceCream("ButterBrittle", 30)));
        root.getLeft().setHeight(3);
        root.getLeft().setBalanceFactor(1);

        root.getLeft().setLeft(new AVLNode<>(new IceCream("MooseTracks", 10)));
        root.getLeft().getLeft().setHeight(1);
        root.getLeft().getLeft().setBalanceFactor(-1);

        root.getLeft().setRight(new AVLNode<>(new IceCream("RockyRoad", 40)));
        root.getLeft().getRight().setHeight(0);
        root.getLeft().getRight().setBalanceFactor(0);

        root.getLeft().getLeft().setRight(new AVLNode<>(
                new IceCream("CookieDough", 20)));
        root.getLeft().getLeft().getRight().setHeight(0);
        root.getLeft().getLeft().getRight().setBalanceFactor(0);

        //Set right side of tree
        root.setRight(new AVLNode<>(new IceCream("CookiesAndCream", 100)));
        root.getRight().setHeight(3);
        root.getRight().setBalanceFactor(1);

        root.getRight().setLeft(new AVLNode<>(new IceCream("ButterPecan", 80)));
        root.getRight().getLeft().setHeight(2);
        root.getRight().getLeft().setBalanceFactor(1);

        root.getRight().getLeft().setRight(new AVLNode<>(
                new IceCream("Strawberry", 90)));
        root.getRight().getLeft().getRight().setHeight(0);
        root.getRight().getLeft().getRight().setBalanceFactor(0);

        root.getRight().getLeft().setLeft(new AVLNode<>(
                new IceCream("MintSwirl", 70)));
        root.getRight().getLeft().getLeft().setHeight(1);
        root.getRight().getLeft().getLeft().setBalanceFactor(1);

        root.getRight().getLeft().getLeft().setLeft(new AVLNode<>(
                new IceCream("LemonSherbet", 60)));
        root.getRight().getLeft().getLeft().getLeft().setHeight(0);
        root.getRight().getLeft().getLeft().getLeft().setBalanceFactor(0);

        root.getRight().setRight(new AVLNode<>(new IceCream("BlueMoon", 110)));
        root.getRight().getRight().setHeight(1);
        root.getRight().getRight().setBalanceFactor(-1);

        root.getRight().getRight().setRight(new AVLNode<>(
                new IceCream("Cherry", 120)));
        root.getRight().getRight().getRight().setHeight(0);
        root.getRight().getRight().getRight().setBalanceFactor(0);

        setTree(actual, root, 12);

        //creates the transformed tree described above
        AVL<IceCream> expected = new AVL<>();

        AVLNode<IceCream> expectedRoot = new AVLNode<>(
                new IceCream("ButterPecan", 80));
        expectedRoot.setHeight(3);
        expectedRoot.setBalanceFactor(0);

        //Set left side of tree
        expectedRoot.setLeft(new AVLNode<>(new IceCream("RockyRoad", 40)));
        expectedRoot.getLeft().setHeight(2);
        expectedRoot.getLeft().setBalanceFactor(0);

        expectedRoot.getLeft().setLeft(new AVLNode<>(
                new IceCream("CookieDough", 20)));
        expectedRoot.getLeft().getLeft().setHeight(1);
        expectedRoot.getLeft().getLeft().setBalanceFactor(0);

        expectedRoot.getLeft().setRight(new AVLNode<>(
                new IceCream("MintSwirl", 70)));
        expectedRoot.getLeft().getRight().setHeight(1);
        expectedRoot.getLeft().getRight().setBalanceFactor(1);

        expectedRoot.getLeft().getLeft().setLeft(new AVLNode<>(
                new IceCream("MooseTracks", 10)));
        expectedRoot.getLeft().getLeft().getLeft().setHeight(0);
        expectedRoot.getLeft().getLeft().getLeft().setBalanceFactor(0);

        expectedRoot.getLeft().getLeft().setRight(new AVLNode<>(
                new IceCream("ButterBrittle", 30)));
        expectedRoot.getLeft().getLeft().getRight().setHeight(0);
        expectedRoot.getLeft().getLeft().getRight().setBalanceFactor(0);

        expectedRoot.getLeft().getRight().setLeft(new AVLNode<>(
                new IceCream("LemonSherbet", 60)));
        expectedRoot.getLeft().getRight().getLeft().setHeight(0);
        expectedRoot.getLeft().getRight().getLeft().setBalanceFactor(0);

        //Set right side of tree
        expectedRoot.setRight(new AVLNode<>(
                new IceCream("CookiesAndCream", 100)));
        expectedRoot.getRight().setHeight(2);
        expectedRoot.getRight().setBalanceFactor(-1);

        expectedRoot.getRight().setLeft(new AVLNode<>(
                new IceCream("Strawberry", 90)));
        expectedRoot.getRight().getLeft().setHeight(0);
        expectedRoot.getRight().getLeft().setBalanceFactor(0);

        expectedRoot.getRight().setRight(new AVLNode<>(
                new IceCream("BlueMoon", 110)));
        expectedRoot.getRight().getRight().setHeight(1);
        expectedRoot.getRight().getRight().setBalanceFactor(-1);

        expectedRoot.getRight().getRight().setRight(new AVLNode<>(
                new IceCream("Cherry", 120)));
        expectedRoot.getRight().getRight().getRight().setHeight(0);
        expectedRoot.getRight().getRight().getRight().setBalanceFactor(0);

        actual.remove(new IceCream("Chocolate", 50));

        Assert.assertEquals(
                "Removing when size is 12 did not reset the size to 11",
                11, actual.size());
        assertTreeEquals(expectedRoot, actual.getRoot());

    }

    /**
     * Before Removing
     *                                             70 scoops of Chocolate
     *                                            /                        \
     *                                           /                          \
     *                      30 scoops of ButterBrittle                      100 scoops of CookiesAndCream
     *                    /                     \                                      /                 \
     *                  /                       \                                     /                   \
     *   10 scoops of MooseTracks             50 scoops of RockyRoad       80 scoops of ButterPecan      110 scoops of BlueMoon
     *                   \                         /            \                                \                          \
     *                    \                       /             \                                \                          \
     *         20 scoops of CookieDough  40 scoops of Vanilla  60 scoops of SaltedCaramel    90 scoops of Strawberry    120 scoops of cherry
     *                                           /
     *                                          /
     *                              35 scoops of CakeBatter
     *
     *
     * After removing 70 scoops of Chocolate
     *
     *                                            60 scoops of SaltedCaramel
     *                                            /                        \
     *                                           /                          \
     *                      30 scoops of ButterBrittle                      100 scoops of CookiesAndCream
     *                    /                     \                                      /                 \
     *                  /                       \                                     /                   \
     *   10 scoops of MooseTracks             40 scoops of Vanilla          80 scoops of ButterPecan      110 scoops of BlueMoon
     *                   \                         /             \                                \                          \
     *                    \                       /               \                                \                          \
     *         20 scoops of CookieDough  35 scoops of CakeBatter  50 scoops of RockyRoad       90 scoops of Strawberry       120 scoops of Cherry
     *
     */
    @Test (timeout = TIMEOUT)
    public void testRotationOnPredecessorPath() {
        AVL<IceCream> actual = new AVL<>();

        AVLNode<IceCream> root = new AVLNode<>(
                new IceCream("Chocolate", 70));
        root.setHeight(4);
        root.setBalanceFactor(1);

        //Set left side of tree
        root.setLeft(new AVLNode<>(
                new IceCream("ButterBrittle", 30)));
        root.getLeft().setHeight(3);
        root.getLeft().setBalanceFactor(-1);

        root.getLeft().setLeft(new AVLNode<>(
                new IceCream("MooseTracks", 10)));
        root.getLeft().getLeft().setHeight(1);
        root.getLeft().getLeft().setBalanceFactor(-1);

        root.getLeft().setRight(new AVLNode<>(
                new IceCream("RockyRoad", 50)));
        root.getLeft().getRight().setHeight(0);
        root.getLeft().getRight().setBalanceFactor(0);

        root.getLeft().getRight().setLeft(new AVLNode<>(
                new IceCream("Vanilla", 40)));
        root.getLeft().getRight().getLeft().setHeight(1);
        root.getLeft().getRight().getLeft().setBalanceFactor(1);

        root.getLeft().getRight().getLeft().setLeft(new AVLNode<>(
                new IceCream("CakeBatter", 35)));
        root.getLeft().getRight().getLeft().getLeft().setHeight(0);
        root.getLeft().getRight().getLeft().getLeft().setBalanceFactor(0);

        root.getLeft().getRight().setRight(new AVLNode<>(
                new IceCream("SaltedCaramel", 60)));
        root.getLeft().getRight().getRight().setHeight(0);
        root.getLeft().getRight().getRight().setBalanceFactor(0);

        root.getLeft().getLeft().setRight(new AVLNode<>(
                new IceCream("CookieDough", 20)));
        root.getLeft().getLeft().getRight().setHeight(0);
        root.getLeft().getLeft().getRight().setBalanceFactor(0);

        //Set right side of tree
        root.setRight(new AVLNode<>(
                new IceCream("CookiesAndCream", 100)));
        root.getRight().setHeight(2);
        root.getRight().setBalanceFactor(0);

        root.getRight().setLeft(new AVLNode<>(
                new IceCream("ButterPecan", 80)));
        root.getRight().getLeft().setHeight(1);
        root.getRight().getLeft().setBalanceFactor(-1);

        root.getRight().getLeft().setRight(new AVLNode<>(
                new IceCream("Strawberry", 90)));
        root.getRight().getLeft().getRight().setHeight(0);
        root.getRight().getLeft().getRight().setBalanceFactor(0);

        root.getRight().setRight(new AVLNode<>(
                new IceCream("BlueMoon", 110)));
        root.getRight().getRight().setHeight(1);
        root.getRight().getRight().setBalanceFactor(-1);

        root.getRight().getRight().setRight(new AVLNode<>(
                new IceCream("Cherry", 120)));
        root.getRight().getRight().getRight().setHeight(0);
        root.getRight().getRight().getRight().setBalanceFactor(0);

        setTree(actual, root, 13);

        //creates the transformed tree described above
        AVL<IceCream> expected = new AVL<>();

        AVLNode<IceCream> expectedRoot = new AVLNode<>(
                new IceCream("SaltedCaramel", 60));
        expectedRoot.setHeight(3);
        expectedRoot.setBalanceFactor(0);

        //Set left side of tree
        expectedRoot.setLeft(new AVLNode<>(
                new IceCream("ButterBrittle", 30)));
        expectedRoot.getLeft().setHeight(2);
        expectedRoot.getLeft().setBalanceFactor(0);

        expectedRoot.getLeft().setLeft(new AVLNode<>(
                new IceCream("MooseTracks", 10)));
        expectedRoot.getLeft().getLeft().setHeight(1);
        expectedRoot.getLeft().getLeft().setBalanceFactor(-1);

        expectedRoot.getLeft().setRight(new AVLNode<>(
                new IceCream("Vanilla", 40)));
        expectedRoot.getLeft().getRight().setHeight(1);
        expectedRoot.getLeft().getRight().setBalanceFactor(0);

        expectedRoot.getLeft().getRight().setLeft(new AVLNode<>(
                new IceCream("CakeBatter", 35)));
        expectedRoot.getLeft().getRight().getLeft().setHeight(0);
        expectedRoot.getLeft().getRight().getLeft().setBalanceFactor(0);

        expectedRoot.getLeft().getRight().setRight(new AVLNode<>(
                new IceCream("RockyRoad", 50)));
        expectedRoot.getLeft().getRight().getRight().setHeight(0);
        expectedRoot.getLeft().getRight().getRight().setBalanceFactor(0);

        expectedRoot.getLeft().getLeft().setRight(new AVLNode<>(
                new IceCream("CookieDough", 20)));
        expectedRoot.getLeft().getLeft().getRight().setHeight(0);
        expectedRoot.getLeft().getLeft().getRight().setBalanceFactor(0);

        //Set right side of tree
        expectedRoot.setRight(new AVLNode<>(
                new IceCream("CookiesAndCream", 100)));
        expectedRoot.getRight().setHeight(2);
        expectedRoot.getRight().setBalanceFactor(0);

        expectedRoot.getRight().setLeft(new AVLNode<>(
                new IceCream("ButterPecan", 80)));
        expectedRoot.getRight().getLeft().setHeight(1);
        expectedRoot.getRight().getLeft().setBalanceFactor(-1);

        expectedRoot.getRight().getLeft().setRight(new AVLNode<>(
                new IceCream("Strawberry", 90)));
        expectedRoot.getRight().getLeft().getRight().setHeight(0);
        expectedRoot.getRight().getLeft().getRight().setBalanceFactor(0);

        expectedRoot.getRight().setRight(new AVLNode<>(
                new IceCream("BlueMoon", 110)));
        expectedRoot.getRight().getRight().setHeight(1);
        expectedRoot.getRight().getRight().setBalanceFactor(-1);

        expectedRoot.getRight().getRight().setRight(new AVLNode<>(
                new IceCream("Cherry", 120)));
        expectedRoot.getRight().getRight().getRight().setHeight(0);
        expectedRoot.getRight().getRight().getRight().setBalanceFactor(0);

        actual.remove(new IceCream("Chocolate", 70));

        Assert.assertEquals(
                "Removing when size is 13 did not reset the size to 12",
                12, actual.size());
        assertTreeEquals(expectedRoot, actual.getRoot());
    }

    // Clear test

    @Test(timeout = TIMEOUT)
    public void testClear() {
        AVL<IceCream> actual = new AVL<>();
        setTree(actual, buildGeneralBalancedAVL(), 9);

        actual.clear();
        Assert.assertEquals(
                "Clear did not reset the size to 0", 0, actual.size());
        assertTreeEquals(null, actual.getRoot());

        actual.clear();
        Assert.assertEquals(
                "Clear did not reset the size to 0", 0, actual.size());
        assertTreeEquals(null, actual.getRoot());
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void tConstructorNullCollectionNullData() {
        try {
            avl = new AVL<>(null);
        } catch (IllegalArgumentException e) {
            assertEquals("Incorrect exception type",
                    IllegalArgumentException.class,
                    e.getClass());
            assertNull("Element was added to tree when null was added",
                    avl.getRoot());
            assertEquals("Size was incremented when IAE was thrown", 0,
                    avl.size());
            throw e;
        }
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void tConstructorNullDatainCollection() {
        List<NewInteger> test = new LinkedList<>();
        test.add(null);
        try {
            avl = new AVL<>(test);
        } catch (IllegalArgumentException f) {
            assertEquals("Incorrect exception type",
                    IllegalArgumentException.class,
                    f.getClass());
            assertNull("Element was added to tree when IAE was thrown",
                    avl.getRoot());
            assertEquals("Size was incremented when IAE was thrown", 0,
                    avl.size());
            throw f;
        }
    }

    @Test(timeout = TIMEOUT)
    public void tConstructorGeneralNoDuplicates() {
        testList.add(new NewInteger(50, 50));
        testList.add(new NewInteger(75, 75));
        testList.add(new NewInteger(25, 25));
        testList.add(new NewInteger(5, 5));
        testList.add(new NewInteger(30, 30));
        testList.add(new NewInteger(60, 60));
        testList.add(new NewInteger(80, 80));
        testList.add(new NewInteger(85, 85));
        testList.add(new NewInteger(29, 29));
        testList.add(new NewInteger(2, 2));
        testList.add(new NewInteger(6, 6));
        testList.add(new NewInteger(63, 63));
        testList.add(new NewInteger(58, 58));
        testList.add(new NewInteger(59, 59));
        avl = new AVL<>(testList);
        verifyTree2();
    }

    @Test(timeout = TIMEOUT)
    public void tConstructorGeneralDuplicates() {
        // tests if for general case along with duplicates
        testList = new LinkedList<NewInteger>();
        testList.add(new NewInteger(50, 50));
        testList.add(new NewInteger(75, 75));
        testList.add(new NewInteger(25, 25));
        testList.add(new NewInteger(5, 5));
        testList.add(new NewInteger(30, 30));
        testList.add(new NewInteger(60, 60));
        testList.add(new NewInteger(80, 80));
        testList.add(new NewInteger(85, 85));
        testList.add(new NewInteger(29, 29));
        testList.add(new NewInteger(2, 2));
        testList.add(new NewInteger(6, 6));
        testList.add(new NewInteger(63, 63));
        testList.add(new NewInteger(58, 58));
        testList.add(new NewInteger(59, 59));
        testList.add(new NewInteger(80, 80));
        testList.add(new NewInteger(85, 85));
        testList.add(new NewInteger(29, 29));
        testList.add(new NewInteger(2, 2));
        testList.add(new NewInteger(6, 6));
        testList.add(new NewInteger(63, 63));
        testList.add(new NewInteger(58, 58));
        testList.add(new NewInteger(59, 59));
        avl = new AVL<>(testList);
        verifyTree2();
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void tAddNull() {
        try {
            avl.add(null);
        } catch (IllegalArgumentException e) {
            assertEquals("Incorrect exception type",
                    IllegalArgumentException.class,
                    e.getClass());
            assertNull("Element was added to tree when IAE was thrown",
                    avl.getRoot());
            assertEquals("Size was incremented when IAE was thrown", 0,
                    avl.size());
            throw e;
        }
    }

    @Test(timeout = TIMEOUT)
    public void tAddNoRotations() {
        /*                 50
                       /       \
                    25         75
                    /  \      /  \
                10     30    55  85
                            / \
                           54 56
        */
        avl.add(new NewInteger(50, 50));
        avl.add(new NewInteger(75, 75));
        avl.add(new NewInteger(25, 25));
        avl.add(new NewInteger(10, 10));
        avl.add(new NewInteger(55, 55));
        avl.add(new NewInteger(30, 30));
        avl.add(new NewInteger(85, 85));
        avl.add(new NewInteger(54, 54));
        avl.add(new NewInteger(56, 56));
        assertEquals(9, avl.size());
        expectedV = new NewInteger[] {
            new NewInteger(50, 50),
            new NewInteger(25, 25),
            new NewInteger(75, 75),
            new NewInteger(10, 10),
            new NewInteger(30, 30),
            new NewInteger(55, 55),
            new NewInteger(85, 85),
            null,
            null,
            null,
            null,
            new NewInteger(54, 54),
            new NewInteger(56, 56),
            null,
            null,
            null,
            null,
            null,
            null
        };
        expectedH = new int[] {3, 1, 2, 0, 0, 1, 0, -1, -1, -1, -1, 0, 0,
            -1, -1, -1, -1, -1, -1};
        expectedBF = new int[] {-1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0};
        verifyTree(expectedV, expectedH, expectedBF);
    }

    @Test(timeout = TIMEOUT)
    public void tAddDuplicates() {
        setTree1();
        testList.add(new NewInteger(30, 30));
        testList.add(new NewInteger(60, 60));
        testList.add(new NewInteger(80, 80));
        testList.add(new NewInteger(85, 85));
        testList.add(new NewInteger(29, 29));
        verifyTree1();
    }

    @Test(timeout = TIMEOUT)
    public void tAddSingleLeftRotation() {
        testList.add(new NewInteger(50, 50));
        testList.add(new NewInteger(75, 75));
        testList.add(new NewInteger(25, 25));
        testList.add(new NewInteger(85, 85));
        tAVL = new TestAVL(testList);
        setAvlTree(avl, tAVL.tRoot, tAVL.tSize);
        avl.add(new NewInteger(95, 95));
        assertEquals(5, avl.size());
        expectedV = new NewInteger[] {
            new NewInteger(50, 50),
            new NewInteger(25, 25),
            new NewInteger(85, 85),
            null,
            null,
            new NewInteger(75, 75),
            new NewInteger(95, 95),
            null,
            null,
            null,
            null
        };
        expectedH = new int[] {2, 0, 1, -1, -1, 0, 0, -1, -1, -1, -1};
        expectedBF = new int[] {-1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        verifyTree(expectedV, expectedH, expectedBF);
    }

    @Test(timeout = TIMEOUT)
    public void tAddSingleRightRotation() {
        testList.add(new NewInteger(50, 50));
        testList.add(new NewInteger(75, 75));
        testList.add(new NewInteger(25, 25));
        testList.add(new NewInteger(15, 15));
        tAVL = new TestAVL(testList);
        setAvlTree(avl, tAVL.tRoot, tAVL.tSize);
        avl.add(new NewInteger(10, 10));
        assertEquals(5, avl.size());
        expectedV = new NewInteger[] {
            new NewInteger(50, 50),
            new NewInteger(15, 15),
            new NewInteger(75, 75),
            new NewInteger(10, 10),
            new NewInteger(25, 25),
            null,
            null,
            null,
            null,
            null,
            null
        };
        expectedH = new int[] {2, 1, 0, 0, 0, -1, -1, -1, -1, -1, -1};
        expectedBF = new int[] {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        verifyTree(expectedV, expectedH, expectedBF);
    }

    @Test(timeout = TIMEOUT)
    public void tAddSingleRightLeftRotation() {
        /* 50
          / \
        25  75
         \    \
          35  85
         /     /
        30    80 */
        testList.add(new NewInteger(50, 50));
        testList.add(new NewInteger(75, 75));
        testList.add(new NewInteger(25, 25));
        testList.add(new NewInteger(35, 35));
        testList.add(new NewInteger(30, 30));
        testList.add(new NewInteger(85, 85));
        tAVL = new TestAVL(testList);
        setAvlTree(avl, tAVL.tRoot, tAVL.tSize);
        avl.add(new NewInteger(80, 80));
        assertEquals(7, avl.size());
        expectedV = new NewInteger[] {
            new NewInteger(50, 50),
            new NewInteger(30, 30),
            new NewInteger(80, 80),
            new NewInteger(25, 25),
            new NewInteger(35, 35),
            new NewInteger(75, 75),
            new NewInteger(85, 85),
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null
        };
        expectedH = new int[] {2, 1, 1, 0, 0, 0, 0, -1, -1, -1, -1, -1, -1, -1,
            -1};
        expectedBF = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        verifyTree(expectedV, expectedH, expectedBF);
    }

    @Test(timeout = TIMEOUT)
    public void tAddSingleLeftRightRotation() {
        testList.add(new NewInteger(50, 50));
        testList.add(new NewInteger(75, 75));
        testList.add(new NewInteger(25, 25));
        testList.add(new NewInteger(10, 10));
        testList.add(new NewInteger(15, 15));
        testList.add(new NewInteger(60, 60));
        tAVL = new TestAVL(testList);
        setAvlTree(avl, tAVL.tRoot, tAVL.tSize);
        avl.add(new NewInteger(70, 70));
        assertEquals(7, avl.size());
        expectedV = new NewInteger[] {
            new NewInteger(50, 50),
            new NewInteger(15, 15),
            new NewInteger(70, 70),
            new NewInteger(10, 10),
            new NewInteger(25, 25),
            new NewInteger(60, 60),
            new NewInteger(75, 75),
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null
        };
        expectedH = new int[] {2, 1, 1, 0, 0, 0, 0, -1, -1, -1, -1, -1, -1, -1,
            -1};
        expectedBF = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        verifyTree(expectedV, expectedH, expectedBF);
    }

    @Test(timeout = TIMEOUT)
    public void tAddManyAllRotations() {
        testList.add(new NewInteger(50, 50));
        testList.add(new NewInteger(75, 75));
        testList.add(new NewInteger(25, 25));
        testList.add(new NewInteger(35, 35));
        tAVL = new TestAVL(testList);
        setAvlTree(avl, tAVL.tRoot, tAVL.tSize);
        avl.add(new NewInteger(45, 45));
        avl.add(new NewInteger(65, 65));
        avl.add(new NewInteger(55, 55));
        avl.add(new NewInteger(38, 38));
        avl.add(new NewInteger(40, 40));
        avl.add(new NewInteger(58, 58));
        avl.add(new NewInteger(56, 56));
        expectedV = new NewInteger[] {
            new NewInteger(50, 50),
            new NewInteger(35, 35),
            new NewInteger(65, 65),
            new NewInteger(25, 25),
            new NewInteger(40, 40),
            new NewInteger(56, 56),
            new NewInteger(75, 75),
            null,
            null,
            new NewInteger(38, 38),
            new NewInteger(45, 45),
            new NewInteger(55, 55),
            new NewInteger(58, 58),
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null
        };
        expectedBF = new int[] {0, -1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0};
        expectedH = new int[] {3, 2, 2, 0, 1, 1, 0, -1, -1, 0, 0, 0, 0, -1, -1,
            -1, -1, -1, -1, -1, -1, -1, -1};
        assertEquals(11, avl.size());
        verifyTree(expectedV, expectedH, expectedBF);
    }

    @Test(timeout = TIMEOUT)
    public void tAddRotateResetsRoot() {
        testList.add(new NewInteger(25, 25));
        testList.add(new NewInteger(50, 50));
        tAVL = new TestAVL(testList);
        setAvlTree(avl, tAVL.tRoot, tAVL.tSize);
        avl.add(new NewInteger(75, 75));
        expectedV = new NewInteger[] {
            new NewInteger(50, 50),
            new NewInteger(25, 25),
            new NewInteger(75, 75),
            null,
            null,
            null,
            null
        };
        expectedBF = new int[] {0, 0, 0, 0, 0, 0, 0};
        expectedH = new int[] {1, 0, 0, -1, -1, -1, -1};
        verifyTree(expectedV, expectedH, expectedBF);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void tGetNull() {
        try {
            setTree1();
            avl.get(null);
        } catch (IllegalArgumentException e) {
            assertEquals("Incorrect exception type",
                    IllegalArgumentException.class,
                    e.getClass());
            verifyTree1();
            throw e;
        }
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void tGetNotInTree() {
        try {
            setTree1();
            avl.get(new NewInteger(420, 420));
        } catch (NoSuchElementException e) {
            assertEquals("Incorrect exception type",
                    NoSuchElementException.class,
                    e.getClass());
            verifyTree1();
            try {
                setAvlTree(avl, null, 0);
                avl.get(new NewInteger(420, 420));
            } catch (NoSuchElementException f) {
                assertEquals("Incorrect exception type",
                        NoSuchElementException.class,
                        f.getClass());
                assertNull("Element was added to tree when NSEE was thrown",
                        avl.getRoot());
                assertEquals("Size was incremented when NSEE was thrown", 0,
                        avl.size());
                throw f;
            }
        }
    }

    @Test(timeout = TIMEOUT)
    public void tGetRoot() {
        setTree1();
        NewInteger m = new NewInteger(50, 50);
        assertTrue(verifyReturn(m, avl.get(m)));
        verifyTree1();
    }

    @Test(timeout = TIMEOUT)
    public void tGetLeaf() {
        setTree1();
        NewInteger m = new NewInteger(6, 6);
        assertTrue(verifyReturn(m, avl.get(m)));
        m = new NewInteger(60, 60);
        assertTrue(verifyReturn(m, avl.get(m)));
        verifyTree1();
    }

    @Test(timeout = TIMEOUT)
    public void tGet() {
        setTree1();
        NewInteger m = new NewInteger(30, 30);
        assertTrue(verifyReturn(m, avl.get(m)));
        m = new NewInteger(80, 80);
        assertTrue(verifyReturn(m, avl.get(m)));
        verifyTree1();
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void tContainsNull() {
        try {
            setTree1();
            avl.contains(null);
        } catch (IllegalArgumentException e) {
            assertEquals("Incorrect exception type",
                    IllegalArgumentException.class,
                    e.getClass());
            verifyTree1();
            throw e;
        }
    }

    @Test(timeout = TIMEOUT)
    public void tContainsNotInTree() {
        assertFalse(avl.contains(new NewInteger(420, 420)));
        assertNull(avl.getRoot());
        assertEquals(0, avl.size());
        setTree1();
        assertFalse(avl.contains(new NewInteger(420, 420)));
        assertFalse(avl.contains(new NewInteger(10, 10)));
        verifyTree1();
    }

    @Test(timeout = TIMEOUT)
    public void tContains() {
        setTree1();
        NewInteger m = new NewInteger(29, 29);
        assertTrue(avl.contains(m));
        m = new NewInteger(80, 80);
        assertTrue(avl.contains(m));
        verifyTree1();
    }


    @Test(timeout = TIMEOUT)
    public void tPreOrderEmpty() {
        setAvlTree(avl, null, 0);
        List<NewInteger> chainz2 = new ArrayList<>();
        assertEquals(chainz2, avl.preorder());
    }

    @Test(timeout = TIMEOUT)
    public void tPreOrderGeneral() {
        setTree1();
        assertEquals(tAVL.preorder(), avl.preorder());
        verifyTree1();
    }

    @Test(timeout = TIMEOUT)
    public void tInOrderEmpty() {
        setAvlTree(avl, null, 0);
        List<NewInteger> chainz2 = new ArrayList<>();
        assertEquals(chainz2, avl.inorder());
    }

    @Test(timeout = TIMEOUT)
    public void tInOrderGeneral() {
        setTree1();
        assertEquals(tAVL.inorder(), avl.inorder());
        verifyTree1();
    }

    @Test(timeout = TIMEOUT)
    public void tPostOrderEmpty() {
        setAvlTree(avl, null, 0);
        List<NewInteger> chainz2 = new ArrayList<>();
        assertEquals(chainz2, avl.postorder());
    }

    @Test(timeout = TIMEOUT)
    public void tPostOrderGeneral() {
        setTree1();
        assertEquals(tAVL.postorder(), avl.postorder());
        verifyTree1();
    }

    @Test(timeout = TIMEOUT)
    public void tLevelOrderEmpty() {
        setAvlTree(avl, null, 0);
        List<NewInteger> chainz2 = new ArrayList<>();
        assertEquals(chainz2, avl.levelorder());
    }

    @Test(timeout = TIMEOUT)
    public void tLevelOrderGeneral() {
        setTree1();
        assertEquals(tAVL.levelorder(), avl.levelorder());
        verifyTree1();
    }

    @Test(timeout = TIMEOUT)
    public void tHeightEmpty() {
        setAvlTree(avl, null, 0);
        assertEquals(-1, avl.height());
    }

    @Test(timeout = TIMEOUT)
    public void tHeightNotEmpty() {
        setTree1();
        assertEquals(3, avl.height());
        verifyTree1();
    }

    /**
     * Verifies if the original tree is unchanged after methods like get,
     * contains, etc.
     * @param values        levelorder of avl data including null
     * @param heights        heights       levelorder heights
     * @param balanceFactor        balanceFactor levelorder balanceFactor
     */
    private void verifyTree(NewInteger[] values, int[] heights,
                            int[] balanceFactor) {
        getTree();
        int i = 0;
        //Checking values
        for (NewInteger e: v) {
            if (e == null) {
                assertNull("AVL node found where null was expected", values[i]);
            } else {
                assertEquals("Incorrect data in a node.",
                        values[i], e);
            }
            i++;
        }
        i = 0;

        //Checking heights
        for (int e: h) {
            assertEquals("Incorrect height of a node.", e, heights[i]);
            i++;
        }
        i = 0;

        // Checking balance Factor
        for (int e: bF) {
            assertEquals("Incorrect balanceFactor of a node.", e,
                    balanceFactor[i]);
            i++;
        }
        i = 0;
        //assertArrayEquals(balanceFactor, bF);
    }

    /**
     * Gets the level order of the tree. It also includes null.
     */
    private void getTree() {
        root = avl.getRoot();
        Queue<AVLNode<NewInteger>> traverseQueue = new LinkedList<>();
        v = new LinkedList<>();
        h = new LinkedList<>();
        bF = new LinkedList<>();
        if (root == null) {
            v.add(null);
            h.add(-1);
            bF.add(0);
        } else {
            AVLNode<NewInteger> leaf = new AVLNode<>(null);
            leaf.setHeight(-1);
            traverseQueue.add(root);
            while (!traverseQueue.isEmpty()) {
                AVLNode<NewInteger> temp = traverseQueue.remove();
                v.add(temp.getData());
                h.add(temp.getHeight());
                bF.add(temp.getBalanceFactor());
                if (temp.getData() != null) {
                    if (temp.getLeft() != null) {
                        traverseQueue.add(temp.getLeft());
                    } else {
                        traverseQueue.add(leaf);
                    }
                    if (temp.getRight() != null) {
                        traverseQueue.add(temp.getRight());
                    } else {
                        traverseQueue.add(leaf);
                    }
                }
            }
        }
    }

    /**
     * checks if return data is equal to the data passed in and it's memory
     * location is not the same as the data passed in.
     * @param passedIn      argument given to methods
     * @param returned      returned value from methods
     * @return  true if value is equal and the memory location is different
     */
    private boolean verifyReturn(NewInteger passedIn, NewInteger returned) {
        return passedIn.equals(returned) && passedIn != returned;
    }

    /**
     * tree setup 1
     */
    private void setTree1() {
        /*
        *                 50
        *              /      \
        *             25       75
        *            /  \     /  \
        *          5    30   60  80
        *         / \   /          \
        *        2   6 29          85
        * SIZE = 11
        */
        testList.add(new NewInteger(50, 50));
        testList.add(new NewInteger(75, 75));
        testList.add(new NewInteger(25, 25));
        testList.add(new NewInteger(5, 5));
        testList.add(new NewInteger(30, 30));
        testList.add(new NewInteger(60, 60));
        testList.add(new NewInteger(80, 80));
        testList.add(new NewInteger(85, 85));
        testList.add(new NewInteger(29, 29));
        testList.add(new NewInteger(2, 2));
        testList.add(new NewInteger(6, 6));
        tAVL = new TestAVL(testList);
        setAvlTree(avl, tAVL.tRoot, tAVL.tSize);

    }

    /**
     * tree verify 2
     */
    private void verifyTree2() {
        assertEquals(14, avl.size());
        expectedV = new NewInteger[] {
            new NewInteger(50, 50),
            new NewInteger(25, 25),
            new NewInteger(75, 75),
            new NewInteger(5, 5),
            new NewInteger(30, 30),
            new NewInteger(60, 60),
            new NewInteger(80, 80),
            new NewInteger(2, 2),
            new NewInteger(6, 6),
            new NewInteger(29, 29),
            null,
            new NewInteger(58, 58),
            new NewInteger(63, 63),
            null,
            new NewInteger(85, 85),
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            new NewInteger(59, 59),
            null,
            null,
            null,
            null,
            null,
            null
        };
        expectedH = new int[] {4, 2, 3, 1, 1, 2, 1, 0, 0, 0, -1, 1, 0, -1, 0,
            -1, -1, -1, -1, -1, -1, -1, 0, -1, -1, -1, -1, -1, -1};
        expectedBF = new int[] {-1, 0, 1, 0, 1, 1, -1, 0, 0, 0, 0, -1, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        verifyTree(expectedV, expectedH, expectedBF);
    }

    /**
     * tree verify 1
     */
    private void verifyTree1() {
        assertEquals(11, avl.size());
        expectedV = new NewInteger[] {
            new NewInteger(50, 50),
            new NewInteger(25, 25),
            new NewInteger(75, 75),
            new NewInteger(5, 5),
            new NewInteger(30, 30),
            new NewInteger(60, 60),
            new NewInteger(80, 80),
            new NewInteger(2, 2),
            new NewInteger(6, 6),
            new NewInteger(29, 29),
            null,
            null,
            null,
            null,
            new NewInteger(85, 85),
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null
        };
        expectedH = new int[] {3, 2, 2, 1, 1, 0, 1, 0, 0, 0, -1, -1, -1, -1, 0,
            -1, -1, -1, -1, -1, -1, -1, -1};
        expectedBF = new int[] {0, 0, -1, 0, 1, 0, -1, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0};
        verifyTree(expectedV, expectedH, expectedBF);
    }

    /**
     * Set the avl tree with parameters root and size.
     * @param avl The avl tree.
     * @param root The root node.
     * @param size The size.
     */
    private void setAvlTree(AVL<NewInteger> avl, AVLNode<NewInteger> root,
                            int size) {
        try {
            Field field = AVL.class.getDeclaredField("root");
            field.setAccessible(true);
            field.set(avl, root);

            field = AVL.class.getDeclaredField("size");
            field.setAccessible(true);
            field.set(avl, size);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    private class NewInteger implements Comparable<NewInteger> {
        private int integer;
        private int id;

        /**
         * Constructor for NewInteger
         * @param integer - integer for this NewInteger
         * @param id - id for this NewInteger
         */
        public NewInteger(int integer, int id) {
            this.integer = integer;
            this.id = id;
        }
        @Override
        public int compareTo(NewInteger that) {
            return this.integer - that.integer;
        }

        @Override
        public boolean equals(Object that) {
            if (!(that instanceof NewInteger)) {
                return false;
            }

            return (this.integer == ((NewInteger) that).integer);
        }

        /**
         * @return the id of this NewInteger
         */
        public int getId() {
            return id;
        }

        @Override
        public int hashCode() {
            return super.hashCode();
        }

        @Override
        public String toString() {
            return Integer.toString(integer);
        }

    }

    private class TestAVL<T extends Comparable<? super T>> {

        private AVLNode<T> tRoot;
        private int tSize;

        /**
         * constructor that Initializes an AVL based on the collection.
         * @param data Collection containing the data
         */
        public TestAVL(Collection<T> data) {
            if (data == null) {
                throw new IllegalArgumentException("Enter something that is "
                        + "not null");
            }
            for (T e: data) {
                tAdd(e);
            }
        }

        /**
         * Finds the preorder traversal of the avl by calling the recursive
         * method.
         * @return preorder traversal of the avl
         */
        private List<T> preorder() {
            return preorder(tRoot, new ArrayList<>());
        }

        /**
         * Find the preorder traversal by recursing.
         * @param  node the current node
         * @param  preorder list containing the elements
         * @return the list containing elements.
         */
        private List<T> preorder(AVLNode<T> node, List<T> preorder) {
            if (node != null) {
                preorder.add(node.getData());
                preorder(node.getLeft(), preorder);
                preorder(node.getRight(), preorder);
            }
            return preorder;
        }

        /**
         * Finds the postorder traversal of the avl by calling the recursive
         * method.
         * @return postorder traversal of the avl
         */
        private List<T> postorder() {
            return postorder(tRoot, new ArrayList<>());
        }

        /**
         * Find the postorder traversal by recursing.
         * @param  node the current node
         * @param  postorder list containing the elements
         * @return the list containing elements.
         */
        private List<T> postorder(AVLNode<T> node, List<T> postorder) {
            if (node != null) {
                postorder(node.getLeft(), postorder);
                postorder(node.getRight(), postorder);
                postorder.add(node.getData());
            }
            return postorder;
        }

        /**
         * Finds the inorder traversal of the avl by calling the recursive
         * method.
         * @return inorder traversal of the avl
         */
        private List<T> inorder() {
            return inorder(tRoot, new ArrayList<>());
        }

        /**
         * Find the inorder traversal by recursing.
         * @param  node the current node
         * @param  inorder list containing the elements
         * @return the list containing elements.
         */
        private List<T> inorder(AVLNode<T> node, List<T> inorder) {
            if (node != null) {
                inorder(node.getLeft(), inorder);
                inorder.add(node.getData());
                inorder(node.getRight(), inorder);
            }
            return inorder;
        }

        /**
         * Finds the levelorder traversal of the avl by calling the recursive
         * method.
         * @return levelorder traversal of the avl
         */
        private List<T> levelorder() {
            Queue<AVLNode<T>> queue = new LinkedList<AVLNode<T>>();
            queue.add(tRoot);
            return levelorder(queue, new ArrayList<>());
        }

        /**
         * Find the levelorder traversal by recursing.
         * @param queue         Queue containing nodes
         * @param levelorder    list containing the elements
         * @return the list containing the elements
         */
        private List<T> levelorder(Queue<AVLNode<T>> queue,
                                   List<T> levelorder) {
            if (!queue.isEmpty()) {
                AVLNode<T> temp = queue.remove();
                if (temp != null) {
                    levelorder.add(temp.getData());
                    queue.add(temp.getLeft());
                    queue.add(temp.getRight());
                }
                levelorder(queue, levelorder);
            }
            return levelorder;
        }

        /**
         * Adds a given element to the avl
         * @param data data to be added
         */
        private void tAdd(T data) {
            if (data == null) {
                throw new IllegalArgumentException("Enter something that is "
                        + "not null");
            }
            tRoot = add(tRoot, data);
        }

        /**
         * Adds the given data at the right place in the tree and balances it.
         *
         * @param data data to be added
         * @param curr current node in the binary tree
         * @return a balanced subtree with a new element
         */
        private AVLNode<T> add(AVLNode<T> curr, T data) {
            if (curr == null) {
                tSize++;
                return new AVLNode<>(data);
            } else if (data.compareTo(curr.getData()) < 0) {
                curr.setLeft(add(curr.getLeft(), data));
            } else if (data.compareTo(curr.getData()) > 0) {
                curr.setRight(add(curr.getRight(), data));
            }
            return rotator(curr);
        }

        /**
         * Updates the balancing factor, height and performs rotations if they
         * are to be done.
         *
         * @param curr current node in the binary tree
         * @return balanced subtree
         */
        private AVLNode<T> rotator(AVLNode<T> curr) {
            curr.setBalanceFactor(subTreeHeight(curr.getLeft())
                    - subTreeHeight(curr.getRight()));
            if (Math.abs(curr.getBalanceFactor()) > 1) {
                if (curr.getBalanceFactor() > 0) {
                    if (curr.getLeft().getBalanceFactor() < 0) {
                        curr.setLeft(leftRot(curr.getLeft()));
                    }
                    curr = rightRot(curr);
                } else {
                    if (curr.getRight().getBalanceFactor() > 0) {
                        curr.setRight(rightRot(curr.getRight()));
                    }
                    curr = leftRot(curr);
                }
                curr.setBalanceFactor(subTreeHeight(curr.getLeft())
                        - subTreeHeight(curr.getRight()));
            }
            curr.setHeight(Math.max(subTreeHeight(curr.getLeft()),
                    subTreeHeight(curr.getRight())) + 1);
            return curr;
        }

        /**
         * Performs a right rotation about a given node.
         *
         * @param curr node to be rotated
         * @return balanced subtree
         */
        private AVLNode<T> rightRot(AVLNode<T> curr) {
            AVLNode<T> temp = curr.getLeft();
            curr.setLeft(temp.getRight());
            temp.setRight(curr);
            curr = temp;
            temp = curr.getRight();
            temp.setHeight(Math.max(subTreeHeight(temp.getLeft()),
                    subTreeHeight(temp.getRight())) + 1);
            temp.setBalanceFactor(subTreeHeight(temp.getLeft())
                    - subTreeHeight(temp.getRight()));
            return curr;
        }

        /**
         * Performs a left rotation about a given node.
         *
         * @param curr node to be rotated
         * @return balanced subtree
         */
        private AVLNode<T> leftRot(AVLNode<T> curr) {
            AVLNode<T> temp = curr.getRight();
            curr.setRight(temp.getLeft());
            temp.setLeft(curr);
            curr = temp;
            temp = curr.getLeft();
            temp.setHeight(Math.max(subTreeHeight(temp.getLeft()),
                    subTreeHeight(temp.getRight())) + 1);
            temp.setBalanceFactor(subTreeHeight(temp.getLeft())
                    - subTreeHeight(temp.getRight()));
            return curr;
        }

        /**
         * Calculate the height of a subtree
         *
         * @param curr current node at which subtree is rooted
         * @return the height of the given subtree
         */
        private int subTreeHeight(AVLNode<T> curr) {
            if (curr == null) {
                return -1;
            }
            return curr.getHeight();
        }
    }

    /**
     * Better than the Parrot class...
     */
    private static class IceCream implements Comparable<IceCream> {

        private final String iceCreamFlavor;
        private final int iceCreamScoops;

        /**
         * Constructor for IceCream
         *
         * @param flavor Flavor of IceCream
         * @param scoops Number of Scoops of a specified IceCream flavor
         */
        IceCream(String flavor, int scoops) {
            iceCreamFlavor = flavor;
            iceCreamScoops = scoops;
        }

        @Override
        public int compareTo(IceCream that) {
            return 2 * (this.iceCreamScoops - that.iceCreamScoops);
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof IceCream) {
                IceCream that = (IceCream) obj;
                return this.iceCreamFlavor.equals(that.iceCreamFlavor)
                        && this.iceCreamScoops == that.iceCreamScoops;
            } else {
                return false;
            }
        }

        @Override
        public String toString() {
            return iceCreamScoops + " scoops of " + iceCreamFlavor;
        }
    }
}