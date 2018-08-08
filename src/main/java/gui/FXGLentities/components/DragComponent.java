package gui.FXGLentities.components;

import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.entity.view.EntityView;
import com.almasb.fxgl.physics.PhysicsComponent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
import kotlin.jvm.internal.Intrinsics;

/**
 * Re-factoring of the {@link com.almasb.fxgl.extra.entity.components.DraggableComponent} class,
 * produced from it's decompiled code, that allows for physics-enabled entity drag and drop manipulation.
 *
 * Added for app alpha testing purposes.
 *
 * @author Diego Ortego Prieto
 * And indirectly
 * @author Almas Baimagambetov (almaslvl@gmail.com)
 *
 * @see com.almasb.fxgl.extra.entity.components.DraggableComponent
 * @see Component
 */
public class DragComponent extends Component {

    /**
     * Keeps track of if the entity is being dragged at the moment.
     */
    private boolean isDragging = false;
    /**
     * Distance traveled in the horizontal axis.
     */
    private double offsetX = 0.0;
    /**
     * Distance traveled in the vertical axis.
     */
    private double offsetY = 0.0;

    /**
     * Starts dragging the entity.
     */
    private EventHandler<MouseEvent> onPress = event -> {
        setDragging(true);

        double mouse_x = FXGL.Companion.getInput().getMouseXWorld();
        offsetX = mouse_x - entity.getX();

        double mouse_y = FXGL.Companion.getInput().getMouseYWorld();
        offsetY = mouse_y - entity.getY();
    };
    /**
     * Stops the dragging process.
     */
    private EventHandler<MouseEvent> onRelease = event -> setDragging(false);

    /**
     * Checks if the entity is being dragged.
     *
     * @return  Whether dragging is in progress.
     */
    private boolean isDragging() {
        return this.isDragging;
    }

    /**
     * Changes the dragging state.
     *
     * @param value New dragging state value.
     */
    private void setDragging(boolean value) {
        this.isDragging = value;
    }

    /**
     * Ensures the entity's view allows the component to access the mouse, and runs the corresponding preparations.
     */
    public void onAdded() {
        Intrinsics.checkExpressionValueIsNotNull(entity, "entity");

        EntityView view = entity.getView();
        Intrinsics.checkExpressionValueIsNotNull(view, "entity.view");

        view.setOnMousePressed(onPress);

        view.setOnMouseReleased(onRelease);

    }

    /**
     * Repositions the dragged entity according to the mouse position on every frame update.
     *
     * @param tpf   Times per frame, will be ignored.
     */
    public void onUpdate(double tpf) {
        if (this.isDragging()) {
            PhysicsComponent component = entity.getComponent(PhysicsComponent.class);
            if (component != null) {
                component.reposition(new Point2D(FXGL.Companion.getInput().getMouseXWorld() - this.offsetX, FXGL.Companion.getInput().getMouseYWorld() - this.offsetY));
                component.setLinearVelocity(0, 0);
            }
            else {
                this.entity.setPosition(FXGL.Companion.getInput().getMouseXWorld() - this.offsetX, FXGL.Companion.getInput().getMouseYWorld() - this.offsetY);
            }
        }
    }

    /**
     * Ensures the entity's view stops caring about the presence of the DragComponent, allowing it to be deleted later.
     */
    public void onRemoved() {
        Intrinsics.checkExpressionValueIsNotNull(entity, "entity");

        EntityView view = entity.getView();
        Intrinsics.checkExpressionValueIsNotNull(view, "entity.view");

        view.setOnMousePressed(null);
        view.setOnMouseReleased(null);
    }

}
