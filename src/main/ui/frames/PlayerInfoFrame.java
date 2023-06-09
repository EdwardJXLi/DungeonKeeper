package ui.frames;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.screen.Screen;
import model.Game;
import model.Player;
import ui.TerminalFrame;

/*
 * text-based ui frame to render info about the player
 */

public class PlayerInfoFrame extends TerminalFrame {
    private final Player player;

    // EFFECTS: Initializes a player info frame that shows player stats
    public PlayerInfoFrame(int leftBound, int topBound, int rightBound, int bottomBound, Screen screen, Game game) {
        super(leftBound, topBound, rightBound, bottomBound, "Player", screen, game);
        this.player = game.getPlayer();
    }

    // EFFECTS: Draws player info onto UI
    public void drawPlayerInfo() {
        drawText(0, 0,
                String.format("HEALTH: %d/%d", player.getHealth(), player.getMaxHealth()),
                TextColor.ANSI.RED, TextColor.ANSI.DEFAULT
        );
        drawText(0, 1,
                String.format("ATTACK: %d", player.getAttack()),
                TextColor.ANSI.YELLOW, TextColor.ANSI.DEFAULT
        );
        drawText(0, 2,
                String.format("DEFENCE: %d", player.getDefense()),
                TextColor.ANSI.BLUE, TextColor.ANSI.DEFAULT
        );
        drawText(0, 3,
                String.format("KILLS: %d", player.getKills()),
                TextColor.ANSI.GREEN, TextColor.ANSI.DEFAULT
        );
        drawText(0, 4,
                String.format("INVENTORY ITEMS: %d", player.getInventory().numItems()),
                TextColor.ANSI.WHITE, TextColor.ANSI.DEFAULT
        );
    }
}
