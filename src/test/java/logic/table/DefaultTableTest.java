package logic.table;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DefaultTableTest {

    private DefaultTable table;

    @Before
    public void setUp() {
        table = new DefaultTable("1", 2, 3, 4, 5);
    }

    @Test
    public void defaultTable() {
        assertEquals("1", table.getTableName());
        assertTrue(table.isPlayableTable());
        assertEquals(5, table.getBumpers().size());
        assertEquals(9, table.getTargets().size());
    }
}