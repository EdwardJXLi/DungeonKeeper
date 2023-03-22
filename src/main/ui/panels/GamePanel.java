package ui.panels;

import model.graphics.SpriteID;
import ui.GraphicalGame;
import ui.sprites.Sprite;
import ui.sprites.SpriteManager;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

public class GamePanel extends JPanel {

    private BufferedImage image;
    SpriteManager spriteManager;

    public GamePanel(SpriteManager spriteManager) {
        super();
        this.spriteManager = spriteManager;
    }

    // TODO: Hacky and Temporary Draw Function!
    public void drawSprite(Graphics g, Sprite sprite, int offsetX, int offsetY) {
        g.drawImage(
                sprite.getImage(),
                offsetX * GraphicalGame.SPRITE_SIZE * GraphicalGame.SCALE,
                offsetY * GraphicalGame.SPRITE_SIZE * GraphicalGame.SCALE,
                null
        );
    }

    @Override
    public void paint(Graphics g) {
        // TODO: Test Draw!
        g.setColor(Color.GRAY);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(Color.WHITE);
        g.setFont(new Font("Test", Font.PLAIN, 24));
        g.drawString(
                "Initial Rendering Test!",
                GraphicalGame.SPRITE_SIZE * GraphicalGame.SCALE,
                GraphicalGame.SPRITE_SIZE * GraphicalGame.SCALE - 8
        );
        g.setColor(Color.BLACK);
        g.setFont(new Font("Test", Font.PLAIN, 12));
        int offsetY = 1;
        for (SpriteID spriteID : SpriteID.values()) {
            List<Sprite> sprites = spriteManager.getSpriteList(spriteID);
            g.drawString(
                    spriteID.name(),
                    8,
                    offsetY * GraphicalGame.SPRITE_SIZE * GraphicalGame.SCALE + 18
            );
            int offsetX = 5;
            for (Sprite sprite : sprites) {
                drawSprite(g, sprite, offsetX, offsetY);
                offsetX++;
            }
            offsetY++;
        }

    }

}
