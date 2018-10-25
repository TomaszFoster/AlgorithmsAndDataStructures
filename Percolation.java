/******************************************************************************
 *  Compilation:  javac algs4 Percolation.java
 *  Execution:    java algs4 Percolation n
 *  
 *  
 * 
 ******************************************************************************/

// import edu.princeton.cs.algs4.StdRandom;
// import edu.princeton.cs.algs4.StdStats;
// import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import java.util.Arrays;

public class Percolation {

    private int[][] grid;
    private int[] monteCarlo;
    private int N;
    private int openSites;
    
    // create n-by-n grid, with all sites blocked
    public Percolation(int n)
    {
        if(n <= 0)
            throw new IllegalArgumentException("n must be greater than zero!");

        N = n;
        grid = new int[N][N];
        monteCarlo = new int[(N*N)+2];
        monteCarlo[N*N+1] = N*N+1;
        openSites = 0;
        int count = 1;
        for(int i = 0; i < N; i++)
        {
            for(int j = 0; j < N; j++)
            {
                grid[i][j] = 1;
                if(i == 0){
                    monteCarlo[count] = 0;
                } else if (i== (N-1)) {
                    monteCarlo[count] = (N*N)+1;
                } else {
                    monteCarlo[count] = count;
                }
                count++;
            }
        }
    }

    // validate that i is a valid index
    private void validate(int i)
    {
        if (i <= 0 || i > N) {
            throw new IllegalArgumentException("index " + i + " is not between 1 and " + N);  
        }
    }

    // open site (row, col) if it is not open already
    public void open(int row, int col)
    {
        validate(row);
        validate(col);
        if ( isFull(row,col) )
        {
            grid[row+1][col+1] = 0;
            openSites++;
        }

    }

    // is site (row, col) open?
    public boolean isOpen(int row, int col)
    {
        validate(row);
        validate(col);
        return grid[row+1][col+1] == 0;
    }

    // is site (row, col) full?
    public boolean isFull(int row, int col)
    {
        validate(row);
        validate(col);
        return grid[row+1][col+1] == 1;
    }

    // number of open sites
    public int numberOfOpenSites()
    {
        return openSites;
    }

    // does the system percolate?
    // public boolean percolates()
    // {
        
    // }

    // test client (optional)
    public static void main(String[] args)
    {
        Percolation percolation = new Percolation(5);
        System.out.print("MonteCarlo is as follows: \n");
        System.out.println(  Arrays.toString( percolation.monteCarlo ) + "\n" );
    }
}