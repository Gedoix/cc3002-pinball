package logic.table;

/**
 * Null empty Table class.
 * Returns false to call to the {@link #isPlayableTable()} method.
 *
 * @author Diego Ortego Prieto
 * @see Table
 * @see AbstractTable
 * @see DefaultTable
 */
public class NullTable extends AbstractTable {

    //  Constructor

    /**
     * Constructor method for the class.
     */
    public NullTable() {
        super("");
    }

}
