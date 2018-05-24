package logic.gameelements.bumper;

import controller.Game;
import logic.utils.GameElementVisitor;

/**
 * Bumper type class for use in {@link controller.Game}.
 *
 * KickerBumpers give a score of 500 points on hit.
 * KickerBumpers can be upgraded after 5 hits, changing the given score to 1000 points.
 *
 * @author Diego Ortego Prieto
 * @see Bumper
 * @see AbstractBumper
 * @see PopBumper
 */
public class KickerBumper extends AbstractBumper {

    /**
     * Default constructor method for the class.
     */
    public KickerBumper() {
        this(false);
    }

    /**
     * Constructor method to be used during testing only.
     *
     * @param upgraded  Initial upgrade status of the object.
     */
    protected KickerBumper(boolean upgraded) {
        super(500, 1000, 5, upgraded);
    }

    @Override
    public void accept(GameElementVisitor visitor, Game game) {
        visitor.visitingKickerBumper(this, game);
    }
}
