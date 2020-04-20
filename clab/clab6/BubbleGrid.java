public class BubbleGrid {
    private int[][] grid;
    private int bNum;

    /* Create new BubbleGrid with bubble/space locations specified by grid.
     * Grid is composed of only 1's and 0's, where 1's denote a bubble, and
     * 0's denote a space. */
    public BubbleGrid(int[][] grid) {
        this.grid = grid;
        bNum = 0;
        for (int[] row : grid) {
            for (int b : row) {
                bNum += b;
            }
        }
    }

    /* Returns an array whose i-th element is the number of bubbles that
     * fall after the i-th dart is thrown. Assume all elements of darts
     * are unique, valid locations in the grid. Must be non-destructive
     * and have no side-effects to grid. */
    public int[] popBubbles(int[][] darts) {
        int[] result = new int[darts.length];
        int[] dPos;
        int drop = 0;
        int pop = 0;
        /*for (int i = 0; i < darts.length; i++) {
            dPos = darts[i];
            if (grid)
            UnionFind bubbles = new UnionFind(bNum - pop - drop)
        }*/
        // 0th: stuck, 1st: empty, 2nd: a unstuck set, rest are connected unstuck bubbles
        UnionFind bubbles = new UnionFind(grid.length * grid[0].length + 3);
        // initialize UnionFind
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (i == 0 && grid[i][j] == 1) { //first row is stuck
                    bubbles.union(linearInd(i, j), 0);
                } else if(grid[i][j] == 0) { // empty
                    bubbles.union(linearInd(i, j), 1);
                } else if (j == 0) { // first column
                    if (bubbles.find(linearInd(i - 1, j)) == 0) { // top neighbor is stuck, this one is stuck
                        bubbles.union(linearInd(i, j), 0);
                    } else if (bubbles.find(linearInd(i - 1, j)) != 1) {
                        // top neighbor is unstuck, this one is added to the unstuck union
                        bubbles.union(linearInd(i, j), linearInd(i - 1, j));
                    }
                } else {// rest columns
                    if (bubbles.find(linearInd(i - 1, j)) == 0 ||
                            bubbles.find(linearInd(i, j - 1)) == 0) {
                        // neighbor is stuck, this one and its union is stuck
                        bubbles.union(linearInd(i, j), 0);
                    } else {
                        if (bubbles.find(linearInd(i - 1, j)) != 1) {
                            // top neighbor is unstuck, this and its union join the unstuck union
                            bubbles.union(linearInd(i, j), linearInd(i - 1, j));
                        }
                        if (bubbles.find(linearInd(i, j - 1)) != 1) {
                            // left neighbor is unstuck, this and its union join the unstuck union
                            bubbles.union(linearInd(i, j), linearInd(i, j - 1));
                        }
                    }
                }
            }
        }

        int iter = 0;
        for (int[] d : darts) {
            // no stuck bubbles pop, next dart
            if (bubbles.find(linearInd(d[0], d[1])) != 0) {
                result[iter] = 0;
                iter += 1;
                continue;
            }
            // a stuck bubble pops, update stuck union
            for (int i = 0; i < grid.length; i++) {
                for (int j = 0; j < grid[0].length; j++) {
                    if (bubbles.find(linearInd(i, j)) == 0){
                        if (i == d[0] && j == d[1]) {
                            // the chosen bubble is now empty
                            bubbles.union(linearInd(i, j), 1); // TODO: this will merge the stuck union to empty union
                        } else {
                            if (i == 0) { //first row is stuck
                                continue;
                            } else if (j == 0) { // first column
                                if (bubbles.find(linearInd(i - 1, j)) == 0) { // top neighbor is stuck, this one is stuck
                                    bubbles.union(linearInd(i, j), 0);
                                } else if (bubbles.find(linearInd(i - 1, j)) != 1) {
                                    // top neighbor is unstuck, this one is added to the unstuck union
                                    bubbles.union(linearInd(i, j), linearInd(i - 1, j));
                                }
                            } else {// rest columns
                                if (bubbles.find(linearInd(i - 1, j)) == 0 ||
                                        bubbles.find(linearInd(i, j - 1)) == 0) {
                                    // neighbor is stuck, this one and its union is stuck
                                    bubbles.union(linearInd(i, j), 0);
                                } else if (bubbles.find(linearInd(i - 1, j)) == 1 &&
                                        bubbles.find(linearInd(i, j - 1)) == 1) {
                                    bubbles.union(linearInd(i, j), 2);
                                }
                                else {
                                    if (bubbles.find(linearInd(i - 1, j)) != 1) {
                                        // top neighbor is unstuck, this and its union join the unstuck union
                                        bubbles.union(linearInd(i, j), linearInd(i - 1, j));
                                    }
                                    if (bubbles.find(linearInd(i, j - 1)) != 1) {
                                        // left neighbor is unstuck, this and its union join the unstuck union
                                        bubbles.union(linearInd(i, j), linearInd(i, j - 1));
                                    }
                                }
                            }
                        }
                    }
                }
            }
            result[iter] = bNum + bubbles.sizeOf(0) + 1 - drop;
            drop = bNum + bubbles.sizeOf(0) + 1;
            iter += 1;
        }
        return result;
    }

    private int linearInd(int row, int col) {
        return row * grid[0].length + col + 3;
    }
}
