package model.enemies;

import com.googlecode.lanterna.TextColor;
import model.Enemy;
import model.Game;
import model.Item;
import model.Player;
import model.items.*;

public class Dummy extends Enemy {
    public static final int INITIAL_HEALTH = 100;
    public static final int INITIAL_DEFENSE = 0;
    public static final int INITIAL_ATTACK = 0;

    // EFFECTS: Creates a basic dummy to fight
    public Dummy(Game game) {
        super(
                game,
                ';', TextColor.ANSI.YELLOW, TextColor.ANSI.DEFAULT,
                "Dummy", INITIAL_HEALTH, INITIAL_DEFENSE, INITIAL_ATTACK
        );
    }

    // EFFECTS: Do nothing on death
    @Override
    public void onDeath() {}

    // EFFECTS: Do nothing on each tick
    @Override
    public void handleNextTick(int tick) {}
}
