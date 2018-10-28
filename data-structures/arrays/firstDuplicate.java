import java.util.HashSet;
import java.util.Set;

/**
 * Problem:
 *
 * Given an array a that contains only numbers in the range from 1 to a.length,
 * find the first duplicate number for which the second occurrence has the
 * minimal index. In other words, if there are more than 1 duplicated numbers,
 * return the number for which the second occurrence has a smaller index than
 * the second occurrence of the other number does. If there are no such
 * elements, return -1.
 *
 * Solution Explanation:
 *
 * Loop through the array. As integers are encountered, put them in a HashSet.
 * Check if the value already existed in the HashSet. If so, return the value.
 * Return -1 if the loop terminates without finding a duplicate.
 */
public class firstDuplicate {
    public static int firstDuplicate(int[] a) {
        Set<Integer> encountered = new HashSet<>();

        for (int i = 0; i < a.length; i++) {
            if (encountered.contains(a[i])) {
                return a[i];
            }
            encountered.add(a[i]);
        }

        return -1;
    }

    public static void main(String[] args) {
        int[] a = {1, 2, 2, 1};
        int ans = firstDuplicate(a);

        if (ans != 2) {
            System.out.println("For array [1, 2, 2, 1], expected 2 but was " + ans + ".");
        }

        a = new int[]{1, 2, 3, 4, 5};
        ans = firstDuplicate(a);

        if (ans != -1) {
            System.out.println("For array [1, 2, 3, 4, 5], expected -1 but was " + ans + ".");
        }

        System.out.println("All tests complete!");
    }
}
