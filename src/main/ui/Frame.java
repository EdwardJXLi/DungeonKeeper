package ui;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;
import model.Game;

public abstract class Frame {
    int leftBound;
    int topBound;
    int rightBound;
    int bottomBound;
    String frameName;
    Screen screen;
    Game game;

    // EFFECTS: Initializes a generic frame that fits within bounds
    public Frame(
            int leftBound, int topBound, int rightBound, int bottomBound, String frameName, Screen screen, Game game
    ) {
        this.leftBound = leftBound;
        this.rightBound = rightBound;
        this.topBound = topBound;
        this.bottomBound = bottomBound;
        this.frameName = frameName;
        this.screen = screen;
        this.game = game;
    }

    // EFFECTS: Draws the border of the frame
    public void drawFrame() {
        // Draw the corners
        internalDrawSprite(leftBound, topBound, '⎾', TextColor.ANSI.GREEN, TextColor.ANSI.DEFAULT);
        internalDrawSprite(leftBound, bottomBound, '⎿', TextColor.ANSI.GREEN, TextColor.ANSI.DEFAULT);
        internalDrawSprite(rightBound, topBound, '⏋', TextColor.ANSI.GREEN, TextColor.ANSI.DEFAULT);
        internalDrawSprite(rightBound, bottomBound, '⏌', TextColor.ANSI.GREEN, TextColor.ANSI.DEFAULT);

        // Draw Top and Bottom Frame Borders
        for (int x = leftBound + 1; x < rightBound; x++) {
            internalDrawSprite(x, topBound, '-', TextColor.ANSI.GREEN, TextColor.ANSI.DEFAULT);
            internalDrawSprite(x, bottomBound, '-', TextColor.ANSI.GREEN, TextColor.ANSI.DEFAULT);
        }

        // Draw Left and Right Frame Borders
        for (int y = topBound + 1; y < bottomBound; y++) {
            internalDrawSprite(leftBound, y, '|', TextColor.ANSI.GREEN, TextColor.ANSI.DEFAULT);
            internalDrawSprite(rightBound, y, '|', TextColor.ANSI.GREEN, TextColor.ANSI.DEFAULT);
        }

        // Draw Frame Title
        internalDrawText(leftBound + 2, topBound, frameName, TextColor.ANSI.GREEN, TextColor.ANSI.DEFAULT);
    }

    // EFFECTS: Draws a sprite with X and Y positions inside the frame
    public void drawSprite(int posX, int posY, char c, TextColor foregroundColor, TextColor backgroundColor) {
        // use internal drawing with offset of frame window
        internalDrawSprite(posX + leftBound + 1, posY + topBound + 1, c, foregroundColor, backgroundColor);
    }

    // EFFECTS: Draws text with default white on black coloring
    public void drawText(int posX, int posY, String text) {
        // use internal drawing with offset of frame window
        internalDrawText(
                posX + leftBound + 1, posY + topBound + 1, text,
                TextColor.ANSI.WHITE, TextColor.ANSI.DEFAULT
        );
    }

    // EFFECTS: Draws text with X and Y positions inside the frame
    public void drawText(int posX, int posY, String text, TextColor foregroundColor, TextColor backgroundColor) {
        // use internal drawing with offset of frame window
        internalDrawText(posX + leftBound + 1, posY + topBound + 1, text, foregroundColor, backgroundColor);
    }

    // EFFECTS: Internal helper function to draw sprites on the screen
    private void internalDrawSprite(int posX, int posY, char c, TextColor fgColor, TextColor bgColor) {
        // Initialize the sprite
        TextGraphics sprite = screen.newTextGraphics();
        sprite.setForegroundColor(fgColor);
        sprite.setBackgroundColor(bgColor);
        sprite.putString(posX, posY, String.valueOf(c));
    }

    // EFFECTS: Internal helper function to draw text on the screen
    private void internalDrawText(int posX, int posY, String text, TextColor fgColor, TextColor bgColor) {
        // Initialize the sprite
        TextGraphics sprite = screen.newTextGraphics();
        sprite.setForegroundColor(fgColor);
        sprite.setBackgroundColor(bgColor);
        sprite.putString(posX, posY, text);
    }
}
