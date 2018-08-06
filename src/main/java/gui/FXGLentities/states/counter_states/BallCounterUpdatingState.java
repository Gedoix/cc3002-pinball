package gui.FXGLentities.states.counter_states;

import com.almasb.fxgl.entity.Entity;
import controller.Game;
import gui.FXGLentities.PinballEntityFactory;
import gui.FXGLentities.states.StateWithGameAndOwner;

public class BallCounterUpdatingState extends StateWithGameAndOwner {

    public BallCounterUpdatingState(Entity owner, Game game_instance) {
        super(owner, game_instance);
    }

    @Override
    protected void onUpdate(double v) {
        owner.setView(PinballEntityFactory.newWhiteText("Balls: "+String.valueOf(game_instance.getBallCounter())));
    }

}
