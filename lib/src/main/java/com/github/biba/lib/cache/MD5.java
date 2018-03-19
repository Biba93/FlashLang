package com.github.biba.lib.cache;

import com.github.biba.lib.logs.Log;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class MD5 {

    private static final String LOG_TAG = MD5.class.getSimpleName();

    public static String hash(final String value) {
        try {
            final MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(value.getBytes());
            final byte[] array = md.digest();
            final StringBuilder sb = new StringBuilder();
            for (final byte anArray : array) {
                sb.append(Integer.toHexString((anArray & 0xFF) | 0x100).substring(1, 3));
            }
            return sb.toString();
        } catch (final NoSuchAlgorithmException pE) {
            Log.e(LOG_TAG, pE.getMessage());
        }
        return value;
    }

}
