package ui.sprites;

import ui.TextureManager;

import java.awt.image.BufferedImage;

public interface Sprite {
    public BufferedImage getImage();

    public BufferedImage getImage(int value);

    public boolean isTransparent();

    public void initialize(TextureManager textureManager);
}
