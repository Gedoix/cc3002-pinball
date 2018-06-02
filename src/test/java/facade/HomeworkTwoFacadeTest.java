package facade;

import logic.bonus.DropTargetBonus;
import logic.bonus.ExtraBallBonus;
import logic.bonus.JackPotBonus;
import logic.gameelements.bumper.Bumper;
import logic.gameelements.target.Target;
import logic.table.Table;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class HomeworkTwoFacadeTest {

    private HomeworkTwoFacade facade_1;
    private HomeworkTwoFacade facade_2;

    @Before
    public void setUp() {
        facade_1 = new HomeworkTwoFacade();
        facade_2 = new HomeworkTwoFacade();

        Table table_1 = facade_1.newFullPlayableTable("hello world 1", 100, 0.5, 50, 50);
        facade_1.setGameTable(table_1);

        Table table_2 = facade_2.newPlayableTableWithNoTargets("hello world 2", 100, 0.5);
        facade_2.setGameTable(table_2);
    }

    @Test
    public void isPlayableTable() {
        assertTrue(facade_1.isPlayableTable());
        assertTrue(facade_2.isPlayableTable());
    }

    @Test
    public void getDropTargetBonus() {
        DropTargetBonus bonus = new DropTargetBonus();
        assertEquals(bonus, facade_1.getDropTargetBonus());
    }

    @Test
    public void getExtraBallBonus() {
        ExtraBallBonus bonus = new ExtraBallBonus();
        assertEquals(bonus, facade_1.getExtraBallBonus());
    }

    @Test
    public void getJackPotBonus() {
        JackPotBonus bonus = new JackPotBonus();
        assertEquals(bonus, facade_1.getJackPotBonus());
    }

    @Test
    public void newPlayableTableWithNoTargets() {
        Table table = facade_2.getCurrentTable();
        assertEquals(0, table.getTargets().size());
    }

    @Test
    public void newFullPlayableTable() {
        Table table = facade_1.getCurrentTable();
        assertNotEquals(0, table.getTargets().size());
    }

    @Test
    public void getBumpers() {
        List<Bumper> bumpers = facade_1.getBumpers();
        assertEquals(100, bumpers.size());
    }

    @Test
    public void getTargets() {
        List<Target> targets = facade_1.getTargets();
        assertEquals(100, targets.size());
    }

    @Test
    public void getTableName() {
        assertEquals("hello world 1", facade_1.getTableName());
        assertEquals("hello world 2", facade_2.getTableName());
    }

    @Test
    public void getAvailableBalls() {
        assertEquals(1, facade_1.getAvailableBalls());
    }

    @Test
    public void getCurrentScore() {
        assertEquals(0, facade_1.getCurrentScore());
    }

    @Test
    public void dropBall() {
        assertEquals(0, facade_1.dropBall());
    }

    @Test
    public void gameOver() {
        for(int i = 0; i < 5; i++) {
            facade_1.dropBall();
        }
        assertTrue(facade_1.gameOver());
    }
}