package creatures;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.HashMap;
import java.awt.Color;
import huglife.Direction;
import huglife.Action;
import huglife.Occupant;
import huglife.Impassible;
import huglife.Empty;

/** Tests the plip class
 *  @authr FIXME
 */

public class TestPlip {

    @Test
    public void testBasics() {
        Plip p = new Plip(2);
        assertEquals(2, p.energy(), 0.01);
        assertEquals(new Color(99, 255, 76), p.color());
        p.move();
        assertEquals(1.85, p.energy(), 0.01);
        p.move();
        assertEquals(1.70, p.energy(), 0.01);
        p.stay();
        assertEquals(1.90, p.energy(), 0.01);
        p.stay();
        assertEquals(2.00, p.energy(), 0.01);
    }

    @Test
    public void testReplicate() {
        // TODO
        Plip p = new Plip(2);
        Plip c = p.replicate();
        assertNotEquals(p, c);
        assertEquals(1, p.energy(), 0.01);
        assertEquals(1, c.energy(), 0.01);
        assertEquals(2, p.energy() + c.energy(), 0.001);

        p = new Plip(1.5);
        c = p.replicate();
        assertNotEquals(p, c);
        assertEquals(0.75, p.energy(), 0.01);
        assertEquals(0.75, c.energy(), 0.01);
        assertEquals(1.5, p.energy() + c.energy(), 0.001);
    }

    @Test
    public void testChoose() {

        // No empty adjacent spaces; stay.
        Plip p = new Plip(1.2);
        HashMap<Direction, Occupant> surrounded = new HashMap<Direction, Occupant>();
        surrounded.put(Direction.TOP, new Impassible());
        surrounded.put(Direction.BOTTOM, new Impassible());
        surrounded.put(Direction.LEFT, new Impassible());
        surrounded.put(Direction.RIGHT, new Impassible());

        Action actual = p.chooseAction(surrounded);
        Action expected = new Action(Action.ActionType.STAY);

        assertEquals(expected, actual);


        // Energy >= 1; replicate towards an empty space.
        p = new Plip(1.2);
        HashMap<Direction, Occupant> topEmpty = new HashMap<Direction, Occupant>();
        topEmpty.put(Direction.TOP, new Empty());
        topEmpty.put(Direction.BOTTOM, new Impassible());
        topEmpty.put(Direction.LEFT, new Impassible());
        topEmpty.put(Direction.RIGHT, new Impassible());

        actual = p.chooseAction(topEmpty);
        expected = new Action(Action.ActionType.REPLICATE, Direction.TOP);

        assertEquals(expected, actual);


        // Energy >= 1; replicate towards an empty space.
        p = new Plip(1.2);
        HashMap<Direction, Occupant> allEmpty = new HashMap<Direction, Occupant>();
        allEmpty.put(Direction.TOP, new Empty());
        allEmpty.put(Direction.BOTTOM, new Empty());
        allEmpty.put(Direction.LEFT, new Empty());
        allEmpty.put(Direction.RIGHT, new Empty());

        actual = p.chooseAction(allEmpty);
        Action unexpected = new Action(Action.ActionType.STAY);

        assertNotEquals(unexpected, actual);


        // Energy < 1; stay.
        p = new Plip(.99);

        actual = p.chooseAction(allEmpty);
        expected = new Action(Action.ActionType.STAY);

        assertEquals(expected, actual);


        // Energy < 1; stay.
        p = new Plip(.99);

        actual = p.chooseAction(topEmpty);
        expected = new Action(Action.ActionType.STAY);

        assertEquals(expected, actual);


        // We don't have Cloruses yet, so we can't test behavior for when they are nearby right now.
        // spot clorus in neighbor, and have empty neighbor, 50% move
        p = new Plip(0.99);
        HashMap<Direction, Occupant> emptyClorus = new HashMap<>();
        emptyClorus.put(Direction.TOP, new Clorus());
        emptyClorus.put(Direction.BOTTOM, new Empty());
        emptyClorus.put(Direction.LEFT, new Impassible());
        emptyClorus.put(Direction.RIGHT, new Impassible());
        expected = new Action(Action.ActionType.MOVE, Direction.BOTTOM);
        int moveNum = 0;
        for (int i = 0; i < 1000; i++) {
            p = new Plip(0.99);
            actual = p.chooseAction(emptyClorus);
            if (actual.equals(expected)) {
                moveNum += 1;
            }
        }

        assertEquals(0.5, moveNum / 1000.0, 0.1);

        // spot clorus in neighbor, and no empty neighbor, stay
        p = new Plip(0.99);
        HashMap<Direction, Occupant> impassibleClorus = new HashMap<>();
        impassibleClorus.put(Direction.TOP, new Clorus());
        impassibleClorus.put(Direction.BOTTOM, new Impassible());
        impassibleClorus.put(Direction.LEFT, new Impassible());
        impassibleClorus.put(Direction.RIGHT, new Impassible());
        actual = p.chooseAction(impassibleClorus);
        expected = new Action(Action.ActionType.STAY);
        assertEquals(expected, actual);
    }
}
