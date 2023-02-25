package ui;

import com.googlecode.lanterna.screen.Screen;

public class HudFrame extends Frame {
    public HudFrame(int leftBound, int topBound, int rightBound, int bottomBound, Screen screen) {
        super(leftBound, topBound, rightBound, bottomBound, "HUD", screen);
    }
}
