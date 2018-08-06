package controller;

import logic.bonus.DropTargetBonus;
import logic.bonus.ExtraBallBonus;
import logic.bonus.JackPotBonus;
import logic.gameelements.HitVisitor;
import logic.gameelements.Hittable;
import logic.gameelements.bumper.Bumper;
import logic.gameelements.target.Target;
import logic.table.DefaultTable;
import logic.table.NullTable;
import logic.table.Table;

import java.util.List;
import java.util.Random;

/**
 * Game logic controller class.
 *
 * @author Juan-Pablo Silva
 */
public class Game {

    //  Fields

    /**
     * Total score earned since game start.
     */
    private int score;

    /**
     * Total amount of balls available.
     */
    private int ball_counter;

    /**
     * Instance of ExtraBallBonus for triggering.
     */
    private final ExtraBallBonus extra_ball_bonus;

    /**
     * Instance of JackPotBonus for triggering.
     */
    private final JackPotBonus jack_pot_bonus;

    /**
     * Instance of DropTargetBonus for triggering.
     */
    private final DropTargetBonus drop_target_bonus;

    /**
     * Currently loaded game table.
     */
    private Table current_table;

    /**
     * Pseudo random number generator for use in table creation.
     */
    private final Random generator;

    /**
     * Weather or not the class is currently being tested.
     */
    private final boolean testing;

    //  Constructors

    /**
     * PinballGameApplication constructor method for the class
     */
    public Game() {
        this(false);
    }

    /**
     * Constructor method for testing use only
     *
     * @param random_seed   Seed for the game's random number generator.
     */
    public Game(long random_seed) {
        this(true);
        this.generator.setSeed(random_seed);
    }

    /**
     * Private specific constructor.
     */
    private Game(boolean testing) {
        this.score = 0;
        this.ball_counter = 1;

        this.extra_ball_bonus = new ExtraBallBonus();
        this.jack_pot_bonus = new JackPotBonus();
        this.drop_target_bonus = new DropTargetBonus();

        this.current_table = new NullTable();

        this.generator = new Random();

        this.testing = testing;
    }

    //  Basic getter methods

    /**
     * Get the total score accumulated so far.
     *
     * @return  The current score.
     */
    public int getScore() {
        return score;
    }

    /**
     * Get the current amount of balls available.
     *
     * @return  The current ball amount counter.
     */
    public int getBallCounter() {
        return ball_counter;
    }

    /**
     * Get the instance of {@link ExtraBallBonus} the game is using.
     *
     * @return  An instance of {@link ExtraBallBonus}.
     */
    public ExtraBallBonus getExtraBallBonus() {
        return extra_ball_bonus;
    }

    /**
     * Get the instance of {@link JackPotBonus} the game is using.
     *
     * @return  An instance of {@link JackPotBonus}.
     */
    public JackPotBonus getJackPotBonus() {
        return jack_pot_bonus;
    }

    /**
     * Get the instance of {@link DropTargetBonus} the game is using.
     *
     * @return  An instance of {@link DropTargetBonus}.
     */
    public DropTargetBonus getDropTargetBonus() {
        return drop_target_bonus;
    }

    /**
     * Get the current game table.
     *
     * @return  The current instance of {@link Table} in the game.
     */
    public Table getTable() {
        return this.current_table;
    }

    //  Advanced getter methods

    /**
     * Checks if the game is finished.
     *
     * @return  true if the ball counter is 0, false if not.
     */
    public boolean isGameOver() {
        return this.getBallCounter() == 0;
    }

    /**
     * Checks if the current {@link Table} is playable.
     * Null table from initialization is not playable.
     *
     * @return  true if the table is playable, false if not.
     */
    public boolean isPlayableTable() {
        return this.current_table.isPlayableTable();
    }

    /**
     * Gets the name of the current table.
     * Null table from initialization has null name.
     *
     * @return  name of the table.
     */
    public String getTableName() {
        return this.getTable().getTableName();
    }

    /**
     * Gets the bumpers list of the current table.
     * Null table from initialization has an empty bumpers list.
     *
     * @return  List of all bumpers in the table.
     */
    public List<Bumper> getBumpers() {
        return this.getTable().getBumpers();
    }

    /**
     * Gets the targets list of the current table.
     * Null table from initialization has an empty targets list.
     *
     * @return  List of all targets in the table.
     */
    public List<Target> getTargets()    {
        return this.getTable().getTargets();
    }

    //  Setter methods

    /**
     * Sets the current game table to a new Table object.
     *
     * @param table new table for the game.
     */
    public void setTable(Table table) {
        this.current_table = table;
    }

    //  Table creation

    /**
     * Creates a new playable instance of {@link DefaultTable} with no targets,
     * randomizing the amount of PopBumpers based on a given probability.
     *
     * @param name                      name of the table.
     * @param total_bumpers             total amount of bumpers in the table.
     * @param pop_bumper_probability    probability of any bumper of being a pop bumper.
     * @return                          the new table.
     */
    public Table createRandomTableNoTargets(String name, int total_bumpers, double pop_bumper_probability) {
        return createRandomTable(name, total_bumpers, pop_bumper_probability, 0, 0);
    }

    /**
     * Creates a new playable instance of {@link DefaultTable},
     * randomizing the amount of PopBumpers based on a given probability.
     *
     * @param name                      name of the table.
     * @param total_bumpers             total amount of bumpers in the table.
     * @param pop_bumper_probability    probability of any bumper of being a pop bumper.
     * @param spot_targets              amount of spot targets.
     * @param drop_targets              amount of drop targets.
     * @return                          the new table.
     */
    public Table createRandomTable(String name, int total_bumpers, double pop_bumper_probability, int spot_targets, int drop_targets) {
        int pop_bumpers = 0;
        for(int i = 0; i < total_bumpers; i++) {
            if(generator.nextDouble() <= pop_bumper_probability) {
                pop_bumpers++;
            }
        }
        return createTable(name, total_bumpers-pop_bumpers, pop_bumpers, spot_targets, drop_targets);
    }

    /**
     * Creates a new playable instance of {@link DefaultTable}.
     *
     * @param name              name of the table.
     * @param kicker_bumpers    amount of kicker bumpers.
     * @param pop_bumpers       amount of pop bumpers.
     * @param spot_targets      amount of spot targets.
     * @param drop_targets      amount of drop targets.
     * @return                  the new table.
     */
    private Table createTable(String name, int kicker_bumpers, int pop_bumpers, int spot_targets, int drop_targets) {
        return new DefaultTable(name, kicker_bumpers, pop_bumpers, spot_targets, drop_targets);
    }

    //  Basic interactions

    /**
     * Adds the specified amount of points to the game score.
     *
     * @param points    Amount to add to the score.
     */
    public void addPoints(int points) {
        score += points;
    }

    /**
     * Adds a ball to the game's ball counter.
     */
    public void addBall() {
        ball_counter++;
    }

    /**
     * Removes a ball from the game's ball counter.
     */
    public void removeBall() {
        if(ball_counter != 0) {
            ball_counter--;
        }
    }

    /**
     * Triggers the game's instance of {@link ExtraBallBonus}, adding a ball to the game.
     */
    public void triggerExtraBallBonus() {
        this.extra_ball_bonus.trigger(this);
    }

    /**
     * Triggers the game's instance of {@link JackPotBonus}, adding 100000 (100 thousand) points to the game score.
     */
    public void triggerJackPotBonus() {
        this.jack_pot_bonus.trigger(this);
    }

    /**
     * Triggers the game's instance of {@link DropTargetBonus}, adding 1000000 (1 million) points to the game score.
     */
    private void triggerDropTargetBonus() {
        this.drop_target_bonus.trigger(this);
    }

    //  Table-dependant interactions

    /**
     *  Instantly upgrades all bumpers in the game's table.
     */
    public void upgradeAllBumpers() {
        current_table.upgradeAllBumpers();
    }

    //  Update methods for runtime behavior, currently unused, but will be implemented along with the game view

    /**
     * PinballGameApplication update method for the game, to be used once every frame.
     *
     * Should take into account positioning of the ball and all objects within the table
     * and call the {@link #hit(Hittable)} method accordingly.
     */
    public void update() {
        if (current_table.getNumberOfDropTargets() != 0 &&
                current_table.getNumberOfDropTargets() - current_table.getCurrentlyDroppedDropTargets() == 0) {
            this.triggerDropTargetBonus();
        }
    }

    /**
     * Uses double dispatch and a visitor pattern class to hit a hittable game element, and trigger any events
     * that follow, excepting the {@link DropTargetBonus}, which only gets triggered by the {@link #update()} method.
     *
     * @param hittable  The hittable game element to be hit.
     */
    public void hit(Hittable hittable) {
        HitVisitor visitor;
        if(this.testing) {
            visitor = new HitVisitor(this.generator);
        }
        else {
            visitor = new HitVisitor();
        }
        visitor.visit(hittable, this);

        int result = visitor.getResult();

        this.addPoints(result);
    }

}
