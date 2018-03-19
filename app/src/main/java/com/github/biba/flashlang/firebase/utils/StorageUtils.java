package com.github.biba.flashlang.firebase.utils;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import com.github.biba.flashlang.Constants;

import java.io.ByteArrayOutputStream;

public final class StorageUtils {

    @NonNull
    public static String getChildReference(final String pCollectionName, final String pPicName) {
        final String picFormat;
        if (pCollectionName.equals(Constants.FirebaseStorage.LANGUAGES_IMAGE_FOLDER_NAME)) {
            picFormat = Constants.FirebaseStorage.LANGUAGE_IMAGE_FORMAT;
        } else {
            picFormat = Constants.FirebaseStorage.BASE_IMAGE_FORMAT;
        }
        return String.format(Constants.FirebaseStorage.FULL_IMAGE_PATH_TEMPLATE, pCollectionName, pPicName, picFormat);
    }

    public static byte[] toBytes(final Bitmap pBitmap) {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        pBitmap.compress(Constants.FirebaseStorage.DEFAULT_COMPRESS_FORMAT,
                Constants.FirebaseStorage.DEFAULT_COMPRESS_QUALITY, baos);
        return baos.toByteArray();
    }

}
