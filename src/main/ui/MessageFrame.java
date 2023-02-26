package ui;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.screen.Screen;
import model.Game;

import java.util.List;

public class MessageFrame extends Frame {
    int numMessages;

    // EFFECTS: Initializes a message info frame that shows game messages
    public MessageFrame(
            int leftBound, int topBound, int rightBound, int bottomBound,
            Screen screen, Game game, int numMessages) {
        super(leftBound, topBound, rightBound, bottomBound, "Messages", screen, game);
        this.numMessages = numMessages;
    }

    // EFFECTS: Renders the last few messages
    public void renderMessages() {
        List<String> lastMessages = game.getLastMessages(numMessages);
        for (int i = 0; i < lastMessages.size(); i++) {
            drawText(0, i, lastMessages.get(i));
        }
    }
}
