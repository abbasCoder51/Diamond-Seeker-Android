package com.daggerrunner.game.sprites;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.daggerrunner.game.DaggerJumper;

import java.util.Random;

public class Gem {

    private static final int GRAVITY = 10;
    private static final int MOVEMENT = 60;
    private Texture gem;
    private Vector2 position;
    private Vector3 velocity;
    private Rectangle bounds;
    private Random rand;
    private int min = 1;
    private int max = 3;
    private int xMin = 100;
    private int xMax = DaggerJumper.WIDTH/2;
    private int yMin = 0;
    private int yMax = 100;

    public Gem(){
        rand = new Random();
        int gemValue = rand.nextInt(max - min + 1) + min;
        switch(gemValue){
            case 1: gem = new Texture("redGem.png");
                    break;
            case 2: gem = new Texture("blueGem.png");
                break;
            case 3: gem = new Texture("greenGem.png");
                break;
        }

        int gemPosXValue = rand.nextInt(xMax - xMin + 1) + xMin;
        int gemPosYValue = rand.nextInt(xMax - xMin + 1) + xMin;
        position = new Vector2((DaggerJumper.WIDTH/2 - gemPosXValue), (DaggerJumper.HEIGHT/2 + gemPosYValue));
        velocity = new Vector3(0, 0 ,0);
        bounds = new Rectangle(position.x, position.y, gem.getWidth(), gem.getHeight());

    }

    public void update(float dt){
        position.sub(-velocity.x, MOVEMENT * dt);

    }

    public Texture getTexture(){
        return gem;
    }

    public Vector2 getPosition(){
        return position;
    }

    public void resposition(float x, float y){
        position.set(x, y);
        bounds.setPosition(position.x, position.y);
    }

    public boolean collides (Rectangle player){
        return player.overlaps(bounds);
    }

    public void dispose(){
        gem.dispose();
    }

}
