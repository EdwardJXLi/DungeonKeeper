package model.entity;

import com.googlecode.lanterna.TextColor;
import model.Game;
import model.enemies.Mage;
import model.graphics.SpriteID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class MageTest {
    Game game;
    Mage mage;

    @BeforeEach
    public void setup() {
        game = new Game(32, 24);
        game.initEmptyGame();
        mage = new Mage(game);
    }

    @Test
    public void constructorTest() {
        assertEquals("Mage", mage.getName());
        assertEquals('M', mage.getTextSprite());
        assertEquals(TextColor.ANSI.BLACK, mage.getTextColor());
        assertEquals(TextColor.ANSI.GREEN, mage.getBackgroundColor());
        assertEquals(SpriteID.ENTITY_MAGE, mage.getSpriteID());
        assertEquals(Mage.INITIAL_HEALTH, mage.getMaxHealth());
        assertEquals(Mage.INITIAL_HEALTH, mage.getHealth());
        assertEquals(Mage.INITIAL_ATTACK, mage.getAttack());
        assertEquals(Mage.INITIAL_DEFENSE, mage.getDefense());
        assertEquals(game, mage.getGame());
        assertFalse(mage.isStunned());
    }
}
