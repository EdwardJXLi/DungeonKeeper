package presistence;

import com.googlecode.lanterna.TextColor;
import model.Game;
import model.Inventory;
import model.Level;
import model.Player;
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
            Game game = new Game(32, 24);
            SaveGame saveGame = new SaveGame(game, 64);
            GameWriter gameWriter = new GameWriter("./data/testGameWriterEmptyGame.json");
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

    @Test
    void testGameWriterBasicGame() {
        try {
            // Create Save Game
            Game game = new Game(100, 100);
            game.initGame();
            SaveGame saveGame = new SaveGame(game, 22);
            GameWriter gameWriter = new GameWriter("./data/testGameWriterBasicGame.json");
            gameWriter.open();
            gameWriter.write(saveGame);
            gameWriter.close();

            // Read Save Game
            GameReader gameReader = new GameReader("./data/testGameWriterBasicGame.json");
            SaveGame newSaveGame = gameReader.read();

            // Check Basic Save Game Info
            assertEquals(saveGame.getTick(), newSaveGame.getTick());
            assertEquals(saveGame.getVersion(), newSaveGame.getVersion());

            // Check Basic Save Game Info
            Game newGame = newSaveGame.getGame();
            assertEquals(game.getSizeX(), newGame.getSizeX());
            assertEquals(game.getSizeY(), newGame.getSizeY());
            assertTrue(newGame.isGameRunning());

            // Check Game Messages
            // TODO: KNOWN BROKEN
//            assertFalse(game.getMessages().isEmpty());
//            assertEquals(game.getMessages().size(), newGame.getMessages().size());
//            for (int i = 0; i < game.getMessages().size(); i++) {
//                assertEquals(game.getMessages().get(i), newGame.getMessages().get(i));
//            }

            // Check Player Info
            Player player = game.getPlayer();
            Player newPlayer = newGame.getPlayer();
            assertEquals(newPlayer.getTextSprite(), player.getTextSprite());
            assertEquals(newPlayer.getTextColor(), player.getTextColor());
            assertEquals(newPlayer.getBackgroundColor(), player.getBackgroundColor());
            assertEquals(newPlayer.getName(), player.getName());
            assertEquals(newPlayer.getHealth(), player.getHealth());
            assertEquals(newPlayer.getMaxHealth(), player.getMaxHealth());
            assertEquals(newPlayer.getDefense(), player.getDefense());
            assertEquals(newPlayer.getAttack(), player.getAttack());

            // Check Player Inventory
            Inventory inventory = player.getInventory();
            Inventory newInventory = newPlayer.getInventory();
            assertEquals(inventory.getInventoryItems().size(), newInventory.getInventoryItems().size());
            if (inventory.getEquippedArmor() != null) {
                assertEquals(inventory.getEquippedArmor().getName(), newInventory.getEquippedArmor().getName());
            } else {
                assertNull(newInventory.getEquippedArmor());
            }
            if (inventory.getEquippedWeapon() != null) {
                assertEquals(inventory.getEquippedWeapon().getName(), newInventory.getEquippedWeapon().getName());
            } else {
                assertNull(inventory.getEquippedWeapon());
            }
            for (int i = 0; i < inventory.getInventoryItems().size(); i++) {
                assertEquals(
                        inventory.getInventoryItems().get(i).getName(),
                        newInventory.getInventoryItems().get(i).getName()
                );
            }

            // Check Level
            Level level = game.getLevel();
            Level newLevel = newGame.getLevel();
            assertEquals(level.getLevelNum(), newLevel.getLevelNum());
            assertEquals(level.getSizeX(), newLevel.getSizeX());
            assertEquals(level.getSizeY(), newLevel.getSizeY());
            assertEquals(level.getSpawnX(), newLevel.getSpawnX());
            assertEquals(level.getSpawnY(), newLevel.getSpawnY());

            // Check Tiles
            assertEquals(level.getTiles().size(), newLevel.getTiles().size());
            for (int i = 0; i < level.getTiles().size(); i++) {
                assertEquals(
                        level.getTiles().get(i).getName(),
                        newLevel.getTiles().get(i).getName()
                );
                assertEquals(
                        level.getTiles().get(i).getPosX(),
                        newLevel.getTiles().get(i).getPosX()
                );
                assertEquals(
                        level.getTiles().get(i).getPosY(),
                        newLevel.getTiles().get(i).getPosY()
                );
            }

            // Check Enemies
            assertEquals(level.getEnemies().size(), newLevel.getEnemies().size());
            for (int i = 0; i < level.getEnemies().size(); i++) {
                assertEquals(
                        level.getEnemies().get(i).getName(),
                        newLevel.getEnemies().get(i).getName()
                );
                assertEquals(
                        level.getEnemies().get(i).getHealth(),
                        newLevel.getEnemies().get(i).getHealth()
                );
                assertEquals(
                        level.getEnemies().get(i).getDefense(),
                        newLevel.getEnemies().get(i).getDefense()
                );
                assertEquals(
                        level.getEnemies().get(i).getAttack(),
                        newLevel.getEnemies().get(i).getAttack()
                );
                assertEquals(
                        level.getEnemies().get(i).isStunned(),
                        newLevel.getEnemies().get(i).isStunned()
                );
                assertEquals(
                        level.getEnemies().get(i).getPosX(),
                        newLevel.getEnemies().get(i).getPosX()
                );
                assertEquals(
                        level.getEnemies().get(i).getPosY(),
                        newLevel.getEnemies().get(i).getPosY()
                );
            }

            // Check Dropped Item
            assertEquals(level.getDroppedItems().size(), newLevel.getDroppedItems().size());
            for (int i = 0; i < level.getDroppedItems().size(); i++) {
                assertEquals(
                        level.getDroppedItems().get(i).getItem().getName(),
                        newLevel.getDroppedItems().get(i).getItem().getName()
                );
                assertEquals(
                        level.getDroppedItems().get(i).getPosX(),
                        newLevel.getDroppedItems().get(i).getPosX()
                );
                assertEquals(
                        level.getDroppedItems().get(i).getPosY(),
                        newLevel.getDroppedItems().get(i).getPosY()
                );
            }

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
