package gui.FXGLentities.Components;

import com.almasb.fxgl.entity.component.Component;
import logic.gameelements.Hittable;

public class HittableComponent extends Component {

    private final Hittable hittable;

    public HittableComponent(Hittable linked_game_object) {
        hittable = linked_game_object;
    }

    public Hittable getHittable() {
        return hittable;
    }

    @Override
    public void onAdded() {
        super.onAdded();
    }

    @Override
    public void onUpdate(double tpf) {
        super.onUpdate(tpf);
    }

    @Override
    public void onRemoved() {
        super.onRemoved();
    }

    @Override
    public String toString() {
        return super.toString();
    }

}
