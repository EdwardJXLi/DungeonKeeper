package ui.frames;

import com.googlecode.lanterna.screen.Screen;
import model.Game;
import ui.TerminalFrame;

/*
 * text-based ui frame to render the "you win" screen
 */

public class GameWinFrame extends TerminalFrame {
    // EFFECTS: Initializes an inventory frame that shows the game win screen
    public GameWinFrame(
            int leftBound, int topBound, int rightBound, int bottomBound,
            Screen screen, Game game) {
        super(leftBound, topBound, rightBound, bottomBound, "Congratulations", screen, game);
    }

    // EFFECTS: Renders the Game Win Screen
    public void renderGameWin() {
        this.drawFrame();
        drawText(0, 0, "YOU WIN!");
        drawText(0, 1, "Thank you for playing!");

        drawText(0, 3, "Press [Q] or [ESCAPE] to quit");
    }
}
