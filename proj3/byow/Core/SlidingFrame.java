package byow.Core;

import byow.Core.Creature.Animal;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public class SlidingFrame {
    private TETile[][] world;
    private TETile[][] renderTiles;
    private int fWidth;
    private int fHeight;
    private Point playerPos;
    private Animal player;
    private Point pos;

    // record animals' Pos, used to recover tile after animal moves to another tile
    private Set<Point> animalPos;

    public SlidingFrame(TETile[][] world, int fWidth, int fHeight, Animal player) {
        this.player = player;
        this.playerPos = player.getPos();
        this.world = world;

        this.fWidth = fWidth;
        this.fHeight = fHeight;
        this.pos = new Point();
        animalPos = new HashSet<>();
        centerPlayer();

        prepRenderTiles();
    }

    public TETile[][] getRenderTiles() {
        prepRenderTiles();
        return renderTiles;
    }

    // update render frame if player is not in current render frame
    public boolean slideFrame() {
        this.playerPos = this.player.getPos();
        int inf = playerInFrame();
        if (inf != 0) {
//            centerPlayer();
            slideFrame(inf);
            return true;
        }
        return false;
    }

    // set the frame pos so that the player is at the center of the render frame
    // if frame size if bigger than or equal to world size, put world in center instead
    private void centerPlayer() {
        if (this.fWidth >= world.length) {
            this.pos.x = (world.length - this.fWidth) / 2;
        } else {
            this.pos.x = this.playerPos.x - fWidth / 2;
        }

        if (this.fHeight >= world[0].length) {
            this.pos.y = (world[0].length - this.fHeight) / 2;
        } else {
            this.pos.y = this.playerPos.y - fHeight / 2;
        }

        // prepare new world frame
        resetFrame();
    }

    // slide the frame according to dir, 0, 1, 2, 3 for up, down, left, right
    private void slideFrame(int dir) {
        switch (dir) {
            case 1:
                this.pos.y += this.fHeight;
                break;
            case 2:
                this.pos.y -= this.fHeight;
                break;
            case 3:
                this.pos.x -= this.fWidth;
                break;
            case 4:
                this.pos.x += this.fWidth;
                break;
        }
    }

    // prepare render frame
    private void prepRenderTiles() {
        slideFrame();
        // add player avatar
        prepAnimal(this.player);
    }

    // add an animal's avatar to render frame
    private void prepAnimal(Animal a) {
        resetFrame();
        Point aP = a.getPos();
        animalPos.add(aP);
        renderTiles[aP.x - this.pos.x][aP.y - this.pos.y] = a.getAvatar();
    }

    // check if a render tile is in the world
    private boolean validTile(int x, int y){
        return this.pos.x + x >= 0 && this.pos.y + y >= 0
                && this.pos.x + x < world.length && this.pos.y + y < world[0].length;
    }

    // check if the player has moved outside of the frame
    // return 0 if it's true, otherwise 1, 2, 3, 4 for up, down, left, right
    private int playerInFrame() {
        if (playerPos.x < this.pos.x) { // left
            return 3;
        }
        if (this.pos.x + fWidth <= playerPos.x) { // right
            return 4;
        }
        if (this.pos.y > playerPos.y) {
            return 2;
        }
        if (this.pos.y + fHeight <= playerPos.y) {
            return 1;
        }
        return 0;
    }

    private void resetFrame() {
//        for (Point p: animalPos
//             ) {
//            animalPos.remove(p);
////            renderTiles[p.x - this.pos.x][p.y - this.pos.y] = world[p.x][p.y];
//            renderTiles[p.x - this.pos.x][p.y - this.pos.y] = Tileset.FLOOR;
//        }
        renderTiles = new TETile[fWidth][fHeight];
        for (int x = 0; x < fWidth; x++) {
            for (int y = 0; y < fHeight; y++) {
                if (validTile(x, y)) {
                    renderTiles[x][y] = world[x + this.pos.x][y + this.pos.y];
                } else {
                    renderTiles[x][y] = Tileset.NOTHING;
                }
            }
        }
    }
}
