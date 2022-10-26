package Bomber;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class Game extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Group root = new Group();
        Scene scene = new Scene(root, Color.BLACK );
        Text text = new Text();
        text.setText("Hello Worlds");
        text.setX(55);
        text.setY(55);
        text.setFont( Font.font("Aria" , 50) );
        text.setFill(Color.MIDNIGHTBLUE);

        // Effect
        Glow glow = new Glow();
        glow.setLevel(0.5);

        Image background = new Image("galaxy.jpg");
        ImageView backImage = new ImageView(background);
        backImage.setX(50);
        backImage.setY(50);
        backImage.setFitHeight(100);
        backImage.setPreserveRatio(true);
        backImage.setEffect(glow);

        Circle circle = new Circle();
        circle.setCenterX(200);
        circle.setCenterY(200);
        circle.setRadius(50);
        circle.setFill( Color.CRIMSON );

        EventHandler<MouseEvent> filterEvent = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                System.out.println("HE ok easy");
                circle.setFill(Color.YELLOW);
            }
        };

        EventHandler<MouseEvent> handlerEvent = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                System.out.println("HE ok easy");
                circle.setFill(Color.RED);
            }
        };


        circle.addEventFilter(MouseEvent.MOUSE_CLICKED , filterEvent);
        circle.addEventHandler(MouseEvent.MOUSE_CLICKED , handlerEvent );

        root.getChildren().add(circle);
        root.getChildren().add(backImage);
        root.getChildren().add(text);
        stage.getIcons().add(background);
        stage.setTitle("BomberMan");
        stage.setScene(scene);
        stage.show();
    }
}