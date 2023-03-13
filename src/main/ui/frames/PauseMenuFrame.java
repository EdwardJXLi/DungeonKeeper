package ui.frames;

import com.googlecode.lanterna.screen.Screen;
import model.Game;
import ui.TerminalFrame;

/*
 * text-based ui frame to render the inventory instructions
 */

public class PauseMenuFrame extends TerminalFrame {
    // EFFECTS: Initializes an inventory frame that shows inventory contents
    public PauseMenuFrame(
            int leftBound, int topBound, int rightBound, int bottomBound,
            Screen screen, Game game) {
        super(leftBound, topBound, rightBound, bottomBound, "Instructions", screen, game);
    }

    // EFFECTS: Renders the Instructions
    public void renderPauseMenu() {
        // Render Instructions
        drawText(0, 0, "GAME PAUSED");
        drawText(0, 2, "Press [ESC] to Resume Game");
        drawText(0, 3, "Press [q] to Save and Quit");
        drawText(0, 4, "Press [x] to Quit Without Saving");
    }
}
