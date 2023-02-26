package ui;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.screen.Screen;
import model.Game;
import model.Player;

public class PlayerInfoFrame extends Frame {
    private Player player;

    public PlayerInfoFrame(int leftBound, int topBound, int rightBound, int bottomBound, Screen screen, Game game) {
        super(leftBound, topBound, rightBound, bottomBound, "Player", screen);
        this.player = game.getPlayer();
    }

    public void drawPlayerInfo() {
        drawText(0,0, String.format("HEALTH: %d/%d", player.getHealth(), player.getMaxHealth()), TextColor.ANSI.RED, TextColor.ANSI.DEFAULT);
        drawText(0,1, String.format("ATTACK: %d", player.getAttack()), TextColor.ANSI.YELLOW, TextColor.ANSI.DEFAULT);
        drawText(0,2, String.format("DEFENCE: %d", player.getDefence()), TextColor.ANSI.BLUE, TextColor.ANSI.DEFAULT);
    }
}
