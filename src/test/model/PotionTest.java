package model;

import com.googlecode.lanterna.TextColor;
import model.items.DefensePotion;
import model.items.HealingPotion;
import model.items.StrengthPotion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PotionTest {
    HealingPotion healingPot;
    DefensePotion defensePot;
    StrengthPotion strengthPot;
    Game game;
    Player player;

    @BeforeEach
    public void setup() {
        healingPot = new HealingPotion();
        defensePot = new DefensePotion();
        strengthPot = new StrengthPotion();
        game = new Game(32, 24);
        player = new Player(game);
    }

    @Test
    public void testHealingConstructor() {
        assertEquals("Healing Potion", healingPot.getName());
        assertEquals("Heals 20 Hearts", healingPot.getDescription().get(0));
        assertEquals('▲', healingPot.getTextSprite());
        assertEquals(TextColor.ANSI.WHITE, healingPot.getTextColor());
        assertEquals(TextColor.ANSI.RED, healingPot.getBackgroundColor());
    }

    @Test
    public void testDefenseConstructor() {
        assertEquals("Defense Potion", defensePot.getName());
        assertEquals("Permanently Increases", defensePot.getDescription().get(0));
        assertEquals("Defense by 5", defensePot.getDescription().get(1));
        assertEquals('▲', defensePot.getTextSprite());
        assertEquals(TextColor.ANSI.WHITE, defensePot.getTextColor());
        assertEquals(TextColor.ANSI.BLUE, defensePot.getBackgroundColor());
    }

    @Test
    public void testStrengthConstructor() {
        assertEquals("Strength Potion", strengthPot.getName());
        assertEquals("Permanently Increases", strengthPot.getDescription().get(0));
        assertEquals("Attack by 10", strengthPot.getDescription().get(1));
        assertEquals('▲', strengthPot.getTextSprite());
        assertEquals(TextColor.ANSI.WHITE, strengthPot.getTextColor());
        assertEquals(TextColor.ANSI.MAGENTA, strengthPot.getBackgroundColor());
    }

    @Test
    public void testHealingPotion() {
        player.damage(50);
        player.addItem(healingPot);
        healingPot.useItem(player);
        assertEquals(Player.INITIAL_HEALTH - 50 + HealingPotion.HEAL_AMOUNT, player.getHealth());
    }

    @Test
    public void testDefensePotion() {
        player.addItem(defensePot);
        defensePot.useItem(player);
        assertEquals(Player.INITIAL_DEFENSE + DefensePotion.DEFENSE_AMOUNT, player.getDefense());
    }

    @Test
    public void testStrengthPotion() {
        player.addItem(strengthPot);
        strengthPot.useItem(player);
        assertEquals(Player.INITIAL_ATTACK + StrengthPotion.ATTACK_AMOUNT, player.getAttack());
    }
}
