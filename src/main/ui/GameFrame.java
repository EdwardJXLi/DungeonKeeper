package ui;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.screen.Screen;
import model.Game;
import model.Tile;

public class GameFrame extends Frame {
    Game game;

    public GameFrame(int leftBound, int topBound, int rightBound, int bottomBound, Screen screen, Game game) {
        super(leftBound, topBound, rightBound, bottomBound, "Game Level", screen);
        this.game = game;
    }

    public void drawGame() {
        // Draw all tiles, entities, and items on the map
        renderTiles();
    }

    private void renderTiles() {
        for (Tile t: game.getLevel().getTiles()) {
            drawSprite(t.getPosX(), t.getPosY(), t.getTextSprite(), t.getTextColor(), TextColor.ANSI.DEFAULT);
        }
    }
}
