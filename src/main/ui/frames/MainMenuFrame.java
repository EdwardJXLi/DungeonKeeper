package ui.frames;

import com.googlecode.lanterna.screen.Screen;
import model.Game;
import ui.TerminalFrame;

/*
 * text-based ui frame to render the inventory instructions
 */

public class MainMenuFrame extends TerminalFrame {
    // EFFECTS: Initializes an inventory frame that shows inventory contents
    public MainMenuFrame(
            int leftBound, int topBound, int rightBound, int bottomBound,
            Screen screen, Game game) {
        super(leftBound, topBound, rightBound, bottomBound, "Instructions", screen, game);
    }

    // EFFECTS: Renders the Instructions
    public void renderMainMenu(boolean showSaveGame) {
        // Render Instructions
        drawText(0, 0, "MAIN MENU");

        if (showSaveGame) {
            drawText(0, 2, "[C] to to continue game");
            drawText(0, 3, "[N] for new game");
            drawText(0, 4, "[Q] or [ESCAPE] to quit");
        } else {
            drawText(0, 2, "[N] for new game");
            drawText(0, 3, "[Q] or [ESCAPE] to quit");
        }
    }
}
