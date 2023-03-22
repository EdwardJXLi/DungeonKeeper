package ui.sprites;

import java.awt.image.BufferedImage;

public class Sprite {
    private final BufferedImage rawImage;

    private final SpriteSheet spriteSheet;
    private final int offsetX;
    private final int offsetY;
    private final boolean transparent;

    public Sprite(SpriteSheet spriteSheet, int offsetX, int offsetY, boolean transparent) {
        this.spriteSheet = spriteSheet;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.transparent = transparent;

        // Get the sprite from the spritesheet
        this.rawImage = spriteSheet.getSprite(offsetX, offsetY);
    }
}
