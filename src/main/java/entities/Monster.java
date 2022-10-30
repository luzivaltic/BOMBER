package entities;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

import static Bomber.Game.bomber;

public class Monster extends Entity {
    public int direct;
    public long countdownSecond = 10;

    public Monster(int x, int y, Image img) {
        super( x, y, img);
    }

    public boolean isDead() {
        if (this.isCollide(bomber)) {
            isDead = true;
        }

        return isDead;
    }

    @Override
    public void update(){}
    public void move(){}
    public void dead() {}
    @Override
    public void setDead() {
        this.isDead = true;
    }
}