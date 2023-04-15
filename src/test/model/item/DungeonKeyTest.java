package model.item;

import com.googlecode.lanterna.TextColor;
import model.Game;
import model.Player;
import model.graphics.SpriteID;
import model.items.DungeonKey;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DungeonKeyTest {
    DungeonKey dungeonKey;
    Game game;
    Player player;

    @BeforeEach
    public void setup() {
        dungeonKey = new DungeonKey();
        game = new Game(32, 24);
        player = new Player(game);
    }

    @Test
    public void testDungeonKeyConstructor() {
        assertEquals("Dungeon Key", dungeonKey.getName());
        assertEquals("Mystical Dungeon Key", dungeonKey.getDescription().get(0));
        assertEquals("Used to unlock the exit", dungeonKey.getDescription().get(1));
        assertEquals("at the center of the map", dungeonKey.getDescription().get(2));
        assertEquals('K', dungeonKey.getTextSprite());
        assertEquals(TextColor.ANSI.WHITE, dungeonKey.getTextColor());
        assertEquals(TextColor.ANSI.YELLOW, dungeonKey.getBackgroundColor());
        assertEquals(SpriteID.ITEM_DUNGEON_KEY, dungeonKey.getSpriteID());
    }

    @Test
    public void testUseItem() {
        dungeonKey.useItem(player);
    }
}
