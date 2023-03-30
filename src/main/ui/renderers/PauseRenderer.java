package ui.renderers;

import ui.GameWindow;

import java.awt.*;
import java.awt.event.KeyEvent;

/*
 * Renderer for the pause menu.
 * Allows the player to resume the game, save the game, or quit to the main menu.
 */

public class PauseRenderer extends MenuRenderer {
    public PauseRenderer(GameWindow gameWindow) {
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
        g.drawString("SKILL ISSUE", 16, 48);

        // TODO: NOT DONE!

        // Draw Debug Info
        renderDebugInfo(g);
    }
}
