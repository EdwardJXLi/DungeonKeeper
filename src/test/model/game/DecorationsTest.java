package model.game;

import com.googlecode.lanterna.TextColor;
import model.Game;
import model.Level;
import model.decorations.*;
import model.graphics.SpriteID;
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
    public void testFancyWallConstructor() {
        FancyWall fancyWall = new FancyWall(1, 2, FancyWall.FancyWallType.TOP_LEFT);
        assertEquals(1, fancyWall.getPosX());
        assertEquals(2, fancyWall.getPosY());
        assertEquals(' ', fancyWall.getTextSprite());
        assertEquals(TextColor.ANSI.DEFAULT, fancyWall.getTextColor());
        assertEquals(TextColor.ANSI.DEFAULT, fancyWall.getBackgroundColor());
        assertEquals("Wall (Connected)", fancyWall.getName());
        assertEquals(SpriteID.TILE_WALL, fancyWall.getSpriteID());
        assertEquals(1, fancyWall.getSpriteOffset());
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
    public void testTorchConstructor() {
        Torch torch = new Torch(1, 2, Torch.TorchType.CENTER);
        assertEquals(1, torch.getPosX());
        assertEquals(2, torch.getPosY());
        assertEquals(' ', torch.getTextSprite());
        assertEquals(TextColor.ANSI.DEFAULT, torch.getTextColor());
        assertEquals(TextColor.ANSI.DEFAULT, torch.getBackgroundColor());
        assertEquals("Torch", torch.getName());
        assertEquals(SpriteID.DECORATION_TORCH, torch.getSpriteID());
        assertEquals(0, torch.getSpriteOffset());
    }

    @Test
    public void testSpawnTorch() {
        level.addDecoration(new Torch(0, 0, Torch.TorchType.CENTER));
        level.addDecoration(new Torch(0, 1, Torch.TorchType.LEFT));
        level.addDecoration(new Torch(0, 2, Torch.TorchType.RIGHT));
        assertEquals(3, level.getDecorations().size());
    }

    @Test
    public void testWebConstructor() {
        Web web = new Web(1, 2, Web.WebType.TYPE1_LEFT);
        assertEquals(1, web.getPosX());
        assertEquals(2, web.getPosY());
        assertEquals(' ', web.getTextSprite());
        assertEquals(TextColor.ANSI.DEFAULT, web.getTextColor());
        assertEquals(TextColor.ANSI.DEFAULT, web.getBackgroundColor());
        assertEquals("Web", web.getName());
        assertEquals(SpriteID.DECORATION_WEB, web.getSpriteID());
        assertEquals(0, web.getSpriteOffset());
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
    public void testBoneConstructor() {
        Bone bone = new Bone(1, 2, Bone.BoneType.NORMAL);
        assertEquals(1, bone.getPosX());
        assertEquals(2, bone.getPosY());
        assertEquals(' ', bone.getTextSprite());
        assertEquals(TextColor.ANSI.DEFAULT, bone.getTextColor());
        assertEquals(TextColor.ANSI.DEFAULT, bone.getBackgroundColor());
        assertEquals("Bone", bone.getName());
        assertEquals(SpriteID.DECORATION_BONE, bone.getSpriteID());
        assertEquals(0, bone.getSpriteOffset());
    }

    @Test
    public void testSpawnBone() {
        level.addDecoration(new Bone(0, 0, Bone.BoneType.NORMAL));
        level.addDecoration(new Bone(0, 1, Bone.BoneType.SKULL));
        assertEquals(2, level.getDecorations().size());
    }

    @Test
    public void testBannerConstructor() {
        Banner banner = new Banner(1, 2);
        assertEquals(1, banner.getPosX());
        assertEquals(2, banner.getPosY());
        assertEquals(' ', banner.getTextSprite());
        assertEquals(TextColor.ANSI.DEFAULT, banner.getTextColor());
        assertEquals(TextColor.ANSI.DEFAULT, banner.getBackgroundColor());
        assertEquals("Banner", banner.getName());
        assertEquals(SpriteID.DECORATION_BANNER, banner.getSpriteID());
        assertEquals(0, banner.getSpriteOffset());
    }

    @Test
    public void testSpawnBanner() {
        level.addDecoration(new Banner(0, 0));
        assertEquals(1, level.getDecorations().size());
    }

    @Test
    public void testChainConstructor() {
        Chain chain = new Chain(1, 2, Chain.ChainType.TYPE1);
        assertEquals(1, chain.getPosX());
        assertEquals(2, chain.getPosY());
        assertEquals(' ', chain.getTextSprite());
        assertEquals(TextColor.ANSI.DEFAULT, chain.getTextColor());
        assertEquals(TextColor.ANSI.DEFAULT, chain.getBackgroundColor());
        assertEquals("Chain", chain.getName());
        assertEquals(SpriteID.DECORATION_CHAIN, chain.getSpriteID());
        assertEquals(0, chain.getSpriteOffset());
    }

    @Test
    public void testSpawnChain() {
        level.addDecoration(new Chain(0, 0, Chain.ChainType.TYPE1));
        level.addDecoration(new Chain(0, 1, Chain.ChainType.TYPE2));
        assertEquals(2, level.getDecorations().size());
    }

    @Test
    public void testRockConstructor() {
        Rock rock = new Rock(1, 2, Rock.RockType.BIG);
        assertEquals(1, rock.getPosX());
        assertEquals(2, rock.getPosY());
        assertEquals(' ', rock.getTextSprite());
        assertEquals(TextColor.ANSI.DEFAULT, rock.getTextColor());
        assertEquals(TextColor.ANSI.DEFAULT, rock.getBackgroundColor());
        assertEquals("Rock", rock.getName());
        assertEquals(SpriteID.DECORATION_ROCK, rock.getSpriteID());
        assertEquals(0, rock.getSpriteOffset());
    }

    @Test
    public void testSpawnRock() {
        level.addDecoration(new Rock(0, 0, Rock.RockType.BIG));
        level.addDecoration(new Rock(0, 1, Rock.RockType.SMALL));
        assertEquals(2, level.getDecorations().size());
    }
}
