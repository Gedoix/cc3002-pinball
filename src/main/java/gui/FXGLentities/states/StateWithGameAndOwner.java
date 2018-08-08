package gui.FXGLentities.states;

import com.almasb.fxgl.entity.Entity;
import controller.Game;

/**
 * State abstract class allowing for the saving of a reference to a {@link Game} instance,
 * so that subclasses may have access to it's fields and methods.
 *
 * @author Diego Ortego Prieto
 *
 * @see com.almasb.fxgl.extra.entity.state.State
 * @see Game
 * @see StateWithOwner
 */
public abstract class StateWithGameAndOwner extends StateWithOwner {

    /**
     * Instance of the game to be accessed.
     */
    protected final Game game_instance;

    /**
     * Constructor allowing (and forcing) the setting of both a {@link Game} instance and an owner.
     *
     * @param owner Entity to be saves by the state.
     * @param game_instance Game to be saved by the state.
     */
    public StateWithGameAndOwner(Entity owner, Game game_instance) {
        super(owner);
        this.game_instance = game_instance;
    }

}
