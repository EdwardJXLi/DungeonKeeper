package model.tiles;

import com.googlecode.lanterna.TextColor;
import model.Entity;
import model.Tile;

import java.util.Arrays;

public class Trap extends Tile {
    // EFFECTS: Creates a generic wall object
    public Trap(int posX, int posY) {
        super(
                posX, posY, '"', TextColor.ANSI.RED, TextColor.ANSI.DEFAULT, "Spike Trap",
                Arrays.asList("Ouch! That Hurts!", "Standing on Spike Traps", "deals 5 damage!")
        );
    }

    @Override
    public boolean isSolid() {
        return false;
    }

    @Override
    // MODIFIES: e
    // EFFECTS: Damages entity when stepped on
    public void onStep(Entity e) {
        e.damage(5);
    }
}
