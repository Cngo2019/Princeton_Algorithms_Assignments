import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private double[] open_sites;
    private int T;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("");
        }

        T = trials;
        open_sites = new double[trials];

        for (int t = 0; t < T; t++) {
            Percolation exp = new Percolation(n);
            // Perform the random opening
            while (!exp.percolates()) {
                int r_row = StdRandom.uniform(0, n) + 1;
                int r_col = StdRandom.uniform(0, n) + 1;
                exp.open(r_row, r_col);
            }
            // get results and store it in percolation states
            open_sites[t] = exp.numberOfOpenSites() / ((double) n * n);
        }

    }


    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(open_sites);

    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(open_sites);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - 1.96 * stddev() / (Math.pow(T, .5));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + 1.96 * stddev() / (Math.pow(T, .5));
    }

    // test client (see below)
    public static void main(String[] args) {
        PercolationStats simulation = new PercolationStats(200, 100);
        System.out.println("Mean " + simulation.mean());
        System.out.println("STDDEV " + simulation.stddev());
        System.out.println("L CI" + simulation.confidenceLo());
        System.out.println("H CI" + simulation.confidenceHi());

        simulation = new PercolationStats(2, 10000);
        System.out.println("Mean " + simulation.mean());
        System.out.println("STDDEV " + simulation.stddev());
        System.out.println("L CI" + simulation.confidenceLo());
        System.out.println("H CI" + simulation.confidenceHi());

        simulation = new PercolationStats(2, 100000);
        System.out.println("Mean " + simulation.mean());
        System.out.println("STDDEV " + simulation.stddev());
        System.out.println("L CI" + simulation.confidenceLo());
        System.out.println("H CI" + simulation.confidenceHi());

    }

}
