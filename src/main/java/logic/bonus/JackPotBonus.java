package logic.bonus;

import controller.Game;

/**
 * Bonus type class to be instanced and used by {@link Game} controller.
 * When triggered it adds an extra 100000 (100 thousand) points to the {@link Game} score.
 *
 * @author Diego Ortego Prieto
 * @see Bonus
 * @see AbstractBonus
 * @see ExtraBallBonus
 * @see DropTargetBonus
 */
public class JackPotBonus extends AbstractBonus {

    /**
     * Amount of points to be added to the score when triggered.
     */
    private int trigger_points;

    /**
     * Constructor method.
     * Calls to {@link AbstractBonus}'s constructor implicitly.
     */
    public JackPotBonus() {
        trigger_points = 100000;
    }

    /**
     * {@inheritDoc}
     * Adds an extra 100000 (100 thousand) points to the {@link Game} object.
     *
     * @param game The game controller object.
     */
    @Override
    void bonusBehaviour(Game game) {
        game.addPoints(trigger_points);
    }

}
