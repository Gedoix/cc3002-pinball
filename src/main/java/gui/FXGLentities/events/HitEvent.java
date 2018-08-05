package gui.FXGLentities.events;

import com.almasb.fxgl.entity.Entity;
import javafx.event.Event;
import javafx.event.EventType;

public class HitEvent extends Event {

    public static final EventType<HitEvent> HITTABLE
            = new EventType<>(Event.ANY, "HIT_EVENT");

    public static final EventType<HitEvent> BUMPER
            = new EventType<>(HITTABLE, "BUMPER_HIT");

    public static final EventType<HitEvent> TARGET
            = new EventType<>(HITTABLE, "TARGET_HIT");

    public static final EventType<HitEvent> KICKER_BUMPER
            = new EventType<>(BUMPER, "KICKER_BUMPER_HIT");

    public static final EventType<HitEvent> POP_BUMPER
            = new EventType<>(BUMPER, "POP_BUMPER_HIT");

    public static final EventType<HitEvent> SPOT_TARGET
            = new EventType<>(TARGET, "SPOT_TARGET_HIT");

    public static final EventType<HitEvent> DROP_TARGET
            = new EventType<>(TARGET, "DROP_TARGET_HIT");

    private Entity hit;

    public HitEvent(EventType<? extends Event> eventType, Entity hit) {
        super(eventType);
        this.hit = hit;
    }

    public Entity getHitEntity() {
        return hit;
    }

}
