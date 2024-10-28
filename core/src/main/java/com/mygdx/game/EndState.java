package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

// Entrar solo si jugador pierde todas sus vidas
public class EndState extends GameState{
    @Override
    public void update() {
        // Lógica para el fin del juego (esperar para reiniciar o salir)
    }

    @Override
    public void draw(SpriteBatch batch) {
        // Dibujar mensaje de "Game Over" o de fin del juego y botones de "reinicio" y "salir"
    }

    @Override
    public void handleInput() {
        // Detectar si el usuario quiere salir o reiniciar
    }
}
