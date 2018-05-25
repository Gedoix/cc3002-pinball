package logic.bonus;

import controller.Game;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AbstractBonusTest {

    private Game game;
    private Bonus bonus;

    @Before
    public void setUp() {
        game = new Game();
        bonus = new DropTargetBonus();
    }

    @Test
    public void trigger() {
        assertEquals(0, bonus.timesTriggered());
        bonus.trigger(game);
        assertEquals(1, bonus.timesTriggered());
        bonus.trigger(game);
        assertEquals(2, bonus.timesTriggered());
        bonus.trigger(game);
        assertEquals(3, bonus.timesTriggered());
        bonus.trigger(game);
        assertEquals(4, bonus.timesTriggered());
    }

}