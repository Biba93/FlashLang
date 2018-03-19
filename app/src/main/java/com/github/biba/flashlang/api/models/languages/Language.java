package com.github.biba.flashlang.api.models.languages;

public class Language implements ILanguage {

    private final String mLanguageCode;

    private final String mLanguageName;

    public Language(final String pLanguageCode, final String pLanguageName) {
        mLanguageCode = pLanguageCode;
        mLanguageName = pLanguageName;
    }

    @Override
    public String getLanguageCode() {
        return mLanguageCode;
    }

    @Override
    public String getLanguageName() {
        return mLanguageName;
    }
}
