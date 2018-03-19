package com.github.biba.lib.imageloader;

import com.github.biba.lib.imageloader.cache.CacheManager;
import com.github.biba.lib.imageloader.cache.IImageFileCache;
import com.github.biba.lib.imageloader.cache.IImageMemoryCache;
import com.github.biba.lib.imageloader.load.ImageLoader;
import com.github.biba.lib.imageloader.request.IImageRequest;
import com.github.biba.lib.imageloader.request.ImageRequest;

public final class Louvre implements ILouvre {

    private final ImageLoader mImageLoader;

    Louvre(final Config pConfig) {
        mImageLoader = new ImageLoader();
        mImageLoader.setCacheManager(new CacheManager(pConfig.mMemoryCache, pConfig.mFileCache));
    }

    @Override
    public ImageRequest.Builder newRequest() {
        return new ImageRequest.Builder();
    }

    @Override
    public Void handle(final IImageRequest pRequest) {

        if (pRequest != null) {
            mImageLoader.load(pRequest);
        }
        return null;
    }

    public static class Config {

        IImageFileCache mFileCache;
        IImageMemoryCache mMemoryCache;

        public void setFileCache(final IImageFileCache pFileCache) {
            if (pFileCache != null) {
                mFileCache = pFileCache;
            }
        }

        public void setMemoryCache(final IImageMemoryCache pMemoryCache) {
            if (pMemoryCache != null) {
                mMemoryCache = pMemoryCache;
            }
        }

    }

}
