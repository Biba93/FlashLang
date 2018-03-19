package com.github.biba.lib.utils;

public final class StringUtils {

    public static boolean isNullOrEmpty(final String pString) {
        return pString == null || pString.isEmpty();
    }

    public static boolean isNullOrEmpty(final CharSequence pCharSequence) {
        return pCharSequence == null || pCharSequence.length() <= 0;
    }

    public static String extractNameFromEmail(final String pEmail) {
        final String name = pEmail.substring(0, pEmail.lastIndexOf("@"));
        return name;
    }

}
