package logic.gameelements;

import controller.Game;
import logic.gameelements.bumper.Bumper;
import logic.gameelements.bumper.KickerBumper;
import logic.gameelements.bumper.PopBumper;
import logic.gameelements.target.DropTarget;
import logic.gameelements.target.SpotTarget;
import logic.utils.VisitableGameElement;
import logic.utils.GameElementVisitor;

import java.util.Random;

public class HitVisitor implements GameElementVisitor {

    /**
     * Random number generator for use in some methods.
     *
     * @see Random
     */
    private Random generator;

    /**
     * Resulting score after a successful hit.
     */
    private int result;


    /**
     * Construction method that accepts a seed for the pseudo-random aspects of some methods.
     * Only for testing purposes.
     *
     * @see #updateGameForBumper(Game)
     * @see #updateGameForDropTarget(Game)
     *
     * @param random_seed Random generator seed.
     */
    public HitVisitor(long random_seed) {
        this.generator = new Random(random_seed);
    }

    /**
     * Construction method for the class.
     * Sets the random seed to null, so the {@link Random} class may produce one on it's own.
     */
    public HitVisitor() {
        this.result = 0;
        this.generator = new Random();
    }

    /**
     * {@inheritDoc}
     *
     * @param element Visitable element to be altered.
     * @param game    Link to the {@link Game} object calling this method, for game event triggering.
     */
    @Override
    public void visit(VisitableGameElement element, Game game) {
        element.accept(this, game);
    }

    /**
     * Method for returning a score value back to {@link Game}.
     *
     * @return  Resulting game score.
     */
    public int getResult() {
        return result;
    }

    /**
     * {@inheritDoc}
     *
     * Redirects to {@link #visitingBumper(Bumper, Game)}, since both bumpers share the same behaviour.
     *
     * @param kickerBumper Element to be visited.
     * @param game         Link to the {@link Game} object calling this method, for game event triggering.
     */
    @Override
    public void visitingKickerBumper(KickerBumper kickerBumper, Game game) {
        this.visitingBumper(kickerBumper, game);
    }

    /**
     * {@inheritDoc}
     *
     * Redirects to {@link #visitingBumper(Bumper, Game)}, since both bumpers share the same behaviour.
     *
     * @param popBumper Visitable element to be altered.
     * @param game      Link to the {@link Game} object calling this method, for game event triggering.
     */
    @Override
    public void visitingPopBumper(PopBumper popBumper, Game game) {
        this.visitingBumper(popBumper, game);
    }

    /**
     * {@inheritDoc}
     *
     * @param dropTarget Visitable element to be altered.
     * @param game       Link to the {@link Game} object calling this method, for game event triggering.
     */
    @Override
    public void visitingDropTarget(DropTarget dropTarget, Game game) {
        result = dropTarget.hit();
        updateGameForDropTarget(game);
    }

    /**
     * {@inheritDoc}
     *
     * @param spotTarget Visitable element to be altered.
     * @param game       Link to the {@link Game} object calling this method, for game event triggering.
     */
    @Override
    public void visitingSpotTarget(SpotTarget spotTarget, Game game) {
        result = spotTarget.hit();
        updateGameForSpotTarget(game);
    }

    /**
     * Handles both hit behaviour for both bumper types.
     *
     * @param bumper    Element to be visited.
     * @param game      Link to the {@link Game} object calling this method, for game event triggering.
     */
    private void visitingBumper(Bumper bumper, Game game) {
        boolean previous = bumper.isUpgraded();

        result = bumper.hit();

        boolean after = bumper.isUpgraded();

        if(!previous && after) {
            updateGameForBumper(game);
        }
    }

    private void updateGameForBumper(Game game) {
        if(this.generator.nextFloat() <= 0.1) {
            game.triggerExtraBallBonus();
        }
    }

    private void updateGameForDropTarget(Game game) {
        if(this.generator.nextFloat() <= 0.3) {
            game.triggerExtraBallBonus();
        }
    }

    private void updateGameForSpotTarget(Game game) {
        game.triggerJackPotBonus();
    }

}
