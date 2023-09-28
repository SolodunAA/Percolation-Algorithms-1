import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private final int[] grid;
    private final int len;
    private final WeightedQuickUnionUF guickUnion;

    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }
        len = n;
        grid = new int[n * n + 1];
        guickUnion = new WeightedQuickUnionUF(n * n + 1);

        //Arrays.fill(grid, 0);
        for(int i = 0; i < grid.length; i++){
            grid[i] = 0;
        }
    }

    private int move2dTo1d (int row, int col){
        return len * (row - 1) + col;
    }

    private boolean isInGrid(int row, int col){
        return isInRowOrCol(row) && isInRowOrCol(col);
    }

    private boolean isInRowOrCol(int x){
        return (x >= 1 && x <= len);
    }


    public void open(int row, int col) {
        if (!isInGrid(row, col)) {
            throw new IllegalArgumentException("Not in the grid row=" + row + ", col=" + col + ", grid length=" + len);
        }
        if (!isOpen(row, col)) {
            grid[move2dTo1d(row, col)] = move2dTo1d(row, col);
        }
        unionGrid(row, col);
    }

    private void unionGrid(int row, int col) {

        if (row == 1) {
            guickUnion.union(move2dTo1d(row, col), 0);
        }
        /*if (row == len) {
            guickUnion.union(move2dTo1d(row, col), move2dTo1d(len, len));
        }*/
        if (isInRowOrCol(row - 1) && isOpen(row - 1, col)) {
            guickUnion.union(move2dTo1d(row, col), move2dTo1d(row - 1, col));
        }
        if (isInRowOrCol(row + 1) && isOpen(row + 1, col)) {
            guickUnion.union(move2dTo1d(row, col), move2dTo1d(row + 1, col));
        }
        if (isInRowOrCol(col - 1) && isOpen(row, col - 1)) {
            guickUnion.union(move2dTo1d(row, col), move2dTo1d(row, col - 1));
        }
        if (isInRowOrCol(col + 1) && isOpen(row, col + 1)) {
            guickUnion.union(move2dTo1d(row, col), move2dTo1d(row, col+ 1));
        }
    }

    public boolean isOpen(int row, int col) {
        if (!isInGrid(row, col)) {
            throw new IllegalArgumentException("isOpen row=" + row + ", col=" + col + ", grid length=" + len);
        }
        //System.out.println("row = " + row + ", col = " + col + " open? " + !(grid[row - 1][col - 1] == 0) + " = " + grid[row - 1][col - 1]);
        return !(grid[move2dTo1d(row, col)] == 0);
    }
    public boolean isFull(int row, int col) {
        if (!isInGrid(row, col)) {
            throw new IllegalArgumentException("isFull row=" + row + ", col=" + col + ", grid length=" + len);
        }

        if (!isOpen(row, col)) {
            return false;
        }

        if (row == 1) {
            return true;
        }

        return guickUnion.find(move2dTo1d(row, col)) == guickUnion.find(0);
    }


    public int numberOfOpenSites() {
        //return (int) Arrays.stream(grid).filter(x -> x > 0).count();
        int k = 0;
        for(int i = 0; i < grid.length; i++){
            if(grid[i] > 0){
                k = k + 1;
            }
        }
        return k;
    }

    public boolean percolates() {
        for(int i = 1; i <= len; i++){
            if(isFull(len, i)) return true;
        }
        return false;
    }
}
