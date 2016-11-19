/*----------------------------------------------------------------
 * 
 *  Author:        Apostolos V.
 *  Written:       07/Sep/2016
 *  Last updated:  10/Sep/2016
 *
 *  http://coursera.cs.princeton.edu/algs4/assignments/percolation.html
 *  
 *----------------------------------------------------------------*/

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private int n;
    private int top;
    private int bottom;
    private int[][] grid;
    private WeightedQuickUnionUF uf;
    
    public Percolation(int n) {
        
        if (n <= 0) {
            throw new IllegalArgumentException();
        }
        
        this.n = n;
        grid = new int[n][n];
        uf = new WeightedQuickUnionUF(n*n + 2);
        top = 0;
        bottom = (n*n)+1;
    }
    
    public void open(int i, int j) {
        // Open site (row i, column j) if it is not open already
        checkValues(i, j);
        
        i = i-1;
        j = j-1;
        
        int currentSite = xyTo1D(i, j);
        
        if (grid[i][j] == 0) {
            
            grid[i][j] = 1;
            
            // Union with virtual top
            if (i == 0) {
                uf.union(currentSite, top);
            }
            
            // Union with virtual bottom
            if (i == n-1) {
                uf.union(currentSite, bottom);
            }
            
            // Union with neighbor above
            if (i > 0 && isOpen(i, j+1)) {
                uf.union(currentSite, xyTo1D(i-1, j));
            }
            
            // Union with neighbor below
            if (i < n-1 && isOpen(i+2, j+1)) {
                uf.union(currentSite, xyTo1D(i+1, j));
            }
            
            // Union with left neighbor
            if (j > 0 && isOpen(i+1, j)) {
                uf.union(currentSite, xyTo1D(i, j-1));
            }
            
            // Union with right neighbor
            if (j < n-1 && isOpen(i+1, j+2)) {
                uf.union(currentSite, xyTo1D(i, j+1));
            }            
        }
    }
    
    public boolean isOpen(int i, int j) {
        // is site (row i, column j) open?
        // Range [0-N)
        checkValues(i, j);
        
        i = i-1;
        j = j-1;
        
        return grid[i][j] != 0;
    }
           
    public boolean isFull(int i, int j) {
        // is site (row i, column j) full?
        // TRUE if site is connected with virtual top
        // Range [0-N)
        checkValues(i, j);
        
        i = i-1;
        j = j-1;
        
        int currentSite = xyTo1D(i, j);
        return uf.connected(top, currentSite);
    }
       
    public boolean percolates() {
        // does the system percolate?
        // TRUE if top is connected with the bottom
        return uf.connected(top, bottom);
    }
    
    private boolean checkValues(int i, int j) {
        // Range (0-N]
        if (i < 1 || i > n || j < 1 || j > n) {
            throw new IndexOutOfBoundsException();
        }
        else {
            return true;
        }
    }
    
    private int xyTo1D(int i, int j) {
        // Range [0-N)
        return n*i+j+1;
    }
}
