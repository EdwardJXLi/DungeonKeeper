package ui;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.screen.Screen;
import model.Enemy;
import model.Game;

public class InfoFrame extends Frame {
    private static final int ENEMY_INFO_OFFSET = 4;

    // EFFECTS: Initializes a player info frame that shows information stats
    public InfoFrame(int leftBound, int topBound, int rightBound, int bottomBound, Screen screen, Game game) {
        super(leftBound, topBound, rightBound, bottomBound, "Information", screen, game);
    }

    public void renderInfo() {
        // Draw Basic Instructions
        drawText(0, 0, "Use [W][A][S][D] to move", TextColor.ANSI.WHITE, TextColor.ANSI.DEFAULT);
        drawText(0, 1, "Press [E] for inventory", TextColor.ANSI.WHITE, TextColor.ANSI.DEFAULT);

        // If player last fought an enemy, print its information
        Enemy lastEnemy = game.getPlayer().getLastEnemyFought();
        if (lastEnemy != null) {
            // Show what the last enemy fought was
            drawText(2, ENEMY_INFO_OFFSET, "[Last Enemy Fought]", TextColor.ANSI.RED, TextColor.ANSI.WHITE);
            drawSprite(
                    3, ENEMY_INFO_OFFSET + 1,
                    lastEnemy.getTextSprite(), lastEnemy.getTextColor(), lastEnemy.getBackgroundColor()
            );
            drawText(
                    5, ENEMY_INFO_OFFSET + 1,
                    lastEnemy.getName(), TextColor.ANSI.WHITE, TextColor.ANSI.DEFAULT);

            // Show enemy information
            drawText(2, ENEMY_INFO_OFFSET + 2,
                    String.format("HEALTH: %d/%d", lastEnemy.getHealth(), lastEnemy.getMaxHealth()),
                    TextColor.ANSI.RED, TextColor.ANSI.DEFAULT
            );
            drawText(2, ENEMY_INFO_OFFSET + 3,
                    String.format("ATTACK: %d", lastEnemy.getAttack()),
                    TextColor.ANSI.YELLOW, TextColor.ANSI.DEFAULT
            );
            drawText(2, ENEMY_INFO_OFFSET + 4,
                    String.format("DEFENCE: %d", lastEnemy.getDefense()),
                    TextColor.ANSI.BLUE, TextColor.ANSI.DEFAULT
            );
            drawText(2, ENEMY_INFO_OFFSET + 5,
                    String.format("LOCATION: (x: %d, y: %d)", lastEnemy.getPosX(), lastEnemy.getPosY()),
                    TextColor.ANSI.WHITE, TextColor.ANSI.DEFAULT
            );

            // If enemy is dead, tell player!
            if (lastEnemy.isDead()) {
                drawText(4, ENEMY_INFO_OFFSET + 6, "DEAD", TextColor.ANSI.RED, TextColor.ANSI.WHITE);
            }
        }

        // Draw player coordinates
        drawText(
                0, bottomBound - topBound - 2,
                String.format("[Coords] X: %d, Y: %d", game.getPlayer().getPosX(), game.getPlayer().getPosY()),
                TextColor.ANSI.WHITE, TextColor.ANSI.DEFAULT);
    }
}
