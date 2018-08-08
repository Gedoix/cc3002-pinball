package gui.FXGLentities.events;

import javafx.event.Event;
import javafx.event.EventType;

/**
 * Event class announcing a new game needs to be created.
 *
 * @author Diego Ortego Prieto
 *
 * @see Event
 * @see controller.Game
 * @see gui.PinballGameApplication
 */
public class NewGameEvent extends Event {

    /**
     * Specifies the type of game being made to only default values.
     */
    public static final EventType<NewGameEvent> DEFAULT
            = new EventType<>(Event.ANY, "NEW_GAME");

    /**
     * Constructor method for the event.
     *
     * Sets type DEFAULT by default.
     */
    public NewGameEvent() {
        super(DEFAULT);
    }
}
