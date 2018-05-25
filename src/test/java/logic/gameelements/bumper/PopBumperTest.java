package logic.gameelements.bumper;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PopBumperTest {

    private PopBumper bumper;
    private PopBumper upgraded_bumper;

    @Before
    public void setUp() {
        bumper = new PopBumper();
        upgraded_bumper = new PopBumper(true);
    }

    @Test
    public void popBumper() {
        assertEquals(3, bumper.remainingHitsToUpgrade());
        assertEquals(100, bumper.getScore());
        assertEquals(300, upgraded_bumper.getScore());
    }
}