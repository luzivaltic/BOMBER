package entities;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import graphics.Sprite;

import java.awt.*;

public abstract class Entity {
    //Tọa độ X tính từ góc trái trên trong Canvas
    protected int x;

    //Tọa độ Y tính từ góc trái trên trong Canvas
    protected int y;

    boolean solid = false;
    protected Image img;
    public Rectangle solidArea;

    //Khởi tạo đối tượng, chuyển từ tọa độ đơn vị sang tọa độ trong canvas
    public Entity( int xUnit, int yUnit, Image img) {
        this.x = xUnit * Sprite.SCALED_SIZE;
        this.y = yUnit * Sprite.SCALED_SIZE;
        this.img = img;
        solidArea = new Rectangle(1 , 1 , Sprite.SCALED_SIZE - 2 , Sprite.SCALED_SIZE - 2 );
    }

    public boolean isCollide(Entity other) {
        double max_x = (double) Math.max( this.x + this.solidArea.x , other.x + other.solidArea.x );
        double min_x = Math.min( this.x + this.solidArea.x + this.solidArea.width ,
                                other.x + other.solidArea.x + other.solidArea.width );

        double max_y = (double) Math.max( this.y + this.solidArea.y , other.y + other.solidArea.y );
        double min_y = Math.min( this.y + this.solidArea.y + this.solidArea.height ,
                                other.y + other.solidArea.y + other.solidArea.height );

        return ( max_x < min_x && max_y < min_y );
    }

    public void render(GraphicsContext gc) {
        gc.drawImage(img, x, y);
    }
    public abstract void update();
}
