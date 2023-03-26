package ui.sprites;

import ui.helpers.TextureManager;

import java.awt.image.BufferedImage;
import java.util.List;

public abstract class SpriteCollection implements Sprite {
    protected final List<Sprite> sprites;

    public SpriteCollection(List<Sprite> sprites) {
        this.sprites = sprites;
    }

    @Override
    public BufferedImage getImage() {
        return sprites.get(0).getImage();
    }

    @Override
    public BufferedImage getImage(int value) {
        return sprites.get(value % sprites.size()).getImage();
    }

    @Override
    public BufferedImage getRawImage() {
        return sprites.get(0).getRawImage();
    }

    @Override
    public BufferedImage getRawImage(int value) {
        return sprites.get(value % sprites.size()).getRawImage();
    }

    @Override
    public boolean isTransparent() {
        return sprites.get(0).isTransparent();
    }

    @Override
    public void initialize(TextureManager textureManager) {
        for (Sprite sprite : sprites) {
            sprite.initialize(textureManager);
        }
    }

    public List<Sprite> getSprites() {
        return sprites;
    }
}
