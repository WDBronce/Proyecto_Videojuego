package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;


public class Enlarge implements PowerUp{
    private int x,y,size,speed;
    private boolean active;
    private Color color;
    public static final int duration = 300;

    public Enlarge(int x, int y){
        this.x = x;
        this.y = y;
        this.size = 20;
        this.speed = 4;
        this.active = true;
        this.color = Color.GOLD;
    }

    @Override
    public void applyEffect(BlockBreakerGame game){
        Paddle pad = game.getPad();
        pad.setWidth(pad.getWidth() + 50);

        new Thread(() -> {
            try {
                Thread.sleep(duration*16);
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
    public boolean colision(Paddle paddle) {
        return false;
    }


}
