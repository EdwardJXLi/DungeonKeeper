package ui.sprites;

import ui.helpers.TextureManager;

import java.awt.image.BufferedImage;

/*
 * Interface for all graphical sprites used in the game.
 */

public interface Sprite {
    BufferedImage getImage();

    BufferedImage getImage(int value);

    BufferedImage getRawImage();

    BufferedImage getRawImage(int value);

    boolean isTransparent();

    void initialize(TextureManager textureManager);
}
