package ui;

import com.googlecode.lanterna.screen.Screen;

public class InfoFrame extends Frame {
    public InfoFrame(int leftBound, int topBound, int rightBound, int bottomBound, Screen screen) {
        super(leftBound, topBound, rightBound, bottomBound, "Information", screen);
    }
}
