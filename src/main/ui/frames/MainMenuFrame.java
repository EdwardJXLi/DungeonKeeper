package ui.frames;

import com.googlecode.lanterna.screen.Screen;
import model.Game;
import ui.TerminalFrame;

/*
 * text-based ui frame to render the inventory instructions
 */

public class MainMenuFrame extends TerminalFrame {
    // EFFECTS: Initializes an inventory frame that shows inventory contents
    public MainMenuFrame(
            int leftBound, int topBound, int rightBound, int bottomBound,
            Screen screen, Game game) {
        super(leftBound, topBound, rightBound, bottomBound, "Instructions", screen, game);
    }

    // EFFECTS: Renders the Instructions
    public void renderMainMenu(boolean showSaveGame) {
        // Render Logo
        drawText(0, 0, " ____  _   _ _   _  ____ _____ ___  _   _ ");
        drawText(0, 1, " |  _ \\| | | | \\ | |/ ___| ____/ _ \\| \\ | |");
        drawText(0, 2, " | | | | | | |  \\| | |  _|  _|| | | |  \\| |");
        drawText(0, 3, " | |_| | |_| | |\\  | |_| | |__| |_| | |\\  |");
        drawText(0, 4, " |____/ \\___/|_| \\_|\\____|_____\\___/|_| \\_|");

        drawText(6, 5, "_   __ _____ ___________ ___________");
        drawText(6, 6, "| | / /|  ___|  ___| ___ \\  ___| ___ \\");
        drawText(6, 7, "| |/ / | |__ | |__ | |_/ / |__ | |_/ /");
        drawText(6, 8, "|    \\ |  __||  __||  __/|  __||    / ");
        drawText(6, 9, "| |\\  \\| |___| |___| |   | |___| |\\ \\ ");
        drawText(6, 10, "\\_| \\_/\\____/\\____/\\_|   \\____/\\_| \\_|");

        drawText(3, 11, "By Edward Li");

        // Render Instructions
        drawText(0, 14, ">=> MAIN MENU <=<");

        if (showSaveGame) {
            drawText(0, 15, "Press [C] to to continue game");
            drawText(0, 16, "Press [N] for new game");
            drawText(0, 17, "Press [Q] or [ESCAPE] to quit");
        } else {
            drawText(0, 15, "Press [N] for new game");
            drawText(0, 16, "Press [Q] or [ESCAPE] to quit");
        }
    }
}
