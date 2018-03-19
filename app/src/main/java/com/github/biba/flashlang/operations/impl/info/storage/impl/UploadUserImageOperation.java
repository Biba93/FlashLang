package com.github.biba.flashlang.operations.impl.info.storage.impl;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;

import com.github.biba.flashlang.Constants;
import com.github.biba.flashlang.domain.models.user.User;
import com.github.biba.flashlang.firebase.storage.IFirebaseStorageManager;
import com.github.biba.lib.contracts.ICallback;
import com.github.biba.lib.contracts.IOperation;

public class UploadUserImageOperation implements IOperation<Void> {

    private final Drawable mDrawable;
    private final User mUser;
    private final ICallback<Void> mCallback;

    public UploadUserImageOperation(final User pUser,
                                    final Drawable pDrawable,
                                    final ICallback<Void> pCallback) {
        mUser = pUser;
        mDrawable = pDrawable;
        mCallback = pCallback;
    }

    @Override
    public Void perform() {

        if (mDrawable == null) {
            return null;
        }
        final Bitmap bitmap = ((BitmapDrawable) mDrawable).getBitmap();

        IFirebaseStorageManager.Impl.getInstance()
                .upload(Constants.FirebaseStorage.USER_IMAGE_FOLDER_NAME,
                        mUser.getId(), bitmap, new IFirebaseStorageManager.ILoadListener() {

                            @Override
                            public void onSuccess(final Uri pUri) {
                                mUser.setPictureUrl(pUri.toString());
                                mCallback.onSuccess(null);
                            }

                            @Override
                            public void onError(final Throwable pThrowable) {
                                mCallback.onError(pThrowable);
                            }
                        });
        return null;

    }
}
