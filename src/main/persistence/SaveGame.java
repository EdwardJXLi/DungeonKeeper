package persistence;

import model.Game;

/*
 * Envelope that contains all the necessary data to bootstrap a game from a save file.
 * This exists because external data such as game version and tick is saved outside the
 * "Game" object, so this is needed to save such info.
 */

public class SaveGame {
    private Game game;
    private String version;
    private int tick;
    // TODO: add date

    // EFFECTS: Creates a generic save game from the game object and tick
    public SaveGame(Game game, int tick) {
        this.game = game;
        this.version = Game.VERSION;
        this.tick = tick;
    }

    // EFFECTS: Creates a generic save game from the game object, version, and tick
    public SaveGame(Game game, String version, int tick) {
        this.game = game;
        this.version = version;
        this.tick = tick;
    }

    public Game getGame() {
        return game;
    }

    public String getVersion() {
        return version;
    }

    public int getTick() {
        return tick;
    }
}
