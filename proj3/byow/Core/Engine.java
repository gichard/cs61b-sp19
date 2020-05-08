package byow.Core;

import byow.Core.Creature.Direction;
import byow.Core.Creature.Player;
import byow.Core.Input.InputSource;
import byow.Core.Input.KeyboardInputSource;
import byow.Core.Input.StringInputDevice;
import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.*;
import java.util.List;

public class Engine {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 30;
    public static final int REAL_WIDTH =  100;
    public static final int REAL_HEIGHT = 100;
    public String SAVE_PATH = "./save_data.txt";
    private Map<String, Object> params = new HashMap<>();
    private boolean start = false;
    public RandomWorld rw;
    public SlidingFrame sf;
    private StringBuilder userInput = new StringBuilder();

    /**
     * Method used for exploring a fresh world. This method should handle all inputs,
     * including inputs from the main menu.
     */
    public void interactWithKeyboard() {
        InputSource si = new KeyboardInputSource();

        while (si.possibleNextInput()) {
            if (StdDraw.hasNextKeyTyped()) {
                char instruction = si.getNextKey();
                if (instruction != 'L') { //
                    userInput.append(instruction);
                }
                switch (instruction) {
                    case 'N':
                        if (!start) {
                            getSeed(si);
                            startGame();
                            start = true;
                        }
                        break;
                    case 'L':
                        loadGame(SAVE_PATH);
                        break;
                    case ':':
                        escape(si);
                        break;
                    default:
                        performAction(instruction);
                }
            }
            if (start) {
                refresh(true);
            }
        }
    }

    /**
     * Method used for autograding and testing your code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The engine should
     * behave exactly as if the user typed these characters into the engine using
     * interactWithKeyboard.
     *
     * Recall that strings ending in ":q" should cause the game to quite save. For example,
     * if we do interactWithInputString("n123sss:q"), we expect the game to run the first
     * 7 commands (n123sss) and then quit and save. If we then do
     * interactWithInputString("l"), we should be back in the exact same state.
     *
     * In other words, both of these calls:
     *   - interactWithInputString("n123sss:q")
     *   - interactWithInputString("lww")
     *
     * should yield the exact same world state as:
     *   - interactWithInputString("n123sssww")
     *
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] interactWithInputString(String input) {
        // passed in as an argument, and return a 2D tile representation of the
        // world that would have been drawn if the same inputs had been given
        // to interactWithKeyboard().
        //
        // See proj3.byow.InputDemo for a demo of how you can make a nice clean interface
        // that works for many different input types.

        InputSource si = new StringInputDevice(input);
        parseString(si);

        TETile[][] finalWorldFrame = rw.world;

        return finalWorldFrame;
    }

    private void parseString(InputSource input) {
        if (input == null) {
            throw new IllegalArgumentException();
        }

        while (input.possibleNextInput()) {
            char instruction = input.getNextKey();
            userInput.append(instruction);
            switch (instruction) {
                case 'N':
                    if (!start) {
                        getSeed(input);
                        startGame();
                        start = true;
                    }
                    break;
                case 'L':
                    if (!start) {
                        loadGame(SAVE_PATH);
                    }
                    break;
                case ':':
                    escape(input);
                    break;
                default:
                    performAction(instruction);
                    refresh(false);
            }
        }


    }

    private void getSeed(InputSource input) {
        long seed = 0L;
        while (input.possibleNextInput()){
            char k = input.getNextKey();
            userInput.append(k);
            if ("N".equals(String.valueOf(k))) {
                continue;
            } else if ("S".equals(String.valueOf(k))) {
                this.params.put("seed", seed);
                return;
            } else {
                seed = seed * 10 + Integer.parseInt(String.valueOf(k));
            }
        }
    }

    private void saveExit() {

        String s = userInput.substring(0, userInput.length() - 2); // ignore the ":Q"
        try {
            File sf = new File(SAVE_PATH);
            if (!sf.exists()) {
                sf.createNewFile();
            }

            FileWriter fw = new FileWriter(sf);
            fw.write(s);
            fw.flush();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.exit(0);
    }

    private void performAction(char action) {
//        ((LinkedList<Character>) params.get("actions")).add(action);
        Point dir = new Point(0, 0);
        switch (action) {
            case 'W':
                dir = Direction.UP;
                break;
            case 'A':
                dir = Direction.LEFT;
                break;
            case 'S':
                dir = Direction.DOWN;
                break;
            case 'D':
                dir = Direction.RIGHT;
                break;
        }
        if (isActionValid(dir)) {
            rw.getPlayer().move(dir);
        }
    }

    private boolean isActionValid(Point dir) {
        Point target = new Point();
        Point pPos = rw.getPlayer().getPos();
        target.x = dir.x + pPos.x;
        target.y = dir.y + pPos.y;
        // only check if the target is a wall
        // current game won't allow the player to get to world's border
        return !rw.world[target.x][target.y].equals(Tileset.WALL);
    }

    private void startGame() {
        rw = new RandomWorld((long) params.get("seed"), REAL_WIDTH, REAL_HEIGHT);

        sf = new SlidingFrame(rw.world, WIDTH, HEIGHT, rw.getPlayer());

//        TETile[][] finalWorldFrame = sf.getRenderTiles();
        ter.initialize(WIDTH, HEIGHT + UI.HEADER, 0, UI.FOOT);
//        ter.renderFrame(finalWorldFrame, 5, Tileset.FLOOR);
    }

    private void escape(InputSource input) {
        if (input.possibleNextInput()) {
            char op = input.getNextKey();
            userInput.append(op);
            switch (op) {
                case 'Q':
                    saveExit();
            }
        }
    }

    private void loadGame(String path) {
        File sf = new File(path);
        if (sf.isFile() && sf.exists()) {
            try {
                FileInputStream fis = new FileInputStream(sf);
                InputStreamReader isr = new InputStreamReader(fis);
                BufferedReader br = new BufferedReader(isr);

                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
                interactWithInputString(sb.toString());
                refresh(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.exit(-1);
        }
    }

    private void refresh(boolean doRender) {
        if (doRender) {
            ter.renderFrame(sf.getRenderTiles(), rw.getPlayer().getHP());
        } else {
            sf.slideFrame();
        }
    }
}
