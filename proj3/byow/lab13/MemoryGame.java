package byow.lab13;

import edu.princeton.cs.introcs.StdDraw;

import java.awt.Color;
import java.awt.Font;
import java.util.Collections;
import java.util.Random;

public class MemoryGame {
    private int width;
    private int height;
    private int round;
    private Random rand;
    private boolean gameOver;
    private boolean playerTurn;
    private static final char[] CHARACTERS = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    private static final String[] ENCOURAGEMENT = {"You can do this!", "I believe in you!",
                                                   "You got this!", "You're a star!", "Go Bears!",
                                                   "Too easy for you!", "Wow, so impressive!"};

    public static void main(String[] args) throws InterruptedException {
        if (args.length < 1) {
            System.out.println("Please enter a seed");
            return;
        }

        int seed = Integer.parseInt(args[0]);
        MemoryGame game = new MemoryGame(80, 40, seed);
        game.startGame();
    }

    public MemoryGame(int width, int height, int seed) {
        /* Sets up StdDraw so that it has a width by height grid of 16 by 16 squares as its canvas
         * Also sets up the scale so the top left is (0,0) and the bottom right is (width, height)
         */
        this.width = width;
        this.height = height;
        StdDraw.setCanvasSize(this.width * 16, this.height * 16);
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, this.width);
        StdDraw.setYscale(0, this.height);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();

        //Initialize random number generator
        rand = new Random(seed);
    }

    // Generate random string of letters of length n
    public String generateRandomString(int n) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            sb.append(CHARACTERS[rand.nextInt(CHARACTERS.length)]);
        }
        return sb.toString();
    }

    //Take the string and display it in the center of the screen
    //If game is not over, display relevant game information at the top of the screen
    public void drawFrame(String s) {
        StdDraw.clear(Color.BLACK);
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.text(this.width / 2.0, this.height / 2.0, s);

        if (!gameOver) {
            StdDraw.textLeft(0.5, (double) (this.height - 2), String.format("Round: %d", round));
            StdDraw.textRight((double) width - 0.5, (double) (this.height - 2),
                    ENCOURAGEMENT[rand.nextInt(ENCOURAGEMENT.length)]);
            if (playerTurn) {
                StdDraw.text(this.width / 2.0, (double) (this.height - 2), "Type!");
            } else {
                StdDraw.text(this.width / 2.0, (double) (this.height - 2), "Watch!");
            }

            StdDraw.text(0.0, (double) (this.height - 2.5),
                    String.join("", Collections.nCopies(width, "__")));
        }


        StdDraw.show();
    }

    // Display each character in letters, making sure to blank the screen between letters
    public void flashSequence(String letters) throws InterruptedException {
        for (int i = 0; i < letters.length(); i++) {
            drawFrame(String.valueOf(letters.charAt(i)));
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            drawFrame("");
            try {
                Thread.sleep(500L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // Read n letters of player input, string built up so far will appear
    // centered on the screen as keys are being typed by the user
    public String solicitNCharsInput(int n) {
        StringBuilder sb = new StringBuilder();
        while (n > 0) {
            if (StdDraw.hasNextKeyTyped()) {
                sb.append(StdDraw.nextKeyTyped());
                drawFrame(sb.toString());
                n--;
            }
        }
        return sb.toString();
    }

    public void startGame() throws InterruptedException {
        // Set any relevant variables before the game starts
        round = 1;
        gameOver = false;
        playerTurn = false;

        // Establish Engine loop
        while (!gameOver) {
            drawFrame(String.format("Round: %d", round));
            Thread.sleep(1000L);
            String target = generateRandomString(round);
            flashSequence(target);
            playerTurn = true;
            String userInput = solicitNCharsInput(round);
            if (target.equals(userInput)) {
                round += 1;
            } else {
                gameOver = true;
            }
            playerTurn = false;
        }

        drawFrame(String.format("Game Over! You made it to round: %d", round));
    }

}
