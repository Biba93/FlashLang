package com.github.biba.lib.cacheTest;

import com.github.biba.lib.BuildConfig;
import com.github.biba.lib.TestConstants;
import com.github.biba.lib.cache.file.FileCache;
import com.google.common.io.Files;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.io.File;
import java.io.IOException;

@RunWith(RobolectricTestRunner.class)
@Config(
        constants = BuildConfig.class,
        sdk = TestConstants.SDK_VERSION
)
public class FileCacheTest {

    private static final String KEY = "MyFile";

    private TestFileCache mCache;

    @Before
    public void init() {
        final FileCache.Config config = new FileCache.Config().setCacheDirName(RuntimeEnvironment.application.getCacheDir().getAbsolutePath());
        mCache = new TestFileCache(config);
    }

    @Test
    public void shouldNotBeNull() {
        Assert.assertNotNull(mCache);
    }

    @Test
    public void put() {
        boolean isSuccessful = false;
        try {
            mCache.put(KEY, new Object());
            isSuccessful = true;
        } catch (final IOException ignored) {
        }

        Assert.assertTrue(isSuccessful);

        final File file = mCache.get(KEY);
        Assert.assertNotNull(file);

        byte[] bytes = null;
        boolean isReadSuccessfully = false;
        try {
            bytes = Files.toByteArray(file);
            isReadSuccessfully = true;
        } catch (final IOException ignored) {
        }

        Assert.assertTrue(isReadSuccessfully);
        Assert.assertNotNull(bytes);

    }

}
