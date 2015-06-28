package com.blacktipstudios.model;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.blacktipstudios.game.Game;
import com.blacktipstudios.resources.Assets;
import com.blacktipstudios.resources.Scores;
import com.blacktipstudios.resources.Settings;
import com.blacktipstudios.tools.KeyStroke;
import com.blacktipstudios.tools.Swipe;

import java.util.Random;


public class World {

    public static final int WIDTH = 20;
    public static final int HEIGHT = 25;
    public static float speed;
    public static float time;

    private Snake snake;
    private Item item;
    private Explosions explosions;
    private ParticleEffect effect;
    private Score score;
    private Bombs bombs;
    private Diamond diamond;
    private Special special;
    private Timer counter;
    private TumbleWeed tumbleWeed;
    private float timer;
    private boolean worldAbility;
    private boolean isSnakePaused;

    public World(float worldWidth, float worldHeight) {
        timer = 0;
        snake = new Snake();
        item = new Item();
        explosions = new Explosions();
        bombs = new Bombs();
        diamond = new Diamond();
        score = new Score(worldWidth, worldHeight);
        counter = new Timer(15);
        tumbleWeed = new TumbleWeed();
        setItem();
        //initialize explosions
        for (int i = 0; i < snake.getCarts().size(); i++) {
            Type type = snake.getCarts().get(i).getType();
            explosions.addExplosion(type);
        }
        //set speed for desktop or phone
        if(Gdx.app.getType() == Application.ApplicationType.Desktop ||
                Gdx.app.getType() == Application.ApplicationType.WebGL) {
            speed = 10f;
        } else {
            speed = 7f;
        }
        time = 1 / speed;
    }

   /***************************************************
    ************* WORLD METHODS ***********************
    ***************************************************/

    public Array<Vector2> getOpenLocations() {
        Random r = new Random();
        Array<Vector2> locs = new Array<Vector2>();
        for(int i = 1; i < WIDTH - 1; i++) {
            for(int j = 1; j < HEIGHT - 1; j++) {
                Vector2 temp = new Vector2(i, j);
                boolean hitObject = false;
                for(int k = 0; k < snake.getCarts().size(); k++) {
                    int tempX = snake.getCarts().get(k).getX();
                    int tempY = snake.getCarts().get(k).getY();
                    if(temp.x == tempX && temp.y == tempY) {
                        hitObject = true;
                    }
                }
                if(item.getX() == temp.x && item.getY() == temp.y) {
                    hitObject = true;
                }
                if(diamond.isActive()) {
                    if(diamond.getX() == temp.x && diamond.getY() == temp.y) {
                        hitObject = true;
                    }
                }
                if(!hitObject) {
                    locs.add(temp);
                }
            }
        }
        return locs;
    }

    public void reset() {
        snake = new Snake();
        item = new Item();
        explosions = new Explosions();
        for (int i = 0; i < snake.getCarts().size(); i++) {
            Type type = snake.getCarts().get(i).getType();
            explosions.addExplosion(type);
        }
        setItem();
        bombs = new Bombs();
        score.reset();
        diamond = new Diamond();
        counter = new Timer(15);
        worldAbility = false;

    }

    public void dispose() {
        effect.dispose();
        score.dispose();
    }

    public void drawWorldPaused(SpriteBatch batch) {
        update();
        batch.draw(Assets.background, 0, 0);
        score.draw(batch);
        if(effect != null) effect.draw(batch, Gdx.graphics.getDeltaTime());
        tumbleWeed.draw(batch);
    }

    public void draw(SpriteBatch batch) {
        update();
        batch.draw(Assets.background, 0, 0);
        drawSnake(batch);
        if(snake.isAlive()) {
            score.draw(batch);
            item.draw(batch);
            diamond.draw(batch);
            bombs.draw(batch);
            if(worldAbility) {
                if(!shouldIconBlink()) {
                    switch (special) {
                        case Bombs:
                            batch.draw(Assets.skullIcon, 35, 1470);
                            break;
                        case DoublePoints:
                            batch.draw(Assets.doubleIcon, 35, 1470);
                            break;
                        case Invincible:
                            batch.draw(Assets.shieldIcon, 35, 1470);
                            break;
                        case FastSnake:
                            batch.draw(Assets.speedIcon, 35, 1470);
                            break;
                    }
                }
            }
        }
        if(effect != null) effect.draw(batch, Gdx.graphics.getDeltaTime());
        tumbleWeed.draw(batch);
    }

    private void update() {
        if(!isSnakePaused) {
            updateSnake();
            updateDiamond();
            updateTimer();
        }
    }

    public void specialStateMachine() {
        switch (special) {
            case Bombs:
                placeBombs(30);
                break;
            case FastSnake:
                time = time / 2f;
                break;
            case DoublePoints:
                score.setDoublePoints(true);
                break;
            case Invincible:
                snake.setInvincible(true);
                break;
        }
    }

    public void resetSpecial(Special s) {
        switch (s) {
            case Bombs:
                bombs = new Bombs();
                break;
            case FastSnake:
                time *= 2;
                break;
            case DoublePoints:
                score.setDoublePoints(false);
                break;
            case Invincible:
                snake.setInvincible(false);
                break;
        }
    }
    BitmapFont font = new BitmapFont(); {
        //font.scale(2);
    }
    public void drawTextSpecial(SpriteBatch batch) {
        if(!worldAbility) return;
        String text = "";
        switch (special) {
            case Bombs:
                text = "Bombs";
                break;
            case FastSnake:
                text = "Fast Snake";
                break;
            case DoublePoints:
                text = "Double Points";
                break;
            case Invincible:
                text = "Invincible";
                break;
        }
        font.draw(batch, "" + text, 50, Game.WORLD_HEIGHT - 50);
    }

    public boolean isPaused() {
        return isSnakePaused;
    }

    public void pauseSnake(boolean paused) {
        this.isSnakePaused = paused;
    }

    /***************************************************
    ************* SNAKE METHODS ***********************
    ***************************************************/
    private boolean snakeHitWall() {
        Cart head = snake.getCarts().get(0);
        return (head.getX() <= 0 || head.getX() >= WIDTH - 1 ||
                head.getY() <= 0 || head.getY() >= HEIGHT - 1);
    }

    private boolean snakeHitSelf() {
        Cart head = snake.getCarts().get(0);
        for(int i = 1; i < snake.getCarts().size(); i++) {
            Cart temp = snake.getCarts().get(i);
            if(head.getX() == temp.getX() && head.getY() == temp.getY())
                return true;
        }
        return false;
    }

    private boolean snakeHitBomb() {
        Cart head = snake.getCarts().get(0);
        for(int i = 0 ; i < bombs.getBombs().size; i++) {
            Bomb b = bombs.getBombs().get(i);
            if(head.getX() == b.getX() && head.getY() == b.getY()) {
                return true;
            }
        }
        return false;
    }

    private boolean snakeDied() {
        return (snakeHitWall() && !snake.isInvincible())
                || (snakeHitSelf() && !snake.isInvincible())
                || (snakeHitBomb() && !snake.isInvincible());
    }

    private boolean snakeHitItem() {
        Cart head = snake.getCarts().get(0);
        return(head.getX() == item.getX() &&
               head.getY() == item.getY());
    }

    private boolean snakeHitDiamond() {
        Cart head = snake.getCarts().get(0);
        return(head.getX() == diamond.getX() &&
                head.getY() == diamond.getY());
    }

    private void drawSnake(SpriteBatch batch) {
        if(!snake.isAlive()) {
            for(int i = 0; i < explosions.getExplosions().size; i++) {
                Explosion explosion = explosions.getExplosions().get(i);
                if(!explosion.explosionJustWentOff()) {
                    snake.getCarts().get(i).draw(batch);
                }
            }
        }
        if(snake.isAlive())
            snake.draw(batch);
        else
            explosions.draw(batch);
    }

    private void updateSnake() {
        //update direction
        int direction = snake.getDirection();
        switch (Gdx.app.getType()) {
            case Android:
            case iOS:
                direction = Swipe.direction;
                break;
            case Desktop:
            case WebGL:
                direction = KeyStroke
                        .direction;
                break;
        }
        snake.setDirection(direction);
        if((timer += Gdx.graphics.getDeltaTime()) > time) {
            timer -= time;
            //snake growing
            if (snakeHitItem()) {
                snake.grow(item.getType());
                score.add(item.getType());
                explosions.addExplosion(explosions.getExplosions().size - 1, item.getType());
                if(Settings.isSoundOn()) {
                    Sound sound = Assets.itemCollected;
                    long id = sound.play();
                    switch (item.getType()) {
                        case Coal:
                            sound.setPitch(id, .69f + .2f);
                            break;
                        case Silver:
                            sound.setPitch(id, 0.87f + .2f);
                            break;
                        case Gold:
                            sound.setPitch(id, 0.94f + .2f);
                            break;
                    }
                }
                setItemStars(item);
                setItem();
                //snake advancing
            } else if (!snakeDied()) {
                snake.advance();
                //snake died
            } else {
                if(snake.isAlive()) {
                    snake.setAlive(false);
                    if (special != null) {
                        special = null;
                        worldAbility = false;
                        time = 1 / speed;
                    }
                    prepareExplosions();
                    explosions.getExplosions().get(0).setExplosionStartTime(0);
                    Scores.addScore(this.score.getScore());
                }
            }
        }
        if(snakeHitDiamond() && diamond.isActive()) {
            counter.start();
            Special[] specials = Special.values();
            int n = new Random().nextInt(specials.length);
            special = specials[n];
            diamond.setActive(false);
            worldAbility = true;
            specialStateMachine();
        }
    }

   /***************************************************
    ************* ITEM METHODS ***********************
    ***************************************************/
    private void setItemLocation() {
        Random r = new Random();
        Array<Vector2> locs = getOpenLocations();
        int rand = r.nextInt(locs.size);
        int x = (int) locs.get(rand).x;
        int y = (int) locs.get(rand).y;
        item.setX(x);
        item.setY(y);
    }

    private void setItemType() {
        Type type;
        Random r = new Random();
        int n = r.nextInt(20);
        if(n >= 0 && n <= 11)
            type = Type.Coal;
        else if(n >= 12 && n <= 17)
            type = Type.Silver;
        else
            type = Type.Gold;
        item.setType(type);
    }

    private void setItem() {
        setItemType();
        setItemLocation();
    }

    public void setItemStars(Item item) {
        Type type = item.getType();
        switch (type) {
            case Coal:
                effect = Assets.star_coal;
                break;
            case Silver:
                effect = Assets.star_silver;
                break;
            case Gold:
                effect = Assets.star_gold;
                break;
        }
        effect.setPosition(item.getX() * Cart.WIDTH, item.getY() * Cart.HEIGHT);
        effect.start();
    }

    /***************************************************
     ************* BOMB METHODS ***********************
     ***************************************************/
    //checks if bombs are a good distance apart and also checks if they are
    //a good distance from the snake
    public void placeBombs(int number) {
        Random r = new Random();
        Array<Vector2> locs = getOpenLocations();
        for(int i = 0; i < number; i++) {
            int n = r.nextInt(locs.size);
            Vector2 v = locs.get(n);
            if(getClosestDistance(v) > 1) {
                Vector2 snakeFront = new Vector2(snake.getCarts().get(0).getX(),
                        snake.getCarts().get(0).getY());
                if(getClosestDistance(snakeFront) > 1) {
                    bombs.addBomb((int) v.x, (int) v.y);
                }
            }
        }

    }

    public void placeBomb() {
        Random r = new Random();
        Array<Vector2> locs = getOpenLocations();
        int n = r.nextInt(locs.size);
        Vector2 v = locs.get(n);
        bombs.addBomb((int) v.x, (int) v.y);
    }

    public int getClosestDistance(Vector2 loc) {
        Array<Vector2> locs = bombs.getLocations();
        if(locs.size == 0) return HEIGHT;
        int min = HEIGHT;
        for(int i = 0; i < locs.size; i++) {
            Vector2 temp = locs.get(i);
            if(temp.x == loc.x && temp.y == loc.y) continue;
            double disX = Math.pow(loc.x - temp.x, 2);
            double disY = Math.pow(loc.y - temp.y, 2);
            double distance = (int) Math.sqrt(disX + disY);
            if(distance < min) {
                min = (int) distance;
            }
        }
        return min;
    }


    /***************************************************
     ************* DIAMOND METHODS *********************
     ***************************************************/

    private void placeDiamond() {
        Array<Vector2> locs = getOpenLocations();
        Random r = new Random();
        Vector2 loc = locs.get(r.nextInt(locs.size));
        diamond.setX((int) loc.x);
        diamond.setY((int) loc.y);
        diamond.setActive(true);
    }
    int temp = 1000; //1000
    int dTime = 0;
    private void updateDiamond() {
        //randomize chance of diamond
        Random r = new Random();
        if(r.nextInt(temp) == 0 && !diamond.isActive() && !worldAbility) {
            placeDiamond();
        }
        if(diamond.isActive()) {
            dTime++;
            if(dTime > 400) {
                dTime = 0;
                diamond.setActive(false);
            }
        }

    }

    /***************************************************
     ************* TIMER METHODS ***********************
     ***************************************************/

    private void updateTimer() {
        counter.update();
        if(counter.isDone() && worldAbility) {
            worldAbility = false;
            counter = new Timer(15);
            resetSpecial(special);
        }
    }


   /***************************************************
    ************* GETTERS AND SETTERS *****************
    ***************************************************/
    private void prepareExplosions() {
        switch (Explosions.getExplosionType()) {
            case Linear:
                if (snake.isAlive()) return;
                for (int i = 0; i < snake.getCarts().size(); i++) {
                    int x = snake.getCarts().get(i).getX();
                    int y = snake.getCarts().get(i).getY();
                    Explosion explosion = explosions.getExplosions().get(i);
                    explosion.setX(x * Cart.WIDTH);
                    explosion.setY(y * Cart.HEIGHT);
                    explosions.getExplosions().get(i).setExplosionStartTime(i * 3);
                }
            break;
            case Random:
                if (snake.isAlive()) return;
                for (int i = 0; i < snake.getCarts().size(); i++) {
                    int x = snake.getCarts().get(i).getX();
                    int y = snake.getCarts().get(i).getY();
                    Explosion explosion = explosions.getExplosions().get(i);
                    explosion.setX(x * Cart.WIDTH);
                    explosion.setY(y * Cart.HEIGHT);
                    explosions.getExplosions().get(i).setExplosionStartTime(MathUtils.random(20));
                }
            break;
            case Slow:
                if (snake.isAlive()) return;
                for (int i = 0; i < snake.getCarts().size(); i++) {
                    int x = snake.getCarts().get(i).getX();
                    int y = snake.getCarts().get(i).getY();
                    Explosion explosion = explosions.getExplosions().get(i);
                    explosion.setX(x * Cart.WIDTH);
                    explosion.setY(y * Cart.HEIGHT);
                    explosions.getExplosions().get(i).setExplosionStartTime(4);
                    explosion.setSpeed(1 / 15f);
                }
            break;
        }
    }

    public Explosions getExplosions() {
        return explosions;
    }

    public Snake getSnake() {
        return snake;
    }

    public void setSnake(Snake snake) {
        this.snake = snake;
    }

    public Score getScore() {
        return score;
    }

    private boolean shouldIconBlink() {
        boolean blink = false;
        float startTime = 12.5f;
        float blinkTime = 0.3f;
        float increment = 0;
        if(counter.getTime() > (startTime + increment) &&
                counter.getTime() < (startTime + blinkTime + increment)) {
            blink = true;
        }
        increment += 0.5f;
        if(counter.getTime() > (startTime + increment) &&
                counter.getTime() < (startTime + blinkTime + increment)) {
            blink = true;
        }
        increment += 0.5f;
        if(counter.getTime() > (startTime + increment) &&
                counter.getTime() < (startTime + blinkTime + increment)) {
            blink = true;
        }
        increment += 0.5f;
        if(counter.getTime() > (startTime + increment) &&
                counter.getTime() < (startTime + blinkTime + increment)) {
            blink = true;
        }
        increment += 0.5f;
        if(counter.getTime() > (startTime + increment) &&
                counter.getTime() < (startTime + blinkTime + increment)) {
            blink = true;
        }
        if(counter.getTime() > (startTime + increment) &&
                counter.getTime() < (startTime + blinkTime + increment)) {
            blink = true;
        }
        return blink;
    }

    public TumbleWeed getTumbleWeed() {
        return tumbleWeed;
    }
}
