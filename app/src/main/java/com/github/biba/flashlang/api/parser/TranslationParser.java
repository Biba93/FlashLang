package com.github.biba.flashlang.api.parser;

import com.github.biba.flashlang.api.response.ITranslationResponse;
import com.github.biba.lib.contracts.IResponseConverter;
import com.github.biba.lib.logs.Log;
import com.google.gson.Gson;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public class TranslationParser implements IResponseConverter<ITranslationResponse[], InputStream> {

    private static final String LOG_TAG = TranslationParser.class.getSimpleName();

    @Override
    public ITranslationResponse[] convert(final InputStream pInputStream) {
        //TODO stream is not closed
        //Actually, I think that parser shouldn't care about closing stream
        //Stream will be closed by method that opened that stream and passed it to parser

        if (pInputStream == null) {
            return null;
        }
        try {
            final Reader reader = new InputStreamReader(pInputStream);
            final ResponseModel responseModel = new Gson().fromJson(reader, ResponseModel.class);
            if (responseModel != null) {

                if (responseModel.getError() != null) {
                    return null;
                }
                final ResponseModel.Translations translations = responseModel.getTranslations();
                if (translations != null && translations.getElements().length > 0) {
                    return translations.getElements();
                }
            }
        } catch (final Exception pE) {
            Log.e(LOG_TAG, pE.getMessage());
        }
        return null;
    }

}
