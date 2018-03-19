package com.github.biba.lib.imageloader.result;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import com.github.biba.lib.imageloader.request.IImageRequest;

public class ImageResponse implements IImageResponse {

    private IImageRequest mRequest;
    private Bitmap mBitmap;
    private Throwable mThrowable;

    public ImageResponse() {
    }

    public ImageResponse(@NonNull final IImageRequest pRequest) {
        mRequest = pRequest;
    }

    @Override
    public IImageRequest getRequest() {
        return mRequest;
    }

    @Override
    public Bitmap getResult() {
        return mBitmap;
    }

    @Override
    public Throwable getError() {
        return mThrowable;
    }

    public void setResult(final Bitmap pBitmap) {

        mBitmap = pBitmap;
    }

    public void setError(final Exception pThrowable) {
        mThrowable = pThrowable;
    }
}
