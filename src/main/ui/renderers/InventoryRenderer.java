package ui.renderers;

import model.Item;
import model.graphics.SpriteID;
import ui.GameWindow;
import ui.helpers.ItemBox;
import ui.helpers.TextureManager;
import ui.helpers.TooltipHelper;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class InventoryRenderer extends MenuRenderer {
    private static final int HORIZONTAL_ITEMS = 4;
    private static final int VERTICAL_ITEMS = 7;
    private static final int PLAYER_MODEL_SCALE = 12;
    private static final int PADDING_SIZE = 10;
    private static final int ITEM_RENDER_SIZE = 34;

    private BufferedImage background;
    private List<ItemBox> inventoryItems;
    private final TooltipHelper tooltipHelper;

    // Quick hack to get the player model to animate items in the inventory
    int internalTick = 0;

    public InventoryRenderer(GameWindow gameWindow) {
        super(gameWindow);
        background = null;
        tooltipHelper = new TooltipHelper(this, textureManager);
    }

    // EFFECTS: Sets up the inventory variables when a user opens the inventory
    // MODIFIES: this
    @Override
    public void initRenderer() {
        background = renderBackground(gameWindow.getGameRenderer());

        // Initialize Player Inventory Item Preview
        initializeInventoryItems();
    }

    // EFFECTS: Renders the inventory menu
    @Override
    public void paint(Graphics g) {
        // Increment internal tick
        internalTick++;

        // Draw background
        g.drawImage(background, 0, 0, null);

        // Draw Inventory Text
        // Draw pause menu in the center of the screen
        g.setColor(Color.WHITE);
        g.setFont(textureManager.getFont(32));
        g.drawString("INVENTORY", 16, 48);

        // Render Player Model Preview
        renderPlayerModel(g);

        // Render Inventory Content
        renderInventory(g);

        // Renders Game Messages
        renderGameMessages(g);

        // Render Tooltips
        renderTooltips(g);
    }

    // EFFECTS: Sets up ItemBox's for each item in player inventory
    private void initializeInventoryItems() {
        inventoryItems = new ArrayList<>();
        final int padding = textureManager.getScaledSize(PADDING_SIZE);
        final int itemRenderSize = textureManager.getScaledSize(ITEM_RENDER_SIZE);
        List<Item> inventory = game.getPlayer().getInventory().getInventoryItems();

        // Initialize Misc Items
        initializeMiscItems();

        // Loop through all inventory slots and create an item box
        for (int x = 0; x < HORIZONTAL_ITEMS; x++) {
            for (int y = 0; y < VERTICAL_ITEMS; y++) {
                // Get item. If its out of bounds, item is null
                int index = y * HORIZONTAL_ITEMS + x;
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

    // EFFECTS: Sets up ItemBox's for misc items such as weapons and armors
    private void initializeMiscItems() {
        final int padding = textureManager.getScaledSize(PADDING_SIZE);
        final int itemRenderSize = textureManager.getScaledSize(ITEM_RENDER_SIZE);

        // Add the weapon slot
        inventoryItems.add(new ItemBox(
                gameWindow.getSizeX() / 2 - padding * 2 - itemRenderSize,
                gameWindow.getSizeY() / 2 - padding - itemRenderSize,
                itemRenderSize,
                itemRenderSize,
                game.getPlayer().getWeapon()
        ));

        // Add the armor slot
        inventoryItems.add(new ItemBox(
                gameWindow.getSizeX() / 2 - padding * 2 - itemRenderSize,
                gameWindow.getSizeY() / 2 + padding,
                itemRenderSize,
                itemRenderSize,
                game.getPlayer().getArmor()
        ));
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
        final int itemBoxSize = textureManager.getScaledSize(ITEM_RENDER_SIZE);
        final int itemSize = (int) (itemBoxSize  * (0.75));

        // Draw Outline
        g.setColor(Color.WHITE);
        g.fillRect(ib.getPosX(), ib.getPosY(), ib.getWidth(), ib.getHeight());

        g.setColor(Color.BLACK);
        g.fillRect(
                ib.getPosX() + itemBoxPadding, ib.getPosY() + itemBoxPadding,
                ib.getWidth() - itemBoxPadding * 2, ib.getHeight() - itemBoxPadding * 2
        );

        // If there is an item, draw it!
        if (ib.getItem() != null) {
            BufferedImage itemPreview = TextureManager.resize(
                    textureManager.getSprite(ib.getItem().getSpriteID()).getRawImage(internalTick),
                    itemSize, itemSize, Image.SCALE_FAST
            );
            g.drawImage(
                    itemPreview,
                    ib.getPosX() + itemBoxSize / 2 - itemSize / 2,
                    ib.getPosY() + itemBoxSize / 2 - itemSize / 2,
                    null
            );
        }
    }

    // EFFECTS: Gets ItemBox at the given screen location. Null otherwise.
    private ItemBox getItemBoxAtScreenLocation(int posX, int posY) {
        // Check each item in InventoryItem.
        // If mouse is in the correct range, return said itembox
        for (ItemBox ib : inventoryItems) {
            // Check if mouse is within item area
            if (ib.getItem() != null) {
                if (ib.getPosX() < posX && ib.getPosX() + ib.getHeight() > posX) {
                    if (ib.getPosY() < posY && ib.getPosY() + ib.getHeight() > posY) {
                        return ib;
                    }
                }
            }
        }

        // User is not hovering an itembox. Return
        return null;
    }

    // EFFECTS: Renders Game Messages
    private void renderGameMessages(Graphics g) {
        // Setup Fonts
        g.setFont(textureManager.getFont(12));

        // Get Game Messages
        List<String> gameMessages = game.getLastMessages(5);

        // Renders game messages on bottom left corner of screen, backwards
        for (int i = 0; i < gameMessages.size(); i++) {
            // Render with slight gradient as messages go on
            g.setColor(new Color(
                    255, 200, 255,
                    250 - (int) (200 * ((float) i / (float) gameMessages.size()))
            ));
            g.drawString(
                    gameMessages.get(gameMessages.size() - i - 1),
                    10, gameWindow.getHeight() - 50 - (i * textureManager.getFont(12).getSize())
            );
        }
    }

    // EFFECTS: Renders item information when player hovers in inventory
    private void renderTooltips(Graphics g) {
        final int tooltipOffset = 15;
        if (mouseInFrame) {
            // Get hovering itembox
            ItemBox hoveringItemBox = getItemBoxAtScreenLocation(mouseX, mouseY);

            // If user is hovering over an item, draw the tooltip
            if (hoveringItemBox != null) {
                g.drawImage(
                        tooltipHelper.generateItemTooltip(hoveringItemBox.getItem()),
                        mouseX + tooltipOffset, mouseY + tooltipOffset, null
                );
            }
        }
    }

    @Override
    public void onMouseClick(MouseEvent e) {
        // Get clicked item
        ItemBox clickedItem = getItemBoxAtScreenLocation(e.getX(), e.getY());

        // Check if the item is actually at mouse location
        if (clickedItem != null && clickedItem.getItem() != null) {
            // Use the item
            clickedItem.getItem().useItem(game.getPlayer());
            // Re-init inventory items
            initializeInventoryItems();
        }
    }

    @Override
    public void onKeyPress(KeyEvent e) {
        // Get hovering item
        ItemBox clickedItem = getItemBoxAtScreenLocation(mouseX, mouseY);

        switch (e.getKeyCode()) {
            case KeyEvent.VK_Q:
                // Check if the item is actually at mouse location
                if (clickedItem != null && clickedItem.getItem() != null) {
                    // Drop Item
                    game.getPlayer().dropItem(clickedItem.getItem());
                    // Re-init inventory items
                    initializeInventoryItems();
                }
                break;
            case KeyEvent.VK_X:
                // Check if the item is actually at mouse location
                if (clickedItem != null && clickedItem.getItem() != null) {
                    // Delete item from inventory
                    game.getPlayer().removeItem(clickedItem.getItem());
                    // Re-init inventory items
                    initializeInventoryItems();
                }
                break;
            case KeyEvent.VK_ESCAPE:
            case KeyEvent.VK_E:
                gameWindow.switchRenderer(gameWindow.getGameRenderer(), false);
                break;
        }
    }
}
