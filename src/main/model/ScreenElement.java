package model;

import com.googlecode.lanterna.TextColor;

/*
 * Interface for anything that can be rendered
 * as part of the main game interface
 * Example: Entities, Tiles, Dropped Items
 */

public interface ScreenElement extends Renderable {
    int getPosX();

    int getPosY();
}
