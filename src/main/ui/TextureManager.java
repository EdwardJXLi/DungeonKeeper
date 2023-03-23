package ui;

import model.graphics.SpriteID;
import org.json.JSONArray;
import org.json.JSONObject;
import ui.sprites.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class TextureManager {
    private static final String DEFAULT_FONT = "FONT_DEFAULT";
    // Base TextureManager Information
    private String texturepack;
    private final double scale;

    // Sprite Information
    private Map<String, SpriteSheet> spritesheets;
    private Map<String, Sprite> sprites;
    private final int spriteSize;

    // Font Information
    private Map<String, Font> fonts;

    public TextureManager(String texturepack, double scale, int spriteSize) {
        this.texturepack = texturepack;
        this.scale = scale;

        this.spritesheets = new HashMap<>();
        this.sprites = new HashMap<>();
        this.spriteSize = spriteSize;

        this.fonts = new HashMap<>();

        // Load Textures
        try {
            loadTexturePack();
        } catch (IOException e) {
            System.out.println("Failed to load texture pack: " + texturepack);
            e.printStackTrace();
        }

        // Initialize Missing
        sprites.put(
                "MISSING", new SingleSprite(
                generateMissingImage(
                        getSpriteSize(),
                        getSpriteSize()
                ), false
            )
        );

        // Initialize Sprites
        initializeSprites();
    }

    // EFFECTS: Initializes All Sprites
    private void initializeSprites() {
        for (Sprite sprite : sprites.values()) {
            sprite.initialize(this);
        }
    }

    // EFFECTS: Returns the sprite with the given ID
    public Sprite getSprite(SpriteID id) {
        if (sprites.containsKey(id.toString())) {
            return sprites.get(id.toString());
        } else {
            return sprites.get("MISSING");
        }
    }

    // EFFECTS: Returns sprits as a list
    //          If a SingleSprite is passed, it is the only element in the list
    //          If a SpriteCollection is passed, it is the list of sprites in the collection
    public ArrayList<Sprite> getSpriteList(SpriteID id) {
        ArrayList<Sprite> spriteList = new ArrayList<>();
        Sprite sprite;
        if (sprites.containsKey(id.toString())) {
            sprite = sprites.get(id.toString());
        } else {
            sprite = sprites.get("MISSING");
        }

        if (sprite instanceof SingleSprite) {
            spriteList.add(sprite);
        } else if (sprite instanceof SpriteCollection) {
            spriteList.addAll(((SpriteCollection) sprite).getSprites());
        }
        return spriteList;
    }

    // EFFECTS: Generates Missing Image for Missing Sprites and Textures
    private BufferedImage generateMissingImage(int sizeX, int sizeY) {
        BufferedImage image = new BufferedImage(
                sizeX, sizeY, BufferedImage.TYPE_INT_ARGB
        );
        Graphics2D g = image.createGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(
                0, 0,
                sizeX, sizeY
        );
        g.setColor(Color.MAGENTA);
        g.fillRect(
                0, 0,
                sizeX / 2, sizeY / 2
        );
        g.fillRect(
                sizeX / 2, sizeY / 2,
                sizeX, sizeY
        );
        return image;
    }

    // EFFECTS: Returns given font in given size
    public Font getFont(String font, int size) {
        if (fonts.containsKey(font)) {
            return fonts.get(font).deriveFont((float) (size * scale));
        } else {
            return fonts.get(DEFAULT_FONT).deriveFont((float) (size * scale));
        }
    }

    // EFFECTS: Returns hte default font in given size
    public Font getFont(int size) {
        return getFont(DEFAULT_FONT, size);
    }

    // MODIFIES: this
    // EFFECTS:  Reads the texture pack and loads all assets
    // REQUIRES: The texture pack is valid
    public void loadTexturePack() throws IOException {
        // Read and load the texture pack json
        String jsonData = readFile(texturepack);
        JSONObject json = new JSONObject(jsonData);

        // Load all (useful) metdata
        loadMetadata(json.getJSONObject("metadata"));

        // Load all spritesheets
        loadSpritesheets(json.getJSONObject("spritesheets"));

        // Load all sprites
        loadSprites(json.getJSONObject("sprites"));

        // Load all fonts
        loadFonts(json.getJSONObject("fonts"));
    }

    // MODIFIES: this
    // EFFECTS:  Loads all metadata from the texture pack
    private void loadMetadata(JSONObject data) {
        // TODO: do nothing for now.
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
            String type = spriteData.getString("type");

            // If type is "sprite", load as usual
            switch (type) {
                case "sprite":
                    sprites.put(name, parseSprite(name, spriteData));
                    break;
                case "varied":
                    JSONArray variedSpriteList = spriteData.getJSONArray("sprites");
                    sprites.put(name, new VariedSprite(parseSpriteList(name, variedSpriteList)));
                    break;
                case "animated":
                    JSONArray animatedSpriteList = spriteData.getJSONArray("sprites");
                    int frameTime = spriteData.getInt("frame_time");
                    sprites.put(name, new AnimatedSprite(parseSpriteList(name, animatedSpriteList), frameTime));
                    break;
                default:
                    throw new RuntimeException("Unknown Sprite Type: " + type);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS:  Loads all fonts from the texture pack
    private void loadFonts(JSONObject data) throws IOException {
        for (String name : data.keySet()) {
            System.out.println("Loading Font: " + name);
            // Get Information from Texture Pack
            JSONObject fontData = data.getJSONObject(name);
            String fontSource = fontData.getString("source");

            // Load Font
            Font font;
            try {
                font = Font.createFont(Font.TRUETYPE_FONT, new File(fontSource));
            } catch (FontFormatException e) {
                throw new IOException(e);
            }
            System.out.println(fonts);
            fonts.put(name, font);
            System.out.println(fonts);
        }
    }

    // EFFECTS: Takes in a List of JSON Sprite Data and Returns list of Sprite Objects
    private ArrayList<Sprite> parseSpriteList(String name, JSONArray data) throws IOException {
        ArrayList<Sprite> spriteList = new ArrayList<>();

        for (int i = 0; i < data.length(); i++) {
            spriteList.add(parseSprite(name + "_" + i, data.getJSONObject(i)));
        }
        return spriteList;
    }

    // EFFECTS: Takes in JSON Sprite data and returns Sprite Object
    private Sprite parseSprite(String name, JSONObject data) throws IOException {
        System.out.println("Loading Sprite: " + name);
        // Get Sprite Information
        String source = data.getString("source");
        int spriteSize = data.getInt("sprite_size");
        boolean transparent = data.getBoolean("transparent");
        int x = data.getInt("x");
        int y = data.getInt("y");

        // Check if a spritesheet was referenced. Else, use as file location.
        // After that, load and initialize the sprite
        BufferedImage rawImage;
        if (spritesheets.containsKey(source)) {
            rawImage = spritesheets.get(source).getSprite(x, y, spriteSize);
        } else {
            rawImage = new SpriteSheet(readImage(source)).getSprite(x, y, spriteSize);
        }

        // Load the sprite
        return new SingleSprite(rawImage, transparent);
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
        System.out.println("Reading Source: " + source);
        return ImageIO.read(new File(source));
    }

    // EFFECTS: Resizes sprite with new size
    public static BufferedImage resize(BufferedImage img, int newW, int newH, int scaleType) {
        Image tmp = img.getScaledInstance(newW, newH, scaleType);
        BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = dimg.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();

        return dimg;
    }

    // EFFECTS: Returns the calculated sprite size
    public int getSpriteSize() {
        return spriteSize;
    }

    // EFFECTS: Returns the scale
    public double getScale() {
        return scale;
    }
}
