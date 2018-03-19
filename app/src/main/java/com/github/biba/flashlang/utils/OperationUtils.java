package com.github.biba.flashlang.utils;

import android.database.Cursor;

import com.github.biba.flashlang.db.connector.IDbTableConnector;
import com.github.biba.flashlang.domain.models.achievement.Achievement;
import com.github.biba.flashlang.domain.models.card.Card;
import com.github.biba.flashlang.domain.models.collection.Collection;
import com.github.biba.flashlang.domain.models.user.User;
import com.github.biba.flashlang.firebase.db.connector.IFirebaseDbConnector;
import com.github.biba.lib.utils.IOUtils;

import java.util.List;

public final class OperationUtils {

    public static String getIdForCard() {
        return IFirebaseDbConnector.Impl.Companion.getInstance()
                .getKeyForElement(Card.DbKeys.TABLE_NAME);
    }

    public static String getIdForAchievement() {
        return IFirebaseDbConnector.Impl.Companion.getInstance()
                .getKeyForElement(Achievement.DbKeys.TABLE_NAME);
    }

    public static String getIdForCollection() {
        return IFirebaseDbConnector.Impl.Companion.getInstance()
                .getKeyForElement(Collection.DbKeys.TABLE_NAME);
    }

    public static String getCollectionId(final String pUserID, final String pSourceLanguage, final String pTargetLanguage) {

        final List<Collection> collections = IDbTableConnector.Companion.getInstance()
                .get(Collection.DbKeys.TABLE_NAME, new Collection.CursorConverter(), null,
                        new Collection.ByOwnerIdSelector(pUserID),
                        new Collection.BySourceLanguageSelector(pSourceLanguage),
                        new Collection.ByTargetLanguageSelector(pTargetLanguage));
        if (collections != null && !collections.isEmpty()) {
            return collections.get(0).getId();
        } else {
            return null;
        }
    }

    public static boolean isCollectionEmpty(final String pCollectionId) {
        Cursor cursor = null;
        try {
            cursor = IDbTableConnector.Companion.getInstance().get(Collection.DbKeys.TABLE_NAME,
                    null, new User.ByIdSelector(pCollectionId));
            if (cursor == null) {
                return true;
            }
            if (cursor.getCount() <= 0) {
                return true;
            }
        } finally {
            IOUtils.close(cursor);
        }
        return false;
    }
}
