package logic.gameelements.bumper;

import controller.Game;
import logic.utils.GameElementVisitor;

/**
 * Bumper type class for use in {@link controller.Game}.
 *
 * PopBumpers give a score of 100 points on hit.
 * PopBumpers can be upgraded after 3 hits, changing the given score to 300 points.
 *
 * @author Diego Ortego Prieto
 * @see Bumper
 * @see AbstractBumper
 * @see KickerBumper
 */
public class PopBumper extends AbstractBumper {

    //  Constructors

    /**
     * Default constructor method for the class.
     */
    public PopBumper() {
        this(false);
    }

    /**
     * Constructor method to be used during testing only.
     *
     * @param upgraded  Initial upgrade status of the object.
     */
    public PopBumper(boolean upgraded) {
        super(100, 300, 3, upgraded);
    }

    //  VisitableGameElement method implementation

    /**
     * {@inheritDoc}
     *
     * @param visitor A {@link GameElementVisitor} type object that will make changes.
     * @param game    Link to the {@link Game} object, for event triggering down the line.
     */
    @Override
    public void accept(GameElementVisitor visitor, Game game) {
        visitor.visitingPopBumper(this, game);
    }
}
