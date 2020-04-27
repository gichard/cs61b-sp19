import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.TreeSet;
import java.util.Comparator;

/**
 * Solver for the Flight problem (#9) from CS 61B Spring 2018 Midterm 2.
 * Assumes valid input, i.e. all Flight start times are >= end times.
 * If a flight starts at the same time as a flight's end time, they are
 * considered to be in the air at the same time.
 */
public class FlightSolver {
    private int max;

    public FlightSolver(ArrayList<Flight> flights) {
        /* FIX ME */
        max = 0;
        PriorityQueue<Flight> fQ = new PriorityQueue<>();
        Comparator<Flight> flightComparator = (f1, f2) -> {
            int startC = f1.startTime - f2.startTime;
            int endC =  f1.endTime - f2.endTime;
            if (startC < 0 && endC < 0) {
                return -1;
            } else if (startC > 0 & endC > 0) {
                return 1;
            }
            int st1 = Math.min(f1.startTime, f2.startTime);
            int st2 = Math.max(f1.startTime, f2.startTime);
            int et1 = Math.min(f1.endTime, f2.endTime);
            int et2 = Math.max(f1.endTime, f2.endTime);
            f2.startTime = st2;
            f2.endTime = et1;
            f2.passengers = f1.passengers + f2.passengers;
            max = Math.max(max, f2.passengers);
            if (st2 - st1 > 1) {
                fQ.add(new Flight(st1, st2, f1.startTime == st1 ? f1.passengers : f2.passengers));
            }
            if (et2 - et1 > 1) {
                fQ.add(new Flight(et1, et2, f1.endTime == et2 ? f1.passengers : f2.passengers));
            }
            return 0;
        };

        fQ = new PriorityQueue<>(flightComparator);

        for (Flight f: flights
             ) {
            max = Math.max(max, f.passengers);
            fQ.add(f);
        }
    }

    public int solve() {
        /* FIX ME */
        return max;
    }
}
