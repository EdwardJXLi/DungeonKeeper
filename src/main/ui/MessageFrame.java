package ui;

import com.googlecode.lanterna.screen.Screen;

public class MessageFrame extends Frame {
    public MessageFrame(int leftBound, int topBound, int rightBound, int bottomBound, Screen screen) {
        super(leftBound, topBound, rightBound, bottomBound, "Messages", screen);
    }
}
