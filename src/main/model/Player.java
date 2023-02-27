package model;

import com.googlecode.lanterna.TextColor;
import model.items.*;

import java.util.ArrayList;
import java.util.List;

public class Player extends Entity {
    // Player Constants
    public static final int INITIAL_HEALTH = 200;
    public static final int INITIAL_DEFENSE = 0;
    public static final int INITIAL_ATTACK = 20;

    // Player Information
    private int kills;
    private Enemy lastEnemy;
    private final List<Item> inventory;
    private Armor equippedArmor;
    private Weapon equippedWeapon;

    // EFFECTS: Creates a generic player
    public Player(Game game) {
        super(
                game, 0, 0,
                '@', TextColor.ANSI.YELLOW, TextColor.ANSI.DEFAULT,
                "Player", INITIAL_HEALTH, INITIAL_DEFENSE, INITIAL_ATTACK
        );
        // Initialize Variables
        kills = 0;
        inventory = new ArrayList<>();
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
            getGame().sendMessage(String.format("Congrats! You Killed The %s", enemy.getName()));
        }
    }

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
    public void addItem(Item item) {
        getGame().sendMessage(String.format("★ Added Item %s to inventory", item.getName()));
        inventory.add(item);
    }

    // MODIFIES: this
    // EFFECTS: Removes item from inventory
    public void removeItem(Item item) {
        inventory.removeIf(i -> i == item);
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
        return equippedArmor;
    }

    // MODIFIES: this
    // EFFECTS: Sets currently equipped armor and places current armor in inventory
    public void equipArmor(Armor armor) {
        if (equippedArmor == null) {
            equippedArmor = armor;
            removeItem(armor);
        } else {
            Armor previousArmor = equippedArmor;
            equippedArmor = armor;
            removeItem(armor);
            addItem(previousArmor);
        }
    }

    // EFFECTS: Gets currently equipped weapon
    public Weapon getWeapon() {
        return equippedWeapon;
    }

    // MODIFIES: this
    // EFFECTS: Sets currently equipped armor and places current armor in inventory
    public void equipWeapon(Weapon weapon) {
        if (equippedWeapon == null) {
            equippedWeapon = weapon;
            removeItem(weapon);
        } else {
            Weapon previousWeapon = equippedWeapon;
            equippedWeapon = weapon;
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
        if (equippedArmor != null) {
            return super.getDefense() + equippedArmor.getAdditionalDefense();
        } else {
            return super.getDefense();
        }
    }

    // EFFECTS: Override existing get attack to add weapon attack
    @Override
    public int getAttack() {
        if (equippedWeapon != null) {
            return super.getAttack() + equippedWeapon.getAdditionalAttack();
        } else {
            return super.getAttack();
        }
    }
}
