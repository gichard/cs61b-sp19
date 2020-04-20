import org.junit.Test;
import static org.junit.Assert.*;

public class TestUnionFind {
    @Test
    public void testBasic() {
        UnionFind un = new UnionFind(10);
        for (int i = 0; i < 10; i++) {
            assertEquals(1, un.sizeOf(i));
            assertEquals(-1, un.parent(i));
            try {
                assertFalse(un.connected(i, i + 1));
            } catch (RuntimeException e1) {
                System.out.println("Illegal argument in " + i + " and " + (i + 1));
            }
        }
    }

    @Test
    public void testUnion() {
        UnionFind un = new UnionFind(10);
        un.union(0, 1);
        un.union(2, 3);
        un.union(1, 2);
        un.union(1, 8);

        un.union(4, 5);
        un.union(4, 6);
        un.union(6, 7);
        un.union(4, 9);

        assertFalse(un.connected(1, 9));
        assertTrue(un.connected(1, 3));
        assertTrue(un.connected(4, 9));

        assertEquals(3, un.find(1));
        assertEquals(5, un.find(4));
    }

    @Test
    public void testPathCompression() {
        UnionFind un = new UnionFind(10);
        un.union(0, 1);
        un.union(2, 3);
        un.union(1, 2);
        un.union(1, 8);

        assertEquals(1, un.parent(0));
        assertEquals(3, un.find(0));
        assertEquals(3, un.parent(0));
    }
}
