package com.github.biba.lib.cache.memory;

import android.util.LruCache;

import com.github.biba.lib.Constants;
import com.github.biba.lib.cache.MD5;

public abstract class MemoryCache<TFile> implements IMemoryCache<TFile> {

    private final LruCache<String, TFile> mCache;
    private final Object lock = new Object();

    public MemoryCache(final Config pConfig) {
        final int cacheSize;
        if (pConfig != null) {
            cacheSize = pConfig.mCacheSize;
        } else {
            cacheSize = Constants.MemoryCache.DEFAULT_CACHE_SIZE;
        }

        mCache = new LruCache<String, TFile>(cacheSize) {

            @Override
            protected int sizeOf(final String key, final TFile value) {
                return MemoryCache.this.sizeOf(key, value);
            }
        };
    }

    protected abstract int sizeOf(String pKey, TFile pValue);

    @Override
    public TFile get(final String pKey) {
        synchronized (lock) {
            return mCache.get(MD5.hash(pKey));
        }
    }

    @Override
    public void put(final String pKey, final TFile pFile) {
        synchronized (lock) {
            mCache.put(MD5.hash(pKey), pFile);
        }
    }

    public static class Config {

        private int mCacheSize = Constants.MemoryCache.DEFAULT_CACHE_SIZE;

        public Config setCacheSize(final int pCacheSize) {
            mCacheSize = pCacheSize;
            return this;
        }
    }
}
