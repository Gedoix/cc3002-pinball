package logic.gameelements.target;

import controller.Game;
import logic.utils.GameElementVisitor;

/**
 * Target type class for use in {@link Game}.
 *
 * DropTargets give a score of 100 points when hit and active, and 0 when not active.
 * They deactivate on hit.
 *
 * @author Diego Ortego Prieto
 * @see Target
 * @see AbstractTarget
 * @see SpotTarget
 */
public class DropTarget extends AbstractTarget {

    //  Constructors

    /**
     * Default constructor method for the class.
     */
    public DropTarget() {
        this(true);
    }

    /**
     * Constructor method to be used during testing only.
     *
     * @param active    Starting activation status of the object.
     */
    public DropTarget(boolean active) {
        super(100, active);
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
        visitor.visitingDropTarget(this, game);
    }
}
