package model;

import com.googlecode.lanterna.TextColor;

public interface ScreenElement {

    //
    // Getters
    //

    int getPosX();

    int getPosY();

    char getTextSprite();

    TextColor getTextColor();

    TextColor getBackgroundColor();
}
