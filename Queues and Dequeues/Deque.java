import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private Node first;
    private Node last;
    private static int n;

    private class Node<Item> {
        Node next;
        Node prev;
        Item item;

        public Node(Item item) {
            this.item = item;
        }
    }

    // construct an empty deque
    public Deque() {

    }

    // is the deque empty?
    public boolean isEmpty() {
        return n == 0;
    }

    // return the number of items on the deque
    public int size() {
        return n;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        if (n == 0) {
            first = new Node(item);
            last = first;
        }
        else {
            Node tempFirst = first;
            first = new Node(item);
            first.next = tempFirst;
            tempFirst.prev = first;
        }
        n++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        if (n == 0) {
            first = new Node(item);
            last = first;
        }
        else {
            Node tempLast = new Node(item);
            last.next = tempLast;
            last = tempLast;
            tempLast.prev = last;
        }
        n++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (n == 0) {
            throw new NoSuchElementException();
        }

        /*
        2 cases: if the node has 1 object or 2 objects
         */

        Node temp = first;
        if (n == 1) {
            first = null;
            last = null;
        }
        else {
            first = temp.next;
        }

        n--;
        return (Item) temp.item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (n == 0) {
            throw new NoSuchElementException();
        }

        Node temp = last;
        if (n == 1) {
            first = null;
            last = null;
        }
        else {
            last = temp.prev;
        }

        n--;
        return (Item) temp.item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new dequeIterator();
    }

    private class dequeIterator implements Iterator<Item> {
        private Node curr = first;

        public boolean hasNext() {
            return curr != null;
        }

        public Item next() {
            if (n == 0) {
                throw new NoSuchElementException();
            }
            Item curr_item = (Item) curr.item;
            curr = curr.next;
            return curr_item;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<String> my_DQ = new Deque<String>();
        my_DQ.addFirst("A");
        my_DQ.addFirst("A");
        my_DQ.addLast("I");
        my_DQ.addLast("B");
        my_DQ.addFirst("p");

        Iterator<String> it = my_DQ.iterator();


        while (it.hasNext()) {
            String x = it.next();
            System.out.println(x);
        }


    }

}
