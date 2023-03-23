package ui.renderers;

import model.Game;
import model.graphics.SpriteID;
import ui.GraphicalGame;
import ui.sprites.Sprite;
import ui.sprites.SpriteManager;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

public class TestRenderer extends Renderer {
    public TestRenderer(GraphicalGame gameWindow) {
        super(gameWindow);
    }

    @Override
    public void paint(Graphics g) {
        // TODO: Test Draw!
        g.setColor(Color.GRAY);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(Color.WHITE);
        g.setFont(new Font("Test", Font.PLAIN, 24));
        g.drawString(
                "Initial Rendering Test! " + gameWindow.getTick(),
                GraphicalGame.SPRITE_SIZE * GraphicalGame.SCALE,
                GraphicalGame.SPRITE_SIZE * GraphicalGame.SCALE - 8
        );
        g.setColor(Color.BLACK);
        g.setFont(new Font("Test", Font.PLAIN, 12));
        int offsetY = 1;
        for (SpriteID spriteID : SpriteID.values()) {
            Sprite sprite = spriteManager.getSprite(spriteID);
            List<Sprite> sprites = spriteManager.getSpriteList(spriteID);
            g.drawString(
                    spriteID.name(),
                    8,
                    offsetY * GraphicalGame.SPRITE_SIZE * GraphicalGame.SCALE + 18
            );
            int offsetX = 5;
            drawSprite(g, sprite, offsetX, offsetY, gameWindow.getTick());
            offsetX++;
            for (Sprite s : sprites) {
                drawSprite(g, s, offsetX, offsetY);
                offsetX++;
            }
            offsetY++;
        }

    }

}
