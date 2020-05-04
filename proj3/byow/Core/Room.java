package byow.Core;

import java.awt.*;

public class Room extends Building {
    private static final int DEFAULT_SIZE = 5;

    public Room() {
        this(DEFAULT_SIZE, DEFAULT_SIZE, new Point(0, 0));
    }

    public Room(int width, int height, Point start) {
        super(width, height, start);
        initTiles();
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
