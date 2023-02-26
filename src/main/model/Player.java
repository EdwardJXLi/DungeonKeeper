package model;

import com.googlecode.lanterna.TextColor;

import java.util.ArrayList;
import java.util.List;

public class Player extends Entity {
    private static final int INITIAL_HEALTH = 200;
    private static final int INITIAL_DEFENSE = 10;
    private static final int INITIAL_ATTACK = 20;

    // Player Information
    private int kills;
    private Enemy lastEnemy;
    private List<Item> inventory;

    // EFFECTS: Creates a generic player
    public Player(Game game) {
        super(
                game, 0, 0,
                '@', TextColor.ANSI.YELLOW, TextColor.ANSI.DEFAULT,
                "Player", INITIAL_HEALTH, INITIAL_DEFENSE, INITIAL_ATTACK
        );
        kills = 0;
        inventory = new ArrayList<>();
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
        boolean didMove = super.handleMovement(posX, posY);
        if (!didMove) {
            getGame().sendMessage("Sorry! You Cant Go There!");
        }
        return didMove;
    }

    // MODIFIES: this
    // EFFECTS: Once damaged, check if dead. If so, end game.
    @Override
    public void damage(int amount) {
        // Handle Damage
        super.damage(amount);

        // Print Message
        getGame().sendMessage(String.format(
                "OUCH! You Took %d Damage! Your Health Is Now At %d!", amount, getHealth()
        ));

        // Check if player is dead
        if (this.isDead()) {
            // TODO: HANDLE DEATH
            System.out.println("dead");
        }
    }

    // MODIFIES: this, enemy
    // EFFECTS: Attacks enemy
    private void attackEnemy(Enemy enemy) {
        lastEnemy = enemy;
        // Enemy Always Attacks First
        getGame().sendMessage(String.format("⚔ You got into a fight with %s!", enemy.getName()));
        getGame().sendMessage(String.format("⚔ %s attacks first for %d", enemy.getName(), enemy.getAttack()));
        this.damage(Math.max(0, enemy.getAttack() - this.getDefense()));
        getGame().sendMessage(String.format("⚔ You fight back for %d damage!", this.getAttack()));
        getGame().sendMessage(String.format("⚔ Enemy is now stunned and at %d health.", enemy.getHealth()));
        enemy.damage(Math.max(0, this.getAttack() - enemy.getDefense()));
        enemy.stun();

        if (enemy.isDead()) {
            this.kills++;
            getGame().sendMessage(String.format("Congrats! You Killed The %s", enemy.getName()));
        }
    }

    // EFFECTS: Nothing Happens
    @Override
    public void handleNextTick(int tick) {}

    // EFFECTS: Returns the last enemy fought, null otherwise
    public Enemy getLastEnemyFought() {
        return lastEnemy;
    }

    // EFFECTS: Returns the items in inventory
    public List<Item> getInventory() {
        return inventory;
    }

    // MODIFIES: this
    // EFFECTS: Adds item to inventory
    public void addItemToInventory(Item item) {
        getGame().sendMessage(String.format("★ Added Item %s to inventory", item.getName()));
        inventory.add(item);
    }

    // EFFECTS: Returns the number of enemies killed
    public int getKills() {
        return kills;
    }
}
