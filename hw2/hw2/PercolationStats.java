package hw2;
import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.introcs.StdStats;

public class PercolationStats {
    private int numExp;
    private double[] thresholds;
    private double mean;
    private double stddev;
    private double confLow;
    private double confHigh;

    // perform T independent experiments on an N-by-N grid
    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException();
        }
        numExp = T;
        thresholds = monteCarloSimulation(N, T, pf);
        mean = StdStats.mean(thresholds);
        stddev = (numExp == 1? Double.NaN: StdStats.stddev(thresholds));
        confLow = mean - 1.96 * stddev / Math.sqrt(numExp);
        confHigh = mean + 1.96 * stddev / Math.sqrt(numExp);
    }

    // sample mean of percolation threshold
    public double mean() {
//        return StdStats.mean(thresholds);
        return mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
//        if (numExp == 1) {
//            return Double.NaN;
//        }
//        return StdStats.stddev(thresholds);
        return stddev;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLow() {
//        return mean() - 1.96 * stddev() / Math.sqrt(numExp);
        return confLow;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHigh() {
//        return mean() + 1.96 * stddev() / Math.sqrt(numExp);
        return confHigh;
    }

    private double[] monteCarloSimulation(int N, int T, PercolationFactory pf) {
        double[] ths = new double[T];
        int[] sampleSite = new int[2];
        for (int i = 0; i < T; i++) {
            StdRandom.setSeed(i);
            int[][] sites = new int[N][N];
            Percolation perc = pf.make(N);
            while (!perc.percolates()) {
                sampleSite[0] = StdRandom.uniform(N);
                sampleSite[1] = StdRandom.uniform(N);
                if (sites[sampleSite[0]][sampleSite[1]] == 0) {
                    perc.open(sampleSite[0], sampleSite[1]);
                    sites[sampleSite[0]][sampleSite[1]] = 1;
                }
            }
            ths[i] = ((double) perc.numberOfOpenSites()) / N / N;
        }

        return ths;
    }
}
