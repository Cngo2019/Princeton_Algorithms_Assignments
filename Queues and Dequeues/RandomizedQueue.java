import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    // int N will represent the next index to push an element to the queue (will usually point to null).
    private int N = 0;
    private Item[] s;

    // construct an empty randomized queue
    public RandomizedQueue() {
        s = (Item[]) new Object[1];
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return N == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return N;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        if (N == s.length) {
            Item[] new_s = (Item[]) new Object[2 * s.length];
            for (int i = 0; i < s.length; i++) {
                new_s[i] = s[i];
            }
            s = new_s;
        }

        s[N++] = item;
    }

    // remove and return a random item
    public Item dequeue() {
        if (N == 0) {
            throw new NoSuchElementException();
        }

        int k = StdRandom.uniform(0, N);
        Item random_item = s[k];
        s[k] = s[--N]; //Performs a swap
        s[N] = null; //Avoids loitering
        //N represents the number of items in the list.

        if (N > 0 && N == s.length / 4) {
            //resize:
            Item[] new_s = (Item[]) new Object[s.length / 2];
            for (int i = 0; i < N; i++) {
                new_s[i] = s[i];
            }
            s = new_s;
        }

        return random_item;

    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (N == 0) {
            throw new NoSuchElementException();
        }

        int k = StdRandom.uniform(0, N);
        return s[k];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomQueueIterator();
    }

    private class RandomQueueIterator implements Iterator<Item> {
        int k = N;

        public boolean hasNext() {
            return k != 0;
        }

        public Item next() {
            if (N == 0) {
                throw new NoSuchElementException();
            }
            int rand_int = StdRandom.uniform(0, k);
            Item rand_item = s[rand_int];
            s[rand_int] = s[--k];
            s[k] = null;
            return rand_item;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<String> x = new RandomizedQueue<String>();
        x.enqueue("a");
        x.enqueue("B");
        x.enqueue("c");
        x.enqueue("D");
        x.enqueue("D");
        x.enqueue("D");
        System.out.println(x.dequeue());
        System.out.println(x.dequeue());

        System.out.println("iteratble");
        Iterator<String> it = x.iterator();
        while (it.hasNext()) {
            String c = it.next();
            System.out.println(c);
        }
    }

}
