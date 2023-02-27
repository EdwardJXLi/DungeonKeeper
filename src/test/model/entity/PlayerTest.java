package model.entity;

import model.Game;
import model.Player;
import model.enemies.Dummy;
import org.junit.jupiter.api.BeforeEach;

public class PlayerTest {
    Game game;
    Player player;

    @BeforeEach
    public void setup() {
        game = new Game(32, 24);
        game.initEmptyGame();
        player = new Player(game);
    }
}
