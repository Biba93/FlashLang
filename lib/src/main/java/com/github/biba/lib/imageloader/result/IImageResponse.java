package com.github.biba.lib.imageloader.result;

import android.graphics.Bitmap;

import com.github.biba.lib.contracts.IResponse;
import com.github.biba.lib.imageloader.request.IImageRequest;

public interface IImageResponse extends IResponse<Bitmap> {

    IImageRequest getRequest();
}
