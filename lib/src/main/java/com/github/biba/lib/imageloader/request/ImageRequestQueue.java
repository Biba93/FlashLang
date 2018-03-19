package com.github.biba.lib.imageloader.request;

import android.support.annotation.NonNull;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

public class ImageRequestQueue implements IImageRequestQueue {

    private final BlockingDeque<IImageRequest> mQueue;

    public ImageRequestQueue() {

        mQueue = new LinkedBlockingDeque<>();
    }

    @Override
    public IImageRequest takeFirst() throws InterruptedException {
        return mQueue.takeFirst();
    }

    @Override
    public void addFirst(@NonNull final IImageRequest pImageRequest) {
        mQueue.addFirst(pImageRequest);
    }

}
