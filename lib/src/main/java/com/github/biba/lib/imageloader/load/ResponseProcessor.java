package com.github.biba.lib.imageloader.load;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.github.biba.lib.imageloader.request.IImageRequest;
import com.github.biba.lib.imageloader.result.IImageResponse;
import com.github.biba.lib.imageloader.utils.BitmapUtils;
import com.github.biba.lib.imageloader.utils.ImageUtils;
import com.github.biba.lib.logs.Log;

public class ResponseProcessor {

    public void processImageResponse(final IImageResponse pResponse) {

        Log.d("Image", "processImageResponse() called with: pResponse = [" + pResponse + "]");

        final ImageView imageView = getImageView(pResponse);
        if (imageView != null && isTagCorrect(imageView, pResponse)) {
            Bitmap finalBitmap;
            final Bitmap result = pResponse.getResult();
            if (result != null) {
                finalBitmap = BitmapUtils.getRoundBitmap(result);
            } else {
                finalBitmap = pResponse.getRequest().getErrorImage();
            }
            if (pResponse.getRequest().isRounded()) {
                finalBitmap = BitmapUtils.getRoundBitmap(finalBitmap);
            }
            ImageUtils.setImage(imageView, finalBitmap);
        }
    }

    private ImageView getImageView(final IImageResponse pResponse) {
        if (pResponse != null) {
            final IImageRequest request = pResponse.getRequest();
            if (request != null) {
                return request.getTarget().get();
            }
        }
        return null;
    }

    private boolean isTagCorrect(final ImageView pImageView, final IImageResponse pResponse) {
        final String expectedTag = pResponse.getRequest().getUrl();
        final String actualTag = (String) pImageView.getTag();
        return expectedTag.equals(actualTag);
    }
}
