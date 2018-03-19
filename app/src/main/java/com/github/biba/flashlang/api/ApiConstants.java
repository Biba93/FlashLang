package com.github.biba.flashlang.api;

public interface ApiConstants {

    final class Url {

        public static final String TRANSLATION_URL_TEMPLATE =
                "https://translation.googleapis.com/language/translate/v2" +
                        "?key=%s" +
                        "&source=%s" +
                        "&target=%s" +
                        "&q=%s";
    }

    enum LanguageKeys {
        be,
        zh,
        da,
        nl,
        no,
        en,
        et,
        el,
        fi,
        fr,
        de,
        hu,
        it,
        ja,
        ko,
        lt,
        pl,
        es,
        sv,
        tr,
        uk,
        ru,
        cs,
        sr
    }

}
