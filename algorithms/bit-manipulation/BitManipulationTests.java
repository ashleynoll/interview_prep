import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class BitManipulationTests {

    private static final long TIMEOUT = 200;
    private BitManipulationProblems problems;

    @Before
    public void setUp() {
        problems = new BitManipulationProblems();
    }

    @Test(timeout = TIMEOUT)
    public void testReplaceWithinBitString() {
        assertEquals("Should replace given section within bit string",
                Integer.parseInt("10101010001", 2),
                problems.replaceWithinBitString(Integer.parseInt("10101111001", 2),
                        Integer.parseInt("1010", 2), 3, 6));
    }

    @Test(timeout = TIMEOUT)
    public void testFindBinaryOfDecimal() {
        assertEquals("Return string should be correct.", ".101", problems.findBinaryOfDecimal(.625));
    }

    @Test(timeout = TIMEOUT)
    public void testLongestSequenceOfOnes() {
        assertEquals("Should match longest sequence length.", 9,
                problems.longestSequenceOfOnes(Integer.parseInt("1110011111101101111", 2)));
    }

    @Test(timeout = TIMEOUT)
    public void testNestLargestNextSmallest() {
        assertArrayEquals("Should return expected binary strings.",
                new int[]{ Integer.parseInt("101001", 2), Integer.parseInt("100101", 2) },
                problems.nextLargestNextSmallest(Integer.parseInt("100110", 2)));

        assertArrayEquals("Should return expected binary strings.",
                new int[]{ Integer.parseInt("1000", 2), Integer.parseInt("10", 2) },
                problems.nextLargestNextSmallest(Integer.parseInt("100", 2)));

        assertArrayEquals("Should return expected binary strings.",
                new int[]{ Integer.parseInt("10001", 2), Integer.parseInt("1010", 2) },
                problems.nextLargestNextSmallest(Integer.parseInt("1100", 2)));
    }

    @Test(timeout = TIMEOUT)
    public void testNumberFlippedBitsToConvert() {
        assertEquals("Should find correct number of bits to flip.", 2,
                problems.numberFlippedBitsToConvert(Integer.parseInt("11101", 2), Integer.parseInt("01111", 2)));

        assertEquals("Should find correct number of bits to flip.", 0, problems.numberFlippedBitsToConvert(45, 45));
    }
}
