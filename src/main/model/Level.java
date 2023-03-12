package model;

import model.enemies.Guard;
import model.tiles.Trap;
import model.tiles.Wall;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

/*
 * Main class for all components relating to a game level
 */

public class Level implements Writable {
    // Level Constants
    public static final int INITIAL_GUARDS_SPAWN = 10;

    // Information on level
    private final Game game;
    private final int sizeX;
    private final int sizeY;
    private final int spawnX;
    private final int spawnY;
    private final int levelNum;

    // Level Data
    private List<Tile> tiles;
    private List<Enemy> enemies;
    private List<DroppedItem> droppedItems;

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

    // REQUIRES: Only used for loading from save.
    // EFFECTS: Sets tiles, enemies, and droppedItems.
    public void setGameElements(
            List<Tile> tiles,
            List<Enemy> enemies,
            List<DroppedItem> droppedItems
    ) {
        this.tiles = tiles;
        this.enemies = enemies;
        this.droppedItems = droppedItems;
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

    // MODIFIES: this
    // EFFECTS: Adds tile to level
    public void addTile(Tile tile) {
        tiles.add(tile);
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
            addTile(new Wall(x, 0));

            // Add the bottom wall
            addTile(new Wall(x, sizeY - 1));
        }

        // Set the left and right walls.
        // Starting from 1 to sizeY-1 to avoid duplication
        for (int y = 1; y < sizeY - 1; y++) {
            // Add the left wall
            addTile(new Wall(0, y));

            // Add the right wall
            addTile(new Wall(sizeX - 1, y));
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
                        addTile(new Wall(x, y));
                        break;
                    case 1:
                    case 2:
                        addTile(new Trap(x, y));
                        break;
                }
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: Drops item at location
    public DroppedItem dropItem(int posX, int posY, Item item) {
        game.sendMessage(String.format("%s was dropped!", item.getName()));
        DroppedItem di = new DroppedItem(posX, posY, item);
        // Adding at zero to ensure items are like a stack
        droppedItems.add(0, di);
        return di;
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

    // EFFECTS: Returns JSON Representation of Level object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("sizeX", sizeX);
        json.put("sizeY", sizeY);
        json.put("spawnX", spawnX);
        json.put("spawnY", spawnY);
        json.put("levelNum", levelNum);

        json.put("tiles", tilesToJson());
        json.put("enemies", enemiesToJson());
        json.put("droppedItems", droppedItemsToJson());

        return json;
    }

    // EFFECTS: Returns JSON Array Representation of tiles
    public JSONArray tilesToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Tile t : tiles) {
            jsonArray.put(t.toJson());
        }
        return jsonArray;
    }

    // EFFECTS: Returns JSON Array Representation of enemies
    public JSONArray enemiesToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Enemy e : enemies) {
            jsonArray.put(e.toJson());
        }
        return jsonArray;
    }

    // EFFECTS: Returns JSON Array Representation of dropped items
    public JSONArray droppedItemsToJson() {
        JSONArray jsonArray = new JSONArray();
        for (DroppedItem di : droppedItems) {
            jsonArray.put(di.toJson());
        }
        return jsonArray;
    }

    //
    // Getters, Setters and Adders
    //

    public List<Tile> getTiles() {
        return tiles;
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }

    public List<DroppedItem> getDroppedItems() {
        return droppedItems;
    }

    public int getSizeX() {
        return sizeX;
    }

    public int getSizeY() {
        return sizeY;
    }

    public int getSpawnX() {
        return spawnX;
    }

    public int getSpawnY() {
        return spawnY;
    }

    public int getLevelNum() {
        return levelNum;
    }
}
