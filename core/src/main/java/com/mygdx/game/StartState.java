package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class StartState extends GameState {
    // Atributos
    private BitmapFont font;

    // Constructor
    public StartState() {
        font = new BitmapFont();
        font.getData().setScale(3);
    }

    // Metodos
    @Override
    // Lógica para iniciar el juego si se selecciona el botón "Iniciar"
    public void update() {
        handleInput();
    }

    @Override
    // Dibujar los botones de "Iniciar" y "Salir"
    public void draw(SpriteBatch batch) {
        font.draw(batch,"Block Breaker 2024",300, 400);
        font.draw(batch,"Iniciar",350,300);
        font.draw(batch, "Salir",350,200);
        batch.end();
    }

    @Override
    // Detectar si se ha presionado el botón "Iniciar" o "Salir"
    public void handleInput() {
        if (Gdx.input.isTouched()) {
            // Obtenemos la pos de el input
            int x = Gdx.input.getX();
            int y = Gdx.graphics.getHeight() - Gdx.input.getY(); // Ajustamos para coordenadas invertidas

            // Verificamos si se toco Iniciar
            if (x >= 350 && x <= 450 && y >= 280 && y <= 320) {
                // Cambiar a EstadoDesarrollo
            }

            // Verificamos si se toco Salir
            if (x >= 350 && x <= 450 && y >= 180 && y <= 220) {
                // Finalizamos la ejecución de la app
                Gdx.app.exit();
            }
        }
    }

    // Getters y setters
    public BitmapFont getFont() {
        return font;
    }

    public void setFont(BitmapFont font) {
        this.font = font;
    }
}
