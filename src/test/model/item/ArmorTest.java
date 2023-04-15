package model.item;

import com.googlecode.lanterna.TextColor;
import model.Game;
import model.Player;
import model.graphics.SpriteID;
import model.items.Armor;
import model.items.DefensePotion;
import model.items.GuardArmor;
import model.items.MageRobe;
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
    public void testGuardArmorConstructor() {
        assertEquals("Guard's Armor", armor.getName());
        assertEquals("Sturdy Looking Armor", armor.getDescription().get(0));
        assertEquals("+10 Defense", armor.getDescription().get(1));
        assertEquals(10, armor.getAdditionalDefense());
        assertEquals('$', armor.getTextSprite());
        assertEquals(TextColor.ANSI.WHITE, armor.getTextColor());
        assertEquals(TextColor.ANSI.YELLOW, armor.getBackgroundColor());
        assertEquals(SpriteID.ITEM_GUARD_ARMOR, armor.getSpriteID());
    }

    @Test
    public void testMageRobeConstructor() {
        armor = new MageRobe();
        assertEquals("Mage's Robe", armor.getName());
        assertEquals(MageRobe.MAGE_DEFENSE, armor.getAdditionalDefense());
        assertEquals(']', armor.getTextSprite());
        assertEquals(TextColor.ANSI.GREEN, armor.getTextColor());
        assertEquals(TextColor.ANSI.DEFAULT, armor.getBackgroundColor());
        assertEquals(SpriteID.ITEM_MAGE_ROBE, armor.getSpriteID());
    }

    @Test
    public void testEquipArmor() {
        player.addItem(armor);
        armor.useItem(player);
        assertEquals(Player.INITIAL_DEFENSE + 10, player.getDefense());
    }

    @Test
    public void testUnequipArmor() {
        player.addItem(armor);
        armor.useItem(player);
        armor.useItem(player);
        assertEquals(Player.INITIAL_DEFENSE, player.getDefense());
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
