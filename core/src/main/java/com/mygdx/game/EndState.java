package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class EndState extends GameState {
    // Atributos
    private BitmapFont font;
    private SpriteBatch batch;

    // Constructor
    public EndState(BlockBreakerGame game) {
        super(game);
        font = new BitmapFont();
        batch = new SpriteBatch();
    }

    @Override
    public void update(float deltaTime) {
        if (Gdx.input.justTouched()) {
            int x = Gdx.input.getX();
            int y = Gdx.graphics.getHeight() - Gdx.input.getY();

            if (x > 300 &&  x < 500 && y > 150 && y < 200) {
                game.setGameState(new InitialState(game));
            }
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        font.draw(batch, "Fin del juego", 300, 275);
        font.draw(batch, "Toque para reintentar", 350,175);
        batch.end();
    }

    @Override
    public void dispose() {
        font.dispose();
        batch.dispose();
    }

    @Override
    public void update() {

    }
}
