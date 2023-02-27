package ui.frames;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.screen.Screen;
import model.Game;
import model.Item;
import ui.Frame;

import java.util.List;

/*
 * text-based ui frame to render the item previews in the inventory
 */

public class InvPreviewFrame extends Frame {
    // EFFECTS: Initializes an inventory frame that shows inventory contents
    public InvPreviewFrame(
            int leftBound, int topBound, int rightBound, int bottomBound,
            Screen screen, Game game) {
        super(leftBound, topBound, rightBound, bottomBound, "Item Preview", screen, game);
    }

    // EFFECTS: Renders the Preview
    public void renderPreview(List<Item> inventory, int selection) {
        if (inventory.size() > 0) {
            Item item = inventory.get(selection);
            // Render item & description
            drawSprite(
                    3, 0,
                    item.getTextSprite(), item.getTextColor(), item.getBackgroundColor()
            );
            drawText(
                    5, 0,
                    item.getName(), TextColor.ANSI.GREEN, TextColor.ANSI.DEFAULT);

            if (item.getDescription() != null) {
                // Render tile information
                for (int i = 0; i < item.getDescription().size(); i++) {
                    drawText(2, 1 + i, item.getDescription().get(i));
                }
            }
        }
    }
}
