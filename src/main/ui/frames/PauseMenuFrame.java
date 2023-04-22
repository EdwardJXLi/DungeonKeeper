package ui.frames;

import com.googlecode.lanterna.screen.Screen;
import model.Game;
import ui.TerminalFrame;

/*
 * text-based ui frame to render the pause menu
 */

public class PauseMenuFrame extends TerminalFrame {
    // EFFECTS: Initializes an inventory frame that shows the pause menu
    public PauseMenuFrame(
            int leftBound, int topBound, int rightBound, int bottomBound,
            Screen screen, Game game) {
        super(leftBound, topBound, rightBound, bottomBound, "Instructions", screen, game);
    }

    // EFFECTS: Renders the Instructions
    public void renderPauseMenu(boolean showSaveGame) {
        this.drawFrame();
        drawText(0, 0, "GAME PAUSED");

        // Render Instructions
        if (showSaveGame) {
            drawText(0, 2, "Press [ESC] to Resume Game");
            drawText(0, 3, "Press [s] to Save Game");
            drawText(0, 4, "Press [l] to Load Last Save Game");
            drawText(0, 5, "Press [q] to Save and Quit");
            drawText(0, 6, "Press [x] to Quit Without Saving");
        } else {
            drawText(0, 2, "Press [ESC] to Resume Game");
            drawText(0, 3, "Press [s] to Save Game");
            drawText(0, 4, "Press [q] to Save and Quit");
            drawText(0, 5, "Press [x] to Quit Without Saving");
        }
    }
}
