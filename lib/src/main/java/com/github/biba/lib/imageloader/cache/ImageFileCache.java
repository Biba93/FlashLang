package com.github.biba.lib.imageloader.cache;

import android.graphics.Bitmap;

import com.github.biba.lib.cache.file.FileCache;

import java.io.OutputStream;

public class ImageFileCache extends FileCache<Bitmap> implements IImageFileCache {

    private final Bitmap.CompressFormat mCompressFormat;
    private final int mCompressQuality;

    public ImageFileCache(final Config pConfig) {
        super(pConfig);
        mCompressFormat = pConfig.mCompressFormat;
        mCompressQuality = pConfig.mCompressQuality;
    }

    @Override
    protected void write(final Bitmap pFileToCache, final OutputStream pTargetStream) {
        pFileToCache.compress(mCompressFormat, mCompressQuality, pTargetStream);
    }

    public static class Config extends FileCache.Config {

        private Bitmap.CompressFormat mCompressFormat = com.github.biba.lib.Constants.ImageFileCache.DEFAULT_COMPRESS_FORMAT;
        private int mCompressQuality = com.github.biba.lib.Constants.ImageFileCache.DEFAULT_COMPRESS_QUALITY;

        public Config setCompressFormat(final Bitmap.CompressFormat pCompressFormat) {
            mCompressFormat = pCompressFormat;
            return this;
        }

        public Config setCompressQuality(final int pCompressQuality) {
            mCompressQuality = pCompressQuality;
            return this;
        }

    }
}
