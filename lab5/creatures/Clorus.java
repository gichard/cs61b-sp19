package creatures;

import huglife.*;

import java.awt.Color;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;

public class Clorus extends Creature {

    /**
     * red color.
     */
    private int r;
    /**
     * green color.
     */
    private int g;
    /**
     * blue color.
     */
    private int b;

    /**
     * creates plip with energy equal to E.
     */
    public Clorus(double e) {
        super("clorus");
        r = 34;
        g = 0;
        b = 231;
        energy = e;
    }

    /**
     * creates a plip with energy equal to 1.
     */
    public Clorus() {
        this(1);
    }

    public Color color() {
        return color(r, g, b);
    }

    public void attack(Creature c) {
        energy += c.energy();
    }

    public void move() {
        energy = Math.max(0, energy - 0.03);
    }

    public void stay() {
        energy = Math.max(0, energy - 0.01);
    }

    public Clorus replicate() {
        double loseEnergy = this.energy / 2.0;
        this.energy -= loseEnergy;
        return new Clorus(loseEnergy);
    }

    /** Rules:
     * 1.If there are no empty squares, the Clorus will STAY (even if there are Plips nearby
     *  they could attack since plip squares do not count as empty squares).
     * 2. Otherwise, if any Plips are seen, the Clorus will ATTACK one of them randomly.
     * 3. Otherwise, if the Clorus has energy greater than or equal to 1,
     * it will REPLICATE to a random empty square.
     * 4. Otherwise, the Clorus will MOVE to a random empty square.*/
    public Action chooseAction(Map<Direction, Occupant> neighbors) {
        Deque<Direction> emptyNeighbors = new ArrayDeque<>();
        Deque<Direction> seenPlips = new ArrayDeque<>();
        boolean anyPlips = false;
        for (Map.Entry<Direction, Occupant> e : neighbors.entrySet()) {
            if (e.getValue().name().equals("plip")) {
                seenPlips.addLast(e.getKey());
            } else if(e.getValue().name().equals("empty")) {
                emptyNeighbors.addLast(e.getKey());
            }
        }
        anyPlips = (seenPlips.size() != 0);

        // Rule 1
        if (emptyNeighbors.size() == 0) {
            return new Action(Action.ActionType.STAY);
        }

        // Rule 2
        if (anyPlips) {
            return new Action(Action.ActionType.ATTACK, HugLifeUtils.randomEntry(seenPlips));
        }

        //Rule 3
        if (energy >= 1.0 && Math.random() <= 0.5) {
            return new Action(Action.ActionType.REPLICATE, HugLifeUtils.randomEntry(emptyNeighbors));
        }

        // Rule 4
        return new Action(Action.ActionType.MOVE, HugLifeUtils.randomEntry(emptyNeighbors));
    }


}
