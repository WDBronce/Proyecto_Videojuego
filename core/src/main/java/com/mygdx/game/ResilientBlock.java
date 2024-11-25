package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.Random;

public class ResilientBlock implements BlockEffect {

    int x,y,width,height;
    Color cc;
    boolean destroyed;

    private int lives = 2;
    private Sound breakSound;

    public ResilientBlock(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        destroyed = false;
        Random r = new Random(x+y);

        cc = new Color(0.1f+r.nextFloat(1), r.nextFloat(1), r.nextFloat(1), 10);
        this.breakSound = Gdx.audio.newSound(Gdx.files.internal("assets/boom3.wav"));
    }
    public void draw(ShapeRenderer shape){
        shape.setColor(cc);
        shape.rect(x, y, width, height);
    }

    public int getX(){
        return this.x;
    }

    public int getY() {return this.y;}

    public int getLives() {return lives; }

    public void minusLives() {
        lives -= 1;
        if (lives < 0) {
            lives = 0;
        }
    }

    @Override
    public void breakSound() {
        if (lives == 0) {
            System.out.println("A");
            breakSound.play();
        }
    }

    @Override
    public void dispose() {
        breakSound.dispose();
    }
}
