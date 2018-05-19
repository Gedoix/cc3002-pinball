package logic.bonus;

import controller.Game;

/**
 * Bonus type class to be instanced and used by {@link Game} controller.
 * When triggered it adds an extra 1000000 (1 million) points to the score
 * and upgrades all {@link logic.gameelements.bumper.Bumper} type objects in the {@link Game} object.
 *
 * @author Diego Ortego Prieto
 * @see Bonus
 * @see AbstractBonus
 * @see ExtraBallBonus
 * @see JackPotBonus
 */
public class DropTargetBonus extends AbstractBonus {

    /**
     * Amount of points to be added to the score when triggered.
     */
    private int trigger_points;

    /**
     * Constructor method.
     * Calls to {@link AbstractBonus}'s constructor implicitly.
     */
    public DropTargetBonus(){}

    /**
     * {@inheritDoc}
     * Adds an extra 1000000 (1 million) points to the score
     * and upgrades all {@link logic.gameelements.bumper.Bumper} type objects in the {@link Game} object.
     *
     * @param game The game controller object.
     */
    @Override
    void bonusBehaviour(Game game) {
        game.addPoints(trigger_points);
        game.upgradeAllBumpers();
    }
}
