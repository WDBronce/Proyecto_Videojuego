package com.mygdx.game;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class GameState {
    public abstract void update();
    public abstract void draw(SpriteBatch batch);
    public abstract void handleInput();

}
