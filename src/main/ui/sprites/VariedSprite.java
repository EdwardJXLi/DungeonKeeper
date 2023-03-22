package ui.sprites;

import java.awt.image.BufferedImage;
import java.util.List;

public class VariedSprite extends SpriteCollection {
    public VariedSprite(List<Sprite> sprites) {
        super(sprites);
    }

    @Override
    public BufferedImage getImage(int value) {
        return sprites.get(0).getImage();
    }
}
