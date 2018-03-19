package com.github.biba.flashlang.api;

import android.content.Context;

import com.github.biba.flashlang.api.models.languages.ILanguage;
import com.github.biba.flashlang.api.models.languages.Language;
import com.github.biba.flashlang.api.models.translation.ITranslation;
import com.github.biba.flashlang.api.models.translation.TranslationBuilder;
import com.github.biba.flashlang.api.request.ITranslationRequest;
import com.github.biba.flashlang.api.request.ITranslationRequestHandler;
import com.github.biba.flashlang.api.request.TranslationRequestHandler;
import com.github.biba.flashlang.api.response.ITranslationResponse;
import com.github.biba.flashlang.api.utils.LanguageUtils;
import com.github.biba.lib.context.ContextHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Translator implements ITranslator {

    private Map<ApiConstants.LanguageKeys, ILanguage> mSupportedLanguages;
    private final ITranslationRequestHandler mRequestHandler;

    Translator(final Context pContext) {
        mSupportedLanguages = getSupportedLanguages(pContext);
        mRequestHandler = new TranslationRequestHandler();
    }

    @Override
    public ITranslation translate(final ITranslationRequest pRequest) throws Exception {
        final ITranslationResponse[] translationResponse = mRequestHandler.handle(pRequest);
        if (translationResponse != null && translationResponse.length > 0) {
            return buildTranslation(pRequest, translationResponse[0]);
        }
        return null;
    }

    @Override
    public List<ILanguage> getSupportedLanguages() throws RuntimeException {
        if (mSupportedLanguages == null) {
            mSupportedLanguages = getSupportedLanguages(ContextHolder.getContext());
        }
        return new ArrayList<>(mSupportedLanguages.values());
    }

    private Map<ApiConstants.LanguageKeys, ILanguage> getSupportedLanguages(final Context pContext) {
        if (mSupportedLanguages != null) {
            return mSupportedLanguages;
        }

        final Context context;
        if (pContext != null) {
            context = pContext;
        } else {
            context = ContextHolder.getContext();
        }
        if (context == null) {
            throw new RuntimeException("Can't get context");
        }

        mSupportedLanguages = new HashMap<>();
        for (final ApiConstants.LanguageKeys key :
                ApiConstants.LanguageKeys.values()) {
            final ILanguage language = new Language(key.name(), LanguageUtils.getLanguageName(context, key.name()));
            mSupportedLanguages.put(key, language);
        }

        return mSupportedLanguages;

    }

    private ITranslation buildTranslation(final ITranslationRequest pRequest, final ITranslationResponse pResponse) {

        if (pRequest != null && pResponse != null) {

            String sourceLanguageKey = pRequest.getSourceLanguage();
            if (sourceLanguageKey == null) {
                sourceLanguageKey = pResponse.getSourceLanguageKey();
            }

            final ILanguage sourceLanguage = mSupportedLanguages.get(ApiConstants.LanguageKeys.valueOf(sourceLanguageKey));
            final ILanguage targetLanguage = mSupportedLanguages.get(ApiConstants.LanguageKeys.valueOf(pRequest.getTargetLanguage()));

            final ITranslation translation = new TranslationBuilder()
                    .setSourceLanguage(sourceLanguage)
                    .setSourceText(pRequest.getSourceText())
                    .setTargetLanguage(targetLanguage)
                    .setTranslatedText(pResponse.getTranslatedText())
                    .build();
            return translation;
        }

        return null;
    }

}
