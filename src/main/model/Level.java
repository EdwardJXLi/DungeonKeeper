package model;

import model.tiles.Wall;

import java.util.ArrayList;

public class Level {
    // Information on level
    private int sizeX;
    private int sizeY;
    private int levelNum;

    // Level Data
    private ArrayList<Tile> tiles;

    public Level(int levelNum, int sizeX, int sizeY) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.levelNum = levelNum;
        this.tiles = new ArrayList<>();

        initializeWalls();
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

    public ArrayList<Tile> getTiles() {
        return tiles;
    }
}
