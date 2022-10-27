package Bomber;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;

import java.awt.event.KeyListener;

public class KeyHandler implements EventHandler<KeyEvent> {

    @Override
    public void handle(KeyEvent keyEvent) {
        System.out.println(keyEvent.getCode());
    }
}
