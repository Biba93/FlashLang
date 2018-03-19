package com.github.biba.flashlang.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.github.biba.lib.context.ContextHolder;

public final class SystemConfigUtils {

    public static float getScreenHeight() {
        final Context context = ContextHolder.getContext();
        final WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        final DisplayMetrics outSize = new DisplayMetrics();
        if (windowManager != null) {
            windowManager.getDefaultDisplay().getMetrics(outSize);
            return outSize.heightPixels;
        } else {
            return 0;
        }
    }

    public static int gerOrientation() {
        final Context context = ContextHolder.getContext();
        final int orientation = context.getResources().getConfiguration().orientation;
        return orientation;
    }

}
