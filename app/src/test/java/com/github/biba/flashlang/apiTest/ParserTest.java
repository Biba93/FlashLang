package com.github.biba.flashlang.apiTest;

import com.github.biba.flashlang.api.parser.TranslationParser;
import com.github.biba.flashlang.api.response.ITranslationResponse;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class ParserTest {

    private InputStream mFirstResource;
    private InputStream mSecondResource;
    private InputStream mThirdResource;
    private InputStream mFourthResource;

    @Before
    public void setUp() {
        mFirstResource = this.getClass().getClassLoader().getResourceAsStream("first_translate_sample.json");
        mSecondResource = this.getClass().getClassLoader().getResourceAsStream("second_translate_sample.json");
        mThirdResource = this.getClass().getClassLoader().getResourceAsStream("third_translate_sample.json");
        mFourthResource = this.getClass().getClassLoader().getResourceAsStream("fourth_translate_sample.json");

    }

    @Test
    public void testTranslationParser() {

        final TranslationParser translationParser = new TranslationParser();
        final ITranslationResponse[] firstObjects = translationParser.convert(mFirstResource);
        final ITranslationResponse[] secondObjects = translationParser.convert(mSecondResource);
        final ITranslationResponse[] thirdObjects = translationParser.convert(mThirdResource);
        final ITranslationResponse[] fourthObjects = translationParser.convert(mFourthResource);

        assertEquals(2, firstObjects.length);
        assertEquals(1, secondObjects.length);
        assertEquals(1, thirdObjects.length);
        assertNull(fourthObjects);

        assertEquals("Hallo", firstObjects[0].getTranslatedText());
        assertEquals("Anastasie", firstObjects[1].getTranslatedText());
        assertEquals("en", firstObjects[0].getSourceLanguageKey());

        assertEquals("Hallo", secondObjects[0].getTranslatedText());
        assertEquals("en", secondObjects[0].getSourceLanguageKey());

        assertEquals("Hallo", thirdObjects[0].getTranslatedText());
        assertEquals(null, thirdObjects[0].getSourceLanguageKey());

    }

    @After
    public void tearDown() {
        try {
            mFirstResource.close();
            mSecondResource.close();
            mThirdResource.close();

        } catch (final IOException ignored) {
        }
    }

}
