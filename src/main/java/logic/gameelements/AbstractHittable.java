package logic.gameelements;

/**
 * Abstract class implementing basic hittable behaviour.
 *
 * @author Diego Ortego Prieto
 * @see Hittable
 * @see logic.gameelements.bumper.Bumper
 * @see logic.gameelements.bumper.AbstractBumper
 * @see logic.gameelements.target.Target
 * @see logic.gameelements.target.AbstractTarget
 */
public abstract class AbstractHittable implements Hittable{
    /**
     * Default score to be given to the player when {@link #hit()} is called.
     */
    private int current_score_given;

    /**
     * Accumulative total score that has given to the player since the {@link controller.Game}'s start.
     */
    private int total_score;

    /**
     * Constructor method to be used by subclass constructors.
     *
     * @param default_score_given   The score to be out-putted by {@link #hit()}.
     */
    protected AbstractHittable(int default_score_given) {
        this.current_score_given = default_score_given;
        this.total_score = 0;
    }

    /**
     * {@inheritDoc}
     *
     * Template method that implements basic behaviour,
     * and calls {@link #hittableBehaviour()} for the specific hittable behaviour.
     *
     * @return the score the player obtained hitting the object
     */
    @Override
    public int hit() {
        this.hittableBehaviour();
        this.total_score += this.current_score_given;
        return this.current_score_given;
    }

    /**
     * Gets the current score out-putted by {@link #hit()}.
     *
     * @return Score out-putted by {@link #hit()}.
     */
    protected int getScoreGiven() {
        return this.current_score_given;
    }

    /**
     * Changes the current score out-putted by {@link #hit()}.
     *
     * @param default_score_given   The new score to be out-putted by {@link #hit()}.
     */
    protected void setScoreGiven(int default_score_given) {
        this.current_score_given = default_score_given;
    }

    /**
     * {@inheritDoc}
     *
     * @return the current score of the object when hit
     */
    @Override
    public int getScore() {
        return this.total_score;
    }

    /**
     * Manages specific hittable behaviour.
     * Called first-thing by the {@link #hit()} method.
     */
    abstract protected void hittableBehaviour();
}
