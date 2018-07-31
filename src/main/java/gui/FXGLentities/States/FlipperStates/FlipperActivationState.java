package gui.FXGLentities.States.FlipperStates;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.components.RotationComponent;
import com.almasb.fxgl.extra.entity.state.State;
import com.almasb.fxgl.physics.PhysicsComponent;
import gui.FXGLentities.States.StateWithOwner;

abstract class FlipperActivationState extends StateWithOwner {

    private final boolean counter_clockwise;
    private final double speed_multiplier;
    private final double end_angle;
    private final double epsilon;

    private boolean is_displaced;
    private double distance;

    FlipperActivationState(Entity owner, boolean counter_clockwise, double end_angle) {
        super(owner);
        this.counter_clockwise = counter_clockwise;
        this.speed_multiplier = 2.0;
        assert end_angle >= 0;
        this.end_angle = end_angle;
        this.epsilon = 0.1;
    }

    private void checkIfDisplaced() {
        //is_displaced = !owner.getComponent(RotationComponent.class).angleProperty().isEqualTo(counter_clockwise ? -end_angle : end_angle, epsilon).getValue();
        distance = Math.abs(owner.getComponent(RotationComponent.class).angleProperty().get() - (counter_clockwise ? -end_angle : end_angle));
        is_displaced = distance > epsilon;
    }

    @Override
    protected void onEnter(State prevState) {
        checkIfDisplaced();
        if (is_displaced) {
            owner.getComponent(PhysicsComponent.class).setAngularVelocity((counter_clockwise ? -speed_multiplier : speed_multiplier)*distance/10);
        }
    }

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

    @Override
    protected void onExit() {
        owner.getComponent(PhysicsComponent.class).setAngularVelocity(0);
    }

}
