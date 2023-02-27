package model;

import com.googlecode.lanterna.TextColor;

/*
 * Interface for anything that can be rendered,
 * either as a game screen element or a simple icon.
 * Will be represented as sprites in later versions.
 * Examples: Screen Elements (Entities, Tiles, Dropped Items), Items
 */

public interface Renderable {
    char getTextSprite();

    TextColor getTextColor();

    TextColor getBackgroundColor();
}
