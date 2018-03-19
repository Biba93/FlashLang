package com.github.biba.lib.imageloader.request;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.github.biba.lib.contracts.IRequest;

import java.lang.ref.WeakReference;

public interface IImageRequest extends IRequest {

    String getUrl();

    WeakReference<ImageView> getTarget();

    Bitmap getErrorImage();

    boolean isSaved();

    boolean isRounded();

    boolean isScaled();

}
