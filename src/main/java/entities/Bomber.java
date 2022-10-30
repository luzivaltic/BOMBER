package entities;

import graphics.Sprite;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

import java.awt.*;
import java.util.List;
import Bomber.Game;
public class Bomber extends Entity {

    public static final int STEP_SIZE = 7;
    public int dir;
    public boolean pressed = false;
    public boolean upPressed = false , downPressed = false , leftPressed = false , rightPressed = false , spacePressed = false;
    public long IntervalMove = 1000000000 / 20;
    public long lastMove = 0;
    public boolean isDead = false;
    public long endAnimation;

    public Bomber(int x, int y, Image img) {
        super( x, y, img);
        solidArea = new Rectangle( 4 , 12 , 18 , 20 );
    }

    private boolean moved = false;
    @Override
    public void update() {
        if( isDead ) dead();
        else {
            move();
            collide();
        }
        spriteChange();
    }


    @Override
    public void collideHandler(Entity entity) {
        if( entity instanceof Wall || entity instanceof Brick ) {
            while ( this.isCollide(entity) ){
                x -= DIR_X[dir];
                y -= DIR_Y[dir];
            }
        }

        if( entity instanceof Monster && this.isCollide(entity) ) {
            isDead = true;
            endAnimation = System.nanoTime() + IntervalSpriteChange * 3;
            spriteCount = 0;
            lastSpriteChange = System.nanoTime();
        }
    }

    public void move() {
        moved = false;
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
            x += DIR_X[dir] * STEP_SIZE;
            y += DIR_Y[dir] * STEP_SIZE;
            lastMove = System.nanoTime();
            moved = true;
        }
    }
    public void dead(){
        if( spriteCount == 0 ) img = Sprite.player_dead1.getFxImage();
        else if( spriteCount == 1 ) img = Sprite.player_dead2.getFxImage();
        else if( spriteCount == 2 ) img = Sprite.player_dead3.getFxImage();

        if( endAnimation < System.nanoTime() ) {
            isDead = false;
            x = Sprite.SCALED_SIZE;
            y = Sprite.SCALED_SIZE;
            img = Sprite.player_right.getFxImage();
        }
    }

}