package model.enemies;

import com.googlecode.lanterna.TextColor;
import model.Enemy;
import model.Game;
import model.Item;
import model.items.*;

public class Guard extends Enemy {
    private static final int INITIAL_HEALTH = 50;
    private static final int INITIAL_DEFENSE = 5;
    private static final int INITIAL_ATTACK = 20;
    private static final int TICKS_UNTIL_MOVEMENT = 20;

    // EFFECTS: Creates a basic guard enemy
    public Guard(Game game) {
        super(
                game,
                '%', TextColor.ANSI.MAGENTA, TextColor.ANSI.DEFAULT,
                "Guard", INITIAL_HEALTH, INITIAL_DEFENSE, INITIAL_ATTACK
        );
    }

    // MODIFIES: this
    // EFFECTS: Drops loop on death
    @Override
    public void onDeath() {
        // Chance of dropping either Armor, Weapon, or Potion
        Item droppedItem = new HealingPotion();
        switch (getGame().getRandom().nextInt(5)) {
            case 0:
                droppedItem = new GuardArmor(getGame().getRandom().nextInt(25));
                break;
            case 1:
                droppedItem = new GuardSword(getGame().getRandom().nextInt(25));
                break;
            case 2:
                droppedItem = new StrengthPotion();
                break;
            case 3:
                droppedItem = new DefensePotion();
                break;
            case 4:
                droppedItem = new HealingPotion();
                break;
        }
        // Drop item
        getGame().getLevel().dropItem(getPosX(), getPosY(), droppedItem);
    }

    // MODIFIES: this
    // EFFECTS: Check if enemy should move on each tick
    @Override
    public void handleNextTick(int tick) {
        if (tick % TICKS_UNTIL_MOVEMENT == 0) {
            if (stunned) {
                stunned = false;
                return;
            }

            switch (getGame().getRandom().nextInt(4)) {
                case 0:
                    moveUp();
                    break;
                case 1:
                    moveDown();
                    break;
                case 2:
                    moveLeft();
                    break;
                case 3:
                    moveRight();
                    break;
            }
        }
    }
}
