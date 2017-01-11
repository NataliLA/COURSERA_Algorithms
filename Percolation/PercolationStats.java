import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private double[] results;
    private int trials;

    public PercolationStats(int n, int trials) {
        // perform trials independent experiments on an n-by-n grid
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }

        this.trials = trials;
        results = new double[trials];

        for (int i = 0; i < trials; i++) {
            Percolation mPercolation = new Percolation(n);
            int openedCount = 0;
            while (!mPercolation.percolates()) {
                int row = StdRandom.uniform(1, n + 1);
                int col = StdRandom.uniform(1, n + 1);
                if (!mPercolation.isOpen(row, col)) {
                    mPercolation.open(row, col);
                    openedCount++;
                }
            }
            results[i] = (double) openedCount / (n * n);
        }
    }

    public double mean() {
        // sample mean of percolation threshold
        return StdStats.mean(results);
    }

    public double stddev() {
        System.out.println("stdev");
        // sample standard deviation of percolation threshold
        return StdStats.stddev(results);
    }

    public double confidenceLo() {
        // low endpoint of 95% confidence interval
        return mean() - (1.96 * stddev()) / Math.sqrt(trials);
    }

    public double confidenceHi() {
        // high endpoint of 95% confidence interval
        return mean() + (1.96 * stddev()) / Math.sqrt(trials);
    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);
        PercolationStats mPercolationStats = new PercolationStats(n, t);

        StdOut.println("mean                    = " + mPercolationStats.mean());
        StdOut.println("stddev                  = "
                + mPercolationStats.stddev());
        StdOut.println("95% confidence interval = "
                + mPercolationStats.confidenceLo() + ", "
                + mPercolationStats.confidenceHi());
    }

}
