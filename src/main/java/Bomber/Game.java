package Bomber;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import entities.*;
import graphics.Sprite;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Game extends Application {
    public static final int WIDTH = 31;
    public static final int HEIGHT = 13;
    public static final int FPS = 60;
    private GraphicsContext gc;
    private Canvas canvas;
    public static List<Entity> entities = new ArrayList<>();
    public static List<Entity> stillObjects = new ArrayList<>();
    public static List<Entity> removeList = new ArrayList<>();
    public static List<Entity> addList = new ArrayList<>();
    private long Interval = 1000000000 / FPS;
    private long lastUpdate = 0;
    public static int numberOfMonster = 0;
    public static int bomberLifeRemain = 3;
    public static Bomber bomber;
    public static Group root = new Group();
    public static Label labelLife = new Label();
    public static Label labelStage = new Label();

    public static int idLevel = 0;
    public static int limitLevel = 3;
    public static String level[] = {
            "src/main/resources/level1.txt",
            "src/main/resources/level2.txt",
            "src/main/resources/level3.txt",
    };

    public static void main(String[] args) {
        Application.launch(Game.class);
    }

    public static class Pair {
        public int x;
        public int y;
        public Pair(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    @Override
    public void start(Stage stage) throws Exception {
        // Tao Canvas
        canvas = new Canvas(Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE * (HEIGHT + 1));
        gc = canvas.getGraphicsContext2D();

        // Tao titles
        stage.setTitle("Bomberman");

        // Tao root container

        Scene scene = new Scene(root);
        root.getChildren().add(canvas);

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                switch ( keyEvent.getCode() ) {
                    case UP :bomber.upPressed = true ; break;
                    case DOWN :bomber.downPressed = true; break;
                    case RIGHT :bomber.rightPressed = true; break;
                    case LEFT : bomber.leftPressed = true; break;
                }
            }
        });

        scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                switch ( keyEvent.getCode() ) {
                    case UP : bomber.upPressed = false ; break;
                    case DOWN : bomber.downPressed = false; break;
                    case RIGHT : bomber.rightPressed = false; break;
                    case LEFT : bomber.leftPressed = false; break;
                    case SPACE :
                        int bomber_block_x = ( bomber.x - 16 ) / 32 + 1;
                        int bomber_block_y = ( bomber.y - 16 ) / 32 + 1;
                        if( Bomb.bombCount < Bomb.bombCapacity ) {
                            entities.add(new Bomb(bomber_block_x, bomber_block_y, Sprite.bomb.getFxImage()));
                        }
                        break;
                    case Q: System.exit(1); break;
                }
            }
        });

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                    render_update();
            }
        };
        timer.start();

        createMap();
        buildEntities();

        stage.setScene(scene);
        stage.show();
    }

    public static void buildEntities() {
        try {
            File file = new File(level[idLevel]);
            Scanner scanner = new Scanner(file);

            for (int i = 0; i < HEIGHT; i++) {
                String readMap = scanner.nextLine();

                for (int j = 0; j < WIDTH; j++) {
                    Entity object = null;
                    switch (readMap.charAt(j)) {
                        case 'p':
                            bomber = new Bomber(j, i, Sprite.player_right.getFxImage());
                            break;
                        //case '1': numberOfMonster++; entities.add(new Balloom(j, i, Sprite.balloom_right1.getFxImage())); break;
                        //case '2': numberOfMonster++; entities.add(new Oneal(j, i, Sprite.oneal_right1.getFxImage())); break;
                        //case '3': numberOfMonster++; entities.add(new Kondoria(j, i, Sprite.kondoria_right1.getFxImage())); break;
                        case 'f': {
                            entities.add(new FlameItem(j, i, Sprite.powerup_flames.getFxImage()));
                            entities.add(new Brick(j, i, Sprite.brick.getFxImage()));
                            break;
                        }
                        case 's': {
                            entities.add(new SpeedItem(j, i, Sprite.powerup_speed.getFxImage()));
                            entities.add(new Brick(j, i, Sprite.brick.getFxImage()));
                            break;
                        }
                        case 'b': {
                            entities.add(new BombItem(j, i, Sprite.powerup_bombs.getFxImage()));
                            entities.add(new Brick(j, i, Sprite.brick.getFxImage()));
                            break;
                        }
                        case '#': stillObjects.add(new Wall(j, i, Sprite.wall.getFxImage())); break;
                        case '*': entities.add(new Brick(j, i, Sprite.brick.getFxImage())); break;
                        case 'x': {
                            entities.add(new Portal(j, i, Sprite.portal.getFxImage()));
                            entities.add(new Brick(j, i, Sprite.brick.getFxImage()));
                            break;
                        }
                    }
                }
            }
            entities.add(bomber);

        } catch (Exception e) {

        };
    }

    public static void createMap() {
        entities.clear();
        stillObjects.clear();

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

    int count = 0;
    long pre_count = 0;
    public void render_update() {
        // Tao text HP
        root.getChildren().remove(labelLife);
        root.getChildren().remove(labelStage);

        String HP = String.valueOf(bomberLifeRemain);
        labelLife.setFont(new Font(23));
        labelLife.setText("Life : " + HP);
        labelLife.setLayoutX(30);
        labelLife.setLayoutY(415);

        // Tao text Level
        String level = String.valueOf(idLevel + 1);
        labelStage.setFont(new Font(23));
        labelStage.setText("Stage : " + level);
        labelStage.setLayoutX(180);
        labelStage.setLayoutY(415);

        root.getChildren().addAll(labelLife, labelStage);

        if (bomberLifeRemain == 0) {
            return;
        }

        count++;
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        stillObjects.forEach(g -> g.render(gc));

        try {
            entities.forEach(g -> g.render(gc));
            entities.forEach(Entity::update);
        } catch (Exception e) {};

        entities.removeAll( removeList );
        entities.addAll( addList );
        addList.clear();
        removeList.clear();
        lastUpdate = System.nanoTime();

        if( System.nanoTime() - pre_count > 1000000000 ) {
            count = 0;
            pre_count = System.nanoTime();
        }
    }
}