package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.sun.tools.javac.Main;

import java.util.ArrayList;

public class MainGameState extends GameState {

    // Colecciones de atributos del juego
    private ArrayList<PingBall> ballsInScreen = new ArrayList<>();
    private ArrayList<PowerUp> powerUps = new ArrayList<>();
    private ArrayList<Block> blocks = new ArrayList<>();

    // Atributos varios
    private OrthographicCamera camera;
    private GameState currentState;
    private ShapeRenderer shape;
    private SpriteBatch batch;
    private BitmapFont font;
    private PingBall ball;
    private Paddle pad;

    // Atributos primitivos
    private int points;
    private int level;
    private int life;

    // Constructor
    public MainGameState(BlockBreakerGame game) {
        super(game);

        // Inicializar elementos de juego
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        shape = new ShapeRenderer();
        batch = new SpriteBatch();
        font = new BitmapFont();
        ballsInScreen = new ArrayList<>();
        blocks = new ArrayList<>();

        points = 0;
        life = 3;

        // Crear objetos del juego
        pad = new Paddle(Gdx.graphics.getWidth() / 2 - 50, 40, 100, 10);
        ball = new PingBall(Gdx.graphics.getWidth() / 2 - 10, 41, 10, 3, 4, true);
        ballsInScreen.add(ball);
        createBlocks(3); // Ejemplo con 3 filas de bloques
    }

    public void createBlocks(int rows) {
        blocks.clear();
        int blockWidth = 70;
        int blockHeight = 26;
        int y = Gdx.graphics.getHeight();
        for (int cont = 0; cont<rows; cont++ ) {
            y -= blockHeight+10;
            for (int x = 5; x < Gdx.graphics.getWidth(); x += blockWidth + 10) {
                blocks.add(new Block(x, y, blockWidth, blockHeight));
            }
        }
    }

    public void drawText() {
        //actualizar matrices de la cámara
        camera.update();
        //actualizar
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        //dibujar textos
        font.draw(batch, "Puntos: " + points, 10, 25);
        font.draw(batch, "Vidas : " + life, Gdx.graphics.getWidth()-20, 25);
        batch.end();
    }

    @Override
    public void update(float deltaTime) {
        // copiar y pegar logica?
        // game.updateGameLogic();  // Lógica de actualización del juego
    }

    @Override
    public void render (SpriteBatch batch) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        shape.begin(ShapeRenderer.ShapeType.Filled);
        pad.draw(shape);

        // monitorear inicio del juego
        if (ball.estaQuieto()) {
            ball.setXY(pad.getX()+pad.getWidth()/2-5, pad.getY()+pad.getHeight()+11);
            if (Gdx.input.isKeyPressed(Input.Keys.SPACE)){
                ball.setEstaQuieto(false);
            }
        }else{
            for (PingBall actualBall : ballsInScreen) {
                actualBall.update();     // Actualiza la posición de cada pelota
            }
        }

        //verificar si se fue la bola x abajo
        if (ball.getY()<0) {
            if (ballsInScreen.size() <= 1) { // Se verifica que solo haya una bola en pantalla para quitar la vida
                life--;
                //nivel = 1;
                ball = new PingBall(pad.getX()+pad.getWidth()/2-5, pad.getY()+pad.getHeight()+11, 10, 3, 4, true);
                ballsInScreen.remove(ball);
            }else{
                ballsInScreen.remove(ball);
            }
        }
        // verificar game over
        if (life<=0) {
            game.setGameState(new EndState(game));
            ((EndState)currentState).render(batch);
        }

        // verificar si el nivel se terminó
        if (blocks.size()==0) {
            level++;
            createBlocks(2+level);
            ball = new PingBall(pad.getX()+pad.getWidth()/2-5, pad.getY()+pad.getHeight()+11, 10, 5, 7, true);
        }

        //dibujar bloques
        for (Block b : blocks) {
            b.draw(shape);
            for(PingBall actualBall : ballsInScreen){
                actualBall.checkCollision(b);
            }

        }

        // actualizar estado de los bloques
        for (int i = 0; i < blocks.size(); i++) {
            Block b = blocks.get(i);
            if (b.destroyed) {
                if(Math.random() < 0.35){
                    PowerUp newPower = Math.random() < 0.5 ? new Enlarge(b.x,b.y) : new Multiply(b.x, b.y, shape);
                    powerUps.add(newPower);
                }
                points++;
                blocks.remove(b);
                i--; //para no saltarse 1 tras eliminar del arraylist
            }
        }

        // agregar los power ups a la pantalla
        for (int i = 0; i < powerUps.size(); i++) {
            PowerUp powerUp = powerUps.get(i);
            powerUp.fall();
            powerUp.draw(shape);

            // Aplicar efecto si colisiona con el paddle y luego removerlo
            if (powerUp.colision(pad)) {
                powerUp.applyEffect(this);
                powerUps.remove(i);
                i--;
            } else if (powerUp.active() && powerUp instanceof Enlarge && powerUp.getY() < 0) {
                powerUps.remove(i); // Remover si cae al fondo sin ser recogido
                i--;
            }
        }

        ball.checkCollision(pad);
        ball.draw(shape);

        shape.end();
        drawText();
    }

    @Override
    public void dispose() {
        font.dispose();
        batch.dispose();
        shape.dispose();
    }

    public void addBall(PingBall ball) {
        ballsInScreen.add(ball);
    }


    @Override
    public void update() {

    }

    //Getters
    public Paddle getPad() {
        return pad;
    }

    public PingBall getBall() {
        return ball;
    }
}
