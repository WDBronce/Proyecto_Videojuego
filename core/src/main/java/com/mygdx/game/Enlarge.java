package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;


public class Enlarge implements PowerUp{
    private int x,y,size,speed;
    private boolean active;
    private Color color;
    public static final int duration = 300;

    public Enlarge(int x, int y, int size, int speed, Color color) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.speed = speed;
        this.color = color;
        this.active = true;
    }

    @Override
    public void applyEffect(MainGameState game){
        Paddle pad = game.getPad();
        pad.setWidth(pad.getWidth() + 50);

        new Thread(() -> {
            try {
                Thread.sleep(duration*10);
                pad.setWidth(pad.getWidth()-50);
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }).start();

        active = false;


    }

    @Override
    public void fall(){
        if(!this.active) return;
        y -= speed;
    }

    @Override
    public void draw(ShapeRenderer shape) {
        if(!active) return;
        shape.setColor(color);
        shape.circle(x,y,size);
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
