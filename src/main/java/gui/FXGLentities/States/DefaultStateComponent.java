package gui.FXGLentities.States;

import com.almasb.fxgl.extra.entity.state.State;
import com.almasb.fxgl.extra.entity.state.StateComponent;

public class DefaultStateComponent extends StateComponent {

    public DefaultStateComponent(State initial_state) {
        super(initial_state);
    }

    @Override
    public boolean isAllowStateReentrance() {
        return super.isAllowStateReentrance();
    }

    @Override
    public void setAllowStateReentrance(boolean allowStateReentrance) {
        super.setAllowStateReentrance(allowStateReentrance);
    }

    @Override
    protected void preUpdate(double tpf) {
        super.preUpdate(tpf);
    }

}
