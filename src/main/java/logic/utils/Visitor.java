package logic.utils;

import controller.Game;
import logic.gameelements.Hittable;

/**
 * Interface for implementing the visitor pattern.
 *
 * @author Diego Ortego Prieto
 */
public interface Visitor {

    /**
     * Method for visiting KickerBumper type objects.
     *
     * @param element Object to be visited.
     */
    void visit(Hittable element);

    /**
     * Method for updating {@link controller.Game} values.
     */
    void updateGameForBumper();

    void updateGameForDropTarget();

    void updateGameForSpotTarget();

    /**
     * Method for returning a score value back to {@link Game}.
     *
     * @return  Resulting game score.
     */
    int getResult();

}
