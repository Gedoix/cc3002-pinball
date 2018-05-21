package logic.table;

public class DefaultTable extends AbstractTable {

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
