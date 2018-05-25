package logic.gameelements;

import logic.utils.VisitableGameElement;

/**
 * Interface that represents a hittable object.
 *
 * <p>Objects that are game elements should implement this interface.</p>
 *
 * @author Juan-Pablo Silva
 * @see AbstractHittable
 * @see logic.utils.VisitableGameElement
 * @see logic.gameelements.bumper.Bumper
 * @see logic.gameelements.target.Target
 */
public interface Hittable extends VisitableGameElement {
    /**
     * Defines that an object have been hit.
     * Implementations should consider the events that a hit to an object can trigger.
     *
     * @return the score the player obtained hitting the object
     */
    int hit();

    /**
     * Defines that a hittable object has to have a score when it is hit.
     *
     * @return the current score of the object when hit
     */
    int getScore();

    /**
     * Gives the object the capacity to remember if it's been hit at some point, for testing purposes.
     *
     * @author Diego Ortego Prieto
     *
     * @return  true only if the hittable has been hit before.
     */
    boolean wasHit();
}
