package entities;

import graphics.Sprite;
import javafx.scene.image.Image;

import Bomber.Game;
import Bomber.Game.*;
import entities.Entity;
import entities.Bomber;

import static Bomber.Game.bomber;
import java.util.concurrent.ThreadLocalRandom;

public class Oneal extends Monster {
    private long IntervalChangeDirection = 2100000000;
    private long lastChangeDirection = 0;

    public Oneal(int x, int y, Image img) {
        super( x, y, img);
    }

    @Override
    public void update() {
        if (isDead()) {
            dead();
        }
        else {
            findBomber();
            move();
        }

        spriteChange();
    }

    private void findBomber() {
        if (this.x < bomber.getX()) { direct = RIGHT; return; }
        if (this.x > bomber.getX()) { direct = LEFT; return; }
        if (this.y < bomber.getY()) { direct = DOWN; return; }
        if (this.y > bomber.getY()) { direct = UP; return; }
    }

    public void move() {
        if (direct == LEFT) {
            if (spriteCount == 0) img = Sprite.oneal_left1.getFxImage();
            else if (spriteCount == 1) img = Sprite.oneal_left2.getFxImage();
            else if (spriteCount == 2) img = Sprite.oneal_left3.getFxImage();
        }
        else {
            if (spriteCount == 0) img = Sprite.oneal_right1.getFxImage();
            else if (spriteCount == 1) img = Sprite.oneal_right2.getFxImage();
            else if (spriteCount == 2) img = Sprite.oneal_right3.getFxImage();
        }

        x += DIR_X[direct];
        y += DIR_Y[direct];
    }

    private void dead() {
        if (countdown != 0) {
            img = Sprite.oneal_dead.getFxImage();
            countdown--;
        }
        else {
            x = -1;
            y = -1;
            img = null;
        }
    }
}