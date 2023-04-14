import dsa.WeightedQuickUnionUF;
import stdlib.In;
import stdlib.StdOut;

// An implementation of the Percolation API using the UF data structure.
public class UFPercolation implements Percolation {

    int n; // Percolation system size
    boolean[][] open;  // Percolation system
    int openSites;  // Number of open sites
    WeightedQuickUnionUF uf; // Union-find representation of the percolation system
    WeightedQuickUnionUF buf; // Union-find representation of the percolation system
                                // to account for the back wash problem

    // Constructs an n x n percolation system, with all sites blocked.
    public UFPercolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("Illegal n");
        }
        this.n = n;   // Initialize instance variables
        this.open = new boolean[n][n];
        this.uf = new WeightedQuickUnionUF(n * n + 2);
        this.openSites = 0;
        this.buf = new WeightedQuickUnionUF(n * n + 1);
    }

    // Opens site (i, j) if it is not already open.
    public void open(int i, int j) {
        if (i < 0 || i > n - 1 || j < 0 || j > n - 1) {
            throw new IndexOutOfBoundsException("Illegal i or j");
        }
        if (!open[i][j]) {
            open[i][j] = true;
            openSites++;
            int p = encode(i, j);
            if (i == 0) {  // connect all open sites
                uf.union(0, p);
                buf.union(0, p);
            }
            if (i == n - 1) {
                uf.union(n * n + 1, p);
            }
            if (i > 0 && open[i - 1][j]) {
                uf.union(p, encode(i - 1, j));
                buf.union(p, encode(i - 1, j));
            }
            if (i < n - 1 && open[i + 1][j]) {
                uf.union(p, encode(i + 1, j));
                buf.union(p, encode(i + 1, j));
            }
            if (j > 0 && open[i][j - 1]) {
                uf.union(p, encode(i, j - 1));
                buf.union(p, encode(i, j - 1));
            }
            if (j < n - 1 && open[i][j + 1]) {
                uf.union(p, encode(i, j + 1));
                buf.union(p, encode(i, j + 1));
            }
        }
    }

    // Returns true if site (i, j) is open, and false otherwise.
    public boolean isOpen(int i, int j) {
        if (i < 0 || i > n - 1 || j < 0 || j > n - 1) {
            throw new IndexOutOfBoundsException("Illegal i or j");
        }
        return open[i][j];
    }

    // Returns true if site (i, j) is full, and false otherwise.
    public boolean isFull(int i, int j) {
        if (i < 0 || i > n - 1 || j < 0 || j > n - 1) {
            throw new IndexOutOfBoundsException("Illegal i or j");
        }
        return isOpen(i, j) && buf.connected(0, encode(i, j));
    }

    // Returns the number of open sites.
    public int numberOfOpenSites() {
        return openSites;
    }

    // Returns true if this system percolates, and false otherwise.
    public boolean percolates() {
        return uf.connected(0, n * n + 1);
    }

    // Returns an integer ID (1...n) for site (i, j).
    private int encode(int i, int j) {
        return (n * i) + j + 1;
    }

    // Unit tests the data type. 
    public static void main(String[] args) {
        String filename = args[0];
        In in = new In(filename);
        int n = in.readInt();
        UFPercolation perc = new UFPercolation(n);
        while (!in.isEmpty()) {
            int i = in.readInt();
            int j = in.readInt();
            perc.open(i, j);
        }
        StdOut.printf("%d x %d system:\n", n, n);
        StdOut.printf("  Open sites = %d\n", perc.numberOfOpenSites());
        StdOut.printf("  Percolates = %b\n", perc.percolates());
        if (args.length == 3) {
            int i = Integer.parseInt(args[1]);
            int j = Integer.parseInt(args[2]);
            StdOut.printf("  isFull(%d, %d) = %b\n", i, j, perc.isFull(i, j));
        }
    }
}