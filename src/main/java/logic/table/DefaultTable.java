package logic.table;

/**
 * Table type class for use in {@link controller.Game}
 *
 * @author Diego Ortego Prieto
 * @see Table
 * @see AbstractTable
 */
public class DefaultTable extends AbstractTable {

    //  Constructor

    /**
     * Constructor method for the class.
     *
     * @param name              The table's name.
     * @param kicker_bumpers    Total number of KickerBumpers in the table.
     * @param pop_bumpers       Total number of PopBumpers in the table.
     * @param spot_targets      Total number of SpotTargets in the table.
     * @param drop_targets      Total number of DropTargets in the table.
     */
    public DefaultTable(String name, int kicker_bumpers, int pop_bumpers, int spot_targets, int drop_targets) {
        super(name);

        for(int i = 0; i < kicker_bumpers; i++) {
            this.addKickerBumper();
        }
        for(int i = 0; i < pop_bumpers; i++) {
            this.addPopBumper();
        }
        for(int i = 0; i < spot_targets; i++) {
            this.addSpotTarget();
        }
        for(int i = 0; i < drop_targets; i++) {
            this.addDropTarget();
        }

        this.setPlayableTrue();
    }

}