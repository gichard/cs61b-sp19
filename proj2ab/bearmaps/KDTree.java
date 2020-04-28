package bearmaps;

import java.security.cert.PolicyNode;
import java.util.Comparator;
import java.util.List;

public class KDTree implements PointSet {
    private PNode root;

    public KDTree(List<Point> points) {
        for (Point p: points
             ) {
            add(p);
        }
    }

    public void add(Point p) {
        PNode pN = new PNode(p);
        root = addHelper(pN, root);
    }
    
    private PNode addHelper(PNode pN, PNode insertNode) {
        if (insertNode == null) {
            return pN;
        } else if (pN.compareTo(insertNode) < 0) {
            pN.changeComp();
            insertNode.left = addHelper(pN, insertNode.left);
        } else if (pN.compareTo(insertNode) > 0) {
            pN.changeComp();
            insertNode.right = addHelper(pN, insertNode.right);
        }
        return insertNode;
    }

    @Override
    public Point nearest(double x, double y) {
        Point target = new Point(x, y);
        PNode targetNode = new PNode(target);

        return nearestHelper(targetNode, root, root).p;
    }

    private PNode nearestHelper(PNode target, PNode next, PNode bestPoint) {
        double best = dist(bestPoint.p, target.p);
        if (dist(next.p, target.p) < best) {
            best = dist(next.p, target.p);
            bestPoint = next;
        }
        double lB;
        double rB;
        if (next.left != null) {
            if (next.compareTo(target) > 0 || next.bestToTarget(target.p) < best) {
                PNode lBest = nearestHelper(target, next.left, bestPoint);
                lB = dist(lBest.p, target.p);
                if (best >= lB) {
                    bestPoint = lBest;
                }
            }
        }
        if (next.right != null) {
            if (next.compareTo(target) < 0 || next.bestToTarget(target.p) < best) {
                PNode rBest = nearestHelper(target, next.right, bestPoint);
                rB = dist(rBest.p, target.p);
                if (best >= rB) {
                    bestPoint = rBest;
                }
            }
        }

        return bestPoint;
    }

    private double dist(Point a, Point b) {
        return (a.getX() - b.getX()) * (a.getX() - b.getX())
                + (a.getY() - b.getY()) * (a.getY() - b.getY());
    }

    private static class PNode implements Comparable<PNode> {
        Point p;
        int compDim; // 0 for x, 1 for y
        PNode left;
        PNode right;

        public PNode(Point point, int comp) {
            p = point;
            compDim = comp;
            left = null;
            right = null;
        }

        public PNode() {
            this(null, 0);
        }

        public PNode(Point point) {
            this(point, 0);
        }

        public void changeComp() {
            if (compDim == 0) {
                compDim = 1;
            } else {
                compDim = 0;
            }
        }

        public double bestToTarget(Point tp) {
            if (compDim == 0) {
                return (p.getX() - tp.getX()) * (p.getX() - tp.getX());
            } else {
                return (p.getY() - tp.getY()) * (p.getY() - tp.getY());
            }
        }

        @Override
        public int compareTo(PNode other) {
            if (compDim == 0) {
                return Double.compare(this.p.getX(), other.p.getX());
            } else {
                return Double.compare(this.p.getY(), other.p.getY());
            }
        }
    }
}
