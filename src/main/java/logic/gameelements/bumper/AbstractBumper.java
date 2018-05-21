package logic.gameelements.bumper;

import logic.gameelements.AbstractHittable;

/**
 * Abstract class implementing basic bumper behaviour.
 *
 * @author Diego Ortego Prieto
 * @see logic.gameelements.Hittable
 * @see AbstractHittable
 * @see Bumper
 * @see KickerBumper
 * @see PopBumper
 */
public abstract class AbstractBumper extends AbstractHittable implements Bumper{

    /**
     * Value of the answer to "is the bumper upgraded?"
     * @see #upgrade()
     * @see #downgrade()
     */
    private boolean upgraded;

    /**
     * Amount of hits left for the bumper to upgrade.
     *
     * @see #remainingHitsToUpgrade()
     * @see #total_hits_to_upgrade
     * @see #hit()
     */
    private int hits_left_to_upgrade;

    /**
     * Total amount of hits needed for the bumper to upgrade before it gets hit for the first time.
     *
     * @see #hits_left_to_upgrade
     */
    private final int total_hits_to_upgrade;
    /**
     * Score out-putted on {@link #hit()} when not upgraded.
     *
     * @see #default_score_given
     */
    private final int not_upgraded_score_given;
    /**
     * Score out-putted on {@link #hit()} when upgraded.
     *
     * @see #default_score_given
     */
    private final int upgraded_score_given;

    /**
     * Constructor method to be used by subclass constructors.
     *
     * @param default_score_given   Score to be out-putted on {@link #hit()} when not upgraded.
     * @param upgraded_score_given  Score to be out-putted on {@link #hit()} when upgraded.
     * @param total_hits_to_upgrade Total amount of hits needed for the bumper to upgrade.
     * @param upgraded              Initial upgrade status of the bumper.
     */
    AbstractBumper(int default_score_given, int upgraded_score_given, int total_hits_to_upgrade, boolean upgraded) {
        super(default_score_given);
        this.not_upgraded_score_given = this.getScoreGiven();
        this.upgraded_score_given = upgraded_score_given;
        this.total_hits_to_upgrade = total_hits_to_upgrade;
        if (upgraded) {
            this.upgrade();
        }
        else {
            this.upgraded = false;
        }
        hits_left_to_upgrade = this.total_hits_to_upgrade;
    }

    /**
     * {@inheritDoc}
     *
     * Makes sure the object upgrades when needed.
     */
    @Override
    protected void hittableBehaviour() {
        if (hits_left_to_upgrade != 0) {
            if (hits_left_to_upgrade == 1) {
                this.upgrade();
            }
            hits_left_to_upgrade--;
        }
    }

    /**
     * {@inheritDoc}
     *
     * @return the remaining hits to upgrade the bumper
     */
    @Override
    public int remainingHitsToUpgrade() {
        return this.hits_left_to_upgrade;
    }

    /**
     * {@inheritDoc}
     *
     * @return true if the bumper is upgraded, false otherwise
     */
    @Override
    public boolean isUpgraded() {
        return upgraded;
    }

    /**
     * {@inheritDoc}
     * And setting {@link #hits_left_to_upgrade} to 0.
     */
    @Override
    public void upgrade() {
        this.upgraded = true;
        this.setScoreGiven(this.upgraded_score_given);
        this.hits_left_to_upgrade = 0;
    }

    /**
     * {@inheritDoc}
     * And resetting {@link #hits_left_to_upgrade} to it's original value.
     */
    @Override
    public void downgrade() {
        this.upgraded = false;
        this.setScoreGiven(this.not_upgraded_score_given);
        this.hits_left_to_upgrade = this.total_hits_to_upgrade;
    }
}
