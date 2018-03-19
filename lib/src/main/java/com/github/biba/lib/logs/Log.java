package com.github.biba.lib.logs;

public final class Log {

    private static boolean isEnabled;

    public static void setEnabled(final boolean pIsEnabled) {
        isEnabled = pIsEnabled;
    }

    public static void d(final String pTag, final String pMessage) {
        if (isEnabled) {
            android.util.Log.d(pTag, pMessage);
        }
    }

    public static void e(final String pTag, final String pMessage) {
        if (isEnabled) {
            android.util.Log.e(pTag, pMessage);
        }
    }

    public static void e(final String pTag, final String pMessage, final Throwable pThrowable) {
        if (isEnabled) {
            android.util.Log.e(pTag, pMessage, pThrowable);
        }
    }

    public static void i(final String pTag, final String pMessage) {
        if (isEnabled) {
            android.util.Log.i(pTag, pMessage);
        }
    }

}
