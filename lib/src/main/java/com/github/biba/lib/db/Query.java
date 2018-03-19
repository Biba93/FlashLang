package com.github.biba.lib.db;

import android.database.Cursor;

public class Query {

    private final String mTable;
    private final String[] mProjection;
    private final String mSelection;
    private final String[] mSelectionArg;
    private final String mSortOrder;
    private final String mGroupBy;

    public Query(final Builder pBuilder) {
        mTable = pBuilder.mTable;
        mProjection = pBuilder.mProjection;
        mSelection = pBuilder.mSelection;
        mSelectionArg = pBuilder.mSelectionArg;
        mSortOrder = pBuilder.mSortOrder;
        mGroupBy = pBuilder.mGroupBy;
    }

    public String getTable() {
        return mTable;
    }

    public String[] getProjection() {
        return mProjection;
    }

    public String getSelection() {
        return mSelection;
    }

    public String[] getSelectionArgs() {
        return mSelectionArg;
    }

    public String getSortOrder() {
        return mSortOrder;
    }

    public String getGroupBy() {
        return mGroupBy;
    }

    public static final class Builder {

        private String mTable;
        private String[] mProjection;
        private String mSelection;
        private String[] mSelectionArg;
        private String mSortOrder;
        private String mGroupBy;

        public Builder table(final String pTable) {
            mTable = pTable;
            return this;
        }

        public Builder projection(final String[] pProjection) {
            mProjection = pProjection;
            return this;
        }

        public Builder selection(final String pSelection) {
            mSelection = pSelection;
            return this;
        }

        public Builder selectionArgs(final String[] pSelectionArg) {
            mSelectionArg = pSelectionArg;
            return this;
        }

        public Builder sortOrder(final String pSortOrder) {
            mSortOrder = pSortOrder;
            return this;
        }

        public Builder groupBy(final String pGroupBy) {
            mGroupBy = pGroupBy;
            return this;
        }

        public Cursor cursor() {
            return IDbOperations.Impl.getInstance().query(new Query(this));
        }

    }

}
