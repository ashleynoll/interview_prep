/**
 * Problem:
 *
 * You are given an n x n 2D matrix that represents an image.
 * Rotate the image by 90 degrees (clockwise).
 *
 * Note: Try to solve this task in-place (with O(1) additional memory),
 * since this is what you'll be asked to do during an interview.
 *
 * Solution Explanation:
 *
 * Matrix rotation can be accomplished by mirroring the elements on the
 * upper-left to bottom-right diagonal and then mirroring the elements vertically.
 */
public class rotateImage {
    public static int[][] rotateImage(int[][] a) {
        // Mirror on the diagonal
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < i; j++) {
                int temp = a[i][j];
                a[i][j] = a[j][i];
                a[j][i] = temp;
            }
        }

        // Mirror on the vertical axis
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[i].length / 2; j++) {
                int temp = a[i][j];
                a[i][j] = a[i][a.length - 1 - j];
                a[i][a.length - 1 - j] = temp;
            }
        }

        return a;
    }

    public static void main(String[] args) {
        /**
         * [  1,  2,  3,  4 ]
         * [  5,  6,  7,  8 ]
         * [  9, 10, 11, 12 ]
         * [ 13, 14, 15, 16 ]
         *
         *  Rotates to =>
         *
         * [ 13,  9, 5, 1 ]
         * [ 14, 10, 6, 2 ]
         * [ 15, 11, 7, 3 ]
         * [ 16, 12, 8, 4 ]
         */
        int[][] a = { { 1, 2, 3, 4 }, { 5, 6, 7, 8 }, { 9, 10, 11, 12}, { 13, 14, 15, 16 } },
                ans = { { 13, 9, 5, 1 }, { 14, 10, 6, 2 }, { 15, 11, 7, 3}, { 16, 12, 8, 4 } },
                ret = rotateImage(a);
        boolean correct = true;

        System.out.println("Rotated array is: ");
        for (int i = 0; i < ret.length; i++) {
            System.out.print("[ ");
            for (int j = 0; j < ret[i].length; j++) {
                if (ans[i][j] != ret[i][j]) {
                    correct = false;
                }
                System.out.print(ret[i][j] + " ");
            }
            System.out.print("]");
            System.out.println();
        }
        System.out.println("Solution is " + (correct ? "correct!" : "incorrect."));

        System.out.println("All tests complete!");
    }
}
