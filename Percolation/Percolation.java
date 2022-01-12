import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    // A boolean array where entries are aligned 0,1,2...N - 1 and T if open, F is closed.
    private boolean[] open_sites;
    // A grid for easily taking the union of our sites. Numbers in the grid directly correspond to the open_sites boolean array
    private int[][] number_grid;
    private static int MAX_DIM;
    private WeightedQuickUnionUF my_union;
    private WeightedQuickUnionUF my_union_BW;
    private int counter = 0;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {

        if (n <= 0) {
            throw new IllegalArgumentException("n is less than equal to 0");
        }

        MAX_DIM = n;
        int k = 0;
        number_grid = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                number_grid[i][j] = k;
                k++;
            }
        }

        open_sites = new boolean[n * n];
        // we must take all the grids AND viritual sites into account

        /**
         * my_union will be a union find data structure that has nodes from 0 to n + 1 where n and n + 1 are going to be the virtual sites
         * my_union_BW will be a union find data structure that has nodes from 0 to n where n is the top virtual site.
         *
         * If a site is backwashed, then it percolates. So we really only need my_union_BW to check if a site is full to avoid backwash.
         */
        my_union = new WeightedQuickUnionUF(n * n + 2);
        my_union_BW = new WeightedQuickUnionUF(n * n + 1);
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row > MAX_DIM || col > MAX_DIM || row < 1 || col < 1) {
            throw new IllegalArgumentException("Invalid row and col");
        }


        int i = row - 1;
        int j = col - 1;

        if (open_sites[number_grid[i][j]]) {
            return;
        }
        open_sites[number_grid[i][j]] = true;
        counter += 1;
        /*
        There are 4 cases when we can get out of bounds. The top, bottom, and sides.
         */

        /**
         * Union a neighboring site if: that site is in bounds and is open.
         * Top, bottom, left, and right
         */

        //top
        if (i != 0 && open_sites[number_grid[i - 1][j]]) {
            my_union.union(number_grid[i - 1][j], number_grid[i][j]);
            my_union_BW.union(number_grid[i - 1][j], number_grid[i][j]);
        }

        //bottom
        if (i != MAX_DIM - 1 && open_sites[number_grid[i + 1][j]]) {
            my_union.union(number_grid[i + 1][j], number_grid[i][j]);
            my_union_BW.union(number_grid[i + 1][j], number_grid[i][j]);

        }
        //left
        if (j != 0 && open_sites[number_grid[i][j - 1]]) {
            my_union.union(number_grid[i][j - 1], number_grid[i][j]);
            my_union_BW.union(number_grid[i][j - 1], number_grid[i][j]);

        }
        //right
        if (j != MAX_DIM - 1 && open_sites[number_grid[i][j + 1]]) {
            my_union.union(number_grid[i][j + 1], number_grid[i][j]);
            my_union_BW.union(number_grid[i][j + 1], number_grid[i][j]);
        }


        // If it is a top or bottom row, then we have to union it to the corresponding virtual site!
        // It is important to note that my_union_BW does not have a bottom virtual site. So ignore it.
        if (i == 0) {
            my_union.union(MAX_DIM * MAX_DIM, number_grid[i][j]);
            my_union_BW.union(MAX_DIM * MAX_DIM, number_grid[i][j]);
        }
        if (i == MAX_DIM - 1) {
            my_union.union(MAX_DIM * MAX_DIM + 1, number_grid[i][j]);
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row > MAX_DIM || col > MAX_DIM || row < 1 || col < 1) {
            throw new IllegalArgumentException("Invalid row and col");
        }
        int i = row - 1;
        int j = col - 1;
        return open_sites[number_grid[i][j]];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        /**
         * A site is full iff it is connected to the top virtual site i == MAX_DIM * MAX_DIM.
         * Check to see if the MAX_DIM is connected to the current number.
         */
        if (row > MAX_DIM || col > MAX_DIM || row < 1 || col < 1) {
            throw new IllegalArgumentException("Invalid row and col");
        }
        return my_union_BW.connected(number_grid[row - 1][col - 1], MAX_DIM * MAX_DIM);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return counter;
    }

    // does the system percolate?
    public boolean percolates() {
        return my_union.connected(MAX_DIM * MAX_DIM, MAX_DIM * MAX_DIM + 1);
    }

}
