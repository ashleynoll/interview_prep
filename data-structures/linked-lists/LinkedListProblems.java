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
            while (scanner.next != null) {
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

    /**
     * Problem:
     *
     * Implement an algorithm to find the kth to last element of a singly
     * linked list
     *
     * Solution Explanation:
     *
     * Instantiate a pointer and move said pointer k nodes into the list.
     * If there are not k nodes to move into, return -1 as there is no kth
     * to last element. Else, create a new pointer to the front and move both pointers
     * at the same rate down the list until the later pointer reaches the end. Then
     * return the data from the earlier pointer's node.
     *
     */
    public int returnKthToLast(Node<Integer> head, int k) {
        if (k < 0 || head == null) {
            return -1;
        }

        Node<Integer> runner = head,
                ret = head;

        int move = k;
        while (runner != null && move > 0) {
            runner = runner.next;
            move--;
        }

        if (runner == null) {
            // There are not k elements in the list;
            return -1;
        }

        while (runner.next != null) {
            runner = runner.next;
            ret = ret.next;
        }

        return ret.data;
    }

    /**
     * Problem:
     *
     * Implement an algorithm to delete a node in the middle (i.e. any node but
     * the first and last node, not necessarily the exact middle) of a singly
     * linked list, given only access to that node.
     *
     * Solution Explanation:
     *
     * Since there's no way to go back and change the pointer to the given node,
     * you cannot get rid of that node. Instead, take the data from the next node,
     * put it into the current node, and then remove the next node.
     *
     */
    public void deleteMiddleNode(Node<Integer> deleteNode) {
        if (deleteNode == null || deleteNode.next == null) {
            return;
        }

        deleteNode.data = deleteNode.next.data;
        deleteNode.next = deleteNode.next.next;
    }

    /**
     * Problem:
     *
     * Write code to partition a linked lsit around a value x, such that all
     * nodes less than x come before all nodes greater than or equal to x.
     * If x is contained within the list, the values of x only need to be after
     * the elements less than x. The partition element x can appear anywhere in
     * the "right partition"; it does not need to appear between the left and
     * right partitions.
     *
     * Solution Explanation:
     *
     * Create two pointers, one to look for greater or equal to values, and another
     * to look for less than values. Initiate a loop to continue until one of these
     * pointers is null. Move the greater than pointer until you find a value that
     * is not less than the partition. If the pointer becomes null at any point,
     * return. If the greater pointer passed the less pointer, move the less pointer
     * to one ahead of the greater so you don't end up swapping backwards. Then,
     * move the less pointer to the next value that is lower than the partition.
     * Swap the stored values and move both pointers forwards one. Repeat until
     * the pointer becomes null at any point.
     */
    public void partitionLinkedList(Node<Integer> head, int part) {
        Node<Integer> greater = head,
                less = head;
        int gIndex = 0, lIndex = 0;

        while (greater != null && less != null) {
            while (greater != null && greater.data < part) {
                greater = greater.next;
                gIndex++;
            }

            if (greater == null) {
                return;
            }

            if (gIndex > lIndex) {
                lIndex = gIndex + 1;
                less = greater.next;
            }

            while (less != null && less.data >= part) {
                less = less.next;
                lIndex++;
            }

            if (less == null) {
                return;
            }

            int temp = less.data;
            less.data = greater.data;
            greater.data = temp;

            less = less.next;
            greater = greater.next;

            lIndex++;
            gIndex++;
        }
    }

    /**
     * Problem:
     *
     * You have two numbers represented by a linked list, where each node contains
     * a single digit. The digits are stored in reverse order, such that the 1's
     * digit is at the head of the list. Write a function that adds the two
     * numbers and returns the sum as a linked list.
     *
     * Follow-up:
     *
     * Suppose the digits are stored in forward order. Repeat the above problem.
     *
     * Solution Explanation:
     *
     * Backwards: Going backwards, you only need to iterate over the two lists adding
     * the two numbers and the carryover into a new node before calculating the
     * new carryover and modding the sum by 10. Continue this process while there's
     * a node in either of the lists or the carryover is greater than 0.
     *
     * Forwards: Similarly to backwards, you'll be summing the two list nodes as
     * well as the carryover. However, to add them from smallest to largest digit,
     * you'll need to reverse the linked lists to do so.
     */
    public Node<Integer> sumListsBackwards(Node<Integer> list1, Node<Integer> list2) {
        if (list1 == null) {
            return list2;
        } else if (list2 == null) {
            return list1;
        }

        int carryOver = 0;
        Node<Integer> head = new Node<>(null),
                temp = head;
        while (list1 != null || list2 != null || carryOver > 0) {
            temp.next = new Node<>(carryOver);
            temp = temp.next;
            if (list1 != null) {
                temp.data += list1.data;
                list1 = list1.next;
            }

            if (list2 != null) {
                temp.data += list2.data;
                list2 = list2.next;
            }

            carryOver = temp.data / 10;
            temp.data %= 10;
        }
        return head.next;
    }

    public Node<Integer> sumListsForwards(Node<Integer> list1, Node<Integer> list2) {
        if (list1 == null || list2 == null) {
            return list1 == null ? list2 : list1;
        }

        list1 = reverseLinkedList(list1);
        list2 = reverseLinkedList(list2);

        int carryOver = 0;
        Node<Integer> head = null;
        while (list1 != null || list2 != null || carryOver > 0) {
            head = new Node<>(carryOver, head);
            if (list1 != null) {
                head.data += list1.data;
                list1 = list1.next;
            }

            if (list2 != null) {
                head.data += list2.data;
                list2 = list2.next;
            }

            carryOver = head.data / 10;
            head.data %= 10;
        }
        return head;
    }

    /**
     * Helper method to reverse a given linked list
     * @param head start of a linked list
     * @return reversed list
     */
    private Node<Integer> reverseLinkedList(Node<Integer> head) {
        if (head == null) {
            return null;
        }

        Node<Integer> prev = null,
                     curr = head,
                     next;

        while (curr != null) {
            next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        }

        return prev;
    }

    /**
     * Problem:
     *
     * Implement a function to check if a linked list is a palindrome.
     *
     * Solution Explanation:
     *
     * Find the middle of the linked list by having two pointers go through
     * the list, one at speed of one and the other at a speed of two. When the
     * faster pointer is null, the slower pointer will be at the middle. Reverse
     * the first half of the list. Now, start two pointers at the middle and go
     * through the lists, one forwards and one backwards, until there's a mismatch
     * of data or they are null.
     */
    public boolean isLinkedListPalindrome(Node<Character> head) {
        if (head == null || head.next == null) {
            return true;
        }

        Node<Character> fast = head.next,
                    slow = head;
        boolean isOddLength = true;

        while (fast != null) {
            slow = slow.next;
            if (fast.next == null) {
                fast = null;
                isOddLength = false;
            } else {
                fast = fast.next.next;
            }
        }

        Node<Character> prev = null,
                    curr = head,
                    next = head.next;

        while (curr != slow) {
            curr.next = prev;
            prev = curr;
            curr = next;
            next = curr.next;
        }

        if (isOddLength) {
            curr = curr.next;
        }

        Node<Character> left = prev,
                        right = curr;
        while (left != null && right != null) {
            if (left.data != right.data) {
                return false;
            }

            left = left.next;
            right = right.next;
        }

        return true;
    }

    /**
     * Problem:
     *
     * Given two (singly) linked lists, determine if the two lists intersect.
     * Return the intersecting node. Note that the intersection is defined based
     * on reference, not value. That is, if the kth node of the first linked list
     * is the exact same node. (by reference) as the jth node of the second linked
     * list, then they are intersecting.
     *
     * Solution Explanation:
     *
     * If the two lists merge, then the last node will have to be the same. Reverse
     * both lists and compare from the end forwards. If the next node does not
     * have the same address, return the current node.
     *
     */
    public Node<Integer> areLinkedListsIntersecting(Node<Integer> list1, Node<Integer> list2) {
        if (list1 == null || list2 == null) {
            return null;
        }

        int len1 = countLength(list1),
                len2 = countLength(list2);

        while (len1 > len2) {
            list1 = list1.next;
            len1--;
        }

        while (len2 > len1) {
            list2 = list2.next;
            len2--;
        }

        while (list1 != null && list2 != null) {
            if (list1 == list2) {
                return list1;
            }
            list1 = list1.next;
            list2 = list2.next;
        }

        return null;
    }

    private int countLength(Node<Integer> head) {
        int count = 0;
        while (head != null) {
            head = head.next;
            count++;
        }
        return count;
    }

    /**
     * Problem:
     *
     * Given a circular linked list, implement an algorithm that returns the
     * node at the beginning of the loop.
     *
     * Solution Explanation:
     *
     * Although the question specified it was a circular list, we'll still
     * use cycle detection in this solution. Initiate two pointers at the
     * head of the linked list, one that will go at speed of 1 and another that
     * will go at speed of two. If there's a loop, these two pointers must
     * intersect. When they do, stop the loop.
     *
     * Now, given that the both pointers had to have gone some distance (a) to the
     * beginning of the loop and then some distance (b) to the eventual collision point,
     * along with some constant (x and y) amount of cycles (c), we have that:
     *
     * slow = a + b + cx & fast = a + b + cy
     *
     * The fast pointer went twice as far as the slow pointer so:
     *
     * 2slow = fast
     *
     * 2a + 2b + 2cx = a + b + cy
     * => a + b = c(2x + y)
     * => a + b = cz
     *
     * Therefore, the distance of to the cycle head plus the distance to the
     * collision point is a multiple of the overall cycle length. The collision
     * point (d) occurred b nodes within the cycle of length c, so
     *
     * d + (c - b) = Goal
     *
     * We know from earlier that a + b = c => a = c - b so
     *
     * d + a = Goal
     *
     * Therefore, if you start another pointer at the beginning of your list and
     * another at the collision point, advancing at the same rate will result
     * in a collision at the goal as they will both be traveling a nodes.
     */
    public Node<Integer> findHeadOfCycle(Node<Integer> head) {
        if (head == null) {
            return head;
        }

        Node<Integer> slow = head,
                fast = head;
        boolean foundLoop = false;

        while (fast != null && !foundLoop) {
            slow = slow.next;
            fast = fast.next == null ? fast.next : fast.next.next;
            if (slow == fast) {
                foundLoop = true;
            }
        }

        if (fast == null) {
            return null;
        }

        Node<Integer> start = head;
        boolean isStarting = true;

        while (start != slow || (!isStarting && start == head)) {
            start = start.next;
            slow = slow.next;
            isStarting = false;
        }

        return start;
    }
}
