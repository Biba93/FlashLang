package com.github.biba.flashlang.api.models.translation;

import com.github.biba.flashlang.api.models.languages.ILanguage;

public class TranslationBuilder {

    private ILanguage mSourceLanguage;
    private String mSourceText;
    private ILanguage mTargetLanguage;
    private String mTranslatedText;

    public TranslationBuilder setSourceLanguage(final ILanguage pSourceLanguage) {
        mSourceLanguage = pSourceLanguage;
        return this;
    }

    public TranslationBuilder setSourceText(final String pSourceText) {
        mSourceText = pSourceText;
        return this;
    }

    public TranslationBuilder setTargetLanguage(final ILanguage pTargetLanguage) {
        mTargetLanguage = pTargetLanguage;
        return this;
    }

    public TranslationBuilder setTranslatedText(final String pTranslatedText) {
        mTranslatedText = pTranslatedText;
        return this;
    }

    public ITranslation build() {
        return new Translation(mSourceLanguage, mSourceText, mTargetLanguage, mTranslatedText);
    }
}
