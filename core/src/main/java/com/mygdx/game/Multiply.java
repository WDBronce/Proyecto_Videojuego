package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Multiply implements PowerUp{

    private int x,y,size,speed;
    private boolean active;
    private Color color;
    public static final int duration = 300;
    private ShapeRenderer shape;

    public Multiply(int x, int y, ShapeRenderer shape) {
        this.x = x;
        this.y = y;
        this.size = 10;
        this.speed = 2;
        this.active = true;
        this.color = Color.PURPLE;
        this.shape = shape;
    }

    @Override
    public void applyEffect(MainGameState game){
        PingBall newBall = new PingBall(game.getBall().getX(), game.getBall().getY(), 10 ,-3 ,3, false);
        game.addBall(newBall);
        active = false;
    }

    @Override
    public void fall(){
        if (!active) return;
        y -= speed;
    }

    @Override
    public void draw(ShapeRenderer shape) {
        if (!active) return;
        shape.setColor(color);
        shape.x(x,y, size);
    }

    @Override
    public boolean active() {return active;}

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
