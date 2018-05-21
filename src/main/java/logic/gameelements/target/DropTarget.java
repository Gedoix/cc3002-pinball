package logic.gameelements.target;

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
}
