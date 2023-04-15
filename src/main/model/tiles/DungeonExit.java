package model.tiles;

import com.googlecode.lanterna.TextColor;
import model.Entity;
import model.Player;
import model.Tile;
import model.graphics.SpriteID;
import model.items.DungeonKey;

import java.util.Arrays;
import java.util.Objects;

/*
 * Level Exit. When player steps on this tile, they win the game. Requires Dungeon Key to "Unlock"
 */

public class DungeonExit extends Tile {
    // EFFECTS: Creates an exit tile that wins the game if conditions are met
    public DungeonExit(int posX, int posY) {
        super(
                posX, posY, false, 'O',
                TextColor.ANSI.WHITE, TextColor.ANSI.YELLOW, SpriteID.TILE_EXIT, "Dungeon Exit",
                Arrays.asList("Get a dungeon key", "unlock this door", "to win the game.")
        );
    }

    // MODIFIES: e
    // EFFECTS: Checks if player steps on exit tile. If player has dungeon key, they win the game.
    @Override
    public void onStep(Entity e) {
        // Exit only works on player
        if (e instanceof Player) {
            Player player = (Player) e;
            // Check if player has dungeon key
            if (player.getInventory().getInventoryItems().stream().anyMatch(
                    item -> Objects.equals(item.getClass(), DungeonKey.class))
            ) {
                // Player wins the game
                player.getGame().winGame();
            } else {
                // Player does not have dungeon key
                e.getGame().sendMessage("You need a dungeon key to unlock this door.");
                e.getGame().sendMessage("Kill the dungeon keeper to get one.");
            }
        }

        super.onStep(e);
    }
}
