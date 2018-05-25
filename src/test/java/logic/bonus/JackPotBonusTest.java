package logic.bonus;

import controller.Game;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class JackPotBonusTest {

    private Game game;
    private JackPotBonus bonus;

    @Before
    public void setUp() {
        game = new Game();
        bonus = new JackPotBonus();
    }

    @Test
    public void bonusBehaviour() {
        assertEquals(0, game.getScore());
        bonus.trigger(game);
        assertEquals(100000, game.getScore());
    }
}