package ui.renderers;

import model.graphics.SpriteID;
import ui.GameWindow;
import ui.sprites.Sprite;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.List;

/*
 * Renderer for testing graphics and rendering engine.
 * Kept in the game cuz it was cool lol
 */

public class TestRenderer extends Renderer {
    public TestRenderer(GameWindow gameWindow) {
        super(gameWindow);
    }

    // MODIFIES: g
    // EFFECTS: Draws Test String
    private void drawStringTest(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Test", Font.PLAIN, 24));
        g.drawString(
                "Initial Rendering Test! " + gameWindow.getTick(),
                textureManager.getSpriteSize(),
                textureManager.getSpriteSize() - 8
        );
        g.setColor(Color.BLACK);
        g.setFont(new Font("Test", Font.PLAIN, 12));
    }

    // MODIFIES: g
    // EFFECTS: Draws Test Sprites
    private void drawSpriteTest(Graphics g) {
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
            drawMapSprite(g, sprite, offsetX, offsetY, gameWindow.getTick());
            offsetX++;
            for (Sprite s : sprites) {
                drawMapSprite(g, s, offsetX, offsetY);
                offsetX++;
            }
            offsetY++;
        }
    }

    // MODIFIES: g
    // EFFECTS: Renders the test renderer
    @Override
    public void paint(Graphics g) {
        g.setColor(Color.GRAY);
        g.fillRect(0, 0, getWidth(), getHeight());

        // Draw Test String
        drawStringTest(g);

        // Draw Test Sprites
        drawSpriteTest(g);

        // Draw Debug Info
        renderDebugInfo(g);
    }

    // EFFECTS: Debugs Keypress events
    @Override
    public void onKeyPress(KeyEvent e) {
        System.out.printf(String.format("Key Pressed: %s\n", e.getKeyChar()));
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            gameWindow.switchRenderer(gameWindow.getGameRenderer(), false);
        }
    }

    // EFFECTS: Debugs Mouse events
    @Override
    public void onMouseMove(MouseEvent e) {
        System.out.printf(String.format("Mouse Moved: %s, %s\n", e.getX(), e.getY()));
    }


    // EFFECTS: Debugs Clicked events
    @Override
    public void onMouseClick(MouseEvent e) {
        System.out.printf(String.format("Mouse Clicked: %s, %s", e.getX(), e.getY()));
    }
}
