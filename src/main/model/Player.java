package model;

import com.googlecode.lanterna.TextColor;
import model.graphics.SpriteID;
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
//    public static final int INITIAL_HEALTH = 200;
//    public static final int INITIAL_DEFENSE = 0;
//    public static final int INITIAL_ATTACK = 20;
    // TODO: TEMPORARY VALUES FOR TESTING
    public static final int INITIAL_HEALTH = 500;
    public static final int INITIAL_DEFENSE = 500;
    public static final int INITIAL_ATTACK = 500;

    // Player Information
    private int kills;
    private Enemy lastEnemy;
    private final Inventory inventory;

    // EFFECTS: Creates a generic player
    public Player(Game game) {
        super(
                game, 0, 0,
                '@', TextColor.ANSI.YELLOW, TextColor.ANSI.DEFAULT, SpriteID.PLAYER,
                "Player", INITIAL_HEALTH, INITIAL_DEFENSE, INITIAL_ATTACK
        );
        // Initialize Variables
        kills = 0;
        inventory = new Inventory();
    }

    // REQUIRES: Only used for loading from save.
    // EFFECTS: Creates a player from pre-existing values.
    //          Used for loading games from JSON file
    public Player(Game game, int posX, int posY, int health, int defense, int attack, int kills, Inventory inventory) {
        super(game, posX, posY, '@', TextColor.ANSI.YELLOW, TextColor.ANSI.DEFAULT, SpriteID.PLAYER,
                "Player", INITIAL_HEALTH, INITIAL_DEFENSE, INITIAL_ATTACK);
        setHealth(health);
        setDefense(defense);
        setAttack(attack);
        this.kills = kills;
        this.inventory = inventory;
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

        // Else, just let the superclass method handle movement
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
        getGame().sendMessage(String.format("★ Removed Item %s to inventory", item.getName()));
        inventory.removeItem(item);
    }

    // MODIFIES: this
    // EFFECTS: Picks up dropped item and add to inventory
    public void pickupItem(DroppedItem di) {
        getGame().sendMessage("★ Picked Up Dropped Item");
        addItem(di.getItem());
        getGame().getLevel().removeDroppedItem(di);
    }

    // MODIFIES: this
    // EFFECTS: Drops item onto ground
    public void dropItem(Item item) {
        getGame().sendMessage("★ Dropped Item To Ground");
        getGame().getLevel().dropItem(getPosX(), getPosY(), item);
        removeItem(item);
    }

    // EFFECTS: Gets currently equipped armor
    public Armor getArmor() {
        return inventory.getEquippedArmor();
    }

    // MODIFIES: this
    // EFFECTS: Sets the new equipped armor
    public void equipArmor(Armor armor) {
        getGame().sendMessage(String.format("★ Equipped Armor %s", armor.getName()));
        if (inventory.getEquippedArmor() == null) {
            inventory.setEquippedArmor(armor);
            removeItem(armor);
        } else {
            // Remove armor and try again
            unequipArmor();
            equipArmor(armor);
        }
    }

    // MODIFIES: this
    // EFFECTS: Removes the old equipped armor and adds it back into the inventory
    public void unequipArmor() {
        if (inventory.getEquippedArmor() != null) {
            getGame().sendMessage(String.format("★ Removed Armor %s", inventory.getEquippedArmor().getName()));
            addItem(inventory.getEquippedArmor());
            inventory.setEquippedArmor(null);
        }
    }

    // EFFECTS: Gets currently equipped weapon
    public Weapon getWeapon() {
        return inventory.getEquippedWeapon();
    }

    // MODIFIES: this
    // EFFECTS: Sets the new equipped weapon
    public void equipWeapon(Weapon weapon) {
        getGame().sendMessage(String.format("★ Equipped Weapon %s", weapon.getName()));
        if (inventory.getEquippedWeapon() == null) {
            inventory.setEquippedWeapon(weapon);
            removeItem(weapon);
        } else {
            // Remove weapon and try again
            unequipWeapon();
            equipWeapon(weapon);
        }
    }

    // MODIFIES: this
    // EFFECTS: Removes the old equipped weapon and adds it back into the inventory
    public void unequipWeapon() {
        if (inventory.getEquippedWeapon() != null) {
            getGame().sendMessage(String.format("★ Removed Weapon %s", inventory.getEquippedWeapon().getName()));
            addItem(inventory.getEquippedWeapon());
            inventory.setEquippedWeapon(null);
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
