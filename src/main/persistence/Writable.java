package persistence;

import org.json.JSONObject;

/*
 * Interface for anything that can be saved as JSON.
 */

public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
