package ui.frames;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.screen.Screen;
import model.Game;
import model.Item;
import ui.Frame;

import java.util.List;

public class InventoryFrame extends Frame {
    // EFFECTS: Initializes an inventory frame that shows inventory contents
    public InventoryFrame(
            int leftBound, int topBound, int rightBound, int bottomBound,
            Screen screen, Game game) {
        super(leftBound, topBound, rightBound, bottomBound, "Inventory", screen, game);
    }

    // EFFECTS: Returns the numbers of items that fit within a page
    public int maxItemsInPage() {
        return getHeight() - 2;
    }

    // EFFECTS: Renders the Inventory
    public void renderInventory(List<Item> inventory, int from, int to, int selected) {
        // Render header and footer
        for (int x = 0; x < getWidth(); x++) {
            drawSprite(x, 0, ' ', TextColor.ANSI.GREEN, TextColor.ANSI.GREEN);
            drawSprite(x, getHeight() - 1, ' ', TextColor.ANSI.GREEN, TextColor.ANSI.GREEN);
        }
        drawText(
                1, 0,
                String.format("%d Items In Inventory", inventory.size()),
                TextColor.ANSI.BLACK, TextColor.ANSI.GREEN
        );
        drawText(
                1, getHeight() - 1,
                String.format("f: %d, t: %d, s: %d", from, to, selected),
                TextColor.ANSI.BLACK, TextColor.ANSI.GREEN
        );

        renderInventoryItems(inventory, from, to, selected);
    }

    // EFFECTS: Renders Inventory Items
    private void renderInventoryItems(List<Item> inventory, int from, int to, int selected) {
        int pos = 0;

        // Render Inventory Items
        for (int i = from; i < to; i++) {
            pos++;
            Item item = inventory.get(i);
            drawSprite(1, pos, item.getTextSprite(), item.getTextColor(), item.getBackgroundColor());
            if (i == selected) {
                drawText(3, pos, "<" + item.getName() + ">");
            } else {
                drawText(3, pos, item.getName());
            }
        }
    }
}
