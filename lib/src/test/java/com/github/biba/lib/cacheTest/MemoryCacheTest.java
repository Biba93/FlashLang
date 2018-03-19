package com.github.biba.lib.cacheTest;

import com.github.biba.lib.BuildConfig;
import com.github.biba.lib.TestConstants;
import com.github.biba.lib.cache.memory.MemoryCache;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

@RunWith(RobolectricTestRunner.class)
@Config(
        constants = BuildConfig.class,
        sdk = TestConstants.SDK_VERSION
)
public class MemoryCacheTest {

    private static final String KEY = "MyFile";
    private static final String FILE = "MyFileText";

    private TestMemoryCache mCache;

    @Before
    public void init() {
        final MemoryCache.Config config = new MemoryCache.Config();
        mCache = new TestMemoryCache(config);
    }

    @Test
    public void shouldNotBeNull() {
        Assert.assertNotNull(mCache);
    }

    @Test
    public void put() {

        mCache.put(KEY, FILE);
        final String file = mCache.get(KEY);

        Assert.assertNotNull(file);
        Assert.assertEquals(FILE, file);
    }

}
