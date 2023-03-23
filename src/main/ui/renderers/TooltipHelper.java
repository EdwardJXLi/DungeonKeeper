package ui.renderers;

import model.Tile;
import ui.sprites.TextureManager;

import java.awt.*;
import java.awt.image.BufferedImage;

public class TooltipHelper {
    private Renderer renderer;
    private TextureManager textureManager;
    
    public TooltipHelper(Renderer renderer, TextureManager textureManager) {
        this.renderer = renderer;
        this.textureManager = textureManager;
    }

    public BufferedImage generateTileTooltip(Tile tile) {
        // Generate Tooltip Size
        double scale = renderer.getGameWindow().getScale();
        int width = (int) (96 * scale);
        int height = (int) (128 * scale);
        int padding = (int) (2 * scale);

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

        // Draw Tile Image
        g.drawImage(
                textureManager.getSprite(tile.getSpriteID()).getImage(),
                padding * 4, padding * 4,
                null
        );

        // Draw Tile Name
        g.setColor(Color.WHITE);

        return tooltip;
    }
}
