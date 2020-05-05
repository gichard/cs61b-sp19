package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Engine {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 30;

    /**
     * Method used for exploring a fresh world. This method should handle all inputs,
     * including inputs from the main menu.
     */
    public void interactWithKeyboard() {
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

        Map<String, Object> params = parseString(input);
        long seed = (long) params.get("seed");
        RandomWorld rw = new RandomWorld(seed, WIDTH, HEIGHT);

        TETile[][] finalWorldFrame = rw.world;
        ter.initialize(WIDTH, HEIGHT);
        ter.renderFrame(finalWorldFrame);
        return finalWorldFrame;
    }

    private Map<String, Object> parseString(String input) {
        if (input == null) {
            throw new IllegalArgumentException();
        }
        Map<String, Object> res = new HashMap<>();

        long seed = 0L;
        for (int i = 0; i < input.length(); i++) {
            if ("N".equals(String.valueOf(input.charAt(i)))) {
                continue;
            } else if ("S".equals(String.valueOf(input.charAt(i)))) {
                if (i < input.length() - 1) {
                    List<Character> actions = new LinkedList<>();
                    for (int a = i + 1; a < input.length(); a++) {
                        actions.add(input.charAt(a));
                    }
                    res.put("actions", actions);
                }
            } else {
                seed = seed * 10 + Integer.parseInt(String.valueOf(input.charAt(i)));
            }
        }
        res.put("seed", seed);

        return res;
    }

    
}
