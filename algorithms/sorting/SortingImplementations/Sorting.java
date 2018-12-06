package SortingImplementations;

import java.util.*;

/**
 * Implementation of various sorting algorithms.
 */
public class Sorting {

    /**
     * Implement bubble sort.
     *
     * It should be:
     *  in-place
     *  stable
     *
     * Have a worst case running time of:
     *  O(n^2)
     *
     * And a best case running time of:
     *  O(n)
     *
     * Any duplicates in the array should be in the same relative position after
     * sorting as they were before sorting. (stable)
     *
     * @throws IllegalArgumentException if the array or comparator is null
     * @param <T> data type to sort
     * @param arr the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     */
    public static <T> void bubbleSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("Must have valid array and comparator.");
        }

        int swapped;
        for (int i = arr.length - 1; i > 0; i--) {
            swapped = -1;
            for (int j = 1; j <= i; j++) {
                if (comparator.compare(arr[j - 1], arr[j]) > 0) {
                    swapped = j;
                    swap(arr, j - 1, j);
                }
            }
            i = Math.min(i, swapped);
        }
    }

    /**
     * Implement insertion sort.
     *
     * It should be:
     *  in-place
     *  stable
     *
     * Have a worst case running time of:
     *  O(n^2)
     *
     * And a best case running time of:
     *  O(n)
     *
     * Any duplicates in the array should be in the same relative position after
     * sorting as they were before sorting. (stable).
     *
     * @throws IllegalArgumentException if the array or comparator is null
     * @param <T> data type to sort
     * @param arr the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     */
    public static <T> void insertionSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("Must have valid array and comparator.");
        }

        for (int i = 1; i < arr.length; i++) {
            int j = i;
            while (j > 0 && comparator.compare(arr[j - 1], arr[j]) > 0) {
                swap(arr, j - 1, j);
                j--;
            }
        }
    }

    /**
     * Implement quick sort.
     *
     * Use the provided random object to select your pivots.
     * For example if you need a pivot between a (inclusive)
     * and b (exclusive) where b > a, use the following code:
     *
     * int pivotIndex = r.nextInt(b - a) + a;
     *
     * It should be:
     *  in-place
     *
     * Have a worst case running time of:
     *  O(n^2)
     *
     * And a best case running time of:
     *  O(n log n)
     *
     * Note that there may be duplicates in the array.
     *
     * @throws IllegalArgumentException if the array or comparator or rand is
     * null
     * @param <T> data type to sort
     * @param arr the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @param rand the Random object used to select pivots
     */
    public static <T> void quickSort(T[] arr, Comparator<T> comparator,
                                     Random rand) {
        if (arr == null || comparator == null || rand == null) {
            throw new IllegalArgumentException("Must have valid array and comparator.");
        }

        quickSort(arr, comparator, rand, 0, arr.length);
    }

    private static <T> void quickSort(T[] arr, Comparator<T> comparator, Random rand, int start, int end) {
        if (start >= end) {
            return;
        }

        int pivot = rand.nextInt(end - start) + start;
        swap(arr, pivot, start);

        int i = start + 1,
                j = end - 1;

        while (i <= j) {
            while (i <= j && comparator.compare(arr[i], arr[start]) <= 0) {
                i++;
            }

            while (i <= j && comparator.compare(arr[j], arr[start]) >= 0) {
                j--;
            }

            if (i < j) {
                swap(arr, i, j);
                i++;
                j--;
            }
        }

        swap(arr, start, j);
        quickSort(arr, comparator, rand, start, j);
        quickSort(arr, comparator, rand, j + 1, end);
    }

    /**
     * Implement merge sort.
     *
     * It should be:
     *  stable
     *
     * Have a worst case running time of:
     *  O(n log n)
     *
     * And a best case running time of:
     *  O(n log n)
     *
     * You can create more arrays to run mergesort, but at the end,
     * everything should be merged back into the original T[]
     * which was passed in.
     *
     * Any duplicates in the array should be in the same relative position after
     * sorting as they were before sorting.
     *
     * @throws IllegalArgumentException if the array or comparator is null
     * @param <T> data type to sort
     * @param arr the array to be sorted
     * @param comparator the Comparator used to compare the data in arr
     */
    public static <T> void mergeSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("Must have valid array and comparator.");
        }

        if (arr.length > 1) {
            T[] leftArr = (T[]) new Object[arr.length / 2], rightArr = (T[]) new Object[arr.length - arr.length / 2];

            int i = 0;
            for (; i < leftArr.length; i++) {
                leftArr[i] = arr[i];
            }

            for (; i < arr.length; i++) {
                rightArr[i - leftArr.length] = arr[i];
            }

            mergeSort(leftArr, comparator);
            mergeSort(rightArr, comparator);

            int l = 0, r = 0;
            for (int j = 0; j < arr.length; j++) {
                if (r >= rightArr.length || l < leftArr.length && comparator.compare(leftArr[l], rightArr[r]) <= 0) {
                    arr[j] = leftArr[l];
                    l++;
                } else {
                    arr[j] = rightArr[r];
                    r++;
                }
            }
        }
    }

    /**
     * Implement LSD (least significant digit) radix sort.
     *
     * It should be:
     *  stable
     *
     * Have a worst case running time of:
     *  O(kn)
     *
     * And a best case running time of:
     *  O(kn)
     *
     * Any duplicates in the array should be in the same relative position after
     * sorting as they were before sorting. (stable)
     *
     * @throws IllegalArgumentException if the array is null
     * @param arr the array to be sorted
     * @return the sorted array
     */
    public static int[] lsdRadixSort(int[] arr) {
        if (arr == null) {
            throw new IllegalArgumentException("Must have valid array and comparator.");
        }

        List<Integer>[] buckets = (LinkedList<Integer>[]) new LinkedList[19];

        boolean go = true;
        int digit = 1;

        while (go) {
            go = false;
            for (int val : arr) {
                int bucket = val / digit % 10 + 9;
                go |= val / digit / 10 != 0;
                if (buckets[bucket] == null) {
                    buckets[bucket] = new LinkedList<>();
                }
                buckets[bucket].add(val);
            }

            int i = 0;
            for (List<Integer> bucket : buckets) {
                while (bucket != null && !bucket.isEmpty()) {
                    arr[i++] = bucket.remove(0);
                }
            }

            digit *= 10;
        }

        return arr;
    }

    /**
     * Implement MSD (most significant digit) radix sort.
     *
     * It should:
     *
     * Have a worst case running time of:
     *  O(kn)
     *
     * And a best case running time of:
     *  O(kn)
     *
     * @throws IllegalArgumentException if the array is null
     * @param arr the array to be sorted
     * @return the sorted array
     */
    public static int[] msdRadixSort(int[] arr) {
        if (arr == null) {
            throw new IllegalArgumentException("Must have valid array and comparator.");
        }

        int min = Integer.MAX_VALUE,
                max = Integer.MIN_VALUE;
        for (int val : arr) {
            min = Math.min(min, val);
            max = Math.max(max, val);
        }

        int exp = 0;
        while (min > 0 || max > 0) {
            exp++;
            min /= 10;
            max /= 10;
        }

        return msdRadixSort(arr, exp, 0, arr.length);
    }

    private static int[] msdRadixSort(int[] arr, int exp, int left, int right) {
        if (exp < 0 || right - left <= 1) {
            return arr;
        }

        int digit = pow(10, exp);
        LinkedList<Integer>[] buckets = (LinkedList<Integer>[]) new LinkedList[19];

        for (int i = left; i < right; i++) {
            int bucket = arr[i] / digit % 10 + 9;
            if (buckets[bucket] == null) {
                buckets[bucket] = new LinkedList<>();
            }
            buckets[bucket].add(arr[i]);
        }

        int i = left;
        for (List<Integer> bucket : buckets) {
            if (bucket != null) {
                int size = bucket.size();
                while (!bucket.isEmpty()) {
                    arr[i++] = bucket.remove(0);
                }
                msdRadixSort(arr, exp - 1, i - size, i);
            }
        }

        return arr;
    }

    /**
     * Calculate the result of a number raised to a power. Use this method in
     * your radix sorts instead of {@code Math.pow()}.
     *
     *
     * @throws IllegalArgumentException if both {@code base} and {@code exp} are
     * 0
     * @throws IllegalArgumentException if {@code exp} is negative
     * @param base base of the number
     * @param exp power to raise the base to. Must be 0 or greater.
     * @return result of the base raised to that power
     */
    private static int pow(int base, int exp) {
        if (exp < 0) {
            throw new IllegalArgumentException("Exponent cannot be negative.");
        } else if (base == 0 && exp == 0) {
            throw new IllegalArgumentException(
                    "Both base and exponent cannot be 0.");
        } else if (exp == 0) {
            return 1;
        } else if (exp == 1) {
            return base;
        }
        int halfPow = pow(base, exp / 2);
        if (exp % 2 == 0) {
            return halfPow * halfPow;
        } else {
            return halfPow * halfPow * base;
        }
    }

    private static <T> void swap(T[] arr, int i, int j) {
        T temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
