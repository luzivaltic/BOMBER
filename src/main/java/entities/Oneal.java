package entities;

import graphics.Sprite;
import javafx.scene.image.Image;

import Bomber.Game;

import static Bomber.Game.*;

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

    boolean collide(int x, int y) {
        if (x < 0 || y < 0 || x >= WIDTH || y >= HEIGHT) {
            return true;
        }

        for (Entity entity : stillObjects) {
            if (entity instanceof Brick || entity instanceof Wall) {
                if (this.isCollide(entity)) {
                    return true;
                }
            }
        }

        return false;
    }

    void dfs(int[][] distance, int x, int y) {
        for (int dir = 0; dir < 4; ++dir) {
            int u = x + DIR_X[dir];
            int v = y + DIR_Y[dir];

            if (collide(u, v) == false && distance[u][v] == 0) {
                distance[u][v] = distance[x][y] + 1;
                dfs(distance, u, v);
            }
        }
    }

    private void findBomber() {

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

    public void dead() {
        if (countdownSecond != 0) {
            img = Sprite.oneal_dead.getFxImage();
            countdownSecond--;
        }
        else {
            img = null;
            removeList.add(this);
        }
    }
}