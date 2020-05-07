package byow.Core;

import byow.Core.Creature.Animal;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.awt.*;

public class SlidingFrame {
    private TETile[][] world;
    private TETile[][] renderTiles;
    private int fWidth;
    private int fHeight;
    private Point playerPos;
    private Animal player;
    private Point pos;

    public SlidingFrame(TETile[][] world, int fWidth, int fHeight, Animal player) {
        this.player = player;
        this.playerPos = player.getPos();
        this.world = world;

        this.fWidth = fWidth;
        this.fHeight = fHeight;
        this.pos = new Point();
        centerPlayer();

        prepRenderTiles();
    }

    public TETile[][] getRenderTiles() {
        return renderTiles;
    }

    // update render frame if player is not in current render frame
    public boolean slideFrame() {
        this.playerPos = this.player.getPos();
        if (!playerInFrame()) {
            centerPlayer();
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

    // prepare render frame
    private void prepRenderTiles() {
        slideFrame();
        // add player avatar
        prepAnimal(this.player);
    }

    // add an animal's avatar to render frame
    private void prepAnimal(Animal a) {
        Point aP = a.getPos();
        renderTiles[aP.x - this.pos.x][aP.y - this.pos.y] = a.getAvatar();
    }

    // check if a render tile is in the world
    private boolean validTile(int x, int y){
        return this.pos.x + x >= 0 && this.pos.y + y >= 0
                && this.pos.x + x < world.length && this.pos.y + y < world[0].length;
    }

    // check if the player has moved outside of the frame
    private boolean playerInFrame() {
        return this.pos.x <= playerPos.x && this.pos.x + fWidth > playerPos.x
                && this.pos.y <= playerPos.y && this.pos.y + fHeight > playerPos.y;
    }
}
