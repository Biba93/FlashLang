package com.github.biba.lib.cacheTest;

import com.github.biba.lib.cache.file.FileCache;

import java.io.IOException;
import java.io.OutputStream;

public class TestFileCache extends FileCache<Object> {

    TestFileCache(final Config pConfig) {
        super(pConfig);
    }

    @Override
    protected void write(final Object pFileToCache, final OutputStream pTargetStream) throws IOException {
        pTargetStream.write(pFileToCache.toString().getBytes());
    }

}
