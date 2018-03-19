package com.github.biba.flashlang.api.models.translation;

import com.github.biba.flashlang.api.models.languages.ILanguage;

public class Translation implements ITranslation {

    private final ILanguage mSourceLanguage;
    private final String mSourceText;
    private final ILanguage mTargetLanguage;
    private final String mTranslatedText;

    Translation(final ILanguage pSourceLanguage, final String pSourceText, final ILanguage pTargetLanguage, final String pTranslatedText) {
        mSourceLanguage = pSourceLanguage;
        mSourceText = pSourceText;
        mTargetLanguage = pTargetLanguage;
        mTranslatedText = pTranslatedText;
    }

    @Override
    public String getSourceText() {
        return mSourceText;
    }

    @Override
    public ILanguage getSourceLanguage() {
        return mSourceLanguage;
    }

    @Override
    public ILanguage getTargetLanguage() {
        return mTargetLanguage;
    }

    @Override
    public String getTranslatedText() {
        return mTranslatedText;
    }

}
