package bearmaps;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertEquals;

public class KDTreeTest {
    private List<Point> pointsGen(int numPoints, Random prng) {
        List<Point> res = new ArrayList<>();
        for (int i = 0; i < numPoints; i++) {
            res.add(new Point(prng.nextDouble(), prng.nextDouble()));
        }

        return res;
    }

    @Test
    public void randomTest() {
        Random randPointGen = new Random(1);
        List<Point> points = pointsGen(1000, randPointGen);

        NaivePointSet nPs = new NaivePointSet(points);
        KDTree kdPs = new KDTree(points);

        Random randTargetGen = new Random(2);
        List<Point> targets = pointsGen(100, randTargetGen);

        for (Point target: targets
             ) {
            Point expected = nPs.nearest(target.getX(), target.getY());
            Point actual = kdPs.nearest(target.getX(), target.getY());
            assertEquals(expected.getX(), actual.getX(), 0.01);
            assertEquals(expected.getY(), actual.getY(), 0.01);
        }

    }
}
