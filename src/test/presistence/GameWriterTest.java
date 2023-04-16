package presistence;

import model.Game;
import org.junit.jupiter.api.Test;
import persistence.GameReader;
import persistence.GameWriter;
import persistence.SaveGame;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class GameWriterTest {
    @Test
    void testGameWriterInvalidFile() {
        try {
            Game game = new Game(32, 24);
            GameWriter gameWriter = new GameWriter("./data/this\0illegal:fileName.json");
            gameWriter.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testGameWriterEmptyGame() {
        try {
            // Create Save Game
            Game game = new Game(100, 100);
            GameWriter gameWriter = new GameWriter("./data/testGameWriterEmptyGame.json");
            SaveGame saveGame = gameWriter.createSaveGame(game, 64);
            gameWriter.open();
            gameWriter.write(saveGame);
            gameWriter.close();

            // Read Save Game
            GameReader gameReader = new GameReader("./data/testGameWriterEmptyGame.json");
            SaveGame newSaveGame = gameReader.read();

            // Check Basic Save Game Info
            assertEquals(saveGame.getTick(), newSaveGame.getTick());
            assertEquals(saveGame.getVersion(), newSaveGame.getVersion());

            // Check Basic Game Info
            Game newGame = newSaveGame.getGame();
            assertEquals(game.getSizeX(), newGame.getSizeX());
            assertEquals(game.getSizeY(), newGame.getSizeY());
            assertTrue(newGame.isGameRunning());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
