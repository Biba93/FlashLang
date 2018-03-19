package com.github.biba.lib.dbTest;

import android.provider.BaseColumns;

import com.github.biba.lib.db.annotations.dbPrimaryKey;
import com.github.biba.lib.db.annotations.dbTable;
import com.github.biba.lib.db.annotations.dbTableElement;
import com.github.biba.lib.db.annotations.type.dbForeignKey;
import com.github.biba.lib.db.annotations.type.dbInteger;
import com.github.biba.lib.db.annotations.type.dbLong;
import com.github.biba.lib.db.annotations.type.dbString;

@dbTable(name = "testTable")
@dbTableElement(targetTableName = "testTable")
class CorrectTableElement implements BaseColumns {

    @dbForeignKey(referredTableName = "someTable", referredTableColumnName = "someColumn")
    @dbInteger(name = "mInt")
    private
    int mInt;

    @dbForeignKey(referredTableName = "someTable", referredTableColumnName = "someColumn")
    @dbLong(name = "mLong")
    @dbPrimaryKey(isNull = false)
    private
    long mLong;

    @dbForeignKey(referredTableName = "someTable", referredTableColumnName = "someColumn")
    @dbString(name = "mString")
    private
    String mString;

    public CorrectTableElement(final int pInt, final long pLong, final String pString) {
        mInt = pInt;
        mLong = pLong;
        mString = pString;
    }

    public int getInt() {
        return mInt;
    }

    public void setInt(final int pInt) {
        mInt = pInt;
    }

    public long getLong() {
        return mLong;
    }

    public void setLong(final long pLong) {
        mLong = pLong;
    }

    public String getString() {
        return mString;
    }

    public void setString(final String pString) {
        mString = pString;
    }
}
