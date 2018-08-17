package logic.table;

import logic.gameelements.bumper.Bumper;
import logic.gameelements.target.DropTarget;
import logic.gameelements.target.Target;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AbstractTableTest {

    private Table table_1;
    private Table table_2;

    @Before
    public void setUp() {
        table_1 = new DefaultTable("table 1", 5, 7, 13, 17);
        table_2 = new NullTable();
        assertTrue(table_1.isPlayableTable());
        assertFalse(table_2.isPlayableTable());
    }

    @Test
    public void getTableName() {
        assertEquals("table 1", table_1.getTableName());
        assertEquals("", table_2.getTableName());
    }

    @Test
    public void getNumberOfDropTargets() {
        assertEquals(17, table_1.getNumberOfDropTargets());
        assertEquals(0, table_2.getNumberOfDropTargets());
    }

    @Test
    public void getCurrentlyDroppedDropTargets() {
        assertEquals(0, table_1.getCurrentlyDroppedDropTargets());
        int i = 0;
        for(Target target : table_1.getTargets()) {
            if(target instanceof DropTarget) {
                target.hit();
                i++;
                assertEquals(i, table_1.getCurrentlyDroppedDropTargets());
            }
        }
        assertEquals(table_1.getNumberOfDropTargets(), table_1.getCurrentlyDroppedDropTargets());

        table_1.resetDropTargets();

        assertEquals(0, table_1.getCurrentlyDroppedDropTargets());
    }

    @Test
    public void getBumpers() {
        assertEquals(12, table_1.getBumpers().size());
        assertEquals(0, table_2.getBumpers().size());
    }

    @Test
    public void getTargets() {
        assertEquals(30, table_1.getTargets().size());
        assertEquals(0, table_2.getTargets().size());
    }

    @Test
    public void upgradeAllBumpers() {
        for(Bumper bumper : table_1.getBumpers()) {
            assertFalse(bumper.isUpgraded());
        }
        table_1.upgradeAllBumpers();
        for(Bumper bumper : table_1.getBumpers()) {
            assertTrue(bumper.isUpgraded());
        }
    }

}