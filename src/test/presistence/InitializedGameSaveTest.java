package presistence;

import model.Game;
import org.junit.jupiter.api.BeforeEach;
import persistence.GameReader;
import persistence.GameWriter;
import persistence.SaveGame;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.fail;

public class InitializedGameSaveTest extends SaveGameTest {
    @BeforeEach
    public void setup() {
        try {
            // Create Save Game
            game = new Game(32, 24);
            game.initGame();
            GameWriter gameWriter = new GameWriter("./data/testGameWriterBasicGame.json");
            saveGame = gameWriter.createSaveGame(game, 20);
            gameWriter.open();
            gameWriter.write(saveGame);
            gameWriter.close();

            // Read Save Game
            GameReader gameReader = new GameReader("./data/testGameWriterBasicGame.json");
            newSaveGame = gameReader.read();
            newGame = newSaveGame.getGame();
        } catch (IOException e) {
            fail("IOException Raised When Not Supposed To");
        }
    }
}
