import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private final double[] fraction;
    private final double CONFIDENCE_95 = 1.96;

    public PercolationStats(int n, int trials) {
        Percolation grid;
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("don't do grid n = " + n);
        }

        fraction = new double[trials];

        //grid = new Percolation(n);

        for (int k = 0; k < trials; k++) {
            grid = new Percolation(n);
            while (!grid.percolates()) {
                grid.open(StdRandom.uniform(1, n + 1), StdRandom.uniform(1, n + 1));
            }
            fraction[k] = grid.numberOfOpenSites() / Math.pow(n, 2);
        }
    }

    public double mean() {
        return StdStats.mean(fraction);
    }

    public double stddev() {
        return StdStats.stddev(fraction);
    }

    public double confidenceLo() {
        return mean() - CONFIDENCE_95 * stddev() / Math.sqrt(fraction.length);
    }

    public double confidenceHi() {
        return mean() + CONFIDENCE_95 * stddev() / Math.sqrt(fraction.length);
    }

    public static void main(String[] args) {
        PercolationStats forPrint = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        System.out.println("mean = " + forPrint.mean());
        System.out.println("sttdev = " + forPrint.stddev());
        System.out.println("95% confidence interval = [" + forPrint.confidenceLo() + ", " + forPrint.confidenceHi() + "]");
    }
}

