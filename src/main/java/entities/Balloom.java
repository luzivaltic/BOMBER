package entities;

import graphics.Sprite;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

import Bomber.Game;
import static Bomber.Game.*;
import java.util.concurrent.ThreadLocalRandom;

public class Balloom extends Monster {
    private int dir;
    private long IntervalChangeDirection = 2100000000;
    private long lastChangeDirection = 0;
    private boolean isDead = false;

    public Balloom(int x, int y, Image img) {
        super( x, y, img);
        int randNum = ThreadLocalRandom.current().nextInt(0,  2);
        if (randNum == 0) dir = LEFT;
        else dir = RIGHT;
    }

    @Override
    public void update() {
        if (isDead()) {
            dead();
        }
        else {
            move();
            collideHandler();
        }

        spriteChange();
    }

    public void collideHandler() {
        for(Entity entity : Game.stillObjects ) {
            if( entity instanceof Wall || entity instanceof Brick ) {
                while (this.isCollide(entity)) {
                    if (dir == LEFT) dir = RIGHT;
                    else if (dir == RIGHT) dir = LEFT;

                    x += DIR_X[dir];
                    y += DIR_Y[dir];
                    break;
                }
            }
        }
    }

    public void move() {
        if (dir == LEFT) {
            if (spriteCount == 0) img = Sprite.balloom_left1.getFxImage();
            else if (spriteCount == 1) img = Sprite.balloom_left2.getFxImage();
            else if (spriteCount == 2) img = Sprite.balloom_left3.getFxImage();
        }
        else {
            if (spriteCount == 0) img = Sprite.balloom_right1.getFxImage();
            else if (spriteCount == 1) img = Sprite.balloom_right2.getFxImage();
            else if (spriteCount == 2) img = Sprite.balloom_right3.getFxImage();
        }

        x += DIR_X[dir];
        y += DIR_Y[dir];
    }

    public void dead() {
        if (countdownSecond != 0) {
            img = Sprite.balloom_dead.getFxImage();
            countdownSecond--;
        }
        else {
            img = null;
            removeList.add(this);
        }
    }
}