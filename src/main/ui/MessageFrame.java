package ui;

import com.googlecode.lanterna.screen.Screen;

public class MessageFrame extends Frame {
    // EFFECTS: Initializes a message info frame that shows game messages
    public MessageFrame(int leftBound, int topBound, int rightBound, int bottomBound, Screen screen) {
        super(leftBound, topBound, rightBound, bottomBound, "Messages", screen);
    }
}
