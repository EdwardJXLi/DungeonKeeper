package ui.frames;

import com.googlecode.lanterna.screen.Screen;
import model.Game;
import ui.Frame;

/*
 * text-based ui frame to render the inventory instructions
 */

public class InvInstructionsFrame extends Frame {
    // EFFECTS: Initializes an inventory frame that shows inventory contents
    public InvInstructionsFrame(
            int leftBound, int topBound, int rightBound, int bottomBound,
            Screen screen, Game game) {
        super(leftBound, topBound, rightBound, bottomBound, "Instructions", screen, game);
    }

    // EFFECTS: Renders the Instructions
    public void renderInstructions() {
        // Render Instructions
        drawText(0, 0, "[ESCAPE] or [E] to exit");
        drawText(0, 1, "[↑] or [↓] to navigate");
        drawText(0, 2, "[ENTER] to equip or use item");
        drawText(0, 3, "[Q] to drop item to the ground");
        drawText(0, 4, "[X] to discard item");
    }
}
