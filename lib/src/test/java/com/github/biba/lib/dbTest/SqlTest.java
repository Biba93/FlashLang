package com.github.biba.lib.dbTest;

import com.github.biba.lib.db.sql.ColumnSql;
import com.github.biba.lib.db.sql.SqlBuilder;

import org.junit.Test;

import java.lang.reflect.Field;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class SqlTest {

    private static final String[] CORRECT_FIELD_SQL = {
            "mInt INTEGER",
            "mLong BIGINT PRIMARY KEY NOT NULL",
            "mString TEXT"};

    private static final String[] CORRECT_FOREIGN_KEY_SQL = {
            "FOREIGN KEY (mInt) REFERENCES someTable(someColumn)",
            "FOREIGN KEY (mLong) REFERENCES someTable(someColumn)",
            "FOREIGN KEY (mString) REFERENCES someTable(someColumn)"};

    private static final String CORRECT_SQL = "CREATE TABLE IF NOT EXISTS testTable (" +
            "mInt INTEGER," +
            "mLong BIGINT PRIMARY KEY NOT NULL," +
            "mString TEXT," +
            "FOREIGN KEY (mInt) REFERENCES someTable(someColumn)," +
            "FOREIGN KEY (mLong) REFERENCES someTable(someColumn)," +
            "FOREIGN KEY (mString) REFERENCES someTable(someColumn))";

    @Test
    public void getFieldSql() {
        final Field[] fields = CorrectTableElement.class.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            assertEquals(CORRECT_FIELD_SQL[i], new ColumnSql(fields[i]).getFieldSql());
        }
    }

    @Test
    public void getForeignKeySql() {
        final Field[] fields = CorrectTableElement.class.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            assertEquals(CORRECT_FOREIGN_KEY_SQL[i], new ColumnSql(fields[i]).getForeignKeySql());
        }
    }

    @Test
    public void getCreateCorrectTableSql() throws Exception {
        assertEquals(CORRECT_SQL, SqlBuilder.getCreateTableSql(CorrectTableElement.class));
    }

    @Test
    public void getCreateWrongTableSql() throws Exception {
        assertNull(SqlBuilder.getCreateTableSql(WrongTable.class));
    }
}
