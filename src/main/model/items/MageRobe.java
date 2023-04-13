package model.items;

import com.googlecode.lanterna.TextColor;
import model.graphics.SpriteID;

import java.util.Arrays;

/*
 * Mage Robe - Dropped by mages when killed
 */

public class MageRobe extends Armor {
    public static final int MAGE_DEFENSE = 60;

    // EFFECTS: Creates a mage robe armor.
    public MageRobe() {
        super(
                "Mage Robe", Arrays.asList("Strong Mage Robe", "Dropped by Mages"),
                ']', TextColor.ANSI.GREEN, TextColor.ANSI.DEFAULT, SpriteID.ITEM_MAGE_ROBE,
                MAGE_DEFENSE
        );
    }
}
