package com.github.biba.flashlang.api.request;

public class TranslationRequest implements ITranslationRequest {

    private final String mInputText;

    private final String mSourceLanguage;

    private final String mTargetLanguage;

    private final String mApiKey;

    TranslationRequest(final TranslationRequestBuilder pBuilder) {
        mInputText = pBuilder.getInputText();
        mSourceLanguage = pBuilder.getSourceLanguageKey();
        mTargetLanguage = pBuilder.getTargetLanguageKey();
        mApiKey = pBuilder.getApiKey();
    }

    @Override
    public String getSourceText() {
        return mInputText;
    }

    @Override
    public String getSourceLanguage() {
        return mSourceLanguage;
    }

    @Override
    public String getTargetLanguage() {
        return mTargetLanguage;
    }

    @Override
    public String getApiKey() {
        return mApiKey;
    }

}
