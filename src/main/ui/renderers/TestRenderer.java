package ui.renderers;

import model.graphics.SpriteID;
import ui.GameWindow;
import ui.sprites.Sprite;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.List;

public class TestRenderer extends Renderer {
    public TestRenderer(GameWindow gameWindow) {
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
                textureManager.getSpriteSize(),
                textureManager.getSpriteSize() - 8
        );
        g.setColor(Color.BLACK);
        g.setFont(new Font("Test", Font.PLAIN, 12));
        int offsetY = 1;
        for (SpriteID spriteID : SpriteID.values()) {
            Sprite sprite = textureManager.getSprite(spriteID);
            List<Sprite> sprites = textureManager.getSpriteList(spriteID);
            g.drawString(
                    spriteID.name(),
                    8,
                    offsetY * textureManager.getSpriteSize() + 18
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

    @Override
    public void onKeyPress(KeyEvent e) {
        System.out.printf(String.format("Key Pressed: %s\n", e.getKeyChar()));
    }

    @Override
    public void onMouseMove(MouseEvent e) {
        System.out.printf(String.format("Mouse Moved: %s, %s\n", e.getX(), e.getY()));
    }

    @Override
    public void onMouseClick(MouseEvent e) {
        System.out.printf(String.format("Mouse Clicked: %s, %s", e.getX(), e.getY()));
    }
}
