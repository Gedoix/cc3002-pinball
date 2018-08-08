package gui.FXGLentities.events;

import com.almasb.fxgl.entity.Entity;
import javafx.event.Event;
import javafx.event.EventType;

/**
 * Event class announcing a hit to a {@link logic.gameelements.Hittable}'s entity.
 *
 * Allows recognition of the hittable.
 *
 * @author Diego Ortego Prieto
 *
 * @see Event
 * @see logic.gameelements.Hittable
 */
public class HitEvent extends Event {

    /**
     * Sets a {@link logic.gameelements.Hittable} as the hit object.
     */
    public static final EventType<HitEvent> HITTABLE
            = new EventType<>(Event.ANY, "HIT_EVENT");

    /**
     * Sets a {@link logic.gameelements.bumper.Bumper} as the hit object.
     */
    public static final EventType<HitEvent> BUMPER
            = new EventType<>(HITTABLE, "BUMPER_HIT");

    /**
     * Sets a {@link logic.gameelements.target.Target} as the hit object.
     */
    public static final EventType<HitEvent> TARGET
            = new EventType<>(HITTABLE, "TARGET_HIT");

    /**
     * Sets a {@link logic.gameelements.bumper.KickerBumper} as the hit object.
     */
    public static final EventType<HitEvent> KICKER_BUMPER
            = new EventType<>(BUMPER, "KICKER_BUMPER_HIT");

    /**
     * Sets a {@link logic.gameelements.bumper.PopBumper} as the hit object.
     */
    public static final EventType<HitEvent> POP_BUMPER
            = new EventType<>(BUMPER, "POP_BUMPER_HIT");

    /**
     * Sets a {@link logic.gameelements.target.SpotTarget} as the hit object.
     */
    public static final EventType<HitEvent> SPOT_TARGET
            = new EventType<>(TARGET, "SPOT_TARGET_HIT");

    /**
     * Sets a {@link logic.gameelements.target.DropTarget} as the hit object.
     */
    public static final EventType<HitEvent> DROP_TARGET
            = new EventType<>(TARGET, "DROP_TARGET_HIT");

    /**
     * Entity holding the hit hittable.
     */
    private Entity hit;

    /**
     * Constructor allowing the specification of the hit entity.
     *
     * @param eventType Type of event, from the list above.
     * @param hit   Element hit.
     */
    public HitEvent(EventType<? extends Event> eventType, Entity hit) {
        super(eventType);
        this.hit = hit;
    }

    /**
     * Allows access to the hit entity.
     *
     * @return  The element hit.
     */
    public Entity getHitEntity() {
        return hit;
    }

}
