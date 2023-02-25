package ui;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.Terminal;
import model.tiles.Wall;

public abstract class Frame {
    int leftBound;
    int topBound;
    int rightBound;
    int bottomBound;
    String frameName;
    Screen screen;

    public Frame(int leftBound, int topBound, int rightBound, int bottomBound, String frameName, Screen screen) {
        this.leftBound = leftBound;
        this.rightBound = rightBound;
        this.topBound = topBound;
        this.bottomBound = bottomBound;
        this.frameName = frameName;
        this.screen = screen;
    }

    public void drawFrame() {
        // Draw the corners
        internalDrawSprite(leftBound, topBound, '⎾', TextColor.ANSI.GREEN, TextColor.ANSI.DEFAULT);
        internalDrawSprite(leftBound, bottomBound, '⎿', TextColor.ANSI.GREEN, TextColor.ANSI.DEFAULT);
        internalDrawSprite(rightBound, topBound, '⏋', TextColor.ANSI.GREEN, TextColor.ANSI.DEFAULT);
        internalDrawSprite(rightBound, bottomBound, '⏌', TextColor.ANSI.GREEN, TextColor.ANSI.DEFAULT);

        // Draw Top and Bottom Frame Bounds
        for (int x = leftBound + 1; x < rightBound; x++) {
            internalDrawSprite(x, topBound, '-', TextColor.ANSI.GREEN, TextColor.ANSI.DEFAULT);
            internalDrawSprite(x, bottomBound, '-', TextColor.ANSI.GREEN, TextColor.ANSI.DEFAULT);
        }

        // Draw Left and Right Frame Bounds
        for (int y = topBound + 1; y < bottomBound; y++) {
            internalDrawSprite(leftBound, y, '|', TextColor.ANSI.GREEN, TextColor.ANSI.DEFAULT);
            internalDrawSprite(rightBound, y, '|', TextColor.ANSI.GREEN, TextColor.ANSI.DEFAULT);
        }

        // Draw Frame Title
        internalDrawText(leftBound + 2, topBound, frameName, TextColor.ANSI.GREEN, TextColor.ANSI.DEFAULT);
    }

    public void drawSprite(int posX, int posY, char c, TextColor foregroundColor, TextColor backgroundColor) {
        // use internal drawing with offset of frame window
        internalDrawSprite(posX + 1, posY + 1, c, foregroundColor, backgroundColor);
    }

    public void drawText(int posX, int posY, String text, TextColor foregroundColor, TextColor backgroundColor) {
        // use internal drawing with offset of frame window
        internalDrawText(posX + 1, posY + 1, text, foregroundColor, backgroundColor);
    }

    private void internalDrawSprite(int posX, int posY, char c, TextColor foregroundColor, TextColor backgroundColor) {
        // Initialize the sprite
        TextGraphics sprite = screen.newTextGraphics();
        sprite.setForegroundColor(foregroundColor);
        sprite.setBackgroundColor(backgroundColor);
        sprite.putString(posX, posY, String.valueOf(c));
    }

    private void internalDrawText(
            int posX, int posY, String text, TextColor foregroundColor, TextColor backgroundColor
    ) {
        // Initialize the sprite
        TextGraphics sprite = screen.newTextGraphics();
        sprite.setForegroundColor(foregroundColor);
        sprite.setBackgroundColor(backgroundColor);
        sprite.putString(posX, posY, text);
    }
}
