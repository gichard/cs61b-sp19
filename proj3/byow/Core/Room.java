package byow.Core;

import static byow.Core.RandomUtils.*;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Room extends Building {
    private static final int DEFAULT_SIZE = 5;
    private static final String bType = "Room";

    public Room() {
        this(DEFAULT_SIZE, DEFAULT_SIZE, new Point(0, 0));
    }

    public Room(int width, int height, Point start) {
        super(width, height, start);
        initTiles();
    }

    @Override
    public String getType() {
        return bType;
    }

    public static void main(String[] args) {
        Room testRoom = new Room();
        System.out.println(testRoom.getHeight());
        System.out.println(testRoom.getWidth());
        testRoom.printTiles();

        testRoom = new Room(10, 5, new Point(1,1));
        System.out.println(testRoom.getHeight());
        System.out.println(testRoom.getWidth());
        testRoom.printTiles();
    }
}
