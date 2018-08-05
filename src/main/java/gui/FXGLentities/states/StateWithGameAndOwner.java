package gui.FXGLentities.states;

import com.almasb.fxgl.entity.Entity;
import controller.Game;

public abstract class StateWithGameAndOwner extends StateWithOwner {

    protected final Game game_instance;

    public StateWithGameAndOwner(Entity owner, Game game_instance) {
        super(owner);
        this.game_instance = game_instance;
    }

}
