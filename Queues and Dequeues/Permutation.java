/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;

public class Permutation {
    public static void main(String[] args) {

        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> my_queue = new RandomizedQueue<String>();
        while (!StdIn.isEmpty()) {
            String c = StdIn.readString();
            my_queue.enqueue(c);
        }


        for (int i = 0; i < k; i++) {
            String c = my_queue.dequeue();
            System.out.println(c);
        }


    }
}
