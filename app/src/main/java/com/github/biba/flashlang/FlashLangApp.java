package com.github.biba.flashlang;

import android.app.Application;

import com.github.biba.flashlang.db.AppDb;
import com.github.biba.lib.cache.file.IFreeSpaceStrategy;
import com.github.biba.lib.context.ContextHolder;
import com.github.biba.lib.db.IDbOperations;
import com.github.biba.lib.imageloader.ILouvre;
import com.github.biba.lib.imageloader.Louvre;
import com.github.biba.lib.imageloader.cache.IImageFileCache;
import com.github.biba.lib.imageloader.cache.IImageMemoryCache;
import com.github.biba.lib.imageloader.cache.ImageFileCache;
import com.github.biba.lib.imageloader.cache.ImageMemoryCache;
import com.github.biba.lib.logs.Log;
import com.google.firebase.FirebaseApp;

public class FlashLangApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    private void init() {
        Log.setEnabled(true);
        ContextHolder.setContext(this);
        initDb();
        initImageLoader();
        FirebaseApp.initializeApp(this);
    }

    private void initDb() {
        IDbOperations.Impl.newInstance(this, new AppDb());
    }

    private void initImageLoader() {
        final ImageFileCache.Config fileCacheConfig = new ImageFileCache.Config();
        fileCacheConfig.setCacheDirName(getCacheDir().getAbsolutePath() + Constants.ImageLoader.DISK_CACHE_DIR);
        fileCacheConfig.setFreeSpaceStrategy(new IFreeSpaceStrategy.LastModifiedStrategy());

        final ImageMemoryCache.Config memoryCacheConfig = new ImageMemoryCache.Config();
        memoryCacheConfig.setCacheSize(Constants.ImageLoader.MEMORY_CACHE_SIZE);

        final IImageFileCache fileCache = new ImageFileCache(fileCacheConfig);
        final IImageMemoryCache memoryCache = new ImageMemoryCache(memoryCacheConfig);

        final Louvre.Config louvreConfig = new Louvre.Config();
        louvreConfig.setFileCache(fileCache);
        louvreConfig.setMemoryCache(memoryCache);

        ILouvre.Impl.newInstance(louvreConfig);

    }

}
