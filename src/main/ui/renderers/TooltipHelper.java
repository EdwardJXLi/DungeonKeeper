package ui.renderers;

import model.DroppedItem;
import model.Enemy;
import model.Player;
import model.Tile;
import ui.TextureManager;
import ui.sprites.Sprite;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

public class TooltipHelper {
    private Renderer renderer;
    private TextureManager textureManager;
    
    public TooltipHelper(Renderer renderer, TextureManager textureManager) {
        this.renderer = renderer;
        this.textureManager = textureManager;
    }

    private BufferedImage generateTooltipBackground(int width, int height, int padding) {
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

    public BufferedImage generateDroppedItemTooltip(DroppedItem di) {
        // Generate Tooltip Size
        double scale = renderer.getGameWindow().getScale();
        int width = (int) (144 * scale);
        int height = (int) (86 * scale);
        int padding = (int) (2 * scale);

        // Generate Tooltip Image
        BufferedImage tooltip = generateTooltipBackground(width, height, padding);
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
        drawHelper.drawString("[E] to drop", Color.YELLOW);
        drawHelper.addOffsetX(-(padding * 2));

        // Draw Item Preview
        drawHelper.drawSpriteWithName(
                textureManager.getSprite(di.getSpriteID()),
                di.getItem().getName(),
                16
        );

        return tooltip;
    }

    public BufferedImage generatePlayerTooltip(Player player) {
        // Generate Tooltip Size
        double scale = renderer.getGameWindow().getScale();
        int width = (int) (128 * scale);
        int height = (int) (92 * scale);
        int padding = (int) (2 * scale);

        // Generate Tooltip Image
        BufferedImage tooltip = generateTooltipBackground(width, height, padding);
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

    public BufferedImage generateEnemyTooltip(Enemy enemy) {
        // Generate Tooltip Size
        double scale = renderer.getGameWindow().getScale();
        int width = (int) (128 * scale);
        int height = (int) (72 * scale);
        int padding = (int) (2 * scale);

        // Generate Tooltip Image
        BufferedImage tooltip = generateTooltipBackground(width, height, padding);
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

    public BufferedImage generateTileTooltip(Tile tile) {
        // Generate Tooltip Size
        double scale = renderer.getGameWindow().getScale();
        int width = (int) (128 * scale);
        int height = (int) (64 * scale);
        int padding = (int) (2 * scale);

        // Generate Tooltip Image
        BufferedImage tooltip = generateTooltipBackground(width, height, padding);
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

    private class TooltipRenderHelper {
        private final Graphics2D graphics;
        private int offsetX;
        private int offsetY;
        private final int padding;

        public TooltipRenderHelper(Graphics2D graphics, int offsetX, int offsetY, int padding) {
            this.graphics = graphics;
            this.offsetX = offsetX;
            this.offsetY = offsetY;
            this.padding = padding;
        }

        public void drawSprite(Sprite sprite) {
            graphics.drawImage(
                    sprite.getImage(),
                    offsetX, offsetY,
                    null
            );
            addOffsetY(textureManager.getSpriteSize());
        }

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

        public void drawSpriteWithName(Sprite sprite, String name, int size) {
            drawSpriteWithName(sprite, name, size, Color.WHITE);
        }

        public void drawString(String text, int size, Color color) {
            addOffsetY(textureManager.getFont(size).getSize() / 2);
            graphics.setColor(color);
            graphics.setFont(textureManager.getFont(size));
            graphics.drawString(text, offsetX, offsetY);
            addOffsetY(textureManager.getFont(size).getSize() / 2);
        }

        public void drawString(String text, int size) {
            drawString(text, size, Color.WHITE);
        }

        public void drawString(String text, Color color) {
            drawString(text, 12, color);
        }

        public void drawString(String text) {
            drawString(text, 12, Color.WHITE);
        }

        public int getOffsetX() {
            return offsetX;
        }

        public void addOffsetX(int offsetX) {
            this.offsetX += offsetX;
        }

        public void setOffsetX(int offsetX) {
            this.offsetX = offsetX;
        }

        public int getOffsetY() {
            return offsetY;
        }

        public void setOffsetY(int offsetY) {
            this.offsetY = offsetY;
        }

        public void addOffsetY(int offsetY) {
            this.offsetY += offsetY;
        }
    }
}
