package ui.sprites;

import model.graphics.SpriteID;
import org.json.JSONObject;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class SpriteManager {
    private String texturepack;
    private Map<String, SpriteSheet> spritesheets;
    private Map<String, Sprite> sprites;

    public SpriteManager(String texturepack) {
        this.texturepack = texturepack;
        this.spritesheets = new HashMap<>();
        this.sprites = new HashMap<>();

        // Load Textures
        try {
            loadTexturePack();
        } catch (IOException e) {
            System.out.println("Failed to load texture pack: " + texturepack);
            e.printStackTrace();
        }
    }

    // MODIFIES: this
    // EFFECTS:  Reads the texture pack and loads all assets
    // REQUIRES: The texture pack is valid
    public void loadTexturePack() throws IOException {
        // Read and load the texture pack json
        String jsonData = readFile(texturepack);
        JSONObject json = new JSONObject(jsonData);

        // Load all spritesheets
        loadSpritesheets(json.getJSONObject("spritesheets"));

        // Load all sprites
        loadSprites(json.getJSONObject("sprites"));
    }

    // MODIFIES: this
    // EFFECTS:  Loads all spritesheets from the texture pack
    private void loadSpritesheets(JSONObject data) throws IOException {
        for (String name : data.keySet()) {
            // Get Information from Texture Pack
            String ssSource = data.getString(name);

            // Load Image
            BufferedImage rawImage = readImage(ssSource);

            // Load Spritesheet
            spritesheets.put(name, new SpriteSheet(rawImage));
        }
    }

    // MODIFIES: this
    // EFFECTS:  Loads all sprites from the texture pack
    private void loadSprites(JSONObject data) throws IOException {
        for (String name : data.keySet()) {
            // Get Information from Texture Pack
            JSONObject spriteData = data.getJSONObject(name);
            String source = spriteData.getString("source");
            String type = spriteData.getString("type");
            int spriteSize = spriteData.getInt("sprite_size");
            boolean transparent = spriteData.getBoolean("transparent");

            // If type is "sprite", load as usual
            if (type.equals("sprite")) {
                // Get X Y coords
                int x = spriteData.getInt("x");
                int y = spriteData.getInt("y");

                // Check if a spritesheet was referenced. Else, use as file location.
                // After that, load and initialize the sprite
                BufferedImage rawImage;
                if (spritesheets.containsKey(source)) {
                    rawImage = spritesheets.get(source).getSprite(x, y, spriteSize);
                } else {
                    rawImage = new SpriteSheet(readImage(source)).getSprite(x, y, spriteSize);
                }

                // Load the sprite
                sprites.put(name, new Sprite(rawImage, x, y, transparent));
            }
        }
    }

    // EFFECTS: Reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(contentBuilder::append);
        }

        return contentBuilder.toString();
    }

    // EFFECTS: Reads source file as BufferedImage and returns it
    private BufferedImage readImage(String source) throws IOException {
        return ImageIO.read(new File(source));
    }
}
