package logic.utils;

import controller.Game;

/**
 * Interface for implementing the visitor pattern.
 *
 * @author Diego Ortego Prieto
 */
public interface VisitableGameElement {

    void accept(GameElementVisitor visitor, Game game);

}
