import edu.princeton.cs.algs4.StdRandom;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] items;
    private int tail;

    // construct an empty randomized queue
    public RandomizedQueue() {
        items = (Item[]) new Object[1];
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<Integer> randomizedQueue = new RandomizedQueue<>();
        randomizedQueue.enqueue(1);
        randomizedQueue.isEmpty();
        randomizedQueue.size();
        randomizedQueue.sample();
        randomizedQueue.dequeue();
        randomizedQueue.iterator().hasNext();
        randomizedQueue.iterator().next();
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return tail == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return tail;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        if (tail == items.length) {
            resize(items.length * 2);
        }
        items[tail++] = item;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        int random = StdRandom.uniform(tail);
        Item item = items[random];
        items[random] = null;
        tail--;
        resizeForDequeue(random);
        if (tail > 0 && tail == items.length / 4) {
            resize(items.length / 2);
        }
        return item;
    }

    private void resizeForDequeue(int index) {
        for (int i = index; i < items.length - 1; i++) {
            items[i] = items[i + 1];
        }
    }

    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < tail; i++) {
            copy[i] = items[i];
        }
        items = copy;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        int random = StdRandom.uniform(tail);
        return items[random];
    }

    public Iterator<Item> iterator() {
        return new RandomizedIterator();
    }

    private class RandomizedIterator implements Iterator<Item> {
        int remainingNumberElements = tail;

        public boolean hasNext() {
            return remainingNumberElements != 0;
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            int random = StdRandom.uniform(remainingNumberElements);
            Item itemToReturn = items[random];
            if (random == remainingNumberElements - 1) {
                remainingNumberElements--;
                return itemToReturn;
            } else {
                items[random] = items[remainingNumberElements - 1];
                items[remainingNumberElements - 1] = itemToReturn;
                remainingNumberElements--;
                return itemToReturn;
            }

        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
