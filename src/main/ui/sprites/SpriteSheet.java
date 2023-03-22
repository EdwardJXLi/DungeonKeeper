package ui.sprites;

import java.awt.image.BufferedImage;

public class SpriteSheet {
    private final BufferedImage rawImage;
    private final int spriteSize;

    public SpriteSheet(BufferedImage rawImage, int spriteSize) {
        this.rawImage = rawImage;
        this.spriteSize = spriteSize;
    }

    public BufferedImage getSprite(int x, int y) {
        return rawImage.getSubimage(x * spriteSize, y * spriteSize, spriteSize, spriteSize);
    }
}
