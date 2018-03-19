package com.github.biba.flashlang.dbTest;

import android.database.Cursor;

import com.github.biba.flashlang.db.IDbModel;
import com.github.biba.flashlang.db.connector.ICursorConverter;
import com.github.biba.lib.db.annotations.dbPrimaryKey;
import com.github.biba.lib.db.annotations.dbTable;
import com.github.biba.lib.db.annotations.dbTableElement;
import com.github.biba.lib.db.annotations.type.dbString;

import java.util.HashMap;

@dbTable(name = TestModel.TABLE_NAME)
@dbTableElement(targetTableName = TestModel.TABLE_NAME)
public class TestModel implements IDbModel<String> {

    static final String ID = "id";
    public static final String STRING_KEY = "string";
    public static final String TABLE_NAME = "testTable";

    @dbString(name = ID)
    @dbPrimaryKey(isNull = false)
    private final String mId;

    @dbString(name = STRING_KEY)
    private final String mSomeString;

    TestModel(final String pId, final String pSomeString) {
        mId = pId;
        mSomeString = pSomeString;
    }

    @Override
    public String getId() {
        return mId;
    }

    public String getSomeString() {
        return mSomeString;
    }

    @Override
    public HashMap<String, Object> convertToInsert() {
        final HashMap<String, Object> map = new HashMap<>();
        map.put(ID, mId);
        map.put(STRING_KEY, mSomeString);
        return map;
    }

    @Override
    public HashMap<String, Object> convertToUpdate() {
        final HashMap<String, Object> map = new HashMap<>();
        map.put(STRING_KEY, mSomeString);
        return map;
    }

    public static final class CursorConverter implements ICursorConverter<TestModel> {

        @Override
        public TestModel convert(final Cursor pCursor) {
            final String mId = pCursor.getString(pCursor.getColumnIndex(ID));
            final String mString = pCursor.getString(pCursor.getColumnIndex(STRING_KEY));
            return new TestModel(mId, mString);
        }
    }

}
