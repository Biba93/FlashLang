package com.github.biba.lib.cacheTest;

import com.github.biba.lib.cache.memory.MemoryCache;

public class TestMemoryCache extends MemoryCache<String> {

    public TestMemoryCache(final Config pConfig) {
        super(pConfig);
    }

    @Override
    protected int sizeOf(final String pKey, final String pValue) {
        return pKey.length() + pValue.length();
    }
}
