package com.mygdx.game;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public interface PowerUp {
    void applyEffect(BlockBreakerGame game);
    void fall();
    void draw(ShapeRenderer shape);
    boolean active();
    boolean colision(Paddle paddle);
    int getX();
    int getY();
}
