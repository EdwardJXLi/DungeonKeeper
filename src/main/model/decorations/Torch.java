package model.decorations;

import com.googlecode.lanterna.TextColor;
import model.Decoration;
import model.Enemy;
import model.Entity;
import model.Tile;
import model.graphics.SpriteID;

import java.util.Arrays;

/*
 * Torch Wall Decoration
 */

public class Torch extends Decoration {
    public enum TorchType {
        CENTER(0),
        LEFT(1),
        RIGHT(2);

        private final int value;
        TorchType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    // EFFECTS: Creates a torch wall decoration
    public Torch(int posX, int posY, TorchType type) {
        super(
                posX, posY, SpriteID.DECORATION_TORCH, "Torch", type.getValue()
        );
    }
}
