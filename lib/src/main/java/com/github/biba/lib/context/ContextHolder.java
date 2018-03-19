package com.github.biba.lib.context;

import android.content.Context;

public final class ContextHolder {

    private static Context sContext;

    private ContextHolder() {
    }

    public static Context getContext() {
        if (sContext == null) {
            throw new RuntimeException("Context not set!");
        }
        return sContext;
    }

    public static void setContext(final Context pContext) {
        if (pContext == null) {
            throw new RuntimeException("Wrong context!");
        }
        sContext = pContext;
    }

}
