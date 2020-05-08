package byow.Core.Creature;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.awt.*;

public class Animal {
    private static final int MAX_HEALTH = 5;
    private static final int DEFAULT_VISION = 10;
    private TETile avatar;
    private Point pos;
    private int health;
    private boolean alive;
    private boolean visible;
    private int vision;

    public Animal() {
        this(Tileset.ANIMAL, MAX_HEALTH, DEFAULT_VISION);
    }

    public Animal(TETile avatar, int hp, int vision) {
        this.avatar = avatar;
        this.health = hp;
        this.vision = vision;
        this.alive = true;
        this.visible = true;

        this.pos = new Point (-1, -1); // the true position is later decided by spawn()
    }

    // dir is one of (1, 0), (0, 1), (-1, 0), (0, -1)
    public void move(Point dir) {
        pos.x += dir.x;
        pos.y += dir.y;
        System.out.println(this.avatar.description() + " moved in direction " + dir.x + " ," + dir.y);
    }

    // set the animal's position. also refresh its health if dead
    public void spawn(Point sp){
        this.alive = true;
        this.health = MAX_HEALTH;
        this.pos = sp;
    }

    public Point getPos() {
        return this.pos;
    }

    public int getHP() {
        return this.health;
    }

    public boolean isAlive() {
        return this.alive;
    }

    public boolean isVisible() {
        return this.visible;
    }

    public TETile getAvatar() {
        return this.avatar;
    }
}
