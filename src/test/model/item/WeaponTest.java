package model.item;

import com.googlecode.lanterna.TextColor;
import model.Game;
import model.Player;
import model.graphics.SpriteID;
import model.items.GuardSword;
import model.items.StrengthPotion;
import model.items.VampireFangs;
import model.items.Weapon;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WeaponTest {
    Weapon weapon;
    Game game;
    Player player;

    @BeforeEach
    public void setup() {
        weapon = new GuardSword(16);
        game = new Game(32, 24);
        player = new Player(game);
    }

    @Test
    public void testHealingConstructor() {
        assertEquals("Guard's Sword", weapon.getName());
        assertEquals("Sharp Looking Sword", weapon.getDescription().get(0));
        assertEquals("+16 Attack", weapon.getDescription().get(1));
        assertEquals(16, weapon.getAdditionalAttack());
        assertEquals('!', weapon.getTextSprite());
        assertEquals(TextColor.ANSI.RED, weapon.getTextColor());
        assertEquals(TextColor.ANSI.YELLOW, weapon.getBackgroundColor());
        assertEquals(SpriteID.ITEM_GUARD_SWORD, weapon.getSpriteID());
    }

    @Test
    public void testVampireFangsConstructor() {
        weapon = new VampireFangs();
        assertEquals("Vampire Fangs", weapon.getName());
        assertEquals(VampireFangs.FANG_DAMAGE, weapon.getAdditionalAttack());
        assertEquals('V', weapon.getTextSprite());
        assertEquals(TextColor.ANSI.RED, weapon.getTextColor());
        assertEquals(TextColor.ANSI.DEFAULT, weapon.getBackgroundColor());
        assertEquals(SpriteID.ITEM_VAMPIRE_FANGS, weapon.getSpriteID());
    }

    @Test
    public void testEquipWeapon() {
        player.addItem(weapon);
        weapon.useItem(player);
        assertEquals(Player.INITIAL_ATTACK + 16, player.getAttack());
    }

    @Test
    public void testUneqiupWeapon() {
        player.addItem(weapon);
        weapon.useItem(player);
        weapon.useItem(player);
        assertEquals(Player.INITIAL_ATTACK, player.getAttack());
    }

    @Test
    public void testWeaponAndPotion() {
        StrengthPotion potion = new StrengthPotion();
        player.addItem(potion);
        player.addItem(weapon);
        potion.useItem(player);
        weapon.useItem(player);
        assertEquals(Player.INITIAL_ATTACK + StrengthPotion.ATTACK_AMOUNT + 16, player.getAttack());
    }
}
