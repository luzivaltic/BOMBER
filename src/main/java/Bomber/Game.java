package Bomber;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import entities.*;
import graphics.Sprite;

import java.util.ArrayList;
import java.util.List;

public class Game extends Application {

    public static final int WIDTH = 20;
    public static final int HEIGHT = 15;

    public static final int FPS = 60;
    private GraphicsContext gc;
    private Canvas canvas;
    private List<Entity> entities = new ArrayList<>();
    private List<Entity> stillObjects = new ArrayList<>();

    private long Interval = 1000000000 / FPS;
    private long lastUpdate = 0;


    public static void main(String[] args) {
        Application.launch(Game.class);
    }

    @Override
    public void start(Stage stage) throws Exception {
        // Tao Canvas
        canvas = new Canvas(Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE * HEIGHT);
        gc = canvas.getGraphicsContext2D();

        // Tao root container
        Group root = new Group();
        root.getChildren().add(canvas);
        Scene scene = new Scene(root);

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                    render_update();
            }
        };
        timer.start();
        createMap();
        Bomber bomberman = new Bomber(1, 1, Sprite.player_right.getFxImage());
        entities.add(bomberman);
        stage.setScene(scene);
        stage.show();
    }

    public void createMap() {
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                Entity object;
                object = new Grass(i, j, Sprite.grass.getFxImage());

                if (j == 0 || j == HEIGHT - 1 || i == 0 || i == WIDTH - 1) {
                    object = new Wall(i, j, Sprite.wall.getFxImage());
                }

                stillObjects.add(object);
            }
        }
    }

    public void render_update() {
        if( System.nanoTime() - lastUpdate > Interval ) {
            gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
            stillObjects.forEach(g -> g.render(gc));
            entities.forEach(g -> g.render(gc));
            entities.forEach(Entity::update);
            lastUpdate = System.nanoTime();
        }
    }
}