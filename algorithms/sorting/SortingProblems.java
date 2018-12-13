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
     * Given a sorted array of n integers that has been rotated an
     * unknown number of times, write code to find an element in the
     * array. You may assume that the array was originally sorted in
     * increasing order.
     *
     * Solution Explanation:
     *
     * First, we will find the true starting point of the sorted array.
     * We can do this in O(logn) time using binary search. Next, now that
     * we know the starting point, we can use a modified binary search to
     * find the correct element. To do this we will search normally and then
     * offset the middle by the true starting point.
     */
    public <T extends Comparable<T>> int findElement(T[] arr, T data) {
        if (arr == null || data == null) {
            return -1;
        }

        int offset = findStart(arr, 0, arr.length);

        return binarySearch(arr, data, 0, arr.length, offset);
    }

    private <T extends Comparable<T>> int binarySearch(T[] arr, T data, int start, int end, int offset) {
        if (start >= end) {
            return arr[start].equals(data) ? start : -1;
        }

        int mid = (end - start) / 2 + start,
                offsetMid = mid + offset % arr.length;

        if (arr[offsetMid].equals(data)) {
            return offsetMid;
        } else if (arr[offsetMid].compareTo(arr[start]) < 0) {
            return binarySearch(arr, data, start, mid, offset);
        } else {
            return binarySearch(arr, data, mid + 1, end, offset);
        }
    }

    /**
     * Find the beginning of a shifted sorted list
     */
    private <T extends Comparable<T>> int findStart(T[] arr, int start, int end) {
        if (start >= end) {
            return start;
        }

        int mid = (end - start) / 2 + start;

        if (arr[mid].compareTo(arr[start]) < 0) {
            return findStart(arr, start, mid);
        } else {
            return findStart(arr, mid + 1, end);
        }
    }
}
