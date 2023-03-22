package ui.sprites;

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
    public BufferedImage getSprite(int value) {
        // Alias for getImage
        return getImage();
    }

    @Override
    public boolean isTransparent() {
        return transparent;
    }

    @Override
    public void initialize(int size, int scale) {
        System.out.println("Initializing sprite: " + this);
        this.image = SpriteManager.resize(rawImage, size * scale, size * scale);
    }
}
