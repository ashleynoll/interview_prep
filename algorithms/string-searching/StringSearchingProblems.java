import java.util.Arrays;
import java.util.Random;

public class StringSearchingProblems {
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
            return start < arr.length && arr[start].equals(data) ? start : -1;
        }

        int mid = (end - start) / 2 + start,
                offsetMid = (mid + offset) % arr.length;

        if (arr[offsetMid].equals(data)) {
            return offsetMid;
        } else if (arr[offsetMid].compareTo(data) > 0) {
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

    /**
     * Problem:
     *
     * You are given an array-like data structure Listy which lacks a size method.
     * It does, however, have an elementAt(i) method that returns the element at index
     * i in O(1) time. If i is beyond the bounds of the data structure, it returns -1.
     * (For this reason, the data structure only supports positive integers.) Given a Listy
     * which contains sorted, positive integers, find the index at which an element x occurs.
     * If x occurs multiple times, you may return any index.
     *
     * Solution Explanation:
     *
     * This problem can be easily solved using binary search. The only problem is that there is
     * no known length of the list. We can attempt to find the end of the list, or at least
     * a place near the end of the list in O(logn) by checking data at all 2^n positions. As
     * soon as the data is -1 we start normal binary search. The only variation with this is
     * that if the mid point is -1 we'll search the left half of the list.
     */
    public int findInListy(Listy listy, int data) {
        if (listy == null) {
            return -1;
        }

        // Find end of listy
        int curr = 1;
        while (listy.elementAt(curr) != -1) {
            curr *= 2;
        }

        // Binary search with found length
        return binarySearch(listy, data, 0, curr);
    }

    private int binarySearch(Listy listy, int data, int start, int end) {
        if (start >= end) {
            return listy.elementAt(start) == data ? start : -1;
        }

        int mid = (end - start) / 2 + start;

        if (listy.elementAt(mid) == data) {
            return mid;
        } else if (data < listy.elementAt(mid) || listy.elementAt(mid) == -1) {
            // If the data is -1 we are not in the list and must go with the left half
            return binarySearch(listy, data, start, mid);
        } else {
            return binarySearch(listy, data, mid + 1, end);
        }
    }

    public static class Listy {
        private int[] arr;
        public Listy(int[] arr) {
            this.arr = arr;
        }

        public int elementAt(int i) {
            if (i < 0 || i >= arr.length) {
                return -1;
            }
            return arr[i];
        }
    }

    /**
     * Problem:
     *
     * Given a sorted array of strings that is interspersed with empty strings,
     * write a method to find the location of a given string.
     *
     * Solution Explanation:
     *
     * This is another problem that can be solved with a variation of binary search.
     * Since not all entries will be able to help us narrow down the list, though,
     * when we encounter an empty string we will have to search for the next non-empty
     * string to be able to continue.
     */
    public int findStringWithEmptyStrings(String[] arr, String data) {
        if (arr == null) {
            return -1;
        }

        return binarySearchWithEmptyStrings(arr, data, 0, arr.length);
    }

    private int binarySearchWithEmptyStrings(String[] arr, String data, int start, int end) {
        if (start >= end) {
            return arr[start].equals(data) ? start : -1;
        }

        int mid = (end - start) / 2 + start;

        if (arr[mid].equals("")) {
            int left = mid - 1,
                    right = mid + 1;
            while (left >= start && arr[left].equals("") && right < end && arr[right].equals("")) {
                if (left - 1 < start && right + 1 > end - 1) {
                    return -1;
                }
                if (left >= start) {
                    left--;
                }
                if (right <= end - 1) {
                    right++;
                }
            }
            if (!arr[left].equals("")) {
                mid = left;
            } else {
                mid = right;
            }
        }

        if (arr[mid].equals(data)) {
            return mid;
        } else if (arr[mid].compareTo(data) > 0) {
            return binarySearchWithEmptyStrings(arr, data, start, mid);
        } else {
            return binarySearchWithEmptyStrings(arr, data, mid + 1, end);
        }
    }

    /**
     * Problem:
     *
     * Imagine you are reading in a stream of integers. Periodically, you wish to be
     * able to look up the rank of a number x (the number of values less than or equal to
     * x). Implement the data structures and algorithms to support these operations. That
     * is, implement the method track(int x), which is called when each number is generated,
     * and the method getRankOfNumber(int x) which returns the number of values less
     * than or equal to x (not including x itself.)
     *
     * Solution Explanation:
     *
     * We'll want to be able to insert and find elements in O(logn) time optimally.
     * To do this, we'll utilize a BST. We will store the data and also the number
     * of children that node has. This can be easily included by incrementing the
     * count everytime a node is touched when adding. Then, when retrieving the
     * rank, we'll want to look at the left subtree of everything that we pass to
     * make sure we include all data that is less than what we're searching for.
     */
    public static class RankTracker<T extends Comparable<? super T>> {
        RankNode<T> root;

        public RankTracker() {}

        public void track(T data) {
            if (root == null) {
                root = new RankNode<>(data);
            } else {
                root = add(root, data);
                root.numChildren++;
            }
        }

        private RankNode<T> add(RankNode<T> curr, T data) {
            if (curr == null) {
                return new RankNode<>(data);
            }

            if (curr.data.compareTo(data) <= 0) {
                curr.right = add(curr.right, data);
                curr.numChildren++;
                return curr;
            } else {
                curr.left = add(curr.left, data);
                curr.numChildren++;
                return curr;
            }
        }

        public int getRank(T data) {
            if (data == null || root == null) {
                return -1;
            }

            return getRank(root, data);
        }

        private int getRank(RankNode<T> curr, T data) {
            if (curr == null) {
                return -1;
            }

            if (curr.data.equals(data)) {
                return curr.left != null ? curr.left.numChildren + 1 : 0;
            } else if (curr.data.compareTo(data) < 0) {
                int rank = getRank(curr.right, data);
                if (rank == -1) {
                    return -1;
                }
                return rank + (curr.left != null ? curr.left.numChildren + 1 : 0) + 1;
            } else {
                return getRank(curr.left, data);
            }
        }
    }

    public static class RankNode<T> {
        RankNode<T> left, right;
        T data;
        int numChildren;

        public RankNode(T data) {
            this(data, null, null);
        }

        public RankNode(T data, RankNode<T> left, RankNode<T> right) {
            this.data = data;
            this.left = left;
            this.right = right;
        }
    }
}
