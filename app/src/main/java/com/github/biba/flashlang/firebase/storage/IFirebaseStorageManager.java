package com.github.biba.flashlang.firebase.storage;

import android.graphics.Bitmap;
import android.net.Uri;

import com.github.biba.lib.contracts.ICallback;

import java.io.InputStream;

public interface IFirebaseStorageManager {

    void upload(String pCollectionName, String pPicName, Bitmap pBitmap, ILoadListener pListener);

    void upload(String pCollectionName, String pPicName, InputStream pStream, ILoadListener pListener);

    void getImageUrl(String pCollectionName, String pPicName, final ILoadListener pListener);

    interface ILoadListener extends ICallback<Uri> {

    }

    final class Impl {

        public static IFirebaseStorageManager getInstance() {
            return new FirebaseStorageManager();
        }

    }

}
