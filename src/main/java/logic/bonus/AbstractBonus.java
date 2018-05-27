package logic.bonus;

import controller.Game;

/**
 * Abstract class implementing basic bonus behaviour.
 *
 * @author Diego Ortego Prieto
 * @see Bonus
 * @see ExtraBallBonus
 * @see JackPotBonus
 * @see DropTargetBonus
 */
public abstract class AbstractBonus extends Object implements Bonus {

    //  Fields

    /**
     * Counts the amount of times the bonus has been triggered.
     */
    private int trigger_counter;

    //  Constructor

    /**
     * Constructor method to be summoned by sub-class constructors.
     */
    AbstractBonus(){
        trigger_counter = 0;
    }

    //  Bonus method implementations

    /**
     * {@inheritDoc}
     *
     * Considered a basic behaviour, shouldn't be re-implemented in sub-classes.
     *
     * @return
     */
    @Override
    public int timesTriggered(){
        return trigger_counter;
    }

    /**
     * {@inheritDoc}
     *
     * Template method that calls the {@link  #bonusBehaviour(Game)} method for specific actions related to {@link Game}.
     *
     * @see #bonusBehaviour(Game)
     * @param game the game controller object
     */
    @Override
    public void trigger(Game game){
        trigger_counter++;
        bonusBehaviour(game);
    }

    //  Methods for template pattern implementations by subclasses

    /**
     * Trigger the specific action the bonus does and applies it to the {@link Game} object.
     *
     * @param game The game controller object.
     */
    abstract void bonusBehaviour(Game game);

    //  Testing methods for subclasses

    /**
     * Returns true if both objects are of the same sub-class,
     * since there's no difference between instances of the same Bonus.
     *
     * @param object    Possible bonus object to be compared to.
     * @return          Weather or not the objects are equal.
     */
    @Override
    public abstract boolean equals(Object object);

}
