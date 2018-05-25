package logic.gameelements.bumper;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class KickerBumperTest {

    private KickerBumper bumper;
    private KickerBumper upgraded_bumper;

    @Before
    public void setUp() {
        bumper = new KickerBumper();
        upgraded_bumper = new KickerBumper(true);
    }

    @Test
    public void kickerBumper() {
        assertEquals(5, bumper.remainingHitsToUpgrade());
        assertEquals(500, bumper.getScore());
        assertEquals(1000, upgraded_bumper.getScore());
    }
}