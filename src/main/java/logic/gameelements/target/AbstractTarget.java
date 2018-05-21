package logic.gameelements.target;

import logic.gameelements.AbstractHittable;

/**
 * Abstract class implementing basic target behaviour.
 *
 * @author Diego Ortego Prieto
 * @see logic.gameelements.Hittable
 * @see AbstractHittable
 * @see Target
 */
public abstract class AbstractTarget extends AbstractHittable implements Target {

    private boolean active;
    private int active_score_given;

    /**
     * Constructor method to be used by subclass constructors.
     *
     * @param default_score_given   The score to be out-putted by {@link #hit()}.
     * @param active                Starting activation status of the object.
     */
    AbstractTarget(int default_score_given, boolean active) {
        super(default_score_given);
        this.active_score_given = default_score_given;
        if(active) {
            this.activate();
        }
        else {
            this.deactivate();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    abstract protected void hittableBehaviour();

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
