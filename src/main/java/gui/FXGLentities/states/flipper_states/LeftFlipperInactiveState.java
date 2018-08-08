package gui.FXGLentities.states.flipper_states;

import com.almasb.fxgl.entity.Entity;

/**
 * Class for setting a left flipper to inactive, moving it to a 20 degree end angle below the positive horizontal axis.
 *
 * @author Diego Ortego Prieto
 *
 * @see gui.FXGLentities.states.StateWithOwner
 * @see gui.FXGLentities.PinballEntityFactory
 */
public class LeftFlipperInactiveState extends FlipperActivationState {

    /**
     * Constructor for setting the entity owner of the state.
     *
     * @param owner Flipper to do the turning.
     */
    public LeftFlipperInactiveState(Entity owner) {
        super(owner, false, 20);
    }

}
