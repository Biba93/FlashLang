package com.github.biba.lib.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class DbOperations implements IDbOperations {

    private final SQLiteOpenHelper mHelper;

    DbOperations(final SQLiteOpenHelper pHelper) {
        mHelper = pHelper;
    }

    @Override
    public int insert(@NonNull final String pTable, final ContentValues pValues) {
        final int id = makeTransaction(new ITransaction() {

            @Override
            public int make(final SQLiteDatabase pDatabase) {
                final int id = (int) pDatabase.insertWithOnConflict(pTable, "", pValues, SQLiteDatabase.CONFLICT_REPLACE);
                return id;
            }
        });
        return id;
    }

    @Override
    public int bulkInsert(@NonNull final String pTable, final ContentValues[] pValues) {
        final int inserted = makeTransaction(new ITransaction() {

            @Override
            public int make(final SQLiteDatabase pDatabase) {
                int inserted = 0;

                for (final ContentValues value : pValues) {
                    pDatabase.insert(pTable, "", value);
                    inserted++;
                }

                return inserted;
            }
        });
        return inserted;
    }

    @Override
    public Query.Builder query() {
        return new Query.Builder();
    }

    @Override
    public Cursor query(final Query pQuery) {
        final String table = pQuery.getTable();
        final String[] projection = pQuery.getProjection();
        final String selection = pQuery.getSelection();
        final String[] selectionArgs = pQuery.getSelectionArgs();
        final String sortOrder = pQuery.getSortOrder();
        final String groupBy = pQuery.getGroupBy();
        return mHelper.getWritableDatabase().query(table, projection, selection, selectionArgs,
                groupBy, null, sortOrder);
    }

    @Override
    public int delete(@NonNull final String pTable, @Nullable final String pSelection, @Nullable final String[] pSelectionArgs) {
        final int count = makeTransaction(new ITransaction() {

            @Override
            public int make(final SQLiteDatabase pDatabase) {
                final int count = pDatabase.delete(pTable, pSelection, pSelectionArgs);
                return count;
            }
        });
        return count;
    }

    @Override
    public int update(@NonNull final String pTable, @Nullable final ContentValues pValues, @Nullable final String pSelection, @Nullable final String[] pSelectionArgs) {
        final int count = makeTransaction(new ITransaction() {

            @Override
            public int make(final SQLiteDatabase pDatabase) {
                final int count = pDatabase.update(pTable, pValues, pSelection, pSelectionArgs);
                return count;
            }
        });
        return count;
    }

    private int makeTransaction(final ITransaction pTransaction) {
        final SQLiteDatabase database = mHelper.getWritableDatabase();
        int count;

        database.beginTransaction();
        try {
            count = pTransaction.make(database);
            database.setTransactionSuccessful();
        } finally {
            database.endTransaction();
        }
        return count;
    }

    private interface ITransaction {

        int make(SQLiteDatabase pDatabase);
    }
}
