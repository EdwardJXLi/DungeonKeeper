package presistence;

import model.Enemy;
import model.Game;
import model.enemies.*;
import model.items.*;
import model.tiles.DungeonExit;
import model.tiles.SoulFire;
import model.tiles.Trap;
import model.tiles.Wall;
import org.junit.jupiter.api.BeforeEach;
import persistence.GameReader;
import persistence.GameWriter;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.fail;

public class FullGameSaveTest extends SaveGameTest {
    @BeforeEach
    public void setup() {
        try {
            // Create Full Game, with all enemies and items
            game = new Game(32, 24);
            game.initGame();

            // Add a couple of new messages
            game.sendMessage("TestMessage 1");
            game.sendMessage("TestMessage 2");
            game.sendMessage("TestMessage 3");
            game.sendMessage("TestMessage 4");

            // Change player attributes
            game.getPlayer().damage(18);
            game.getPlayer().setCoordinate(10, 20);

            // Add new items to player inventory
            game.getPlayer().addItem(new HealingPotion());
            game.getPlayer().addItem(new StrengthPotion());
            game.getPlayer().addItem(new DefensePotion());
            game.getPlayer().addItem(new GuardArmor(11));
            game.getPlayer().addItem(new GuardSword(22));
            game.getPlayer().addItem(new GuardArmor(33));
            game.getPlayer().addItem(new GuardArmor(44));
            game.getPlayer().addItem(new MageRobe());
            game.getPlayer().addItem(new VampireFangs());
            game.getPlayer().addItem(new Ectoplasm());
            game.getPlayer().addItem(new DungeonKey());
            game.getPlayer().getInventory().setEquippedWeapon(new GuardSword(100));
            game.getPlayer().getInventory().setEquippedArmor(new GuardArmor(100));

            // Add new tiles to map
            game.getLevel().addTile(new Wall(1, 1));
            game.getLevel().addTile(new Trap(2, 1));
            game.getLevel().addTile(new SoulFire(3, 1));
            game.getLevel().addTile(new DungeonExit(4, 1));

            // Add new enemies to map
            game.getLevel().spawnEnemy(new Dummy(game), 2, 1);
            game.getLevel().spawnEnemy(new Guard(game), 2, 2);
            game.getLevel().spawnEnemy(new Mage(game), 2, 3);
            game.getLevel().spawnEnemy(new Vampire(game), 2, 4);
            game.getLevel().spawnEnemy(new Wisp(game), 2, 5);
            game.getLevel().spawnEnemy(new Whisper(game), 2, 6);
            game.getLevel().spawnEnemy(new Fire(game), 2, 7);
            game.getLevel().spawnEnemy(new DungeonKeeper(game), 2, 8);
            Enemy stunnedEnemy = new Guard(game);
            stunnedEnemy.stun();
            game.getLevel().spawnEnemy(stunnedEnemy, 2, 3);

            // Add dropped items
            game.getLevel().dropItem(1, 3, new HealingPotion());
            game.getLevel().dropItem(2, 3, new StrengthPotion());
            game.getLevel().dropItem(3, 3, new DefensePotion());
            game.getLevel().dropItem(4, 3, new GuardArmor(12));
            game.getLevel().dropItem(5, 3, new GuardSword(234));
            game.getLevel().dropItem(6, 3, new GuardArmor(3456));
            game.getLevel().dropItem(7, 3, new GuardArmor(45678));
            game.getLevel().dropItem(8, 3, new MageRobe());
            game.getLevel().dropItem(9, 3, new VampireFangs());
            game.getLevel().dropItem(10, 3, new Ectoplasm());
            game.getLevel().dropItem(11, 3, new DungeonKey());

            // Create Save Game
            GameWriter gameWriter = new GameWriter("./data/testGameWriterFullGame.json");
            saveGame = gameWriter.createSaveGame(game, 20);
            gameWriter.open();
            gameWriter.write(saveGame);
            gameWriter.close();

            // Read Save Game
            GameReader gameReader = new GameReader("./data/testGameWriterFullGame.json");
            newSaveGame = gameReader.read();
            newGame = newSaveGame.getGame();
        } catch (IOException e) {
            fail("IOException Raised When Not Supposed To");
        }
    }
}
