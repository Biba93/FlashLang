package com.github.biba.flashlang.ui;

public interface ViewConstants {

    final class Splash {

        public static final int ANIMATION_DURATION_MILLIS = 2000;
    }

    final class Auth {

        public static final int SIGN_IN_POSITION = 0;
        public static final int SIGN_UP_POSITION = 1;
        public static final int FRAGMENT_COUNT = 2;
    }

    final class Main {

        public static final int VIEW_PAGER_OFFSCREEN_PAGE_LIMIT = 3;

        public static final int COLLECTION_FRAGMENT_POSITION = 0;
        public static final int TRANSLATE_FRAGMENT_POSITION = 1;
        public static final int USER_INFO_FRAGMENT_POSITION = 2;

        public static final int FRAGMENT_COUNT = 3;
    }

    final class Collection {

        public static final String SOURCE_COLLECTION_FRAGMENT_TAG = "sourcecollection";
        public static final String TARGET_COLLECTION_FRAGMENT_TAG = "targetcollection";
        public static final String CARDS_COLLECTION_FRAGMENT_TAG = "cardscollection";

        public static final String ARGS = "args";

        public static final String ARGS_SOURCE_LANGUAGE_KEY = "sourcelang";
        public static final String ARGS_SOURCE_COVER_KEY = "sourcecover";
        public static final String ARGS_TARGET_LANGUAGE_KEY = "targetlang";
        public static final String ARGS_TARGET_COVER_KEY = "targetcover";
    }

    final class CardGame {

        public static final String ARGS = "args";

        public static final String ARGS_SOURCE_LANGUAGE_KEY = "sourcelang";
        public static final String ARGS_TARGET_LANGUAGE_KEY = "targetlang";
    }

}
