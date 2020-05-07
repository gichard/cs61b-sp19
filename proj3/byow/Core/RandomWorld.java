package byow.Core;

import byow.Core.Creature.Animal;
import byow.Core.Creature.Player;
import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import static byow.Core.RandomUtils.*;

import java.awt.*;
import java.math.RoundingMode;
import java.util.Map;
import java.util.Random;

public class RandomWorld {
    private static final int WORLD_WIDTH = 100;
    private static final int WORLD_HEIGHT = 60;
    private static final long DEFAULT_SEED = 95537L;

    private Random r;
    private int wWidth;
    private int wHeight;
    public TETile[][] world;
    private Building[] buildings;
    private int numB;

    public Animal[] animals;

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
        spawnAnimals();
    }

    public Player getPlayer() {
        return (Player) animals[0];
    }

    // generate random world by a bottom up approach
    private void generateRWorld() {
        int initWidth = uniform(r, 5, 8);
        int initHeight = uniform(r, 5, 8);
        Point initPos = new Point(uniform(r, wWidth / 3, 2 * wWidth / 3),
                                  uniform(r, wHeight / 3, 2 * wHeight / 3));
        Building initRoom = new Room(initWidth, initHeight, initPos);

        int totalStructure = wWidth * wHeight / 4 / 4;
        double randomFactor = uniform(r, 1.0, 1.5);
        totalStructure = (int) Math.floor(totalStructure * randomFactor);

        buildings = new Building[totalStructure];
        numB = 0;
        addBuilding(initRoom);
        while (numB < totalStructure) {
            Building newB = randomNeighbor(initRoom, 10);
            if (newB != null) {
                buildings[numB] = newB;
                connect(initRoom, newB);
                addBuilding(newB);
                totalStructure -= 1;
            }
            int newInit = uniform(r, 0, numB);
            initRoom = buildings[newInit];
        }
    }

    // copy tiles form new building b to world
    private void addBuilding(Building b) {
        buildings[numB] = b;
        numB += 1;
        Point pos = b.getPos();
        TETile[][] tiles = b.getTiles();
        for (int x = 0; x < b.getWidth(); x++) {
            System.arraycopy(tiles[x], 0, world[x + pos.x], pos.y, b.getHeight());
        }
    }

    // check if a new building overlaps with existing buildings
    private boolean isOverlap(int bWidth, int bHeight, Point bPos) {
        for (int x = 0; x < bWidth; x++) {
            for (int y = 0; y < bHeight; y++) {
                if (!world[x + bPos.x][y + bPos.y].equals(Tileset.NOTHING)) {
                    return true;
                }
            }
        }

        return false;
    }

    // check if a new building can not fit into the world
    private boolean isOut(int bWidth, int bHeight, Point bPos) {
        int x = bPos.x;
        int y = bPos.y;
        return x < 0 || x + bWidth > wWidth || y < 0 || y + bHeight > wHeight;
    }

    // generate a random neighbor of an existing building
    // rooms have a higher probability to be next to a hallway
    private Building randomNeighbor(Building b, int timeout) {
        double hwProb = 0.5;
        if ("Room".equals(b.getType())) {
            hwProb = 0.9;
        }

        while (timeout > 0) {
            Map<String, Integer> neighborParams = b.planNeighbor(r, hwProb);
            int nW = neighborParams.get("width");
            int nH = neighborParams.get("height");
            Point nP = new Point(neighborParams.get("posX"), neighborParams.get("posY"));
            if (!isOut(nW, nH, nP) && !isOverlap(nW, nH, nP)) {
                if (neighborParams.get("type") == 1) {
                    return new Room(nW, nH, nP);
                } else {
                    return new HallWay(nW, nH, nP);
                }
            }
            timeout -= 1;
        }
        return null;
    }

    // connects a building b and its newly generated neighbor n
    // by punching a hole(i.e a floor) on both buildings
    private void connect(Building b, Building n) {
        int dir = relativeDir(b, n);
        Point[][] openable = openingCandidates(b, n, dir);
        Point[] open = openable[uniform(r, openable.length)];
        b.addOpening(open[0]);
        this.world[open[0].x][open[0].y] = Tileset.FLOOR;
        n.addOpening(open[1]);
    }

    // computes direction of n relative to b
    private int relativeDir(Building b, Building n) {
        // 0, 1, 2, 3 for up, down, left, right
        if (n.getPos().y == b.getPos().y + b.getHeight()) {
            return 0;
        }
        if (n.getPos().y == b.getPos().y - n.getHeight()) {
            return 1;
        }
        if (n.getPos().x == b.getPos().x - n.getWidth()) {
            return 2;
        }
        if (n.getPos().x == b.getPos().x + b.getWidth()) {
            return 3;
        }
        return -1;
    }

    // compute the intersection of 2 intervals
    private int[] intervalOverlap(int l1, int r1, int l2, int r2) {
        int[] res = new int[2];
        res[0] = Math.max(l1, l2) + 1;
        res[1] = Math.min(r1, r2) - 1;
        return res;
    }

    private Point[][] openingCandidates(Building b, Building n, int dir) {
        int numPoints = 0;
        Point[][] res;
        int[] overlap = new int[]{0, 1};
        switch (dir) {
            case 0:
            case 1:
                overlap = intervalOverlap(b.getPos().x, b.getPos().x + b.getWidth() - 1,
                        n.getPos().x, n.getPos().x + n.getWidth() - 1);
                break;
            case 2:
            case 3:
                overlap = intervalOverlap(b.getPos().y, b.getPos().y + b.getHeight() - 1,
                        n.getPos().y, n.getPos().y + n.getHeight() - 1);
                break;
        }
        numPoints = overlap[1] - overlap[0] + 1;
        res = new Point[numPoints][2];
        addCandidates(res, overlap[0], dir, b);

        return res;
    }

    private void addCandidates(Point[][] candidates, int start, int dir, Building b) {
        int border;
        switch (dir) {
            case 0:
                border = b.getPos().y + b.getHeight() - 1;
                for (int i = 0; i < candidates.length; i++) {
                    candidates[i][0] = new Point(start + i, border);
                    candidates[i][1] = new Point(start + i, border + 1);
                }
                break;
            case 1:
                border = b.getPos().y;
                for (int i = 0; i < candidates.length; i++) {
                    candidates[i][0] = new Point(start + i, border);
                    candidates[i][1] = new Point(start + i, border - 1);
                }
                break;
            case 2:
                border = b.getPos().x;
                for (int i = 0; i < candidates.length; i++) {
                    candidates[i][0] = new Point(border, start + i);
                    candidates[i][1] = new Point(border - 1, start + i);
                }
                break;
            case 3:
                border = b.getPos().x + b.getWidth() - 1;
                for (int i = 0; i < candidates.length; i++) {
                    candidates[i][0] = new Point(border, start + i);
                    candidates[i][1] = new Point(border + 1, start + i);
                }
                break;
        }
    }

    private void spawnAnimals() {
        int numAnimals = uniform(r, 1, 2); // for now, only spawn a player
        animals = new Animal[numAnimals];
        Animal p = new Player(10, 10);
        p.spawn(spawnPos());
        animals[0] = p;
    }

    // randomly generate a spawn point
    private Point spawnPos() {
        Point res = randomPoint();
        while (!validSPoint(res)) {
            res = randomPoint();
        }
        return res;
    }

    // check if a point is a valid spawn point
    private boolean validSPoint(Point sp) {
        return world[sp.x][sp.y].equals(Tileset.FLOOR);
    }

    // generate a random point in the world
    private Point randomPoint() {
        return new Point(uniform(r, 0, wWidth), uniform(r, 0, wHeight));
    }

    private static void testConnect() {
        RandomWorld RW = new RandomWorld();
        RW.world = new TETile[WORLD_WIDTH][WORLD_HEIGHT];
        for (int x = 0; x < WORLD_WIDTH; x++) {
            for (int y = 0; y < WORLD_HEIGHT; y++) {
                RW.world[x][y] = Tileset.NOTHING;
            }
        }

        Building room1 = new Room(5, 5, new Point(50, 20));
        RW.addBuilding(room1);
        Building room2 = new Room(5, 7, new Point(55, 20));
        RW.connect(room1, room2);
        RW.addBuilding(room2);

        Building hw1 = new HallWay(5, 3, new Point(50, 10));
        RW.addBuilding(hw1);
        Building hw2 = new HallWay(3, 3, new Point(55, 10));
        RW.connect(hw1, hw2);
        RW.addBuilding(hw2);

        Building hw3 = new HallWay(10, 3, new Point(60, 20));
        RW.connect(room2, hw3);
        RW.addBuilding(hw3);

        Building vHW = new HallWay(3, 10, new Point(65, 23));
        RW.connect(hw3, vHW);
        RW.addBuilding(vHW);

        TERenderer ter = new TERenderer();
        ter.initialize(WORLD_WIDTH, WORLD_HEIGHT);


        ter.renderFrame(RW.world);
    }

    private static void testGenerator() {
        RandomWorld RW = new RandomWorld();

        TERenderer ter = new TERenderer();
        ter.initialize(WORLD_WIDTH, WORLD_HEIGHT);

        ter.renderFrame(RW.world);
    }

    private static void testGenerator(long seed) {
        RandomWorld RW = new RandomWorld(seed);

        TERenderer ter = new TERenderer();
        ter.initialize(WORLD_WIDTH, WORLD_HEIGHT);

        ter.renderFrame(RW.world);
    }

    public static void main(String[] args) {
        // test random world by visualising


//        testConnect();
//        testGenerator();
        testGenerator(1988L);
    }
}
