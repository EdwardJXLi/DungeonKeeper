package presistence;

import model.Game;
import org.junit.jupiter.api.Test;
import persistence.GameReader;
import persistence.SaveGame;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class GameReaderTest {
    @Test
    void testReaderNonExistentFile() {
        GameReader gameReader = new GameReader("./data/noSuchFile.json");
        try {
            SaveGame saveGame = gameReader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testGameReaderEmptyGame() {
        try {
            // Read Save Game
            GameReader gameReader = new GameReader("./data/testGameReaderEmptyGame.json");
            SaveGame newSaveGame = gameReader.read();

            // Check Basic Save Game Info
            assertEquals(0, newSaveGame.getTick());
            assertEquals(Game.VERSION, newSaveGame.getVersion());

            // Check Basic Game Info
            Game newGame = newSaveGame.getGame();
            assertEquals(32, newGame.getSizeX());
            assertEquals(24, newGame.getSizeY());
            assertTrue(newGame.isGameRunning());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
