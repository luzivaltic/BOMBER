package entities;

import graphics.Sprite;
import javafx.scene.image.Image;

public class Bomb extends Entity {

    public Bomb(int x, int y, Image img) {super(x, y, img);}

    @Override
    public void update() {
        spriteChange();
        if( spriteCount == 0 ) img = Sprite.bomb.getFxImage();
        else if( spriteCount == 1 ) img = Sprite.bomb_1.getFxImage();
        else if( spriteCount == 2 ) img = Sprite.bomb_2.getFxImage();
    }
}
