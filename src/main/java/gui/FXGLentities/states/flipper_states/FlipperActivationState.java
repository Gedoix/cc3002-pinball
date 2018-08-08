package gui.FXGLentities.states.flipper_states;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.components.RotationComponent;
import com.almasb.fxgl.extra.entity.state.State;
import com.almasb.fxgl.physics.PhysicsComponent;
import gui.FXGLentities.states.StateWithOwner;

/**
 * Abstracts state class for allowing multiple rotation states (subclasses) to switch
 * fluently in a flipper's state machine, and update the rotation of the entity correctly.
 *
 * @author Diego Ortego Prieto
 *
 * @see StateWithOwner
 * @see gui.FXGLentities.PinballEntityFactory
 */
abstract class FlipperActivationState extends StateWithOwner {

    /**
     * Direction of the angular velocity with which to achieve the desired angle.
     */
    private final boolean counter_clockwise;
    /**
     * Affects the angular speed of the turning, not linearly though.
     */
    private final double speed_multiplier;
    /**
     * End offset from the rotational origin.
     * Always positive.
     */
    private final double end_angle;
    /**
     * Range of angles acceptable for stopping the rotation state.
     */
    private final double epsilon;

    /**
     * Whether or not the flipper has reached it's destination angle.
     */
    private boolean is_displaced;
    /**
     * Angular distance to reach the desired angle.
     */
    private double distance;

    /**
     * Constructor allowing the setting of an owner entity, rotational direction and desired angle.
     *
     * @param owner Entity to rotate.
     * @param counter_clockwise Angular velocity's sign.
     * @param end_angle Desired angle offset, measured from the origin.
     */
    FlipperActivationState(Entity owner, boolean counter_clockwise, double end_angle) {
        super(owner);
        this.counter_clockwise = counter_clockwise;
        this.speed_multiplier = 2.0;
        assert end_angle >= 0;
        this.end_angle = end_angle;
        this.epsilon = 0.1;
    }

    /**
     * Updates the state of displacement of the state, using the epsilon range previously defined.
     */
    private void checkIfDisplaced() {
        distance = Math.abs(owner.getComponent(RotationComponent.class).angleProperty().get() - (counter_clockwise ? -end_angle : end_angle));
        is_displaced = distance > epsilon;
    }

    /**
     * Runs when the state has just been assigned.
     *
     * Checks whether any movement will be necessary.
     *
     * @param prevState Previous state the flipper was in, irrelevant value for this purpose.
     */
    @Override
    protected void onEnter(State prevState) {
        checkIfDisplaced();
        if (is_displaced) {
            owner.getComponent(PhysicsComponent.class).setAngularVelocity((counter_clockwise ? -speed_multiplier : speed_multiplier)*distance/10);
        }
    }

    /**
     * Rotates the flipper according to the fields of the state once every frame.
     *
     * Calls {@link #checkIfDisplaced()} to know when to stop.
     *
     * @param var1  Updates per frame probably, Should be ignored.
     */
    @Override
    protected void onUpdate(double var1) {
        if (is_displaced) {
            checkIfDisplaced();
            if (!is_displaced) {
                owner.getComponent(PhysicsComponent.class).setAngularVelocity(0);
            }
            else {
                owner.getComponent(PhysicsComponent.class).setAngularVelocity((counter_clockwise ? -speed_multiplier : speed_multiplier)*distance/10);
            }
        }
    }

    /**
     * Ensures the flipper stops moving briefly before entering a new state.
     */
    @Override
    protected void onExit() {
        owner.getComponent(PhysicsComponent.class).setAngularVelocity(0);
    }

}
