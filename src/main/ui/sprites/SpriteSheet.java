package ui.sprites;

import java.awt.image.BufferedImage;

/*
 * Main class for spritesheets, single images with potentially multiple sprites embedded within
 */

public class SpriteSheet {
    private final BufferedImage rawImage;

    // EFFECTS: Creates a new spritesheet from the given image
    public SpriteSheet(BufferedImage rawImage) {
        this.rawImage = rawImage;
    }

    // EFFECTS: Returns a sprite from the spritesheet at the given X Y coordinates
    // NOTE: The X Y coordinates are in terms of sprites, not pixels
    public BufferedImage getSprite(int spriteX, int spriteY, int spriteSize) {
        return rawImage.getSubimage(spriteX * spriteSize, spriteY * spriteSize, spriteSize, spriteSize);
    }
}
