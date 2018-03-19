package com.github.biba.lib.utils;

import android.database.Cursor;

import com.github.biba.lib.logs.Log;

import java.io.Closeable;
import java.io.IOException;
import java.net.HttpURLConnection;

public final class IOUtils {

    private static final String LOG_TAG = IOUtils.class.getSimpleName();

    public static void close(final Closeable pCloseable) {
        if (pCloseable != null) {
            try {
                pCloseable.close();
            } catch (final IOException pE) {
                Log.e(LOG_TAG, pE.getMessage());
            }
        }
    }

    //Casting Cursor to closable requires API level 16,
    //So this method is called if min API level is less than 16
    public static void close(final Cursor pCursor) {
        if (pCursor != null) {
            if (!pCursor.isClosed()) {
                try {
                    pCursor.close();
                } catch (final Exception pE) {
                    Log.e(LOG_TAG, pE.getMessage());
                }
            }
        }
    }

    public static void disconnect(final HttpURLConnection pConnection) {
        if (pConnection != null) {
            pConnection.disconnect();
        }
    }
}
