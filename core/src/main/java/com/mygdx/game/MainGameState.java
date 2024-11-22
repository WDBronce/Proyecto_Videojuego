package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.sun.tools.javac.Main;

import java.util.ArrayList;

public class MainGameState extends GameState {

    // Colecciones de atributos del juego
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

    //Los atributos primitivos fueron
    //reemplazados, para el uso del patron Singleton en GameConfig
    GameConfig config = GameConfig.getInstance();

    // Constructor
    public MainGameState(BlockBreakerGame game) {
        super(game);

        // Inicializar elementos de juego
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        shape = new ShapeRenderer();
        batch = new SpriteBatch();
        font = new BitmapFont();

        blocks = new ArrayList<>();



        // Crear objetos del juego
        pad = new Paddle(Gdx.graphics.getWidth() / 2 - 50, 40, 100, 10);
        ball = new PingBall(Gdx.graphics.getWidth() / 2 - 10, 41, 10, 3, 4, true);

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
        font.draw(batch, "Puntos: " + config.getPoints(), 10, 25);
        font.draw(batch, "Vidas : " + config.getLives(), Gdx.graphics.getWidth()-20, 25);
        batch.end();
    }

    @Override
    public void update(float deltaTime) {}

    @Override
    public void render (SpriteBatch batch) {
        int aux;
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
                ball.update();// Actualiza la posición de cada pelota
        }

        //verificar si se fue la bola x abajo
        if (ball.getY()<0) {
            if(!config.getShield()){
                aux = config.getLives();
                config.setLives(aux-1);
                //nivel = 1;
                ball = new PingBall(pad.getX()+pad.getWidth()/2-5, pad.getY()+pad.getHeight()+11, 10, 3, 4, true);
            }else{
                ball.setXY(pad.getX()+pad.getWidth()/2-5, pad.getY()+pad.getHeight()+11);
                config.setShield(false);
            }
        }


        // verificar game over
        if (config.getLives()<=0) {
            game.setGameState(new EndState(game));
            ((EndState)currentState).render(batch);
        }

        // verificar si el nivel se terminó
        if (blocks.size()==0) {
            aux = config.getLevel();
            config.setLevel(aux+1);
            createBlocks(2+config.getLevel());
            ball = new PingBall(pad.getX()+pad.getWidth()/2-5, pad.getY()+pad.getHeight()+11, 10, 5, 7, true);
        }

        //dibujar bloques
        for (Block b : blocks) {
            b.draw(shape);
            ball.checkCollision(b);
        }

        // actualizar estado de los bloques
        for (int i = 0; i < blocks.size(); i++) {
            Block b = blocks.get(i);
            if (b.destroyed) {
                if(Math.random() < 0.30){
                    double powerUpChance = Math.random();

                    PowerUp newPower;
                    if (powerUpChance < 0.6) { // 60% de probabilidad para Enlarge
                        newPower = new Enlarge(b.x, b.y);
                    } else if (powerUpChance < 0.85) { // 25% de probabilidad para Shield
                        newPower = new Shield(b.x, b.y);
                    } else { // 15% de probabilidad para ExtraLife
                        newPower = new ExtraLife(b.x, b.y);
                    }
                    powerUps.add(newPower);
                }
                aux = config.getPoints();
                config.addPoints(aux+1);
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




    @Override
    public void update() {

    }

    //Getters y setter
    public Paddle getPad() {
        return pad;
    }

    public PingBall getBall() {
        return ball;
    }

    public int getLife() {
        return config.getLives();
    }

    public void setLife(int life) {
        config.setLives(life);
    }

    public boolean getShield() {
        return config.getShield();
    }

    public void setShield(boolean shield) {
        config.setShield(shield);
    }

    public int getPoints() {
        return config.getPoints();
    }

    public void setPoints(int points) {
        config.setPoints(points);
    }

    public GameState getCurrentState() {return currentState;}

    public void setCurrentState(GameState currentState) {this.currentState = currentState;}

}
