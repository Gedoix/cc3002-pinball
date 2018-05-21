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

public abstract class AbstractTable implements Table {

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
    private final List<SpotTarget> spot_targets;
    private final List<DropTarget> drop_targets;
    private List<Target> targets;

    AbstractTable(String name) {
        this.name = name;
        this.bumpers = new LinkedList<>();
        this.spot_targets = new LinkedList<>();
        this.drop_targets = new LinkedList<>();
        this.targets = new ArrayList<>();
    }

    void addKickerBumper() {
        this.bumpers.add(new KickerBumper());
    }

    void addPopBumper() {
        this.bumpers.add(new PopBumper());
    }

    void addSpotTarget() {
        this.spot_targets.add(new SpotTarget());
    }

    void addDropTarget() {
        this.drop_targets.add(new DropTarget());
    }

    void setPlayableTrue() {
        this.playable = true;
    }

    /**
     * Gets the table name.
     *
     * @return the table's name
     */
    @Override
    public String getTableName() {
        return this.name;
    }

    /**
     * Gets the number of {@link DropTarget} in the table.
     *
     * @return the number of DropTargets in the table
     */
    @Override
    public int getNumberOfDropTargets() {
        return drop_targets.size();
    }

    /**
     * Gets the number of {@link DropTarget} that are currently dropped or inactive.
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
     * Gets the {@link List} of {@link Bumper}s in the table.
     *
     * @return the bumpers in the table
     */
    @Override
    public List<Bumper> getBumpers() {
        return bumpers;
    }

    /**
     * Gets the {@link List} of {@link Target}s in the table.
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
     * Resets all {@link DropTarget} in the table. Make them active.
     */
    @Override
    public void resetDropTargets() {
        for(DropTarget target : drop_targets) {
            target.reset();
        }
    }

    /**
     * Upgrade all {@link Bumper}s in the table.
     */
    @Override
    public void upgradeAllBumpers() {
        for(Bumper bumper : bumpers) {
            bumper.upgrade();
        }
    }

    /**
     * Gets whether the table is playable or not.
     *
     * @return true if the table is playable, false otherwise
     */
    @Override
    public boolean isPlayableTable() {
        return this.playable;
    }
}
