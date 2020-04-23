package hw2;
import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.introcs.StdStats;

public class PercolationStats {
    private int numExp;
    private double[] thresholds;

    // perform T independent experiments on an N-by-N grid
    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException();
        }
        numExp = T;
        thresholds = monteCarloSimulation(N, T, pf);
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(thresholds);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        if (numExp == 1) {
            return Double.NaN;
        }
        return StdStats.stddev(thresholds);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLow() {
        return mean() - 1.96 * stddev() / Math.sqrt(numExp);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHigh() {
        return mean() + 1.96 * stddev() / Math.sqrt(numExp);
    }

    private double[] monteCarloSimulation(int N, int T, PercolationFactory pf) {
        double[] ths = new double[T];
        int[] sampleSite = new int[2];
        for (int i = 0; i < T; i++) {
            Percolation perc = pf.make(N);
            while (!perc.percolates()) {
                sampleSite[0] = StdRandom.uniform(N - 1);
                sampleSite[1] = StdRandom.uniform(N - 1);
                perc.open(sampleSite[0], sampleSite[1]);
            }
            ths[i] = ((double) perc.numberOfOpenSites()) / N / N;
        }

        return ths;
    }
}
