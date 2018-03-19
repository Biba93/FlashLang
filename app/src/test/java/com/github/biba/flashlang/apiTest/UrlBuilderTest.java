package com.github.biba.flashlang.apiTest;

import com.github.biba.flashlang.api.request.ITranslationRequest;
import com.github.biba.flashlang.api.utils.UrlBuilder;

import org.junit.Assert;
import org.junit.Test;

import java.io.UnsupportedEncodingException;

public class UrlBuilderTest {

    private static final String CORRECT_URL = "https://translation.googleapis.com/language/translate/" +
            "v2?key=somekey&source=ru&target=en&q=%D0%BF%D1%80%D0%B8%D0%B2%D0%B5%D1%82";

    @Test
    public void buildUrl() {
        final UrlBuilder builder = new UrlBuilder();
        String translateUrl = null;
        try {
            final ITranslationRequest translationRequest = new TestTranslateRequest("привет", "ru", "en");
            translateUrl = builder.getTranslateUrl("somekey", translationRequest);
        } catch (final UnsupportedEncodingException ignored) {
        }

        Assert.assertNotNull(translateUrl);
        Assert.assertEquals(CORRECT_URL, translateUrl);

    }
}
