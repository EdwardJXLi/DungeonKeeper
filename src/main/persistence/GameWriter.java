package persistence;

import model.Game;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

/*
 * Helper object to write game data to file
 */

public class GameWriter {
    public static final String SAVE_FILE_VERSION = "debug";
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

    // EFFECTS: Returns JSON Metadata of Save File
    public JSONObject getMetadata(int tick) {
        JSONObject json = new JSONObject();
        json.put("save_version", SAVE_FILE_VERSION);
        json.put("game_tick", tick);
        return json;
    }

    // MODIFIES: this
    // EFFECTS: Writes JSON Representation of Game to file
    public void write(Game game, int tick) {
        JSONObject json = new JSONObject();
        json.put("game", game.toJson());
        json.put("metadata", getMetadata(tick));
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
