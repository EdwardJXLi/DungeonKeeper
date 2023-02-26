package model;

import com.googlecode.lanterna.TextColor;

public abstract class Entity implements ScreenElement {
    // Screen Element Info
    private final char textSprite;
    private final TextColor textColor;
    private final TextColor backgroundColor;

    // Entity Information
    private int posX;
    private int posY;
    private String name;
    private final Game game;
    private int maxHealth;
    private int health;
    private int defense;
    private int attack;

    // EFFECTS: Creates a generic entity with health, defence, and attack
    public Entity(
            Game game, int posX, int posY, char textSprite,
            TextColor textColor, TextColor backgroundColor,
            String name, int health, int defence, int attack
    ) {
        this.game = game;
        this.posX = posX;
        this.posY = posY;
        this.textSprite = textSprite;
        this.textColor = textColor;
        this.backgroundColor = backgroundColor;
        this.name = name;
        this.maxHealth = health;
        this.health = health;
        this.defense = defence;
        this.attack = attack;
    }

    // MODIFIES: this
    // EFFECTS: Tries to move player to new location
    //          If allowed (within bounds and no walls):
    //               Moves the player up and returns true
    //          Else:
    //               Returns False
    protected boolean handleMovement(int posX, int posY) {
        // Check if entity can go to location
        if (!canBeAtLocation(game.getLevel(), posX, posY)) {
            return false;
        }

        // Move to new location
        setPosX(posX);
        setPosY(posY);

        // Check if new location is a tile. If so, run onStep
        Tile steppedTile = game.getLevel().getTileAtLocation(getPosX(), getPosY());
        if (steppedTile != null) {
            steppedTile.onStep(this);
        }

        return true;
    }

    // MODIFIES: this
    // EFFECTS: Tries to move player up.
    public boolean moveUp() {
        return handleMovement(getPosX(), getPosY() - 1);
    }

    // MODIFIES: this
    // EFFECTS: Tries to move player down.
    public boolean moveDown() {
        return handleMovement(getPosX(), getPosY() + 1);
    }

    // MODIFIES: this
    // EFFECTS: Tries to move player left.
    public boolean moveLeft() {
        return handleMovement(getPosX() - 1, getPosY());
    }

    // MODIFIES: this
    // EFFECTS: Tries to move player right.
    public boolean moveRight() {
        return handleMovement(getPosX() + 1, getPosY());
    }

    // EFFECTS: Checks if entity can move to locaiton
    public boolean canBeAtLocation(Level level, int posX, int posY) {
        // Check if it is within bounds
        if (!(posX >= 0 && posX < level.getSizeX())) {
            return false;
        }
        if (!(posY >= 0 && posY < level.getSizeY())) {
            return false;
        }

        // Check if there is a solid tile there
        return !level.isSolidTileAtLocation(posX, posY);
    }

    // REQUIRES: amount > 0
    // MODIFIES: this
    // EFFECTS: Applies Damage to Entity
    public void damage(int amount) {
        // Ensure health does not go into the negatives
        health = Math.max(health - amount, 0);
    }

    // REQUIRES: amount > 0
    // MODIFIES: this
    // EFFECTS: Applies Healing to Entity
    public void heal(int amount) {
        // Ensure health does not go over max health
        health = Math.min(maxHealth, health + amount);
    }

    // EFFECTS: returns if entity is dead
    public boolean isDead() {
        return health == 0;
    }

    // MODIFIES: this
    // EFFECTS: Handles what happens when next tick occurs
    public abstract void handleNextTick(int tick);

    //
    // Getters
    //

    @Override
    public int getPosX() {
        return posX;
    }

    protected void setPosX(int posX) {
        this.posX = posX;
    }

    @Override
    public int getPosY() {
        return posY;
    }

    protected void setPosY(int posY) {
        this.posY = posY;
    }

    @Override
    public char getTextSprite() {
        return textSprite;
    }

    @Override
    public TextColor getTextColor() {
        return textColor;
    }

    @Override
    public TextColor getBackgroundColor() {
        return backgroundColor;
    }

    //
    // Getters and Setters
    //

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Game getGame() {
        return game;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    protected void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public int getHealth() {
        return health;
    }

    protected void setHealth(int health) {
        this.health = health;
    }

    public int getDefense() {
        return defense;
    }

    protected void setDefense(int defense) {
        this.defense = defense;
    }

    public int getAttack() {
        return attack;
    }

    protected void setAttack(int attack) {
        this.attack = attack;
    }
}
