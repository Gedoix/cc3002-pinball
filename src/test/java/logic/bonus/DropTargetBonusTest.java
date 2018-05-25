package logic.bonus;

import controller.Game;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DropTargetBonusTest {

    private Game game;
    private DropTargetBonus bonus;

    @Before
    public void setUp() {
        game = new Game();
        bonus = new DropTargetBonus();
    }

    @Test
    public void bonusBehaviour() {
        assertEquals(0, game.getScore());
        bonus.trigger(game);
        assertEquals(1000000, game.getScore());
    }
}