package entities;

import graphics.Sprite;
import javafx.scene.image.Image;
import Bomber.Game;
import static Bomber.Game.*;

public class Portal extends Entity {

    public Portal(int x, int y, Image img) {
        super(x, y, img);
    }

    @Override
    public void update() {
        if (numberOfMonster == 0) {
            collideHandler(bomber);
        }
    }

    void reset_bomber()
    {
        bomber = new Bomber( 1 , 1 , null);
        Bomb.bombCapacity = 2;
        Bomb.flameLength = 1;
    }

    @Override
    public void collideHandler(Entity entity) {
        if (entity instanceof Bomber) {
            if (this.isCollide(entity)) {
                if (idLevel < limitLevel - 1) {
                    ++idLevel;
                    reset_bomber();
                    createMap();
                    buildEntities();
                } else if (idLevel == limitLevel - 1) {
                    gameState = "Game is over ! You are the winner !";
                }
            }
        }
    }
}
