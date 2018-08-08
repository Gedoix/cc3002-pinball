package gui.FXGLentities.components;

import com.almasb.fxgl.extra.entity.state.State;
import com.almasb.fxgl.extra.entity.state.StateComponent;

/**
 * Class for simulating state machine behaviour in entities using {@link StateComponent}'s abstract functionality.
 *
 * @author Diego Ortego Prieto
 *
 * @see State
 * @see StateComponent
 */
public class DefaultStateComponent extends StateComponent {

    /**
     * Constructor enabling a default state for the component.
     * Allows instantiation of the super abstracts class.
     *
     * @param initial_state Initial state for the machine.
     */
    public DefaultStateComponent(State initial_state) {
        super(initial_state);
    }

}
