package model;

import com.googlecode.lanterna.TextColor;

public interface Renderable {
    char getTextSprite();

    TextColor getTextColor();

    TextColor getBackgroundColor();
}
