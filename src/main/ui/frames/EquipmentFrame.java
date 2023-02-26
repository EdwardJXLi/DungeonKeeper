package ui.frames;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.screen.Screen;
import model.Game;
import model.Player;
import model.items.Armor;
import model.items.Weapon;
import ui.Frame;

public class EquipmentFrame extends Frame {
    private static final int ARMOR_BOX_OFFSET = 6;

    // EFFECTS: Initializes an inventory frame that shows inventory contents
    public EquipmentFrame(
            int leftBound, int topBound, int rightBound, int bottomBound,
            Screen screen, Game game) {
        super(leftBound, topBound, rightBound, bottomBound, "Equipment", screen, game);
    }

    // EFFECTS: Renders the Equipment
    public void renderEquipment() {
        // Get player equipment
        Player player = getGame().getPlayer();
        Armor armor = player.getArmor();
        Weapon weapon = player.getWeapon();

        // Print information about Armor
        drawText(2, 0, "[Equipped Armor]", TextColor.ANSI.MAGENTA, TextColor.ANSI.DEFAULT);

        // Check if armor is equipped
        if (armor != null) {
            // Render armor & description
            drawSprite(
                    3, 1,
                    armor.getTextSprite(), armor.getTextColor(), armor.getBackgroundColor()
            );
            drawText(
                    5, 1,
                    armor.getName(), TextColor.ANSI.GREEN, TextColor.ANSI.DEFAULT);

            if (armor.getDescription() != null) {
                // Render tile information
                for (int i = 0; i < armor.getDescription().size(); i++) {
                    drawText(2, 2 + i, armor.getDescription().get(i));
                }
            }
        } else {
            drawText(3, 1, "No Armor Equipped");
        }

        // Print information about Weapon
        drawText(2, ARMOR_BOX_OFFSET + 0, "[Equipped Weapon]", TextColor.ANSI.MAGENTA, TextColor.ANSI.DEFAULT);

        // Check if weapon is equipped
        if (weapon != null) {
            // Render weapon & description
            drawSprite(
                    3, ARMOR_BOX_OFFSET + 1,
                    weapon.getTextSprite(), weapon.getTextColor(), weapon.getBackgroundColor()
            );
            drawText(
                    5, ARMOR_BOX_OFFSET + 1,
                    weapon.getName(), TextColor.ANSI.GREEN, TextColor.ANSI.DEFAULT);

            if (weapon.getDescription() != null) {
                // Render tile information
                for (int i = 0; i < weapon.getDescription().size(); i++) {
                    drawText(2, ARMOR_BOX_OFFSET + 2 + i, weapon.getDescription().get(i));
                }
            }
        } else {
            drawText(3, ARMOR_BOX_OFFSET + 1, "No Weapon Equipped");
        }

    }
}
