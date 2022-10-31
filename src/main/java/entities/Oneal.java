package entities;

import graphics.Sprite;
import javafx.scene.image.Image;

import Bomber.Game;

import java.util.*;

import static Bomber.Game.*;

class Pair {
    public int x;
    public int y;
    public Pair(int x, int y) {
        this.x = x;
        this.y = y;
    }
}

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

    private boolean checkGrid(int u, int v) {
        /*if (u < 0 || v < 0 || u >= WIDTH || v >= HEIGHT) {
            return false;
        }

        Entity temp = new Monster(u * Sprite.SCALED_SIZE, v * Sprite.SCALED_SIZE, null);

        for (Entity entity : stillObjects) {
            if (entity instanceof Wall) {
                if (temp.isCollide(entity)) {
                    return false;
                }
            }
        }

        for (Entity entity : entities) {
            if (entity instanceof Brick) {
                if (temp.isCollide(entity)) {
                    return false;
                }
            }
        }*/

        return true;
    }

    private void findBomber() {
        /*System.err.println(bomber.x + " " + bomber.y);

        LinkedList<Pair> queue = new LinkedList<>();
        boolean[][] color = new boolean[WIDTH][HEIGHT];
        int[][] distance = new int[WIDTH][HEIGHT];

        queue.add(new Pair(convert(bomber.x), convert(bomber.y)));
        distance[convert(bomber.x)][convert(bomber.y)] = 1;
        color[convert(bomber.x)][convert(bomber.y)] = true;

        while (queue.isEmpty() == false) {
            Pair element = queue.getFirst();
            queue.removeFirst();

            int x = element.x;
            int y = element.y;

            for (int dir = 0; dir < 4; ++dir) {
                int u = x + DIR_X[dir];
                int v = y + DIR_Y[dir];

                if (checkGrid(u, v) == true) {
                    if (color[u][v] == false) {
                        color[u][v] = true;
                        distance[u][v] = distance[x][y] + 1;
                        queue.add(new Pair(u, v));
                    }
                }
            }
        }

        System.err.println(distance[convert(this.x)][convert(this.y)]);

        if (distance[convert(x)][convert(y)] != 0) {
            for (int dir = 0; dir < 4; ++dir) {
                int u = convert(x) + DIR_X[dir];
                int v = convert(y) + DIR_Y[dir];
                if (checkGrid(u, v) == true && distance[convert(x)][convert(y)] == distance[u][v] + 1) {
                    direct = dir;
                    break;
                }
            }
        } else {
            for (int dir = 0; dir < 4; ++dir) {
                int u = convert(x) + DIR_X[dir];
                int v = convert(y) + DIR_Y[dir];
                if (checkGrid(u, v) == true) {
                    direct = dir;
                    break;
                }
            }
        }*/
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