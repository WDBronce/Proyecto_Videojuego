package com.mygdx.game;

import java.util.ArrayList;
import java.util.List;


import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;


public class BlockBreakerGame extends ApplicationAdapter {
    private OrthographicCamera camera;
	private SpriteBatch batch;
	private BitmapFont font;
	private ShapeRenderer shape;
	private PingBall ball;
	private Paddle pad;
	private ArrayList<Block> blocks = new ArrayList<>();
	private int vidas;
	private int puntaje;
	private int nivel;
    private ArrayList<PingBall> ballsInScreen = new ArrayList<>();
    private ArrayList<PowerUp> powerUps = new ArrayList<>();

    //Metodos

		@Override
		public void create () {
			camera = new OrthographicCamera();
		    camera.setToOrtho(false, 800, 480);
		    batch = new SpriteBatch();
		    font = new BitmapFont();
		    font.getData().setScale(3, 2);
		    nivel = 1;
		    crearBloques(2+nivel);

		    shape = new ShapeRenderer();
		    ball = new PingBall(Gdx.graphics.getWidth()/2-10, 41, 10, 5, 7, true);
		    pad = new Paddle(Gdx.graphics.getWidth()/2-50,40,100,10);
		    vidas = 3;
		    puntaje = 0;
		}

		public void crearBloques(int filas) {
			blocks.clear();
			int blockWidth = 70;
		    int blockHeight = 26;
		    int y = Gdx.graphics.getHeight();
		    for (int cont = 0; cont<filas; cont++ ) {
		    	y -= blockHeight+10;
		    	for (int x = 5; x < Gdx.graphics.getWidth(); x += blockWidth + 10) {
		            blocks.add(new Block(x, y, blockWidth, blockHeight));
		        }
		    }
		}

		public void dibujaTextos() {
			//actualizar matrices de la cámara
			camera.update();
			//actualizar
			batch.setProjectionMatrix(camera.combined);
			batch.begin();
			//dibujar textos
			font.draw(batch, "Puntos: " + puntaje, 10, 25);
			font.draw(batch, "Vidas : " + vidas, Gdx.graphics.getWidth()-20, 25);
			batch.end();
		}

		@Override
		public void render () {
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	        shape.begin(ShapeRenderer.ShapeType.Filled);
	        pad.draw(shape);

	        // monitorear inicio del juego
	        if (ball.estaQuieto()) {
	        	ball.setXY(pad.getX()+pad.getWidth()/2-5, pad.getY()+pad.getHeight()+11);
	        	if (Gdx.input.isKeyPressed(Input.Keys.SPACE)){
                    ball.setEstaQuieto(false);
                    addBall(ball);
                }
	        }else ball.update();

	        //verificar si se fue la bola x abajo
	        if (ball.getY()<0) {
                if (ballsInScreen.size() == 1) { // Se verifica que solo haya una bola en pantalla para quitar la vida
	        	    vidas--;
	        	    //nivel = 1;
	        	    ball = new PingBall(pad.getX()+pad.getWidth()/2-5, pad.getY()+pad.getHeight()+11, 10, 5, 7, true);
                    ballsInScreen.remove(ball);
                }else{
                    ballsInScreen.remove(ball);
                }
	        }
	        // verificar game over
	        if (vidas<=0) {
	        	vidas = 3;
	        	nivel = 1;
	        	crearBloques(2+nivel);
	        	//ball = new PingBall(pad.getX()+pad.getWidth()/2-5, pad.getY()+pad.getHeight()+11, 10, 5, 7, true);
	        }

	        // verificar si el nivel se terminó
	        if (blocks.size()==0) {
	        	nivel++;
	        	crearBloques(2+nivel);
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
                    if(Math.random() < 0.35){
                        PowerUp newPower = Math.random() < 0.5 ? new Enlarge(b.x,b.y) : new Multiply(b.x, b.y, shape);
                        powerUps.add(newPower);
                    }
	            	puntaje++;
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
	        dibujaTextos();
		}

		@Override
		public void dispose () {

		}

        public void addBall(PingBall ball) {
            ballsInScreen.add(ball);
        }

        //Getters
    public Paddle getPad() {
        return pad;
    }

    public PingBall getBall() {
        return ball;
    }


}
