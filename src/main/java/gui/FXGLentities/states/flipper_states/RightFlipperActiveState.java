package gui.FXGLentities.states.flipper_states;

import com.almasb.fxgl.entity.Entity;

/**
 * Class for setting a right flipper to active, moving it to a 45 degree end angle over the negative horizontal axis.
 *
 * @author Diego Ortego Prieto
 *
 * @see gui.FXGLentities.states.StateWithOwner
 * @see gui.FXGLentities.PinballEntityFactory
 */
public class RightFlipperActiveState extends FlipperActivationState {

    /**
     * Constructor for setting the entity owner of the state.
     *
     * @param owner Flipper to do the turning.
     */
    public RightFlipperActiveState(Entity owner) {
        super(owner, false, 45);
    }

}
