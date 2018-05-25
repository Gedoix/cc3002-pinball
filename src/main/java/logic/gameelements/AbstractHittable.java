package logic.gameelements;

/**
 * Abstract class implementing basic hittable behaviour.
 * Can me visited by {@link logic.utils.GameElementVisitor} types.
 *
 * @author Diego Ortego Prieto
 * @see Hittable
 * @see logic.utils.VisitableGameElement
 * @see logic.gameelements.bumper.Bumper
 * @see logic.gameelements.bumper.AbstractBumper
 * @see logic.gameelements.target.Target
 * @see logic.gameelements.target.AbstractTarget
 */
public abstract class AbstractHittable implements Hittable{

    //  Fields

    /**
     * Score to be given to the player when {@link #hit()} is called.
     */
    private int current_score_given;

    /**
     * Remembers weather the hittable has been hit before.
     */
    private boolean been_hit;

    //  Constructor

    /**
     * Constructor method to be used by subclass constructors.
     *
     * @param default_score_given   The score to be out-putted by {@link #hit()}.
     */
    protected AbstractHittable(int default_score_given) {
        this.current_score_given = default_score_given;
        this.been_hit = false;
    }

    //  Public methods for game use, implementations of Hittable methods

    /**
     * {@inheritDoc}
     *
     * Template method that implements basic behaviour,
     * and calls {@link #beforeHitBehaviour()} for the specific hittable behaviour.
     *
     * Is called by {@link HitVisitor} on visit.
     *
     * @return the score the player obtained hitting the object
     */
    @Override
    public int hit() {
        this.beforeHitBehaviour();
        int result = getScore();
        this.been_hit = true;
        this.afterHitBehaviour();
        return result;
    }

    /**
     * {@inheritDoc}
     *
     * @return the current score of the object when hit
     */
    @Override
    public int getScore() {
        return this.current_score_given;
    }

    /**
     * {@inheritDoc}
     *
     * @return  true only if the hittable has been hit before.
     */
    @Override
    public boolean wasHit() {
        return this.been_hit;
    }

    //  Protected methods for subclass use

    /**
     * Changes the current score out-putted by {@link #hit()}.
     *
     * @param new_score   The new score to be out-putted by {@link #hit()}.
     */
    protected void setScore(int new_score) {
        this.current_score_given = new_score;
    }

    //  Methods for template pattern implementations by subclasses

    /**
     * Manages specific hittable behaviour.
     * Called first-thing by the {@link #hit()} method.
     */
    abstract protected void beforeHitBehaviour();

    /**
     * Manages specific hittable behaviour.
     * Called after calculating resulting score by the {@link #hit()} method.
     */
    abstract protected void afterHitBehaviour();

}
