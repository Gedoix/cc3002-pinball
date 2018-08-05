package gui.FXGLentities.states.counter_states;

import com.almasb.fxgl.entity.Entity;
import controller.Game;
import gui.FXGLentities.states.StateWithGameAndOwner;
import gui.FXGLentities.PinballEntityFactory;

public class ScoreCounterUpdatingState extends StateWithGameAndOwner {

    public ScoreCounterUpdatingState(Entity owner, Game game_instance) {
        super(owner, game_instance);
    }

    @Override
    protected void onUpdate(double v) {
        owner.setView(PinballEntityFactory.newWhiteText("Score: "+String.valueOf(game_instance.getScore())));
    }
}
