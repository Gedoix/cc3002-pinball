package logic.gameelements.target;

import controller.Game;
import logic.utils.GameElementVisitor;

public class DropTarget extends AbstractTarget {

    /**
     * Default constructor method for the class.
     */
    public DropTarget() {
        this(true);
    }

    /**
     * Constructor method to be used by subclass constructors.
     *
     * @param active              Starting activation status of the object.
     */
    protected DropTarget(boolean active) {
        super(100, active);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void hittableBehaviour() {
    }

    @Override
    public void accept(GameElementVisitor visitor, Game game) {
        visitor.visitingDropTarget(this, game);
    }
}
