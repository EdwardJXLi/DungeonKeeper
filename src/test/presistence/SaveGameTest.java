package presistence;

import model.Game;
import model.Inventory;
import model.Level;
import model.Player;
import model.tiles.DungeonExit;
import org.junit.jupiter.api.Test;
import persistence.SaveGame;

import static org.junit.jupiter.api.Assertions.*;

public abstract class SaveGameTest {
    public SaveGame saveGame;
    public SaveGame newSaveGame;
    public Game game;
    public Game newGame;

    @Test
    void testSaveGame() {
        assertEquals(saveGame.getTick(), newSaveGame.getTick());
        assertEquals(saveGame.getVersion(), newSaveGame.getVersion());
    }

    @Test
    void testGameInfo() {
        assertEquals(game.getSizeX(), newGame.getSizeX());
        assertEquals(game.getSizeY(), newGame.getSizeY());
        assertTrue(newGame.isGameRunning());
    }

    @Test
    void testGameMessages() {
        assertFalse(game.getMessages().isEmpty());
        assertEquals(game.getMessages().size(), newGame.getMessages().size());
        for (int i = 0; i < game.getMessages().size(); i++) {
            assertEquals(game.getMessages().get(i), newGame.getMessages().get(i));
        }
    }

    @Test
    void testPlayerInfo() {
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
    }

    @Test
    void testPlayerInventory() {
        Player player = game.getPlayer();
        Player newPlayer = newGame.getPlayer();

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
                    inventory.getInventoryItems().get(i).getClass(),
                    newInventory.getInventoryItems().get(i).getClass()
            );
            assertEquals(
                    inventory.getInventoryItems().get(i).getName(),
                    newInventory.getInventoryItems().get(i).getName()
            );
            assertEquals(
                    inventory.getInventoryItems().get(i).getDescription(),
                    newInventory.getInventoryItems().get(i).getDescription()
            );
        }
    }

    @Test
    void testLevel() {
        Level level = game.getLevel();
        Level newLevel = newGame.getLevel();

        assertEquals(level.getLevelNum(), newLevel.getLevelNum());
        assertEquals(level.getSizeX(), newLevel.getSizeX());
        assertEquals(level.getSizeY(), newLevel.getSizeY());
        assertEquals(level.getSpawnX(), newLevel.getSpawnX());
        assertEquals(level.getSpawnY(), newLevel.getSpawnY());
    }

    @Test
    void testLevelTiles() {
        Level level = game.getLevel();
        Level newLevel = newGame.getLevel();

        assertEquals(level.getTiles().size(), newLevel.getTiles().size());
        for (int i = 0; i < level.getTiles().size(); i++) {
            assertEquals(
                    level.getTiles().get(i).getClass(),
                    newLevel.getTiles().get(i).getClass()
            );
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
    }

    @Test
    void testLevelEnemies() {
        Level level = game.getLevel();
        Level newLevel = newGame.getLevel();

        assertEquals(level.getEnemies().size(), newLevel.getEnemies().size());
        for (int i = 0; i < level.getEnemies().size(); i++) {
            assertEquals(
                    level.getEnemies().get(i).getClass(),
                    newLevel.getEnemies().get(i).getClass()
            );
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
    }

    @Test
    void testLevelDroppedItems() {
        Level level = game.getLevel();
        Level newLevel = newGame.getLevel();

        assertEquals(level.getDroppedItems().size(), newLevel.getDroppedItems().size());
        for (int i = 0; i < level.getDroppedItems().size(); i++) {
            assertEquals(
                    level.getDroppedItems().get(i).getClass(),
                    newLevel.getDroppedItems().get(i).getClass()
            );
            assertEquals(
                    level.getDroppedItems().get(i).getItem().getClass(),
                    newLevel.getDroppedItems().get(i).getItem().getClass()
            );
            assertEquals(
                    level.getDroppedItems().get(i).getItem().getName(),
                    newLevel.getDroppedItems().get(i).getItem().getName()
            );
            assertEquals(
                    level.getDroppedItems().get(i).getItem().getDescription(),
                    newLevel.getDroppedItems().get(i).getItem().getDescription()
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
    }

    @Test
    void testDungeonExit() {
        DungeonExit exit = game.getLevel().getDungeonExit();
        DungeonExit newExit = newGame.getLevel().getDungeonExit();

        assertEquals(exit.getPosX(), newExit.getPosX());
        assertEquals(exit.getPosY(), newExit.getPosY());
    }
}
