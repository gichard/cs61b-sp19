package byow.Core;

import java.awt.*;

public class HallWay extends Building {
    private static final int DEFAULT_WIDTH = 5;
    private static final int DEFAULT_HEIGHT = 3;

    public HallWay() {
        this(DEFAULT_WIDTH, DEFAULT_HEIGHT, new Point(0, 0));
    }

    public HallWay(int width, int height, Point pos) {
        super(width, height, pos);
        initTiles();
    }

    public static HallWay horizontalHallway(int length, Point pos) {
        return new HallWay(length, DEFAULT_HEIGHT, pos);
    }

    public static HallWay verticalHallway(int length, Point pos) {
        return new HallWay(DEFAULT_HEIGHT, length, pos);
    }

    public static void main(String[] args) {
        HallWay testHW = new HallWay();
        testHW.printTiles();

        testHW = new HallWay(10, 3, new Point(0, 0));
        testHW.printTiles();

        testHW = verticalHallway(10, new Point(0, 0));
        testHW.printTiles();

        testHW = horizontalHallway(10, new Point(0, 0));
        testHW.printTiles();
    }
}
