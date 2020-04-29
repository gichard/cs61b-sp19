package bearmaps.hw4;

import bearmaps.proj2ab.ArrayHeapMinPQ;
import bearmaps.proj2ab.DoubleMapPQ;
import bearmaps.proj2ab.ExtrinsicMinPQ;
import edu.princeton.cs.algs4.Stopwatch;

import javax.management.ValueExp;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class AStarSolver<Vertex> implements ShortestPathsSolver<Vertex> {
    private double timeSpent;
    private SolverOutcome outcome;
    private List<Vertex> solPath;
    private double solWeight;
    private int numExp;

    private Map<Vertex, Double> distTo;
    private Map<Vertex, Vertex> vertexTo;
    ExtrinsicMinPQ<Vertex> fringe;

    public AStarSolver(AStarGraph<Vertex> input, Vertex start, Vertex end, double timeout) {
        Stopwatch sw = new Stopwatch();
        timeSpent = sw.elapsedTime();
//        fringe = new DoubleMapPQ<>();
        fringe = new ArrayHeapMinPQ<>();
        distTo = new HashMap<>();
        vertexTo = new HashMap<>();
        numExp = 0;
        for (WeightedEdge<Vertex> we: input.neighbors(start)
             ) {
            fringe.add(we.to(), we.weight() + input.estimatedDistanceToGoal(we.to(), end));
            distTo.put(we.to(), we.weight());
            vertexTo.put(we.to(), start);
        }
        fringe.add(start, input.estimatedDistanceToGoal(start, end));
        distTo.put(start, 0.0);
        vertexTo.put(start, start);

        while (fringe.size() > 0 && timeSpent <= timeout) {
            Vertex p = fringe.removeSmallest();
            numExp += 1;
            if (p.equals(end)) {
                outcome = SolverOutcome.SOLVED;
                break;
            }
            for (WeightedEdge<Vertex> we: input.neighbors(p)
                 ) {
                relaxEdge(input, we, end);
            }
            timeSpent = sw.elapsedTime();
        }
        timeSpent = sw.elapsedTime();

        if (fringe.size() == 0 && SolverOutcome.SOLVED != outcome) {
            outcome = SolverOutcome.UNSOLVABLE;
        } else if(timeout <= timeSpent) {
            outcome = SolverOutcome.TIMEOUT;
        }

        solPath = new LinkedList<>();
        if (SolverOutcome.SOLVED == outcome) {
            formPath(start, end, solPath);
            solWeight = distTo(end);
        } else {
            solWeight = 0;
        }

    }
    
    private double distTo(Vertex v) {
        if (distTo.containsKey(v)) {
            return distTo.get(v);
        } else {
            return Double.POSITIVE_INFINITY;
        }
    }

    private void formPath(Vertex start, Vertex end, List<Vertex> sol) {
        if (start.equals(end)) {
            sol.add(start);
        } else {
            formPath(start, vertexTo.get(end), sol);
            sol.add(end);
        }
    }

    private void relaxEdge(AStarGraph<Vertex> input, WeightedEdge<Vertex> e, Vertex end) {
        Vertex p = e.from();
        Vertex q = e.to();
        double w = e.weight();
        if (w + distTo(p) < distTo(q)) {
            distTo.put(q, w + distTo(p));
            vertexTo.put(q, p);
            if (fringe.contains(q)) {
                fringe.changePriority(q, distTo(q) + input.estimatedDistanceToGoal(q, end));
            } else {
                fringe.add(q, distTo(q) + input.estimatedDistanceToGoal(q, end));
            }
        }
    }

    public SolverOutcome outcome() {
        return outcome;
    }

    public List<Vertex> solution() {
        return solPath;
    }

    public double solutionWeight() {
        return solWeight;
    }

    public int numStatesExplored() {
        return numExp;
    }

    public double explorationTime() {
        return timeSpent;
    }
}
