import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private WeightedQuickUnionUF quickUnionUF;

    private boolean[][] baseData;

    private int topPoint = 0;
    private int bottomPoint;
    private int size;

    public Percolation(int n) {
        // create n-by-n grid, with all sites blocked
        bottomPoint = n * n + 1;
        size = n;
        quickUnionUF = new WeightedQuickUnionUF(n * n + 2);
        if (n > 0) {
            baseData = new boolean[n][n];
        } else {
            throw new IllegalArgumentException();
        }
    }

    public void open(int row, int col) {
        checkRowColValuesValid(row, col);

        if (row == 1) {
            quickUnionUF.union(getQuickUnionIndex(row, col), topPoint);
        }
        if (row == size) {
            quickUnionUF.union(getQuickUnionIndex(row, col), bottomPoint);
        }

        // left
        if (col > 1 && isOpen(row, col - 1)) {
            quickUnionUF.union(getQuickUnionIndex(row, col),
                    getQuickUnionIndex(row, col - 1));
        }
        // right
        if (col < size && isOpen(row, col + 1)) {
            quickUnionUF.union(getQuickUnionIndex(row, col),
                    getQuickUnionIndex(row, col + 1));
        }
        // top
        if (row > 1 && isOpen(row - 1, col)) {
            quickUnionUF.union(getQuickUnionIndex(row, col),
                    getQuickUnionIndex(row - 1, col));
        }
        // bottom
        if (row < size && isOpen(row + 1, col)) {
            quickUnionUF.union(getQuickUnionIndex(row, col),
                    getQuickUnionIndex(row + 1, col));
        }

        baseData[row - 1][col - 1] = true;
    }

    private void checkRowColValuesValid(int row, int col) {
        if (row <= 0 || col <= 0 || row > size || col > size) {
            throw new IndexOutOfBoundsException();
        }
    }

    public boolean isOpen(int row, int col) {
        // is site (row, col) open?
        checkRowColValuesValid(row, col);
        return baseData[row - 1][col - 1];
    }

    public boolean isFull(int row, int col) {
        // is site (row, col) full?
        checkRowColValuesValid(row, col);
        if (!isOpen(row, col)) {
            return false;
        }
        return quickUnionUF.connected(topPoint, getQuickUnionIndex(row, col));
    }

    public int numberOfOpenSites() {
        // number of open sites
        int result = 0;
        for (int i = 1; i <= size; i++) {
            for (int j = 1; j <= size; j++) {
                if (isOpen(i, j)) {
                    result++;
                }
            }
        }
        return result;
    }

    public boolean percolates() {
        // does the system percolate?
        return quickUnionUF.connected(topPoint, bottomPoint);
    }

    private int getQuickUnionIndex(int i, int j) {
        return size * (i - 1) + j;
    }
}