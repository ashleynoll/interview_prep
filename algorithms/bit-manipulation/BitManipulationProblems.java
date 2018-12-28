public class BitManipulationProblems {

    /**
     * Problem:
     *
     * You are given two 32-bit numbers, N and M, and two bit positions,
     * i and j. Write a method to insert M into N such that M starts at
     * bit j and ends at bit i. You can assume that the bits j through
     * i have enough space to fit all of M.
     *
     * Solution Explanation:
     *
     * You need to create a mask that's as large as the area you're replacing.
     * This can be accomplished by left pushing a number by the number of needed
     * spots and subtracting 1 (1000 - 1 = 111). Then, shift this into position and
     * negate, anding together with the original string to clear the bits. Shift the
     * other given bit string over to fit in the given spot and or with this newly
     * cleared section.
     */
    public int replaceWithinBitString(int N, int M, int i, int j) {
        return N & ~(((1 << (j - i + 1)) - 1) << i) | (M << i);
    }

    /**
     * Problem:
     *
     * Given a real number between 0 and 1 (e.g., 0.72) that is passed in as a
     * double, print the binary representation. If the number cannot be represented
     * accurately in binary with at most 32 characters, print ERROR.
     *
     * Solution Explanation:
     *
     * Since decimals in binary are 1/2, 1/4, 1/8, ... we will need to find a way
     * to convert this to 1s and 0s. We can do this by continuously multiplying
     * by 2 and checking whether the number has exceeded 1. If so, we add a
     * 1 to our string, otherwise, a 0.
     */
    public String findBinaryOfDecimal(double num) {
        StringBuilder sb = new StringBuilder(".");

        while (num > 0) {
            if (sb.length() >= 32) {
                return "ERROR";
            }

            num *= 2;
            if (num >= 1) {
                sb.append(1);
                num -= 1;
            } else {
                sb.append(0);
            }
        }

        return sb.toString();
    }

    /**
     * Problem:
     *
     * You have an integer and you can flip exactly one bit from a 0 to a 1.
     * Write code to find the length of the longest sequence of 1s you could create.
     *
     * Solution Explanation:
     *
     * We only need to add a given string of 1s to those around it to determine which
     * creates the longest. We'll do this by keeping track of two subsequent 1 strings,
     * incrementing the second counter every time we see a 1. When we see a zero, we
     * update the overall longest count and move the second count to the first, resetting
     * the first.
     */
    public int longestSequenceOfOnes(int a) {
        int countFirst = 0, countSecond = 0, countLongest = 0;

        while (a > 0) {
            if (a % 2 == 1) {
                countSecond++;
            } else {
                countLongest = Math.max(countFirst + countSecond + 1, countLongest);
                countFirst = countSecond;
                countSecond = 0;
            }
            a /= 2;
        }

        return countLongest;
    }

    /**
     * Problem:
     *
     * Given a positive integer, print the next smallest and the next largest
     * number that have the same number of 1 bits in their binary representation.
     *
     * Solution Explanation:
     *
     * For the largest number, we'll find the first zero with a 1 before it and swap
     * their places, moving all other ones before it to the beginning. For the smallest,
     * we'll find the first 1 proceeded by 0s and move it down by one.
     */
    public int[] nextLargestNextSmallest(int i) {
        int[] arr = new int[2];

        arr[0] = nextLargest(i);
        arr[1] = nextSmallest(i);

        return arr;
    }

    private int nextLargest(int i) {
        int countOnes = 0, countZeros = 0;

        while (i > 1) {
            if ((i & 1) == 1) {
                countOnes++;
            } else if (countOnes > 0) {
                i /= 2;

                // Make room for digits to add back
                i = i << (countOnes + countZeros + 1);

                // Move the found 1
                i |= (1 << (countZeros + countOnes));

                // Add back ones to end
                if (countOnes > 0) {
                    i |= (1 << countOnes - 1) - 1;
                }

                return i;
            } else {
                countZeros++;
            }

            i /= 2;
        }

        i = i << (countZeros + countOnes + 1);

        if (countOnes > 0) {
            i |= (1 << countOnes) - 1;
        }

        return i;
    }

    private int nextSmallest(int i) {
        int countZeros = 0, countOnes = 0;

        // Find 1 proceeded by 0s
        while (i > 1) {
            if ((i & 1) == 0) {
                countZeros++;
            } else if (countZeros > 0) {
                i /= 2;

                // Make room for digits to add back
                i = i << (countZeros + countOnes + 1);

                // Move the found zero back by one
                i |= (1 << (countZeros + countOnes - 1));

                // Add back the ones to the beginning
                if (countOnes > 0) {
                    i |= (1 << countOnes) - 1;
                }

                return i;
            } else {
                countOnes++;
            }
            i /= 2;
        }

        i = i << (countZeros + countOnes - 1);

        if (countOnes > 0) {
            i |= (1 << countOnes) - 1;
        }

        return i;
    }

    /**
     * Problem:
     *
     * Write a function to determine the number of bits you would need
     * to flip to convert integer A to integer B.
     *
     * Solution Explanation:
     *
     * What this question is really asking for is similar to the result
     * of an exclusive OR of A and B. Therefore, we can use a ^ b and while
     * this resultant number is not 0, continuously clear the found 1s by using
     * c & (c - 1). The number of times this runs is the number of non-matching
     * bits and therefore the answer.
     */
    public int numberFlippedBitsToConvert(int a, int b) {
        int count = 0, c = a ^ b;

        while (c > 0) {
            count++;
            c &= c - 1;
        }

        return count;
    }
}
