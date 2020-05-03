package byow.lab12;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.*;

public class TestHexWorld {
    private static final int WIDTH = 100;
    private static final int HEIGHT = 60;

    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        // initialize tiles
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }

//        HexWorld.addHexagon(world, 10, new Point(10, 10), Tileset.FLOWER);
        HexWorld.tesselateWithHex19(world, 3);

        ter.renderFrame(world);
    }
}
