package logic.gameelements.target;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DropTargetTest {

    private DropTarget target;
    private DropTarget un_active_target;

    @Before
    public void setUp() {
        target = new DropTarget();
        un_active_target = new DropTarget(false);
    }

    @Test
    public void dropTarget() {
        assertTrue(target.isActive());
        assertFalse(un_active_target.isActive());
        assertEquals(100, target.hit());
        assertFalse(target.isActive());
        assertEquals(0, target.hit());
        assertEquals(0, un_active_target.hit());
    }
}