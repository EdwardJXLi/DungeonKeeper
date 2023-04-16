package model.game;

import model.logging.Event;
import model.logging.EventLog;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static java.lang.Thread.sleep;
import static org.junit.jupiter.api.Assertions.*;

public class LoggingTest {
    @BeforeEach
    public void setup() {
        EventLog.getInstance().clear();
        EventLog.getInstance().iterator().next();
    }

    @Test
    public void testGetInstance() {
        assertEquals(EventLog.getInstance(), EventLog.getInstance());
    }

    @Test
    public void testLogging() {
        EventLog log = EventLog.getInstance();
        log.logEvent(new Event("Test event"));
        log.logEvent(new Event("Test event 2"));
        log.logEvent(new Event("Test event 3"));

        Iterator iterator = log.iterator();
        iterator.next();
        assertTrue(iterator.hasNext());
        assertEquals("Test event", ((Event) iterator.next()).getDescription());
        assertTrue(iterator.hasNext());
        assertEquals("Test event 2", ((Event) iterator.next()).getDescription());
        assertTrue(iterator.hasNext());
        assertEquals("Test event 3", ((Event) iterator.next()).getDescription());
        assertFalse(iterator.hasNext());
    }

    @Test
    public void testClear() {
        EventLog log = EventLog.getInstance();
        log.logEvent(new Event("Test event"));
        log.logEvent(new Event("Test event 2"));
        log.logEvent(new Event("Test event 3"));
        log.clear();
        Iterator iterator = log.iterator();
        assertTrue(iterator.hasNext());
        assertEquals("Event log cleared.", ((Event) iterator.next()).getDescription());
        assertFalse(iterator.hasNext());
    }

    @Test
    public void testEvent() {
        Event event = new Event("Test event");
        assertEquals("Test event", event.getDescription());
        assertNotNull(event.getDate());
        assertTrue(event.toString().contains("Test event"));
    }

    @Test
    public void testEventComparison() {
        Event event1 = new Event("Test event");
        Event event2 = event1;
        Event event3 = new Event("Test event");
        Event event4 = new Event("Different event");
        assertNotEquals(event1, null);
        assertNotEquals(event1, "Test event");
        assertEquals(event1, event2);
        assertEquals(event1, event3);
        assertNotEquals(event1, event4);
        try {
            sleep(10);
        } catch (InterruptedException e) {
            fail("Failed to sleep in event test.");
        }
        Event event5 = new Event("Test event");
        assertNotEquals(event1, event5);
    }

    @Test
    public void testEventHashComparison() {
        Event event1 = new Event("Test event");
        Event event2 = event1;
        Event event3 = new Event("Test event");
        Event event4 = new Event("Different event");
        assertEquals(event1.hashCode(), event2.hashCode());
        assertEquals(event1.hashCode(), event3.hashCode());
        assertNotEquals(event1.hashCode(), event4.hashCode());
        try {
            sleep(10);
        } catch (InterruptedException e) {
            fail("Failed to sleep in event test.");
        }
        Event event5 = new Event("Test event");
        assertNotEquals(event1.hashCode(), event5.hashCode());
    }
}
