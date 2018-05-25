package logic.table;

import logic.gameelements.bumper.Bumper;
import logic.gameelements.bumper.KickerBumper;
import logic.gameelements.bumper.PopBumper;
import logic.gameelements.target.DropTarget;
import logic.gameelements.target.SpotTarget;
import logic.gameelements.target.Target;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Abstract class implementing basic Table behaviours.
 *
 * @author Diego Ortego Prieto
 * @see Table
 * @see DefaultTable
 */
public abstract class AbstractTable implements Table {

    //  Fields

    /**
     * Name of the table.
     */
    private final String name;

    /**
     * Is the table playable.
     */
    private boolean playable;

    /**
     * All bumpers are stored here, regardless of type.
     */
    private final List<Bumper> bumpers;

    /**
     * All SpotTargets are store here.
     */
    private final List<SpotTarget> spot_targets;

    /**
     * All DropTargets are stored here.
     */
    private final List<DropTarget> drop_targets;

    /**
     * All Targets are stored here, regardless of type.
     */
    private List<Target> targets;

    //  Constructor

    /**
     * Constructor method for use by subclasses.
     *
     * @param name  Name of the table.
     */
    AbstractTable(String name) {
        this.name = name;
        this.playable = false;
        this.bumpers = new LinkedList<>();
        this.spot_targets = new LinkedList<>();
        this.drop_targets = new LinkedList<>();
        this.targets = new ArrayList<>();
    }

    //  Table method implementations

    /**
     * {@inheritDoc}
     *
     * @return the table's name
     */
    @Override
    public String getTableName() {
        return this.name;
    }

    /**
     * {@inheritDoc}
     *
     * @return the number of DropTargets in the table
     */
    @Override
    public int getNumberOfDropTargets() {
        return drop_targets.size();
    }

    /**
     * {@inheritDoc}
     *
     * @return the number of DropTargets that are currently inactive
     */
    @Override
    public int getCurrentlyDroppedDropTargets() {
        int counter = 0;
        for(DropTarget target : drop_targets) {
            if(!target.isActive()) {
                counter++;
            }
        }
        return counter;
    }

    /**
     * {@inheritDoc}
     *
     * @return the bumpers in the table
     */
    @Override
    public List<Bumper> getBumpers() {
        return bumpers;
    }

    /**
     * {@inheritDoc}
     *
     * @return the targets in the table
     */
    @Override
    public List<Target> getTargets() {
        targets.clear();
        targets.addAll(spot_targets);
        targets.addAll(drop_targets);
        return targets;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void resetDropTargets() {
        for(DropTarget target : drop_targets) {
            target.reset();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void upgradeAllBumpers() {
        for(Bumper bumper : bumpers) {
            bumper.upgrade();
        }
    }

    /**
     * {@inheritDoc}
     *
     * @return true if the table is playable, false otherwise
     */
    @Override
    public boolean isPlayableTable() {
        return this.playable;
    }

    //  Utility class methods

    /**
     * Adds a KickerBumper object to the table.
     */
    void addKickerBumper() {
        this.bumpers.add(new KickerBumper());
    }

    /**
     * Adds a PopBumper object to the table.
     */
    void addPopBumper() {
        this.bumpers.add(new PopBumper());
    }

    /**
     * Adds a SpotTarget object to the table.
     */
    void addSpotTarget() {
        this.spot_targets.add(new SpotTarget());
    }

    /**
     * Adds a DropTarget object to the table.
     */
    void addDropTarget() {
        this.drop_targets.add(new DropTarget());
    }

    /**
     * Sets the table object to playable permanently.
     */
    void setPlayableTrue() {
        this.playable = true;
    }

}
