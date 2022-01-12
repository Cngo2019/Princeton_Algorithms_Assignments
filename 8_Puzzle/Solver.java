import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;

import java.util.ArrayList;

public class Solver {
    private Board initial;
    private ArrayList<searchNode> path;
    private boolean isSolvable = true;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null)
            throw new IllegalArgumentException();
        path = new ArrayList<searchNode>();
        this.initial = initial;
        Board initial_twin = initial.twin();
        MinPQ<searchNode> pq = new MinPQ<searchNode>();
        MinPQ<searchNode> pq_twin = new MinPQ<searchNode>();

        searchNode initialNode = new searchNode(initial, null);
        searchNode initialNode_twin = new searchNode(initial_twin, null);
        pq.insert(initialNode);
        pq_twin.insert(initialNode_twin);
        while (pq.size() > 0) {
            //deletes the minimum
            searchNode curr = pq.delMin();
            searchNode curr_twin = pq_twin.delMin();
            // if the current node's tiles have 0 hamming score, then it is the solution.
            if (curr.tiles.manhattan() == 0) {
                while (curr != null) {
                    path.add(curr);
                    curr = curr.prev;
                }
                break;
            }

            if (curr_twin.tiles.manhattan() == 0) {
                isSolvable = false;
                break;
            }

            ArrayList<searchNode> neighbors = new ArrayList<searchNode>();
            ArrayList<searchNode> neighbors_twin = new ArrayList<searchNode>();

            neighbors = getNeighbors(curr);
            for (int i = 0; i < neighbors.size(); i++) {
                if (curr.prev == null || !curr.prev.tiles.equals(neighbors.get(i).tiles)) {
                    pq.insert(neighbors.get(i));
                }
            }

            neighbors_twin = getNeighbors(curr_twin);
            for (int i = 0; i < neighbors_twin.size(); i++) {
                if (curr_twin.prev == null || !curr_twin.prev.tiles
                        .equals(neighbors_twin.get(i).tiles)) {
                    pq_twin.insert(neighbors_twin.get(i));
                }
            }
        }


    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return isSolvable;
    }


    private ArrayList<searchNode> getNeighbors(searchNode input) {
        ArrayList<searchNode> neighbors = new ArrayList<searchNode>();
        Board this_tile = input.tiles;

        Iterable<Board> it = this_tile.neighbors();
        /**
         *  1  2  3
         *  4  5  0
         *  6  7  8
         */
        for (Board item : it) {
            neighbors.add(new searchNode(item, input));
        }
        return neighbors;

    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (!isSolvable)
            return -1;
        return path.size() - 1;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (!isSolvable())
            return null;
        Stack<Board> my_stack = new Stack<Board>();
        for (int i = 0; i < path.size(); i++) {
            my_stack.push(path.get(i).tiles);
        }
        return my_stack;
    }

    private class searchNode implements Comparable<searchNode> {
        private Board tiles;
        private int moves;
        private searchNode prev;
        private int priority;

        public searchNode(Board tiles, searchNode prev) {
            this.tiles = tiles;
            // number of predecessors (the level it is on in the PQ).
            this.prev = prev;


            if (prev != null)
                moves = prev.moves + 1;
            else
                moves = 0;

            priority = tiles.manhattan() + moves;

        }

        public int compareTo(searchNode that) {
            if (this.priority > that.priority) return 1;
            if (this.priority < that.priority) return -1;
            return 0;
        }
    }

}
