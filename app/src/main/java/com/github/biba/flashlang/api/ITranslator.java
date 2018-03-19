package com.github.biba.flashlang.api;

import android.content.Context;

import com.github.biba.flashlang.api.models.languages.ILanguage;
import com.github.biba.flashlang.api.models.translation.ITranslation;
import com.github.biba.flashlang.api.request.ITranslationRequest;

import java.util.List;

public interface ITranslator {

    ITranslation translate(ITranslationRequest pRequest) throws Exception;

    List<ILanguage> getSupportedLanguages() throws RuntimeException;

    final class Impl {

        public static ITranslator getTranslator(final Context pContext) {
            return new Translator(pContext);
        }

    }
}
