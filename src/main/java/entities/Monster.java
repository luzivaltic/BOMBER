package entities;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

import static Bomber.Game.bomber;

public class Monster extends Entity {
    public int countdown;
    public int direct;
    public boolean isDead = false;
    public Monster(int x, int y, Image img) {
        super( x, y, img);
    }

    public boolean isDead() {
        return isDead;
    }

    @Override
    public void update(){}
    public void move(){}

    public void dead() {}
}