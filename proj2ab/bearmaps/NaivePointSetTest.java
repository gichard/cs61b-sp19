package bearmaps;

import org.junit.Test;
import java.util.LinkedList;
import static org.junit.Assert.*;

public class NaivePointSetTest {
    @Test
    public void simpleTest() {
        Point p1 = new Point(1.1, 2.2); // constructs a Point with x = 1.1, y = 2.2
        Point p2 = new Point(3.3, 4.4);
        Point p3 = new Point(-2.9, 4.2);
        LinkedList<Point> points = new LinkedList<>();
        points.add(p1);
        points.add(p2);
        points.add(p3);

        NaivePointSet nn = new NaivePointSet(points);
        Point ret = nn.nearest(3.0, 4.0); // returns p2
        assertEquals(3.3, ret.getX(), 0.01);
        assertEquals(4.4, ret.getY(), 0.01);
    }
}
