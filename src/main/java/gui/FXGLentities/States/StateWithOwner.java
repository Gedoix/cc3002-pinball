package gui.FXGLentities.States;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.extra.entity.state.State;

public abstract class StateWithOwner extends State {

    protected final Entity owner;

    public StateWithOwner(Entity owner) {
        this.owner = owner;
    }

}
