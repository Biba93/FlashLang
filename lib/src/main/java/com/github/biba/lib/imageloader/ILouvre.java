package com.github.biba.lib.imageloader;

import com.github.biba.lib.contracts.IRequestHandler;
import com.github.biba.lib.imageloader.request.IImageRequest;
import com.github.biba.lib.imageloader.request.ImageRequest;

public interface ILouvre extends IRequestHandler<IImageRequest, Void> {

    ImageRequest.Builder newRequest();

    final class Impl {

        private static Louvre INSTANCE;
        private static final Object syncLock = new Object();

        public static ILouvre newInstance(final Louvre.Config pConfig) {
            if (INSTANCE != null) {
                return INSTANCE;
            }

            synchronized (syncLock) {
                if (INSTANCE == null) {
                    INSTANCE = new Louvre(pConfig);
                }
            }
            return INSTANCE;
        }

        public static ILouvre getInstance() throws IllegalStateException {
            if (INSTANCE == null) {
                throw new IllegalStateException("Image Loader not instantiated");
            } else {
                return INSTANCE;
            }
        }
    }

}
