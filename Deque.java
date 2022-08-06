import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private Node first;
    private Node last;
    private int count;

    // construct an empty deque
    public Deque() {
    }

    private class Node {
        Item item;
        Node next;
        Node previous;
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<>();
        deque.isEmpty();
        deque.addFirst(1);
        deque.addLast(2);
        deque.iterator().next();
        deque.iterator().hasNext();
        deque.removeFirst();
        deque.removeLast();
    }

    // is the deque empty?
    public boolean isEmpty() {
        return count == 0;
    }

    // return the number of items on the deque
    public int size() {
        return count;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        if (first == null) {
            createFirstElement(item);
        } else {
            Node oldFirst = first;
            first = new Node();
            first.item = item;
            first.next = oldFirst;
            oldFirst.previous = first;
        }

        count++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        if (first == null) {
            createFirstElement(item);
        } else {
            Node oldLast = last;
            last = new Node();
            last.item = item;
            last.previous = oldLast;
            oldLast.next = last;
        }

        count++;
    }

    private void createFirstElement(Item item) {
        first = new Node();
        first.item = item;
        last = first;
    }


    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        } else if (count == 1) {
            return removeLastRemainingElement();
        } else {
            Item oldFirst = first.item;
            first = first.next;
            first.previous = null;

            count--;
            return oldFirst;
        }
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        } else if (count == 1) {
            return removeLastRemainingElement();
        } else {
            Item oldLast = last.item;
            last = last.previous;
            last.next = null;

            count--;
            return oldLast;
        }
    }

    private Item removeLastRemainingElement() {
        Item oldFirst = first.item;
        first = null;
        last = null;
        count--;
        return oldFirst;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Item item = current.item;
            current = current.next;
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
