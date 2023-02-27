package model;

import com.googlecode.lanterna.TextColor;

public abstract class Entity implements ScreenElement {
    // Screen Element Info
    private final char textSprite;
    private final TextColor textColor;
    private final TextColor backgroundColor;
    private final Game game;
    private final int maxHealth;
    // Entity Information
    private int posX;
    private int posY;
    private String name;
    private int health;
    private int baseDefense;
    private int baseAttack;

    // EFFECTS: Creates a generic entity with sprites, health, defence, and attack
    public Entity(
            Game game, int posX, int posY, char textSprite,
            TextColor textColor, TextColor backgroundColor,
            String name, int health, int baseDefence, int baseAttack
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
        this.baseDefense = baseDefence;
        this.baseAttack = baseAttack;
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
    public void moveUp() {
        handleMovement(getPosX(), getPosY() - 1);
    }

    // MODIFIES: this
    // EFFECTS: Tries to move player down.
    public void moveDown() {
        handleMovement(getPosX(), getPosY() + 1);
    }

    // MODIFIES: this
    // EFFECTS: Tries to move player left.
    public void moveLeft() {
        handleMovement(getPosX() - 1, getPosY());
    }

    // MODIFIES: this
    // EFFECTS: Tries to move player right.
    public void moveRight() {
        handleMovement(getPosX() + 1, getPosY());
    }

    // EFFECTS: Checks if entity can move to location
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
        assert amount > 0;

        // Calculate damage amount based on defense
        // Ensure damage does not go into the negatives
        int damage = Math.max(0, amount - getDefense());
        // Ensure health does not go into the negatives
        health = Math.max(health - damage, 0);
    }

    // REQUIRES: amount > 0
    // MODIFIES: this
    // EFFECTS: Applies Healing to Entity
    public void heal(int amount) {
        assert amount > 0;

        // Ensure health does not go over max health
        health = Math.min(maxHealth, health + amount);
    }

    // EFFECTS: returns whether entity is dead
    public boolean isDead() {
        return health == 0;
    }

    // MODIFIES: this
    // EFFECTS: Handles what happens when next tick occurs in the game
    public abstract void handleNextTick(int tick);

    // Getters and Setters

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

    public int getHealth() {
        return health;
    }

    public int getDefense() {
        return baseDefense;
    }

    public void addDefense(int amount) {
        this.baseDefense += amount;
    }

    public int getAttack() {
        return baseAttack;
    }

    public void addAttack(int amount) {
        this.baseAttack += amount;
    }
}
