public class UnionFind {
    private int[] vertices;

    /* Creates a UnionFind data structure holding n vertices. Initially, all
       vertices are in disjoint sets. */
    public UnionFind(int n) {
        vertices = new int[n];
        for (int i = 0; i < n; i++) {
            vertices[i] = -1;
        }
    }

    /* Throws an exception if v1 is not a valid index. */
    private void validate(int vertex) {
        if (vertex >= vertices.length) {
            throw new RuntimeException("IllegalArgumentException");
        }
    }

    /* Returns the size of the set v1 belongs to. */
    public int sizeOf(int v1) {
        validate(v1);
        if (vertices[v1] < 0) {
            return -vertices[v1];
        } else {
            return sizeOf(parent(v1));
        }
    }

    /* Returns the parent of v1. If v1 is the root of a tree, returns the
       negative size of the tree for which v1 is the root. */
    public int parent(int v1) {
        validate(v1);
        return vertices[v1];
    }

    /* Returns true if nodes v1 and v2 are connected. */
    public boolean connected(int v1, int v2) {
        validate(v1);
        validate(v2);
        return find(v1) == find(v2);
    }

    /* Connects two elements v1 and v2 together. v1 and v2 can be any valid
       elements, and a union-by-size heuristic is used. If the sizes of the sets
       are equal, tie break by connecting v1's root to v2's root. Unioning a
       vertex with itself or vertices that are already connected should not
       change the sets but may alter the internal structure of the data. */
    public void union(int v1, int v2) {
        validate(v1);
        validate(v2);
        if (!connected(v1, v2)) {
            if (sizeOf(v1) > sizeOf(v2)) {
                vertices[find(v1)] += vertices[find(v2)]; // add size to v1's root
                vertices[find(v2)] = find(v1); // connect v2's root to v1's root
            } else {
                vertices[find(v2)] += vertices[find(v1)]; // add size to v2's root
                vertices[find(v1)] = find(v2); // connect v1's root to v2's root
            }
        }

    }

    /* Returns the root of the set V belongs to. Path-compression is employed
       allowing for fast search-time. */
    public int find(int vertex) {
        validate(vertex);
        if (parent(vertex) < 0) {
            return vertex;
        } else {
            vertices[vertex] = find(parent(vertex)); // path compression
            return vertices[vertex];
        }
    }

    /* set a vertex's parent to -1*/
    public void setHead(int vertex) {
        validate(vertex);
        vertices[vertex] = -1;
    }

}
