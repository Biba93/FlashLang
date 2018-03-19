package com.github.biba.flashlang.domain.models.card;

import android.database.Cursor;
import android.support.annotation.NonNull;

import com.github.biba.flashlang.db.IDbModel;
import com.github.biba.flashlang.db.connector.ICursorConverter;
import com.github.biba.flashlang.domain.db.Selector;
import com.github.biba.flashlang.firebase.db.annotations.FirebaseDbElement;
import com.github.biba.flashlang.firebase.db.connector.IDataSnapshotConverter;
import com.github.biba.lib.db.annotations.dbPrimaryKey;
import com.github.biba.lib.db.annotations.dbTable;
import com.github.biba.lib.db.annotations.dbTableElement;
import com.github.biba.lib.db.annotations.type.dbString;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.PropertyName;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

import static com.github.biba.flashlang.domain.models.card.Card.DbKeys.ID;
import static com.github.biba.flashlang.domain.models.card.Card.DbKeys.OWNER_ID;
import static com.github.biba.flashlang.domain.models.card.Card.DbKeys.PICTURE;
import static com.github.biba.flashlang.domain.models.card.Card.DbKeys.REFERRED_COLLECTION_ID;
import static com.github.biba.flashlang.domain.models.card.Card.DbKeys.SOURCE_LANGUAGE;
import static com.github.biba.flashlang.domain.models.card.Card.DbKeys.SOURCE_TEXT;
import static com.github.biba.flashlang.domain.models.card.Card.DbKeys.TABLE_NAME;
import static com.github.biba.flashlang.domain.models.card.Card.DbKeys.TARGET_LANGUAGE;
import static com.github.biba.flashlang.domain.models.card.Card.DbKeys.TRANSLATED_TEXT;

@dbTable(name = TABLE_NAME)
@dbTableElement(targetTableName = TABLE_NAME)
@FirebaseDbElement(targetTableName = TABLE_NAME)
public class Card implements ICard, IDbModel<String> {

    @dbPrimaryKey(isNull = false)
    @dbString(name = ID)
    @PropertyName(ID)
    private final String mId;

    @dbString(name = OWNER_ID)
    @PropertyName(OWNER_ID)
    private final String mOwnerId;

    @dbString(name = REFERRED_COLLECTION_ID)
    @PropertyName(REFERRED_COLLECTION_ID)
    private final String mReferredCollectionId;

    @dbString(name = SOURCE_LANGUAGE)
    @PropertyName(SOURCE_LANGUAGE)
    private final String mSourceLanguageKey;

    @dbString(name = SOURCE_TEXT)
    @PropertyName(SOURCE_TEXT)
    private String mSourceText;

    @dbString(name = TARGET_LANGUAGE)
    @PropertyName(TARGET_LANGUAGE)
    private final String mTargetLanguage;

    @dbString(name = TRANSLATED_TEXT)
    @PropertyName(TRANSLATED_TEXT)
    private String mTranslatedText;

    @dbString(name = PICTURE)
    @PropertyName(PICTURE)
    private String mPictureUrl;

    Card(final String pId, final String pOwnerId, final String pReferredCollectionId, final String pSourceLanguageKey,
         final String pSourceText, final String pTargetLanguage, final String pTranslatedText,
         final String pPictureUrl) {
        mId = pId;
        mOwnerId = pOwnerId;
        mReferredCollectionId = pReferredCollectionId;
        mSourceLanguageKey = pSourceLanguageKey;
        mSourceText = pSourceText;
        mTargetLanguage = pTargetLanguage;
        mTranslatedText = pTranslatedText;
        mPictureUrl = pPictureUrl;
    }

    public Card(final CardBuilder pCardBuilder) {
        mId = pCardBuilder.getId();
        mOwnerId = pCardBuilder.getOwnerId();
        mReferredCollectionId = pCardBuilder.getReferredCollectionId();
        mSourceLanguageKey = pCardBuilder.getSourceLanguageKey();
        mSourceText = pCardBuilder.getSourceText();
        mTargetLanguage = pCardBuilder.getTargetLanguageKey();
        mTranslatedText = pCardBuilder.getTranslatedText();
        mPictureUrl = pCardBuilder.getPictureUrl();
    }

    public void setSourceText(final String pSourceText) {
        mSourceText = pSourceText;
    }

    public void setTranslatedText(final String pTranslatedText) {
        mTranslatedText = pTranslatedText;
    }

    public void setPictureUrl(final String pPictureUrl) {
        mPictureUrl = pPictureUrl;
    }

    @Override
    public String getId() {
        return mId;
    }

    @Override
    public String getOwnerId() {
        return mOwnerId;
    }

    @Override
    public String getReferredCollectionId() {
        return mReferredCollectionId;
    }

    @Override
    public String getSourceLanguageKey() {
        return mSourceLanguageKey;
    }

    @Override
    public String getSourceText() {
        return mSourceText;
    }

    @Override
    public String getTargetLanguageKey() {
        return mTargetLanguage;
    }

    @Override
    public String getTranslatedText() {
        return mTranslatedText;
    }

    @Override
    public String getPictureUrl() {
        return mPictureUrl;
    }

    @Override
    public HashMap<String, Object> convertToInsert() {
        final HashMap<String, Object> map = new HashMap<>();
        map.put(ID, this.getId());
        map.put(OWNER_ID, this.getOwnerId());
        map.put(REFERRED_COLLECTION_ID, this.getReferredCollectionId());
        map.put(SOURCE_LANGUAGE, this.getSourceLanguageKey());
        map.put(SOURCE_TEXT, this.getSourceText());
        map.put(TARGET_LANGUAGE, this.getTargetLanguageKey());
        map.put(TRANSLATED_TEXT, this.getTranslatedText());
        map.put(PICTURE, this.getPictureUrl());
        return map;
    }

    @Override
    public HashMap<String, Object> convertToUpdate() {
        final HashMap<String, Object> map = new HashMap<>();
        map.put(SOURCE_LANGUAGE, this.getSourceLanguageKey());
        map.put(SOURCE_TEXT, this.getSourceText());
        map.put(TARGET_LANGUAGE, this.getTargetLanguageKey());
        map.put(TRANSLATED_TEXT, this.getTranslatedText());
        map.put(PICTURE, this.getPictureUrl());
        return map;
    }

    public final class DbKeys {

        public static final String TABLE_NAME = "cards";
        public static final String ID = "id";
        public static final String OWNER_ID = "ownerid";
        public static final String REFERRED_COLLECTION_ID = "collectionid";
        public static final String SOURCE_LANGUAGE = "sourceLanguage";
        public static final String SOURCE_TEXT = "sourceText";
        public static final String TARGET_LANGUAGE = "targetLanguage";
        public static final String TRANSLATED_TEXT = "translatedText";
        public static final String PICTURE = "picture";
    }

    public static class CursorConverter implements ICursorConverter<Card> {

        @Override
        public Card convert(@NonNull final Cursor pCursor) {
            final String id = pCursor.getString(pCursor.getColumnIndex(ID));
            final String ow = pCursor.getString(pCursor.getColumnIndex(OWNER_ID));
            final String ref = pCursor.getString(pCursor.getColumnIndex(REFERRED_COLLECTION_ID));
            final String sl = pCursor.getString(pCursor.getColumnIndex(SOURCE_LANGUAGE));
            final String st = pCursor.getString(pCursor.getColumnIndex(SOURCE_TEXT));
            final String tl = pCursor.getString(pCursor.getColumnIndex(TARGET_LANGUAGE));
            final String tt = pCursor.getString(pCursor.getColumnIndex(TRANSLATED_TEXT));
            final String pic = pCursor.getString(pCursor.getColumnIndex(PICTURE));

            return new Card(id, ow, ref, sl, st, tl, tt, pic);
        }
    }

    public static class DataSnapshotConverter implements IDataSnapshotConverter<Card> {

        @NonNull
        @Override
        public Card convert(@NotNull final DataSnapshot pSnapshot) {
            final String id = (String) pSnapshot.child(ID).getValue();
            final String ow = (String) pSnapshot.child(OWNER_ID).getValue();
            final String ref = (String) pSnapshot.child(REFERRED_COLLECTION_ID).getValue();
            final String sl = (String) pSnapshot.child(SOURCE_LANGUAGE).getValue();
            final String st = (String) pSnapshot.child(SOURCE_TEXT).getValue();
            final String tl = (String) pSnapshot.child(TARGET_LANGUAGE).getValue();
            final String tt = (String) pSnapshot.child(TRANSLATED_TEXT).getValue();
            final String pic = (String) pSnapshot.child(PICTURE).getValue();

            return new Card(id, ow, ref, sl, st, tl, tt, pic);
        }
    }

    public static class ByIdSelector extends Selector.ByFieldSelector {

        public ByIdSelector(final String pFiledValue) {
            super(ID, pFiledValue);
        }
    }

    public static class ByOwnerIdSelector extends Selector.ByFieldSelector {

        public ByOwnerIdSelector(final String pFiledValue) {
            super(OWNER_ID, pFiledValue);
        }
    }

    public static class ByReferredCollectionIdSelector extends Selector.ByFieldSelector {

        public ByReferredCollectionIdSelector(final String pFiledValue) {
            super(REFERRED_COLLECTION_ID, pFiledValue);
        }
    }

    public static class BySourceLanguageSelector extends Selector.ByFieldSelector {

        public BySourceLanguageSelector(final String pFiledValue) {
            super(SOURCE_LANGUAGE, pFiledValue);
        }
    }

    public static class ByTargetLanguageSelector extends Selector.ByFieldSelector {

        public ByTargetLanguageSelector(final String pFiledValue) {
            super(TARGET_LANGUAGE, pFiledValue);
        }
    }

}
