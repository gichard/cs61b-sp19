package bearmaps;

import java.util.List;

public class NaivePointSet implements PointSet {
    private List<Point> points;

    public NaivePointSet(List<Point> points) {
        this.points = points;
    }

    public Point nearest(double x, double y) {
        double dist = 0;
        Point p = null;
        double dx;
        double dy;
        double newDist;
        for (Point p1: points
             ) {
            dx = x - p1.getX();
            dy = y - p1.getY();
            newDist = dx * dx + dy * dy;
            if (dist == 0 || dist > newDist) {
                dist = newDist;
                p = p1;
            }
        }
        return p;
    }
}
