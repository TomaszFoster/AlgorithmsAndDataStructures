/* *****************************************************************************
 *  Name: Tomasz Foster
 *  Date: 10/15/2018
 *  Description: Percolation / Monte Carlo simulation
 **************************************************************************** */

// import edu.princeton.cs.algs4.StdStats;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private final WeightedQuickUnionUF quickUnion;
    private final WeightedQuickUnionUF quickUnionFullness;
    private final int number;
    private final int sites;
    private int openSites;
    private boolean[][] grid;

    public Percolation(int n) {
        if (n <= 0)
            throw new IllegalArgumentException("n must be greater than zero!");

        number = n;
        sites = n * n;
        quickUnion = new WeightedQuickUnionUF(sites + 2);
        quickUnionFullness = new WeightedQuickUnionUF(sites + 2);

        grid = new boolean[number][number];
        openSites = 0;
        for (int i = 0; i < number; i++) {
            for (int j = 0; j < number; j++) {
                grid[i][j] = false;
            }
        }
    }

    private void validate(int i) {
        if (i <= 0 || i > number) {
            throw new IllegalArgumentException("index " + i + " is not between 1 and " + number);
        }
    }

    private void updateMonteCarlo(int row, int col) {
        /*
            Check the following grid positions for other open points

                - X -
                X - X
                - X -
         */
        int index = xyTo1d(row, col);
        // translate row and column from monte carlo grid to our path array index

        // if point is in the first row, make union with virtual topsite
        if (row == 1) {
            quickUnion.union(0, index);
            quickUnionFullness.union(0, index);
        }

        testLeftSite(row, col, index);
        testRightSite(row, col, index);
        testTopSite(row, col, index);
        testBottomSite(row, col, index);

        if (row == number) {
            quickUnion.union(index, sites + 1);
        }

    }

    private void testLeftSite(int row, int col, int index) {
        if (col > 1) {
            if (isOpen(row, col - 1)) {
                quickUnion.union(index - 1, index);
                quickUnionFullness.union(index - 1, index);
            }
        }
    }

    private void testRightSite(int row, int col, int index) {
        if (col < number) {
            if (isOpen(row, col + 1)) {
                quickUnion.union(index + 1, index);
                quickUnionFullness.union(index + 1, index);
            }
        }
    }

    private void testTopSite(int row, int col, int index) {
        if (row > 1) {
            if (isOpen(row - 1, col)) {
                quickUnion.union(index - number, index);
                quickUnionFullness.union(index - number, index);
            }
        }
    }

    private void testBottomSite(int row, int col, int index) {
        if (row < number) {
            if (isOpen(row + 1, col)) {
                quickUnion.union(index + number, index);
                quickUnionFullness.union(index + number, index);
            }
        }
    }

    private int xyTo1d(int row, int col) {
        return (row - 1) * number + col;
    }

    /*
        open site (row, col) if it is not open already
        parameters given in form of 1 to N
        grid is in form of 0 to N-1
        therefore must subtract 1 to row/col index
     */
    public void open(int row, int col) {
        validate(row);
        validate(col);
        if (!isOpen(row, col)) {
            grid[row - 1][col - 1] = true;
            updateMonteCarlo(row, col);
            openSites++;
        }
    }

    // is site (row, col) open?
    public boolean isOpen(int row, int col) {
        validate(row);
        validate(col);
        return grid[row - 1][col - 1];
    }

    // is site (row, col) full?
    public boolean isFull(int row, int col) {
        validate(row);
        validate(col);
        int index = (row - 1) * number + col;
        return quickUnionFullness.connected(0, index);
    }

    // number of open sites
    public int numberOfOpenSites() {
        return openSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return quickUnion.connected(0, sites + 1);
    }

    public static void main(String[] args) {
        // In in = new In(args[0]);      // input file
        // int n = in.readInt();         // n-by-n percolation system
        // Percolation percolation = new Percolation(n);
        // while (!in.isEmpty()) {
        //     int i = in.readInt();
        //     int j = in.readInt();
        //     percolation.open(i, j);
        // }
        // System.out.print("Grid:\n");
        // for (int i = 0; i < percolation.number; i++) {
        //     for (int j = 0; j < percolation.number; j++) {
        //         System.out.printf(" %3d", percolation.grid[i][j] ? 1 : 0);
        //     }
        //     System.out.print("\n");
        // }
        //
        // System.out.printf("Is point 18,1 full? %s\n",
        //                   percolation.isFull(18, 1) ? "True" : "False");
        //
        // System.out.printf("Monte carlo percolates? %s\n\n",
        //                   percolation.percolates() ? "True" : "False");
    }
}
