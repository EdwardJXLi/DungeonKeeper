package ui.sprites;

import ui.helpers.TextureManager;

import java.awt.image.BufferedImage;
import java.util.List;

/*
 * Abstract class for a sprite that is a collection of other sprites.
 * This is used for sprites that have multiple states, such as animated sprites.
 */

public abstract class SpriteCollection implements Sprite {
    protected final List<Sprite> sprites;

    // REQUIRES: sprites is not empty
    // EFFECTS: Creates a new sprite collection with the given sprites
    public SpriteCollection(List<Sprite> sprites) {
        this.sprites = sprites;
    }

    // EFFECTS: Returns the first sprite in the collection
    @Override
    public BufferedImage getImage() {
        return sprites.get(0).getImage();
    }

    // REQUIRES: value >= 0
    // EFFECTS: Returns the sprite at the given index in the collection
    @Override
    public BufferedImage getImage(int value) {
        return sprites.get(value % sprites.size()).getImage();
    }

    // EFFECTS: Returns the first raw sprite in the collection
    @Override
    public BufferedImage getRawImage() {
        return sprites.get(0).getRawImage();
    }

    // REQUIRES: value >= 0
    // EFFECTS: Returns the raw sprite at the given index in the collection
    @Override
    public BufferedImage getRawImage(int value) {
        return sprites.get(value % sprites.size()).getRawImage();
    }

    // EFFECTS: Returns true if the first sprite in the collection is transparent
    //          Assuming that if one of the sprites is transparent, most of them are.
    @Override
    public boolean isTransparent() {
        return sprites.get(0).isTransparent();
    }

    // MODIFIES: this
    // EFFECTS: Initializes all sprites in the collection. This should be called after the texture manager is loaded.
    @Override
    public void initialize(TextureManager textureManager) {
        for (Sprite sprite : sprites) {
            sprite.initialize(textureManager);
        }
    }

    // EFFECTS: Returns the list of sprites in the collection
    public List<Sprite> getSprites() {
        return sprites;
    }
}
