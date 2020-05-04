package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.awt.*;
import java.util.List;
import java.util.Set;

public class Building {
    private int width;
    private int height;
    private Point pos;
    private Set<Point> openings;
    private TETile[][] tiles;

    public Building(){}

    public Building(int width, int height, Point pos) {
        this.width = width;
        this.height = height;
        this.pos = pos;
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
        if (openings.add(op)) {
            tiles[op.x - width + 1][op.y - height + 1] = Tileset.FLOOR;
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
}
