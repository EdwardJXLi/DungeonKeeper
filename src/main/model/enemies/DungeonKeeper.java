package model.enemies;

import com.googlecode.lanterna.TextColor;
import model.Enemy;
import model.Game;
import model.Item;
import model.graphics.SpriteID;
import model.items.*;

/*
 * The dungeon keeper enemy. Drops the dungeon key on death.
 * Large, attack, defense, and health.
 * Idea is that the player has to kill both the vampire and mage first before killing the keeper.
 * "Final Boss" of the game.
 */

public class DungeonKeeper extends Enemy {
    // Enemy Constants
    // Public so that tests can access them
    public static final int INITIAL_HEALTH = 2000;
    public static final int INITIAL_DEFENSE = 40;
    public static final int INITIAL_ATTACK = 100;
    public static final int TICKS_UNTIL_MOVEMENT = 40;

    // EFFECTS: Creates a dungeon keeper enemy
    public DungeonKeeper(Game game) {
        super(
                game,
                '%', TextColor.ANSI.YELLOW, TextColor.ANSI.DEFAULT, SpriteID.ENTITY_DUNGEON_KEEPER,
                "Dungeon Keeper", INITIAL_HEALTH, INITIAL_DEFENSE, INITIAL_ATTACK
        );
    }

    // REQUIRES: Only used for loading from save.
    // EFFECTS: Creates a dungeon keeper, from pre-existing values.
    public DungeonKeeper(Game game, int posX, int posY, int health, int defense, int attack, boolean stunned) {
        super(game, posX, posY, '%',
                TextColor.ANSI.YELLOW, TextColor.ANSI.DEFAULT, SpriteID.ENTITY_DUNGEON_KEEPER,
                "Dungeon Keeper", stunned, INITIAL_HEALTH, INITIAL_DEFENSE, INITIAL_ATTACK,
                health, defense, attack);
    }

    // MODIFIES: this
    // EFFECTS: Drops dungeon key on death
    @Override
    public void onDeath() {
        // Drop item
        getGame().getLevel().dropItem(getPosX(), getPosY(), new DungeonKey());

        // Handle super
        super.onDeath();
    }

    // MODIFIES: this
    // EFFECTS: Check if enemy should move on each tick
    @Override
    public void handleNextTick(int tick) {
        if (tick % TICKS_UNTIL_MOVEMENT == 0) {
            // If enemy is stunned, skip movement
            if (isStunned()) {
                resetStunned();
                return;
            }

            // Process Random Movement AI
            moveRandom();
        }

        // Handle super
        super.handleNextTick(tick);
    }
}
