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
        Comparator<Flight> startTimeCmp = (Flight f1, Flight f2) -> Integer.compare(f1.startTime, f2.startTime);
        PriorityQueue<Flight> sfQ = new PriorityQueue<>(startTimeCmp);
        sfQ.addAll(flights);

        Comparator<Flight> endTimeCmp = (Flight f1, Flight f2) -> Integer.compare(f1.endTime, f2.endTime);
        PriorityQueue<Flight> efQ = new PriorityQueue<>(endTimeCmp);
        efQ.addAll(flights);

        int pCount = 0;
        while (sfQ.size() > 0) {
            if (sfQ.peek().startTime <= efQ.peek().endTime) {
                pCount += sfQ.poll().passengers;
                if (pCount > max) {
                    max = pCount;
                }
            } else {
                pCount -= efQ.poll().passengers;
            }
        }

    }

    public int solve() {
        /* FIX ME */
        return max;
    }
}
