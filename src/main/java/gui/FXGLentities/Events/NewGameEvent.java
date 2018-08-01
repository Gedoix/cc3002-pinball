package gui.FXGLentities.Events;

import controller.Game;
import javafx.event.Event;
import javafx.event.EventType;

public class NewGameEvent extends Event {

    public static final EventType<NewGameEvent> DEFAULT
            = new EventType<>(Event.ANY, "NEW_GAME");

    public NewGameEvent(EventType<? extends Event> eventType) {
        super(eventType);
    }
}
