package entities;

import graphics.Sprite;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

public class Oneal extends Monster {
    private int dir = RIGHT;
    private long IntervalChangeDirection = 2100000000;
    private long lastChangeDirection = 0;
    public Oneal(int x, int y, Image img) {
        super( x, y, img);
    }

    @Override
    public void update() {
        if (dir == LEFT) {
            if (spriteCount == 0) img = Sprite.oneal_left1.getFxImage();
            else if (spriteCount == 1) img = Sprite.oneal_left2.getFxImage();
            else if (spriteCount == 2) img = Sprite.oneal_left3.getFxImage();
        }
        else if (dir == RIGHT) {
            if (spriteCount == 0) img = Sprite.oneal_right1.getFxImage();
            else if (spriteCount == 1) img = Sprite.oneal_right2.getFxImage();
            else if (spriteCount == 2) img = Sprite.oneal_right3.getFxImage();
        }

        if (System.nanoTime() - lastChangeDirection > IntervalChangeDirection) {
            if (dir == LEFT) dir = RIGHT;
            else dir = LEFT;
            lastChangeDirection = System.nanoTime();
        }

        move();
    }

    @Override
    public void move() {
        x += DIR_X[dir];
        y += DIR_Y[dir];

        if (System.nanoTime() - lastSpriteChange > IntervalSpriteChange) {
            spriteCount = (spriteCount + 1) % 3;
            lastSpriteChange = System.nanoTime();
        }
    }
}