package ui.sprites;

import java.awt.image.BufferedImage;
import java.util.List;

public class AnimatedSprite extends SpriteCollection {
    int frameTime;

    public AnimatedSprite(List<Sprite> sprites, int frameTime) {
        super(sprites);
        this.frameTime = frameTime;
    }

    @Override
    public BufferedImage getImage(int value) {
        return sprites.get((value / frameTime) % sprites.size()).getImage();
    }

    @Override
    public BufferedImage getRawImage(int value) {
        return sprites.get((value / frameTime) % sprites.size()).getRawImage();
    }
}
