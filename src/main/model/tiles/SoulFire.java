package model.tiles;

import com.googlecode.lanterna.TextColor;
import model.Enemy;
import model.Entity;
import model.Player;
import model.Tile;
import model.graphics.SpriteID;

import java.util.Arrays;

/*
 * Map tile that deals damage to only enemies when stepped upon
 */

public class SoulFire extends Tile {
    // SoulFire Constants
    public static final int SOULFIRE_DAMAGE = 35;

    // EFFECTS: Creates a soul fire tile that only damages players when stepped
    public SoulFire(int posX, int posY) {
        super(
                posX, posY, false, '#',
                TextColor.ANSI.CYAN, TextColor.ANSI.BLUE, SpriteID.SOULFIRE, "Soul Fire",
                Arrays.asList("deals 35 damage to", "ONLY enemies")
        );
    }

    // MODIFIES: e
    // EFFECTS: Damages entity when stepped on
    @Override
    public void onStep(Entity e) {
        // Soul Fire only damage player
        if (e instanceof Enemy) {
            e.damage(SOULFIRE_DAMAGE);
        }

        super.onStep(e);
    }
}
