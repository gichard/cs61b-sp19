package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import static byow.Core.RandomUtils.*;

import java.awt.*;
import java.util.Random;

public class RandomWorld {
    private static final int WORLD_WIDTH = 120;
    private static final int WORLD_HEIGHT = 60;
    private static final long DEFAULT_SEED = 95537L;

    private Random r;
    private int wWidth;
    private int wHeight;
    public TETile[][] world;

    public RandomWorld() {
        this(DEFAULT_SEED, WORLD_WIDTH, WORLD_HEIGHT);
    }

    public RandomWorld(long seed) {
        this(seed, WORLD_WIDTH, WORLD_HEIGHT);
    }

    public RandomWorld(long seed, int w, int h) {
        r = new Random(seed);
        wWidth = w;
        wHeight = h;
        world = new TETile[wWidth][wHeight];
        for (int x = 0; x < wWidth; x++) {
            for (int y = 0; y < wHeight; y++) {
                world[x][y] = Tileset.NOTHING;
            }
        }

        generateRWorld();
    }

    private void generateRWorld() {
        int initWidth = uniform(r, 4, 8);
        int initHeight = uniform(r, 4, 8);
        Point initPos = new Point(uniform(r, wWidth / 3, 2 * wWidth / 3),
                                  uniform(r, wHeight / 3, 2 * wHeight / 3));
        Room initRoom = new Room(initWidth, initHeight, initPos);
        addBuilding(initRoom);
    }

    private void addBuilding(Building b) {
        Point pos = b.getPos();
        TETile[][] tiles = b.getTiles();
        for (int x = 0; x < b.getWidth(); x++) {
            System.arraycopy(tiles[x], 0, world[x + pos.x], pos.y, b.getHeight());
        }
    }

    public static void main(String[] args) {
        RandomWorld RW = new RandomWorld();

        TERenderer ter = new TERenderer();
        ter.initialize(WORLD_WIDTH, WORLD_HEIGHT);

        ter.renderFrame(RW.world);
    }
}
