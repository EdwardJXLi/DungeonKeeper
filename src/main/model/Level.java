package model;

import model.enemies.Guard;
import model.tiles.Trap;
import model.tiles.Wall;

import java.util.ArrayList;

public class Level {
    // Level Constants
    private static final int INITIAL_GUARDS_SPAWN = 10;

    // Information on level
    private final Game game;
    private final int sizeX;
    private final int sizeY;
    private final int spawnX;
    private final int spawnY;
    private final int levelNum;

    // Level Data
    private final ArrayList<Tile> tiles;
    private final ArrayList<Enemy> enemies;
    private final ArrayList<DroppedItem> droppedItems;

    // EFFECTS: Creates a level with size X and Y
    public Level(int levelNum, Game game, int sizeX, int sizeY, int spawnX, int spawnY) {
        this.game = game;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.spawnX = spawnX;
        this.spawnY = spawnY;
        this.levelNum = levelNum;
        this.tiles = new ArrayList<>();
        this.enemies = new ArrayList<>();
        this.droppedItems = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: Generates map and spawns initial batch of enemies
    public void initLevel() {
        // Populate the level
        initializeWalls();
        generateRandomMap();

        // Spawn random guards around the map
        for (int i = 0; i < INITIAL_GUARDS_SPAWN; i++) {
            this.spawnEnemy(new Guard(game),
                    game.getRandom().nextInt(sizeX - 2) + 1,
                    game.getRandom().nextInt(sizeY - 2) + 1
            );
        }
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

    // MODIFIES: this
    // EFFECTS: Places random walls and traps to decorate map
    private void generateRandomMap() {
        for (int y = 1; y < sizeY - 1; y++) {
            for (int x = 1; x < sizeX - 1; x++) {
                // 1 in 40 chance of solid wall,
                // 2 in 40 chance of spikes
                switch (game.getRandom().nextInt(40)) {
                    case 0:
                        tiles.add(new Wall(x, y));
                        break;
                    case 1:
                    case 2:
                        tiles.add(new Trap(x, y));
                        break;
                }
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: Drops item at location
    public void dropItem(int posX, int posY, Item item) {
        game.sendMessage(String.format("%s was dropped!", item.getName()));
        droppedItems.add(new DroppedItem(posX, posY, item));
    }

    // REQUIRES: there must actually be a dropped item at the location
    // MODIFIES: this
    // EFFECTS: Removes dropped item at location
    public void removeDroppedItem(DroppedItem item) {
        droppedItems.removeIf(i -> i == item);
    }

    // EFFECTS: Returns if there is a solid tile at position
    public boolean isSolidTileAtLocation(int posX, int posY) {
        for (Tile t : tiles) {
            if (t.getPosX() == posX && t.getPosY() == posY && t.isSolid()) {
                return true;
            }
        }
        return false;
    }

    // EFFECTS: Returns tile at location, null otherwise
    public Tile getTileAtLocation(int posX, int posY) {
        for (Tile t : tiles) {
            if (t.getPosX() == posX && t.getPosY() == posY) {
                return t;
            }
        }
        return null;
    }

    // EFFECTS: Returns enemy at location, null otherwise
    public Enemy getEnemyAtLocation(int posX, int posY) {
        for (Enemy e : enemies) {
            if (e.getPosX() == posX && e.getPosY() == posY) {
                return e;
            }
        }
        return null;
    }

    // EFFECTS: Returns dropped items at location, null otherwise
    public DroppedItem getDroppedItemAtLocation(int posX, int posY) {
        for (DroppedItem i : droppedItems) {
            if (i.getPosX() == posX && i.getPosY() == posY) {
                return i;
            }
        }
        return null;
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

    public ArrayList<DroppedItem> getDroppedItems() {
        return droppedItems;
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
