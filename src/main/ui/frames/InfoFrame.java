package ui.frames;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.screen.Screen;
import model.*;
import ui.TerminalFrame;

import java.util.List;

/*
 * text-based ui frame to render live game information and tooltips
 */

public class InfoFrame extends TerminalFrame {
    private static final int INFO_OFFSET = 5;

    // EFFECTS: Initializes a player info frame that shows information stats
    public InfoFrame(int leftBound, int topBound, int rightBound, int bottomBound, Screen screen, Game game) {
        super(leftBound, topBound, rightBound, bottomBound, "Information", screen, game);
    }

    // EFFECTS: Renders the HUD info box. Draws useful info about the game
    //          as well as user coordinates
    //          The importance of what gets rendered is as follows:
    //           - A dropped item is at player location
    //           - Player is standing on a special tile
    //           - Player previously fought an enemy
    public void renderInfo() {
        // Draw Basic Instructions
        drawText(0, 0, "Controls:");
        drawText(2, 1, "[W][A][S][D] to move");
        drawText(2, 2, "[E] to open inventory");

        // Get General Info
        Player player = getGame().getPlayer();
        DroppedItem droppedItem = getGame().getLevel().getDroppedItemAtLocation(player.getPosX(), player.getPosY());
        Tile standingTile = getGame().getLevel().getTileAtLocation(player.getPosX(), player.getPosY());
        Enemy lastEnemy = player.getLastEnemyFought();

        // If a dropped item is at the location, print its information
        // else, if player standing on a tile, print its information
        // else, if last fought an enemy, print its information
        if (droppedItem != null) {
            renderDroppedItemInformation(droppedItem);
        } else if (standingTile != null && standingTile.getDescription() != null) {
            renderTileInformation(standingTile);
        } else if (lastEnemy != null) {
            renderEnemyInformation(lastEnemy);
        }

        // Draw player coordinates
        drawText(
                0, getHeight() - 1,
                String.format(
                        "[Coords] X: %d, Y: %d",
                        getGame().getPlayer().getPosX(), getGame().getPlayer().getPosY()
                ),
                TextColor.ANSI.WHITE, TextColor.ANSI.DEFAULT);
    }

    // EFFECTS: Helper function to render information about dropped item
    private void renderDroppedItemInformation(DroppedItem droppedItem) {
        Item item = droppedItem.getItem();

        // Print information about tile
        drawText(2, INFO_OFFSET, "[Dropped Item]", TextColor.ANSI.GREEN, TextColor.ANSI.WHITE);

        // Give instructions on picking up item
        drawText(2, INFO_OFFSET + 1, "[Q] to pick up", TextColor.ANSI.GREEN, TextColor.ANSI.DEFAULT);
        drawText(2, INFO_OFFSET + 2, "[X] to discard", TextColor.ANSI.GREEN, TextColor.ANSI.DEFAULT);

        // Render item & description
        drawSprite(
                3, INFO_OFFSET + 4,
                item.getTextSprite(), item.getTextColor(), item.getBackgroundColor()
        );
        drawText(
                5, INFO_OFFSET + 4,
                item.getName(), TextColor.ANSI.GREEN, TextColor.ANSI.DEFAULT);

        if (item.getDescription() != null) {
            // Render tile information
            for (int i = 0; i < item.getDescription().size(); i++) {
                drawText(2, INFO_OFFSET + 5 + i, item.getDescription().get(i));
            }
        }
    }

    // EFFECTS: Helper function to render information about tile
    private void renderTileInformation(Tile tile) {
        List<String> description = tile.getDescription();
        // Check is description does not exist
        if (description == null) {
            return;
        }
        // Print information about tile
        drawText(2, INFO_OFFSET, "[Current Tile]", TextColor.ANSI.BLUE, TextColor.ANSI.WHITE);
        drawSprite(
                3, INFO_OFFSET + 1,
                tile.getTextSprite(), tile.getTextColor(), tile.getBackgroundColor()
        );
        drawText(
                5, INFO_OFFSET + 1,
                tile.getName(), TextColor.ANSI.BLUE, TextColor.ANSI.DEFAULT);

        // Render tile information
        for (int i = 0; i < description.size(); i++) {
            drawText(2, INFO_OFFSET + 3 + i, description.get(i));
        }
    }

    // EFFECTS: Helper function to render info about enemy
    private void renderEnemyInformation(Enemy enemy) {
        // Show what the last enemy fought was
        drawText(2, INFO_OFFSET, "[Last Enemy Fought]", TextColor.ANSI.RED, TextColor.ANSI.WHITE);
        drawSprite(
                3, INFO_OFFSET + 1,
                enemy.getTextSprite(), enemy.getTextColor(), enemy.getBackgroundColor()
        );
        drawText(
                5, INFO_OFFSET + 1,
                enemy.getName(), TextColor.ANSI.RED, TextColor.ANSI.DEFAULT);

        // Show enemy information
        drawText(2, INFO_OFFSET + 3,
                String.format("HEALTH: %d/%d", enemy.getHealth(), enemy.getMaxHealth()),
                TextColor.ANSI.RED, TextColor.ANSI.DEFAULT
        );
        drawText(2, INFO_OFFSET + 4,
                String.format("ATTACK: %d", enemy.getAttack()),
                TextColor.ANSI.YELLOW, TextColor.ANSI.DEFAULT
        );
        drawText(2, INFO_OFFSET + 5,
                String.format("DEFENCE: %d", enemy.getDefense()),
                TextColor.ANSI.BLUE, TextColor.ANSI.DEFAULT
        );

        // If enemy is dead, tell player!
        if (enemy.isDead()) {
            drawText(4, INFO_OFFSET + 6, "DEAD", TextColor.ANSI.RED, TextColor.ANSI.WHITE);
        }
    }
}
