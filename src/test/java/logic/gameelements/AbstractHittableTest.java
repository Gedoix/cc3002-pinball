package logic.gameelements;

import logic.gameelements.bumper.PopBumper;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AbstractHittableTest {

    Hittable hittable;

    @Before
    public void setUp() {
        hittable = new PopBumper();
        assertFalse(hittable.wasHit());
    }

    @Test
    public void hit() {
        assertEquals(hittable.getScore(), hittable.hit());
        assertTrue(hittable.wasHit());
    }
}