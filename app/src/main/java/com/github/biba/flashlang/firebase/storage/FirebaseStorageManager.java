package com.github.biba.flashlang.firebase.storage;

import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.github.biba.flashlang.firebase.utils.StorageUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.InputStream;

public class FirebaseStorageManager implements IFirebaseStorageManager {

    private final StorageReference mStorageReference = FirebaseStorage.getInstance().getReference();

    @Override
    public void upload(final String pCollectionName, final String pPicName, final Bitmap pBitmap, final ILoadListener pListener) {
        final byte[] data = StorageUtils.toBytes(pBitmap);
        final StorageReference imageReference = getImageReference(pCollectionName, pPicName);
        final UploadTask uploadTask = imageReference.putBytes(data);
        if (pListener != null) {
            uploadTask.addOnCompleteListener(new LoadOnCompleteListener(pListener));
        }

    }

    @Override
    public void upload(final String pCollectionName, final String pPicName, final InputStream pStream, final ILoadListener pListener) {
        final StorageReference imageReference = getImageReference(pCollectionName, pPicName);
        final UploadTask uploadTask = imageReference.putStream(pStream);
        if (pListener != null) {
            uploadTask.addOnCompleteListener(new LoadOnCompleteListener(pListener));
        }
    }

    @Override
    public void getImageUrl(final String pCollectionName, final String pPicName, final ILoadListener pListener) {
        final StorageReference imageReference = getImageReference(pCollectionName, pPicName);
        imageReference.getDownloadUrl()
                .addOnSuccessListener(new OnSuccessListener<Uri>() {

                    @Override
                    public void onSuccess(final Uri pUri) {
                        pListener.onSuccess(pUri);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {

                    @Override
                    public void onFailure(@NonNull final Exception pE) {
                        pListener.onError(pE);
                    }
                });
    }

    @NonNull
    private StorageReference getImageReference(final String pCollectionName, final String pPicName) {
        return mStorageReference.child(StorageUtils.getChildReference(pCollectionName, pPicName));
    }

    private class LoadOnCompleteListener implements OnCompleteListener<UploadTask.TaskSnapshot> {

        final ILoadListener mLoadListener;

        public LoadOnCompleteListener(final ILoadListener pLoadListener) {
            mLoadListener = pLoadListener;
        }

        @Override
        public void onComplete(@NonNull final Task<UploadTask.TaskSnapshot> pTask) {
            if (!pTask.isSuccessful()) {
                mLoadListener.onError(pTask.getException());
            } else {
                mLoadListener.onSuccess(pTask.getResult().getDownloadUrl());
            }
        }
    }
}
