package com.daggerrunner.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.daggerrunner.game.DaggerJumper;
import com.daggerrunner.game.sprites.Enemy;
import com.daggerrunner.game.sprites.Gem;
import com.daggerrunner.game.sprites.Player;

import java.util.ArrayList;

public class PlayState extends State {
    private static final int ENEMY_SPACING = 150;
    private static final int ENEMY_COUNT = 4;
    private static final int PLAYER_GROUND = 20;
    private static final int PLAYER_POINTS = 5;

    private Player player;
    private Texture bg;
    private Array<Enemy> enemies;
    private Array<Gem> gems;
    private Array<Integer> removeGems;

    //    private Gem gem;
    private Vector3 playerVec;
    private int playerCoordsXRight;

    BitmapFont font;
    String myText;
    Integer scoreCounter;
    Integer gemCounter;

    protected PlayState(GameStateManager gsm) {
        super(gsm);
        scoreCounter = 0;
        player = new Player(100, PLAYER_GROUND);
        playerCoordsXRight = (player.getAnimation().getWidth() / 4);
        bg = new Texture("bg.png");

        // set positioning of screen
        cam.setToOrtho(false, DaggerJumper.WIDTH / 2, DaggerJumper.HEIGHT / 2);

        playerVec = new Vector3(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), 0);
        cam.unproject(playerVec);

//        gems.add(new Gem());
        gems = new Array<Gem>();
        for (int i = 1; i <= 100; i++) {
            gems.add(new Gem());

        }

        removeGems = new Array<Integer>();

//        for(int i = 1;i<=10;i++)
//        {
//            gems.add(new Gem());
//        }

//        enemies = new Array<Enemy>();
//
//        for(int i = 1;i<=ENEMY_COUNT;i++)
//        {
//            enemies.add(new Enemy(i*(ENEMY_SPACING + Enemy.ENEMY_WIDTH)));
//        }

        font = new BitmapFont(Gdx.files.internal("data/simple.fnt"));
        myText = "Score: 0";

    }

    @Override
    protected void handleInput() {
        if (Gdx.input.justTouched()) {
            System.out.print("Get X value: " + Gdx.input.getX());

            if ((player.getPosition().y == PLAYER_GROUND) || (player.jumpCount() < 2)) {
                if ((Gdx.graphics.getHeight() / 2) > Gdx.input.getY()) {
//                    player.jump();
                }
            }

            // direction to move in
            playerVec = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            cam.unproject(playerVec);

            if (player.getPosition().x < (playerVec.x - playerCoordsXRight)) {
                player.move(true, (int) (playerVec.x - playerCoordsXRight), "right", true);
                player.directionChanged(true);
            } else {
                player.move(true, (int) (playerVec.x - playerCoordsXRight), "left", true);
                player.directionChanged(true);
            }
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
        player.update(dt);

//        cam.position.x = player.getPosition().x + 80;

        // stop character position of touched area on screen - right
        if ((player.getPosition().x > (playerVec.x - playerCoordsXRight)) && (player.movePlayer == true) && (player.direction == "right")) {
            player.move(false, 0, "right", false);
        }

        // stop character position of touched area on screen - right
        if ((playerVec.x > player.getPosition().x) && (player.movePlayer == true) && (player.direction == "left")) {
            player.move(false, 0, "left", false);
        }

//        for(Gem gems as){
//
//        }

//        for(Gem gem: gems){
//
//            gemCounter++;
//
//            // retrieve gem from array
//            gem.update(dt);
//            gem.resposition(gem.getPosition().x, gem.getPosition().y);
//
//            // check if gem collides with player or gem moves out of screen
//            if(gem.collides(player.getBounds())){
//
//                scoreCounter++;
//
//                // remove gem instance from list of array
////                gems =
////                Integer numElements = gems.size - ( gemCounter + 1);
////                System.arraycopy(gems, gemCounter + 1, gems, gemCounter, numElements);
//
//                gems.remove(gemCounter);
//
//                myText = "Score:" + scoreCounter.toString();
//
//            }
//
//            Gdx.app.log("Gem", "Nice Gem #");
//
//        }

        for (int i = 0; i < gems.size; i++) {

            // retrieve gem from array list
            Gem gem = gems.get(i);
            gem.update(dt);

            gem.resposition(gem.getPosition().x, gem.getPosition().y);

//            // Not a good idea to remove gems from array whilst in for loop
//            if(gem.collides(player.getBounds()) || gem.getPosition().y <= 0){
//
//                // add index of gem to remove
//                removeGems.add(i);
//
//            }

            // check if gem collides with player or gem moves out of screen
            if ((gem.collides(player.getBounds()) && !gem.getPointGiven())) {

                // add score to counter
                scoreCounter++;
                myText = "Score:" + scoreCounter.toString();
                gem.pointGiven();

                removeGems.add(i);

            }

//            if(gem.getPosition().y <= 0){
//                removeGems.add(i);
//
//            }

            Gdx.app.log("Gem", "Nice Gem #" + i);
        }

//        removeGems.clear();

        // list of indexes to remove place here
//        for(int i = 0; i < removeGems.size;i++){
//            gems.removeIndex(removeGems.get(i));
//
//        }

        System.out.println("Gem Size: " + gems.size);

        for (int i = 0; i < removeGems.size; i++) {
            gems.removeIndex(removeGems.get(i));

        }

        removeGems.clear();


//        if(player.movePlayer)
//        {
//            if(player.getPosition().x > player.movePlayerX)
//            {
//                player.move(false, 0, "");
//            }
//        }

//        for(int i=0; i < enemies.size; i++)
//        {
//            Enemy enemy = enemies.get(i);
//            if(cam.position.x - (cam.viewportWidth/2) > enemy.getPosEnemy().x + enemy.getEnemy().getWidth())
//            {
//                enemy.reposition(enemy.getPosEnemy().x + ((Enemy.ENEMY_WIDTH + ENEMY_SPACING) * ENEMY_COUNT));
//            }
//
//            if(enemy.collides(player.getBounds()))
//            {
////                gsm.set(new PlayState(gsm));
//            }
//        }

//        for (int i = 0; i < gems.size; i++) {
//            Gem gem = gems.get(i);
//            gem.update(dt);
//
//            if(gem.collides(player.getBounds()))
//            {
////                gsm.set(new PlayState(gsm));
//                gem.dispose();
//            }
//        }

//        gem.resposition(gem.getPosition().x, gem.getPosition().y);
//
//        if(gem.collides(player.getBounds())){
//            gem.dispose();
//
//        }

        cam.update();

    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(bg, cam.position.x - (cam.viewportWidth / 2), 0);
        font.draw(sb, myText, 0, cam.viewportHeight + 2);
        sb.draw(player.getTexture(), player.getPosition().x, player.getPosition().y);
//        if(!gem.collides(player.getBounds())) {
//            sb.draw(gem.getTexture(), gem.getPosition().x, gem.getPosition().y);
//        }

        for (Gem gem : gems) {
            sb.draw(gem.getTexture(), gem.getPosition().x, gem.getPosition().y);
        }

//        for(Gem gem : gems){
//            sb.draw(gem.getTexture(), gem.getPosition().x, gem.getPosition().y);
//        }
//        for(Enemy enemy : enemies)
//        {
//            sb.draw(enemy.getEnemy(), enemy.getPosEnemy().x, enemy.getPosEnemy().y);
//        }
//        sb.draw(gem.getGem(), gem.getPosGem().x, gem.getPosGem().y);
        sb.end();
    }

    @Override
    public void dispose() {
        bg.dispose();
        player.dispose();

        for (Gem gem : gems) {
            gem.dispose();
        }
//        for(Enemy enemy : enemies)
//        {
//            enemy.dispose();
//        }
    }
}
