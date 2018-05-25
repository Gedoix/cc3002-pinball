package logic.bonus;

import controller.Game;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ExtraBallBonusTest {

    private Game game;
    private ExtraBallBonus bonus;

    @Before
    public void setUp() {
        game = new Game();
        bonus = new ExtraBallBonus();
    }

    @Test
    public void bonusBehaviour() {
        int before = game.getBallCounter();
        bonus.trigger(game);
        int after = game.getBallCounter();
        assertEquals(1, after-before);

        before = game.getBallCounter();
        bonus.trigger(game);
        bonus.trigger(game);
        bonus.trigger(game);
        bonus.trigger(game);
        bonus.trigger(game);
        bonus.trigger(game);
        after = game.getBallCounter();
        assertEquals(6, after-before);
    }
}