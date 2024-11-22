package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;

public class PowerUpBuilder {
    private int x;
    private int y;
    private int size = 10;
    private int speed = 2;
    private Color color = Color.WHITE;
    private Class<? extends PowerUp> powerUpType;

    public PowerUpBuilder setPosition(int x, int y) {
        this.x = x;
        this.y = y;
        return this;
    }

    public PowerUpBuilder setSize(int size) {
        this.size = size;
        return this;
    }

    public PowerUpBuilder setSpeed(int speed) {
        this.speed = speed;
        return this;
    }

    public PowerUpBuilder setColor(Color color) {
        this.color = color;
        return this;
    }

    public PowerUpBuilder setType(Class<? extends PowerUp> powerUpType) {
        this.powerUpType = powerUpType;
        return this;
    }

    // constructor del powerUp
    public PowerUp build() {
        if (powerUpType == null) {
            throw new IllegalArgumentException("Debe especificar un tipo de PowerUp.");
        }
        try {
            return powerUpType.getConstructor(int.class, int.class, int.class, int.class, Color.class).newInstance(x, y, size, speed, color);
        } catch (Exception e) {
            throw new RuntimeException("Error al crear el PowerUp.", e);
        }
    }

}
