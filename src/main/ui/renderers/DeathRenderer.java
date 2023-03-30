package ui.renderers;

import ui.GameWindow;

import java.awt.*;
import java.awt.event.KeyEvent;

/*
 * Renderer for the death menu.
 * Tells the player that they died.
 */

public class DeathRenderer extends MenuRenderer {
    public DeathRenderer(GameWindow gameWindow) {
        super(gameWindow);
    }

    // EFFECTS: Handles key presses
    @Override
    public void onKeyPress(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            gameWindow.switchRenderer(gameWindow.getGameRenderer(), false);
        }
    }

    // MODIFIES: g
    // EFFECTS: Renders pause menu
    @Override
    public void paint(Graphics g) {
        // Draw background
        renderBackground(g);

        // Draw pause menu in the center of the screen
        g.setColor(Color.WHITE);
        g.setFont(textureManager.getFont(32));
        g.drawString("YOU DIED...", 16, 48);
    }
}
