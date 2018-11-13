import java.util.NoSuchElementException;

/**
 * Implementation of a Singly Linked List
 */
public class SinglyLinkedList<T> implements LinkedListInterface<T> {
    private LinkedListNode<T> head;
    private LinkedListNode<T> tail;
    private int size;

    @Override
    public void addAtIndex(int index, T data) {
        if (data == null) {
            throw new IllegalArgumentException("Provided data cannot be null.");
        } else if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("Provided index cannot be beyond " +
                    "the size of the list or negative.");
        }

        LinkedListNode<T> temp = head;
        if (index == 0) {
            head = new LinkedListNode<>(data, head);
            temp = head;
        } else {
            for (int i = 1; i < index; i++) {
                temp = temp.getNext();
            }
            temp.setNext(new LinkedListNode<>(data, temp.getNext()));
            temp = temp.getNext();
        }

        if (index == size) {
            tail = temp;
        }
        size++;
    }

    @Override
    public void addToFront(T data) {
        addAtIndex(0, data);
    }

    @Override
    public void addToBack(T data) {
        addAtIndex(size, data);
    }

    @Override
    public T removeAtIndex(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Cannot remove from index beyond " +
                    "the size of the list or a negative index.");
        }
        T data;
        LinkedListNode<T> temp = head;
        if (index == 0) {
            data = head.getData();
            head = head.getNext();
            temp = head;
        } else {
            for (int i = 1; i < index; i++) {
                temp = temp.getNext();
            }
            data = temp.getNext().getData();
            temp.setNext(temp.getNext().getNext());
        }
        if (index == --size) {
            tail = temp;
        }
        return data;
    }

    @Override
    public T removeFromFront() {
        return size == 0 ? null : removeAtIndex(0);
    }

    @Override
    public T removeFromBack() {
        return size == 0 ? null : removeAtIndex(size - 1);
    }

    @Override
    public T removeFirstOccurrence(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Provided data cannot be null.");
        } else if (size == 0) {
            throw new NoSuchElementException("The provided data was not found " +
                    "in the list as it is empty.");
        }
        LinkedListNode<T> temp = head;
        int index = 0;
        T ret;
        if (head.getData().equals(data)) {
            ret = head.getData();
            head = head.getNext();
            temp = head;
        } else {
            index++;
            while (temp.getNext() != null && !temp.getNext().getData().equals(data)) {
                temp = temp.getNext();
                index++;
            }
            if (temp.getNext() == null) {
                throw new NoSuchElementException("The provided data was not found " +
                        "in the list.");
            }
            ret = temp.getNext().getData();
            temp.setNext(temp.getNext().getNext());
        }

        if (index == --size) {
            tail = temp;
        }
        return ret;
    }

    @Override
    public T get(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Cannot access data beyond the " +
                    "size of the list or at a negative index.");
        }
        if (index == size - 1) {
            return tail.getData();
        }
        LinkedListNode<T> temp = head;
        for (int i = 0; i < index; i++) {
            temp = temp.getNext();
        }
        return temp.getData();
    }

    @Override
    public Object[] toArray() {
        Object[] ret = new Object[size];
        LinkedListNode<T> temp = head;
        for (int i = 0; i < size; i++) {
            ret[i] = temp.getData();
            temp = temp.getNext();
        }
        return ret;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }

    @Override
    public LinkedListNode<T> getHead() {
        return head;
    }

    @Override
    public LinkedListNode<T> getTail() {
        return tail;
    }
}
