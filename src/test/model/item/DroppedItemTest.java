package model.item;

import model.Game;
import model.Item;
import model.Player;
import model.enemies.Dummy;
import model.items.HealingPotion;
import org.junit.jupiter.api.BeforeEach;

public class DroppedItemTest {
    Game game;
    Player player;
    Item testItem;

    @BeforeEach
    public void setup() {
        game = new Game(32, 24);
        game.initEmptyGame();
        player = new Player(game);
        testItem = new HealingPotion();
    }
}
