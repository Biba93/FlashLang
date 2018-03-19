package com.github.biba.lib.db.sql;

import android.support.annotation.Nullable;

import com.github.biba.lib.Constants;
import com.github.biba.lib.db.utils.DbUtils;
import com.github.biba.lib.utils.StringUtils;

import java.lang.reflect.Field;

public final class SqlBuilder {

    @Nullable
    public static String getCreateTableSql(final Class<?> pTable) {

        final String tableName = DbUtils.getTableName(pTable);
        if (StringUtils.isNullOrEmpty(tableName)) {
            return null;
        }

        final String columnsQuery = getColumnsSql(pTable);
        if (StringUtils.isNullOrEmpty(columnsQuery)) {
            return null;
        }

        return String.format(Constants.Sql.TABLE_TEMPLATE, tableName, columnsQuery);
    }

    private static String getColumnsSql(final Class<?> pTable) {

        final StringBuilder sql = new StringBuilder();
        final StringBuilder foreignKeyBuilder = new StringBuilder();

        final Field[] fields = pTable.getDeclaredFields();

        for (final Field field : fields) {

            final ColumnSql column = new ColumnSql(field);
            final String fieldQuery = column.getFieldSql();
            if (fieldQuery != null) {
                sql.append(fieldQuery)
                        .append(Constants.Sql.STATEMENT_SEPARATOR);
                final String foreignKeyQuery = column.getForeignKeySql();
                if (foreignKeyQuery != null) {
                    foreignKeyBuilder.append(foreignKeyQuery)
                            .append(Constants.Sql.STATEMENT_SEPARATOR);
                }
            }
        }

        if (StringUtils.isNullOrEmpty(sql)) {
            return null;
        }

        sql.append(foreignKeyBuilder);

        final int lastIndex = sql.length() - 1;
        if (sql.charAt(lastIndex) == Constants.Sql.STATEMENT_SEPARATOR) {
            sql.deleteCharAt(lastIndex);
        }

        return sql.toString();
    }

}
