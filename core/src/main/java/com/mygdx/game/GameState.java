package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class GameState {
    protected BlockBreakerGame game;

    public GameState(BlockBreakerGame game) {
        this.game = game;
    }

    public abstract void update(float deltaTime);
    public abstract void render(SpriteBatch batch);
    public abstract void dispose();

    public abstract void update();
}
