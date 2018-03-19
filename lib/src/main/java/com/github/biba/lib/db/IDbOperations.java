package com.github.biba.lib.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public interface IDbOperations {

    int insert(@NonNull String pTable, ContentValues pValues);

    int bulkInsert(@NonNull String pTable, ContentValues[] pValues);

    Query.Builder query();

    Cursor query(Query pQuery);

    int delete(@NonNull String pTable, @Nullable final String pSelection, @Nullable final String[] pSelectionArgs);

    int update(@NonNull final String pTable, @Nullable final ContentValues pValues, @Nullable final String pSelection, @Nullable final String[] pSelectionArgs);

    final class Impl {

        private static IDbOperations INSTANCE;
        private static final Object syncLock = new Object();

        public static IDbOperations newInstance(final Context pContext, final IDb pDb) {
            if (INSTANCE != null) {
                return INSTANCE;
            }
            synchronized (syncLock) {
                if (INSTANCE == null) {
                    INSTANCE = new DbOperations(new DbHelper(pContext, pDb));
                }
            }
            return INSTANCE;
        }

        public static IDbOperations getInstance() throws IllegalStateException {
            if (INSTANCE == null) {
                throw new IllegalStateException("Db Operations not instantiated!");
            } else {
                return INSTANCE;
            }
        }
    }

}
