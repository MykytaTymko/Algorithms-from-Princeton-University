import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private final int times;
    private double[] threshold;
    private final double stddev, mean, hi, lo;
    private int sidesOpened;

    /**
     * perform T independent computational experiments on an N-by-N grid
     *
     * @param N - size
     * @param T - how much times
     */
    public PercolationStats(int N, int T) {
        if (T <= 0) throw new IllegalArgumentException("T shall be > 0");
        times = T;
        threshold = new double[times];

        for (int i = 0; i < times; i++) {
            Percolation p = new Percolation(N);
            sidesOpened = 0;
            do {
                int row = StdRandom.uniform(1, N + 1);
                int col = StdRandom.uniform(1, N + 1);
                if (!p.isOpen(row, col)){
                    p.open(row, col);
                    sidesOpened++;
                }
            } while (!p.percolates());

            threshold[i] = (double)sidesOpened / (N * N);
        }

        stddev = StdStats.stddev(threshold);
        mean = StdStats.mean(threshold);
        double s = 1.96 * stddev/ Math.sqrt(times);
        lo = mean - s;
        hi = mean + s;
    }

    public static void main(String[] args) {
        PercolationStats ps = new PercolationStats(
                Integer.parseInt(args[0]),
                Integer.parseInt(args[1])
        );
        System.out.println("Mean                    = " + ps.mean());
        System.out.println("StdDev                  = " + ps.stddev());
        System.out.println("95% confidence interval = " + ps.confidenceLo() + ", " + ps.confidenceHi());
    }

    /**
     * sample mean of percolation threshold
     *
     * @return mean
     */
    public double mean() {
        return mean;
    }

    /**
     * sample standard deviation of percolation threshold
     *
     * @return stddev
     */
    public double stddev() {
        return stddev;
    }

    /**
     * returns lower bound of the 95% confidence interval
     *
     * @return lower bound
     */
    public double confidenceLo() {
        return lo;
    }

    /**
     * returns upper bound of the 95% confidence interval
     *
     * @return upper bound
     */
    public double confidenceHi() {
        return hi;
    }
}
