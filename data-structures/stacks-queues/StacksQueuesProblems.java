import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Stack;

/**
 * Various problems including stacks and queues
 */
public class StacksQueuesProblems {

    /**
     * Problem:
     *
     * Implement a data structure SetOfStacks that mimics splitting a stack
     * of plates into smaller stacks. SetOfStacks should be composed of several
     * stacks and should create a new stack once the previous one exceeds capacity.
     * push() and pop() should behave identically to a single stack. Implement a
     * function popAt(int index) which performs a pop operation on a specific
     * sub-stack
     *
     * Solution Explanation:
     *
     * Maintain an array list of stacks. When an item is pushed onto the stack,
     * find the last stack and push to it. When an item is popped, find the
     * last stack and pop, removing the stack if it is then empty. When popping
     * at a given index, we will decide to not roll back elements as this would
     * result in a large time trade-off. However, if all stacks are assumed full
     * this could cause problems. Discuss decision with interviewer.
     */
    public static class SetOfStacks<T> {
        private ArrayList<Stack<T>> stacks;
        private final int MAX_SIZE = 5;

        public SetOfStacks() {
            stacks = new ArrayList<>();
        }

        public void push(T data) {
            if (data == null) {
                return;
            }
            int index = stacks.size() - 1;
            // Solution for not being able to initiate stack with size constraint
            if (index == -1 || stacks.get(index).size() >= MAX_SIZE) {
                stacks.add(++index, new Stack<>());
            }
            stacks.get(index).push(data);
        }

        public T pop() {
            if (stacks.isEmpty()) {
                return null;
            }
            int index = stacks.size() - 1;
            T data = stacks.get(index).pop();
            if (stacks.get(index).size() == 0) {
                stacks.remove(index);
            }
            return data;
        }

        public T popAt(int index) {
            if (stacks.get(index) == null) {
                return null;
            }

            return stacks.get(index).pop();
        }
    }

    /**
     * Problem:
     *
     * Implement a MyQueue class which implements a queue using two stacks.
     *
     * Solution Explanation:
     *
     *
     */
    public static class MyQueue<T> {
        private Stack<T> dequeueStack, enqueueStack;

        public MyQueue() {
            dequeueStack = new Stack<>();
            enqueueStack = new Stack<>();
        }

        public void enqueue(T data) {
            if (data == null) {
                return;
            }

            enqueueStack.push(data);
        }

        public T dequeue() {
            if (dequeueStack.isEmpty()) {
                if (enqueueStack.isEmpty()) {
                    return null;
                }
                while (!enqueueStack.isEmpty()) {
                    dequeueStack.push(enqueueStack.pop());
                }
            }

            return dequeueStack.pop();
        }
    }

    /**
     * Problem:
     *
     * Write a program to sort a stack such that the smallest items are on the top.
     * You can use an additional temporary stack, but you may not copy the elements
     * into any other data structure (such as an array). The stack supports the
     * following operations: push, pop, peek, and isEmpty.
     *
     * Solution Explanation:
     *
     * We will use the second stack to hold an ongoing sorted stack. We will
     * dequeue from the original stack into the sorted stack while the top of the
     * sorted stack is larger than the top of the original stack. When an element
     * is found that is larger in the original stack, we will pop it into a temp
     * variable. Then we will pop from the sorted stack back onto the original stack
     * until the temp variable is less than the top of the sorted. We then start
     * over, popping from the original to the sorted.
     *
     * The time complexity of this algorithm is O(n^2)
     */
    public Stack<Integer> sortStack(Stack<Integer> stack) {
        if (stack.isEmpty()) {
            return stack;
        }
        Stack<Integer> sorted = new Stack<>();
        sorted.push(stack.pop());

        while (!stack.isEmpty()) {
            while (!stack.isEmpty() && stack.peek() < sorted.peek()) {
                sorted.push(stack.pop());
            }

            if (stack.isEmpty()) {
                return sorted;
            }

            int larger = stack.pop();

            while (!sorted.isEmpty() && sorted.peek() < larger) {
                stack.push(sorted.pop());
            }

            sorted.push(larger);
        }

        return sorted;
    }

    /**
     * Problem:
     *
     * An animal shelter, which holds only dogs and cats, operates on a strictly
     * "first in, first out" basis. People must adopt either the "oldest" (based
     * on arrival time) of all animals at the shelter, or they can select whether
     * they would prefer a dog or a cat (and will receive the oldest animal of
     * that type). They cannot select which specific animal they would like.
     * Create the data structures to maintain this system and implement operations
     * such as enqueue, dequeueAny, dequeueDog, and dequeueCat. You may use the built-in
     * LinkedList data structure
     *
     * Solution Explanation:
     *
     * To keep track of the animal, it's age, and an id for testing, we will create
     * an enum to differentiate between dogs and cats and a pet class to hold the
     * type, id, and relative age. As pets are enqueued to the shelter, they are
     * added to the back of their respective list and a counter is applied to be their age
     * and is incremented. When a dog or cat is requested, the pet is pulled from
     * the front of their list. If any is requested, we will peek at the first
     * element of both and choose the one with the smaller age which will be the
     * one added earliest.
     */
    public enum Animal {
        DOG, CAT
    }
    public static class Pet {
        private Animal type;
        private int id;
        private int age;

        public Pet(Animal type, int id) {
            this.type = type;
            this.id = id;
        }

        public Animal getType() {
            return type;
        }

        public int getId() {
            return id;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public int getAge() {
            return age;
        }
    }
    public static class ShelterQueue {
        private LinkedList<Pet> catQueue, dogQueue;
        int count;

        public ShelterQueue() {
            catQueue = new LinkedList<>();
            dogQueue = new LinkedList<>();
        }

        public void enqueue(Pet pet) {
            if (pet == null) {
                return;
            }
            pet.setAge(count++);
            if (pet.getType() == Animal.CAT) {
                catQueue.addLast(pet);
            } else {
                dogQueue.addLast(pet);
            }
        }

        public Pet dequeueAny() {
            if (catQueue.isEmpty()) {
                return dogQueue.removeFirst();
            } else if (dogQueue.isEmpty()) {
                return catQueue.removeFirst();
            }
            return catQueue.peek().getAge() < dogQueue.peek().getAge()
                    ? catQueue.removeFirst() : dogQueue.removeFirst();
        }

        public Pet dequeueDog() {
            return dogQueue.removeFirst();
        }

        public Pet dequeueCat() {
            return catQueue.removeFirst();
        }
    }
}
