package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class InitialState extends GameState {
    private BitmapFont font;
    private SpriteBatch batch;

    public InitialState(BlockBreakerGame game) {
        super(game);
        this.font = new BitmapFont();
        this.batch = new SpriteBatch();
    }

    @Override
    public void update(float deltaTime) {
        if (Gdx.input.isTouched()) {
            int x = Gdx.input.getX();
            int y = Gdx.graphics.getHeight() - Gdx.input.getY();

            if (x > 300 && x < 500) { // Ancho del botón
                if (y > 250 && y < 300) { // Coordenadas botón "Jugar"
                    game.setGameState(new MainGameState(game));
                } else if (y > 150 && y < 200) { // Coordenadas botón "Salir"
                    Gdx.app.exit();
                }
            }
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        // Limpiar la pantalla
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Dibujar los botones
        batch.begin();
        font.draw(batch, "Jugar", 350, 275);  // Posición para el texto "Jugar"
        font.draw(batch, "Salir", 350, 175);  // Posición para el texto "Salir"
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
