package gui.FXGLentities.States.CounterStates;

import com.almasb.fxgl.entity.Entity;
import controller.Game;
import gui.FXGLentities.States.StateWithGameAndOwner;
import gui.FXGLentities.GameEntityFactory;

public class BallCounterUpdatingState extends StateWithGameAndOwner {

    public BallCounterUpdatingState(Entity owner, Game game_instance) {
        super(owner, game_instance);
    }

    @Override
    protected void onUpdate(double v) {
        owner.setView(GameEntityFactory.newWhiteText("Balls: "+String.valueOf(game_instance.getBallCounter())));
    }

}
