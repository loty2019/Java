import stdlib.StdOut;
import stdlib.StdRandom;
import stdlib.StdStats;

public class PercolationStats {

    int m; // Number of independent experiments
    double[] x; // Percolation thresholds for the m experiments

    // Performs m independent experiments on an n x n percolation system.
    public PercolationStats(int n, int m) {
        if (n <= 0 || m <= 0) {
            throw new IllegalArgumentException("Illegal n or m");
        }
        this.m = m;  // Initialize instance variables
        this.x = new double[m];

        for (int i = 0; i < m; i++) { // perform the experiment m number of times
            UFPercolation percolation = new UFPercolation(n);
            while (!percolation.percolates()) {
                int row = StdRandom.uniform(0, n);  // create a random site
                int coll = StdRandom.uniform(0, n);

                if (!percolation.isOpen(row, coll)) {  // open random site if closed
                    percolation.open(row, coll);
                }
            }
            x[i] = (double) percolation.openSites / (n * n);  // Calculate threshold
        }
    }

    // Returns sample mean of percolation threshold.
    public double mean() {
        return StdStats.mean(x);
    }

    // Returns sample standard deviation of percolation threshold.
    public double stddev() {
        return StdStats.stddev(x);
    }

    // Returns low endpoint of the 95% confidence interval.
    public double confidenceLow() {
        return mean() - 1.96 * stddev() / Math.sqrt(m);
    }

    // Returns high endpoint of the 95% confidence interval.
    public double confidenceHigh() {
        return mean() + 1.96 * stddev() / Math.sqrt(m);
    }

    // Unit tests the data type. 
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int m = Integer.parseInt(args[1]);
        PercolationStats stats = new PercolationStats(n, m);
        StdOut.printf("Percolation threshold for a %d x %d system:\n", n, n);
        StdOut.printf("  Mean                = %.3f\n", stats.mean());
        StdOut.printf("  Standard deviation  = %.3f\n", stats.stddev());
        StdOut.printf("  Confidence interval = [%.3f, %.3f]\n", stats.confidenceLow(),
                stats.confidenceHigh());
    }
}