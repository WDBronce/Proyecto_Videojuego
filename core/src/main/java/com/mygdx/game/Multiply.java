package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Multiply implements PowerUp{

    private int x,y,size,speed;
    private boolean active;
    private Color color;
    public static final int duration = 300;

    public Multiply(int x, int y){
        this.x = x;
        this.y = y;
        this.size = 20;
        this.speed = 4;
        this.active = true;
        this.color = Color.PURPLE;
    }

    @Override
    public void applyEffect(BlockBreakerGame game){

    }

    @Override
    public void fall(){

    }

    @Override
    public void draw(ShapeRenderer shape) {

    }

    @Override
    public boolean active() {


        return false;
    }

    @Override
    public boolean colision(Paddle paddle) {
        return false;
    }


}
