/* *****************************************************************************
 *  Name: Tomasz Foster
 *  Date: 10/16/2018
 *  Description: Percolation Statistics
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private int number, trials;
    private final double mean, confidence, standardDeviation;

    public PercolationStats(int n, int t) {
        validate(n, t);
        double[] sites = new double[t];
        double arraySize = 1.0 * n * n;
        for (int i = 0; i < t; i++) {
            Percolation percolation = new Percolation(n);
            boolean percolates = false;
            int count = 0;
            while (!percolates) {
                int row = randomNumber();
                int col = randomNumber();
                if (!percolation.isOpen(row, col)) {
                    percolation.open(row, col);
                    percolates = percolation.percolates();
                    count++;
                }
            }
            sites[i] = (count / arraySize);
        }
        mean = StdStats.mean(sites);
        standardDeviation = StdStats.stddev(sites);
        confidence = 1.96 * standardDeviation / (Math.sqrt(trials));
    }

    private void validate(int n, int t) {
        number = n;
        trials = t;
        if (n <= 0 || t <= 0) {
            throw new IllegalArgumentException(
                    "Size of grid: " + n + " and number of trials " + trials
                            + " must be greater than 0");
        }
    }

    private int randomNumber() {
        return StdRandom.uniform(1, number + 1);
    }

    public double mean() {
        return mean;
    }

    public double stddev() {
        return standardDeviation;
    }

    public double confidenceLo() {
        return mean - confidence;
    }

    public double confidenceHi() {
        return mean + confidence;
    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);

        PercolationStats percolationStats = new PercolationStats(n, t);
        System.out.printf("mean \t\t\t= %s\n"
                                  + "stddev \t\t\t= %s\n"
                                  + "95%% confidence interval = "
                                  + "[%s, %s]\n",
                          percolationStats.mean(),
                          percolationStats.stddev(),
                          percolationStats.confidenceLo(),
                          percolationStats.confidenceHi()
        );
    }
}
