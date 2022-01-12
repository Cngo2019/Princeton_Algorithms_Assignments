import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class SAP {
    private Digraph G;

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        if (G.equals(null))
            throw new IllegalArgumentException();
        this.G = new Digraph(G);
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        if (v >= G.V() || w >= G.V() || v <= -1 || w <= -1)
            throw new IllegalArgumentException();
        //Starting conditions.

        boolean[] v_visited = new boolean[G.V()];
        boolean[] w_visited = new boolean[G.V()];
        int[] v_dist = new int[G.V()];
        int[] w_dist = new int[G.V()];

        Queue<Integer> v_queue = new Queue<Integer>();
        Queue<Integer> w_queue = new Queue<Integer>();

        v_visited[v] = true;
        w_visited[w] = true;
        v_dist[v] = 0;
        w_dist[w] = 0;
        if (v == w) {
            return 0;
        }
        v_queue.enqueue(v);
        w_queue.enqueue(w);

        /**
         * Why not just make them both go at the same time?
         */

        int curr = -1;
        int distance = 0;
        int best_length = G.E() * 2;
        int best_ancestor = -1;
        boolean ancestor_found = false;
        while (!w_queue.isEmpty() || !v_queue.isEmpty()) {
            if (!v_queue.isEmpty()) {
                curr = v_queue.dequeue();
                for (int k : G.adj(curr)) {
                    if (!v_visited[k]) {
                        //  System.out.println("For vertex V we are enqueueing neighbor " + k);
                        v_queue.enqueue(k);
                        v_visited[k] = true;
                        //System.out.println("Has distance " + (distance + 1));
                        v_dist[k] = v_dist[curr] + 1;
                        if (v_visited[k] && w_visited[k] && (v_dist[k] + w_dist[k] < best_length)) {
                            best_length = v_dist[k] + w_dist[k];
                            best_ancestor = k;
                            ancestor_found = true;
                        }
                    }
                }
            }

            if (!w_queue.isEmpty()) {
                curr = w_queue.dequeue();
                for (int k : G.adj(curr)) {
                    if (!w_visited[k]) {
                        // System.out.println("For vertex W we are enqueueing neighbor " + k);
                        w_queue.enqueue(k);
                        w_visited[k] = true;
                        // System.out.println("has distance " + (distance + 1));
                        w_dist[k] = w_dist[curr] + 1;
                        if (v_visited[k] && w_visited[k] && (v_dist[k] + w_dist[k] < best_length)) {
                            best_length = v_dist[k] + w_dist[k];
                            best_ancestor = k;
                            ancestor_found = true;
                        }
                    }
                }
            }

            distance++;


        }
        if (ancestor_found)
            return best_length;
        else
            return -1;

    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        if (v >= G.V() || w >= G.V() || v <= -1 || w <= -1)
            throw new IllegalArgumentException();
        //Starting conditions.

        boolean[] v_visited = new boolean[G.V()];
        boolean[] w_visited = new boolean[G.V()];
        int[] v_dist = new int[G.V()];
        int[] w_dist = new int[G.V()];

        Queue<Integer> v_queue = new Queue<Integer>();
        Queue<Integer> w_queue = new Queue<Integer>();

        v_visited[v] = true;
        w_visited[w] = true;
        v_dist[v] = 0;
        w_dist[w] = 0;
        if (v == w) {
            return v;
        }
        v_queue.enqueue(v);
        w_queue.enqueue(w);

        /**
         * Why not just make them both go at the same time?
         */

        int curr = -1;
        int distance = 0;
        int best_length = G.E() * 2;
        int best_ancestor = -1;
        boolean ancestor_found = false;
        while (!w_queue.isEmpty() || !v_queue.isEmpty()) {
            if (!v_queue.isEmpty()) {
                curr = v_queue.dequeue();
                for (int k : G.adj(curr)) {
                    if (!v_visited[k]) {
                        //  System.out.println("For vertex V we are enqueueing neighbor " + k);
                        v_queue.enqueue(k);
                        v_visited[k] = true;
                        //System.out.println("Has distance " + (distance + 1));
                        v_dist[k] = v_dist[curr] + 1;
                        if (v_visited[k] && w_visited[k] && (v_dist[k] + w_dist[k] < best_length)) {
                            best_length = v_dist[k] + w_dist[k];
                            best_ancestor = k;
                            ancestor_found = true;
                        }
                    }
                }
            }

            if (!w_queue.isEmpty()) {
                curr = w_queue.dequeue();
                for (int k : G.adj(curr)) {
                    if (!w_visited[k]) {
                        // System.out.println("For vertex W we are enqueueing neighbor " + k);
                        w_queue.enqueue(k);
                        w_visited[k] = true;
                        // System.out.println("has distance " + (distance + 1));
                        w_dist[k] = w_dist[curr] + 1;
                        if (v_visited[k] && w_visited[k] && (v_dist[k] + w_dist[k] < best_length)) {
                            best_length = v_dist[k] + w_dist[k];
                            best_ancestor = k;
                            ancestor_found = true;
                        }
                    }
                }
            }

            distance++;


        }
        if (ancestor_found)
            return best_ancestor;
        else
            return -1;

    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        if (v == null || w == null)
            throw new IllegalArgumentException();

        for (int k : v) {
            if (k <= -1 || k >= G.V())
                throw new IllegalArgumentException();
        }
        for (int k : w) {
            if (k <= -1 || k >= G.V())
                throw new IllegalArgumentException();
        }
        int min = G.V() + 1;
        for (int i : v) {
            for (int j : w) {
                int curr = length(i, j);
                if (curr < min) {
                    min = curr;
                }
            }
        }

        if (min == G.V() + 1)
            return -1;

        return min;
    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        if (v == null || w == null)
            throw new IllegalArgumentException();

        for (int k : v) {
            if (k <= -1 || k >= G.V())
                throw new IllegalArgumentException();
        }
        for (int k : w) {
            if (k <= -1 || k >= G.V())
                throw new IllegalArgumentException();
        }
        int min = G.V() + 1;
        int min_ancestor = -1;

        for (int i : v) {
            for (int j : w) {
                int curr = length(i, j);
                if (curr < min) {
                    min = curr;
                    min_ancestor = ancestor(i, j);
                }
            }
        }

        return min_ancestor;

    }


    // a common ancestor that participates in shortest ancestral path; -1 if no such path

    // do unit testing of this class
    public static void main(String[] args) {

        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }

        /**
         In in = new In(args[0]);
         Digraph G = new Digraph(in);
         SAP sp = new SAP(G);


         ArrayList<Integer> A = new ArrayList<Integer>();
         ArrayList<Integer> B = new ArrayList<Integer>();

         A.add(13);
         A.add(23);
         A.add(24);
         B.add(6);
         B.add(16);
         B.add(17);

         System.out.println(sp.length(A, B));
         System.out.println(sp.ancestor(A, B));
         **/
    }
}
