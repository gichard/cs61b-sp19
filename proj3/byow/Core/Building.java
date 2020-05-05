package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.awt.*;
import java.util.*;
import java.util.List;

import static byow.Core.RandomUtils.uniform;

public class Building {
    private int width;
    private int height;
    private Point pos;
    private Set<Point> openings;
    private TETile[][] tiles;
    private static final String bType = "Building";


    public Building(){}

    public Building(int width, int height, Point pos) {
        this.width = width;
        this.height = height;
        this.pos = pos;
        this.openings = new HashSet<>();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Point getPos() {
        return pos;
    }

    public Set<Point> getOpenings() {
        return openings;
    }

    public TETile[][] getTiles() {
        return tiles;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setOpenings(Point[] openings) {
        for (Point op: openings
             ) {
            addOpening(op);
        }
    }

    public void addOpening(Point op) {
        if (this.openings.add(op)) {
            this.tiles[op.x - pos.x][op.y - pos.y] = Tileset.FLOOR;
        }
    }

    public void initTiles(TETile floor, TETile wall) {
        tiles = new TETile[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (y == 0 || y == height - 1) {
                    tiles[x][y] = wall;
                } else if (x == 0 || x == width - 1) {
                    tiles[x][y] = wall;
                } else {
                    tiles[x][y] = floor;
                }
            }
        }
    }

    public void initTiles() {
        initTiles(Tileset.FLOOR, Tileset.WALL);
    }

    // prints a rotated version of this building, for TEST ONLY
    public void printTiles() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (tiles[x][y].equals(Tileset.FLOOR)) {
                    System.out.print('.');
                }
                if (tiles[x][y].equals(Tileset.WALL)) {
                    System.out.print('#');
                }
            }
            System.out.println();
        }
    }

    public String getType() {
        return bType;
    }

    public Map<String, Integer> planNeighbor(Random r, double hwProb) {
        Map<String, Integer> res = new HashMap<>();

        // randomly choose direction. 0, 1, 2, 3 for up, down, left, right
        int dir = uniform(r, 4);

        // randomly choose neighbor building type, 0 for hallway, 1 for room
        int type = uniform(r, 0.0, 1.0) > hwProb ? 1 : 0;

        // randomly choose neighbor pos and size
        int xOffset = uniform(r, 0, this.getWidth() - 2);
        int yOffset = uniform(r, 0, this.getHeight() - 2);
        int neighborWidth = uniform(r, 3, 10);
        int neighborHeight = uniform(r, 3, 10);

        // put it together
        if (type == 1) {
            res.put("type", 1); // 1 for room
            res.put("width", neighborWidth);
            res.put("height", neighborHeight);
            calNeighborPos(res, dir, xOffset, yOffset, neighborHeight, neighborWidth);
        } else {
            // randomly choose vertical or horizontal hallway,
            res.put("type", 0);
            int verticalHW = uniform(r, 2) ==  0 ? 0 : 1;
            if (verticalHW == 0) { // 0 for horizontal
                res.put("width", neighborWidth);
                res.put("height", 3);
                neighborHeight = 3;
            } else { // 1 for vertical
                res.put("width", 3);
                neighborWidth = 3;
                res.put("height", neighborHeight);
            }

            calNeighborPos(res, dir, xOffset, yOffset, neighborHeight, neighborWidth);
        }

        return res;
    }

    // calculate neighbor position and put it in res
    private void calNeighborPos(Map<String, Integer> res, int dir, int xOffset,
                                int yOffset, int neighborHeight, int neighborWidth) {
        switch (dir) {
            case 0:
                res.put("posX", xOffset + this.getPos().x);
                res.put("posY", this.getHeight() + this.getPos().y);
                break;
            case 1:
                res.put("posX", xOffset + this.getPos().x);
                res.put("posY", this.getPos().y - neighborHeight);
                break;
            case 2:
                res.put("posX", this.getPos().x - neighborWidth);
                res.put("posY", yOffset + this.getPos().y);
                break;
            case 3:
                res.put("posX", this.getPos().x + this.getWidth());
                res.put("posY", yOffset + this.getPos().y);
                break;
        }
    }
}
