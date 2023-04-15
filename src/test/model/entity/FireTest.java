package model.entity;

import com.googlecode.lanterna.TextColor;
import model.Game;
import model.enemies.Fire;
import model.graphics.SpriteID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class FireTest {
    Game game;
    Fire fire;

    @BeforeEach
    public void setup() {
        game = new Game(32, 24);
        game.initEmptyGame();
        fire = new Fire(game);
    }

    @Test
    public void constructorTest() {
        assertEquals("Fire", fire.getName());
        assertEquals('#', fire.getTextSprite());
        assertEquals(TextColor.ANSI.YELLOW, fire.getTextColor());
        assertEquals(TextColor.ANSI.RED, fire.getBackgroundColor());
        assertEquals(SpriteID.FIRE, fire.getSpriteID());
        assertEquals(Fire.INITIAL_HEALTH, fire.getMaxHealth());
        assertEquals(Fire.INITIAL_HEALTH, fire.getHealth());
        assertEquals(Fire.INITIAL_ATTACK, fire.getAttack());
        assertEquals(Fire.INITIAL_DEFENSE, fire.getDefense());
        assertEquals(game, fire.getGame());
        assertFalse(fire.isStunned());
    }
}
