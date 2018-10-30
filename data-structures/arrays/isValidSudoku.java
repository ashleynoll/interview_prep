import java.util.HashMap;
import java.util.HashSet;

/**
 * Problem:
 *
 * Sudoku is a number-placement puzzle. The objective is to fill a 9 × 9 grid
 * with numbers in such a way that each column, each row, and each of the nine
 * 3 × 3 sub-grids that compose the grid all contain all of the numbers from
 * 1 to 9 one time.

 Implement an algorithm that will check whether the given grid of numbers
 represents a valid Sudoku puzzle according to the layout rules described above.
 Note that the puzzle represented by grid does not have to be solvable.
 *
 * Solution Explanation:
 * Maintain two maps of maps that track the column and row usage of numbers.
 * Go through every 3 x 3 box, making sure that there are no duplicates by
 * storing values in a hashmap. At the same time, add numbers to row/column
 * map and check for duplicates. After all boxes have been checked the sudoku
 * grid can be called valid.
 *
 */
public class isValidSudoku {
    public static boolean isValidSudoku(char[][] grid) {
        HashMap<Integer, HashSet<Character>> columns = new HashMap<>(),
                rows = new HashMap<>();

        // Check every 3x3 box
        for (int i = 0; i < 9; i++) {
            HashSet<Character> inBoxSet = new HashSet<>();
            for (int k = i % 3 * 3; k < i % 3 * 3 + 3; k++) {
                for (int j = i / 3 * 3; j < i / 3 * 3 + 3; j ++) {
                    char curr = grid[k][j];
                    if (curr != '.') {
                        // Check if valid for box
                        if (inBoxSet.contains(curr)) {
                            return false;
                        }
                        inBoxSet.add(curr);

                        // Check if valid for rows
                        rows.computeIfAbsent(k, key -> new HashSet<>());
                        if (rows.get(k).contains(curr)) {
                            return false;
                        }
                        rows.get(k).add(curr);

                        // Check if valid for rows
                        columns.computeIfAbsent(j, key-> new HashSet<>());
                        if (columns.get(j).contains(curr)) {
                            return false;
                        }
                        columns.get(j).add(curr);
                    }
                }
            }
        }

        return true;
    }

    public static void main(String[] args) {
        char[][] a = new char[][]{
                {'9', '7', '.', '.', '.', '.', '.', '.', '.'},
                {'6', '1', '8', '.', '.', '9', '.', '5', '.'},
                {'.', '.', '.', '3', '7', '.', '.', '1', '.'},
                {'.', '3', '2', '8', '.', '.', '.', '6', '1'},
                {'.', '.', '4', '6', '5', '7', '2', '.', '.'},
                {'7', '8', '.', '.', '.', '2', '9', '4', '.'},
                {'.', '6', '.', '.', '8', '1', '.', '.', '.'},
                {'.', '2', '.', '4', '.', '.', '1', '9', '8'},
                {'.', '.', '.', '.', '.', '.', '.', '7', '4'}
        };

        if (!isValidSudoku(a)) {
            System.out.println("Incorrectly identified valid Sudoku as invalid.");
        }

        a = new char[][]{
                {'.', '.', '.', '.', '3', '6', '9', '.', '1'},
                {'.', '1', '.', '.', '.', '4', '.', '.', '5'},
                {'.', '8', '.', '.', '9', '.', '7', '.', '.'},
                {'2', '.', '1', '7', '8', '5', '.', '.', '.'},
                {'.', '5', '.', '.', '.', '9', '.', '.', '7'},
                {'9', '7', '4', '6', '.', '2', '3', '.', '.'},
                {'.', '4', '.', '.', '6', '.', '1', '.', '8'},
                {'3', '9', '2', '.', '.', '7', '6', '8', '.'}, // Extra 8 in box
                {'1', '.', '7', '.', '.', '.', '5', '9', '3'}
        };

        if (isValidSudoku(a)) {
            System.out.println("Incorrectly identified invalid Sudoku as valid");
        }

        a = new char[][]{
                {'.', '.', '.', '.', '3', '6', '9', '.', '1'},
                {'.', '1', '.', '.', '.', '4', '.', '.', '5'},
                {'.', '8', '.', '.', '9', '.', '7', '.', '.'},
                {'2', '.', '1', '7', '8', '5', '.', '.', '.'},
                {'.', '5', '.', '.', '.', '9', '.', '.', '7'},
                {'9', '7', '4', '6', '.', '2', '3', '.', '.'},
                {'.', '4', '.', '.', '6', '5', '1', '.', '.'}, // Extra 5 in column
                {'3', '9', '2', '.', '.', '7', '6', '8', '.'},
                {'1', '.', '7', '.', '.', '.', '5', '9', '3'}
        };

        if (isValidSudoku(a)) {
            System.out.println("Incorrectly identified invalid Sudoku as valid");
        }

        a = new char[][]{
                {'.', '.', '.', '.', '3', '6', '9', '.', '1'},
                {'.', '1', '.', '.', '.', '4', '.', '.', '5'},
                {'.', '8', '.', '.', '9', '.', '7', '.', '.'},
                {'2', '.', '1', '7', '8', '5', '.', '.', '.'},
                {'.', '5', '.', '.', '.', '9', '.', '.', '7'},
                {'9', '7', '4', '6', '.', '2', '3', '.', '2'}, // Extra 2 in row
                {'.', '4', '.', '.', '6', '.', '1', '.', '.'},
                {'3', '9', '2', '.', '.', '7', '6', '8', '.'},
                {'1', '.', '7', '.', '.', '.', '5', '9', '3'}
        };

        if (isValidSudoku(a)) {
            System.out.println("Incorrectly identified invalid Sudoku as valid");
        }

        System.out.println("All tests complete!");
    }
}
