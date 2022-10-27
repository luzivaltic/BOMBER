package entities;

import graphics.Sprite;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

public class Bomber extends Entity {
    public static final int UP = 0, DOWN = 1 , LEFT = 2 , RIGHT = 3;
    public static final int[] DIR_X = { 0 , 0 , -1 , 1 };
    public static final int[] DIR_Y = { -1 , 1 , 0 , 0 };
    public static final int STEP_SIZE = 7;
    public int dir;
    public int spriteCount = 0;
    public boolean pressed = false;
    public boolean upPressed = false , downPressed = false , leftPressed = false , rightPressed = false;
    public long IntervalMove = 1000000000 / 20;
    public long IntervalSpriteChange = 1000000000 / 12;
    public long lastMove = 0;
    public long lastSpriteChange = 0;

    public Bomber(int x, int y, Image img) {
        super( x, y, img);
    }

    @Override
    public void update() {
        if( rightPressed ) {
            dir = RIGHT;
            if( spriteCount == 0 ) img = Sprite.player_right.getFxImage();
            else if( spriteCount == 1 ) img = Sprite.player_right_1.getFxImage();
            else if( spriteCount == 2 ) img = Sprite.player_right_2.getFxImage();
        }
        if( leftPressed ) {
            dir = LEFT;
            if( spriteCount == 0 ) img = Sprite.player_left.getFxImage();
            else if( spriteCount == 1 ) img = Sprite.player_left_1.getFxImage();
            else if( spriteCount == 2 ) img = Sprite.player_left_2.getFxImage();
        }
        if( downPressed ) {
            dir = DOWN;
            if( spriteCount == 0 ) img = Sprite.player_down.getFxImage();
            else if( spriteCount == 1 ) img = Sprite.player_down_1.getFxImage();
            else if( spriteCount == 2 ) img = Sprite.player_down_2.getFxImage();
        }
        if( upPressed ) {
            dir = UP;
            if( spriteCount == 0 ) img = Sprite.player_up.getFxImage();
            else if( spriteCount == 1 ) img = Sprite.player_up_1.getFxImage();
            else if( spriteCount == 2 ) img = Sprite.player_up_2.getFxImage();
        }

        pressed = downPressed || upPressed || rightPressed || leftPressed;
        if( pressed && System.nanoTime() - lastMove > IntervalMove ){
            move();
            lastMove = System.nanoTime();
        }
    }
    public void move() {
        x += DIR_X[dir] * STEP_SIZE;
        y += DIR_Y[dir] * STEP_SIZE;
        if( System.nanoTime() - lastSpriteChange > IntervalSpriteChange ) {
            spriteCount = (spriteCount + 1) % 3;
            lastSpriteChange = System.nanoTime();
        }
    }
}