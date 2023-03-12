package persistence;

/*
 * Helper object to read game data fom file
 */

import model.*;
import model.enemies.Dummy;
import model.enemies.Guard;
import model.items.*;
import model.tiles.Trap;
import model.tiles.Wall;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/*
 * Save Game Reader Class to load Game States from JSON File
 * Code inspired by: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
 */

public class GameReader {
    private String source;
    private Game game;

    // EFFECTS: Creates a game reader that loads game saves from file
    public GameReader(String source) {
        this.source = source;
    }

    // EFFECTS: Reads Game from JSON file and returns as SaveGame;
    //          throws IOException if an error occurs reading data from file
    public SaveGame read() throws IOException {
        String jsonData = readFile(source);
        JSONObject json = new JSONObject(jsonData);
        return parseSaveGameObject(json);
    }

    // EFFECTS: Parses SaveGame Objects from JSON
    public SaveGame parseSaveGameObject(JSONObject json) {
        // Parse out metadata
        JSONObject metadata = json.getJSONObject("metadata");
        String version = metadata.getString("version");
        int tick = metadata.getInt("tick");

        // Parse out game object
        Game game = parseGameObject(json.getJSONObject("game"));

        // Create and return save game
        return new SaveGame(game, version, tick);
    }

    // EFFECTS: Parses Game Objects from JSON
    public Game parseGameObject(JSONObject json) {
        // Load Game Messages
        List<String> gameMessages = parseGameMessagesArray(json.getJSONArray("gameMessages"));

        // Load Game Object.
        // Making it a class method so that other objects from around the class can use the same
        // game object.
        this.game = new Game(
                json.getInt("sizeX"),
                json.getInt("sizeY"),
                gameMessages
        );

        // Load Player Object
        Player player;
        if (json.has("player")) {
            player = parsePlayerObject(json.getJSONObject("player"));
        } else {
            player = null;
        }

        // Load Level Object
        Level level;
        if (json.has("level")) {
            level = parseLevelObject(json.getJSONObject("level"));
        } else {
            level = null;
        }

        // Set Player and Level
        this.game.setGamePlayerAndLevel(player, level);

        return this.game;
    }

    // EFFECTS: Parses Game Message Lists from JSON Array
    public List<String> parseGameMessagesArray(JSONArray array) {
        List<String> gameMessages = new ArrayList<>();
        for (Object s: array) {
            gameMessages.add(s.toString());
        }
        return gameMessages;
    }

    // EFFECTS: Parses Player Objects from JSON
    public Player parsePlayerObject(JSONObject json) {
        // Load Player Object
        return new Player(
                game,
                json.getInt("posX"),
                json.getInt("posY"),
                json.getInt("health"),
                json.getInt("baseDefense"),
                json.getInt("baseAttack"),
                json.getInt("kills"),
                parseInventoryObject(json.getJSONObject("inventory"))
        );
    }

    // EFFECTS: Parses Inventory Objects from JSON
    public Inventory parseInventoryObject(JSONObject json) {
        // Parse Armor
        Armor armor;
        if (json.has("equippedArmor")) {
            armor = (Armor) parseItemObject(json.getJSONObject("equippedArmor"));
        } else {
            armor = null;
        }

        // Parse Weapon
        Weapon weapon;
        if (json.has("equippedWeapon")) {
            weapon = (Weapon) parseItemObject(json.getJSONObject("equippedWeapon"));
        } else {
            weapon = null;
        }

        return new Inventory(parseItemsArray(json.getJSONArray("inventory")), armor, weapon);
    }

    // EFFECTS: Parses Item Lists from JSON Arrays
    public List<Item> parseItemsArray(JSONArray array) {
        List<Item> items = new ArrayList<>();
        for (Object i: array) {
            JSONObject json = (JSONObject) i;
            items.add(parseItemObject(json));
        }
        return items;
    }

    // EFFECTS: Parses Item Objects from JSON
    public Item parseItemObject(JSONObject json) {
        String itemClass = json.getString("__type__");
        switch (itemClass) {
            case "model.items.HealingPotion":
                return new HealingPotion();
            case "model.items.StrengthPotion":
                return new StrengthPotion();
            case "model.items.DefensePotion":
                return new DefensePotion();
            case "model.items.GuardSword":
                return new GuardSword(
                        json.getInt("additionalAttack")
                );
            case "model.items.GuardArmor":
                return new GuardArmor(
                        json.getInt("additionalDefense")
                );
            default:
                throw new RuntimeException("Error While Parsing Item! Unknown Item: " + itemClass);
        }
    }

    // EFFECTS: Parses Level Objects from JSON
    public Level parseLevelObject(JSONObject json) {
        // Create a new level with existing data
        Level level = new Level(
                json.getInt("levelNum"),
                game,
                json.getInt("sizeX"),
                json.getInt("sizeY"),
                json.getInt("spawnX"),
                json.getInt("spawnY")
        );


        // Load, Parse, and Set Tile, Enemy, and Dropped Item data.
        level.setGameElements(
                parseTilesArray(json.getJSONArray("tiles")),
                parseEnemiesArray(json.getJSONArray("enemies")),
                parseDroppedItemsArray(json.getJSONArray("droppedItems"))
        );

        return level;
    }

    // EFFECTS: Parses Tile Lists from JSON Array
    public List<Tile> parseTilesArray(JSONArray array) {
        List<Tile> tiles = new ArrayList<>();
        for (Object i: array) {
            JSONObject json = (JSONObject) i;
            tiles.add(parseTileObject(json));
        }
        return tiles;
    }

    // EFFECTS: Parses Tile Objects from JSON
    public Tile parseTileObject(JSONObject json) {
        String tileClass = json.getString("__type__");
        int posX = json.getInt("posX");
        int posY = json.getInt("posY");
        switch (tileClass) {
            case "model.tiles.Wall":
                return new Wall(posX, posY);
            case "model.tiles.Trap":
                return new Trap(posX, posY);
            default:
                throw new RuntimeException("Error While Parsing Tile! Unknown Tile: " + tileClass);
        }
    }

    // EFFECTS: Parses Enemy List from JSON Array
    public List<Enemy> parseEnemiesArray(JSONArray array) {
        List<Enemy> enemies = new ArrayList<>();
        for (Object i: array) {
            JSONObject json = (JSONObject) i;
            enemies.add(parseEnemyObject(json));
        }
        return enemies;
    }

    // EFFECTS: Parses Enemy Objects from JSON
    public Enemy parseEnemyObject(JSONObject json) {
        String enemyClass = json.getString("__type__");
        int posX = json.getInt("posX");
        int posY = json.getInt("posY");
        int health = json.getInt("health");
        int defense = json.getInt("baseDefense");
        int attack = json.getInt("baseAttack");
        boolean stunned = json.getBoolean("stunned");
        switch (enemyClass) {
            case "model.enemies.Dummy":
                return new Dummy(game, posX, posY, health, defense, attack, stunned);
            case "model.enemies.Guard":
                return new Guard(game, posX, posY, health, defense, attack, stunned);
            default:
                throw new RuntimeException("Error While Parsing Enemy! Unknown Enemy: " + enemyClass);
        }
    }

    // EFFECTS: Parses DroppedItem Lists from JSON Arrays
    public List<DroppedItem> parseDroppedItemsArray(JSONArray array) {
        List<DroppedItem> droppedItems = new ArrayList<>();
        for (Object i: array) {
            JSONObject json = (JSONObject) i;
            droppedItems.add(parseDroppedItemObject(json));
        }
        return droppedItems;
    }

    // EFFECTS: Parses DroppedItem Objects from JSON
    public DroppedItem parseDroppedItemObject(JSONObject json) {
        return new DroppedItem(
                json.getInt("posX"),
                json.getInt("posY"),
                parseItemObject(json.getJSONObject("item"))
        );
    }

    // EFFECTS: Reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(contentBuilder::append);
        }

        return contentBuilder.toString();
    }
}
