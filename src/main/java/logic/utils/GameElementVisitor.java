package logic.utils;

import controller.Game;
import logic.gameelements.bumper.Bumper;
import logic.gameelements.bumper.KickerBumper;
import logic.gameelements.bumper.PopBumper;
import logic.gameelements.target.DropTarget;
import logic.gameelements.target.SpotTarget;

/**
 * Interface for implementing the visitor pattern.
 *
 * @author Diego Ortego Prieto
 */
public interface GameElementVisitor {

    /**
     * Method for visiting certain game elements, implementation includes double dispatch.
     * The parameter 'game' allows the visitor to trigger game events if needed.
     *
     * @param element   Visitable element to be altered.
     * @param game      Link to the {@link Game} object calling this method, for game event triggering.
     */
    void visit(VisitableGameElement element, Game game);

    /**
     * Visiting method for {@link KickerBumper} type objects.
     *
     * @param kickerBumper  Element to be visited.
     * @param game          Link to the {@link Game} object calling this method, for game event triggering.
     */
    void visitingKickerBumper(KickerBumper kickerBumper, Game game);

    /**
     * Visiting method for {@link PopBumper} type objects.
     *
     * @param popBumper     Visitable element to be altered.
     * @param game          Link to the {@link Game} object calling this method, for game event triggering.
     */
    void visitingPopBumper(PopBumper popBumper, Game game);

    /**
     * Visiting method for {@link DropTarget} type objects.
     *
     * @param dropTarget    Visitable element to be altered.
     * @param game          Link to the {@link Game} object calling this method, for game event triggering.
     */
    void visitingDropTarget(DropTarget dropTarget, Game game);

    /**
     * Visiting method for {@link SpotTarget} type objects.
     *
     * @param spotTarget    Visitable element to be altered.
     * @param game          Link to the {@link Game} object calling this method, for game event triggering.
     */
    void visitingSpotTarget(SpotTarget spotTarget, Game game);

}
