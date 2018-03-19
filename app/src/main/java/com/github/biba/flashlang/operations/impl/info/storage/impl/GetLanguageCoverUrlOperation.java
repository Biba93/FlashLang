package com.github.biba.flashlang.operations.impl.info.storage.impl;

import com.github.biba.flashlang.Constants;
import com.github.biba.flashlang.firebase.storage.IFirebaseStorageManager;
import com.github.biba.lib.contracts.IOperation;

public class GetLanguageCoverUrlOperation implements IOperation<Void> {

    private final String mLanguageKey;
    private final IFirebaseStorageManager.ILoadListener mListener;

    public GetLanguageCoverUrlOperation(final String pLanguageKey, final IFirebaseStorageManager.ILoadListener pListener) {
        mLanguageKey = pLanguageKey;
        mListener = pListener;
    }

    @Override
    public Void perform() {
        IFirebaseStorageManager.Impl.getInstance()
                .getImageUrl(Constants.FirebaseStorage.LANGUAGES_IMAGE_FOLDER_NAME, mLanguageKey, mListener);
        return null;
    }
}
