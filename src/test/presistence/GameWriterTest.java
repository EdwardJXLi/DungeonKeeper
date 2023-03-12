package presistence;

import model.Game;
import org.junit.jupiter.api.Test;
import persistence.GameReader;
import persistence.GameWriter;
import persistence.SaveGame;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.fail;

public class GameWriterTest {
    @Test
    void testGameWriterInvalidFile() {
        try {
            Game game = new Game(32, 48);
            GameWriter gameWriter = new GameWriter("./data/this\0illegal:fileName.json");
            gameWriter.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyWorkroom() {
        try {
            Game game = new Game(32, 48);
            SaveGame saveGame = new SaveGame(game, 64);
            GameWriter gameWriter = new GameWriter("./data/testGameWriterEmptyGame.json");
            gameWriter.open();
            gameWriter.write(saveGame);
            gameWriter.close();

            GameReader gameReader = new GameReader("./data/testGameWriterEmptyGame.json");
            SaveGame newSaveGame = gameReader.read();
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
