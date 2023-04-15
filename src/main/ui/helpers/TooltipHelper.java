package ui.helpers;

import model.*;
import ui.renderers.Renderer;
import ui.sprites.Sprite;

import java.awt.*;
import java.awt.image.BufferedImage;

/*
 * Helper class for generating tooltips. Used by the UI to generate tooltips for items, entities, etc.
 */

public class TooltipHelper {
    private final Renderer renderer;
    private final TextureManager textureManager;
    private final int padding;

    // EFFECTS: Creates a new TooltipHelper
    public TooltipHelper(Renderer renderer, TextureManager textureManager) {
        this.renderer = renderer;
        this.textureManager = textureManager;

        // Calculate Padding
        this.padding = textureManager.getScaledSize(2);
    }

    // EFFECTS: Helper method for generating the default tooltip background
    private BufferedImage generateTooltipBackground(int width, int height) {
        // Generate Tooltip Image
        BufferedImage tooltip = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = tooltip.createGraphics();

        // Draw Border
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);
        g.setColor(Color.BLACK);
        g.fillRect(
                padding, padding,
                width - (padding * 2), height - (padding * 2));

        return tooltip;
    }

    // EFFECTS: Generates a tooltip for the given item
    public BufferedImage generateItemTooltip(Item item) {
        // Generate Tooltip Size
        int width = textureManager.getScaledSize(128);
        int height = textureManager.getScaledSize(100);

        // Generate Tooltip Image
        BufferedImage tooltip = generateTooltipBackground(width, height);
        Graphics2D g = tooltip.createGraphics();

        // Create Render Helper
        TooltipRenderHelper drawHelper = new TooltipRenderHelper(
                g, padding * 4, padding * 4, padding
        );

        // Draw Tile Image
        drawHelper.drawSpriteWithName(
                textureManager.getSprite(item.getSpriteID()),
                item.getName(),
                16
        );

        // Draw Tile Description
        for (String line : item.getDescription()) {
            drawHelper.drawString(line);
        }

        // Draw Item Instructions
        drawHelper.drawString("Click to use/equip", Color.GREEN);
        drawHelper.drawString("[Q] to drop item", Color.GREEN);
        drawHelper.drawString("[X] to delete item", Color.GREEN);

        return tooltip;
    }

    // EFFECTS: Generates a tooltip for the given dropped item
    public BufferedImage generateDroppedItemTooltip(DroppedItem di) {
        // Generate Tooltip Size
        int width = textureManager.getScaledSize(144);
        int height = textureManager.getScaledSize(86);

        // Generate Tooltip Image
        BufferedImage tooltip = generateTooltipBackground(width, height);
        Graphics2D g = tooltip.createGraphics();

        // Create Render Helper
        TooltipRenderHelper drawHelper = new TooltipRenderHelper(
                g, padding * 4, padding * 4, padding
        );

        // Draw Instructions
        drawHelper.drawString("[DROPPED ITEMS]", 16, Color.GREEN);
        drawHelper.drawString("Stand on item and press:");
        drawHelper.addOffsetX(padding * 2);
        drawHelper.drawString("[Q] to pick up", Color.YELLOW);
        drawHelper.drawString("[X] to delete", Color.YELLOW);
        drawHelper.addOffsetX(-(padding * 2));

        // Draw Item Preview
        drawHelper.drawSpriteWithName(
                textureManager.getSprite(di.getSpriteID()),
                di.getItem().getName(),
                16
        );

        return tooltip;
    }

    // EFFECTS: Generates a tooltip for the given player
    public BufferedImage generatePlayerTooltip(Player player) {
        // Generate Tooltip Size
        int width = textureManager.getScaledSize(128);
        int height = textureManager.getScaledSize(92);

        // Generate Tooltip Image
        BufferedImage tooltip = generateTooltipBackground(width, height);
        Graphics2D g = tooltip.createGraphics();

        // Create Render Helper
        TooltipRenderHelper drawHelper = new TooltipRenderHelper(
                g, padding * 4, padding * 4, padding
        );

        // Draw Player Image
        drawHelper.drawSpriteWithName(
                textureManager.getSprite(player.getSpriteID()),
                player.getName(),
                16
        );

        // Draw Player Stats
        drawHelper.drawString("Health: " + player.getHealth() + "/" + player.getMaxHealth(), Color.RED);
        drawHelper.drawString("Attack: " + player.getAttack(), Color.ORANGE);
        drawHelper.drawString("Defense: " + player.getDefense(), Color.BLUE);
        drawHelper.drawString("Kill Count: " + player.getKills(), Color.GREEN);
        drawHelper.drawString("Inventory Items: " + player.getInventory().numItems());

        return tooltip;
    }

    // EFFECTS: Generates a tooltip for the given enemy
    public BufferedImage generateEnemyTooltip(Enemy enemy) {
        // Generate Tooltip Size
        int width = textureManager.getScaledSize(128);
        int height = textureManager.getScaledSize(72);

        // Generate Tooltip Image
        BufferedImage tooltip = generateTooltipBackground(width, height);
        Graphics2D g = tooltip.createGraphics();

        // Create Render Helper
        TooltipRenderHelper drawHelper = new TooltipRenderHelper(
                g, padding * 4, padding * 4, padding
        );

        // Draw Player Image
        drawHelper.drawSpriteWithName(
                textureManager.getSprite(enemy.getSpriteID()),
                enemy.getName(),
                16
        );

        // Draw Player Stats
        drawHelper.drawString("Health: " + enemy.getHealth() + "/" + enemy.getMaxHealth(), Color.RED);
        drawHelper.drawString("Attack: " + enemy.getAttack(), Color.ORANGE);
        drawHelper.drawString("Defense: " + enemy.getDefense(), Color.BLUE);

        return tooltip;
    }

    // EFFECTS: Generates a tooltip for the given tile
    public BufferedImage generateTileTooltip(Tile tile) {
        // Generate Tooltip Size
        int width = textureManager.getScaledSize(128);
        int height = textureManager.getScaledSize(72);

        // Generate Tooltip Image
        BufferedImage tooltip = generateTooltipBackground(width, height);
        Graphics2D g = tooltip.createGraphics();

        // Create Render Helper
        TooltipRenderHelper drawHelper = new TooltipRenderHelper(
                g, padding * 4, padding * 4, padding
        );

        // Draw Tile Image
        drawHelper.drawSpriteWithName(
                textureManager.getSprite(tile.getSpriteID()),
                tile.getName(),
                16
        );

        // Draw Tile Description
        for (String line : tile.getDescription()) {
            drawHelper.drawString(line);
        }
        
        return tooltip;
    }

    /*
     * Private internal-use only class for helping with spacing and rendering for tooltip text.
     * This is highly specific to the tooltip rendering and should not be used for anything else.
     */
    private class TooltipRenderHelper {
        // Tooltip Constants
        // This should be static, but that is not supported in jdk8 :skull:
        private final Color defaultColor = Color.WHITE;
        private final int defaultSize = 12;

        // Tooltip Parameters
        private final Graphics2D graphics;
        private int offsetX;
        private int offsetY;
        private final int padding;

        // EFFECTS: Creates a new TooltipRenderHelper with the given parameters
        public TooltipRenderHelper(Graphics2D graphics, int offsetX, int offsetY, int padding) {
            this.graphics = graphics;
            this.offsetX = offsetX;
            this.offsetY = offsetY;
            this.padding = padding;
        }

        // MODIFIES: this
        // EFFECTS: Draws a sprite on the next line of the tooltip
        public void drawSprite(Sprite sprite) {
            graphics.drawImage(
                    sprite.getImage(),
                    offsetX, offsetY,
                    null
            );
            addOffsetY(textureManager.getSpriteSize());
        }

        // MODIFIES: this
        // EFFECTS: Draws a sprite on the next line of the tooltip alongside the given name
        public void drawSpriteWithName(Sprite sprite, String name, int size, Color color) {
            graphics.drawImage(
                    sprite.getImage(),
                    offsetX, offsetY,
                    null
            );
            addOffsetY(textureManager.getSpriteSize() / 2 + padding * 2);
            graphics.setColor(color);
            graphics.setFont(textureManager.getFont(16));
            graphics.drawString(
                    name,
                    offsetX + textureManager.getSpriteSize() + padding,
                    offsetY
            );
            addOffsetY(textureManager.getFont(size).getSize() / 2);
        }

        // MODIFIES: this
        // EFFECTS: Draws a sprite on the next line of the tooltip alongside the given name
        //          with default color
        public void drawSpriteWithName(Sprite sprite, String name, int size) {
            drawSpriteWithName(sprite, name, size, defaultColor);
        }

        // MODIFIES: this
        // EFFECTS: Draws a string on the next line of the tooltip
        public void drawString(String text, int size, Color color) {
            addOffsetY(textureManager.getFont(size).getSize() / 2);
            graphics.setColor(color);
            graphics.setFont(textureManager.getFont(size));
            graphics.drawString(text, offsetX, offsetY);
            addOffsetY(textureManager.getFont(size).getSize() / 2);
        }

        // MODIFIES: this
        // EFFECTS: Draws a string on the next line of the tooltip with default color
        public void drawString(String text, int size) {
            drawString(text, size, defaultColor);
        }

        // MODIFIES: this
        // EFFECTS: Draws a string on the next line of the tooltip with default size
        public void drawString(String text, Color color) {
            drawString(text, defaultSize, color);
        }

        // MODIFIES: this
        // EFFECTS: Draws a string on the next line of the tooltip with default size and color
        public void drawString(String text) {
            drawString(text, defaultSize, defaultColor);
        }

        // EFFECTS: Returns the current x offset of text rendering
        public int getOffsetX() {
            return offsetX;
        }

        // MODIFIES: this
        // EFFECTS: Sets the current x offset of text rendering
        public void addOffsetX(int offsetX) {
            this.offsetX += offsetX;
        }

        // MODIFIES: this
        // EFFECTS: Sets the current x offset of text rendering
        public void setOffsetX(int offsetX) {
            this.offsetX = offsetX;
        }

        // EFFECTS: Returns the current y offset of text rendering
        public int getOffsetY() {
            return offsetY;
        }

        // MODIFIES: this
        // EFFECTS: Sets the current y offset of text rendering
        public void setOffsetY(int offsetY) {
            this.offsetY = offsetY;
        }

        // MODIFIES: this
        // EFFECTS: Adds to the current y offset of text rendering
        public void addOffsetY(int offsetY) {
            this.offsetY += offsetY;
        }
    }
}
