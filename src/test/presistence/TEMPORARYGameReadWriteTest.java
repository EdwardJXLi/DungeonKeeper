package presistence;

/*
 * This is just a temporary file to play around how the game writers work.
 * Think of this as a scratchpad of ideas.
 */

import model.Game;
import org.junit.jupiter.api.Test;
import persistence.GameReader;
import persistence.GameWriter;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class TEMPORARYGameReadWriteTest {
    @Test
    void gameWriterTest() {
        try {
            Game game = new Game(32, 48);
            game.initGame();

            GameWriter gameWriter = new GameWriter("./data/temp.json");
            gameWriter.open();
            gameWriter.write(game, 10);
            gameWriter.close();

            GameReader gameReader = new GameReader("./data/temp.json");
            Game newGame = gameReader.read();
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
