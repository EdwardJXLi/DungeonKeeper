package ui.frames;

import com.googlecode.lanterna.screen.Screen;
import model.Game;
import ui.Frame;

public class InvPreviewFrame extends Frame {
    // EFFECTS: Initializes an inventory frame that shows inventory contents
    public InvPreviewFrame(
            int leftBound, int topBound, int rightBound, int bottomBound,
            Screen screen, Game game) {
        super(leftBound, topBound, rightBound, bottomBound, "Preview", screen, game);
    }

    // EFFECTS: Renders the Preview
    public void renderPreview() {

    }
}
