package byow.lab12;
import org.junit.Test;
import static org.junit.Assert.*;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.awt.*;
import java.util.Map;
import java.util.Random;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {
    private static final Random r = new Random(95533L);

    public static void tesselateWithHex19(TETile[][] world, int hexSize) {
        int tesSize = 3;
        for (int col = 0; col < 5; col++) {
            Point colStart = new Point(col * (2 * hexSize - 1), colHexOffsetY(col, tesSize, hexSize));
            int colSize = calColHexNum(col, tesSize);
            TETile[] randomTiles = randomTileGen(colSize);
            addHexCol(world, hexSize, colStart, colSize, randomTiles);
        }
    }

    private static int calColHexNum(int hCol, int tesSize) {
        if (hCol >= tesSize) {
            return tesSize * 2 - hCol + 1;
        }

        return tesSize + hCol;
    }

    private static int colHexOffsetY(int hCol, int tesSize, int hexSize) {
        int maxNum = 2 * tesSize - 1;
        return hexSize * (maxNum - calColHexNum(hCol, tesSize));
    }


    private static void addHexCol(TETile[][] world, int sideLength, Point start, int numHex, TETile[] tiles) {
        for (int i = 0; i < numHex; i++) {
            addHexagon(world, sideLength, start, tiles[i]);
            start.y += 2 * sideLength;
        }
    }

    private static TETile[] randomTileGen(int num) {
        TETile[] res = new TETile[num];
        for (int i = 0; i < num; i++) {
            res[i] =getTile(r.nextInt());
        }

        return res;
    }

    private static TETile getTile(int r) {
        r = Math.floorMod(r, 4);
        System.out.println(r);
        switch (r) {
            case 0 : return Tileset.FLOWER;
            case 1 : return Tileset.GRASS;
            case 2 : return Tileset.MOUNTAIN;
            case 3 : return Tileset.SAND;
            default : return Tileset.FLOWER;
        }

    }

    public static void addHexagon(TETile[][] world, int sideLength, Point pos, TETile tile) {
        int startX = pos.x;
        int startY = pos.y;
        boolean[][] hex = hexRegeion(sideLength);
        for (int x = startX; x < startX + hex.length; x++) {
            for (int y = startY; y < startY + hex[0].length; y++) {
                if (hex[x - startX][y - startY]) {
                    world[x][y] = tile;
                }
            }
        }
    }

    private static boolean[][] hexRegeion(int s) {
        int boundWidth = 3 * s - 2;
        int boundHeight = 2 * s;
        boolean[][] boundBox = new boolean[boundWidth][boundHeight];
        for (int x = 0; x < boundWidth; x++) {
            for (int y = 0; y < boundHeight; y++) {
                boundBox[x][y] = inHex(x, y, s);
            }
        }

        return boundBox;
    }

    private static boolean inHex(int x, int y, int s) {
        boolean aboveBLE = x + y >= s - 1;
        boolean belowULE = y - x <= s;
        boolean aboveBRE = x - y <= 2 * s - 2;
        boolean belowURE = x + y <= 4 * s - 3;
        return aboveBLE && belowULE && aboveBRE && belowURE;
    }
}
