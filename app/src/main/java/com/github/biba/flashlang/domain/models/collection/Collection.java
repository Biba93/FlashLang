package com.github.biba.flashlang.domain.models.collection;

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

import static com.github.biba.flashlang.domain.models.collection.Collection.DbKeys.ID;
import static com.github.biba.flashlang.domain.models.collection.Collection.DbKeys.OWNER_ID;
import static com.github.biba.flashlang.domain.models.collection.Collection.DbKeys.SOURCE_LANGUAGE;
import static com.github.biba.flashlang.domain.models.collection.Collection.DbKeys.SOURCE_LANGUAGE_COVER;
import static com.github.biba.flashlang.domain.models.collection.Collection.DbKeys.TABLE_NAME;
import static com.github.biba.flashlang.domain.models.collection.Collection.DbKeys.TARGET_LANGUAGE;
import static com.github.biba.flashlang.domain.models.collection.Collection.DbKeys.TARGET_LANGUAGE_COVER;

@dbTable(name = TABLE_NAME)
@dbTableElement(targetTableName = TABLE_NAME)
@FirebaseDbElement(targetTableName = TABLE_NAME)
public class Collection implements ICollection, IDbModel<String> {

    @dbPrimaryKey(isNull = false)
    @dbString(name = ID)
    @PropertyName(ID)
    private final String mId;

    @dbString(name = OWNER_ID)
    @PropertyName(OWNER_ID)
    private final String mOwnerId;

    @dbString(name = SOURCE_LANGUAGE)
    @PropertyName(SOURCE_LANGUAGE)
    private final String mSourceLanguage;

    @dbString(name = SOURCE_LANGUAGE_COVER)
    @PropertyName(SOURCE_LANGUAGE_COVER)
    private final String mSourceLanguageCover;

    @dbString(name = TARGET_LANGUAGE)
    @PropertyName(TARGET_LANGUAGE)
    private final String mTargetLanguage;

    @dbString(name = TARGET_LANGUAGE_COVER)
    @PropertyName(TARGET_LANGUAGE_COVER)
    private final String mTargetLanguageCover;

    public Collection(final CollectionBuilder pCollectionBuilder) {
        mId = pCollectionBuilder.getId();
        mOwnerId = pCollectionBuilder.getOwnerId();
        mSourceLanguage = pCollectionBuilder.getSourceLanguage();
        mTargetLanguage = pCollectionBuilder.getTargetLanguage();
        mSourceLanguageCover = pCollectionBuilder.getSourceLanguageCover();
        mTargetLanguageCover = pCollectionBuilder.getTargetLanguageCover();
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
    public String getSourceLanguage() {
        return mSourceLanguage;
    }

    @Override
    public String getTargetLanguage() {
        return mTargetLanguage;
    }

    @Override
    public String getSourceLanguageCover() {
        return mSourceLanguageCover;
    }

    @Override
    public String getTargetLanguageCover() {
        return mTargetLanguageCover;
    }

    @Override
    public HashMap<String, Object> convertToInsert() {
        final HashMap<String, Object> map = new HashMap<>();
        map.put(ID, this.getId());
        map.put(OWNER_ID, this.getOwnerId());
        map.put(SOURCE_LANGUAGE, this.getSourceLanguage());
        map.put(TARGET_LANGUAGE, this.getTargetLanguage());
        map.put(SOURCE_LANGUAGE_COVER, this.getSourceLanguageCover());
        map.put(TARGET_LANGUAGE_COVER, this.getTargetLanguageCover());
        return map;
    }

    @Override
    public HashMap<String, Object> convertToUpdate() {
        final HashMap<String, Object> map = new HashMap<>();
        map.put(SOURCE_LANGUAGE_COVER, this.getSourceLanguageCover());
        map.put(TARGET_LANGUAGE_COVER, this.getTargetLanguageCover());
        return map;
    }

    public final class DbKeys {

        public static final String TABLE_NAME = "collections";
        public static final String ID = "id";
        public static final String OWNER_ID = "ownerid";
        public static final String SOURCE_LANGUAGE = "sourcelanguage";
        public static final String SOURCE_LANGUAGE_COVER = "sourcelanguagecover";
        public static final String TARGET_LANGUAGE = "targetlanguage";
        public static final String TARGET_LANGUAGE_COVER = "targetlanguagecover";
    }

    public static class CursorConverter implements ICursorConverter<Collection> {

        @Override
        public Collection convert(@NonNull final Cursor pCursor) {
            final String id = pCursor.getString(pCursor.getColumnIndex(ID));
            final String owner = pCursor.getString(pCursor.getColumnIndex(OWNER_ID));
            final String source = pCursor.getString(pCursor.getColumnIndex(SOURCE_LANGUAGE));
            final String target = pCursor.getString(pCursor.getColumnIndex(TARGET_LANGUAGE));
            final String sourceCover = pCursor.getString(pCursor.getColumnIndex(SOURCE_LANGUAGE_COVER));
            final String targetCover = pCursor.getString(pCursor.getColumnIndex(TARGET_LANGUAGE_COVER));

            return new CollectionBuilder().setId(id).setOwnerId(owner)
                    .setSourceLanguage(source).setTargetLanguage(target)
                    .setSourceLanguageCover(sourceCover)
                    .setTargetLanguageCover(targetCover)
                    .createCollection();
        }
    }

    public static class DataSnapshotConverter implements IDataSnapshotConverter<Collection> {

        @Override
        public Collection convert(@NotNull final DataSnapshot pSnapshot) {
            final String id = (String) pSnapshot.child(ID).getValue();
            final String owner = (String) pSnapshot.child(OWNER_ID).getValue();
            final String source = (String) pSnapshot.child(SOURCE_LANGUAGE).getValue();
            final String target = (String) pSnapshot.child(TARGET_LANGUAGE).getValue();
            final String sourceCover = (String) pSnapshot.child(SOURCE_LANGUAGE_COVER).getValue();
            final String targetCover = (String) pSnapshot.child(TARGET_LANGUAGE_COVER).getValue();

            return new CollectionBuilder().setId(id).setOwnerId(owner)
                    .setSourceLanguage(source).setTargetLanguage(target)
                    .setSourceLanguageCover(sourceCover)
                    .setTargetLanguageCover(targetCover)
                    .createCollection();
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
