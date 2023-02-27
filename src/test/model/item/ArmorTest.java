package model.item;

import com.googlecode.lanterna.TextColor;
import model.Game;
import model.Player;
import model.items.Armor;
import model.items.DefensePotion;
import model.items.GuardArmor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ArmorTest {
    Armor armor;
    Game game;
    Player player;

    @BeforeEach
    public void setup() {
        armor = new GuardArmor(10);
        game = new Game(32, 24);
        player = new Player(game);
    }

    @Test
    public void testHealingConstructor() {
        assertEquals("Guard's Armor", armor.getName());
        assertEquals("Sturdy Looking Armor", armor.getDescription().get(0));
        assertEquals("+10 Defense", armor.getDescription().get(1));
        assertEquals(10, armor.getAdditionalDefense());
        assertEquals('$', armor.getTextSprite());
        assertEquals(TextColor.ANSI.WHITE, armor.getTextColor());
        assertEquals(TextColor.ANSI.YELLOW, armor.getBackgroundColor());
    }

    @Test
    public void testEquipArmor() {
        player.addItem(armor);
        armor.useItem(player);
        assertEquals(Player.INITIAL_DEFENSE + 10, player.getDefense());
    }

    @Test
    public void testArmorAndPotion() {
        DefensePotion potion = new DefensePotion();
        player.addItem(potion);
        player.addItem(armor);
        potion.useItem(player);
        armor.useItem(player);
        assertEquals(Player.INITIAL_DEFENSE + DefensePotion.DEFENSE_AMOUNT + 10, player.getDefense());
    }
}
