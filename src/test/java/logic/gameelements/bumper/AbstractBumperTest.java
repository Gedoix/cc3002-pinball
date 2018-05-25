package logic.gameelements.bumper;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AbstractBumperTest {

    Bumper bumper;

    @Before
    public void setUp() {
        bumper = new PopBumper();
    }

    @Test
    public void hittableBehaviour() {
        assertFalse(bumper.isUpgraded());
        int un_upgraded_score = bumper.hit();

        for(int i = bumper.remainingHitsToUpgrade(); i > 1; i--) {
            assertFalse(bumper.isUpgraded());
            assertEquals(un_upgraded_score, bumper.hit());
        }

        assertFalse(bumper.isUpgraded());
        assertNotEquals(un_upgraded_score, bumper.hit());

        assertTrue(bumper.isUpgraded());
        assertEquals(0, bumper.remainingHitsToUpgrade());
        assertNotEquals(un_upgraded_score, bumper.hit());

        bumper.downgrade();

        assertFalse(bumper.isUpgraded());
        assertNotEquals(0, bumper.remainingHitsToUpgrade());
        assertEquals(un_upgraded_score, bumper.hit());
    }

}