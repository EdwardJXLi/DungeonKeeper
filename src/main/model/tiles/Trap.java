package model.tiles;

import com.googlecode.lanterna.TextColor;
import model.Entity;
import model.Tile;
import model.graphics.SpriteID;

import java.util.Arrays;

/*
 * Map tile that deals damage when stepped upon
 */

public class Trap extends Tile {
    // Trap Constants
    public static final int TRAP_DAMAGE = 5;

    // EFFECTS: Creates a trap tile that damages player when stepped
    public Trap(int posX, int posY) {
        super(
                posX, posY, false, '"',
                TextColor.ANSI.RED, TextColor.ANSI.DEFAULT, SpriteID.TILE_TRAP, "Spike Trap",
                Arrays.asList("Ouch! That Hurts!", "Standing on Spike Traps", "deals 5 damage!")
        );
    }

    // MODIFIES: e
    // EFFECTS: Damages entity when stepped on
    @Override
    public void onStep(Entity e) {
        e.damage(TRAP_DAMAGE);
        super.onStep(e);
    }
}
