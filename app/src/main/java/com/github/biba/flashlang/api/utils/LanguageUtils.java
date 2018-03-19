package com.github.biba.flashlang.api.utils;

import android.content.Context;

import com.github.biba.lib.context.ContextHolder;

public final class LanguageUtils {

    public static String getLanguageName(final Context pContext, final String pLanguageKey) {
        final int languageId = pContext.getResources().getIdentifier(pLanguageKey, "string", pContext.getPackageName());
        final String languageName = pContext.getString(languageId);
        return languageName;
    }

    public static String getLanguageName(final String pLanguageKey) {
        final Context context = ContextHolder.getContext();
        return getLanguageName(context, pLanguageKey);
    }
}
