package com.github.biba.flashlang.domain.models.achievement;

import android.database.Cursor;

import com.github.biba.flashlang.db.IDbModel;
import com.github.biba.flashlang.db.connector.ICursorConverter;
import com.github.biba.flashlang.domain.db.Selector;
import com.github.biba.flashlang.firebase.db.annotations.FirebaseDbElement;
import com.github.biba.flashlang.firebase.db.connector.IDataSnapshotConverter;
import com.github.biba.lib.db.annotations.dbPrimaryKey;
import com.github.biba.lib.db.annotations.dbTable;
import com.github.biba.lib.db.annotations.dbTableElement;
import com.github.biba.lib.db.annotations.type.dbLong;
import com.github.biba.lib.db.annotations.type.dbString;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.PropertyName;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

import static com.github.biba.flashlang.domain.models.achievement.Achievement.DbKeys.ID;
import static com.github.biba.flashlang.domain.models.achievement.Achievement.DbKeys.OWNER_ID;
import static com.github.biba.flashlang.domain.models.achievement.Achievement.DbKeys.TABLE_NAME;
import static com.github.biba.flashlang.domain.models.achievement.Achievement.DbKeys.TOTAL_CONNECTIONS;
import static com.github.biba.flashlang.domain.models.achievement.Achievement.DbKeys.TOTAL_WORDS;

@dbTable(name = TABLE_NAME)
@dbTableElement(targetTableName = TABLE_NAME)
@FirebaseDbElement(targetTableName = TABLE_NAME)
public class Achievement implements IAchievement, IDbModel<String> {

    @dbPrimaryKey(isNull = false)
    @dbString(name = ID)
    @PropertyName(ID)
    private final String mId;

    @dbString(name = OWNER_ID)
    @PropertyName(OWNER_ID)
    private final String mOwnerId;

    @dbLong(name = TOTAL_CONNECTIONS)
    @PropertyName(TOTAL_CONNECTIONS)
    private long mTotalConnections;

    @dbLong(name = TOTAL_WORDS)
    @PropertyName(TOTAL_WORDS)
    private long mTotalWords;

    public Achievement(final String pId, final String pOwnerId) {
        mId = pId;
        mOwnerId = pOwnerId;
    }

    Achievement(final String pId, final String pOwnerId, final long pTotalConnections, final long pTotalWords) {
        mId = pId;
        mOwnerId = pOwnerId;
        mTotalConnections = pTotalConnections;
        mTotalWords = pTotalWords;
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
    public long getTotalConnections() {
        return mTotalConnections;
    }

    @Override
    public long getTotalWords() {
        return mTotalWords;
    }

    public void setTotalConnections(final long pTotalConnections) {
        mTotalConnections = pTotalConnections;
    }

    public void setTotalWords(final long pTotalWords) {
        mTotalWords = pTotalWords;
    }

    @Override
    public Map<String, Object> convertToInsert() {
        final Map<String, Object> map = new HashMap<>();
        map.put(ID, this.getId());
        map.put(OWNER_ID, this.getOwnerId());
        map.put(TOTAL_CONNECTIONS, this.getTotalConnections());
        map.put(TOTAL_WORDS, this.getTotalWords());
        return map;
    }

    @Override
    public Map<String, Object> convertToUpdate() {
        final Map<String, Object> map = new HashMap<>();
        map.put(TOTAL_CONNECTIONS, this.getTotalConnections());
        map.put(TOTAL_WORDS, this.getTotalWords());
        return map;
    }

    public static final class DbKeys {

        public static final String TABLE_NAME = "achievemnts";
        public static final String ID = "id";
        public static final String OWNER_ID = "ownerid";
        public static final String TOTAL_CONNECTIONS = "totalconnections";
        public static final String TOTAL_WORDS = "totalwords";
    }

    public static class CursorConverter implements ICursorConverter<Achievement> {

        @Override
        public Achievement convert(@NotNull final Cursor pCursor) {
            final String id = pCursor.getString(pCursor.getColumnIndex(DbKeys.ID));
            final String owner = pCursor.getString(pCursor.getColumnIndex(DbKeys.OWNER_ID));
            final long connections = pCursor.getInt(pCursor.getColumnIndex(DbKeys.TOTAL_CONNECTIONS));
            final long words = pCursor.getInt(pCursor.getColumnIndex(DbKeys.TOTAL_WORDS));

            return new Achievement(id, owner, connections, words);
        }
    }

    public static class DataSnapshotConverter implements IDataSnapshotConverter<Achievement> {

        @Override
        public Achievement convert(@NotNull final DataSnapshot pSnapshot) {
            final String id = (String) pSnapshot.child(DbKeys.ID).getValue();
            final String owner = (String) pSnapshot.child(DbKeys.OWNER_ID).getValue();
            final long connections = (long) pSnapshot.child(DbKeys.TOTAL_CONNECTIONS).getValue();
            final long words = (long) pSnapshot.child(DbKeys.TOTAL_WORDS).getValue();

            return new Achievement(id, owner, connections, words);
        }
    }

    public static class ByIdSelector extends Selector.ByFieldSelector {

        public ByIdSelector(@NotNull final String pFieldValue) {
            super(DbKeys.ID, pFieldValue);
        }
    }

    public static class ByOwnerIdSelector extends Selector.ByFieldSelector {

        public ByOwnerIdSelector(@NotNull final String pFieldValue) {
            super(DbKeys.OWNER_ID, pFieldValue);
        }
    }

}
