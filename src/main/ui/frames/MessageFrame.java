package ui.frames;

import com.googlecode.lanterna.screen.Screen;
import model.Game;
import ui.Frame;

import java.util.List;

/*
 * text-based ui frame to render game messages
 */

public class MessageFrame extends Frame {
    // EFFECTS: Initializes a message info frame that shows game messages
    public MessageFrame(
            int leftBound, int topBound, int rightBound, int bottomBound,
            Screen screen, Game game) {
        super(leftBound, topBound, rightBound, bottomBound, "Messages", screen, game);
    }

    // EFFECTS: Renders the last few messages
    public void renderMessages() {
        List<String> lastMessages = getGame().getLastMessages(getHeight());
        for (int i = 0; i < lastMessages.size(); i++) {
            drawText(0, i, lastMessages.get(i));
        }
    }
}
