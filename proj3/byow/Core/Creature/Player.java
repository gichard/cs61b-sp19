package byow.Core.Creature;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

public class Player extends Animal {

    public Player(int hp, int vision) {
        super(Tileset.AVATAR,hp, vision);
    }


}
