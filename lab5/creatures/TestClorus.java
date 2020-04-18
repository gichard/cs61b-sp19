package creatures;

import huglife.*;
import org.junit.Assert;
import org.junit.Test;
import org.omg.PortableInterceptor.ACTIVE;

import java.awt.*;
import java.util.HashMap;

import static org.junit.Assert.*;

public class TestClorus {
    @Test
    public void testBasics() {
        Clorus cl = new Clorus(10);
        assertEquals(10, cl.energy(), 0.01);
        assertEquals(new Color(34, 0, 231), cl.color());
        assertEquals("clorus", cl.name());
        cl.move();
        assertEquals(9.97, cl.energy(), 0.01);
        cl.stay();
        assertEquals(9.96, cl.energy(), 0.01);
        cl.move();
        assertEquals(9.93, cl.energy(), 0.01);
        cl.stay();
        assertEquals(9.92, cl.energy(), 0.01);

        Plip targetP = new Plip(2);
        cl.attack(targetP);
        assertEquals(11.92, cl.energy(), 0.01);
    }

    @Test
    public void testReplicate() {
        Clorus pC = new Clorus(2);
        Clorus cC = pC.replicate();
        assertNotEquals(pC, cC);
        assertEquals(1, pC.energy(), 0.01);
        assertEquals(1, cC.energy(), 0.01);
        assertEquals(2, pC.energy() + cC.energy(), 0.001);
    }

    @Test
    public void testChooseAction() {
        // No empty space, stay
        Clorus cl = new Clorus(10);
        HashMap<Direction, Occupant> surrounded = new HashMap<Direction, Occupant>();
        surrounded.put(Direction.TOP, new Impassible());
        surrounded.put(Direction.BOTTOM, new Impassible());
        surrounded.put(Direction.LEFT, new Impassible());
        surrounded.put(Direction.RIGHT, new Impassible());
        Action actual = cl.chooseAction(surrounded);
        Action expected = new Action(Action.ActionType.STAY);
        assertEquals(expected, actual);

        // see Plips, but no empty space, stay
        cl = new Clorus(10);
        HashMap<Direction, Occupant> topPlip = new HashMap<>();
        topPlip.put(Direction.TOP, new Plip());
        topPlip.put(Direction.BOTTOM, new Impassible());
        topPlip.put(Direction.LEFT, new Impassible());
        topPlip.put(Direction.RIGHT, new Impassible());
        actual = cl.chooseAction(topPlip);
        expected = new Action(Action.ActionType.STAY);
        assertEquals(expected, actual);

        // see Plips, have empty space, attack
        cl = new Clorus(10);
        HashMap<Direction, Occupant> emptyPlip = new HashMap<>();
        emptyPlip.put(Direction.TOP, new Plip());
        emptyPlip.put(Direction.BOTTOM, new Empty());
        emptyPlip.put(Direction.LEFT, new Impassible());
        emptyPlip.put(Direction.RIGHT, new Impassible());
        actual = cl.chooseAction(emptyPlip);
        expected = new Action(Action.ActionType.ATTACK, Direction.TOP);
        assertEquals(expected, actual);

        // Energy >= 1; replicate towards an empty space.
        cl = new Clorus(10);
        HashMap<Direction, Occupant> allEmpty = new HashMap<>();
        allEmpty.put(Direction.TOP, new Empty());
        allEmpty.put(Direction.BOTTOM, new Empty());
        allEmpty.put(Direction.LEFT, new Empty());
        allEmpty.put(Direction.RIGHT, new Empty());
        actual = cl.chooseAction(allEmpty);
        expected = new Action(Action.ActionType.STAY);
        assertNotEquals(expected, actual);

        // Energy < 1, Move
        cl = new Clorus(0.5);
        actual = cl.chooseAction(allEmpty);
        expected = new Action(Action.ActionType.STAY);
        assertNotEquals(expected, actual);

        // Energy < 1, Move
        cl = new Clorus(0.5);
        HashMap<Direction, Occupant> topEmpty = new HashMap<>();
        topEmpty.put(Direction.TOP, new Empty());
        topEmpty.put(Direction.BOTTOM, new Impassible());
        topEmpty.put(Direction.LEFT, new Impassible());
        topEmpty.put(Direction.RIGHT, new Impassible());
        actual = cl.chooseAction(topEmpty);
        expected = new Action(Action.ActionType.MOVE, Direction.TOP);
        assertEquals(expected, actual);

    }

}
