package com.github.biba.lib.imageloader.utils;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.github.biba.lib.imageloader.request.IImageRequest;

import static com.github.biba.lib.Constants.ImageLoader.EMPTY_DRAWABLE;

public final class ImageUtils {

    public static boolean imageHasSize(final IImageRequest pImageRequest) {
        final ImageView imageView = pImageRequest.getTarget().get();
        return imageView != null && imageView.getWidth() > 0 && imageView.getHeight() > 0;
    }

    public static int getImageWidth(final IImageRequest pImageRequest) {
        final ImageView imageView = pImageRequest.getTarget().get();
        if (imageView != null) {
            return imageView.getWidth();
        }
        return 0;
    }

    public static int getImageHeight(final IImageRequest pImageRequest) {
        final ImageView imageView = pImageRequest.getTarget().get();
        if (imageView != null) {
            return imageView.getHeight();
        }
        return 0;
    }

    public static void setImage(@NonNull final ImageView pTarget, final Integer pImage) {
        if (pImage != null) {
            pTarget.setImageResource(pImage);
        }
    }

    public static void setImage(@NonNull final ImageView pTarget, final Bitmap pBitmap) {
        if (pBitmap != null) {
            final TransitionDrawable drawable = new TransitionDrawable(new Drawable[]{EMPTY_DRAWABLE, new BitmapDrawable(pBitmap)});
            pTarget.setImageDrawable(drawable);
            drawable.startTransition(1000);
        }
    }
}
