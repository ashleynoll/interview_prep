import com.sun.xml.internal.fastinfoset.util.CharArray;

import java.util.*;

public class SortingProblems {

    /**
     * Problem:
     *
     * You are given two sorted arrays, A and B, where A has a large enough buffer at the
     * end to hold B. Write a method to merge B into A in sorted order.
     *
     * Solution Explanation:
     *
     * To avoid extraneous movements and overwriting data, we will start adding from
     * the end of a. We know that a includes extra spaces for b, so the true length of
     * a must be a.length - b.length. We'll start a pointer at the end of both a's
     * and b's data and one at the very end of a where the buffer is. Now, we'll
     * compare the data to find the greater of the two and put that on the back of the
     * list, moving the end pointer and the data's pointer forward one. This will continue
     * until b's pointer has run through it's list.
     */
    public <T extends Comparable<T>> void merge(T[] a, T[] b) {
        if (a == null || b == null) {
            return;
        }

        int endIndex = a.length - 1,
                aIndex = a.length - b.length - 1,
                bIndex = b.length - 1;
        while (bIndex >= 0) {
            if (aIndex >= 0 && a[aIndex].compareTo(b[bIndex]) > 0) {
                a[endIndex--] = a[aIndex--];
            } else {
                a[endIndex--] = b[bIndex--];
            }
        }
    }

    /**
     * Problem:
     *
     * Write a method to sort an array of strings so that all the anagrams
     * are next to each other.
     *
     * Solution Explanation:
     *
     * Anagrams will have the same sorted order. Therefore, we will go through
     * and add all of the entries to a map with the key being the sorted version
     * of the string. Afterwards, we go through the map and put elements back
     * into the original array.
     */
    public void sortAnagrams(String[] arr) {
        if (arr == null) {
            return;
        }
        Map<String, List<String>> map = new HashMap<>();

        for (String s: arr) {
            char[] chars = s.toCharArray();
            Arrays.sort(chars);
            String sorted = new String(chars);

            if (map.get(sorted) == null) {
                List<String> list = new ArrayList<>();
                list.add(s);
                map.put(sorted, list);
            } else {
                map.get(sorted).add(s);
            }
        }

        int index = 0;
        for (String s: map.keySet()) {
            for (String t : map.get(s)) {
                arr[index++] = t;
            }
        }
    }

    /**
     * Problem:
     *
     * In an array of integers, a "peak" is an element which is greater than or
     * equal to the adjacent integers and a "valley" is an element which is less
     * than or equal to the adjacent integers. Given an array of integers, sort
     * the array into an alternating sequence of peaks and valleys.
     *
     * Solution:
     *
     * To solve this, we'll go through and create the peaks by looking at the
     * surrounding elements. As long as the peaks are in place, the other
     * elements must be the valleys. Therefore, we'll go to every other element and make
     * it a peak by finding the max element in the subarray of three and placing it in
     * the middle.
     */
    public <T extends Comparable<? super T>> void peaksAndValleys(T[] arr) {
        if (arr == null) {
            return;
        }

        for (int i = 0; i < arr.length - 2; i += 2) {
            int maxIndex = findMax(arr, i, i + 3);
            swap(arr, maxIndex, i + 1);
        }
    }

    private <T extends Comparable<? super T>> int findMax(T[] arr, int start, int end) {
        T max = arr[start];
        int maxIndex = start;
        for (int i = start + 1; i < end; i++) {
            boolean isLarger = arr[i].compareTo(max) > 0;
            max = isLarger ? arr[i] : max;
            maxIndex = isLarger ? i : maxIndex;
        }

        return maxIndex;
    }

    private <T> void swap(T[] arr, int i, int j) {
        T temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
