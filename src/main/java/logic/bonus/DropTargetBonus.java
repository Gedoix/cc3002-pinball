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

    //  Fields

    /**
     * Amount of points to be added to the score when triggered.
     */
    private int trigger_points;

    //  Constructor

    /**
     * Constructor method.
     * Calls to {@link AbstractBonus}'s constructor implicitly.
     */
    public DropTargetBonus(){
        this.trigger_points = 1000000;
    }

    //  AbstractBonus method implementations

    /**
     * {@inheritDoc}
     *
     * Adds an extra 1000000 (1 million) points to the score
     * and upgrades all {@link logic.gameelements.bumper.Bumper} type objects in the {@link Game} object.
     *
     * @param game The game controller object.
     */
    @Override
    protected void bonusBehaviour(Game game) {
        game.addPoints(trigger_points);
        game.upgradeAllBumpers();
    }

    /**
     * {@inheritDoc}
     *
     * @param object    Possible bonus object to be compared to.
     * @return          Weather or not the objects are DropTargetBonuses.
     */
    @Override
    public boolean equals(Object object) {
        return object instanceof DropTargetBonus;
    }

}