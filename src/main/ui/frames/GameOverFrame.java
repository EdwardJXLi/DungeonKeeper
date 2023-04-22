package ui.frames;

import com.googlecode.lanterna.screen.Screen;
import model.Game;
import ui.TerminalFrame;

/*
 * text-based ui frame to render the game over screen
 */

public class GameOverFrame extends TerminalFrame {
    // EFFECTS: Initializes an inventory frame that shows the game over screen
    public GameOverFrame(
            int leftBound, int topBound, int rightBound, int bottomBound,
            Screen screen, Game game) {
        super(leftBound, topBound, rightBound, bottomBound, "Game Over", screen, game);
    }

    // EFFECTS: Renders the Game Over screen
    public void renderGameOver() {
        this.drawFrame();
        drawText(0, 0, "GAME OVER");
        drawText(0, 1, "Thank you for playing!");

        // Render Game Over
        drawText(0, 3, "Press [Q] or [ESCAPE] to quit");
    }
}
