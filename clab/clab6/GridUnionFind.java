public class GridUnionFind extends UnionFind {
    private int[][] grid;
    private int rows;
    private int cols;
    private int size;
    private int drop;
    private int bubbles;
    private static final int OVERHEAD = 3;
    private static final int STUCK = 0;
    private static final int EMPTY = 1;
    private static final int UNSTUCK = 2;

    public GridUnionFind(int[][] g) {
        super(g.length * g[0].length + OVERHEAD);
        rows = g.length;
        cols = g[0].length;
        grid = new int[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                grid[i][j] = g[i][j];
                bubbles += g[i][j];
            }
        }
        drop = 0;
        size = rows * cols + OVERHEAD;
        constructTopo();
    }

    // reset stuck bubbles
    private void reset() {
        for (int i = 0; i < size; i++) {
            setHead(i);
        }
    }

    // construct union set
    private void constructTopo() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (i == 0 && grid[i][j] == 1) { //first row is stuck
                    union(linearInd(i, j), STUCK);
                } else if(grid[i][j] == 0) { // empty
                    union(linearInd(i, j), EMPTY);
                } else if (j == 0) { // first column
                    if (find(linearInd(i - 1, j)) == STUCK) { // top neighbor is stuck, this one is stuck
                        union(linearInd(i, j), STUCK);
                    } else if (find(linearInd(i - 1, j)) != EMPTY) {
                        // top neighbor is unstuck, this one is added to the unstuck union
                        union(linearInd(i, j), linearInd(i - 1, j));
                    }
                } else {// rest columns
                    if (find(linearInd(i - 1, j)) == STUCK ||
                            find(linearInd(i, j - 1)) == STUCK) {
                        // neighbor is stuck, this one and its union is stuck
                        union(linearInd(i, j), STUCK);
                        // update its neighbor to stuck
                        if (isUnstuck(linearInd(i - 1, j))) {
                            // top neighbor is unstuck
                            union(linearInd(i - 1, j), STUCK);
                        }
                        if (isUnstuck(linearInd(i, j - 1))) {
                            // left neighbor is unstuck
                            union(linearInd(i, j - 1), STUCK);
                        }
                    } else {
                        if (find(linearInd(i - 1, j)) != EMPTY) {
                            // top neighbor is unstuck, this and its union join the unstuck union
                            union(linearInd(i, j), linearInd(i - 1, j));
                        }
                        if (find(linearInd(i, j - 1)) != EMPTY) {
                            // left neighbor is unstuck, this and its union join the unstuck union
                            union(linearInd(i, j), linearInd(i, j - 1));
                        }
                    }
                }
            }
        }
    }

    public int applyDart(int[] dart) {
        if (find(linearInd(dart[0], dart[1])) == EMPTY) {
            drop = 0;
            return drop;
        } else if (find(linearInd(dart[0], dart[1])) == UNSTUCK) {
            bubbles -= 1;
            drop = 0;
            return drop;
        } else {
            bubbles -= 1;
            grid[dart[0]][dart[1]] = 0;
            reset();
            constructTopo();
            drop = bubbles - sizeOf(STUCK) + 1 - drop;
            return drop;
        }
    }

    private int linearInd(int row, int col) {
        return row * grid[0].length + col + OVERHEAD;
    }

    private boolean isStuck(int[] pos) {
        return find(linearInd(pos[0], pos[1])) == STUCK;
    }

    private boolean isStuck(int index) {
        return find(index) == STUCK;
    }

    private boolean isUnstuck(int index) {
        return find(index) == UNSTUCK || find(index) == index;
    }

    private boolean isUnstuck(int[] pos) {
        return isUnstuck(linearInd(pos[0], pos[1]));
    }
}
