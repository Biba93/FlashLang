package com.github.biba.flashlang.operations.impl.info.storage;

import android.graphics.drawable.Drawable;

import com.github.biba.flashlang.domain.models.user.User;
import com.github.biba.flashlang.firebase.storage.IFirebaseStorageManager;
import com.github.biba.flashlang.operations.impl.info.storage.impl.GetLanguageCoverUrlOperation;
import com.github.biba.flashlang.operations.impl.info.storage.impl.UploadUserImageOperation;
import com.github.biba.lib.contracts.ICallback;
import com.github.biba.lib.contracts.IOperation;

public class StorageOperationsBuilder {

    public IOperation<Void> getLanguageCoverUrl(final String pLanguageKey, final IFirebaseStorageManager.ILoadListener pListener) {
        return new GetLanguageCoverUrlOperation(pLanguageKey, pListener);
    }

    public IOperation<Void> uploadUserImage(final User pUser, final Drawable pDrawable, final ICallback<Void> pCallback) {
        return new UploadUserImageOperation(pUser, pDrawable, pCallback);
    }

}
