package com.github.biba.flashlang.apiTest;

import com.github.biba.flashlang.api.request.ITranslationRequest;

public class TestTranslateRequest implements ITranslationRequest {

    private final String mSourceText;
    private final String mSourceLanguage;
    private final String mTargetLanguage;

    public TestTranslateRequest(final String pSourceText, final String pSourceLanguage, final String pTargetLanguage) {
        mSourceText = pSourceText;
        mSourceLanguage = pSourceLanguage;
        mTargetLanguage = pTargetLanguage;
    }

    @Override
    public String getSourceText() {
        return mSourceText;
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
        return "somekey";
    }
}
