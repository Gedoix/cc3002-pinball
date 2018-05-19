package logic.bonus;

import controller.Game;

/**
 * Bonus type class to be instanced and used by {@link Game} controller.
 * When triggered it adds an extra ball to the {@link Game}.
 *
 * @author Diego Ortego Prieto
 * @see Bonus
 * @see AbstractBonus
 * @see JackPotBonus
 * @see DropTargetBonus
 */
public class ExtraBallBonus extends AbstractBonus {

    /**
     * Constructor method.
     * Calls to {@link AbstractBonus}'s constructor implicitly.
     */
    public ExtraBallBonus() {}

    /**
     * {@inheritDoc}
     * Adds an extra ball to the {@link Game} object.
     *
     * @param game The game controller object.
     */
    @Override
    void bonusBehaviour(Game game) {
        game.addBall();
    }

}
