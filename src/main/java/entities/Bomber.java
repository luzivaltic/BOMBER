package entities;

import Bomber.Game;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

public class Bomber extends Entity {
    public static final int UP = 0, DOWN = 1 , LEFT = 2 , RIGHT = 3;
    public static final int[] DIR_X = { 0 , 0 , -1 , 1 };
    public static final int[] DIR_Y = { -1 , 1 , 0 , 0 };
    public static final int STEP_SIZE = 10;
    public int dir = RIGHT;
    public boolean moving = false;

    public Bomber(int x, int y, Image img) {
        super( x, y, img);
    }

    @Override
    public void update() {
        if( moving ){
            x += DIR_X[dir] * STEP_SIZE;
            y += DIR_Y[dir] * STEP_SIZE;
        }
    }
}