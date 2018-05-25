package logic.gameelements.target;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SpotTargetTest {

    private SpotTarget target;
    private SpotTarget un_active_target;

    @Before
    public void setUp() {
        target = new SpotTarget();
        un_active_target = new SpotTarget(false);
    }

    @Test
    public void spotTarget() {
        assertTrue(target.isActive());
        assertFalse(un_active_target.isActive());
        assertEquals(0, target.hit());
        assertFalse(target.isActive());
        assertEquals(0, target.hit());
        assertEquals(0, un_active_target.hit());
    }
}