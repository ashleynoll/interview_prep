import java.util.HashSet;
import java.util.Set;

/**
 * An assortment of problems requiring a Linked List for the optimal solution.
 */
public class LinkedListProblems {

    public static class Node<T> {
        public T data;
        public Node<T> next;

        public Node(T data) {
            this(data, null);
        }

        public Node(T data, Node<T> next) {
            this.data = data;
            this.next = next;
        }

         public boolean equals(Object o) {
            if (o == this) {
                return true;
            } else if (!(o instanceof Node)) {
                return false;
            }

            Node<T> node = (Node<T>) o;
            return this.data.equals(node.data);
         }
    }

    /**
     *
     * Problem:
     *
     * Write code to remove duplicates from an unsorted linked list.
     *
     * How would you solve this problem if a temporary buffer is not allowed?
     *
     * Solution Explanation:
     *
     * The first implementation uses O(n) extra space to store encountered values
     * in a hash set. If the value is encountered again, it is removed. This is
     * an O(n) algorithm.
     *
     * The second implementation uses no extra space, but is O(n^2). You use an
     * additional pointer to scan ahead of the current node and remove all duplicates.
     *
     */
    public Node<Integer> removeDuplicatesExtraSpace(Node<Integer> head) {
        if (head == null) {
            return null;
        }

        Node<Integer> temp = head;
        Set<Integer> encountered = new HashSet<>();
        encountered.add(temp.data);

        while (temp.next != null) {
            if (encountered.contains(temp.next.data)) {
                temp.next = temp.next.next;
            } else {
                encountered.add(temp.next.data);
                temp = temp.next;
            }
        }

        return head;
    }

    public Node<Integer> removeDuplicatesMoreTime(Node<Integer> head) {
        if (head == null) {
            return null;
        }

        Node<Integer> temp = head;

        while (temp != null) {
            Node<Integer> scanner = temp;
            while (scanner.next != null) {r
                if (temp.data.equals(scanner.next.data)) {
                    scanner.next = scanner.next.next;
                } else {
                    scanner = scanner.next;
                }
            }
            temp = temp.next;
        }
        return head;
    }
}
