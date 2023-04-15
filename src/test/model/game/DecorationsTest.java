package model.game;

import model.Game;
import model.Level;
import model.decorations.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DecorationsTest {
    Game game;
    Level level;

    @BeforeEach
    public void setup() {
        game = new Game(32, 24);
        level = new Level(11, game, 32, 24, 1, 2);
    }

    @Test
    public void testSpawnFancyWall() {
        level.addDecoration(new FancyWall(0, 0, FancyWall.FancyWallType.TOP_LEFT));
        level.addDecoration(new FancyWall(0, 1, FancyWall.FancyWallType.TOP_RIGHT));
        level.addDecoration(new FancyWall(0, 2, FancyWall.FancyWallType.BOTTOM_LEFT));
        level.addDecoration(new FancyWall(0, 3, FancyWall.FancyWallType.BOTTOM_RIGHT));
        level.addDecoration(new FancyWall(0, 4, FancyWall.FancyWallType.TOP));
        level.addDecoration(new FancyWall(0, 5, FancyWall.FancyWallType.BOTTOM));
        level.addDecoration(new FancyWall(0, 6, FancyWall.FancyWallType.LEFT));
        level.addDecoration(new FancyWall(0, 7, FancyWall.FancyWallType.RIGHT));
        assertEquals(8, level.getDecorations().size());
    }

    @Test
    public void testSpawnTorch() {
        level.addDecoration(new Torch(0, 0, Torch.TorchType.CENTER));
        level.addDecoration(new Torch(0, 1, Torch.TorchType.LEFT));
        level.addDecoration(new Torch(0, 2, Torch.TorchType.RIGHT));
        assertEquals(3, level.getDecorations().size());
    }

    @Test
    public void testSpawnWeb() {
        level.addDecoration(new Web(0, 0, Web.WebType.TYPE1_LEFT));
        level.addDecoration(new Web(0, 1, Web.WebType.TYPE1_RIGHT));
        level.addDecoration(new Web(0, 2, Web.WebType.TYPE2_LEFT));
        level.addDecoration(new Web(0, 3, Web.WebType.TYPE2_RIGHT));
        assertEquals(4, level.getDecorations().size());
    }

    @Test
    public void testSpawnBone() {
        level.addDecoration(new Bone(0, 0, Bone.BoneType.NORMAL));
        level.addDecoration(new Bone(0, 1, Bone.BoneType.SKULL));
        assertEquals(2, level.getDecorations().size());
    }

    @Test
    public void testSpawnBanner() {
        level.addDecoration(new Banner(0, 0));
        assertEquals(1, level.getDecorations().size());
    }

    @Test
    public void testSpawnChain() {
        level.addDecoration(new Chain(0, 0, Chain.ChainType.TYPE1));
        level.addDecoration(new Chain(0, 1, Chain.ChainType.TYPE2));
        assertEquals(2, level.getDecorations().size());
    }

    @Test
    public void testSpawnRock() {
        level.addDecoration(new Rock(0, 0, Rock.RockType.BIG));
        level.addDecoration(new Rock(0, 1, Rock.RockType.SMALL));
        assertEquals(2, level.getDecorations().size());
    }
}
