package com.github.biba.flashlang.api.parser;

import com.github.biba.flashlang.api.response.TranslationResponse;
import com.google.gson.annotations.SerializedName;

public class ResponseModel {

    @SerializedName(Keys.DATA)
    private Translations mTranslations;

    @SerializedName(Keys.ERROR)
    private Error mError;

    public Translations getTranslations() {
        return mTranslations;
    }

    public Error getError() {
        return mError;
    }

    class Translations {

        @SerializedName(Keys.DATA_TRANSLATIONS)
        private TranslationResponse[] mElements;

        public TranslationResponse[] getElements() {
            return mElements;
        }
    }

    class Error {

        @SerializedName(Keys.ERROR_CODE)
        private int mCode;

        @SerializedName(Keys.ERROR_MESSAGE)
        private String mMessage;

        public int getCode() {
            return mCode;
        }

        public String getMessage() {
            return mMessage;
        }
    }

    final class Keys {

        static final String DATA = "data";
        static final String ERROR = "error";
        public static final String DATA_TRANSLATIONS = "translations";
        public static final String ERROR_CODE = "code";
        public static final String ERROR_MESSAGE = "message";

    }
}
