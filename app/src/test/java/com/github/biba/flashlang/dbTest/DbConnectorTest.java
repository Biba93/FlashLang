package com.github.biba.flashlang.dbTest;

import com.github.biba.flashlang.BuildConfig;
import com.github.biba.flashlang.TestConstants;
import com.github.biba.flashlang.db.IDbModel;
import com.github.biba.flashlang.db.connector.DbTableConnector;
import com.github.biba.flashlang.domain.db.Selector;
import com.github.biba.flashlang.mocks.TestApplication;
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
import java.util.List;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class,
        sdk = TestConstants.SDK_VERSION,
        application = TestApplication.class)
public class DbConnectorTest {

    private DbTableConnector mDbTableConnector;

    @Before
    public void setUp() {
        IDbOperations.Impl.newInstance(RuntimeEnvironment.application, new TestDb());
        mDbTableConnector = new DbTableConnector();
    }

    @Test
    public void shouldNotBeNull() {
        Assert.assertNotNull(mDbTableConnector);
    }

    @Test
    public void insert() {
        final boolean inserted = mDbTableConnector.insert(new TestModel("0", "Insert 0"));
        Assert.assertTrue(inserted);
    }

    @Test
    public void bulkInsert() {
        final TestModel[] models = new TestModel[20];
        for (int i = 0; i < models.length; i++) {
            models[i] = new TestModel(String.valueOf(i), "Model " + i % 10);
        }
        final boolean inserted = mDbTableConnector.insert(models);
        Assert.assertTrue(inserted);
    }

    @Test
    public void delete() {
        bulkInsert();
        final boolean deleted = mDbTableConnector.delete(TestModel.TABLE_NAME, new Selector.ByFieldSelector(TestModel.STRING_KEY, "Model 2"));
        Assert.assertTrue(deleted);
    }

    @Test
    public void get() {
        bulkInsert();
        final List<TestModel> testModels = mDbTableConnector.get(TestModel.TABLE_NAME,
                new TestModel.CursorConverter(), null, new Selector.ByFieldSelector(TestModel.STRING_KEY, "Model 2"));

        Assert.assertNotNull(testModels);
        Assert.assertEquals(2, testModels.size());
        Assert.assertEquals("Model 2", testModels.get(0).getSomeString());

    }

    @Test
    public void update() {
        bulkInsert();
        final IDbModel model = new TestModel("1", "NEW STRING");
        final boolean updated = mDbTableConnector.update(model, new Selector.ByFieldSelector(TestModel.STRING_KEY, "Model 2"));
        Assert.assertTrue(updated);
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
