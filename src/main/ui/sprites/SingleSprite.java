package ui.sprites;

import ui.helpers.TextureManager;

import java.awt.*;
import java.awt.image.BufferedImage;

/*
 * Main class for sprites, single images with no embedded sprites
 */

public class SingleSprite implements Sprite {
    private final BufferedImage rawImage;

    // Sprite Information
    private BufferedImage image;
    private final boolean transparent;

    // EFFECTS: Creates a new sprite from the given image
    public SingleSprite(BufferedImage rawImage, boolean transparent) {
        this.rawImage = rawImage;
        this.transparent = transparent;
    }

    // EFFECTS: Returns the sprite image
    @Override
    public BufferedImage getImage() {
        return image;
    }

    // REQUIRES: value >= 0
    // EFFECTS: Returns the sprite image. Since there is only one sprite, the value makes no difference
    @Override
    public BufferedImage getImage(int value) {
        // Alias for getImage
        return getImage();
    }

    // EFFECTS: Returns the raw sprite image
    @Override
    public BufferedImage getRawImage() {
        return rawImage;
    }

    // REQUIRES: value >= 0
    // EFFECTS: Returns the raw sprite image. Since there is only one sprite, the value makes no difference
    @Override
    public BufferedImage getRawImage(int value) {
        // Alias for getRawImage
        return getRawImage();
    }

    // EFFECTS: Returns true if the sprite is transparent
    @Override
    public boolean isTransparent() {
        return transparent;
    }

    // MODIFIES: this
    // EFFECTS: Initializes the sprite by resizing the image and saving it to memory.
    //          This is an optimization step so that the game does not need to rescale the images every frame
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
