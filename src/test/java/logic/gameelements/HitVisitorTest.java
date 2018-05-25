package logic.gameelements;

import controller.Game;
import logic.gameelements.bumper.KickerBumper;
import logic.gameelements.bumper.PopBumper;
import logic.gameelements.target.DropTarget;
import logic.gameelements.target.SpotTarget;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class HitVisitorTest {

    private Game game;
    private HitVisitor visitor;
    private Hittable hittable1;
    private Hittable hittable2;
    private Hittable hittable3;
    private Hittable hittable4;

    @Before
    public void setUp() {
        game = new Game();
        visitor = new HitVisitor(0);
        hittable1 = new KickerBumper();
        hittable2 = new PopBumper();
        hittable3 = new DropTarget();
        hittable4 = new SpotTarget();
        assertFalse(hittable1.wasHit());
        assertFalse(hittable2.wasHit());
        assertFalse(hittable3.wasHit());
        assertFalse(hittable4.wasHit());
    }

    @Test
    public void visitingKickerBumper() {
        visitor.visit(hittable1, game);
        assertEquals(500, visitor.getResult());
        assertTrue(hittable1.wasHit());
    }

    @Test
    public void visitingPopBumper() {
        visitor.visit(hittable2, game);
        assertEquals(100, visitor.getResult());
        assertTrue(hittable2.wasHit());
    }

    @Test
    public void visitingDropTarget() {
        visitor.visit(hittable3, game);
        assertEquals(100, visitor.getResult());
        assertTrue(hittable3.wasHit());
    }

    @Test
    public void visitingSpotTarget() {
        visitor.visit(hittable4, game);
        assertEquals(0, visitor.getResult());
        assertTrue(hittable4.wasHit());
    }
}