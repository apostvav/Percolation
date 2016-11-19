/*----------------------------------------------------------------
 * 
 *  Author:        Apostolos V.
 *  Written:       07/Sep/2016
 *  Last updated:  10/Sep/2016
 *
 *  http://coursera.cs.princeton.edu/algs4/assignments/percolation.html
 *  
 *----------------------------------------------------------------*/

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    
    private int t;
    private double[] fractions;

    public PercolationStats(int n, int trials) {
        // perform trials independent experiments on an n-by-n grid
        if (n < 1 || trials < 1) {
            throw new IllegalArgumentException();
        }
        
        t = trials;
        fractions = new double[trials];
            
        for (int run = 0; run < t; run++) {
            Percolation p = new Percolation(n);
            int openedSites = 0;
            
            while (!p.percolates()) {               
                // Pick a random site
                int i = StdRandom.uniform(1, n+1);
                int j = StdRandom.uniform(1, n+1);
                
                if (!p.isOpen(i, j)) {
                    p.open(i, j);
                    openedSites++;
                }
                
                fractions[run] = (double) openedSites / (n*n);
            }      
        }
    }
    
    public double mean() {
        // sample mean of percolation threshold
        return StdStats.mean(fractions);
    }
    
    public double stddev() {
        // sample standard deviation of percolation threshold
        return StdStats.stddev(fractions);
    }
    
    public double confidenceLo() {
        // low  endpoint of 95% confidence interval
        return mean() - (1.96*stddev())/(Math.sqrt(t)); 
    }
    
    public double confidenceHi() {
        // high endpoint of 95% confidence interval
        return mean() + (1.96*stddev())/(Math.sqrt(t));
    }

    public static void main(String[] args) {
        // test client (described below)
        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);
        PercolationStats ps = new PercolationStats(n, t);
        System.out.println("mean                    = "+ps.mean());
        System.out.println("stddev                  = "+ps.stddev());
        System.out.println("95% confidence interval = "+ps.confidenceLo()+", "+ps.confidenceHi());
    }
    
}
