package model;

import com.googlecode.lanterna.TextColor;

public abstract class Entity implements ScreenElement {
    private int posX;
    private int posY;
    private int maxHealth;
    private int health;
    private int defence;
    private int attack;

    private char textSprite;
    private TextColor textColor;

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

    @Override
    public int getPosX() {
        return posX;
    }

    @Override
    public int getPosY() {
        return posY;
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

    public boolean canBeAtLocation(Level level, int posX, int posY) {
        // Check if it is within bounds
        if (!(posX >= 0 && posX < level.getSizeX())) {
            return false;
        }
        if (!(posY >= 0 && posY < level.getSizeY())) {
            return false;
        }

        // Check if there is a solid tile there
        if (level.solidTileAt(posX, posY)) {
            return false;
        }

        return true;
    }

    protected void setPosX(int posX) {
        this.posX = posX;
    }

    protected void setPosY(int posY) {
        this.posY = posY;
    }

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
