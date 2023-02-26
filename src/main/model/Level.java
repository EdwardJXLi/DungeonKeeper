package model;

import model.tiles.Wall;

import java.util.ArrayList;

public class Level {
    // Information on level
    private final int sizeX;
    private final int sizeY;
    private final int spawnX;
    private final int spawnY;
    private final int levelNum;

    // Level Data
    private final ArrayList<Tile> tiles;
    private final ArrayList<Enemy> enemies;

    // EFFECTS: Creates a level with size X and Y
    public Level(int levelNum, int sizeX, int sizeY, int spawnX, int spawnY) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.spawnX = spawnX;
        this.spawnY = spawnY;
        this.levelNum = levelNum;
        this.tiles = new ArrayList<>();
        this.enemies = new ArrayList<>();

        initializeWalls();
    }

    // MODIFIES: this, p
    // EFFECTS: Sets player location to spawn location
    public void spawnPlayer(Player p) {
        p.setPosX(this.spawnX);
        p.setPosY(this.spawnY);
    }

    // MODIFIES: this, enemy
    // EFFECTS: Spawns enemy at position
    public void spawnEnemy(Enemy e, int posX, int posY) {
        enemies.add(e);
        e.setPosX(posX);
        e.setPosY(posY);
    }

    // MODIFIES: this
    // EFFECTS: Sets up the bounding walls around the map
    private void initializeWalls() {
        // Set the top and bottom walls
        for (int x = 0; x < sizeX; x++) {
            // Add the top wall
            tiles.add(new Wall(x, 0));

            // Add the bottom wall
            tiles.add(new Wall(x, sizeY - 1));
        }

        // Set the left and right walls.
        // Starting from 1 to sizeY-1 to avoid duplication
        for (int y = 1; y < sizeY - 1; y++) {
            // Add the left wall
            tiles.add(new Wall(0, y));

            // Add the right wall
            tiles.add(new Wall(sizeX - 1, y));
        }
    }

    // EFFECTS: Returns if there is a solid tile at position
    public boolean solidTileAt(int posX, int posY) {
        for (Tile t : tiles) {
            if (t.getPosX() == posX && t.getPosY() == posY && t.isSolid()) {
                return true;
            }
        }
        return false;
    }

    //
    // Getters
    //

    public ArrayList<Tile> getTiles() {
        return tiles;
    }

    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }

    public int getSizeX() {
        return sizeX;
    }

    public int getSizeY() {
        return sizeY;
    }

    public int getLevelNum() {
        return levelNum;
    }
}
