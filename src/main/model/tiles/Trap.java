package model.tiles;

import com.googlecode.lanterna.TextColor;
import model.Entity;
import model.Tile;

import java.util.Arrays;

public class Trap extends Tile {
    // EFFECTS: Creates a trap tile that damages player when stepped
    public Trap(int posX, int posY) {
        super(
                posX, posY, false, '"',
                TextColor.ANSI.RED, TextColor.ANSI.DEFAULT, "Spike Trap",
                Arrays.asList("Ouch! That Hurts!", "Standing on Spike Traps", "deals 5 damage!")
        );
    }

    // MODIFIES: e
    // EFFECTS: Damages entity when stepped on
    @Override
    public void onStep(Entity e) {
        e.damage(5);
    }
}
