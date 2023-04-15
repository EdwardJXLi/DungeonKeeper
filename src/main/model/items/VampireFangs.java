package model.items;

import com.googlecode.lanterna.TextColor;
import model.graphics.SpriteID;

import java.util.Arrays;

/*
 * Vampire Fangs - Dropped by vampires when killed
 */

public class VampireFangs extends Weapon {
    public static final int FANG_DAMAGE = 60;

    // EFFECTS: Creates a vampire fang weapon.
    public VampireFangs() {
        super(
                "Vampire Fangs", Arrays.asList("Sharp Vampire Fangs."),
                'V', TextColor.ANSI.RED, TextColor.ANSI.DEFAULT, SpriteID.ITEM_VAMPIRE_FANGS,
                FANG_DAMAGE
        );
    }
}
