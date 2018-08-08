package logic.bonus;

import controller.Game;
import logic.gameelements.target.DropTarget;
import logic.gameelements.target.Target;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

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
        game.setTable(game.createRandomTable("table", 0, 0, 0, 10));
        List<Target> targets = game.getTargets();
        for (int i = 0; i < 5; i++) {
            Target target = targets.get(i);
            game.hit(target);
        }
        assertEquals(5, game.getTable().getCurrentlyDroppedDropTargets());
        bonus.trigger(game);
        assertEquals(0, game.getTable().getCurrentlyDroppedDropTargets());
    }
}