package model;

import com.googlecode.lanterna.TextColor;

public abstract class Entity implements ScreenElement {
    // Entity Information
    private int posX;
    private int posY;
    private final int maxHealth;
    private int health;
    private int defence;
    private int attack;

    // Screen Element Info
    private final char textSprite;
    private final TextColor textColor;

    // EFFECTS: Creates a generic entity with health, defence, and attack
    public Entity(int posX, int posY, char textSprite, TextColor textColor, int maxHealth, int defence, int attack) {
        this.posX = posX;
        this.posY = posY;
        this.textSprite = textSprite;
        this.textColor = textColor;

        // Set max health and initial health to both maxHealth
        this.maxHealth = maxHealth;
        this.health = maxHealth;

        // Set rest of entity values
        this.defence = defence;
        this.attack = attack;
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
        return !level.solidTileAt(posX, posY);
    }

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
        return TextColor.ANSI.DEFAULT;
    }

    //
    // Getters and Setters
    //

    public int getMaxHealth() {
        return maxHealth;
    }

    public int getHealth() {
        return health;
    }

    protected void setHealth(int health) {
        this.health = health;
    }

    public int getDefence() {
        return defence;
    }

    protected void setDefence(int defence) {
        this.defence = defence;
    }

    public int getAttack() {
        return attack;
    }

    protected void setAttack(int attack) {
        this.attack = attack;
    }
}
