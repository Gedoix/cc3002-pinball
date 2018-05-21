package controller;

import javafx.scene.control.Tab;
import logic.bonus.DropTargetBonus;
import logic.bonus.ExtraBallBonus;
import logic.bonus.JackPotBonus;
import logic.gameelements.HitVisitor;
import logic.gameelements.Hittable;
import logic.table.DefaultTable;
import logic.table.Table;
import logic.utils.Visitor;

import java.util.ArrayList;
import java.util.List;

/**
 * Game logic controller class.
 *
 * @author Juan-Pablo Silva
 */
public class Game {

    private int ball_counter;
    private int score;
    private Table current_table;
    private List<Table> tables;
    private final ExtraBallBonus extra_ball_bonus;
    private final JackPotBonus jack_pot_bonus;
    private final DropTargetBonus drop_target_bonus;

    public Game() {
        this.score = 0;
        this.ball_counter = 1;

        tables = new ArrayList<>();

        extra_ball_bonus = new ExtraBallBonus();
        jack_pot_bonus = new JackPotBonus();
        drop_target_bonus = new DropTargetBonus();
    }

    public void hit(Hittable hittable) {
        HitVisitor visitor = new HitVisitor(this);
        hittable.accept(visitor);
        int result = visitor.getResult();
        this.addPoints(result);
    }

    public void addBall() {
        ball_counter++;
    }

    public void addPoints(int points) {
        score += points;
    }

    public void upgradeAllBumpers() {
        current_table.upgradeAllBumpers();
    }

    public int getScore() {
        return score;
    }

    public int getBall_counter() {
        return ball_counter;
    }

    public boolean containsTable(Table table) {
        return this.tables.contains(table);
    }

    public boolean containsTable(String table_name) {
        for(Table table : tables) {
            if(table.getTableName().equals(table_name)) {
                return true;
            }
        }
        return false;
    }

    public Table getCurrentTable() {
        return this.current_table;
    }

    public Table getLastAddedTable() {
        return this.tables.get(this.getAmountOfTables()-1);
    }

    public Table getTable(int table_index) {
        return this.tables.get(table_index);
    }

    public Table getTable(String table_name) throws ElementNotFoundException {
        for(Table table : tables) {
            if(table.getTableName().equals(table_name)) {
                return table;
            }
        }
        throw new ElementNotFoundException();
    }

    public void addTable(Table table) throws DuplicateNameException {
        if(!this.containsTable(table)) {
            this.tables.add(table);
        }
        else {
            throw new DuplicateNameException();
        }
    }

    public void addDefaultTable(String name, int kicker_bumpers, int pop_bumpers, int spot_targets, int drop_targets) throws DuplicateNameException {
        if(!this.containsTable(name)) {
            this.tables.add(new DefaultTable(name, kicker_bumpers,  pop_bumpers, spot_targets, drop_targets));
        }
        else {
            throw new DuplicateNameException();
        }
    }

    public void setCurrentTableToLastAdded() {
        this.setCurrentTable(this.getAmountOfTables()-1);
    }

    public void setCurrentTable(int table_index) {
        this.current_table = tables.get(table_index);
    }

    public void setCurrentTable(String table_name) throws ElementNotFoundException {
        this.current_table = this.getTable(table_name);
    }

    public boolean isPlayableCurrentTable() {
        return this.current_table.isPlayableTable();
    }

    private boolean isPlayableTable(int table_index) {
        return tables.get(table_index).isPlayableTable();
    }

    private boolean isPlayableTable(String table_name) throws ElementNotFoundException {
        return this.getTable(table_name).isPlayableTable();
    }

    private int getAmountOfTables() {
        return tables.size();
    }

    public ExtraBallBonus getExtraBallBonus() {
        return extra_ball_bonus;
    }

    public JackPotBonus getJackPotBonus() {
        return jack_pot_bonus;
    }

    public DropTargetBonus getDropTargetBonus() {
        return drop_target_bonus;
    }

    public void triggerExtraBallBonus() {
        this.extra_ball_bonus.trigger(this);
    }

    public void triggerJackPotBonus() {
        this.jack_pot_bonus.trigger(this);
    }

    public void triggerDropTargetBonus() {
        this.drop_target_bonus.trigger(this);
    }

    public class DuplicateNameException extends Exception {
    }

    public class ElementNotFoundException extends Exception {
    }
}
