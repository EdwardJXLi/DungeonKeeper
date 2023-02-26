package model;

import model.tiles.Wall;

import java.util.ArrayList;

public class Level {
    // Information on level
    private int sizeX;
    private int sizeY;
    private int spawnX;
    private int spawnY;
    private int levelNum;

    // Level Data
    private ArrayList<Tile> tiles;

    public Level(int levelNum, int sizeX, int sizeY, int spawnX, int spawnY) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.spawnX = spawnX;
        this.spawnY = spawnY;
        this.levelNum = levelNum;
        this.tiles = new ArrayList<>();

//        initializeWalls();
    }

    public void spawnPlayer(Player p) {
        p.setPosX(this.spawnX);
        p.setPosY(this.spawnY);
    }

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

    public boolean solidTileAt(int posX, int posY) {
        for (Tile t: tiles) {
            if (t.getPosX() == posX && t.getPosY() == posY && t.isSolid()) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<Tile> getTiles() {
        return tiles;
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
