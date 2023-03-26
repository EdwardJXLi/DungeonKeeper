package ui.renderers;

import ui.GameWindow;

import java.awt.*;
import java.awt.event.KeyEvent;

public class PauseRenderer extends MenuRenderer {
    public PauseRenderer(GameWindow gameWindow) {
        super(gameWindow);
    }

    @Override
    public void onKeyPress(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            gameWindow.switchRenderer(gameWindow.getGameRenderer(), false);
        }
    }

    // EFFECTS: Renders pause menu
    @Override
    public void paint(Graphics g) {
        // Draw background
        renderBackground(g);

        // Draw pause menu in the center of the screen
        g.setColor(Color.WHITE);
        g.setFont(textureManager.getFont(32));
        g.drawString("GAME PAUSED", 16, 48);
    }
}
