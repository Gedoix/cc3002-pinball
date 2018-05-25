package logic.gameelements.target;

import controller.Game;
import logic.utils.GameElementVisitor;

/**
 * Target type class for use in {@link Game}.
 *
 * SpotTargets give a score of 0 points when hit, regardless of their activation state.
 * They deactivate on hit.
 *
 * @author Diego Ortego Prieto
 * @see Target
 * @see AbstractTarget
 * @see DropTarget
 */
public class SpotTarget extends AbstractTarget {

    //  Constructors

    /**
     * Default constructor method for the class.
     */
    public SpotTarget() {
        this(true);
    }

    /**
     * Constructor method to be used during testing only.
     *
     * @param active    Starting activation status of the object.
     */
    public SpotTarget(boolean active) {
        super(0, active);
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
        visitor.visitingSpotTarget(this, game);
    }
}
