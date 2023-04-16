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
                "Rendering and Graphics Test! - Press ESC To Exit " + gameWindow.getTick(),
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
        int offsetX = 0;
        for (SpriteID spriteID : SpriteID.values()) {
            if (offsetY > 22) {
                offsetX = 15;
                offsetY = 5;
            }
            Sprite sprite = textureManager.getSprite(spriteID);
            List<Sprite> sprites = textureManager.getSpriteList(spriteID);
            g.drawString(
                    spriteID.name(),
                    offsetX * textureManager.getSpriteSize() + 8,
                    offsetY * textureManager.getSpriteSize() + 18
            );
            int tileOffsetX = 5;
            drawMapSprite(g, sprite, offsetX + tileOffsetX, offsetY, gameWindow.getTick());
            tileOffsetX++;
            for (Sprite s : sprites) {
                drawMapSprite(g, s, offsetX + tileOffsetX, offsetY);
                tileOffsetX++;
            }
            offsetY++;
        }
    }

    // MODIFIES: g
    // EFFECTS: Renders the test renderer
    @Override
    public void paint(Graphics g) {
        super.paint(g);

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
        super.onKeyPress(e);

        System.out.printf(String.format("Key Pressed: %s\n", e.getKeyChar()));
    }

    // EFFECTS: Debugs Mouse events
    @Override
    public void onMouseMove(MouseEvent e) {
        super.onMouseMove(e);

        System.out.printf(String.format("Mouse Moved: %s, %s\n", e.getX(), e.getY()));
    }


    // EFFECTS: Debugs Clicked events
    @Override
    public void onMouseClick(MouseEvent e) {
        super.onMouseClick(e);

        System.out.printf(String.format("Mouse Clicked: %s, %s", e.getX(), e.getY()));
    }
}
