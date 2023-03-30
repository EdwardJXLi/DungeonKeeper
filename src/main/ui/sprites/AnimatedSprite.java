package ui.sprites;

import java.awt.image.BufferedImage;
import java.util.List;

/*
 * Main class for animated sprites. These have multiple potential subsprites that change
 * depending on the current game tick.
 */

public class AnimatedSprite extends SpriteCollection {
    // The number of ticks between each frame
    int frameTime;

    // REQUIRES: sprites is not empty
    // EFFECTS: Creates a new animated sprite with the given sprites and frame time
    public AnimatedSprite(List<Sprite> sprites, int frameTime) {
        super(sprites);
        this.frameTime = frameTime;
    }

    // REQUIRES: value >= 0
    // EFFECTS: Returns the sprite at the given at the current game tick.
    //          The sprite is determined by the current game tick divided by the frame time.
    @Override
    public BufferedImage getImage(int value) {
        return sprites.get((value / frameTime) % sprites.size()).getImage();
    }

    // REQUIRES: value >= 0
    // EFFECTS: Returns the raw sprite at the given at the current game tick.
    //          The sprite is determined by the current game tick divided by the frame time.
    @Override
    public BufferedImage getRawImage(int value) {
        return sprites.get((value / frameTime) % sprites.size()).getRawImage();
    }
}
