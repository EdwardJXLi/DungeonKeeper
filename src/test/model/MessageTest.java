package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MessageTest {
    Game game;

    @BeforeEach
    public void setup() {
        game = new Game(32, 24);
    }

    @Test
    public void testConstructor() {
        assertTrue(game.getMessages().isEmpty());
        assertTrue(game.getLastMessages(10).isEmpty());
    }

    @Test
    public void testSendMessage() {
        game.sendMessage("Hello World!");
        assertEquals(1, game.getMessages().size());
        assertEquals("Hello World!", game.getMessages().get(0));
    }

    @Test
    public void testSendMultipleMessage() {
        game.sendMessage("Message 1");
        game.sendMessage("Message 2");
        game.sendMessage("Message 3");
        assertEquals(3, game.getMessages().size());
        assertEquals("Message 1", game.getMessages().get(0));
        assertEquals("Message 2", game.getMessages().get(1));
        assertEquals("Message 3", game.getMessages().get(2));
    }

    @Test
    public void testGetLastMessagesExactSize() {
        game.sendMessage("Message 1");
        game.sendMessage("Message 2");
        game.sendMessage("Message 3");
        assertEquals(3, game.getLastMessages(3).size());
        assertEquals("Message 1", game.getMessages().get(0));
        assertEquals("Message 2", game.getMessages().get(1));
        assertEquals("Message 3", game.getMessages().get(2));
    }

    @Test
    public void testGetLastMessagesLargerSize() {
        game.sendMessage("Message 1");
        game.sendMessage("Message 2");
        game.sendMessage("Message 3");
        assertEquals(3, game.getLastMessages(5).size());
        assertEquals("Message 1", game.getMessages().get(0));
        assertEquals("Message 2", game.getMessages().get(1));
        assertEquals("Message 3", game.getMessages().get(2));
    }

    @Test
    public void testGetLastMessagesSmallerSize() {
        game.sendMessage("Message 1");
        game.sendMessage("Message 2");
        game.sendMessage("Message 3");
        assertEquals(2, game.getLastMessages(2).size());
        assertEquals("Message 2", game.getMessages().get(0));
        assertEquals("Message 3", game.getMessages().get(1));
    }

    @Test
    public void testGameInit() {
        game.initGame();
        // Check if all messages are sent
        assertFalse(game.getMessages().isEmpty());
        assertEquals(6, game.getMessages().size());

        // Check if last message is welcome message
        assertEquals(game.WELCOME_MESSAGE, game.getMessages().get(game.getMessages().size() - 1));
    }
}
