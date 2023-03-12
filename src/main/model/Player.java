package model;

import com.googlecode.lanterna.TextColor;
import model.items.*;
import org.json.JSONObject;
import persistence.Writable;

import java.util.List;

/*
 * Main class for Players
 * Players and attack enemies.
 */

public class Player extends Entity implements Writable {
    // Player Constants
    public static final int INITIAL_HEALTH = 200;
    public static final int INITIAL_DEFENSE = 0;
    public static final int INITIAL_ATTACK = 20;

    // Player Information
    private int kills;
    private Enemy lastEnemy;
    private final Inventory inventory;

    // EFFECTS: Creates a generic player
    public Player(Game game) {
        super(
                game, 0, 0,
                '@', TextColor.ANSI.YELLOW, TextColor.ANSI.DEFAULT,
                "Player", INITIAL_HEALTH, INITIAL_DEFENSE, INITIAL_ATTACK
        );
        // Initialize Variables
        kills = 0;
        inventory = new Inventory();
    }

    // MODIFIES: this
    // EFFECTS: Adds initial items to player
    public void initPlayer() {
        // Add three healing potions and one defense and strength potion
        addItem(new HealingPotion());
        addItem(new HealingPotion());
        addItem(new HealingPotion());
        addItem(new DefensePotion());
        addItem(new StrengthPotion());
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
            getGame().sendMessage("X Sorry! You Cant Go There!");
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
            getGame().gameOver();
        }
    }

    // MODIFIES: this, enemy
    // EFFECTS: Attacks enemy
    public void attackEnemy(Enemy enemy) {
        lastEnemy = enemy;
        // Enemy Always Attacks First
        getGame().sendMessage(String.format("⚔ You got into a fight with %s!", enemy.getName()));
        getGame().sendMessage(String.format("⚔ %s attacks first for %d", enemy.getName(), enemy.getAttack()));
        this.damage(enemy.getAttack());
        getGame().sendMessage(String.format("⚔ You fight back for %d damage!", this.getAttack()));
        getGame().sendMessage(String.format("⚔ Enemy is now stunned and at %d health.", enemy.getHealth()));
        enemy.damage(this.getAttack());
        enemy.stun();

        if (enemy.isDead()) {
            this.kills++;
            getGame().sendMessage(String.format("⚔ Congrats! You Killed The %s", enemy.getName()));
        }
    }

    // EFFECTS: Returns the last enemy fought, null otherwise
    public Enemy getLastEnemyFought() {
        return lastEnemy;
    }

    // EFFECTS: Returns the items in inventory
    public Inventory getInventory() {
        return inventory;
    }

    // MODIFIES: this
    // EFFECTS: Adds item to player inventory
    public void addItem(Item item) {
        getGame().sendMessage(String.format("★ Added Item %s to inventory", item.getName()));
        inventory.addItem(item);
    }

    // MODIFIES: this
    // EFFECTS: Removes item from player inventory
    public void removeItem(Item item) {
        inventory.removeItem(item);
    }

    // MODIFIES: this
    // EFFECTS: Picks up dropped item and add to inventory
    public void pickupItem(DroppedItem di) {
        addItem(di.getItem());
        getGame().getLevel().removeDroppedItem(di);
    }

    // MODIFIES: this
    // EFFECTS: Drops item onto ground
    public void dropItem(Item item) {
        getGame().getLevel().dropItem(getPosX(), getPosY(), item);
        removeItem(item);
    }

    // EFFECTS: Gets currently equipped armor
    public Armor getArmor() {
        return inventory.getEquippedArmor();
    }

    // MODIFIES: this
    // EFFECTS: Sets currently equipped armor and places current armor in inventory
    public void equipArmor(Armor armor) {
        if (inventory.getEquippedArmor() == null) {
            inventory.setEquippedArmor(armor);
            removeItem(armor);
        } else {
            Armor previousArmor = inventory.getEquippedArmor();
            inventory.setEquippedArmor(armor);
            removeItem(armor);
            addItem(previousArmor);
        }
    }

    // EFFECTS: Gets currently equipped weapon
    public Weapon getWeapon() {
        return inventory.getEquippedWeapon();
    }

    // MODIFIES: this
    // EFFECTS: Sets currently equipped armor and places current armor in inventory
    public void equipWeapon(Weapon weapon) {
        if (inventory.getEquippedWeapon() == null) {
            inventory.setEquippedWeapon(weapon);
            removeItem(weapon);
        } else {
            Weapon previousWeapon = inventory.getEquippedWeapon();
            inventory.setEquippedWeapon(weapon);
            removeItem(weapon);
            addItem(previousWeapon);
        }
    }

    // EFFECTS: Helper function to run movement tests
    public void setCoordinate(int posX, int posY) {
        setPosX(posX);
        setPosY(posY);
    }

    // EFFECTS: Returns the number of enemies killed
    public int getKills() {
        return kills;
    }

    // EFFECTS: Override existing get defence to add armor defence
    @Override
    public int getDefense() {
        if (inventory.getEquippedArmor() != null) {
            return super.getDefense() + inventory.getEquippedArmor().getAdditionalDefense();
        } else {
            return super.getDefense();
        }
    }

    // EFFECTS: Override existing get attack to add weapon attack
    @Override
    public int getAttack() {
        if (inventory.getEquippedWeapon() != null) {
            return super.getAttack() + inventory.getEquippedWeapon().getAdditionalAttack();
        } else {
            return super.getAttack();
        }
    }

    // EFFECTS: Returns JSON Representation of a Player object
    @Override
    public JSONObject toJson() {
        JSONObject json = super.toJson();
        json.put("kills", kills);
        json.put("inventory", inventory.toJson());
        // Last Killed Data is lost during this. THIS IS INTENTIONAL!
        // That should only be a UI helper, so no functionality would be affected!
        return json;
    }
}
