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
    public static Bomber bomber;
    public static char[][] board = new char[WIDTH][HEIGHT];


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
                        entities.add( new Bomb(bomber_block_x , bomber_block_y , Sprite.bomb.getFxImage() ) );
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

    public void buildEntities() throws FileNotFoundException {
        File file = new File("src/main/resources/level1.txt");
        Scanner scanner = new Scanner(file);

        for (int i = 0; i < HEIGHT; i++) {
            String readMap = scanner.nextLine();

            for (int j = 0; j < WIDTH; j++) {
                Entity object = null;
                board[j][i] = readMap.charAt(j);
                switch (readMap.charAt(j)) {
                    case 'p': bomber = new Bomber(j, i, Sprite.player_right.getFxImage()); break;
                    case '1': entities.add(new Balloom(j, i, Sprite.balloom_right1.getFxImage())); break;
                    case '2': entities.add(new Oneal(j, i, Sprite.oneal_right1.getFxImage())); break;
                    case '#': stillObjects.add(new Wall(j, i, Sprite.wall.getFxImage()));break;
                    case '*': entities.add(new Brick(j, i, Sprite.brick.getFxImage())); break;
                    case 'x': stillObjects.add(new Portal(j, i, Sprite.portal.getFxImage())); break;
                }
            }
        }

        entities.add(bomber);
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

    int count = 0;
    long pre_count = 0;
    public void render_update() {
        count++;
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        stillObjects.forEach(g -> g.render(gc));
        entities.forEach(g -> g.render(gc));
        entities.forEach(Entity::update);
        entities.removeAll( removeList );
        entities.addAll( addList );
        addList.clear();
        removeList.clear();
        lastUpdate = System.nanoTime();

        if( System.nanoTime() - pre_count > 1000000000 ) {
            //System.out.println(count);
            count = 0;
            pre_count = System.nanoTime();
        }
    }
}