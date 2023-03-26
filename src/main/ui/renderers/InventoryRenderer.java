package ui.renderers;

import model.Item;
import model.graphics.SpriteID;
import ui.GameWindow;
import ui.helpers.ItemBox;
import ui.helpers.TextureManager;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class InventoryRenderer extends MenuRenderer {
    private static final int HORIZONTAL_ITEMS = 4;
    private static final int VERTICAL_ITEMS = 7;
    private static final int PLAYER_MODEL_SCALE = 12;
    private static final int PADDING_SIZE = 10;
    private static final int ITEM_RENDER_SIZE = 34;

    private List<ItemBox> inventoryItems;

    // Quick hack to get the player model to animate items in the inventory
    int internalTick = 0;

    public InventoryRenderer(GameWindow gameWindow) {
        super(gameWindow);
        inventoryItems = new ArrayList<>();
    }

    @Override
    public void onKeyPress(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_ESCAPE:
            case KeyEvent.VK_E:
                gameWindow.switchRenderer(gameWindow.getGameRenderer(), false);
                break;
        }
    }

    // EFFECTS: Renders the inventory menu
    @Override
    public void paint(Graphics g) {
        // Increment internal tick
        internalTick++;

        // Draw background
        g.drawImage(renderBackground(gameWindow.getGameRenderer()), 0, 0, null);

        // Draw Inventory Text
        // Draw pause menu in the center of the screen
        g.setColor(Color.WHITE);
        g.setFont(textureManager.getFont(32));
        g.drawString("INVENTORY", 16, 48);

        // Initialize Player Inventory Item Preview
        initializeInventoryItems();

        // Render Player Model Preview
        renderPlayerModel(g);

        // Render Inventory Content
        renderInventory(g);
    }

    // EFFECTS: Sets up ItemBox's for each item in player inventory
    private void initializeInventoryItems() {
        final int padding = textureManager.getScaledSize(PADDING_SIZE);
        final int itemRenderSize = textureManager.getScaledSize(ITEM_RENDER_SIZE);
        List<Item> inventory = game.getPlayer().getInventory().getInventoryItems();

        // Add the weapon slot
//        inventoryItems.add(new ItemBox(
//
//        ));

        // Loop through all inventory slots and create an item box
        for (int x = 0; x < HORIZONTAL_ITEMS; x++) {
            for (int y = 0; y < VERTICAL_ITEMS; y++) {
                // Get item. If its out of bounds, item is null
                int index = x * HORIZONTAL_ITEMS + y;
                Item item;
                
                if (index < inventory.size()) {
                    item = inventory.get(index);
                } else {
                    item = null;
                }
                
                inventoryItems.add(new ItemBox(
                        gameWindow.getSizeX() / 2 + padding * 4 + (itemRenderSize + padding) * x,
                        padding * 4 + (itemRenderSize + padding) * y,
                        itemRenderSize,
                        itemRenderSize,
                        item
                ));
            }
        }
    }

    // EFFECTS: Renders Large Version of Player Model
    private void renderPlayerModel(Graphics g) {
        // Get the base player image
        BufferedImage rawPlayerSprite = textureManager.getSprite(SpriteID.PLAYER).getRawImage(internalTick);

        // Calculate new sprite size, and location on screen it should be drawn
        int spriteSize = textureManager.getSpriteSize() * PLAYER_MODEL_SCALE;
        int spriteXOffset = (gameWindow.getWidth() / 4) - (spriteSize / 2);
        int spriteYOffset = (gameWindow.getHeight() / 2) - (spriteSize / 2);

        // Scale up player sprite and draw it
        g.drawImage(
                TextureManager.resize(
                        rawPlayerSprite,
                        spriteSize,
                        spriteSize,
                        Image.SCALE_FAST
                ),
                spriteXOffset, spriteYOffset, null
        );
    }

    // EFFECTS: Renders the inventory content
    // MODIFIES: this
    private void renderInventory(Graphics g) {
        final int padding = textureManager.getScaledSize(PADDING_SIZE);

        // Draw Content Background
        g.setColor(new Color(0.2f, 0.2f, 0.2f));
        g.fillRect(
                gameWindow.getSizeX() / 2 + padding * 2,
                padding * 2,
                gameWindow.getSizeX() / 2 - padding * 4,
                gameWindow.getSizeY() - padding * 4
        );

        // Render all itemboxes
        for (ItemBox ib : inventoryItems) {
            drawItemBox(g, ib);
        }
    }

    // EFFECTS: Draws an itembox on screen
    private void drawItemBox(Graphics g, ItemBox ib) {
        final int itemBoxPadding = textureManager.getScaledSize(2);

        // Draw Outline
        g.setColor(Color.WHITE);
        g.fillRect(
                ib.getPosX(),
                ib.getPosY(),
                ib.getWidth(),
                ib.getHeight()
        );

        g.setColor(Color.BLACK);
        g.fillRect(
                ib.getPosX() + itemBoxPadding,
                ib.getPosY() + itemBoxPadding,
                ib.getWidth() - itemBoxPadding * 2,
                ib.getHeight() - itemBoxPadding * 2
        );
    }
}
