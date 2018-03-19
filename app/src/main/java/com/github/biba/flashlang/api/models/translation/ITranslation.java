package com.github.biba.flashlang.api.models.translation;

import com.github.biba.flashlang.api.models.languages.ILanguage;

public interface ITranslation {

    String getSourceText();

    ILanguage getSourceLanguage();

    ILanguage getTargetLanguage();

    String getTranslatedText();

}
