package ui.sprites;

import java.awt.image.BufferedImage;

public class Sprite {
    private final BufferedImage rawImage;
    private final int offsetX;
    private final int offsetY;
    private final boolean transparent;

    public Sprite(BufferedImage rawImage, int offsetX, int offsetY, boolean transparent) {
        this.rawImage = rawImage;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.transparent = transparent;
    }
}
