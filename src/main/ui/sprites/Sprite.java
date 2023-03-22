package ui.sprites;

import java.awt.image.BufferedImage;

public interface Sprite {
    public BufferedImage getImage();

    public BufferedImage getImage(int value);

    public boolean isTransparent();

    public void initialize(int size, int scale);
}
