package model.item;

import com.googlecode.lanterna.TextColor;
import model.Game;
import model.Player;
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
        player.damage(100);
        player.addItem(healingPot);
        healingPot.useItem(player);
        assertEquals(Player.INITIAL_HEALTH - 100 + HealingPotion.HEAL_AMOUNT, player.getHealth());
    }

    @Test
    public void testMultipleHealingPotion() {
        player.damage(100);
        player.addItem(healingPot);
        player.addItem(healingPot);
        player.addItem(healingPot);
        healingPot.useItem(player);
        healingPot.useItem(player);
        healingPot.useItem(player);
        assertEquals(Player.INITIAL_HEALTH - 100 + HealingPotion.HEAL_AMOUNT * 3, player.getHealth());
    }

    @Test
    public void testDefensePotion() {
        player.addItem(defensePot);
        defensePot.useItem(player);
        assertEquals(Player.INITIAL_DEFENSE + DefensePotion.DEFENSE_AMOUNT, player.getDefense());
    }

    @Test
    public void testMultipleDefensePotion() {
        player.addItem(defensePot);
        player.addItem(defensePot);
        player.addItem(defensePot);
        defensePot.useItem(player);
        defensePot.useItem(player);
        defensePot.useItem(player);
        assertEquals(Player.INITIAL_DEFENSE + DefensePotion.DEFENSE_AMOUNT * 3, player.getDefense());
    }

    @Test
    public void testStrengthPotion() {
        player.addItem(strengthPot);
        strengthPot.useItem(player);
        assertEquals(Player.INITIAL_ATTACK + StrengthPotion.ATTACK_AMOUNT, player.getAttack());
    }

    @Test
    public void testMultipleStrengthPotion() {
        player.addItem(strengthPot);
        player.addItem(strengthPot);
        player.addItem(strengthPot);
        strengthPot.useItem(player);
        strengthPot.useItem(player);
        strengthPot.useItem(player);
        assertEquals(Player.INITIAL_ATTACK + StrengthPotion.ATTACK_AMOUNT * 3, player.getAttack());
    }
}
