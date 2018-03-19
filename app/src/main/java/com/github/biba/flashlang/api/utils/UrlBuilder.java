package com.github.biba.flashlang.api.utils;

import com.github.biba.flashlang.api.ApiConstants;
import com.github.biba.flashlang.api.request.ITranslationRequest;
import com.github.biba.lib.logs.Log;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class UrlBuilder {

    private static final String LOG_TAG = UrlBuilder.class.getSimpleName();

    public String getTranslateUrl(final String pApiKey, final ITranslationRequest pRequest) throws UnsupportedEncodingException {
        final String sourceText = URLEncoder.encode(pRequest.getSourceText(), "UTF-8");
        final String url = String.format(ApiConstants.Url.TRANSLATION_URL_TEMPLATE,
                pApiKey, pRequest.getSourceLanguage(), pRequest.getTargetLanguage(), sourceText);
        Log.d(LOG_TAG, "getTranslateUrl: " + url);
        return url;
    }

}
