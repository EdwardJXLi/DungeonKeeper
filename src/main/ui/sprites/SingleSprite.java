package ui.sprites;

import ui.helpers.TextureManager;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SingleSprite implements Sprite {
    private final BufferedImage rawImage;

    private BufferedImage image;
    private final boolean transparent;

    public SingleSprite(BufferedImage rawImage, boolean transparent) {
        this.rawImage = rawImage;
        this.transparent = transparent;
    }

    @Override
    public BufferedImage getImage() {
        return image;
    }

    @Override
    public BufferedImage getImage(int value) {
        // Alias for getImage
        return getImage();
    }

    @Override
    public BufferedImage getRawImage() {
        return rawImage;
    }

    @Override
    public BufferedImage getRawImage(int value) {
        // Alias for getRawImage
        return getRawImage();
    }

    @Override
    public boolean isTransparent() {
        return transparent;
    }

    @Override
    public void initialize(TextureManager textureManager) {
        System.out.println("Initializing sprite: " + this);
        this.image = TextureManager.resize(
                rawImage,
                textureManager.getSpriteSize(),
                textureManager.getSpriteSize(),
                Image.SCALE_FAST
        );
    }
}
