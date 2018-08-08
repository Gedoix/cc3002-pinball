package gui.FXGLentities.components;

import com.almasb.fxgl.entity.component.Component;
import logic.gameelements.Hittable;

/**
 * Component class responsible for keeping a reference to a {@link Hittable} instance
 * for an {@link com.almasb.fxgl.entity.Entity}.
 *
 * @author Diego Ortego Prieto
 *
 * @see Component
 */
public class HittableComponent extends Component {

    /**
     * Reference to a hittable object.
     */
    private final Hittable hittable;

    /**
     * Constructor ensuring the hittable is initialized.
     *
     * @param linked_game_object    Hittable to store.
     */
    public HittableComponent(Hittable linked_game_object) {
        hittable = linked_game_object;
    }

    /**
     * Grants access to the hittable reference, so that changes may be made to it, or info may be extracted.
     *
     * @return  The stored hittable.
     */
    public Hittable getHittable() {
        return hittable;
    }

}
