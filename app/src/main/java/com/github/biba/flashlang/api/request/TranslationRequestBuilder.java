package com.github.biba.flashlang.api.request;

public class TranslationRequestBuilder {

    private String mApiKey;
    private String mInputText;
    private String mSourceLanguageKey;
    private String mTargetLanguageKey;

    public TranslationRequestBuilder setApiKey(final String pApiKey) {
        mApiKey = pApiKey;
        return this;
    }

    public TranslationRequestBuilder setInputText(final String pInputText) {
        mInputText = pInputText;
        return this;
    }

    public TranslationRequestBuilder setSourceLanguageKey(final String pSourceLanguageKey) {
        mSourceLanguageKey = pSourceLanguageKey;
        return this;
    }

    public TranslationRequestBuilder setTargetLanguageKey(final String pTargetLanguageKey) {
        mTargetLanguageKey = pTargetLanguageKey;
        return this;
    }

    public String getApiKey() {
        return mApiKey;
    }

    public String getInputText() {
        return mInputText;
    }

    public String getSourceLanguageKey() {
        return mSourceLanguageKey;
    }

    public String getTargetLanguageKey() {
        return mTargetLanguageKey;
    }

    public ITranslationRequest createTranslationRequest() {
        return new TranslationRequest(this);
    }
}
