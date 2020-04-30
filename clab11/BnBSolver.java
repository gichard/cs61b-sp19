import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * BnBSolver for the Bears and Beds problem. Each Bear can only be compared to Bed objects and each Bed
 * can only be compared to Bear objects. There is a one-to-one mapping between Bears and Beds, i.e.
 * each Bear has a unique size and has exactly one corresponding Bed with the same size.
 * Given a list of Bears and a list of Beds, create lists of the same Bears and Beds where the ith Bear is the same
 * size as the ith Bed.
 */
public class BnBSolver {
    private List<Bear> solvedBears;
    private List<Bed> solvedBeds;

    public BnBSolver(List<Bear> bears, List<Bed> beds) {
        List<Pair<Bear, Bed>> unsorted = new ArrayList<>();
        for (int i = 0; i < bears.size(); i++) {
            unsorted.add(new Pair<Bear, Bed>(bears.get(i), beds.get(i)));
        }

        List<Pair<Bear, Bed>> sorted = quickSortBnB(unsorted);
        solvedBears = new ArrayList<>();
        solvedBeds = new ArrayList<>();
        for (Pair<Bear, Bed> p: sorted
             ) {
            solvedBears.add(p.first());
            solvedBeds.add(p.second());
        }
    }

    /**
     * Returns List of Bears such that the ith Bear is the same size as the ith Bed of solvedBeds().
     */
    public List<Bear> solvedBears() {
        return solvedBears;
    }

    /**
     * Returns List of Beds such that the ith Bear is the same size as the ith Bear of solvedBears().
     */
    public List<Bed> solvedBeds() {
        return solvedBeds;
    }

    private List<Pair<Bear, Bed>>  quickSortBnB(List<Pair<Bear, Bed>> unsorted) {
        if (unsorted.size() <= 1) {
            return unsorted;
        }

        List<Pair<Bear, Bed>> left = new ArrayList<>();
        List<Pair<Bear, Bed>> equal = new ArrayList<>();
        List<Pair<Bear, Bed>> right = new ArrayList<>();

        partition(unsorted, getRandomPair(unsorted), left, equal, right);

        return catenate(catenate(quickSortBnB(left), equal), quickSortBnB(right));
    }

    private Pair<Bear, Bed> getRandomPair(List<Pair<Bear, Bed>> pairs) {
        int pivotIndex = (int) (Math.random() * pairs.size());
        Pair<Bear, Bed> pivot = null;
        for (Pair<Bear, Bed> pair : pairs) {
            if (pivotIndex == 0) {
                pivot = pair;
                break;
            }
            pivotIndex--;
        }
        return pivot;
    }

    private void partition(List<Pair<Bear, Bed>> unsorted, Pair<Bear, Bed> pivot,
                           List<Pair<Bear, Bed>> left, List<Pair<Bear, Bed>> equal
                            , List<Pair<Bear, Bed>> right) {
        for (Pair<Bear, Bed> p: unsorted
             ) {
            if (p.second().compareTo(pivot.first()) == 0) {
                swapBed(p, pivot);
            }
        }

        // partition bed
        for (Pair<Bear, Bed> p: unsorted
        ) {
            if (p.second().compareTo(pivot.first()) < 0) {
                left.add(p);
            } else if (p.second().compareTo(pivot.first()) > 0) {
                right.add(p);
            }
        }

        //partition bear
        Iterator<Pair<Bear, Bed>> leftIter = left.iterator();
        Iterator<Pair<Bear, Bed>> rightIter = right.iterator();
        Pair<Bear, Bed> lP;
        Pair<Bear, Bed> rP;
        while (leftIter.hasNext() && rightIter.hasNext()) {
            lP = nextUnordered(leftIter, pivot, -1);
            rP = nextUnordered(rightIter, pivot, 1);
            if (lP != null) {
                lP.swapFirst(rP);
            }
        }

        equal.add(pivot);
    }

    // left dir = -1, right dir = 1
    private Pair<Bear, Bed> nextUnordered(Iterator<Pair<Bear, Bed>> iter, Pair<Bear, Bed> pivot, int dir) {
        if (!iter.hasNext()) {
            return null;
        }
        Pair<Bear, Bed> p = iter.next();
        while (p.first().compareTo(pivot.second()) * dir > 0) {
            if (iter.hasNext()) {
                p = iter.next();
            } else {
                return null;
            }
        }
        return p;
    }

    private void swapBed(Pair<Bear, Bed> p1, Pair<Bear, Bed> p2) {
       p1.swapSecond(p2);
    }

    private List<Pair<Bear, Bed>> catenate(List<Pair<Bear, Bed>> l1, List<Pair<Bear, Bed>> l2) {
        List<Pair<Bear, Bed>> catenated = new ArrayList<>();
        for (Pair<Bear, Bed> item: l1
             ) {
            catenated.add(item);
        }
        for (Pair<Bear, Bed> item: l2
        ) {
            catenated.add(item);
        }
        return catenated;
    }
}
