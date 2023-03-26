package ui.sprites;

import ui.helpers.TextureManager;

import java.awt.image.BufferedImage;

public interface Sprite {
    public BufferedImage getImage();

    public BufferedImage getImage(int value);

    public BufferedImage getRawImage();

    public BufferedImage getRawImage(int value);

    public boolean isTransparent();

    public void initialize(TextureManager textureManager);
}
