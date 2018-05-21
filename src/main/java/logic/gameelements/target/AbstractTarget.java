package logic.gameelements.target;

import logic.gameelements.AbstractHittable;

public class AbstractTarget extends AbstractHittable implements Target {
    /**
     * Constructor method to be used by subclass constructors.
     *
     * @param default_score_given The score to be out-putted by {@link #hit()}.
     */
    protected AbstractTarget(int default_score_given) {
        super(default_score_given);
    }

    /**
     * Manages specific hittable behaviour.
     * Called first-thing by the {@link #hit()} method.
     */
    @Override
    protected void hittableBehaviour() {

    }

    /**
     * Gets whether the target is currently active or not.
     *
     * @return true if the target is active, false otherwise
     */
    @Override
    public boolean isActive() {
        return false;
    }

    /**
     * Resets the state of a target making it active again.
     */
    @Override
    public void reset() {

    }
}
