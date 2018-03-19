package com.github.biba.flashlang;

import android.graphics.Bitmap;

public interface Constants {

    final class Strings {

        public static final String EMPTY_STRING = "";
    }

    final class Db {

        public static final String DB_NAME = "flashlang.info";

        public static final int DB_VERSION = 1;
    }

    final class ImageLoader {

        public static final String DISK_CACHE_DIR = "/images";

        public static final int MEMORY_CACHE_SIZE = 50 * 1024 * 1024;
    }

    final class FirebaseStorage {

        public static final Bitmap.CompressFormat DEFAULT_COMPRESS_FORMAT = Bitmap.CompressFormat.JPEG;

        public static final int DEFAULT_COMPRESS_QUALITY = 100;

        public static final String CARD_IMAGE_FOLDER_NAME = "cards";

        public static final String USER_IMAGE_FOLDER_NAME = "users";

        public static final String LANGUAGES_IMAGE_FOLDER_NAME = "languages";

        public static final String BASE_IMAGE_FORMAT = ".jpg";

        public static final String LANGUAGE_IMAGE_FORMAT = ".png";

        public static final String FULL_IMAGE_PATH_TEMPLATE = "%s/%s%s";
    }

    final class FirebaseDb {

        public static final String API_KEY_REF = "apikey";
    }

}
