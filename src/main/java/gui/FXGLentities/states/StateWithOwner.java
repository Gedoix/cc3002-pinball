package gui.FXGLentities.states;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.extra.entity.state.State;

/**
 * Abstract state class allowing subclasses to save a reference to an {@link Entity} owner,
 * so that it's fields and methods can be accessed by the event's handler.
 *
 * @author Diego Ortego Prieto
 *
 * @see Entity
 * @see javafx.event.Event
 * @see State
 */
public abstract class StateWithOwner extends State {

    /**
     * Saved entity reference for the state.
     */
    protected final Entity owner;

    /**
     * Constructor allowing for owner entity setting.
     *
     * @param owner Entity which must have it's reference saved.
     */
    public StateWithOwner(Entity owner) {
        this.owner = owner;
    }

}
