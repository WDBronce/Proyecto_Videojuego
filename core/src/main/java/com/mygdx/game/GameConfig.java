package com.mygdx.game;

public class GameConfig {

    private static  GameConfig instance;

    private int points;
    private int level;
    private int lives;
    private boolean shield;

    private GameConfig(){
        reset();
    }

    public static GameConfig getInstance(){
        if(instance == null){
            instance = new GameConfig();
        }
        return instance;
    }

    public void reset(){
        points = 0;
        level = 1;
        lives = 3;
        shield = false;
    }

    public int getPoints() { return points; }
    public void setPoints(int points) { this.points = points; }
    public void addPoints(int points) { this.points += points; }

    public int getLevel() { return level; }
    public void setLevel(int level) { this.level = level; }

    public int getLives() { return lives; }
    public void setLives(int lives) { this.lives = lives; }

    public boolean getShield() { return shield; }
    public void setShield(boolean shield) { this.shield = shield; }
}
