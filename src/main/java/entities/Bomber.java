package entities;

import Bomber.Game;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

public class Bomber extends Entity {
    private static final int DISTANCE_JUMP = 32;

    public Bomber(int x, int y, Image img) {
        super( x, y, img);
    }

    @Override
    public void update() {

    }

    public void goUp() {
        int U =  y / DISTANCE_JUMP;
        if (U > 1) {
            y -= DISTANCE_JUMP;
        }
    }

    public void goDown() {
        int D =  y / DISTANCE_JUMP;
        if (D + 1 < Game.HEIGHT - 1) {
            y += DISTANCE_JUMP;
        }
    }

    public void goLeft() {
        int L = x / DISTANCE_JUMP;
        if (L > 1) {
            x -= DISTANCE_JUMP;
        }
    }

    public void goRight() {
        int R = x / DISTANCE_JUMP;
        if (R + 1 < Game.WIDTH - 1) {
            x += DISTANCE_JUMP;
        }
    }
}