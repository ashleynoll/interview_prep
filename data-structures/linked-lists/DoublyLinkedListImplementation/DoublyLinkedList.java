/**
 * Implementation of a DoublyLinkedList
 */
public class DoublyLinkedList<T> implements DoublyLinkedListInterface<T> {
    private DoublyLinkedListNode<T> head;
    private DoublyLinkedListNode<T> tail;
    private int size;

    @Override
    public void addAtIndex(int index, T data) {
        if (data == null) {
            throw new IllegalArgumentException("Null data cannot be added to " +
                    "the linked list.");
        } else if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index to add cannot be " +
                    "beyond the size of the list or negative.");
        }

        if (size == 0) {
            head = new DoublyLinkedListNode<T>(data);
        } else if (index == 0) {
            head = new DoublyLinkedListNode<T>(data, null, head);
            head.getNext().setPrevious(head);
        } else if (index == size) {
            tail.setNext(new DoublyLinkedListNode<>(data, tail, null));
            tail = tail.getNext();
        } else {
            DoublyLinkedListNode<T> temp = getNodeAtIndex(index);
            temp.setPrevious(new DoublyLinkedListNode<>(data, temp.getPrevious(), temp));
            temp.getPrevious().getPrevious().setNext(temp.getPrevious());
        }

        if (++size == 1) {
            tail = head;
        }
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
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index to remove cannot be " +
                    "beyond the size of the list or negative.");
        }

        T data;

        if (size == 1) {
            data = head.getData();
            head = null;
        } else if (index == 0) {
            data = head.getData();
            head = head.getNext();
            head.setPrevious(null);
        } else if (index == size - 1) {
            data = tail.getData();
            tail = tail.getPrevious();
            tail.setNext(null);
        } else {
            DoublyLinkedListNode<T> temp = getNodeAtIndex(index);
            temp.getPrevious().setNext(temp.getNext());
            temp.getNext().setPrevious(temp.getPrevious());
            data = temp.getData();
        }

        if (--size == 0) {
            tail = null;
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
    public boolean removeFirstOccurrence(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Null data cannot be removed from " +
                    "the linked list as it does not exist.");
        } else if (size == 0) {
            return false;
        }

        DoublyLinkedListNode<T> temp = head;
        for (int i = 0; i < size && !temp.getData().equals(data); i++) {
            temp = temp.getNext();
        }

        if (temp != null && temp.getData().equals(data)) {
            if (temp == head) {
                removeFromFront();
                return true;
            } else if (temp == tail) {
                removeFromBack();
                return true;
            }
            temp.getNext().setPrevious(temp.getPrevious());
            temp.getPrevious().setNext(temp.getNext());
            size --;
            return true;
        }

        return false;
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index to access cannot be " +
                    "beyond the size of the list or negative.");
        }
        return getNodeAtIndex(index).getData();
    }

    @Override
    public Object[] toArray() {
        Object[] ret = new Object[size];
        DoublyLinkedListNode<T> temp = head;
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
    public DoublyLinkedListNode<T> getHead() {
        return head;
    }

    @Override
    public DoublyLinkedListNode<T> getTail() {
        return tail;
    }

    private DoublyLinkedListNode<T> getNodeAtIndex(int index) {
        DoublyLinkedListNode<T> temp;
        if (index <= size / 2 ) {
            temp = head;
            for (int i = 0; i < index; i++) {
                temp = temp.getNext();
            }
        } else {
            temp = tail;
            for (int i = size - 1; i > index; i--) {
                temp = temp.getPrevious();
            }
        }
        return temp;
    }
}
