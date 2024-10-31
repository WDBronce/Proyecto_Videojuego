package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class ExtraLife implements PowerUp{
    private int x,y,size,speed;
    private boolean active;
    private Color color;
    public static final int duration = 300;

    public ExtraLife(int x, int y){
        this.x = x;
        this.y = y;
        this.size = 10;
        this.speed = 2;
        this.active = true;
        this.color = Color.GREEN;
    }

    //Metodos

    @Override
    public void applyEffect(MainGameState game){
        int actualLife = game.getLife();
        game.setLife(actualLife + 1);
        active = false;
    };

    @Override
    public void fall(){
        if(!this.active) return;
        y -= speed;
    }

    @Override
    public void draw(ShapeRenderer shape) {
        if(!active) return;
        shape.setColor(color);
        shape.x(x,y,size);
    }

    @Override
    public boolean active() {
        return active;
    }

    @Override
    public boolean colision(Paddle pad) {
        return active && (pad.getX() + pad.getWidth() >= x - size && pad.getX() <= x + size) &&
            (pad.getY() + pad.getHeight() >= y - size && pad.getY() <= y + size);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
