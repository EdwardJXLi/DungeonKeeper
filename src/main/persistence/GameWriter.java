package persistence;

import model.Game;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

/*
 * Save Game Writer Class to save Game States to JSON File
 * Code inspired by: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
 */

public class GameWriter {
    private static final int TAB = 4;
    private final String destination;
    private PrintWriter writer;

    // EFFECTS: Creates a game writer with a destination
    public GameWriter(String destination) {
        this.destination = destination;
    }

    // MODIFIES: this
    // EFFECTS: Opens writer
    //          => throws FileNotFoundException if destination file cannot be opened for writing
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    // EFFECTS: Creates SaveGame from Game and Tick
    public SaveGame createSaveGame(Game game, int tick) {
        return new SaveGame(game, Game.VERSION, tick);
    }

    // EFFECTS: Returns JSON Metadata of Save File
    public JSONObject getMetadata(SaveGame saveGame) {
        JSONObject json = new JSONObject();
        json.put("version", saveGame.getVersion());
        json.put("tick", saveGame.getTick());
        return json;
    }

    // MODIFIES: this
    // EFFECTS: Writes JSON Representation of Game to file
    public void write(SaveGame saveGame) {
        JSONObject json = new JSONObject();
        json.put("game", saveGame.getGame().toJson());
        json.put("metadata", getMetadata(saveGame));
        saveToFile(json.toString(TAB));
    }

    // MODIFIES: this
    // EFFECTS: Closes Writer
    public void close() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: Writes JSON to File
    private void saveToFile(String json) {
        writer.print(json);
    }
}
