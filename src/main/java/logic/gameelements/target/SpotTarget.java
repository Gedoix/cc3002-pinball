package logic.gameelements.target;

public class SpotTarget extends AbstractTarget {

    /**
     * Default contructor method for the class.
     */
    public SpotTarget() {
        this(true);
    }

    /**
     * Constructor method to be used during testing only.
     *
     * @param active              Starting activation status of the object.
     */
    protected SpotTarget(boolean active) {
        super(0, active);
    }
}
