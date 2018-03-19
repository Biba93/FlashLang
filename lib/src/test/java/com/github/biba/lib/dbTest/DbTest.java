package com.github.biba.lib.dbTest;

import android.content.ContentValues;
import android.database.Cursor;

import com.github.biba.lib.BuildConfig;
import com.github.biba.lib.TestConstants;
import com.github.biba.lib.db.IDbOperations;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.lang.reflect.Field;

@RunWith(RobolectricTestRunner.class)
@Config(
        constants = BuildConfig.class,
        sdk = TestConstants.SDK_VERSION
)
public class DbTest {

    private static final String TABLE_NAME = "testTable";
    private static final int ELEMENT_COUNT = 50;
    private static final String KEY_INT = "mInt";
    private static final String KEY_LONG = "mLong";
    private static final String KEY_STRING = "mString";
    private IDbOperations mDbOperations;

    @Before
    public void setUp() {
        IDbOperations.Impl.newInstance(RuntimeEnvironment.application, new TestDb());
        mDbOperations = IDbOperations.Impl.getInstance();
    }

    @Test
    public void shouldNotBeNull() {
        Assert.assertNotNull(mDbOperations);
    }

    @Test
    public void insert() {
        Assert.assertEquals(1,
                mDbOperations.insert(TABLE_NAME, generateContentValues()));
    }

    @Test
    public void bulkInsert() {
        Assert.assertEquals(ELEMENT_COUNT,
                mDbOperations.bulkInsert(TABLE_NAME, generateContentValuesArray()));
    }

    @Test
    public void delete() {
        mDbOperations.bulkInsert(TABLE_NAME, generateContentValuesArray());
        Assert.assertEquals(5,
                mDbOperations.delete(TABLE_NAME, KEY_INT + " LIKE 1", null));

    }

    @Test
    public void query() {
        mDbOperations.bulkInsert(TABLE_NAME, generateContentValuesArray());
        final Cursor cursor = mDbOperations.query()
                .table(TABLE_NAME)
                .selection(KEY_INT + " LIKE 1")
                .cursor();

        int count = 0;
        while (cursor.moveToNext()) {
            count++;
            Assert.assertEquals("ELEMENT " + 1, cursor.getString(cursor.getColumnIndex(KEY_STRING)));
        }

        Assert.assertEquals(5, count);

        cursor.close();
    }

    @Test
    public void update() {
        mDbOperations.bulkInsert(TABLE_NAME, generateContentValuesArray());
        final ContentValues values = new ContentValues();
        values.put(KEY_STRING, "string");
        Assert.assertEquals(5, mDbOperations.update(TABLE_NAME, values, KEY_INT + " LIKE 1", null));
    }

    private ContentValues generateContentValues() {
        final ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_INT, 10);
        contentValues.put(KEY_LONG, Long.MAX_VALUE);
        contentValues.put(KEY_STRING, "ONE ELEMENT");

        return contentValues;
    }

    private ContentValues[] generateContentValuesArray() {
        int count = 1;
        final ContentValues[] elements = new ContentValues[ELEMENT_COUNT];
        for (int i = 0; i < ELEMENT_COUNT; i++) {
            elements[i] = new ContentValues();
            elements[i].put(KEY_INT, count);
            elements[i].put(KEY_LONG, Long.MAX_VALUE - i);
            elements[i].put(KEY_STRING, "ELEMENT " + count);

            if (count == 10) {
                count = 1;
            } else {
                count++;

            }
        }
        return elements;

    }

    @After
    public void tearDown() {
        resetSingleton(IDbOperations.Impl.class);
    }

    private void resetSingleton(final Class clazz) {
        final Field instance;
        try {
            instance = clazz.getDeclaredField("INSTANCE");
            instance.setAccessible(true);
            instance.set(null, null);
        } catch (final Exception e) {
            throw new RuntimeException();
        }
    }
}
