package ui.sprites;

import java.awt.image.BufferedImage;
import java.util.List;

public abstract class SpriteCollection implements Sprite {
    private final List<Sprite> sprites;

    public SpriteCollection(List<Sprite> sprites) {
        this.sprites = sprites;
    }

    @Override
    public BufferedImage getImage() {
        return sprites.get(0).getImage();
    }

    @Override
    public BufferedImage getSprite(int value) {
        return sprites.get(value % sprites.size()).getImage();
    }

    @Override
    public boolean isTransparent() {
        return sprites.get(0).isTransparent();
    }

    @Override
    public void initialize(int size, int scale) {
        for (Sprite sprite : sprites) {
            sprite.initialize(size, scale);
        }
    }

    public List<Sprite> getSprites() {
        return sprites;
    }
}
