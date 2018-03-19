package com.github.biba.flashlang.api.response;

import com.google.gson.annotations.SerializedName;

public class TranslationResponse implements ITranslationResponse {

    @SerializedName("detectedSourceLanguage")
    private String mSourceLanguage;
    @SerializedName("translatedText")
    private String mTranslatedText;

    @Override
    public String getSourceLanguageKey() {
        return mSourceLanguage;
    }

    @Override
    public String getTranslatedText() {
        return mTranslatedText;
    }

    public void setSourceLanguage(final String pSourceLanguage) {
        mSourceLanguage = pSourceLanguage;
    }

    public void setTranslatedText(final String pTranslatedText) {
        mTranslatedText = pTranslatedText;
    }

}
