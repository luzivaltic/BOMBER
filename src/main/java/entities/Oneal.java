package entities;

import graphics.Sprite;
import javafx.scene.image.Image;

import Bomber.Game;

import java.util.*;

import static Bomber.Game.*;

public class Oneal extends Monster {
    private long IntervalChangeDirection = 2100000000;
    private long lastChangeDirection = 0;

    private int countdown = Sprite.SCALED_SIZE;

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
            if (countdown != 0) {
                move();
                countdown--;
            }
        }

        spriteChange();
    }

    private int block(int x) {
        return (x + 16 + Sprite.SCALED_SIZE - 1) / Sprite.SCALED_SIZE - 1;
    }

    private boolean checkGrid(int u, int v) {
        if (u < 0 || v < 0 || u >= WIDTH || v >= HEIGHT) {
            return false;
        }

        Monster temp = new Monster(u, v, null);

        for (Entity entity : entities) {
            if (entity instanceof Brick || entity instanceof Wall) {
                if (temp.isCollide(entity)) {
                    return false;
                }
            }
        }

        return true;
    }

    private void findBomber() {
        if (countdown != 0) {
            return;
        }

        LinkedList<Pair> queue = new LinkedList<>();
        boolean[][] color = new boolean[2 * WIDTH][2 * HEIGHT];
        int[][] preMove = new int[2 * WIDTH][2 * HEIGHT];

        for (int i = 0; i < WIDTH; ++i) {
            for (int j = 0; j < HEIGHT; ++j) {
                preMove[i][j] = -1;
            }
        }

        queue.add(new Pair(block(bomber.x), block(bomber.y)));
        color[block(bomber.x)][block(bomber.y)] = true;

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

                        if (dir == LEFT) preMove[u][v] = RIGHT;
                        else if (dir == RIGHT) preMove[u][v] = LEFT;
                        else if (dir == UP) preMove[u][v] = DOWN;
                        else preMove[u][v] = UP;

                        if (u == this.x && v == this.y) {
                            break;
                        }

                        queue.add(new Pair(u, v));
                    }
                }
            }
        }

        countdown = Sprite.SCALED_SIZE;

        if (preMove[block(this.x)][block(this.y)] != -1) {
            direct = preMove[block(this.x)][block(this.y)];
        } else {
            for (int dir = 0; dir < 4; ++dir) {
                int u = x + DIR_X[dir];
                int v = y + DIR_Y[dir];
                if (checkGrid(u, v) == true) {
                    direct = dir;
                    break;
                }
            }
        }
        System.err.println(direct);
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