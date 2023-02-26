package model;

import com.googlecode.lanterna.TextColor;

public class Player extends Entity {
    private static final int INITIAL_HEALTH = 200;
    private static final int INITIAL_DEFENSE = 10;
    private static final int INITIAL_ATTACK = 20;

    // Player Information
    private int kills;

    // EFFECTS: Creates a generic player
    public Player(Game game) {
        super(
                game, 0, 0,
                '@', TextColor.ANSI.YELLOW, TextColor.ANSI.DEFAULT,
                "Player", INITIAL_HEALTH, INITIAL_DEFENSE, INITIAL_ATTACK
        );
        kills = 0;
    }

    // MODIFIES: this
    // EFFECTS: Before moving, check if there is an enemy there. Else, ATTACK!
    @Override
    protected boolean handleMovement(int posX, int posY) {
        // Check if there is an enemy there
        Enemy enemy = getGame().getLevel().getEnemyAtLocation(posX, posY);
        if (enemy != null) {
            attackEnemy(enemy);
            return false;
        }

        // Else, just jet the superclass method handle movement
        return super.handleMovement(posX, posY);
    }

    // MODIFIES: this
    // EFFECTS: Once damaged, check if dead. If so, end game.
    @Override
    public void damage(int amount) {
        super.damage(amount);
        if (this.isDead()) {
            // TODO: HANDLE DEATH
            System.out.println("dead");
        }
    }

    // MODIFIES: this, enemy
    // EFFECTS: Attacks enemy
    private void attackEnemy(Enemy enemy) {
        // Enemy Always Attacks First
        this.damage(enemy.getAttack() - this.getDefense());
        enemy.damage(this.getAttack() - enemy.getDefense());
        enemy.stun();
    }

    // EFFECTS: Nothing Happens
    @Override
    public void handleNextTick(int tick) {}

    //
    // Getters and Setters
    //

    public int getKills() {
        return kills;
    }
}
