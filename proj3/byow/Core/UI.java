package byow.Core;

import byow.Core.Creature.Player;
import byow.TileEngine.TETile;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;
import java.util.Collections;

public class UI {
    private static final int TILE_SIZE = 16;
    private static final int WELCOME_WIDTH = 40;
    private static final int WELCOME_HEIGHT = 40;
    public static final int HEADER = 3;
    public static final int FOOT = 1;
//    private int fWidth;
//    private int fHeight;
//
//    public UI() {
//        this(WELCOME_WIDTH, WELCOME_HEIGHT);
//    }
//
//    public UI(int w, int h) {
//        fWidth = w;
//        fHeight = h;
//    }

    public static void showWelcome() {
        StdDraw.setCanvasSize(WELCOME_WIDTH * TILE_SIZE, WELCOME_HEIGHT * TILE_SIZE);
        Font font = new Font("Monaco", Font.BOLD, 40);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, WELCOME_WIDTH);
        StdDraw.setYscale(0, WELCOME_HEIGHT);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();
        StdDraw.setPenColor(Color.WHITE);

        StdDraw.text(WELCOME_WIDTH / 2.0, WELCOME_HEIGHT - 5, "CS61B: THE GAME");

        font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.text(WELCOME_WIDTH / 2.0, WELCOME_HEIGHT / 2.0, "New Game (N)");
        StdDraw.text(WELCOME_WIDTH / 2.0, WELCOME_HEIGHT / 2.0 - 3, "Load Game (L)");
        StdDraw.text(WELCOME_WIDTH / 2.0, WELCOME_HEIGHT / 2.0 - 6, "Quit (Q)");

        StdDraw.show();
    }

    public static void drawStatus(int hp, TETile t, int w, int h) {
        StdDraw.setFont(new Font("Monaco", Font.BOLD, 30));
        StdDraw.setPenColor(Color.WHITE);
        String hpS = String.join("", Collections.nCopies(hp, "♡"));
        StdDraw.textLeft(1.0, h - 1, hpS);
        StdDraw.textRight(w - 1, h - 1, t.description());
    }

    public static void main(String[] args) {
        showWelcome();
    }
}
