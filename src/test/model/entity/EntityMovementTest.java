package model.entity;

import model.Entity;
import model.Game;
import model.enemies.Dummy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class EntityMovementTest {
    Game game;
    Entity entity;

    @BeforeEach
    public void setup() {
        game = new Game(32, 24);
        game.initEmptyGame();
        entity = new Dummy(game);
    }
}
