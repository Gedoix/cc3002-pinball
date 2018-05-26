package logic.table;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class NullTableTest {

    private NullTable table;

    @Before
    public void setUp() {
        table = new NullTable();
    }

    @Test
    public void nullTable() {
        assertNull(table.getTableName());
        assertFalse(table.isPlayableTable());
        assertEquals(0, table.getBumpers().size());
        assertEquals(0, table.getTargets().size());
    }
}