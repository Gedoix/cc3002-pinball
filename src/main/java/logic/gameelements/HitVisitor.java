package logic.gameelements;

import controller.Game;
import logic.utils.Visitor;

import java.util.Random;

public class HitVisitor implements Visitor {

    private Random generator;

    private final Game game;
    private int result;

    public HitVisitor(Game game, long random_seed) {
        this(game);
        this.generator = new Random(random_seed);
    }

    public HitVisitor(Game game) {
        this.game = game;
        this.result = 0;
        this.generator = new Random();
    }

    /**
     * Method for visiting KickerBumper type objects.
     *
     * @param element Object to be visited.
     */
    @Override
    public void visit(Hittable element) {
        result = element.hit();
    }

    /**
     * Method for updating {@link Game} values.
     */
    @Override
    public void updateGameForBumper() {
        if(this.generator.nextFloat() <= 0.1) {
            game.triggerExtraBallBonus();
        }
    }

    @Override
    public void updateGameForDropTarget() {
        if(this.generator.nextFloat() <= 0.3) {
            game.triggerExtraBallBonus();
        }
    }

    @Override
    public void updateGameForSpotTarget() {
        game.triggerJackPotBonus();
    }

    /**
     * Method for returning a score value back to {@link Game}.
     *
     * @return Resulting game score.
     */
    @Override
    public int getResult() {
        return result;
    }
}
