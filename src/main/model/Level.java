package model;

import model.enemies.*;
import model.items.HealingPotion;
import model.tiles.DungeonExit;
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
    public static final int TICKS_UNTIL_GUARD_SPAWN = 200;
    public static final int TICKS_UNTIL_WISP_SPAWN = 720;
    public static final int TICKS_UNTIL_HEALTH_SPAWN = 100;

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
    private final List<Decoration> decorations;
    private DungeonExit dungeonExit;

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
        this.decorations = new ArrayList<>();
    }

    // REQUIRES: Only used for loading from save.
    // EFFECTS: Sets tiles, enemies, and droppedItems.
    public void setGameElements(
            List<Tile> tiles,
            List<Enemy> enemies,
            List<DroppedItem> droppedItems,
            DungeonExit dungeonExit
    ) {
        this.tiles = tiles;
        this.enemies = enemies;
        this.droppedItems = droppedItems;
        this.dungeonExit = dungeonExit;
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

        // Spawns two wisps on the top corners of the screen
        this.spawnEnemy(new Wisp(game), sizeX - 5, 5);
        this.spawnEnemy(new Wisp(game), 5, sizeY - 5);

        // Spawn mage and vampire randomly
        this.spawnEnemy(new Vampire(game),
                game.getRandom().nextInt(sizeX - 2) + 1,
                game.getRandom().nextInt(sizeY - 2) + 1
        );
        this.spawnEnemy(new Mage(game),
                game.getRandom().nextInt(sizeX - 2) + 1,
                game.getRandom().nextInt(sizeY - 2) + 1
        );

        // Spawn Dungeon Keeper at the center of the map
        this.spawnEnemy(new DungeonKeeper(game), sizeX / 2, sizeY / 2);
    }

    // MODIFIES: this
    // EFFECTS: Adds tile to level
    public void addTile(Tile tile) {
        tiles.add(tile);
    }

    // MODIFIES: this
    // EFFECTS: Adds tile to level, making sure it doesn't overlap with other tiles
    public void addTile(Tile tile, boolean check) {
        if (!check) {
            addTile(tile);
        } else {
            // Check that tile does not overlap with other tiles
            if (getTileAtLocation(tile.getPosX(), tile.getPosY()) == null) {
                addTile(tile);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: Adds decoration to level
    public void addDecoration(Decoration decoration) {
        decorations.add(decoration);
    }

    // MODIFIES: this, p
    // EFFECTS: Sets player location to spawn location
    public void spawnPlayer(Player p) {
        p.setPosX(this.spawnX);
        p.setPosY(this.spawnY);

        // If game's player is not set, set it as this player
        if (game.getPlayer() == null) {
            game.setPlayer(p);
        }
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
        // Place Random Lines of Walls. Only place horizontal walls
        for (int i = 0; i < 8; i++) {
            int y = game.getRandom().nextInt(sizeY - 4) + 2;
            int x1 = game.getRandom().nextInt(sizeX - 4) + 2;
            int x2 = game.getRandom().nextInt(sizeX - 4) + 2;
            for (int j = Math.min(x1, x2); j < Math.max(x1, x2); j++) {
                addTile(new Wall(j, y));
            }
        }

        // Place Random Elements
        for (int y = 2; y < sizeY - 2; y++) {
            for (int x = 2; x < sizeX - 2; x++) {
                // 1 in 40 chance of solid wall,
                // 2 in 40 chance of spikes
                switch (game.getRandom().nextInt(100)) {
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

        // Generate Dungeon Exit
        generateDungeonExit();
    }

    // MODIFIES: this
    // EFFECTS: Generates dungeon exit
    private void generateDungeonExit() {
        // Generate dungeon exit in the center of the map
        int exitX = sizeX / 2;
        int exitY = sizeY / 2;

        // Remove all tiles surrounding the door
        for (int y = exitY - 1; y <= exitY + 1; y++) {
            for (int x = exitX - 1; x <= exitX + 1; x++) {
                Tile t = getTileAtLocation(x, y);
                if (t != null) {
                    tiles.remove(t);
                }
            }
        }

        // Generate Dungeon Exit
        this.dungeonExit = new DungeonExit(exitX, exitY);

        // Add the exit door
        addTile(dungeonExit, false);
    }

    // MODIFIES: this
    // EFFECTS: Sets up next tick logic
    public void handleNextTick(int tick) {
        // Every couple seconds, spawn a new guard enemy
        if (tick % TICKS_UNTIL_GUARD_SPAWN == 0) {
            this.spawnEnemy(new Guard(game),
                    game.getRandom().nextInt(sizeX - 2) + 1,
                    game.getRandom().nextInt(sizeY - 2) + 1
            );
        }

        // Every couple seconds, spawn a new wisp enemy
        if (tick % TICKS_UNTIL_WISP_SPAWN == 0) {
            this.spawnEnemy(new Wisp(game),
                    game.getRandom().nextInt(sizeX - 2) + 1,
                    game.getRandom().nextInt(sizeY - 2) + 1
            );
        }

        // Every couple seconds, spawn a health potion at a random location
        if (tick % TICKS_UNTIL_HEALTH_SPAWN == 0) {
            this.dropItem(
                    game.getRandom().nextInt(sizeX - 2) + 1,
                    game.getRandom().nextInt(sizeY - 2) + 1,
                    new HealingPotion()
            );
        }
    }

    // MODIFIES: this
    // EFFECTS: Drops item at location
    public DroppedItem dropItem(int posX, int posY, Item item) {
        // Don't drop item if there is a solid tile at the location
        if (isSolidTileAtLocation(posX, posY)) {
            return null;
        }

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

    public List<Decoration> getDecorations() {
        return decorations;
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

    public DungeonExit getDungeonExit() {
        return dungeonExit;
    }
}
