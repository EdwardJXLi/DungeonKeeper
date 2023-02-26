package ui.frames;

import com.googlecode.lanterna.screen.Screen;
import model.Game;
import ui.Frame;

public class EquipmentFrame extends Frame {
    // EFFECTS: Initializes an inventory frame that shows inventory contents
    public EquipmentFrame(
            int leftBound, int topBound, int rightBound, int bottomBound,
            Screen screen, Game game) {
        super(leftBound, topBound, rightBound, bottomBound, "Equipment", screen, game);
    }

    // EFFECTS: Renders the Equipment
    public void renderEquipment() {

    }
}
