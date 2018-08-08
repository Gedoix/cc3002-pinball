package gui.FXGLentities.states.counter_states;

import com.almasb.fxgl.entity.Entity;
import controller.Game;
import gui.FXGLentities.PinballEntityFactory;
import gui.FXGLentities.states.StateWithGameAndOwner;

/**
 * State class allowing the {@link Entity} responsible for the ball counter to update it's value automatically every frame.
 *
 * @author Diego Ortego Prieto
 *
 * @see com.almasb.fxgl.extra.entity.state.State
 * @see StateWithGameAndOwner
 * @see PinballEntityFactory
 */
public class BallCounterUpdatingState extends StateWithGameAndOwner {

    /**
     * Constructor for setting the game and ball counter owning the state.
     *
     * @param owner Ball counter to be updated.
     * @param game_instance Instance of the game from which to extract ball counting info from.
     */
    public BallCounterUpdatingState(Entity owner, Game game_instance) {
        super(owner, game_instance);
    }

    /**
     * Updates the ball counter's view text with the corresponding value.
     *
     * @param v Updates per frame maybe. Just don't touch it.
     */
    @Override
    protected void onUpdate(double v) {
        owner.setView(PinballEntityFactory.newWhiteText("Balls: "+String.valueOf(game_instance.getBallCounter())));
    }

}
