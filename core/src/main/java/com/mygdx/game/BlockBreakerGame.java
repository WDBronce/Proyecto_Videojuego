package com.mygdx.game;

import java.util.ArrayList;


import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.sun.tools.javac.Main;


public class BlockBreakerGame extends ApplicationAdapter {
    // Atributos
    // Atributos varios
    private GameState currentState;
    private SpriteBatch batch;

    // Metodos
    @Override
    public void create () {
        System.out.println("b");
        setGameState(new InitialState(this));
        batch = new SpriteBatch();
        currentState.update(Gdx.graphics.getDeltaTime());
    }

    public void setGameState(GameState newState) {
        if (currentState != null) currentState.dispose();
        currentState = newState;
    }

    public void createBlocks(int filas) {
        if(currentState instanceof MainGameState){
            ((MainGameState) currentState).createBlocks(filas);
        }else{
            return;
        }
    }

    public void drawText() {
        if(currentState instanceof MainGameState){
            ((MainGameState) currentState).drawText();
        }else {
            return;
        }
    }

    @Override
    public void render () {
        currentState.update(Gdx.graphics.getDeltaTime());
        currentState.render(batch);
    }

    @Override
    public void dispose () {
        batch.dispose();
        if (currentState != null) currentState.dispose();
    }









}
