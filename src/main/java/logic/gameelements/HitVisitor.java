package logic.gameelements;

import controller.Game;
import logic.gameelements.bumper.Bumper;
import logic.gameelements.bumper.KickerBumper;
import logic.gameelements.bumper.PopBumper;
import logic.gameelements.target.DropTarget;
import logic.gameelements.target.SpotTarget;
import logic.utils.GameElementVisitor;
import logic.utils.VisitableGameElement;

import java.util.Random;

/**
 * Visitor class for hitting hittable Game elements.
 *
 * @author Diego Ortego Prieto
 * @see GameElementVisitor
 * @see VisitableGameElement
 * @see KickerBumper
 * @see PopBumper
 * @see DropTarget
 * @see SpotTarget
 */
public class HitVisitor implements GameElementVisitor {

    //  Fields

    /**
     * Random number generator for use in some methods.
     *
     * @see Random
     */
    private final Random generator;

    /**
     * Resulting score after a successful hit.
     */
    private int result;

    //  Constructors

    /**
     * Construction method for the class.
     * Sets the random seed to null, so the {@link Random} class may produce one on it's own.
     */
    public HitVisitor() {
        this.result = 0;
        this.generator = new Random();
    }

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
        this();
        this.generator.setSeed(random_seed);
    }

    //  Getter for score result

    /**
     * Method for returning a score value back to {@link Game}.
     *
     * @return  Resulting game score.
     */
    public int getResult() {
        return result;
    }

    //  GameElementVisitor method overriding

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

    //  Private "inside" behaviours

    /**
     * Handles hit behaviour for both bumper types.
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

    /**
     * Uses a random number generator to ensure {@link logic.bonus.ExtraBallBonus} only triggers on a 0.1 probability.
     *
     * @param game  Link to the {@link Game} object calling this method, for Extra Ball Bonus triggering.
     */
    private void updateGameForBumper(Game game) {
        if(this.generator.nextFloat() <= 0.1) {
            game.triggerExtraBallBonus();
        }
    }

    /**
     * Uses a random number generator to ensure {@link logic.bonus.ExtraBallBonus} only triggers on a 0.3 probability.
     *
     * @param game  Link to the {@link Game} object calling this method, for Extra Ball Bonus triggering.
     */
    private void updateGameForDropTarget(Game game) {
        if(this.generator.nextFloat() <= 0.3) {
            game.triggerExtraBallBonus();
        }
    }

    /**
     * Triggers {@link logic.bonus.JackPotBonus} inside game.
     *
     * @param game  Link to the {@link Game} object calling this method, for Jack Pot Bonus triggering.
     */
    private void updateGameForSpotTarget(Game game) {
        game.triggerJackPotBonus();
    }

}
