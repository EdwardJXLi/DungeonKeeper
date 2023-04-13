package model.items;

import com.googlecode.lanterna.TextColor;
import model.Item;
import model.Player;
import model.graphics.SpriteID;
import model.tiles.SoulFire;

import java.util.Arrays;

/*
 * Basic defense potion that permanently increases defense
 */

public class Ectoplasm extends Item {
    // EFFECTS: Creates soul fire that only damages enemies
    public Ectoplasm() {
        super(
                "Ectoplasm",
                Arrays.asList("Spawns Soul Fire", "That Only Damages", "Enemies"),
                '*', TextColor.ANSI.CYAN, TextColor.ANSI.DEFAULT, SpriteID.ITEM_ECTOPLASM);
    }

    // MODIFIES: p
    // EFFECTS: Spawns a soul fire that only damages enemies
    @Override
    public void useItem(Player p) {
        p.getGame().sendMessage("Spawned Soul Fire!");

        // Spawn soul fire in a cross pattern around player
        int playerX = p.getPosX();
        int playerY = p.getPosY();
        p.getGame().getLevel().addTile(new SoulFire(playerX, playerY), true);
        p.getGame().getLevel().addTile(new SoulFire(playerX + 1, playerY + 1), true);
        p.getGame().getLevel().addTile(new SoulFire(playerX - 1, playerY + 1), true);
        p.getGame().getLevel().addTile(new SoulFire(playerX + 1, playerY - 1), true);
        p.getGame().getLevel().addTile(new SoulFire(playerX - 1, playerY - 1), true);

        // Use Item
        p.removeItem(this);
    }
}
