package ui;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.screen.Screen;
import model.Game;
import model.Item;

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
        return height - 2;
    }

    // EFFECTS: Renders the Inventory
    public void renderInventory(List<Item> inventory, int from, int to, int selected) {
        // Render header and footer
        for (int x = 0; x < width; x++) {
            drawSprite(x, 0, ' ', TextColor.ANSI.GREEN, TextColor.ANSI.GREEN);
            drawSprite(x, height - 1, ' ', TextColor.ANSI.GREEN, TextColor.ANSI.GREEN);
        }
        drawText(
                1, 0,
                String.format("%d Items In Inventory", inventory.size()),
                TextColor.ANSI.BLACK, TextColor.ANSI.GREEN
        );

        // Render Inventory Items
        for (int i = 0; i < to - from; i++) {
            int index = from + i;
            Item item = inventory.get(index);
            drawSprite(1, i + 1, item.getTextSprite(), item.getTextColor(), item.getBackgroundColor());
            if (i == selected) {
                drawText(3, i + 1, "<" + item.getName() + ">");
            } else {
                drawText(3, i + 1, item.getName());
            }
        }
    }
}
