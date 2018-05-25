package logic.gameelements.target;

import logic.gameelements.AbstractHittable;

/**
 * Abstract class implementing basic target behaviour.
 *
 * @author Diego Ortego Prieto
 * @see logic.gameelements.Hittable
 * @see AbstractHittable
 * @see Target
 * @see DropTarget
 * @see SpotTarget
 */
public abstract class AbstractTarget extends AbstractHittable implements Target {

    //  Fields

    /**
     * Value of "Is the target active?"
     *
     * @see #activate()
     * @see #deactivate()
     */
    private boolean active;

    /**
     * Score out-putted on {@link #hit()} when active.
     *
     * @see #current_score_given
     */
    private final int active_score_given;

    //  Constructor

    /**
     * Constructor method to be used by subclass constructors.
     *
     * @param score_given_when_active   The score to be out-putted by {@link #hit()} when target is active.
     * @param active                    Starting activation status of the object.
     */
    AbstractTarget(int score_given_when_active, boolean active) {
        super(score_given_when_active);
        this.active_score_given = score_given_when_active;
        if(active) {
            this.activate();
        }
        else {
            this.deactivate();
        }
    }

    //  Hittable method overriding

    /**
     * {@inheritDoc}
     */
    @Override
    protected void hittableBehaviour(){}

    //  Target method implementations

    /**
     * {@inheritDoc}
     *
     * @return true if the target is active, false otherwise
     */
    @Override
    public boolean isActive() {
        return this.active;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void reset() {
        this.activate();
    }

    //  Private utility methods for the class

    /**
     * Activates the target.
     */
    private void activate() {
        this.active = true;
        this.setScoreGiven(this.active_score_given);
    }

    /**
     * De-activates the target.
     */
    private void deactivate() {
        this.active = false;
        this.setScoreGiven(0);
    }
}
