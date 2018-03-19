package com.github.biba.lib.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.github.biba.lib.db.sql.SqlBuilder;
import com.github.biba.lib.utils.StringUtils;

public class DbHelper extends SQLiteOpenHelper {

    private final Class<?>[] mTables;

    DbHelper(final Context pContext, final IDb pDb) {
        super(pContext, pDb.getName(), null, pDb.getVersion());
        mTables = pDb.getTableModels();
    }

    @Override
    public void onCreate(final SQLiteDatabase db) {
        createTables(db, mTables);
    }

    @Override
    public void onUpgrade(final SQLiteDatabase db, final int oldVersion, final int newVersion) {

    }

    private void createTables(final SQLiteDatabase pDatabase, final Class<?>[] pTables) {
        pDatabase.beginTransaction();
        try {
            for (final Class<?> table :
                    pTables) {
                try {
                    final String sql = SqlBuilder.getCreateTableSql(table);
                    if (!StringUtils.isNullOrEmpty(sql)) {
                        pDatabase.execSQL(sql);
                    }
                } catch (final Exception ignored) {

                }
            }
            pDatabase.setTransactionSuccessful();
        } finally {
            pDatabase.endTransaction();
        }
    }

}


