package com.github.biba.lib.imageloader.cache;

public class CacheManager implements ICacheManager {

    private final IImageMemoryCache mMemoryCache;
    private final IImageFileCache mFileCache;

    public CacheManager(final IImageMemoryCache pMemoryCache, final IImageFileCache pFileCache) {
        mMemoryCache = pMemoryCache;
        mFileCache = pFileCache;
    }

    @Override
    public IImageMemoryCache getMemoryCache() {
        return mMemoryCache;
    }

    @Override
    public IImageFileCache getFileCache() {
        return mFileCache;
    }
}
