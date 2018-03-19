package com.github.biba.lib.imageloader.cache;

import android.graphics.Bitmap;

import com.github.biba.lib.cache.memory.MemoryCache;

public class ImageMemoryCache extends MemoryCache<Bitmap> implements IImageMemoryCache {

    public ImageMemoryCache(final Config pConfig) {
        super(pConfig);
    }

    @Override
    protected int sizeOf(final String pKey, final Bitmap pValue) {
        return pKey.length() + pValue.getByteCount();
    }
}
