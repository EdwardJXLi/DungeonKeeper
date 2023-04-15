package model.items;

import com.googlecode.lanterna.TextColor;
import model.Item;
import model.Player;
import model.graphics.SpriteID;

import java.util.Arrays;

public class DungeonKey extends Item {
    public DungeonKey() {
        super("Dungeon Key",
                Arrays.asList("Mystical Dungeon Key", "Used to unlock the exit", "at the center of the map"),
                'K', TextColor.ANSI.WHITE, TextColor.ANSI.YELLOW, SpriteID.ITEM_DUNGEON_KEY);
    }

    @Override
    public void useItem(Player p) {
        // Do Nothing
    }
}
