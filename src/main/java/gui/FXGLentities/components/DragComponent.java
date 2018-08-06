package gui.FXGLentities.components;

import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.entity.view.EntityView;
import com.almasb.fxgl.physics.PhysicsComponent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
import kotlin.jvm.internal.Intrinsics;

public class DragComponent extends Component {

    private boolean isDragging;
    private double offsetX;
    private double offsetY;

    private EventHandler<MouseEvent> onPress = event -> {
        DragComponent component = DragComponent.this;
        component.setDragging(true);

        double mouse_x = FXGL.Companion.getInput().getMouseXWorld();
        component.offsetX = mouse_x - entity.getX();

        double mouse_y = FXGL.Companion.getInput().getMouseYWorld();
        component.offsetY = mouse_y - entity.getY();
    };
    private EventHandler<MouseEvent> onRelease = event -> {
        DragComponent component = DragComponent.this;
        component.setDragging(false);
    };

    private boolean isDragging() {
        return this.isDragging;
    }

    private void setDragging(boolean value) {
        this.isDragging = value;
    }

    public void onAdded() {
        Entity entity = this.entity;
        Intrinsics.checkExpressionValueIsNotNull(this.entity, "entity");

        EntityView view = entity.getView();
        Intrinsics.checkExpressionValueIsNotNull(view, "entity.view");

        view.setOnMousePressed(onPress);

        view.setOnMouseReleased(onRelease);

    }

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

    public void onRemoved() {
        Entity entity = this.entity;
        Intrinsics.checkExpressionValueIsNotNull(this.entity, "entity");

        EntityView view = entity.getView();
        Intrinsics.checkExpressionValueIsNotNull(view, "entity.view");

        view.setOnMousePressed(null);
        view.setOnMouseReleased(null);
    }

}
