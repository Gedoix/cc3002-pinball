package logic.utils;

import controller.Game;

/**
 * Interface for implementing the visitor pattern.
 * Marks visitable Game elements.
 *
 * @author Diego Ortego Prieto
 * @see GameElementVisitor
 * @see logic.gameelements.AbstractHittable
 */
public interface VisitableGameElement {

    //  Double dispatch accept method

    /**
     * Method allowing a visitor to make changes to this object.
     *
     * @param visitor   A {@link GameElementVisitor} type object that will make changes.
     * @param game      Link to the {@link Game} object, for event triggering down the line.
     */
    void accept(GameElementVisitor visitor, Game game);

}
