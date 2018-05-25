package logic.gameelements.target;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AbstractTargetTest {

    Target target;

    @Before
    public void setUp() {
        target = new DropTarget();
    }

    @Test
    public void hittableBehaviour() {
        assertTrue(target.isActive());
        int before = target.hit();
        assertFalse(target.isActive());
        assertNotEquals(before, target.hit());
        assertEquals(0, target.hit());

        target.reset();

        assertTrue(target.isActive());
        assertEquals(before, target.hit());
        assertFalse(target.isActive());
        assertNotEquals(before, target.hit());
        assertEquals(0, target.hit());
    }

}