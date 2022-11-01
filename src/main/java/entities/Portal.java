package entities;

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

    @Override
    public void collideHandler(Entity entity) {
        if (entity instanceof Bomber) {
            if (this.isCollide(entity)) {
                if (idLevel < limitLevel - 1) {
                    ++idLevel;
                    createMap();
                    buildEntities();
                } else if (idLevel == limitLevel - 1) {
                    gameState = "Game is over ! You are the winner !";
                }
            }
        }
    }
}
