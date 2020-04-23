package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private WeightedQuickUnionUF topology;
    private int[][] grid;
    private static final int BLOCKED = 0;
    private static final int OPEN = 1;
    private int size;
    private int numOpen;
//    private int[] topFull;
//    private int topNum;
//    private int[] botOpen;
//    private int botNum;

    // create N-by-N grid, with all sites initially blocked
    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException();
        }
        size = N;
        numOpen = 0;
        grid = new int[N][N];
//        topFull = new int[N];
//        topNum = 0;
//        botOpen = new int[N];
//        botNum = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                grid[i][j] = BLOCKED;
            }
        }

        topology = new WeightedQuickUnionUF(N * N + 2);
    }

    private void validateIndex(int row, int col) {
        if (row < 0 || row > size - 1) {
            throw new IndexOutOfBoundsException();
        }
        if (col < 0 || col > size - 1) {
            throw new IndexOutOfBoundsException();
        }
    }

    // open the site (row, col) if it is not open already
    public void open(int row, int col) {
        validateIndex(row, col);
        if (grid[row][col] == BLOCKED) {
            numOpen += 1;
            grid[row][col] = OPEN;
            unionOpenNeighbor(row, col);
            if (row == 0) {
//                topFull[topNum] = col;
//                topNum += 1;
                topology.union(0, linearInd(row, col));
            }
            if (row == size - 1) {
//                botOpen[botNum] = linearInd(row, col);
//                botNum += 1;
                topology.union(1, linearInd(row, col));
            }
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        validateIndex(row, col);
        return grid[row][col] == OPEN;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        validateIndex(row, col);
        if (row == 0) {
            return grid[row][col] == OPEN;
        }

        return topology.connected(linearInd(row, col), 0);
//        for (int i = 0; i < topNum; i++) {
//            if (topology.connected(linearInd(row, col), topFull[i])) {
//                return true;
//            }
//        }

//        return false;
    }

    // number of open sites
    public int numberOfOpenSites() {
        return numOpen;
    }

    // does the system percolate?
    public boolean percolates() {
        return topology.connected(0, 1);
//        for (int i = 0; i < botNum; i++) {
//            int[] bot = xyInd(botOpen[i]);
//            if (isFull(bot[0], bot[1])) {
//                return true;
//            }
//        }
//        return false;
    }

    private int linearInd(int row, int col) {
        return row * size + col + 2;
    }

    private int[] xyInd(int linInd) {
        int[] res = new int[2];
        res[0] = (linInd - 2) / size;
        res[1] = (linInd - 2) % size;
        return res;
    }

    private int[] openNeighbors(int row, int col) {
        int[] result = new int[] {-1, -1, -1, -1}; // top, down, left, right
        int num = 0;
        if (row > 0 && grid[row - 1][col] == OPEN) {
            result[0] = linearInd(row - 1, col);
            num += 1;
        }
        if (row < size - 1 && grid[row + 1][col] == OPEN) {
            result[1] = linearInd(row + 1, col);
            num += 1;
        }
        if (col > 0 && grid[row][col - 1] == OPEN) {
            result[2] = linearInd(row, col - 1);
            num += 1;
        }
        if (col < size - 1 && grid[row][col + 1] == OPEN) {
            result[3] = linearInd(row, col + 1);
            num += 1;
        }

        if (num == 0) {
            return null;
        }

        int[] validRes = new int[num];
        for (int i = 0, j = 0; i < result.length; i++) {
            if (result[i] != -1) {
                validRes[j] = result[i];
                j++;
            }
        }
        return validRes;
    }

    private void unionOpenNeighbor(int row, int col) {
        int[] ons = openNeighbors(row, col);
        if (ons == null) {
            return;
        }
        int ind = linearInd(row, col);
        for (int neighbor: ons) {
            topology.union(ind, neighbor);
        }
    }

    // use for unit testing (not required, but keep this here for the autograder)
    public static void main(String[] args) {

    }
}
