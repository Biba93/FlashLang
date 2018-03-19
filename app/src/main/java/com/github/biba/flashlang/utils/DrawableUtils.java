package com.github.biba.flashlang.utils;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;

import com.github.biba.lib.context.ContextHolder;

public final class DrawableUtils {

    public static Bitmap bitmapFromDrawable(final int pId) {
        return ((BitmapDrawable) ContextHolder.getContext().getResources().getDrawable(pId)).getBitmap();
    }

}
