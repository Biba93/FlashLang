package com.github.biba.flashlang.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.github.biba.lib.context.ContextHolder;
import com.github.biba.lib.logs.Log;

public final class ConnectionManager {

    private static final String LOG_TAG = ConnectionManager.class.getSimpleName();

    public static boolean isNetworkAvailable() {
        final boolean[] isAvailable = new boolean[1];
        final Thread checkConnectionThread = new Thread(new Runnable() {

            @Override
            public void run() {
                final ConnectivityManager connectivityManager
                        = (ConnectivityManager) ContextHolder.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                final NetworkInfo activeNetworkInfo;
                if (connectivityManager != null) {
                    activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
                    isAvailable[0] = activeNetworkInfo != null && activeNetworkInfo.isConnected();
                }
            }
        });
        checkConnectionThread.start();
        try {
            checkConnectionThread.join();
        } catch (final InterruptedException pE) {
            Log.e(LOG_TAG, "isNetworkAvailable: ", pE);
        }
        return isAvailable[0];
    }

}
