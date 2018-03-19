package com.github.biba.lib.imageloader.cache;

public interface ICacheManager {

    IImageMemoryCache getMemoryCache();

    IImageFileCache getFileCache();
}
