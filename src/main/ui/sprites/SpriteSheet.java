package ui.sprites;

import java.awt.image.BufferedImage;

public class SpriteSheet {
    private final BufferedImage rawImage;

    public SpriteSheet(BufferedImage rawImage) {
        this.rawImage = rawImage;
    }

    public BufferedImage getSprite(int x, int y, int spriteSize) {
        return rawImage.getSubimage(x * spriteSize, y * spriteSize, spriteSize, spriteSize);
    }
}
